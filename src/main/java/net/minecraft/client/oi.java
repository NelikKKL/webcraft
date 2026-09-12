/*
 * ПАТЧЕНО для web-порта — см. PATCHES.md, раздел "oi.java (мышь: grab/ungrab)".
 *
 * Оригинал использовал java.awt.Component (для getWidth/getHeight — центрирование
 * курсора при ungrab) и org.lwjgl.input.Cursor (кастомная форма курсора ОС).
 * В веб-версии:
 *  - Component всегда был бы null (AWT canvas не используется) — заменён на
 *    org.lwjgl.opengl.Display, который всегда корректно отражает размер
 *    реального <canvas>.
 *  - Cursor (кастомная форма курсора) не реализован в этой сессии — Pointer
 *    Lock API браузера и так скрывает курсор полностью в grabbed-режиме,
 *    а вне grabbed-режима используется обычный курсор ОС/браузера.
 */
package net.minecraft.client;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;

public class oi {
    public int a;
    public int b;

    public oi() {
    }

    public void a() {
        Mouse.setGrabbed(true);
        this.a = 0;
        this.b = 0;
    }

    public void b() {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void c() {
        this.a = Mouse.getDX();
        this.b = Mouse.getDY();
    }
}
