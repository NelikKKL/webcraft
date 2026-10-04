package net.minecraft.client.web;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import org.teavm.jso.typedarrays.Float32Array;

/**
 * Эмуляция fixed-function OpenGL 1.1 (immediate mode, матричный стек,
 * display lists, простое освещение и туман) поверх WebGL2, который умеет
 * только programmable pipeline.
 *
 * Стратегия:
 *  - Матрицы (MODELVIEW/PROJECTION) храним как float[16] в Java и
 *    пересчитываем при каждом изменении (glTranslatef/glRotatef/glScalef).
 *    Перед draw-вызовом текущая matrix отправляется в шейдер как uniform.
 *  - Display list = список записанных вызовов GL11 (лямбды/Runnable).
 *    glNewList начинает запись, glEndList заканчивает, glCallList
 *    воспроизводит записанные вызовы. Это единственный практичный способ
 *    эмулировать display lists без доступа к их будущему содержимому заранее.
 *  - Client-side vertex arrays (glVertexPointer с прямым Buffer, а не offset)
 *    заливаются в scratch VBO непосредственно перед glDrawArrays.
 *  - Освещение (glLight) и туман (glFog) не гоняются через настоящий GL —
 *    их параметры копятся в полях этого класса и передаются в единый
 *    universal-шейдер (см. Shaders.java) как uniform'ы.
 *
 * Единственный экземпляр на приложение — доступен через GLState.INSTANCE,
 * а org.lwjgl.opengl.GL11 — это набор статических методов-делегатов сюда
 * (чтобы сигнатуры 1:1 совпадали с оригинальным LWJGL API и decomp-код
 * компилировался без изменений в вызывающих местах).
 */
public final class GLState {

    public static GLState INSTANCE;

    public final WebGL2 gl;
    public final Shaders shaders;

    public GLState(WebGL2 gl) {
        this.gl = gl;
        this.shaders = new Shaders(gl);
        loadIdentity(projection);
        loadIdentity(modelview);
        // Стек изначально пуст — pushMatrix()/popMatrix() симметричны, как в
        // реальном GL. НЕ кладём сюда фиктивную "нижнюю" запись: если
        // где-то в вызывающем коде случится лишний popMatrix() без парного
        // push (ошибка), popMatrix() должен быть безопасным no-op'ом (см.
        // ниже), а не тихо обнулять текущую матрицу восстановлением мусора.
    }

    // ---------------------------------------------------------------
    // Матричный стек
    // ---------------------------------------------------------------

    /** GL_MODELVIEW = 5888, GL_PROJECTION = 5889 (как в реальном GL11). */
    public static final int MODELVIEW = 5888;
    public static final int PROJECTION = 5889;

    private int currentMatrixMode = MODELVIEW;
    public final float[] projection = new float[16];
    public final float[] modelview = new float[16];
    // PERF: стек матриц на заранее выделенных массивах — раньше каждый
    // glPushMatrix делал current().clone() (новый float[16]) + ArrayDeque.
    // Семантика прежняя: один общий стек для обоих режимов матрицы, pop
    // восстанавливает в ТЕКУЩУЮ матрицу, лишний pop — no-op.
    private float[][] matrixStack = new float[64][16];
    private int matrixSp = 0;

    public void matrixMode(int mode) {
        this.currentMatrixMode = mode;
    }

    private float[] current() {
        return currentMatrixMode == PROJECTION ? projection : modelview;
    }

    public void loadIdentity() {
        loadIdentity(current());
    }

    private static void loadIdentity(float[] m) {
        for (int i = 0; i < 16; i++) m[i] = 0f;
        m[0] = m[5] = m[10] = m[15] = 1f;
    }

    public void pushMatrix() {
        if (matrixSp == matrixStack.length) {
            float[][] bigger = new float[matrixStack.length * 2][];
            System.arraycopy(matrixStack, 0, bigger, 0, matrixSp);
            for (int i = matrixSp; i < bigger.length; i++) bigger[i] = new float[16];
            matrixStack = bigger;
        }
        System.arraycopy(current(), 0, matrixStack[matrixSp++], 0, 16);
    }

    public void popMatrix() {
        if (matrixSp > 0) {
            System.arraycopy(matrixStack[--matrixSp], 0, current(), 0, 16);
        }
    }

    public void translatef(float x, float y, float z) {
        Mat4.translate(current(), x, y, z);
    }

    public void rotatef(float angleDeg, float x, float y, float z) {
        Mat4.rotate(current(), angleDeg, x, y, z);
    }

    public void scalef(float x, float y, float z) {
        Mat4.scale(current(), x, y, z);
    }

    public void ortho(double left, double right, double bottom, double top, double near, double far) {
        Mat4.ortho(current(), left, right, bottom, top, near, far);
    }

