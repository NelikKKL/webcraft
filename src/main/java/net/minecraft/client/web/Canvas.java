package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.webgl.WebGLRenderingContext;

/**
 * Единая точка доступа к <canvas> и WebGL2-контексту.
 *
 * Держим здесь только "сырое" получение контекста и размеров — вся логика
 * стека матриц / fixed-function эмуляции живёт в org.lwjgl.opengl.GL11
 * (см. GLState). Этот класс не должен знать про Minecraft-специфику.
 *
 * TeaVM jso-apis пока не имеет полноценных типов для WebGL2RenderingContext,
 * поэтому получаем контекст через generic @JSBody и приводим позже к
 * WebGL2RenderingContext через отдельный interop-интерфейс (WebGL2.java).
 */
public final class Canvas {

    private Canvas() {}

    @JSBody(params = {}, script = "return document.getElementById('glcanvas');")
    public static native HTMLCanvasElement get();

    @JSBody(params = { "canvas" }, script =
        "var gl = canvas.getContext('webgl2', {" +
        "  alpha: false," +
        "  antialias: false," +
        "  depth: true," +
        "  stencil: false," +
        "  preserveDrawingBuffer: false," +
        "  powerPreference: 'high-performance'" +
        "});" +
        "if (!gl) { throw new Error('WebGL2 not supported'); }" +
        "return gl;")
    public static native WebGL2 getContext(HTMLCanvasElement canvas);

    @JSBody(params = { "canvas" }, script = "return canvas.width;")
    public static native int width(HTMLCanvasElement canvas);

    @JSBody(params = { "canvas" }, script = "return canvas.height;")
    public static native int height(HTMLCanvasElement canvas);

    @JSBody(params = { "canvas", "w", "h" }, script =
        "canvas.width = w; canvas.height = h;")
    public static native void setSize(HTMLCanvasElement canvas, int w, int h);

    @JSBody(params = {}, script =
        "return window.devicePixelRatio || 1;")
    public static native double devicePixelRatio();

    /**
     * Запрос Pointer Lock — нужен для Mouse.setGrabbed(true) (обзор камерой).
     *
     * ИСПРАВЛЕНО: движок вызывает setGrabbed(true) из своего игрового цикла
     * (requestAnimationFrame), а не напрямую из DOM-обработчика клика — браузер
     * расценивает это как "not called from inside a short running
     * user-generated event handler" и молча/с ошибкой отклоняет запрос
     * (Promise из requestPointerLock() отклоняется с NotAllowedError, и если
     * его не поймать — вылетает "Unhandled promise rejection" в консоль).
     *
     * Ловим и глушим этот reject здесь (первая попытка почти всегда мимо),
     * и дополнительно выставляем флаг window.__wantsPointerLock — настоящий
     * повторный запрос уходит из MouseBridge-обработчика canvas.mousedown
     * (см. MouseBridge.java), который ВСЕГДА является настоящим user gesture,
     * так что следующий же клик по канвасу реально захватывает указатель.
     */
    @JSBody(params = { "canvas" }, script =
        "window.__wantsPointerLock = true;" +
        "if (document.pointerLockElement !== canvas) {" +
        "  var p = canvas.requestPointerLock();" +
        "  if (p && p.catch) { p.catch(function(e) {}); }" +
        "}")
    public static native void requestPointerLock(HTMLCanvasElement canvas);

    @JSBody(params = {}, script =
        "window.__wantsPointerLock = false;" +
        "document.exitPointerLock();")
    public static native void exitPointerLock();

    @JSBody(params = { "canvas" }, script =
        "return document.pointerLockElement === canvas;")
    public static native boolean isPointerLocked(HTMLCanvasElement canvas);
}
