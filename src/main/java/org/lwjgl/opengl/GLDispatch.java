package org.lwjgl.opengl;

import net.minecraft.client.web.*;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint8Array;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Все GL-операции проходят через maybeRecord(): если сейчас идёт запись
 * display list (glNewList был вызван, glEndList ещё нет), вызов
 * складывается в список и НЕ исполняется сразу — исполнится только при
 * будущем glCallList. Иначе выполняется немедленно.
 *
 * Именно так себя ведёт настоящий OpenGL: всё, что можно "показывать",
 * записывается, а не выполняется, во время GL_COMPILE.
 *
 * GLBridge — это точка, которая реально дергает GLState/WebGL2; вынесена
 * отдельно, чтобы дать методам тут единообразную форму
 * maybeRecord(() -> GLBridge.xxx(...)).
 */
final class GLDispatch {
    private GLDispatch() {}

    private static GLState s() { return GLState.INSTANCE; }

    private static void maybeRecord(Runnable action) {
        GLState state = s();
        if (state.isRecordingPublic()) {
            state.recordPublic(action);
        } else {
            action.run();
        }
    }

    // --- state toggles ---
    static void enable(int cap, boolean on) {
        maybeRecord(() -> GLBridge.setCapability(s(), cap, on));
    }

    static void clear(int mask) {
        maybeRecord(() -> GLBridge.clear(s(), mask));
    }

    static void clearColor(float r, float g, float b, float a) {
        maybeRecord(() -> s().gl.clearColor(r, g, b, a));
    }

    static void clearDepth(float d) {
        maybeRecord(() -> s().gl.clearDepth(d));
    }

    static void colorMask(boolean r, boolean g, boolean b, boolean a) {
        maybeRecord(() -> s().gl.colorMask(r, g, b, a));
    }

    static void depthFunc(int func) {
        maybeRecord(() -> s().gl.depthFunc(GLBridge.translateDepthFunc(func)));
    }

    static void depthMask(boolean flag) {
        maybeRecord(() -> s().gl.depthMask(flag));
    }

    static void cullFace(int mode) {
        maybeRecord(() -> s().gl.cullFace(GLBridge.translateCullFace(mode)));
    }

    static void blendFunc(int sfactor, int dfactor) {
        maybeRecord(() -> s().gl.blendFunc(GLBridge.translateBlendFactor(sfactor), GLBridge.translateBlendFactor(dfactor)));
    }

    static void lineWidth(float width) {
        maybeRecord(() -> s().gl.lineWidth(width));
    }

    static void polygonOffset(float factor, float units) {
        maybeRecord(() -> s().gl.polygonOffset(factor, units));
    }

    static void alphaFunc(int func, float ref) {
        maybeRecord(() -> GLBridge.setAlphaFunc(s(), func, ref));
    }

    static void viewport(int x, int y, int w, int h) {
        maybeRecord(() -> s().gl.viewport(x, y, w, h));
    }

    static void color(float r, float g, float b, float a) {
        maybeRecord(() -> GLBridge.setColor(s(), r, g, b, a));
    }

    static void normal(float x, float y, float z) {
        maybeRecord(() -> GLBridge.setCurrentNormal(s(), x, y, z));
    }

    // --- textures ---
    static int genTexture() { return GLBridge.genTexture(s()); }

    /**
     * IntBuffer-форма glGenTextures: реальная семантика LWJGL — заполнить
     * buffer.remaining() слотов НОВЫМИ ID, начиная с текущей position(),
     * НЕ продвигая саму position() (вызывающий код сам потом читает через
     * position()..limit(), см. ds.java в decomp). Это единственная форма,
     * реально используемая в decomp (проверено grep — no-arg int-форма
     * нигде не вызывается вне нашего собственного smoke-теста).
     */
    static void genTextures(IntBuffer textures) {
        GLBridge.genTexturesInto(s(), textures);
    }

    static void bindTexture(int target, int texture) {
        maybeRecord(() -> GLBridge.bindTexture(s(), texture));
    }

