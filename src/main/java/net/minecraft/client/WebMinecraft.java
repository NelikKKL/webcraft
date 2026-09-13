package net.minecraft.client;

import org.teavm.jso.JSBody;

/**
 * Наш аналог оригинальных `i.java`/`StandaloneClient.java` (оба исключены
 * из сборки, см. PATCHES.md) — конкретная реализация абстрактного
 * `Minecraft.a(hr)` (обработка краша), но вместо AWT `CrashDialog`
 * показывает экран краша поверх страницы (см. index.html,
 * window.__showCrashScreen) с полным текстом ошибки и кнопкой копирования
 * — вместо тихого падения в консоль.
 */
public final class WebMinecraft extends Minecraft {

    public WebMinecraft(int width, int height, boolean fullscreen) {
        super(null, null, new MinecraftApplet(), width, height, fullscreen);
    }

    @Override
    public void a(hr crashReport) {
        String message = crashReport.a;
        Throwable cause = crashReport.b;
        StringBuilder sb = new StringBuilder();
        sb.append(message == null ? "Game crashed" : message);
        if (cause != null) {
            sb.append('\n').append(cause);
            for (StackTraceElement el : cause.getStackTrace()) {
                sb.append("\n    at ").append(el);
            }
        }
        String text = sb.toString();
        System.err.println(text);
        showCrashScreen(text);
    }

    @JSBody(params = { "text" }, script = "if (window.__showCrashScreen) window.__showCrashScreen(text);")
    private static native void showCrashScreen(String text);
}
