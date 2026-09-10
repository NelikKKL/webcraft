package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Вызовы WebGL2, у которых в спецификации несколько JS-перегрузок или нужна
 * специфическая передача типов (typed arrays с offset/length), которые
 * неудобно/ненадёжно выразить через простой @JSMethod-интерфейс. Каждый
 * метод здесь явно выбирает нужную перегрузку внутри JS-тела.
 */
public final class GLRaw {

    private GLRaw() {}

    // --- getParameter возвращает то int, то String, то float[] в зависимости
    //     от pname — заводим отдельный метод под каждый нужный нам случай
    //     (см. GL11.glGetString / glGetFloat в decomp-исходниках). ---

    @JSBody(params = { "gl", "pname" }, script =
        "return gl.getParameter(pname) || '';")
    public static native String getParameterString(WebGL2 gl, int pname);

    @JSBody(params = { "gl", "pname" }, script =
        "var v = gl.getParameter(pname);" +
        "if (v == null) return 0;" +
        "if (v.length !== undefined) return v[0];" + // Float32Array-подобные
        "return v;")
    public static native float getParameterFloat(WebGL2 gl, int pname);

    // --- Компиляция шейдера: статус + лог одним вызовом, чтобы не гонять
    //     туда-сюда лишние JS-обращения. ---

    @JSBody(params = { "gl", "shader" }, script =
        "return gl.getShaderParameter(shader, gl.COMPILE_STATUS);")
    public static native boolean getShaderCompileStatus(WebGL2 gl, WebGLShader shader);

    @JSBody(params = { "gl", "shader" }, script =
        "return gl.getShaderInfoLog(shader) || '';")
    public static native String getShaderInfoLog(WebGL2 gl, WebGLShader shader);

    @JSBody(params = { "gl", "program" }, script =
        "return gl.getProgramParameter(program, gl.LINK_STATUS);")
    public static native boolean getProgramLinkStatus(WebGL2 gl, WebGLProgram program);

    @JSBody(params = { "gl", "program" }, script =
        "return gl.getProgramInfoLog(program) || '';")
    public static native String getProgramInfoLog(WebGL2 gl, WebGLProgram program);

    // --- Загрузка текстуры из RGBA8 байтов (сконвертированных из Java int[]
    //     ARGB в GLState.uploadTexture). Всегда используем формат
    //     RGBA/UNSIGNED_BYTE — этого достаточно для всех вызовов glTexImage2D
    //     / glTexSubImage2D, встречающихся в decomp. ---

    @JSBody(params = { "gl", "target", "level", "width", "height", "pixels" }, script =
        "gl.texImage2D(target, level, gl.RGBA8, width, height, 0, gl.RGBA, gl.UNSIGNED_BYTE, pixels);")
    public static native void texImage2DRGBA(WebGL2 gl, int target, int level,
                                              int width, int height, Uint8Array pixels);

    @JSBody(params = { "gl", "target", "level", "xoffset", "yoffset", "width", "height", "pixels" }, script =
        "gl.texSubImage2D(target, level, xoffset, yoffset, width, height, gl.RGBA, gl.UNSIGNED_BYTE, pixels);")
    public static native void texSubImage2DRGBA(WebGL2 gl, int target, int level,
                                                 int xoffset, int yoffset,
                                                 int width, int height, Uint8Array pixels);

    @JSBody(params = { "gl", "x", "y", "width", "height", "pixels" }, script =
        "gl.readPixels(x, y, width, height, gl.RGBA, gl.UNSIGNED_BYTE, pixels);")
    public static native void readPixelsRGBA(WebGL2 gl, int x, int y, int width, int height, Uint8Array pixels);

    // bufferData с заранее известным размером (только резервирование памяти,
    // без данных) — используется для стриминга динамических VBO кадр за кадром.
    @JSBody(params = { "gl", "target", "size", "usage" }, script =
        "gl.bufferData(target, size, usage);")
    public static native void bufferDataCapacity(WebGL2 gl, int target, int size, int usage);

    // fog принимает то float, то массив (glFogfv для GL_FOG_COLOR) —
    // в decomp используются только glFogf/glFogi с одиночным значением,
    // цвет тумана эмулируем отдельным полем в GLState (см. GL11.glFog(int,FloatBuffer)).
}
