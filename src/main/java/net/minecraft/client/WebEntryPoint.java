package net.minecraft.client;

import net.minecraft.client.web.ResourcePreloader;
import net.minecraft.client.web.ResourceManifest;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

/**
 * Реальная точка входа веб-порта. Последовательность запуска:
 *  1. ResourcePreloader.preloadAll(...) — асинхронно грузит все 44
 *     картиночных ресурса (fetch + decode браузером), см.
 *     ResourceManifest/ResourceCache/javax.imageio.ImageIO-шим.
 *  2. По завершении прелоадки — создаём WebMinecraft (наш аналог
 *     i.java/StandaloneClient.java, см. PATCHES.md) и вызываем init()
 *     (разовая инициализация — Display.create(), загрузка текстур,
 *     шрифта и т.д., см. Minecraft.a()).
 *  3. Запускаем игровой цикл через requestAnimationFrame, вызывая
 *     WebMinecraft.runOneFrame() на каждый кадр (см. PATCHES.md — почему
 *     оригинальный блокирующий run()-with-while был разбит на
 *     init()+runOneFrame()).
 *
 * Предыдущая версия этого файла (смоук-тест GL-моста — вращающиеся кубы,
 * текстурированный пол) была временной, использовалась для проверки
 * GL11/Display/Keyboard/Mouse ДО того, как реальное decomp-дерево было
 * скопировано в проект — сохранена в истории git на случай регрессий.
 */
public final class WebEntryPoint {

    private static WebMinecraft minecraft;

    public static void main(String[] args) {
        logStatus("Preloading resources…");
        ResourcePreloader.preloadAll(ResourceManifest.IMAGE_PATHS, WebEntryPoint::startGame);
    }

    private static void startGame() {
        logStatus("Starting game…");
        try {
            // 854x480 — то же разрешение по умолчанию, что и в оригинальном
            // desktop-лаунчере (см. исключённый StandaloneClient.java);
            // fullscreen=false — обычный canvas фиксированного размера.
            minecraft = new WebMinecraft(854, 480, false);
            minecraft.init();
        } catch (Exception e) {
            e.printStackTrace();
            logStatus("Failed to start: " + e);
            return;
        }
        logStatus(""); // очищаем статус-оверлей — игра сама рисует свой UI
        requestFrame(WebEntryPoint::frame);
    }

    private static void frame(double timestampMs) {
        minecraft.runOneFrame();
        if (minecraft.isRunning()) {
            requestFrame(WebEntryPoint::frame);
        } else {
            logStatus("Game stopped.");
        }
    }

    // --- JS interop ---

    @JSFunctor
    interface FrameCallback extends JSObject {
        void run(double timestampMs);
    }

    @JSBody(params = { "cb" }, script = "window.requestAnimationFrame(function(t) { cb.run(t); });")
    private static native void requestFrame(FrameCallback cb);

    @JSBody(params = { "msg" }, script =
        "console.log(msg); var el = document.getElementById('status'); if (el) el.textContent = msg;")
    private static native void logStatus(String msg);
}
