package net.minecraft.client.web;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSMethod;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Typed-интерфейс к WebGL2RenderingContext — только подмножество, реально
 * нужное для GL11-шима (см. grep всех GL11.* в decomp-исходниках).
 *
 * ВАЖНО про сигнатуры: WebGL-методы с несколькими JS-перегрузками
 * (getParameter, getShaderParameter, texImage2D, readPixels) не заводим
 * через этот интерфейс, а выносим в GLRaw (@JSBody) — там имя JS-функции
 * не привязано жёстко к Java-сигнатуре и можно явно выбрать перегрузку.
 *
 * WebGL2 — programmable pipeline. У него нет display lists, матричного
 * стека, fixed-function освещения/тумана и client-side vertex arrays
 * времён GL 1.1 — всё это эмулируется в Java (см. GLState) поверх этого
 * набора примитивов.
 */
public interface WebGL2 extends JSObject {

    // --- Shader / program ---
    @JSMethod WebGLShader createShader(int type);
    @JSMethod void shaderSource(WebGLShader shader, String source);
    @JSMethod void compileShader(WebGLShader shader);
    @JSMethod void deleteShader(WebGLShader shader);

    @JSMethod WebGLProgram createProgram();
    @JSMethod void attachShader(WebGLProgram program, WebGLShader shader);
    @JSMethod void linkProgram(WebGLProgram program);
    @JSMethod void useProgram(WebGLProgram program);
    @JSMethod void deleteProgram(WebGLProgram program);

    @JSMethod int getAttribLocation(WebGLProgram program, String name);
    @JSMethod WebGLUniformLocation getUniformLocation(WebGLProgram program, String name);
    @JSMethod void uniform1i(WebGLUniformLocation loc, int v);
    @JSMethod void uniform1f(WebGLUniformLocation loc, float v);
    @JSMethod void uniform3f(WebGLUniformLocation loc, float a, float b, float c);
    @JSMethod void uniform4f(WebGLUniformLocation loc, float a, float b, float c, float d);
    @JSMethod void uniformMatrix4fv(WebGLUniformLocation loc, boolean transpose, Float32Array value);

    // --- Buffers ---
    @JSMethod WebGLBuffer createBuffer();
    @JSMethod void bindBuffer(int target, WebGLBuffer buffer);
    @JSMethod void bufferData(int target, Float32Array data, int usage);
    @JSMethod void bufferSubData(int target, int offset, Float32Array data);
    @JSMethod void deleteBuffer(WebGLBuffer buffer);

    // --- Vertex Array Objects (WebGL2 native — заменяет client-side arrays GL1.1) ---
    @JSMethod WebGLVertexArrayObject createVertexArray();
    @JSMethod void bindVertexArray(WebGLVertexArrayObject vao);
    @JSMethod void enableVertexAttribArray(int index);
    @JSMethod void disableVertexAttribArray(int index);
    @JSMethod void vertexAttrib4f(int index, float x, float y, float z, float w);
    @JSMethod void vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset);

    // --- Textures ---
    @JSMethod WebGLTexture createTexture();
    @JSMethod void bindTexture(int target, WebGLTexture texture);
    @JSMethod void deleteTexture(WebGLTexture texture);
    @JSMethod void activeTexture(int unit);
    @JSMethod void texParameteri(int target, int pname, int param);
    @JSMethod void pixelStorei(int pname, int param);
    @JSMethod void generateMipmap(int target);

    // --- Drawing / state ---
    @JSMethod void viewport(int x, int y, int w, int h);
    @JSMethod void clearColor(float r, float g, float b, float a);
    @JSMethod void clearDepth(float d);
    @JSMethod void clear(int mask);
    @JSMethod void enable(int cap);
    @JSMethod void disable(int cap);
    @JSMethod void depthFunc(int func);
    @JSMethod void depthMask(boolean flag);
    @JSMethod void colorMask(boolean r, boolean g, boolean b, boolean a);
    @JSMethod void cullFace(int mode);
    @JSMethod void blendFunc(int sfactor, int dfactor);
    @JSMethod void lineWidth(float w);
    @JSMethod void polygonOffset(float factor, float units);
    @JSMethod void drawArrays(int mode, int first, int count);
    @JSMethod void drawElements(int mode, int count, int type, int offset);
    @JSMethod int getError();
    @JSMethod void finish();
}
