package org.lwjgl.opengl;

import net.minecraft.client.web.*;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint8Array;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Реализация отдельных GL-операций, вызываемая из GLDispatch. Разделена на
 * файл ради читаемости — GLDispatch отвечает за display-list recording,
 * этот класс — за фактическое выполнение операции над WebGL2/GLState.
 */
final class GLBridge {
    private GLBridge() {}

    // Копия ATTR_* констант из GLState.AttrState для удобства (Java не даёt
    // "import static" на inner class поля без явного класса-держателя —
    // используем GLState.ATTR_POSITION и т.д. напрямую, здесь только алиасы).
    static final int ATTR_POSITION = GLState.ATTR_POSITION;
    static final int ATTR_TEXCOORD = GLState.ATTR_TEXCOORD;
    static final int ATTR_COLOR = GLState.ATTR_COLOR;
    static final int ATTR_NORMAL = GLState.ATTR_NORMAL;

    // --- GL enum -> WebGL enum перевод (значения constant для тех случаев,
    // где GL11/WebGL расходятся; для большинства realistic-констант GL11
    // числа исторически совпадают с WebGL, т.к. WebGL унаследовал их из
    // OpenGL ES, но explicit перевод безопаснее и документирует намерение). ---

    static int translateDepthFunc(int glFunc) { return glFunc; } // GL_LEQUAL=515 и т.п. совпадают
    static int translateCullFace(int glFace) { return glFace; }  // GL_BACK=1029 совпадает
    static int translateBlendFactor(int f) { return f; }         // GL_SRC_ALPHA=770 и т.п. совпадают

    // WebGL2 понимает только чётко ограниченный набор capability-констант
    // для enable()/disable(): DEPTH_TEST, BLEND, CULL_FACE, STENCIL_TEST,
    // SCISSOR_TEST, DITHER, POLYGON_OFFSET_FILL, SAMPLE_ALPHA_TO_COVERAGE,
    // SAMPLE_COVERAGE, RASTERIZER_DISCARD. Оригинальный decompiled-код
    // (унаследованный от fixed-function OpenGL 1.1) местами включает/
    // выключает то, чего в WebGL просто нет — например GL_LIGHT0 (16384),
    // GL_LIGHT1 (16385), GL_COLOR_MATERIAL (2903), GL_RESCALE_NORMAL
    // (32826). Наш кастомный шейдерный пайплайн эти состояния не читает
    // (освещение считается через GLState.lightingEnabled самостоятельно),
    // так что раньше не хватало не результата, а самого факта: прямая
    // передача такой константы в gl.enable()/gl.disable() кидает
    // GL_INVALID_ENUM, WebGL выставляет "залипающий" флаг ошибки, и на
    // ближайшей проверке Minecraft.checkGLError() читает этот флаг и
    // делает this.H = false — БЕЗ исключения, без экрана краша, просто
    // тихо и навсегда останавливает игровой цикл (симптом выглядел как
    // "чёрный экран, ничего не рисуется"). ИСПРАВЛЕНО: default-ветка
    // теперь просто игнорирует неизвестные capability вместо передачи их
    // в WebGL.
    // OpenGL legacy enums used by the bridge. These are intentionally
    // declared here because GLBridge is not a subclass of GL11 and Java
    // does not implicitly import GL11's static constants.
    static final int GL_TEXTURE_2D = 3553;
    static final int GL_FOG = 2912;
    static final int GL_LIGHTING = 2896;
    static final int GL_ALPHA_TEST = 3008;
    static final int GL_DEPTH_TEST = 2929;
    static final int GL_BLEND = 3042;
    static final int GL_CULL_FACE = 2884;
    static final int ARRAY_BUFFER = 34962;
    static final int STREAM_DRAW = 35040;

    static final int GL_LIGHT0 = 16384;
    static final int GL_LIGHT1 = 16385;
    static final int GL_COLOR_MATERIAL = 2903;
    static final int GL_RESCALE_NORMAL = 32826;

    static void setCapability(GLState s, int cap, boolean on) {
        switch (cap) {
            case GL_TEXTURE_2D:
                // texturing включение/выключение обрабатывается через
                // uUseTexture uniform при draw call'е (см. drawArrays) —
                // само состояние держим на GLState.
                s.textureEnabled = on;
                return;
            case GL_FOG:
                s.fogEnabled = on;
                return;
            case GL_LIGHTING:
                s.lightingEnabled = on;
                return;
            case GL_ALPHA_TEST:
                s.alphaTestEnabled = on;
                return;
            case GL_DEPTH_TEST: {
                int want = on ? 1 : 2;
                if (s.stDepthTest != want) {
                    s.stDepthTest = want;
                    if (on) s.gl.enable(cap); else s.gl.disable(cap);
                }
                return;
            }
            case GL_BLEND: {
                int want = on ? 1 : 2;
                if (s.stBlend != want) {
                    s.stBlend = want;
                    if (on) s.gl.enable(cap); else s.gl.disable(cap);
                }
                return;
            }
            case GL_CULL_FACE: {
                int want = on ? 1 : 2;
                if (s.stCull != want) {
                    s.stCull = want;
                    if (on) s.gl.enable(cap); else s.gl.disable(cap);
                }
                return;
            }
            case GL_LIGHT0:
            case GL_LIGHT1:
            case GL_COLOR_MATERIAL:
            case GL_RESCALE_NORMAL:
                // Легаси fixed-function состояния без аналога в WebGL —
                // кастомный шейдер их не читает, просто игнорируем.
                return;
            default:
                // Неизвестная capability — не передаём в WebGL (см. выше,
                // почему это раньше тихо валило весь рендер-цикл).
                // Логируем один раз для отладки на случай, если это
                // окажется что-то реально важное.
                System.out.println("GLBridge: ignoring unknown GL capability " + cap);
        }
    }


