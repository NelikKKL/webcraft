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

    static final int GL_TEXTURE_2D = 3553;
    static final int GL_DEPTH_TEST = 2929;
    static final int GL_BLEND = 3042;
    static final int GL_CULL_FACE = 2884;
    static final int GL_FOG = 2912;
    static final int GL_LIGHTING = 2896;
    static final int GL_ALPHA_TEST = 3008;
    static final int ARRAY_BUFFER = 34962;
    static final int STATIC_DRAW = 35044;
    static final int STREAM_DRAW = 35040;

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
            case GL_DEPTH_TEST:
                if (on) s.gl.enable(cap); else s.gl.disable(cap);
                return;
            case GL_BLEND:
                if (on) s.gl.enable(cap); else s.gl.disable(cap);
                return;
            case GL_CULL_FACE:
                if (on) s.gl.enable(cap); else s.gl.disable(cap);
                return;
            default:
                if (on) s.gl.enable(cap); else s.gl.disable(cap);
        }
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
        s.gl.bindTexture(GL_TEXTURE_2D, tex);
    }

    static void deleteTexture(GLState s, int id) {
        WebGLTexture tex = s.textures.remove(id);
        if (tex != null) s.gl.deleteTexture(tex);
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

    /** Собираем interleaved float-массив [pos3, uv2, color4, normal3] = 12 floats/vertex в scratch VBO. */
    private static void stageClientBuffers(GLState s, int first, int count) {
        final int FLOATS_PER_VERTEX = 12; // 3 pos + 2 uv + 4 color + 3 normal
        float[] interleaved = new float[count * FLOATS_PER_VERTEX];

        for (int i = 0; i < count; i++) {
            int vtx = first + i;
            int base = i * FLOATS_PER_VERTEX;

            readAttr(s.attrs[ATTR_POSITION], vtx, interleaved, base, 3, 1f, 1f, 1f, 0f);
            readAttr(s.attrs[ATTR_TEXCOORD], vtx, interleaved, base + 3, 2, 0f, 0f, 0f, 0f);
            readAttrColorDefault(s.attrs[ATTR_COLOR], vtx, interleaved, base + 5, s);
            readAttrNormalDefault(s.attrs[ATTR_NORMAL], vtx, interleaved, base + 9, s);
        }

        if (s.scratchVbo == null) s.scratchVbo = s.gl.createBuffer();
        s.gl.bindBuffer(ARRAY_BUFFER, s.scratchVbo);
        Float32Array data = Float32ArrayFactory.wrap(interleaved);
        s.gl.bufferData(ARRAY_BUFFER, data, STREAM_DRAW);
        s.boundArrayBuffer = s.scratchVbo;

        // После стейджинга все атрибуты читаются из scratch VBO по фиксированным офсетам.
        setStagedOffsets(s);
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
            return;
        }
        s.gl.enableVertexAttribArray(attr);
        int type = 5126; // GL_FLOAT — после стейджинга всё во float32
        s.gl.vertexAttribPointer(attr, a.size, type, false, a.stride, (int) a.offset);
    }

    private static void applyUniforms(GLState s) {
        Shaders sh = s.shaders;
        s.gl.useProgram(sh.program);

        Float32Array proj = Float32ArrayFactory.wrap(s.projection);
        Float32Array mv = Float32ArrayFactory.wrap(s.modelview);
        s.gl.uniformMatrix4fv(sh.uProjection, false, proj);
        s.gl.uniformMatrix4fv(sh.uModelview, false, mv);

        boolean useTexture = s.textureEnabled && s.boundTexture != null;
        s.gl.uniform1i(sh.uUseTexture, useTexture ? 1 : 0);
        if (useTexture) {
            s.gl.activeTexture(33984 /* TEXTURE0 */);
            s.gl.bindTexture(GL_TEXTURE_2D, s.boundTexture);
            s.gl.uniform1i(sh.uTexture, 0);
        }

        s.gl.uniform1i(sh.uUseLighting, s.lightingEnabled ? 1 : 0);
        s.gl.uniform3f(sh.uLightDir0, s.lightPosition[0][0], s.lightPosition[0][1], s.lightPosition[0][2]);
        s.gl.uniform3f(sh.uLightDir1, s.lightPosition[1][0], s.lightPosition[1][1], s.lightPosition[1][2]);
        s.gl.uniform3f(sh.uLightColor0, s.lightDiffuse[0][0], s.lightDiffuse[0][1], s.lightDiffuse[0][2]);
        s.gl.uniform3f(sh.uLightColor1, s.lightDiffuse[1][0], s.lightDiffuse[1][1], s.lightDiffuse[1][2]);
        s.gl.uniform3f(sh.uAmbient, s.lightModelAmbient[0], s.lightModelAmbient[1], s.lightModelAmbient[2]);

        s.gl.uniform1i(sh.uUseFog, s.fogEnabled ? 1 : 0);
        int fogModeIdx = s.fogMode == 2049 ? 1 : (s.fogMode == 9729 ? 2 : 0);
        s.gl.uniform1i(sh.uFogMode, fogModeIdx);
        s.gl.uniform1f(sh.uFogDensity, s.fogDensity);
        s.gl.uniform1f(sh.uFogStart, s.fogStart);
        s.gl.uniform1f(sh.uFogEnd, s.fogEnd);
        s.gl.uniform4f(sh.uFogColor, s.fogColor[0], s.fogColor[1], s.fogColor[2], s.fogColor[3]);

        s.gl.uniform4f(sh.uColorMult, s.r, s.g, s.b, s.a);
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