    static void deleteTexture(int texture) {
        maybeRecord(() -> GLBridge.deleteTexture(s(), texture));
    }

    static void deleteTextures(IntBuffer textures) {
        maybeRecord(() -> GLBridge.deleteTexturesFrom(s(), textures));
    }

    static void texParameteri(int target, int pname, int param) {
        maybeRecord(() -> s().gl.texParameteri(target, pname, param));
    }

    static void pixelStorei(int pname, int param) {
        maybeRecord(() -> s().gl.pixelStorei(pname, param));
    }

    static void texImage2D(int target, int level, int w, int h, ByteBuffer pixels) {
        Uint8Array data = GLBridge.toUint8Array(pixels, w * h * 4);
        maybeRecord(() -> GLRaw.texImage2DRGBA(s().gl, target, level, w, h, data));
    }

    static void texSubImage2D(int target, int level, int xoff, int yoff, int w, int h, ByteBuffer pixels) {
        Uint8Array data = GLBridge.toUint8Array(pixels, w * h * 4);
        maybeRecord(() -> GLRaw.texSubImage2DRGBA(s().gl, target, level, xoff, yoff, w, h, data));
    }

    // --- vertex arrays ---
    // ВАЖНО #1: LWJGL glXxxPointer(..., Buffer) использует ТЕКУЩУЮ позицию
    // буфера (buffer.position()) как базовый offset для чтения атрибута —
    // decomp-код явно этим пользуется (см. is.java: buffer.position(3) перед
    // glTexCoordPointer, buffer.position(0) перед glVertexPointer — общий
    // interleaved FloatBuffer, разный "срез" через position()). Поэтому
    // захватываем position() ЗДЕСЬ, синхронно, вне зависимости от recording —
    // к моменту фактического воспроизведения (glCallList) позиция буфера
    // могла уже измениться на что угодно посторонним кодом.
    //
    // ВАЖНО #2: сама установка состояния (какой буфер/offset/stride активны)
    // должна идти через maybeRecord() так же, как glDrawArrays — иначе
    // display list с несколькими парами (glVertexPointer, glDrawArrays)
    // внутри одного list'а (типичный паттерн для террейна: разные грани/
    // подмеши с разными офсетами в одном и том же display list) при
    // воспроизведении будут все рисоваться ПОСЛЕДНИМ набором вершин,
    // записанным во время compile-фазы, а не тем набором, что был активен
    // непосредственно перед каждым конкретным glDrawArrays.
    static void vertexPointerClient(int size, int stride, FloatBuffer buffer) {
        int baseOffset = buffer.position();
        maybeRecord(() -> GLBridge.setClientArray(s(), GLBridge.ATTR_POSITION, size, stride, buffer, baseOffset));
    }

    static void vertexPointerOffset(int size, int type, int stride, long offset) {
        maybeRecord(() -> GLBridge.setBoundArray(s(), GLBridge.ATTR_POSITION, size, stride, offset));
    }

    static void texCoordPointerClient(int size, int stride, FloatBuffer buffer) {
        int baseOffset = buffer.position();
        maybeRecord(() -> GLBridge.setClientArray(s(), GLBridge.ATTR_TEXCOORD, size, stride, buffer, baseOffset));
    }

    static void texCoordPointerOffset(int size, int type, int stride, long offset) {
        maybeRecord(() -> GLBridge.setBoundArray(s(), GLBridge.ATTR_TEXCOORD, size, stride, offset));
    }

    static void colorPointerClient(int size, int stride, ByteBuffer buffer) {
        int baseOffset = buffer.position();
        maybeRecord(() -> GLBridge.setClientArrayBytes(s(), GLBridge.ATTR_COLOR, size, stride, buffer, baseOffset));
    }

    static void colorPointerOffset(int size, int type, int stride, long offset) {
        maybeRecord(() -> GLBridge.setBoundArray(s(), GLBridge.ATTR_COLOR, size, stride, offset));
    }

