package net.minecraft.client;

import net.minecraft.client.web.ResourcePreloader;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

/**
 * Реальная точка входа веб-порта. Последовательность запуска:
 *  1. ResourcePreloader.preloadAll(...) — асинхронно скачивает
 *     web/assets.zip (все игровые текстуры одним архивом), распаковывает
 *     через JSZip и декодирует каждую картинку в браузере — см.
 *     ResourcePreloader/ResourceCache/javax.imageio.ImageIO-шим.
 *     Прогресс отображается на экране загрузки (см. index.html,
 *     window.__setLoadingProgress).
 *  2. По завершении прелоадки — создаём WebMinecraft (наш аналог
 *     i.java/StandaloneClient.java, см. PATCHES.md) и вызываем init()
 *     (разовая инициализация — Display.create(), загрузка текстур,
 *     шрифта и т.д., см. Minecraft.a()).
 *  3. Запускаем игровой цикл через requestAnimationFrame, вызывая
 *     WebMinecraft.runOneFrame() на каждый кадр (см. PATCHES.md — почему
 *     оригинальный блокирующий run()-with-while был разбит на
 *     init()+runOneFrame()).
 *
 * Любая ошибка на любом из этапов ведёт на экран краша (см.
 * window.__showCrashScreen в index.html) с полным стектрейсом и кнопкой
 * копирования — вместо тихого зависания на экране загрузки.
 */
public final class WebEntryPoint {

    private static WebMinecraft minecraft;

    public static void main(String[] args) {
        ResourcePreloader.preloadAll(WebEntryPoint::startGame);
    }

    private static void startGame() {
        try {
            // 854x480 — то же разрешение по умолчанию, что и в оригинальном
            // desktop-лаунчере (см. исключённый StandaloneClient.java);
            // fullscreen=false — обычный canvas фиксированного размера.
            minecraft = new WebMinecraft(854, 480, false);
            minecraft.init();
        } catch (Throwable t) {
            showCrash("Failed to start game", t);
            return;
        }
        hideLoadingScreen();
        requestFrame(WebEntryPoint::frame);
    }

    private static void frame(double timestampMs) {
        try {
            minecraft.runOneFrame();
        } catch (Throwable t) {
            showCrash("Unexpected error in game loop", t);
            return;
        }
        if (minecraft.isRunning()) {
            requestFrame(WebEntryPoint::frame);
        }
    }

    private static void showCrash(String title, Throwable t) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(": ").append(t).append('\n');
        for (StackTraceElement el : t.getStackTrace()) {
            sb.append("    at ").append(el).append('\n');
        }
        t.printStackTrace();
        showCrashScreen(sb.toString());
    }

    // --- JS interop ---

    @JSFunctor
    interface FrameCallback extends JSObject {
        void run(double timestampMs);
    }

    @JSBody(params = { "cb" }, script = "window.requestAnimationFrame(function(t) { cb(t); });")
    private static native void requestFrame(FrameCallback cb);

    @JSBody(params = {}, script = "if (window.__hideLoadingScreen) window.__hideLoadingScreen();")
    private static native void hideLoadingScreen();

    @JSBody(params = { "text" }, script = "if (window.__showCrashScreen) window.__showCrashScreen(text);")
    private static native void showCrashScreen(String text);
}
