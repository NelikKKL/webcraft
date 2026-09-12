package org.lwjgl.opengl;

import org.teavm.jso.typedarrays.Float32Array;

/**
 * Конвертация Java float[] -> JS Float32Array через
 * Float32Array.copyFromJavaArray(float[]) — официальный, документированный
 * способ TeaVM (подтверждено официальным примером teavm.org/playground:
 * examples/advanced/Cube3D.java, использующим ровно этот метод для
 * загрузки геометрии куба в WebGL — тот же сценарий использования, что и
 * здесь). Предыдущая версия этого класса вручную собирала ArrayBuffer +
 * Float32Array и копировала поэлементно через arr.set(i, value) —
 * рабочий, но менее надёжный путь; заменён на официальный метод по
 * результатам первого реального прогона CI (см. PATCHES.md/TODO.md).
 */
final class Float32ArrayFactory {
    private Float32ArrayFactory() {}

    static Float32Array wrap(float[] data) {
        return Float32Array.copyFromJavaArray(data);
    }
}
