package net.minecraft.client.web;

import java.util.HashMap;
import java.util.Map;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Мост между Java и WebRTC (web/extras.js, раздел "Мультиплеер").
 *
 * Одна "ссылка" (link) = одно WebRTC-соединение = один игрок. Каждое сообщение
 * DataChannel — ровно один игровой пакет (SCTP сохраняет границы сообщений), поэтому
 * отдельной нарезки потока на пакеты не нужно.
 */
public final class Mp {

    private Mp() {}

    public interface Listener {
        void onOpen();
        void onMessage(byte[] data);
        void onClose();
        /** Результат createInvite/acceptInvite: код или текст ошибки. */
        void onCode(String code, String error);
        /** Результат acceptAnswer: error == "" — успех. */
        void onAccepted(String error);
    }

    @JSFunctor interface IdCb extends JSObject { void call(int id); }
    @JSFunctor interface MsgCb extends JSObject { void call(int id, Uint8Array data); }
    @JSFunctor interface CodeCb extends JSObject { void call(int id, String code, String error); }
    @JSFunctor interface AcceptedCb extends JSObject { void call(int id, String error); }
    @JSFunctor interface PasteCb extends JSObject { void call(String text); }

    private static final Map<Integer, Listener> listeners = new HashMap<>();
    private static boolean installed = false;

    /** Текст, пришедший из буфера обмена (или из prompt); consumePasted() забирает его один раз. */
    private static String pasted = null;
    private static boolean pasteBusy = false;

    public static void install() {
        if (installed) return;
        installed = true;
        IdCb open = id -> { Listener l = listeners.get(id); if (l != null) l.onOpen(); };
        MsgCb msg = (id, data) -> {
            Listener l = listeners.get(id);
            if (l == null) return;
            int n = data.getLength();
            byte[] b = new byte[n];
            for (int i = 0; i < n; i++) b[i] = (byte) data.get(i);
            l.onMessage(b);
        };
        IdCb close = id -> { Listener l = listeners.get(id); if (l != null) l.onClose(); };
        CodeCb code = (id, c, e) -> { Listener l = listeners.get(id); if (l != null) l.onCode(c, e); };
        AcceptedCb accepted = (id, e) -> { Listener l = listeners.get(id); if (l != null) l.onAccepted(e); };
        PasteCb paste = t -> { pasted = t == null ? "" : t; pasteBusy = false; };
        installNative(open, msg, close, code, accepted, paste);
    }

    @JSBody(params = { "open", "msg", "close", "code", "accepted", "paste" }, script =
        "window.__javaMp = { onOpen: open, onMessage: msg, onClose: close, onCode: code, onAccepted: accepted, onPaste: paste };")
    private static native void installNative(IdCb open, MsgCb msg, IdCb close, CodeCb code, AcceptedCb accepted, PasteCb paste);

    // ------------------------------------------------------------ ссылки

    public static int newLink(Listener l) {
        install();
        int id = newLinkNative();
        listeners.put(id, l);
        return id;
    }

    /** Подписать слушателя на уже созданную ссылку (когда он сам создаётся позже ссылки). */
    public static void registerListener(int id, Listener l) { listeners.put(id, l); }

    public static void createInvite(int id) { createInviteNative(id); }
    public static void acceptInvite(int id, String code) { acceptInviteNative(id, code); }
    public static void acceptAnswer(int id, String code) { acceptAnswerNative(id, code); }

    public static void send(int id, byte[] data, int len) {
        Uint8Array arr = new Uint8Array(new ArrayBuffer(len));
        if (data.length == len) {
            arr.set(data, 0);
        } else {
            byte[] exact = new byte[len];
            System.arraycopy(data, 0, exact, 0, len);
            arr.set(exact, 0);
        }
        sendNative(id, arr);
    }

    public static void close(int id) {
        listeners.remove(id);
        closeNative(id);
    }

    public static boolean isOpen(int id) { return isOpenNative(id); }

    @JSBody(script = "return window.Mp.newLink();")
    private static native int newLinkNative();
    @JSBody(params = { "id" }, script = "window.Mp.createInvite(id);")
    private static native void createInviteNative(int id);
    @JSBody(params = { "id", "code" }, script = "window.Mp.acceptInvite(id, code);")
    private static native void acceptInviteNative(int id, String code);
    @JSBody(params = { "id", "code" }, script = "window.Mp.acceptAnswer(id, code);")
    private static native void acceptAnswerNative(int id, String code);
    @JSBody(params = { "id", "data" }, script = "window.Mp.send(id, data);")
    private static native void sendNative(int id, Uint8Array data);
    @JSBody(params = { "id" }, script = "window.Mp.close(id);")
    private static native void closeNative(int id);
    @JSBody(params = { "id" }, script = "return window.Mp.isOpen(id);")
    private static native boolean isOpenNative(int id);

    // ------------------------------------------------------- буфер обмена

    public static void copy(String text) { copyNative(text); }

    /** Запросить вставку; результат — через consumePasted() в следующих тиках. */
    public static void requestPaste() {
        install();
        if (pasteBusy) return;
        pasteBusy = true;
        pasted = null;
        pasteNative();
    }

    /** Возвращает вставленный текст один раз (пустая строка = пользователь отменил) либо null, если ещё нет. */
    public static String consumePasted() {
        String p = pasted;
        pasted = null;
        return p;
    }

    @JSBody(params = { "text" }, script = "window.Mp.copy(text);")
    private static native void copyNative(String text);
    @JSBody(script = "window.Mp.paste();")
    private static native void pasteNative();
}
