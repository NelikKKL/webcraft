package net.minecraft.client.web;

import java.nio.Buffer;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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
    final ArrayDeque<float[]> matrixStack = new ArrayDeque<>();

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
        matrixStack.push(current().clone());
    }

    public void popMatrix() {
        float[] top = matrixStack.isEmpty() ? null : matrixStack.pop();
        if (top != null) {
            System.arraycopy(top, 0, current(), 0, 16);
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

    private final Map<Integer, java.util.List<Runnable>> displayLists = new HashMap<>();
    private int nextListId = 1;
    private java.util.List<Runnable> recording = null;
    private int recordingId = -1;

    public int genLists(int count) {
        int base = nextListId;
        for (int i = 0; i < count; i++) {
            displayLists.put(nextListId++, new java.util.ArrayList<>());
        }
        return base;
    }

    public void newList(int id, int mode) {
        recording = displayLists.computeIfAbsent(id, k -> new java.util.ArrayList<>());
        recording.clear();
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
        java.util.List<Runnable> list = displayLists.get(id);
        if (list != null) {
            for (Runnable r : list) r.run();
        }
    }

    public void deleteLists(int id, int count) {
        for (int i = 0; i < count; i++) displayLists.remove(id + i);
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

    public final AttrState[] attrs = { new AttrState(), new AttrState(), new AttrState(), new AttrState() };

    // Один переиспользуемый scratch VBO для client-side заливок каждый кадр —
    // избегаем create/delete буфера на каждый draw call.
    public WebGLBuffer scratchVbo = null;
}