    static void setDepthMask(GLState s, boolean flag) {
        int want = flag ? 1 : 2;
        if (s.stDepthMask == want) return;
        s.stDepthMask = want;
        s.gl.depthMask(flag);
    }

    static void setDepthFunc(GLState s, int func) {
        if (s.stDepthFunc == func) return;
        s.stDepthFunc = func;
        s.gl.depthFunc(translateDepthFunc(func));
    }

    static void setCullFace(GLState s, int mode) {
        if (s.stCullFace == mode) return;
        s.stCullFace = mode;
        s.gl.cullFace(translateCullFace(mode));
    }

    static void setBlendFunc(GLState s, int sf, int df) {
        if (s.stBlendSrc == sf && s.stBlendDst == df) return;
        s.stBlendSrc = sf;
        s.stBlendDst = df;
        s.gl.blendFunc(translateBlendFactor(sf), translateBlendFactor(df));
    }

    static void clear(GLState s, int mask) {
        s.gl.clear(mask);
    }

    static void setAlphaFunc(GLState s, int func, float ref) {
        s.alphaFunc = func;
        s.alphaRef = ref;
    }

    static void setColor(GLState s, float r, float g, float b, float a) {
        s.r = r; s.g = g; s.b = b; s.a = a;
    }

    static void setCurrentNormal(GLState s, float x, float y, float z) {
        s.nx = x; s.ny = y; s.nz = z;
    }

    /** GL_VERTEX_ARRAY=32884, GL_NORMAL_ARRAY=32885, GL_COLOR_ARRAY=32886, GL_TEXTURE_COORD_ARRAY=32888. */
    static int clientStateToAttr(int cap) {
        switch (cap) {
            case 32884: return ATTR_POSITION;
            case 32885: return ATTR_NORMAL;
            case 32886: return ATTR_COLOR;
            case 32888: return ATTR_TEXCOORD;
            default: return -1;
        }
    }

    // --- textures ---

    static int genTexture(GLState s) {
        WebGLTexture tex = s.gl.createTexture();
        int id = s.nextTextureId++;
        s.textures.put(id, tex);
        return id;
    }

    /** См. javadoc GLDispatch.genTextures — заполняет buffer от position() до limit(), НЕ меняя саму position(). */
    static void genTexturesInto(GLState s, java.nio.IntBuffer textures) {
        int pos = textures.position();
        int limit = textures.limit();
        for (int i = pos; i < limit; i++) {
            int id = genTexture(s);
            textures.put(i, id);
        }
    }

    static void bindTexture(GLState s, int id) {
        WebGLTexture tex = id == 0 ? null : s.textures.get(id);
        s.boundTexture = tex;
        s.boundTextureId = id;
        Boolean nearest = s.texNearest.get(id);
        s.curNearest = nearest == null || nearest.booleanValue();
        if (!s.glTexValid || s.glTex != tex) {
            s.gl.bindTexture(GL_TEXTURE_2D, tex);
            s.glTex = tex;
            s.glTexValid = true;
        }
    }

    /** glTexParameteri: передаём в GL и запоминаем запрошенный фильтр увеличения для шейдера. */
    static void texParameteri(GLState s, int target, int pname, int param) {
        s.gl.texParameteri(target, pname, param);
        if (pname == 10240 /* GL_TEXTURE_MAG_FILTER */) {
            boolean nearest = param == 9728 || param == 9984 || param == 9986;
            s.texNearest.put(s.boundTextureId, Boolean.valueOf(nearest));
            s.curNearest = nearest;
        }
    }

    static void deleteTexture(GLState s, int id) {
        s.texNearest.remove(id);
        WebGLTexture tex = s.textures.remove(id);
        if (tex != null) {
            s.gl.deleteTexture(tex);
            // GL сам отвязывает удаляемую текстуру — кэш больше не доверяем.
            if (s.glTex == tex) s.glTexValid = false;
        }
    }

    /** Удаляет текстуры с ID, перечисленными в буфере от position() до limit() (как реальный LWJGL glDeleteTextures(IntBuffer)). */
    static void deleteTexturesFrom(GLState s, java.nio.IntBuffer textures) {
        int pos = textures.position();
        int limit = textures.limit();
        for (int i = pos; i < limit; i++) {
            deleteTexture(s, textures.get(i));
        }
    }

