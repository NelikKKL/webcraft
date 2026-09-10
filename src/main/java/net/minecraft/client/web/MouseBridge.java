package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.html.HTMLCanvasElement;

/**
 * Мост между DOM mouse-событиями и org.lwjgl.input.Mouse.
 *
 * Использует Pointer Lock API для grabbed-режима (обзор камерой без выхода
 * курсора за пределы окна — это то, что делает setGrabbed(true) в реальном
 * LWJGL на десктопе). В режиме pointer lock движение мыши идёт через
 * MouseEvent.movementX/Y (относительные дельты, не зависящие от границ
 * экрана) — это именно то, что нужно getDX()/getDY().
 */
public final class MouseBridge {

    private MouseBridge() {}

    /** type: 0=move, 1=down, 2=up, 3=wheel. */
    public interface MouseCallback {
        void onEvent(int type, int x, int y, int dx, int dy, int button, int wheel);
    }

    @JSFunctor
    interface DomMouseHandler extends JSObject {
        void handle(int type, int x, int y, int dx, int dy, int button, int wheel);
    }

    private static MouseCallback callback;

    public static void install(MouseCallback cb) {
        callback = cb;
        DomMouseHandler handler = (type, x, y, dx, dy, button, wheel) -> callback.onEvent(type, x, y, dx, dy, button, wheel);
        HTMLCanvasElement canvas = Canvas.get();
        installListeners(canvas, handler);
    }

    public static void setGrabbed(boolean grabbed) {
        HTMLCanvasElement canvas = Canvas.get();
        if (grabbed) {
            Canvas.requestPointerLock(canvas);
        } else {
            Canvas.exitPointerLock();
        }
    }

    /**
     * DOM Y растёт вниз, LWJGL/OpenGL Y растёт вверх — конвертируем здесь
     * через canvas.height, чтобы вызывающий Java-код (Mouse.java) получал
     * координаты в уже привычной GL-подобной системе, как ожидает decomp.
     */
    @JSBody(params = { "canvas", "handler" }, script =
        "function flipY(y) { return canvas.height - y; }" +
        "canvas.addEventListener('mousemove', function(e) {" +
        "  var rect = canvas.getBoundingClientRect();" +
        "  var x = e.clientX - rect.left;" +
        "  var y = flipY(e.clientY - rect.top);" +
        "  var dx = e.movementX || 0;" +
        "  var dy = -(e.movementY || 0);" +
        "  handler.handle(0, x|0, y|0, dx|0, dy|0, -1, 0);" +
        "}, false);" +
        "canvas.addEventListener('mousedown', function(e) {" +
        "  canvas.focus();" +
        "  handler.handle(1, 0, 0, 0, 0, e.button, 0);" +
        "  e.preventDefault();" +
        "}, false);" +
        "window.addEventListener('mouseup', function(e) {" +
        "  handler.handle(2, 0, 0, 0, 0, e.button, 0);" +
        "}, false);" +
        "canvas.addEventListener('wheel', function(e) {" +
        "  var w = e.deltaY < 0 ? 120 : -120;" +
        "  handler.handle(3, 0, 0, 0, 0, -1, w);" +
        "  e.preventDefault();" +
        "}, { passive: false });" +
        "canvas.addEventListener('contextmenu', function(e) { e.preventDefault(); }, false);" +
        "canvas.tabIndex = 0;")
    private static native void installListeners(HTMLCanvasElement canvas, DomMouseHandler handler);
}