    public void frustum(double left, double right, double bottom, double top, double near, double far) {
        Mat4.frustum(current(), left, right, bottom, top, near, far);
    }

    // ---------------------------------------------------------------
    // Display lists
    // ---------------------------------------------------------------

    /**
     * Запись display list'а, владеющая GPU-ресурсами (VBO/VAO). Освобождается,
     * когда list перезаписывается (glNewList) или удаляется (glDeleteLists).
     */
    public interface Disposable {
        void dispose();
    }

    // PERF: таблица list'ов индексируется id напрямую (id выдаются подряд
    // счётчиком) — вместо HashMap<Integer,...> с боксингом на каждый
    // glCallList (а их сотни за кадр: по одному на каждый видимый чанк).
    private java.util.ArrayList<Runnable>[] listTable = newTable(1024);
    private int nextListId = 1;
    private java.util.List<Runnable> recording = null;
    private int recordingId = -1;

    @SuppressWarnings("unchecked")
    private static java.util.ArrayList<Runnable>[] newTable(int n) {
        return (java.util.ArrayList<Runnable>[]) new java.util.ArrayList[n];
    }

    private void ensureTable(int id) {
        if (id < listTable.length) return;
        int n = listTable.length;
        while (n <= id) n *= 2;
        java.util.ArrayList<Runnable>[] bigger = newTable(n);
        System.arraycopy(listTable, 0, bigger, 0, listTable.length);
        listTable = bigger;
    }

    private static void disposeAll(java.util.List<Runnable> list) {
        for (int i = 0, n = list.size(); i < n; i++) {
            Runnable r = list.get(i);
            if (r instanceof Disposable) ((Disposable) r).dispose();
        }
    }

    public int genLists(int count) {
        int base = nextListId;
        ensureTable(base + count);
        for (int i = 0; i < count; i++) {
            listTable[nextListId++] = new java.util.ArrayList<>();
        }
        return base;
    }

    public void newList(int id, int mode) {
        ensureTable(id);
        java.util.ArrayList<Runnable> l = listTable[id];
        if (l == null) {
            l = new java.util.ArrayList<>();
            listTable[id] = l;
        } else {
            // PERF/утечка: старые GPU-буферы перезаписываемого list'а (чанк
            // перестраивается постоянно) нужно освободить.
            disposeAll(l);
            l.clear();
        }
        recording = l;
        recordingId = id;
        // mode == GL_COMPILE_AND_EXECUTE (4098) должен и исполнять сразу —
        // не встречается в decomp-коде для миров, поэтому не реализуем;
        // если понадобится, добавить прямой вызов recorded-команды при записи.
    }

    public void endList() {
        recording = null;
        recordingId = -1;
    }

    public void callList(int id) {
        if (id < 0 || id >= listTable.length) return;
        java.util.ArrayList<Runnable> list = listTable[id];
        if (list != null) {
            for (int i = 0, n = list.size(); i < n; i++) list.get(i).run();
        }
    }

    public void deleteLists(int id, int count) {
        for (int i = 0; i < count; i++) {
            int k = id + i;
            if (k < 0 || k >= listTable.length) continue;
            java.util.ArrayList<Runnable> l = listTable[k];
            if (l != null) {
                disposeAll(l);
                listTable[k] = null;
            }
        }
    }

    /** true если сейчас идёт запись display list — вызовы нужно буферизовать, а не исполнять. */
    boolean isRecording() {
        return recording != null;
    }

    void record(Runnable r) {
        recording.add(r);
    }

    // Публичные алиасы для org.lwjgl.opengl (другой пакет) — GLState живёт в
    // net.minecraft.client.web и не может раскрывать package-private члены
    // напрямую пакету org.lwjgl.opengl, поэтому даём тонкие public-обёртки.
    public boolean isRecordingPublic() { return isRecording(); }
    public void recordPublic(Runnable r) { record(r); }

    // ---------------------------------------------------------------
    // Освещение и туман — параметры для универсального шейдера
    // ---------------------------------------------------------------

    public boolean lightingEnabled = false;
    public boolean fogEnabled = false;
    public float fogDensity = 0.001f;
    public float fogStart = 0f;
    public float fogEnd = 1000f;
    public int fogMode = 2048; // GL_EXP по умолчанию, как в реальном GL11
    public final float[] fogColor = { 1f, 1f, 1f, 1f };

    // Две directional-подобных лампы GL_LIGHT0/GL_LIGHT1, как использует
    // decomp (см. lclass.java) — позиция(w=0 => directional), diffuse, ambient.
    public final float[][] lightDiffuse = { {1,1,1,1}, {1,1,1,1} };
    public final float[][] lightPosition = { {0,1,0,0}, {0,-1,0,0} };
    public final float[] lightModelAmbient = { 0.2f, 0.2f, 0.2f, 1f };

