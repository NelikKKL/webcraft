package org.lwjgl.input;

import net.minecraft.client.web.KeyboardBridge;

import java.util.ArrayDeque;

/**
 * Шим org.lwjgl.input.Keyboard. Реальный LWJGL/decomp-код Alpha 1.2.6
 * работает с числовыми scancode (DirectInput-style — 1=ESCAPE, 42=LSHIFT,
 * 59=F1 и т.д.), а НЕ с Keyboard.KEY_* именованными константами (проверено
 * grep'ом по всему decomp — символьные константы нигде не используются).
 *
 * DOM даёт события через KeyboardEvent.code ("KeyW", "ShiftLeft", "F1"...),
 * поэтому нужен явный перевод code -> DirectInput scancode. Таблица в
 * KeyboardBridge.scancodeFor(String).
 *
 * Модель событий: реальный LWJGL — это pull-based очередь (next() продвигает
 * указатель, getEventKey()/getEventKeyState() читают текущее событие).
 * JS-сторона (KeyboardBridge, слушает window keydown/keyup) складывает
 * события в общую очередь, next() их вычитывает.
 */
public final class Keyboard {
    private Keyboard() {}

    public static final int KEY_NONE = 0x00;
    public static final int KEY_ESCAPE = 0x01;
    public static final int KEY_RETURN = 0x1C;
    public static final int KEY_TAB = 0x0F;
    public static final int KEY_SPACE = 0x39;
    public static final int KEY_LSHIFT = 0x2A;
    public static final int KEY_RSHIFT = 0x36;
    public static final int KEY_LCONTROL = 0x1D;

    static final class Event {
        final int key;
        final boolean state;
        final char ch;
        Event(int key, boolean state, char ch) { this.key = key; this.state = state; this.ch = ch; }
    }

    private static final ArrayDeque<Event> queue = new ArrayDeque<>();
    private static final boolean[] down = new boolean[256];
    private static Event current = null;
    private static boolean created = false;

    public static void create() {
        if (created) return;
        created = true;
        KeyboardBridge.install(Keyboard::onDomKeyEvent);
    }

    public static void destroy() {
        created = false;
        // Снятие DOM-листенеров не реализовано отдельно — при перезагрузке
        // страницы это не требуется; если понадобится полноценный
        // жизненный цикл (пересоздание Keyboard без reload), нужно
        // добавить removeEventListener в KeyboardBridge.
    }

    public static boolean isCreated() { return created; }

    /** Вызывается из JS при keydown/keyup. domCode — KeyboardEvent.code, keyChar — из KeyboardEvent.key (для печатных символов, иначе '\0'). */
    private static void onDomKeyEvent(String domCode, boolean pressed, char keyChar) {
        int scancode = KeyboardBridge.scancodeFor(domCode);
        if (scancode == 0) return;
        down[scancode & 0xFF] = pressed;
        queue.addLast(new Event(scancode, pressed, keyChar));
        // Не даём очереди расти бесконечно, если кадры почему-то не идут.
        while (queue.size() > 1024) queue.removeFirst();
    }

    public static boolean next() {
        if (queue.isEmpty()) { current = null; return false; }
        current = queue.removeFirst();
        return true;
    }

    public static int getEventKey() { return current == null ? 0 : current.key; }
    public static boolean getEventKeyState() { return current != null && current.state; }
    public static char getEventCharacter() { return current == null ? 0 : current.ch; }

    public static boolean isKeyDown(int key) {
        if (key < 0 || key >= down.length) return false;
        return down[key];
    }

    public static String getKeyName(int key) {
        return KeyboardBridge.nameForScancode(key);
    }
}