    /**
     * ByteBuffer в decomp хранит пиксели как RGBA-байты, но порядок каналов
     * может отличаться от того, что ожидает WebGL (WebGL2 texImage2D с
     * форматом RGBA/UNSIGNED_BYTE ожидает R,G,B,A по возрастанию адреса —
     * то же самое, что кладёт оригинальный Minecraft-код). Прямое побайтовое
    /**
     * ByteBuffer в decomp хранит пиксели как RGBA-байты, но порядок каналов
     * может отличаться от того, что ожидает WebGL (WebGL2 texImage2D с
     * форматом RGBA/UNSIGNED_BYTE ожидает R,G,B,A по возрастанию адреса —
     * то же самое, что кладёт оригинальный Minecraft-код). Прямое побайтовое
     * копирование достаточно; endian-конвертации не нужны, т.к. это массив
     * байт, а не int[].
     *
     * Используем ПОДТВЕРЖДЁННЫЙ реальной ошибкой компиляции (первый прогон
     * CI) метод ArrayBufferView.set(byte[], int) — компилятор перечислил
     * его как существующую, но неподходящую по аргументам перегрузку для
     * прежнего кода (arr.set(i, int) — которого не существует, есть только
     * set(int, short) для одного элемента). Bulk-копирование byte[] через
     * set(byte[], int) — безопасный, подтверждённый путь без поэлементных
     * вызовов и без риска несовпадения типов.
     */
    static Uint8Array toUint8Array(ByteBuffer buffer, int expectedLength) {
        int pos = buffer.position();
        int len = Math.min(buffer.remaining(), expectedLength > 0 ? expectedLength : buffer.remaining());
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = buffer.get(pos + i);
        }
        Uint8Array arr = Uint8ArrayFactory.create(len);
        arr.set(bytes, 0);
        return arr;
    }

    static Uint8Array newUint8Array(int length) {
        return Uint8ArrayFactory.create(length);
    }

    static void copyToByteBuffer(Uint8Array src, ByteBuffer dest) {
        int pos = dest.position();
        int len = Math.min(src.getLength(), dest.remaining());
        for (int i = 0; i < len; i++) {
            dest.put(pos + i, (byte) src.get(i));
        }
    }

    // --- vertex arrays ---

    static void setClientArray(GLState s, int attr, int size, int stride, FloatBuffer buffer, int baseOffset) {
        GLState.AttrState a = s.attrs[attr];
        a.fromClient = true;
        a.clientBuffer = buffer;
        a.clientBaseOffset = baseOffset;
        a.size = size;
        a.stride = stride;
        a.isByte = false;
    }

    static void setClientArrayBytes(GLState s, int attr, int size, int stride, ByteBuffer buffer, int baseOffset) {
        GLState.AttrState a = s.attrs[attr];
        a.fromClient = true;
        a.clientBuffer = buffer;
        a.clientBaseOffset = baseOffset;
        a.size = size;
        a.stride = stride;
        a.isByte = true;
    }

    static void setBoundArray(GLState s, int attr, int size, int stride, long offset) {
        GLState.AttrState a = s.attrs[attr];
        a.fromClient = false;
        a.offset = offset;
        a.size = size;
        a.stride = stride;
    }

    /**
     * Перед draw call: если хотя бы один атрибут задан как client-side
     * Buffer, заливаем ВСЕ активные client-side атрибуты в единый scratch
     * VBO с интерливингом (как это и есть в decomp — один и тот же
     * VertexBuffer используется под vertex+texcoord+color+normal с общим
     * stride, просто с разными offset'ами внутри одной записи). Если все
     * атрибуты уже bound-offset форм (уже в VBO), просто их и используем.
     */
    static void drawArrays(GLState s, int mode, int first, int count) {
        boolean anyClient = false;
        for (GLState.AttrState a : s.attrs) {
            if (a.size > 0 && a.fromClient) { anyClient = true; break; }
        }

        // ВАЖНО: если данные пришли из client-side Java Buffer (anyClient),
        // stageClientBuffers() заливает их в НОВЫЙ scratch VBO, содержащий
        // ТОЛЬКО эти `count` вершин, начиная с локального индекса 0 —
        // исходный `first` был смещением внутри Java-буфера (уже учтено
        // через clientBaseOffset при чтении), а не индексом в GPU-буфере.
        // Поэтому после стейджинга рисовать нужно от 0, а не от `first`.
        // Если же атрибуты заданы как offset в уже забинженном VBO
        // (!anyClient), `first` остаётся индексом в РЕАЛЬНОМ GPU-буфере
        // (пользовательском, не scratch) — там `first` корректен как есть.
        int drawFirst = anyClient ? 0 : first;

        if (anyClient) {
            stageClientBuffers(s, first, count);
        }

        bindVertexAttribs(s);
        applyUniforms(s);

        int glMode = translateDrawMode(mode);
        if (mode == 7 /* GL_QUADS */) {
            // WebGL2 не поддерживает GL_QUADS. Четыре вершины квада заданы
            // по периметру (как того требует immediate-mode GL_QUADS), а
            // TRIANGLE_FAN с 4 вершинами v0,v1,v2,v3 рисует ровно то же
            // самое: треугольники (v0,v1,v2) и (v0,v2,v3) — корректный
            // способ отрисовать выпуклый квад без per-vertex дублирования.
            final int GL_TRIANGLE_FAN = 6;
            int quads = count / 4;
            for (int q = 0; q < quads; q++) {
                s.gl.drawArrays(GL_TRIANGLE_FAN, drawFirst + q * 4, 4);
            }
        } else {
            s.gl.drawArrays(glMode, drawFirst, count);
        }
    }

    private static int translateDrawMode(int glMode) {
        // GL_TRIANGLES=4, GL_TRIANGLE_STRIP=5, GL_TRIANGLE_FAN=6, GL_LINES=1,
        // GL_LINE_STRIP=3 — совпадают с WebGL. GL_QUADS=7 обрабатывается
        // отдельно перед вызовом этого метода.
        return glMode;
    }

    /**
     * Строит interleaved-снимок [pos3, uv2, color4, normal3] ПРЯМО СЕЙЧАС из
     * текущих client-side буферов атрибутов. Используется в двух случаях:
     *  1) немедленная отрисовка (stageClientBuffers ниже вызывает ровно ту
     *     же логику через readAttr*, см. её тело) — там buffer живой и это
     *     не имеет значения, т.к. draw идёт тут же;
     *  2) запись в display list (см. GLDispatch.drawArrays) — здесь как раз
     *     критично снять снимок СЕЙЧАС, а не откладывать чтение буфера на
     *     момент glCallList в будущем, когда общий буфер тессельятора уже
     *     будет содержать данные совсем другого рисунка.
     */
    static float[] snapshotClientBuffers(GLState s, int first, int count) {
        float[] interleaved = new float[count * 12];
        fillSnapshot(s, first, count, interleaved);
        return interleaved;
    }

    /** Заполняет первые count*12 float'ов массива out (см. snapshotClientBuffers). */
    static void fillSnapshot(GLState s, int first, int count, float[] interleaved) {
        final int FLOATS_PER_VERTEX = 12; // 3 pos + 2 uv + 4 color + 3 normal
        for (int i = 0; i < count; i++) {
            int vtx = first + i;
            int base = i * FLOATS_PER_VERTEX;

            readAttr(s.attrs[ATTR_POSITION], vtx, interleaved, base, 3, 1f, 1f, 1f, 0f);
            readAttr(s.attrs[ATTR_TEXCOORD], vtx, interleaved, base + 3, 2, 0f, 0f, 0f, 0f);
            readAttrColorDefault(s.attrs[ATTR_COLOR], vtx, interleaved, base + 5, s);
            readAttrNormalDefault(s.attrs[ATTR_NORMAL], vtx, interleaved, base + 9, s);
        }
    }

    // ------------------------------------------------------------------
    // PERF: запись display list'а с готовыми GPU-буферами.
    //
    // Раньше геометрия каждого чанка при КАЖДОМ glCallList (т.е. каждый
    // кадр, для каждого видимого чанка) заново заливалась в scratch VBO
    // через bufferData(new Float32Array(...)) + пересоздавались атрибуты
    // и все uniform'ы. Теперь снимок заливается в собственный STATIC VBO
    // ОДИН раз при записи, а при воспроизведении — bindVertexArray + draw.
    // Картинка идентична: те же вершины, тот же порядок, те же шейдеры.
    // GL_QUADS при записи разворачивается в треугольники (v0,v1,v2),(v0,v2,v3)
    // — ровно то, что давал прежний TRIANGLE_FAN из 4 вершин.
    // ------------------------------------------------------------------
    static final class DrawRecord implements Runnable, GLState.Disposable {
        private final GLState s;
        private final int mode;
        private final int vertexCount;
        private WebGLBuffer vbo;
        private WebGLVertexArrayObject vao;
        // ИСПРАВЛЕНИЕ "моб становится полностью белым при уроне": если на момент
        // записи массив цвета (или нормали) был выключен, вершины берут
        // ТЕКУЩИЙ glColor/glNormal — и в настоящем GL это значение читается при
        // ВОСПРОИЗВЕДЕНИИ list'а, а не запекается. Модели мобов (ka.java)
        // записываются в list один раз с белым glColor, а красный оверлей урона
        // (ec.java: glColor4f(f,0,0,0.4)) выставляется перед glCallList.
        // Раньше снимок запекал белый цвет → оверлей рисовался белым.
        // Теперь такие атрибуты в VAO выключены, а значение подставляется на replay.
        private final boolean colorFromState;
        private final boolean normalFromState;

        DrawRecord(GLState s, int mode, float[] snap, int count) {
            this.s = s;
            GLState.AttrState ca = s.attrs[ATTR_COLOR];
            GLState.AttrState na = s.attrs[ATTR_NORMAL];
            this.colorFromState = ca.size <= 0 || !ca.fromClient;
            this.normalFromState = na.size <= 0 || !na.fromClient;
            float[] data = snap;
            int n = count;
            int drawMode = mode;
            if (mode == 7 /* GL_QUADS */) {
                int quads = count / 4;
                n = quads * 6;
                data = new float[n * 12];
                int o = 0;
                for (int q = 0; q < quads; q++) {
                    int b = q * 4 * 12;
                    o = copyVertex(snap, b, data, o);
                    o = copyVertex(snap, b + 12, data, o);
                    o = copyVertex(snap, b + 24, data, o);
                    o = copyVertex(snap, b, data, o);
                    o = copyVertex(snap, b + 24, data, o);
                    o = copyVertex(snap, b + 36, data, o);
                }
                drawMode = 4; // GL_TRIANGLES
            }
            this.mode = drawMode;
            this.vertexCount = n;

            WebGL2 gl = s.gl;
            vbo = gl.createBuffer();
            gl.bindBuffer(ARRAY_BUFFER, vbo);
            gl.bufferData(ARRAY_BUFFER, Float32ArrayFactory.wrap(data), 35044 /* STATIC_DRAW */);

            vao = gl.createVertexArray();
            gl.bindVertexArray(vao);
            final int stride = 12 * 4;
            gl.enableVertexAttribArray(ATTR_POSITION);
            gl.vertexAttribPointer(ATTR_POSITION, 3, 5126, false, stride, 0);
            gl.enableVertexAttribArray(ATTR_TEXCOORD);
            gl.vertexAttribPointer(ATTR_TEXCOORD, 2, 5126, false, stride, 3 * 4);
            if (colorFromState) {
                gl.disableVertexAttribArray(ATTR_COLOR);
            } else {
                gl.enableVertexAttribArray(ATTR_COLOR);
                gl.vertexAttribPointer(ATTR_COLOR, 4, 5126, false, stride, 5 * 4);
            }
            if (normalFromState) {
                gl.disableVertexAttribArray(ATTR_NORMAL);
            } else {
                gl.enableVertexAttribArray(ATTR_NORMAL);
                gl.vertexAttribPointer(ATTR_NORMAL, 3, 5126, false, stride, 9 * 4);
            }
            gl.bindVertexArray(null);
            s.currentVao = null;
            // Вернуть привязку ARRAY_BUFFER, которую ожидает immediate-путь.
            gl.bindBuffer(ARRAY_BUFFER, s.boundArrayBuffer);
        }

        private static int copyVertex(float[] src, int from, float[] dst, int to) {
            for (int i = 0; i < 12; i++) dst[to + i] = src[from + i];
            return to + 12;
        }

        @Override
        public void run() {
            if (vao == null || vertexCount == 0) return;
            useVao(s, vao);
            if (colorFromState) s.gl.vertexAttrib4f(ATTR_COLOR, s.r, s.g, s.b, s.a);
            if (normalFromState) s.gl.vertexAttrib4f(ATTR_NORMAL, s.nx, s.ny, s.nz, 1f);
            applyUniforms(s);
            s.gl.drawArrays(mode, 0, vertexCount);
        }

        @Override
        public void dispose() {
            if (vao != null) {
                if (s.currentVao == vao) {
                    s.gl.bindVertexArray(null);
                    s.currentVao = null;
                }
                s.gl.deleteVertexArray(vao);
                vao = null;
            }
            if (vbo != null) {
                s.gl.deleteBuffer(vbo);
                vbo = null;
            }
        }
    }

    static Runnable createDrawRecord(GLState s, int mode, float[] snap, int count) {
        return new DrawRecord(s, mode, snap, count);
    }

    static void useVao(GLState s, WebGLVertexArrayObject vao) {
        if (s.currentVao != vao) {
            s.gl.bindVertexArray(vao);
            s.currentVao = vao;
        }
    }

    /** Заливает уже готовый (замороженный на момент записи list'а) снимок вершин в scratch VBO и рисует. */
    static void drawArraysFromSnapshot(GLState s, int mode, float[] interleaved, int count) {
        uploadScratch(s, interleaved);
        setStagedOffsetsForce(s);
        bindVertexAttribs(s);
        applyUniforms(s);

        int glMode = translateDrawMode(mode);
        if (mode == 7 /* GL_QUADS */) {
            final int GL_TRIANGLE_FAN = 6;
            int quads = count / 4;
            for (int q = 0; q < quads; q++) {
                s.gl.drawArrays(GL_TRIANGLE_FAN, q * 4, 4);
            }
        } else {
            s.gl.drawArrays(glMode, 0, count);
        }
    }

    private static void uploadScratch(GLState s, float[] interleaved) {
        if (s.scratchVbo == null) s.scratchVbo = s.gl.createBuffer();
        s.gl.bindBuffer(ARRAY_BUFFER, s.scratchVbo);
        Float32Array data = Float32ArrayFactory.wrap(interleaved);
        s.gl.bufferData(ARRAY_BUFFER, data, STREAM_DRAW);
        s.boundArrayBuffer = s.scratchVbo;
    }

    /** Собираем interleaved float-массив [pos3, uv2, color4, normal3] = 12 floats/vertex в scratch VBO. */
    private static void stageClientBuffers(GLState s, int first, int count) {
        int needed = count * 12;
        int k = 8;                       // тир: 2^k float'ов, минимум 256
        while ((1 << k) < needed) k++;
        int idx = k - 8;
        float[] tier = s.stageTier[idx];
        if (tier == null) {
            tier = new float[1 << k];
            s.stageTier[idx] = tier;
            s.stageTierJs[idx] = Float32ArrayFactory.wrap(tier);
        }
        fillSnapshot(s, first, count, tier);
        Float32Array js = s.stageTierJs[idx];
        js.set(tier, 0);
        if (s.scratchVbo == null) s.scratchVbo = s.gl.createBuffer();
        s.gl.bindBuffer(ARRAY_BUFFER, s.scratchVbo);
        GLRaw.bufferDataPrefix(s.gl, ARRAY_BUFFER, js, needed, STREAM_DRAW);
        s.boundArrayBuffer = s.scratchVbo;
        // После стейджинга все атрибуты читаются из scratch VBO по фиксированным офсетам.
        setStagedOffsets(s);
    }

    /**
     * ИСПРАВЛЕНО: обычный setStagedOffsets() пропускает атрибут, если его
     * текущий a.size <= 0 — это верно для НЕМЕДЛЕННОЙ отрисовки (там a.size
     * гарантированно отражает состояние ИМЕННО ЭТОГО draw call'а), но
     * ЛОМАЕТСЯ для воспроизведения display list'а: между записью чанка и
     * его реальным glCallList (много кадров спустя) успевает отрисоваться
     * куча НЕСВЯЗАННОГО кода (HUD, рука игрока, другие чанки), который
     * вызывает glDisableClientState для НЕ НУЖНЫХ ЕМУ атрибутов (например,
     * 2D-квад HUD использует только position+texcoord, отключая color/
     * normal) — а это НАПРЯМУЮ мутирует те же самые общие GLState.attrs[].
     * К моменту реального replay a.size для COLOR/NORMAL мог оказаться 0
     * от ЧУЖОГО, никак не связанного вызова — и обычный setStagedOffsets
     * молча пропускал стейджинг, оставляя bindOne() применять "текущий"
     * (chужой!) default-цвет через vertexAttrib4f(s.r,s.g,s.b,s.a). Если в
     * этот момент s.a случайно было < 0.004 (например, из-за недавнего
     * прозрачного оверлея), фрагментный шейдер (if (color.a<0.004) discard;)
     * выбрасывал АБСОЛЮТНО ВСЕ пиксели чанка — блоки существовали, текстура
     * была привязана верно, но ничего не рисовалось.
     *
     * Снимок (snapshot) уже содержит ПРАВИЛЬНЫЕ данные для всех 4 каналов
     * (реальные или вменяемые дефолты — см. readAttr) на момент записи —
     * поэтому здесь стейджим ВСЕ 4 канала безусловно, игнорируя то, что
     * сейчас случайно лежит в a.size от постороннего кода.
     */
    private static void setStagedOffsetsForce(GLState s) {
        int strideBytes = 12 * 4;
        forceStage(s.attrs[ATTR_POSITION], 3, 0, strideBytes);
        forceStage(s.attrs[ATTR_TEXCOORD], 2, 3 * 4, strideBytes);
        forceStage(s.attrs[ATTR_COLOR], 4, 5 * 4, strideBytes);
        forceStage(s.attrs[ATTR_NORMAL], 3, 9 * 4, strideBytes);
    }

    private static void forceStage(GLState.AttrState a, int size, int offset, int stride) {
        a.fromClient = false;
        a.offset = offset;
        a.stride = stride;
        a.size = size;
    }

    private static void setStagedOffsets(GLState s) {
        int strideBytes = 12 * 4;
        setStagedIfActive(s.attrs[ATTR_POSITION], 3, 0, strideBytes);
        setStagedIfActive(s.attrs[ATTR_TEXCOORD], 2, 3 * 4, strideBytes);
        setStagedIfActive(s.attrs[ATTR_COLOR], 4, 5 * 4, strideBytes);
        setStagedIfActive(s.attrs[ATTR_NORMAL], 3, 9 * 4, strideBytes);
    }

    private static void setStagedIfActive(GLState.AttrState a, int size, int offset, int stride) {
        if (a.size <= 0) return;
        a.fromClient = false;
        a.offset = offset;
        a.stride = stride;
        a.size = size;
    }

    private static void readAttr(GLState.AttrState a, int vtx, float[] out, int outBase, int n,
                                  float d0, float d1, float d2, float d3) {
        if (a.size <= 0) {
            out[outBase] = d0; if (n > 1) out[outBase + 1] = d1;
            if (n > 2) out[outBase + 2] = d2; if (n > 3) out[outBase + 3] = d3;
            return;
        }
        if (!a.fromClient) return; // offset-форма обрабатывается отдельным путём (не staging)
        FloatBuffer fb = (FloatBuffer) a.clientBuffer;
        int strideFloats = a.stride / 4;
        int idx = a.clientBaseOffset + vtx * strideFloats;
        for (int k = 0; k < n; k++) {
            out[outBase + k] = (k < a.size) ? fb.get(idx + k) : 0f;
        }
    }

    private static void readAttrColorDefault(GLState.AttrState a, int vtx, float[] out, int outBase, GLState s) {
        if (a.size <= 0 || !a.fromClient) {
            out[outBase] = s.r; out[outBase + 1] = s.g; out[outBase + 2] = s.b; out[outBase + 3] = s.a;
            return;
        }
        ByteBuffer bb = (ByteBuffer) a.clientBuffer;
        int idx = a.clientBaseOffset + vtx * a.stride;
        for (int k = 0; k < 4; k++) {
            out[outBase + k] = (k < a.size) ? ((bb.get(idx + k) & 0xFF) / 255f) : 1f;
        }
    }

    private static void readAttrNormalDefault(GLState.AttrState a, int vtx, float[] out, int outBase, GLState s) {
        if (a.size <= 0 || !a.fromClient) {
            out[outBase] = s.nx; out[outBase + 1] = s.ny; out[outBase + 2] = s.nz;
            return;
        }
        ByteBuffer bb = (ByteBuffer) a.clientBuffer;
        int idx = a.clientBaseOffset + vtx * a.stride;
        // GL_BYTE нормали в диапазоне [-128,127] представляют [-1,1]
        out[outBase] = bb.get(idx) / 127f;
        out[outBase + 1] = bb.get(idx + 1) / 127f;
        out[outBase + 2] = bb.get(idx + 2) / 127f;
    }

    private static void bindVertexAttribs(GLState s) {
        useVao(s, null);
        s.gl.bindBuffer(ARRAY_BUFFER, s.boundArrayBuffer);
        bindOne(s, ATTR_POSITION);
        bindOne(s, ATTR_TEXCOORD);
        bindOne(s, ATTR_COLOR);
        bindOne(s, ATTR_NORMAL);
    }

    private static void bindOne(GLState s, int attr) {
        GLState.AttrState a = s.attrs[attr];
        if (a.size <= 0) {
            s.gl.disableVertexAttribArray(attr);
            // In OpenGL's fixed-function client-array model, a disabled
            // COLOR_ARRAY falls back to the current glColor (white for the
            // GUI). WebGL's default disabled vertex attribute is (0,0,0,1),
            // which made textured GUI quads completely black. Explicitly
            // restore the fixed-function defaults for disabled attributes.
            if (attr == ATTR_COLOR) {
                s.gl.vertexAttrib4f(attr, s.r, s.g, s.b, s.a);
            } else if (attr == ATTR_TEXCOORD) {
                s.gl.vertexAttrib4f(attr, 0f, 0f, 0f, 1f);
            } else if (attr == ATTR_NORMAL) {
                s.gl.vertexAttrib4f(attr, s.nx, s.ny, s.nz, 1f);
            }
            return;
        }
        s.gl.enableVertexAttribArray(attr);
        int type = 5126; // GL_FLOAT — после стейджинга всё во float32
        s.gl.vertexAttribPointer(attr, a.size, type, false, a.stride, (int) a.offset);
    }

    private static boolean sameFloats(float[] a, float[] b, int n) {
        for (int i = 0; i < n; i++) if (a[i] != b[i]) return false;
        return true;
    }

    private static void uploadMatrix(GLState s, WebGLUniformLocation loc, float[] m) {
        if (s.matBuf == null) s.matBuf = Float32ArrayFactory.wrap(new float[16]);
        s.matBuf.set(m, 0);
        s.gl.uniformMatrix4fv(loc, false, s.matBuf);
    }

    /**
     * PERF: шлём в браузер только изменившиеся uniform'ы. Значения те же,
     * что и раньше (см. комментарии про uColorMult ниже) — просто не
     * повторяем то, что уже установлено у программы.
     */
    private static void applyUniforms(GLState s) {
        Shaders sh = s.shaders;
        if (!s.programBound) {
            s.gl.useProgram(sh.program);
            s.programBound = true;
        }
        if (!s.staticUniformsSet) {
            s.gl.activeTexture(33984 /* TEXTURE0 */);  // другие юниты не используются
            s.gl.uniform1i(sh.uTexture, 0);
            // ИСПРАВЛЕНО (блоки становились прозрачными в меню паузы): текущий
            // glColor уже попадает в шейдер через атрибут aColor (bindOne/
            // readAttrColorDefault подставляют s.r/g/b/a, когда массив цветов
            // выключен), а в fixed-function GL при включённом массиве цветов
            // glColor вообще не применяется. Раньше шейдер ещё и умножал на
            // uColorMult = текущий glColor: при проигрывании display list
            // чанков там лежал "хвост" от GUI (градиент паузы с alpha < 1), и
            // все блоки рисовались полупрозрачными. Множитель всегда единичный.
            s.gl.uniform4f(sh.uColorMult, 1f, 1f, 1f, 1f);
            s.staticUniformsSet = true;
        }

        if (!s.cProjValid || !sameFloats(s.cProj, s.projection, 16)) {
            uploadMatrix(s, sh.uProjection, s.projection);
            System.arraycopy(s.projection, 0, s.cProj, 0, 16);
            s.cProjValid = true;
        }
        if (!s.cMvValid || !sameFloats(s.cMv, s.modelview, 16)) {
            uploadMatrix(s, sh.uModelview, s.modelview);
            System.arraycopy(s.modelview, 0, s.cMv, 0, 16);
            s.cMvValid = true;
        }

        // Текстура уже привязана в bindTexture() (с кэшем) — повторный
        // bindTexture на каждый draw не нужен.
        int useTexture = (s.textureEnabled && s.boundTexture != null) ? 1 : 0;
        if (s.cUseTex != useTexture) {
            s.gl.uniform1i(sh.uUseTexture, useTexture);
            s.cUseTex = useTexture;
        }

        int nearest = s.curNearest ? 1 : 0;
        if (s.cNearest != nearest) {
            s.gl.uniform1i(sh.uNearest, nearest);
            s.cNearest = nearest;
        }
        // glAlphaFunc(GREATER|GEQUAL, ref) при включённом GL_ALPHA_TEST
        int alphaFunc = !s.alphaTestEnabled ? 0 : (s.alphaFunc == 516 ? 1 : (s.alphaFunc == 518 ? 2 : 0));
        if (s.cAlphaFunc != alphaFunc) {
            s.gl.uniform1i(sh.uAlphaFunc, alphaFunc);
            s.cAlphaFunc = alphaFunc;
        }
        if (s.cAlphaRef != s.alphaRef) {
            s.gl.uniform1f(sh.uAlphaRef, s.alphaRef);
            s.cAlphaRef = s.alphaRef;
        }

        int useLighting = s.lightingEnabled ? 1 : 0;
        if (s.cUseLight != useLighting) {
            s.gl.uniform1i(sh.uUseLighting, useLighting);
            s.cUseLight = useLighting;
        }
        float[] L = s.tmpLight;
        L[0] = s.lightPosition[0][0]; L[1] = s.lightPosition[0][1]; L[2] = s.lightPosition[0][2];
        L[3] = s.lightPosition[1][0]; L[4] = s.lightPosition[1][1]; L[5] = s.lightPosition[1][2];
        L[6] = s.lightDiffuse[0][0]; L[7] = s.lightDiffuse[0][1]; L[8] = s.lightDiffuse[0][2];
        L[9] = s.lightDiffuse[1][0]; L[10] = s.lightDiffuse[1][1]; L[11] = s.lightDiffuse[1][2];
        L[12] = s.lightModelAmbient[0]; L[13] = s.lightModelAmbient[1]; L[14] = s.lightModelAmbient[2];
        if (!s.cLightValid || !sameFloats(s.cLight, L, 15)) {
            s.gl.uniform3f(sh.uLightDir0, L[0], L[1], L[2]);
            s.gl.uniform3f(sh.uLightDir1, L[3], L[4], L[5]);
            s.gl.uniform3f(sh.uLightColor0, L[6], L[7], L[8]);
            s.gl.uniform3f(sh.uLightColor1, L[9], L[10], L[11]);
            s.gl.uniform3f(sh.uAmbient, L[12], L[13], L[14]);
            System.arraycopy(L, 0, s.cLight, 0, 15);
            s.cLightValid = true;
        }

        int useFog = s.fogEnabled ? 1 : 0;
        if (s.cUseFog != useFog) {
            s.gl.uniform1i(sh.uUseFog, useFog);
            s.cUseFog = useFog;
        }
        int fogModeIdx = s.fogMode == 2049 ? 1 : (s.fogMode == 9729 ? 2 : 0);
        float[] F = s.tmpFog;
        F[0] = fogModeIdx; F[1] = s.fogDensity; F[2] = s.fogStart; F[3] = s.fogEnd;
        F[4] = s.fogColor[0]; F[5] = s.fogColor[1]; F[6] = s.fogColor[2]; F[7] = s.fogColor[3];
        if (!s.cFogValid || !sameFloats(s.cFog, F, 8)) {
            s.gl.uniform1i(sh.uFogMode, fogModeIdx);
            s.gl.uniform1f(sh.uFogDensity, s.fogDensity);
            s.gl.uniform1f(sh.uFogStart, s.fogStart);
            s.gl.uniform1f(sh.uFogEnd, s.fogEnd);
            s.gl.uniform4f(sh.uFogColor, F[4], F[5], F[6], F[7]);
            System.arraycopy(F, 0, s.cFog, 0, 8);
            s.cFogValid = true;
        }
    }

    // --- lighting / fog setters ---

    static void setLight(GLState s, int light, int pname, float[] v) {
        int idx = light == 16384 ? 0 : 1; // GL_LIGHT0/GL_LIGHT1
        switch (pname) {
            case 4611: // GL_POSITION -> используем как directional (w игнорируем, decomp всегда шлёт w=0)
                s.lightPosition[idx][0] = v[0]; s.lightPosition[idx][1] = v[1]; s.lightPosition[idx][2] = v[2];
                break;
            case 4609: // GL_DIFFUSE
                s.lightDiffuse[idx][0] = v[0]; s.lightDiffuse[idx][1] = v[1]; s.lightDiffuse[idx][2] = v[2];
                break;
            default:
                // GL_AMBIENT(4608)/GL_SPECULAR(4610) для двух ламп не используются
                // отдельно в универсальном шейдере (упрощённая модель) — игнорируем.
        }
    }

    static void setLightModel(GLState s, int pname, float[] v) {
        if (pname == 2899) { // GL_LIGHT_MODEL_AMBIENT
            s.lightModelAmbient[0] = v[0]; s.lightModelAmbient[1] = v[1]; s.lightModelAmbient[2] = v[2];
        }
    }

    static void setFogv(GLState s, int pname, float[] v) {
        if (pname == 2918) { // GL_FOG_COLOR
            s.fogColor[0] = v[0]; s.fogColor[1] = v[1]; s.fogColor[2] = v[2]; s.fogColor[3] = v[3];
        }
    }

    static void setFogf(GLState s, int pname, float param) {
        switch (pname) {
            case 2914: s.fogDensity = param; break; // GL_FOG_DENSITY
            case 2915: s.fogStart = param; break;    // GL_FOG_START
            case 2916: s.fogEnd = param; break;      // GL_FOG_END
        }
    }

    static void setFogi(GLState s, int pname, int param) {
        if (pname == 2917) s.fogMode = param; // GL_FOG_MODE
    }

    /**
     * Читает до `count` float из буфера НАЧИНАЯ С ТЕКУЩЕЙ position(), не
     * трогая саму позицию буфера (glLight/glFog копируют значения сразу,
     * не хранят ссылку на буфер — эта функция должна вызываться СИНХРОННО
     * в момент самого GL-вызова, а не отложенно в maybeRecord-лямбде,
     * иначе к моменту чтения содержимое буфера может уже отличаться).
     * Если в буфере осталось меньше `count` элементов, недостающие
     * заполняются нулями.
     */
    static float[] readFloats(FloatBuffer buf, int count) {
        float[] out = new float[count];
        int pos = buf.position();
        int available = Math.min(count, buf.remaining());
        for (int i = 0; i < available; i++) {
            out[i] = buf.get(pos + i);
        }
        return out;
    }

    // --- queries ---

    static String getString(GLState s, int name) {
        switch (name) {
            case 7936: return "TeaVM-WebGL2 (Minecraft Alpha web port)"; // GL_VENDOR
            case 7937: return "GLState";                                  // GL_RENDERER
            case 7938: return "2.0 WebGL2 emulation";                     // GL_VERSION
            default: return "";
        }
    }

    static void getFloatv(GLState s, int pname, FloatBuffer out) {
        // GL_MODELVIEW_MATRIX=2982, GL_PROJECTION_MATRIX=2983 — используются
        // decomp-ом (см. grep) для собственных вычислений (picking/fog).
        float[] src = pname == 2982 ? s.modelview : (pname == 2983 ? s.projection : null);
        if (src == null) return;
        int pos = out.position();
        for (int i = 0; i < 16; i++) out.put(pos + i, src[i]);
    }
}
