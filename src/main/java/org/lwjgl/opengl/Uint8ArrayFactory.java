package org.lwjgl.opengl;

import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Создание Uint8Array заданной длины.
 *
 * ПРИМЕЧАНИЕ О РИСКЕ: сборка здесь не проверяется (нет сети/Maven в этой
 * среде — см. обсуждение в чате), поэтому используем самый "низкоуровневый"
 * и стабильный путь через ArrayBuffer, который в TeaVM jso-apis существует
 * стабильно уже много версий (в отличие от Uint8Array.create(int), который
 * в некоторых версиях jso-apis мог отсутствовать как static factory).
 * Если первая сборка в CI покажет, что Uint8Array.create(int) тоже доступен
 * напрямую — можно упростить до одной строки.
 */
final class Uint8ArrayFactory {
    private Uint8ArrayFactory() {}

    static Uint8Array create(int length) {
        ArrayBuffer buffer = new ArrayBuffer(length);
        return new Uint8Array(buffer);
    }
}
