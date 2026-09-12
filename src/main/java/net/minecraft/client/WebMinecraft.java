package net.minecraft.client;

import org.teavm.jso.JSBody;

/**
 * Наш аналог оригинальных `i.java`/`StandaloneClient.java` (оба исключены
 * из сборки, см. PATCHES.md) — конкретная реализация абстрактного
 * `Minecraft.a(hr)` (обработка краша), но вместо AWT `CrashDialog` просто
 * выводит сообщение об ошибке в консоль браузера и на DOM-элемент
 * (#status, тот же, что использует WebEntryPoint для статуса загрузки).
 */
public final class WebMinecraft extends Minecraft {

    public WebMinecraft(int width, int height, boolean fullscreen) {
        super(null, null, new MinecraftApplet(), width, height, fullscreen);
    }

    @Override
    public void a(hr crashReport) {
        String message = crashReport.a;
        Throwable cause = crashReport.b;
        String stack = cause == null ? "" : stackTraceToString(cause);
        logCrash(message + "\n" + stack);
    }

    private static String stackTraceToString(Throwable t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.toString());
        for (StackTraceElement el : t.getStackTrace()) {
            sb.append("\n    at ").append(el.toString());
        }
        return sb.toString();
    }

    @JSBody(params = { "msg" }, script =
        "console.error('Minecraft crashed:', msg);" +
        "var el = document.getElementById('status');" +
        "if (el) { el.textContent = 'Game crashed — see console for details'; el.style.color = '#f88'; }")
    private static native void logCrash(String msg);
}
