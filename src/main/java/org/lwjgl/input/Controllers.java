package org.lwjgl.input;

/**
 * Шим org.lwjgl.input.Controllers. Используется в decomp только в
 * Controllers.create(), вызванном внутри try/catch (см. Minecraft.java) —
 * геймпады не являются частью MVP веб-порта; можно добавить позже через
 * Gamepad API браузера, если понадобится.
 */
public final class Controllers {
    private Controllers() {}

    public static void create() {
        // no-op: геймпады не реализованы. Вызывающий код уже оборачивает
        // этот вызов в try/catch, так что молчаливый no-op безопасен.
    }

    public static int getControllerCount() { return 0; }
}
