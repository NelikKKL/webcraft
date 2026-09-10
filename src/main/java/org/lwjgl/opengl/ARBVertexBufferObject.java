package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Шим org.lwjgl.opengl.ARBVertexBufferObject.
 *
 * ВАЖНО: в decomp (is.java, Tessellator) путь через эту extension-based VBO
 * API защищён условием "this.x = c && GLContext.getCapabilities()....",
 * где c — статическое поле, жёстко равное false в этой сборке. Значит
 * this.x всегда false, и методы этого класса физически НИКОГДА не будут
 * вызваны в реальном игровом цикле — единственный путь geometрии, который
 * реально исполняется, это client-side vertex arrays (glVertexPointer с
 * прямым FloatBuffer), уже полностью реализованный в GL11/GLBridge.
 *
 * Эти методы существуют здесь только чтобы decomp-код КОМПИЛИРОВАЛСЯ
 * (вызовы есть в мёртвой ветке, но типы должны существовать). Если
 * когда-нибудь понадобится реальная VBO-реализация (например, при
 * портировании более поздней версии игры, где VBO путь не заблокирован),
 * это можно сделать через настоящие WebGL2 buffer object вызовы — GLState
 * уже умеет создавать/биндить WebGLBuffer, не хватает только int-хендл
 * таблицы (по аналогии с textures) и glBufferData(ByteBuffer) конвертации.
 */
public final class ARBVertexBufferObject {
    private ARBVertexBufferObject() {}

    public static void glGenBuffersARB(IntBuffer buffer) {
        throw new UnsupportedOperationException(
            "ARBVertexBufferObject не реализован в web-порте — этот путь не должен " +
            "исполняться (capability флаг всегда false), см. javadoc класса");
    }

    public static void glBindBufferARB(int target, int buffer) {
        throw new UnsupportedOperationException("см. javadoc ARBVertexBufferObject");
    }

    public static void glBufferDataARB(int target, ByteBuffer data, int usage) {
        throw new UnsupportedOperationException("см. javadoc ARBVertexBufferObject");
    }
}
