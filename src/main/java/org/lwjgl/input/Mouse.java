package org.lwjgl.input;

import net.minecraft.client.web.MouseBridge;

import java.util.ArrayDeque;

/**
 * Шим org.lwjgl.input.Mouse. LWJGL-модель: pull-based очередь событий
 * (next()/getEventX/Y/Button/ButtonState/DWheel), плюс "мгновенные"
 * getX/getY/getDX/getDY для текущего кадра (относительное движение —
 * используется для обзора камерой при grabbed-режиме).
 *
 * grabbed-режим = Pointer Lock API браузера: при setGrabbed(true) курсор
 * скрывается и движения мыши идут как относительные дельты
 * (MouseEvent.movementX/Y), что 1:1 соответствует ожиданиям decomp-кода
 * (getDX/getDY при активном grab используются для поворота камеры).
 *
 * LWJGL Y растёт снизу вверх (оконная система OpenGL), DOM Y растёт сверху
 * вниз — конвертация происходит в MouseBridge на границе с DOM-событием.
 */
public final class Mouse {
    private Mouse() {}

    static final class Event {
        final int x, y, dwheel, button;
        final boolean buttonState;
        Event(int x, int y, int dwheel, int button, boolean buttonState) {
            this.x = x; this.y = y; this.dwheel = dwheel; this.button = button; this.buttonState = buttonState;
        }
    }

    private static final ArrayDeque<Event> queue = new ArrayDeque<>();
    private static Event current = null;
    private static final boolean[] buttonDown = new boolean[8];

    private static int curX = 0, curY = 0;
    private static int accumDx = 0, accumDy = 0;
    private static boolean created = false;

    public static void create() {
        if (created) return;
        created = true;
        MouseBridge.install(Mouse::onDomMouseEvent);
    }

    public static void destroy() {
        created = false;
    }

    /**
     * Вызывается из JS при mousemove/mousedown/mouseup/wheel.
     * type: 0=move, 1=down, 2=up, 3=wheel.
     */
    private static void onDomMouseEvent(int type, int x, int y, int dx, int dy, int button, int wheel) {
        switch (type) {
            case 0: // move
                curX = x; curY = y;
                accumDx += dx; accumDy += dy;
                queue.addLast(new Event(x, y, 0, -1, false));
                break;
            case 1: // down
                if (button >= 0 && button < buttonDown.length) buttonDown[button] = true;
                queue.addLast(new Event(curX, curY, 0, button, true));
                break;
            case 2: // up
                if (button >= 0 && button < buttonDown.length) buttonDown[button] = false;
                queue.addLast(new Event(curX, curY, 0, button, false));
                break;
            case 3: // wheel
                queue.addLast(new Event(curX, curY, wheel, -1, false));
                break;
        }
        while (queue.size() > 1024) queue.removeFirst();
    }

    public static boolean next() {
        if (queue.isEmpty()) { current = null; return false; }
        current = queue.removeFirst();
        return true;
    }

    public static int getEventX() { return current == null ? curX : current.x; }
    public static int getEventY() { return current == null ? curY : current.y; }
    public static int getEventDWheel() { return current == null ? 0 : current.dwheel; }
    public static int getEventButton() { return current == null ? -1 : current.button; }
    public static boolean getEventButtonState() { return current != null && current.buttonState; }

    public static int getX() { return curX; }
    public static int getY() { return curY; }

    /** Накопленная относительная дельта с последнего вызова — LWJGL-семантика: вызов "потребляет" дельту. */
    public static int getDX() {
        int d = accumDx;
        accumDx = 0;
        return d;
    }

    public static int getDY() {
        int d = accumDy;
        accumDy = 0;
        return d;
    }

    public static boolean isButtonDown(int button) {
        return button >= 0 && button < buttonDown.length && buttonDown[button];
    }

    public static void setCursorPosition(int x, int y) {
        curX = x; curY = y;
    }

    public static void setGrabbed(boolean grabbed) {
        MouseBridge.setGrabbed(grabbed);
    }
}