    static void normalPointerClient(int stride, ByteBuffer buffer) {
        int baseOffset = buffer.position();
        maybeRecord(() -> GLBridge.setClientArrayBytes(s(), GLBridge.ATTR_NORMAL, 3, stride, buffer, baseOffset));
    }

    static void normalPointerOffset(int type, int stride, long offset) {
        maybeRecord(() -> GLBridge.setBoundArray(s(), GLBridge.ATTR_NORMAL, 3, stride, offset));
    }

    static void drawArrays(int mode, int first, int count) {
        GLState state = s();
        if (state.isRecordingPublic()) {
            boolean anyClient = false;
            for (GLState.AttrState a : state.attrs) {
                if (a.size > 0 && a.fromClient) { anyClient = true; break; }
            }
            if (anyClient) {
                // ИСПРАВЛЕНО (главная причина "блоки есть по коллизии, но
                // невидимы"): раньше весь glDrawArrays целиком откладывался
                // через maybeRecord() — включая чтение client-side буферов
                // (is.java Tessellator использует ОДИН переиспользуемый
                // ByteBuffer для HUD, GUI, неба И КАЖДОГО чанка). Display
                // list чанка компилируется ОДИН раз, а воспроизводится
                // (glCallList) много кадров спустя — к этому моменту общий
                // буфер тессельятора уже десятки раз перезаписан другими
                // отрисовками, и stageClientBuffers() при реальном
                // воспроизведении читал уже ЧУЖИЕ/пустые данные. Небо
                // работало только потому, что его display list
                // перезаписывается и тут же проигрывается в том же кадре —
                // буфер ещё не успевал измениться.
                //
                // Теперь: если сейчас идёт запись list'а и хотя бы один
                // атрибут — client-side буфер, СНИМАЕМ снимок данных
                // СРАЗУ (пока буфер ещё содержит то, что только что
                // затессельировал вызывающий код), и в отложенный вызов
                // передаём уже готовый float[] снимок, а не ссылку на
                // живой буфер. Коллизии (данные блоков в мире) эта ошибка
                // никогда не задевала — она чисто про кэш геометрии.
                float[] snapshot = GLBridge.snapshotClientBuffers(state, first, count);
                state.recordPublic(() -> GLBridge.drawArraysFromSnapshot(s(), mode, snapshot, count));
                return;
            }
        }
        maybeRecord(() -> GLBridge.drawArrays(s(), mode, first, count));
    }

    static void disableClientState(int cap) {
        int attr = GLBridge.clientStateToAttr(cap);
        if (attr < 0) return;
        maybeRecord(() -> s().attrs[attr].size = 0);
    }

    // --- lighting / fog ---
    static void light(int light, int pname, FloatBuffer params) {
        float[] v = GLBridge.readFloats(params, 4);
        maybeRecord(() -> GLBridge.setLight(s(), light, pname, v));
    }

    static void lightModel(int pname, FloatBuffer params) {
        float[] v = GLBridge.readFloats(params, 4);
        maybeRecord(() -> GLBridge.setLightModel(s(), pname, v));
    }

    static void fog(int pname, FloatBuffer params) {
        float[] v = GLBridge.readFloats(params, 4);
        maybeRecord(() -> GLBridge.setFogv(s(), pname, v));
    }

    static void fogf(int pname, float param) {
        maybeRecord(() -> GLBridge.setFogf(s(), pname, param));
    }

    static void fogi(int pname, int param) {
        maybeRecord(() -> GLBridge.setFogi(s(), pname, param));
    }

    // --- misc ---
    static int getError() { return s().gl.getError(); }

    static String getString(int name) {
        return GLBridge.getString(s(), name);
    }

    static void getFloat(int pname, FloatBuffer out) {
        GLBridge.getFloatv(s(), pname, out);
    }

    static void readPixels(int x, int y, int w, int h, ByteBuffer pixels) {
        Uint8Array tmp = GLBridge.newUint8Array(w * h * 4);
        s().gl.finish();
        GLRaw.readPixelsRGBA(s().gl, x, y, w, h, tmp);
        GLBridge.copyToByteBuffer(tmp, pixels);
    }
}
