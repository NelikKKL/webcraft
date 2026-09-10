package org.lwjgl.opengl;

import net.minecraft.client.web.Canvas;
import net.minecraft.client.web.GLState;
import net.minecraft.client.web.WebGL2;
import org.lwjgl.LWJGLException;
import org.teavm.jso.dom.html.HTMLCanvasElement;

/**
 * Шим org.lwjgl.opengl.Display. В десктопном LWJGL это реальное OS-окно;
 * здесь — единственный <canvas id="glcanvas"> элемент на странице (см.
 * web/index.html). "Fullscreen" реализуем как canvas, растянутый на весь
 * viewport через CSS + синхронизация canvas.width/height с window
 * innerWidth/innerHeight при resize.
 *
 * Display.create() — точка, где реально инициализируется WebGL2-контекст
 * и создаётся GLState.INSTANCE (единственный на всё приложение) — именно
 * поэтому GL11.* методы работать не будут ДО вызова Display.create(),
 * ровно как и в настоящем LWJGL.
 */
public final class Display {
    private Display() {}

    private static HTMLCanvasElement canvas;
    private static boolean created = false;
    private static boolean fullscreen = false;
    private static DisplayMode currentMode = new DisplayMode(854, 480);
    private static String title = "";
    private static boolean closeRequested = false;
    private static boolean vsync = true;

    // --- API, повторяющее сигнатуры реального LWJGL Display ---

    public static void setParent(java.awt.Canvas parentCanvas) {
        // В десктопной версии это встраивание в AWT-канвас апплета.
        // В веб-версии canvas уже находится в DOM статически (index.html) —
        // здесь ничего дополнительно встраивать не нужно, метод существует
        // только чтобы вызывающий decomp-код компилировался без изменений.
    }

    public static void setFullscreen(boolean value) {
        fullscreen = value;
        if (value) {
            resizeToWindow();
        }
    }

    public static boolean isFullscreen() { return fullscreen; }

    public static void setDisplayMode(DisplayMode mode) {
        currentMode = mode;
        if (canvas != null) {
            Canvas.setSize(canvas, mode.getWidth(), mode.getHeight());
        }
    }

    public static DisplayMode getDisplayMode() { return currentMode; }

    public static DisplayMode getDesktopDisplayMode() {
        return new DisplayMode(windowWidth(), windowHeight());
    }

    public static void setTitle(String newTitle) {
        title = newTitle;
        setDocumentTitle(newTitle);
    }

    public static void create() throws LWJGLException {
        if (created) return;
        canvas = Canvas.get();
        if (canvas == null) {
            throw new LWJGLException("No <canvas id=\"glcanvas\"> found in the page");
        }
        if (fullscreen) {
            resizeToWindow();
        } else {
            Canvas.setSize(canvas, currentMode.getWidth(), currentMode.getHeight());
        }
        WebGL2 gl = Canvas.getContext(canvas);
        GLState.INSTANCE = new GLState(gl);
        gl.viewport(0, 0, Canvas.width(canvas), Canvas.height(canvas));
        created = true;
    }

    public static boolean isCreated() { return created; }

    public static void destroy() {
        created = false;
        // Реальное освобождение WebGL-контекста не требуется — страница,
        // как правило, перезагружается целиком при повторном запуске.
    }

    /**
     * В реальном LWJGL это своп буферов (двойная буферизация) — в WebGL
     * буфер меняется браузером автоматически в конце кадра requestAnimationFrame,
     * поэтому явного действия не требуется. Оставлено как no-op ради
     * совместимости сигнатур с decomp-кодом (см. Minecraft.java:238).
     */
    public static void swapBuffers() {
        // no-op: см. javadoc выше.
    }

    /**
     * В реальном LWJGL update() обрабатывает оконные сообщения ОС и меняет
     * буферы. Здесь используем этот вызов как точку синхронизации размера
     * canvas с окном браузера в fullscreen-режиме (аналог того, как в
     * decomp-коде проверяется this.k.getWidth()/getHeight() каждый кадр).
     */
    public static void update() {
        if (fullscreen) {
            resizeToWindow();
        }
    }

    public static boolean isActive() {
        return !documentHidden();
    }

    public static boolean isCloseRequested() {
        return closeRequested;
    }

    public static void setVSyncEnabled(boolean sync) {
        vsync = sync;
        // requestAnimationFrame по своей природе синхронизирован с частотой
        // обновления экрана — явного управления vsync в браузере нет.
    }

    public static int getWidth() { return canvas == null ? currentMode.getWidth() : Canvas.width(canvas); }
    public static int getHeight() { return canvas == null ? currentMode.getHeight() : Canvas.height(canvas); }

    // --- внутреннее ---

    private static void resizeToWindow() {
        int w = windowWidth();
        int h = windowHeight();
        Canvas.setSize(canvas, w, h);
        currentMode = new DisplayMode(w, h);
        if (GLState.INSTANCE != null) {
            GLState.INSTANCE.gl.viewport(0, 0, w, h);
        }
    }

    @org.teavm.jso.JSBody(params = {}, script = "return window.innerWidth;")
    private static native int windowWidth();

    @org.teavm.jso.JSBody(params = {}, script = "return window.innerHeight;")
    private static native int windowHeight();

    @org.teavm.jso.JSBody(params = {}, script = "return document.hidden;")
    private static native boolean documentHidden();

    @org.teavm.jso.JSBody(params = { "t" }, script = "document.title = t;")
    private static native void setDocumentTitle(String t);
}
