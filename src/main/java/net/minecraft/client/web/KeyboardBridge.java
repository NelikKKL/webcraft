package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Устанавливает window keydown/keyup слушатели и переводит
 * KeyboardEvent.code (DOM, независим от раскладки) в DirectInput scancode,
 * который ожидает decomp-код Alpha 1.2.6 (см. Keyboard.java — весь decomp
 * использует числовые scancode, не символьные Keyboard.KEY_* константы).
 *
 * Java -> JS callback передаётся через @JSFunctor-интерфейс (документированный
 * TeaVM-паттерн: функциональный интерфейс с единственным методом, помеченный
 * @JSFunctor, компилируется в вызываемую JS-функцию и может быть передан
 * прямо в @JSBody как аргумент). Это надёжнее самодельных мостов через
 * глобальные window-поля и приватные методы.
 */
public final class KeyboardBridge {

    private KeyboardBridge() {}

    public interface KeyCallback {
        void onKey(String domCode, boolean pressed, char keyChar);
    }

    @JSFunctor
    interface DomKeyHandler extends JSObject {
        void handle(String code, boolean pressed, String keyStr);
    }

    private static final Map<String, Integer> CODE_TO_SCANCODE = new HashMap<>();
    private static final Map<Integer, String> SCANCODE_TO_NAME = new HashMap<>();

    static {
        put("Escape", 0x01, "ESCAPE");
        put("Digit1", 0x02, "1"); put("Digit2", 0x03, "2"); put("Digit3", 0x04, "3");
        put("Digit4", 0x05, "4"); put("Digit5", 0x06, "5"); put("Digit6", 0x07, "6");
        put("Digit7", 0x08, "7"); put("Digit8", 0x09, "8"); put("Digit9", 0x0A, "9");
        put("Digit0", 0x0B, "0");
        put("Minus", 0x0C, "MINUS"); put("Equal", 0x0D, "EQUALS");
        put("Backspace", 0x0E, "BACK");
        put("Tab", 0x0F, "TAB");
        put("KeyQ", 0x10, "Q"); put("KeyW", 0x11, "W"); put("KeyE", 0x12, "E"); put("KeyR", 0x13, "R");
        put("KeyT", 0x14, "T"); put("KeyY", 0x15, "Y"); put("KeyU", 0x16, "U"); put("KeyI", 0x17, "I");
        put("KeyO", 0x18, "O"); put("KeyP", 0x19, "P");
        put("BracketLeft", 0x1A, "LBRACKET"); put("BracketRight", 0x1B, "RBRACKET");
        put("Enter", 0x1C, "RETURN");
        put("ControlLeft", 0x1D, "LCONTROL");
        put("KeyA", 0x1E, "A"); put("KeyS", 0x1F, "S"); put("KeyD", 0x20, "D"); put("KeyF", 0x21, "F");
        put("KeyG", 0x22, "G"); put("KeyH", 0x23, "H"); put("KeyJ", 0x24, "J"); put("KeyK", 0x25, "K");
        put("KeyL", 0x26, "L");
        put("Semicolon", 0x27, "SEMICOLON"); put("Quote", 0x28, "APOSTROPHE"); put("Backquote", 0x29, "GRAVE");
        put("ShiftLeft", 0x2A, "LSHIFT");
        put("Backslash", 0x2B, "BACKSLASH");
        put("KeyZ", 0x2C, "Z"); put("KeyX", 0x2D, "X"); put("KeyC", 0x2E, "C"); put("KeyV", 0x2F, "V");
        put("KeyB", 0x30, "B"); put("KeyN", 0x31, "N"); put("KeyM", 0x32, "M");
        put("Comma", 0x33, "COMMA"); put("Period", 0x34, "PERIOD"); put("Slash", 0x35, "SLASH");
        put("ShiftRight", 0x36, "RSHIFT");
        put("NumpadMultiply", 0x37, "MULTIPLY");
        put("AltLeft", 0x38, "LMENU");
        put("Space", 0x39, "SPACE");
        put("CapsLock", 0x3A, "CAPITAL");
        put("F1", 0x3B, "F1"); put("F2", 0x3C, "F2"); put("F3", 0x3D, "F3"); put("F4", 0x3E, "F4");
        put("F5", 0x3F, "F5"); put("F6", 0x40, "F6"); put("F7", 0x41, "F7"); put("F8", 0x42, "F8");
        put("F9", 0x43, "F9"); put("F10", 0x44, "F10"); put("F11", 0x57, "F11"); put("F12", 0x58, "F12");
        put("NumLock", 0x45, "NUMLOCK");
        put("ScrollLock", 0x46, "SCROLL");
        put("ArrowUp", 0xC8, "UP"); put("ArrowDown", 0xD0, "DOWN");
        put("ArrowLeft", 0xCB, "LEFT"); put("ArrowRight", 0xCD, "RIGHT");
        put("ControlRight", 0x9D, "RCONTROL");
        put("AltRight", 0xB8, "RMENU");
        put("Insert", 0xD2, "INSERT"); put("Delete", 0xD3, "DELETE");
        put("Home", 0xC7, "HOME"); put("End", 0xCF, "END");
        put("PageUp", 0xC9, "PRIOR"); put("PageDown", 0xD1, "NEXT");
    }

    private static void put(String domCode, int scancode, String name) {
        CODE_TO_SCANCODE.put(domCode, scancode);
        SCANCODE_TO_NAME.put(scancode, name);
    }

    public static int scancodeFor(String domCode) {
        Integer v = CODE_TO_SCANCODE.get(domCode);
        return v == null ? 0 : v;
    }

    public static String nameForScancode(int scancode) {
        String n = SCANCODE_TO_NAME.get(scancode);
        return n == null ? "UNKNOWN" : n;
    }

    private static KeyCallback callback;

    public static void install(KeyCallback cb) {
        callback = cb;
        DomKeyHandler handler = (code, pressed, keyStr) -> {
            char c = (keyStr != null && keyStr.length() == 1) ? keyStr.charAt(0) : '\0';
            callback.onKey(code, pressed, c);
        };
        installListeners(handler);
    }

    /**
     * org.lwjgl.input.Keyboard.enableRepeatEvents(boolean) — включает
     * повторные KEY_DOWN события при удержании клавиши (нужно для
     * зажатия Backspace в текстовых полях чата/ввода имени). DOM и так
     * шлёт keydown с e.repeat=true при удержании — просто перестаём их
     * фильтровать, когда включено. Флаг живёт на JS-стороне
     * (window.__mcRepeatEnabled), чтобы не гонять его через отдельный
     * Java<->JS вызов на каждое нажатие.
     */
    @JSBody(params = { "enabled" }, script = "window.__mcRepeatEnabled = enabled;")
    public static native void setRepeatEventsEnabled(boolean enabled);

    @JSBody(params = { "handler" }, script =
        "window.__mcRepeatEnabled = window.__mcRepeatEnabled || false;" +
        "window.addEventListener('keydown', function(e) {" +
        "  if (e.repeat && !window.__mcRepeatEnabled) return;" +
        "  handler.handle(e.code, true, e.key);" +
        "  var navKeys = ['Tab','Space','ArrowUp','ArrowDown','ArrowLeft','ArrowRight'];" +
        "  if (navKeys.indexOf(e.code) !== -1) e.preventDefault();" +
        "}, false);" +
        "window.addEventListener('keyup', function(e) {" +
        "  handler.handle(e.code, false, e.key);" +
        "}, false);")
    private static native void installListeners(DomKeyHandler handler);
}
