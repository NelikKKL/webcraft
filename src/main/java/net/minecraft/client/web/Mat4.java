package net.minecraft.client.web;

/**
 * Column-major 4x4 матрицы, как того ожидает OpenGL/WebGL (uniformMatrix4fv
 * с transpose=false). Все операции реализуют классическую семантику
 * fixed-function GL: m = m * op (умножение справа), т.к. GL применяет
 * трансформации в обратном порядке вызова.
 */
final class Mat4 {
    private Mat4() {}

    // PERF: раньше каждая операция (translate/scale/rotate/ortho/frustum)
    // выделяла 2-3 новых float[16] — тысячи мусорных массивов в кадр при
    // проигрывании display list'ов чанков (push+translate*3+scale+pop на
    // каждый чанк) и, как следствие, фризы от GC. Теперь используем два
    // статических временных массива (JS однопоточный, повторный вход
    // невозможен). Арифметика НЕ менялась — результаты побитово те же.
    private static final float[] TMP = new float[16];
    private static final float[] OP = new float[16];

    private static float[] opIdentity() {
        float[] o = OP;
        for (int i = 0; i < 16; i++) o[i] = 0f;
        o[0] = o[5] = o[10] = o[15] = 1f;
        return o;
    }

    private static float[] opZero() {
        float[] o = OP;
        for (int i = 0; i < 16; i++) o[i] = 0f;
        return o;
    }

    static void multiply(float[] m, float[] rhs) {
        float[] result = TMP;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += m[k * 4 + row] * rhs[col * 4 + k];
                }
                result[col * 4 + row] = sum;
            }
        }
        System.arraycopy(result, 0, m, 0, 16);
    }

    // Специализированные translate/scale: тот же порядок сложения, что и в
    // общем multiply() (нулевые слагаемые не меняют конечный результат), но
    // без 64 умножений на вызов.
    static void translate(float[] m, float x, float y, float z) {
        for (int row = 0; row < 4; row++) {
            m[12 + row] = m[row] * x + m[4 + row] * y + m[8 + row] * z + m[12 + row];
        }
    }

    static void scale(float[] m, float x, float y, float z) {
        for (int row = 0; row < 4; row++) {
            // "+ 0f" воспроизводит знак нуля общего multiply() (0 + (-0) = +0).
            m[row] = m[row] * x + 0f;
            m[4 + row] = m[4 + row] * y + 0f;
            m[8 + row] = m[8 + row] * z + 0f;
        }
    }

    static void rotate(float[] m, float angleDeg, float x, float y, float z) {
        double rad = Math.toRadians(angleDeg);
        float c = (float) Math.cos(rad);
        float s = (float) Math.sin(rad);
        float len = (float) Math.sqrt(x * x + y * y + z * z);
        if (len == 0) return;
        x /= len; y /= len; z /= len;
        float t = 1 - c;

        float[] r = opIdentity();
        r[0] = t * x * x + c;
        r[1] = t * x * y + s * z;
        r[2] = t * x * z - s * y;
        r[4] = t * x * y - s * z;
        r[5] = t * y * y + c;
        r[6] = t * y * z + s * x;
        r[8] = t * x * z + s * y;
        r[9] = t * y * z - s * x;
        r[10] = t * z * z + c;
        multiply(m, r);
    }

    static void ortho(float[] m, double left, double right, double bottom, double top, double near, double far) {
        float[] o = opZero();
        o[0] = (float) (2.0 / (right - left));
        o[5] = (float) (2.0 / (top - bottom));
        o[10] = (float) (-2.0 / (far - near));
        o[12] = (float) (-(right + left) / (right - left));
        o[13] = (float) (-(top + bottom) / (top - bottom));
        o[14] = (float) (-(far + near) / (far - near));
        o[15] = 1f;
        multiply(m, o);
    }

    static void frustum(float[] m, double left, double right, double bottom, double top, double near, double far) {
        float[] f = opZero();
        f[0] = (float) (2 * near / (right - left));
        f[5] = (float) (2 * near / (top - bottom));
        f[8] = (float) ((right + left) / (right - left));
        f[9] = (float) ((top + bottom) / (top - bottom));
        f[10] = (float) (-(far + near) / (far - near));
        f[11] = -1f;
        f[14] = (float) (-(2 * far * near) / (far - near));
        multiply(m, f);
    }

    static float[] identity() {
        float[] m = new float[16];
        m[0] = m[5] = m[10] = m[15] = 1f;
        return m;
    }
}
