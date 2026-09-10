package net.minecraft.client.web;

/**
 * Column-major 4x4 матрицы, как того ожидает OpenGL/WebGL (uniformMatrix4fv
 * с transpose=false). Все операции реализуют классическую семантику
 * fixed-function GL: m = m * op (умножение справа), т.к. GL применяет
 * трансформации в обратном порядке вызова.
 */
final class Mat4 {
    private Mat4() {}

    static void multiply(float[] m, float[] rhs) {
        float[] result = new float[16];
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

    static void translate(float[] m, float x, float y, float z) {
        float[] t = identity();
        t[12] = x; t[13] = y; t[14] = z;
        multiply(m, t);
    }

    static void scale(float[] m, float x, float y, float z) {
        float[] s = identity();
        s[0] = x; s[5] = y; s[10] = z;
        multiply(m, s);
    }

    static void rotate(float[] m, float angleDeg, float x, float y, float z) {
        double rad = Math.toRadians(angleDeg);
        float c = (float) Math.cos(rad);
        float s = (float) Math.sin(rad);
        float len = (float) Math.sqrt(x * x + y * y + z * z);
        if (len == 0) return;
        x /= len; y /= len; z /= len;
        float t = 1 - c;

        float[] r = identity();
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
        float[] o = new float[16];
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
        float[] f = new float[16];
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
