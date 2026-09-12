package org.lwjgl.opengl;

import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Float32Array;

/**
 * Конвертация Java float[] -> JS Float32Array.
 *
 * ИСТОРИЯ ПРАВОК (см. TODO.md за подробностями):
 *  1. Изначально: ручной цикл arr.set(i, data[i]) — риск, что set(int,float)
 *     может не существовать (по аналогии с Uint8Array.set(int,int)).
 *  2. Заменено на Float32Array.copyFromJavaArray(float[]) по примеру из
 *     teavm.org/playground — ОКАЗАЛОСЬ НЕ СУЩЕСТВУЕТ в этой версии TeaVM
 *     (подтверждено реальной ошибкой компиляции: "cannot find symbol:
 *     method copyFromJavaArray(float[])"). Судя по всему, тот playground
 *     пример использовал другую версию TeaVM API.
 *  3. Текущая версия: bulk set(float[], int) — ПОДТВЕРЖДЕНО существующим
 *     самим текстом ПЕРВОЙ ошибки компиляции в этом проекте (список
 *     кандидатных перегрузок ArrayBufferView.set включал
 *     "set(float[],int) is not applicable" для другого вызова — то есть
 *     метод РЕАЛЬНО существует в API, просто не подошёл по типам в том
 *     конкретном месте). Тот же паттерн уже успешно работает для
 *     Uint8Array (см. GLBridge.toUint8Array — set(byte[], int)).
 */
final class Float32ArrayFactory {
    private Float32ArrayFactory() {}

    static Float32Array wrap(float[] data) {
        ArrayBuffer buffer = new ArrayBuffer(data.length * 4);
        Float32Array arr = new Float32Array(buffer);
        arr.set(data, 0);
        return arr;
    }
}