    // ---------------------------------------------------------------
    // Активная текстура / прочее простое состояние
    // ---------------------------------------------------------------

    public WebGLTexture boundTexture = null;
    public int boundTextureId = 0;
    /** Запрошенный фильтр увеличения по id текстуры: true = GL_NEAREST (по умолчанию в игре). */
    public final java.util.HashMap<Integer, Boolean> texNearest = new java.util.HashMap<>();
    public boolean curNearest = true;
    public float r = 1, g = 1, b = 1, a = 1;
    public float nx = 0, ny = 1, nz = 0;
    public float alphaRef = 0f;
    public int alphaFunc = 519; // GL_ALWAYS
    public boolean textureEnabled = false;
    public boolean alphaTestEnabled = false;

    // int-хендлы <-> реальные WebGL-объекты. LWJGL API оперирует int id
    // (glGenTextures возвращает int, glBindTexture принимает int), а WebGL2
    // работает с непрозрачными объектами WebGLTexture — нужна таблица связи
    // в обе стороны для реализации glBindTexture/glDeleteTextures.
    public final Map<Integer, WebGLTexture> textures = new HashMap<>();
    public int nextTextureId = 1;

    // Текущий bound array buffer (для *Pointer(..., long offset) форм —
    // client code сначала делает glBindBuffer на VBO, потом задаёт offset).
    public WebGLBuffer boundArrayBuffer = null;

    // "Client-side" vertex array состояние (форма glVertexPointer(size, stride, FloatBuffer)):
    // сырые Buffer-ссылки на Java-память, заливаются в scratch VBO перед
    // непосредственно glDrawArrays, т.к. WebGL2 не умеет читать напрямую
    // из памяти Java-кучи.
    public static final int ATTR_POSITION = 0;
    public static final int ATTR_TEXCOORD = 1;
    public static final int ATTR_COLOR = 2;
    public static final int ATTR_NORMAL = 3;

    /** Описание одного vertex-атрибута — либо client-side Buffer, либо offset в уже забинженный VBO. */
    public static final class AttrState {
        public boolean fromClient;
        public Buffer clientBuffer;   // valid если fromClient
        public int clientBaseOffset;  // buffer.position() в момент вызова glXxxPointer — валиден если fromClient
        public long offset;           // valid если !fromClient (offset в уже забинженном VBO)
        public int size;
        public int stride;
        public boolean isByte;        // true для ATTR_COLOR/ATTR_NORMAL client-side (ByteBuffer, нормализованные)
    }

    // ---------------------------------------------------------------
    // PERF: кэши реального GL-состояния. Всё GL-состояние меняем только мы,
    // поэтому можно не слать в браузер вызовы, которые ничего не меняют
    // (раньше каждый draw call слал ~25 uniform-вызовов + 2 new Float32Array).
    // 0 = неизвестно (первый вызов всегда проходит).
    // ---------------------------------------------------------------
    public int stDepthTest, stBlend, stCull;            // 0 unknown, 1 on, 2 off
    public int stDepthMask;                             // 0 unknown, 1 true, 2 false
    public int stBlendSrc = -1, stBlendDst = -1, stDepthFunc = -1, stCullFace = -1;

    public WebGLTexture glTex = null;       // что реально привязано к TEXTURE_2D
    public boolean glTexValid = false;

    public boolean programBound = false;
    public boolean staticUniformsSet = false;
    public final float[] cProj = new float[16];
    public final float[] cMv = new float[16];
    public boolean cProjValid = false, cMvValid = false;
    public int cUseTex = -1, cUseLight = -1, cUseFog = -1, cNearest = -1, cAlphaFunc = -1;
    public float cAlphaRef = -1f;
    public final float[] cLight = new float[15];
    public boolean cLightValid = false;
    public final float[] cFog = new float[8];
    public boolean cFogValid = false;
    public final float[] tmpLight = new float[15];
    public final float[] tmpFog = new float[8];
    public Float32Array matBuf = null;       // переиспользуемый Float32Array(16)

    /** Какой VAO сейчас привязан (null = дефолтный, для immediate-режима). */
    public WebGLVertexArrayObject currentVao = null;

    // Тиры scratch-буферов для immediate-режима (GUI, руки, мобы, облака…):
    // размер 2^k float'ов, k = 8..24. Раньше на каждый draw выделялись новые
    // float[] + ArrayBuffer + Float32Array.
    public final float[][] stageTier = new float[17][];
    public final Float32Array[] stageTierJs = new Float32Array[17];

    public final AttrState[] attrs = { new AttrState(), new AttrState(), new AttrState(), new AttrState() };

    // Один переиспользуемый scratch VBO для client-side заливок каждый кадр —
    // избегаем create/delete буфера на каждый draw call.
    public WebGLBuffer scratchVbo = null;
}
