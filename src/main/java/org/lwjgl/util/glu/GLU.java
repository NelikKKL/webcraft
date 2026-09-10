package org.lwjgl.util.glu;

import net.minecraft.client.web.GLState;

/**
 * Шим org.lwjgl.util.glu.GLU. Реализует только реально используемые в
 * decomp методы (проверено grep'ом по всему проекту): gluPerspective и
 * gluErrorString.
 */
public final class GLU {
    private GLU() {}

    /** Классическая формула перевода fovY/aspect/near/far в glFrustum-параметры. */
    public static void gluPerspective(float fovY, float aspect, float zNear, float zFar) {
        double top = zNear * Math.tan(Math.toRadians(fovY) / 2.0);
        double bottom = -top;
        double left = bottom * aspect;
        double right = top * aspect;
        GLState.INSTANCE.frustum(left, right, bottom, top, zNear, zFar);
    }

    public static String gluErrorString(int errorCode) {
        return "GL error " + errorCode;
    }
}
