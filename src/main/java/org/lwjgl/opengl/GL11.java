package org.lwjgl.opengl;

import net.minecraft.client.web.*;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint8Array;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Шим org.lwjgl.opengl.GL11 — сигнатуры методов 1:1 повторяют реальный
 * LWJGL 2.x, чтобы decomp-код Minecraft компилировался без изменений
 * (за пределами замены import'ов на этот пакет). Вся логика — в GLState.
 *
 * Константы взяты из реального GL11 (совпадают с классическими OpenGL
 * enum-значениями) — decomp использует их как integer-литералы напрямую
 * (см. grep: 2918=GL_FOG_COLOR и т.п.), но здесь определены и именованные
 * константы на случай, если где-то встречается символьная форма.
 */
public final class GL11 {
    private GL11() {}

    // --- Константы (полный список не нужен — только реально используемые) ---
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_QUADS = 7; // WebGL не умеет QUADS — эмулируется в GLState.drawArrays
    public static final int GL_MODELVIEW = 5888;
    public static final int GL_PROJECTION = 5889;
    public static final int GL_FLOAT = 5126;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_BYTE = 5120;
    public static final int GL_RGBA = 6408;
    public static final int GL_LIGHT0 = 16384;
    public static final int GL_LIGHT1 = 16385;
    public static final int GL_FOG_MODE = 2917;
    public static final int GL_FOG_DENSITY = 2914;
    public static final int GL_FOG_START = 2915;
    public static final int GL_FOG_END = 2916;
    public static final int GL_FOG_COLOR = 2918;
    public static final int GL_EXP = 2048;
    public static final int GL_EXP2 = 2049;
    public static final int GL_LINEAR = 9729;

    private static GLState s() { return GLState.INSTANCE; }

    // ---------------------------------------------------------------
    // Матрицы
    // ---------------------------------------------------------------
    public static void glMatrixMode(int mode) { s().matrixMode(mode); }
    public static void glLoadIdentity() { s().loadIdentity(); }
    public static void glPushMatrix() { s().pushMatrix(); }
    public static void glPopMatrix() { s().popMatrix(); }
    public static void glTranslatef(float x, float y, float z) { s().translatef(x, y, z); }
    public static void glRotatef(float angle, float x, float y, float z) { s().rotatef(angle, x, y, z); }
    public static void glScalef(float x, float y, float z) { s().scalef(x, y, z); }
    public static void glOrtho(double l, double r, double b, double t, double n, double f) { s().ortho(l, r, b, t, n, f); }

    // ---------------------------------------------------------------
    // Display lists
    // ---------------------------------------------------------------
    public static int glGenLists(int range) { return s().genLists(range); }
    public static void glNewList(int list, int mode) { s().newList(list, mode); }
    public static void glEndList() { s().endList(); }
    public static void glCallList(int list) { s().callList(list); }
    public static void glCallLists(IntBuffer lists) {
        int pos = lists.position();
        while (lists.hasRemaining()) s().callList(lists.get());
        lists.position(pos);
    }
    public static void glDeleteLists(int list, int range) { s().deleteLists(list, range); }

    // ---------------------------------------------------------------
    // Простое состояние
    // ---------------------------------------------------------------
    public static void glEnable(int cap) { GLDispatch.enable(cap, true); }
    public static void glDisable(int cap) { GLDispatch.enable(cap, false); }
    // GL_VERTEX_ARRAY=32884, GL_NORMAL_ARRAY=32885, GL_COLOR_ARRAY=32886, GL_TEXTURE_COORD_ARRAY=32888.
    // decomp честно включает/выключает клиентские массивы вокруг каждого
    // glDrawArrays (см. is.java) — glDisableClientState обязан реально
    // погасить атрибут (size=0), иначе состояние от предыдущего меша
    // "утечёт" в следующий draw call, где этот атрибут не был переустановлен.
    public static void glEnableClientState(int cap) { /* сами данные приходят через *Pointer(...) — здесь нечего включать */ }
    public static void glDisableClientState(int cap) { GLDispatch.disableClientState(cap); }
    public static void glClear(int mask) { GLDispatch.clear(mask); }
    public static void glClearColor(float r, float g, float b, float a) { GLDispatch.clearColor(r, g, b, a); }
    public static void glClearDepth(double depth) { GLDispatch.clearDepth((float) depth); }
    public static void glColorMask(boolean r, boolean g, boolean b, boolean a) { GLDispatch.colorMask(r, g, b, a); }
    public static void glDepthFunc(int func) { GLDispatch.depthFunc(func); }
    public static void glDepthMask(boolean flag) { GLDispatch.depthMask(flag); }
    public static void glCullFace(int mode) { GLDispatch.cullFace(mode); }
    public static void glBlendFunc(int sfactor, int dfactor) { GLDispatch.blendFunc(sfactor, dfactor); }
    public static void glLineWidth(float width) { GLDispatch.lineWidth(width); }
    public static void glPolygonOffset(float factor, float units) { GLDispatch.polygonOffset(factor, units); }
    public static void glAlphaFunc(int func, float ref) { /* эмулируется через discard в шейдере по alpha порогу; см. GLDispatch */ GLDispatch.alphaFunc(func, ref); }
    public static void glShadeModel(int mode) { /* smooth shading — уже поведение по умолчанию у нашего шейдера */ }
    public static void glColorMaterial(int face, int mode) { /* fixed-function material tracking — не требуется: цвет уже идёт per-vertex */ }
    public static void glViewport(int x, int y, int w, int h) { GLDispatch.viewport(x, y, w, h); }
    public static void glColor3f(float r, float g, float b) { GLDispatch.color(r, g, b, 1f); }
    public static void glColor4f(float r, float g, float b, float a) { GLDispatch.color(r, g, b, a); }
    public static void glNormal3f(float x, float y, float z) { GLDispatch.normal(x, y, z); }

    // ---------------------------------------------------------------
    // Текстуры
    // ---------------------------------------------------------------
    public static int glGenTextures() { return GLDispatch.genTexture(); }
    public static void glGenTextures(IntBuffer textures) { GLDispatch.genTextures(textures); }
    public static void glBindTexture(int target, int texture) { GLDispatch.bindTexture(target, texture); }
    public static void glDeleteTextures(int texture) { GLDispatch.deleteTexture(texture); }
    public static void glDeleteTextures(IntBuffer textures) { GLDispatch.deleteTextures(textures); }
    public static void glTexParameteri(int target, int pname, int param) { GLDispatch.texParameteri(target, pname, param); }
    public static void glPixelStorei(int pname, int param) { GLDispatch.pixelStorei(pname, param); }
    public static void glTexImage2D(int target, int level, int internalFormat, int w, int h, int border, int format, int type, ByteBuffer pixels) {
        GLDispatch.texImage2D(target, level, w, h, pixels);
    }
    public static void glTexSubImage2D(int target, int level, int xoff, int yoff, int w, int h, int format, int type, ByteBuffer pixels) {
        GLDispatch.texSubImage2D(target, level, xoff, yoff, w, h, pixels);
    }

    // ---------------------------------------------------------------
    // Vertex arrays (client-side И VBO-offset формы)
    // ---------------------------------------------------------------
    public static void glVertexPointer(int size, int stride, FloatBuffer buffer) { GLDispatch.vertexPointerClient(size, stride, buffer); }
    public static void glVertexPointer(int size, int type, int stride, long offset) { GLDispatch.vertexPointerOffset(size, type, stride, offset); }
    public static void glTexCoordPointer(int size, int stride, FloatBuffer buffer) { GLDispatch.texCoordPointerClient(size, stride, buffer); }
    public static void glTexCoordPointer(int size, int type, int stride, long offset) { GLDispatch.texCoordPointerOffset(size, type, stride, offset); }
    public static void glColorPointer(int size, boolean unsigned, int stride, ByteBuffer buffer) { GLDispatch.colorPointerClient(size, stride, buffer); }
    public static void glColorPointer(int size, int type, int stride, long offset) { GLDispatch.colorPointerOffset(size, type, stride, offset); }
    public static void glNormalPointer(int stride, ByteBuffer buffer) { GLDispatch.normalPointerClient(stride, buffer); }
    public static void glNormalPointer(int type, int stride, long offset) { GLDispatch.normalPointerOffset(type, stride, offset); }
    public static void glDrawArrays(int mode, int first, int count) { GLDispatch.drawArrays(mode, first, count); }

    // ---------------------------------------------------------------
    // Освещение / туман — параметры копятся в GLState, применяются шейдером
    // ---------------------------------------------------------------
    public static void glLight(int light, int pname, FloatBuffer params) { GLDispatch.light(light, pname, params); }
    public static void glLightModel(int pname, FloatBuffer params) { GLDispatch.lightModel(pname, params); }
    public static void glFog(int pname, FloatBuffer params) { GLDispatch.fog(pname, params); }
    public static void glFogf(int pname, float param) { GLDispatch.fogf(pname, param); }
    public static void glFogi(int pname, int param) { GLDispatch.fogi(pname, param); }

    // ---------------------------------------------------------------
    // Разное
    // ---------------------------------------------------------------
    public static int glGetError() { return GLDispatch.getError(); }
    public static String glGetString(int name) { return GLDispatch.getString(name); }
    public static void glGetFloat(int pname, FloatBuffer out) { GLDispatch.getFloat(pname, out); }
    public static void glReadPixels(int x, int y, int w, int h, int format, int type, ByteBuffer pixels) {
        GLDispatch.readPixels(x, y, w, h, pixels);
    }
}
