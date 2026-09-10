package org.lwjgl.opengl;

import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Float32Array;

/**
 * Конвертация Java float[] -> JS Float32Array через ArrayBuffer (см.
 * примечание о риске в Uint8ArrayFactory — тот же принцип: используем самый
 * стабильный, долго существующий кусок TeaVM jso-apis, а не полагаемся на
 * предположения о деталях реализации, которые здесь негде проверить сборкой).
 *
 * Счётные массивы тут — матрицы 16 float на draw call, не мегабайты
 * геометрии, так что цена поэлементного копирования пренебрежимо мала.
 */
final class Float32ArrayFactory {
    private Float32ArrayFactory() {}

    static Float32Array wrap(float[] data) {
        ArrayBuffer buffer = new ArrayBuffer(data.length * 4);
        Float32Array arr = new Float32Array(buffer);
        for (int i = 0; i < data.length; i++) {
            arr.set(i, data[i]);
        }
        return arr;
    }
}
