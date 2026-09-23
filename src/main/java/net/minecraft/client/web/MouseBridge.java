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
        // ИСПРАВЛЕНО: канвас растянут CSS на 100vw/100vh (см. index.html),
        // а внутреннее разрешение буфера фиксировано (854x480, задаётся
        // Canvas.resize). getBoundingClientRect() даёт РАЗМЕР НА ЭКРАНЕ
        // (CSS-пиксели), который почти всегда отличается от canvas.width/
        // canvas.height — без пересчёта клики регистрировались в неверном
        // месте (курсор и точка клика визуально расходились). scaleX/scaleY
        // переводят CSS-координаты клика в пространство внутреннего буфера.
        "function toCanvasX(clientX, rect) { return ((clientX - rect.left) * (canvas.width / rect.width)) | 0; }" +
        "function toCanvasY(clientY, rect) { return (flipY((clientY - rect.top) * (canvas.height / rect.height))) | 0; }" +
        // curX/curY отслеживают актуальную позицию курсора — используется
        // как фолбэк в mouseup (который навешан на window и не знает rect).
        "var curX = 0, curY = 0;" +
        "canvas.addEventListener('mousemove', function(e) {" +
        "  var rect = canvas.getBoundingClientRect();" +
        "  curX = toCanvasX(e.clientX, rect);" +
        "  curY = toCanvasY(e.clientY, rect);" +
        "  var dx = e.movementX || 0;" +
        "  var dy = -(e.movementY || 0);" +
        "  handler(0, curX, curY, dx|0, dy|0, -1, 0);" +
        "}, false);" +
        // ИСПРАВЛЕНО (клики): оригинальный код слал (0,0) вместо реальной
        // позиции. bp.java использует Mouse.getEventX/Y() из самого
        // click-события (не из mousemove) для определения нажатой кнопки
        // GUI — без позиции все клики регистрировались в углу (0,0) и
        // никогда не попадали в область кнопки. Теперь координаты идут
        // через тот же rect+scale пересчёт, что и mousemove.
        "canvas.addEventListener('mousedown', function(e) {" +
        "  canvas.focus();" +
        "  var rect = canvas.getBoundingClientRect();" +
        "  curX = toCanvasX(e.clientX, rect);" +
        "  curY = toCanvasY(e.clientY, rect);" +
        "  handler(1, curX, curY, 0, 0, e.button, 0);" +
        // Повторная попытка Pointer Lock: этот mousedown — настоящий,
        // синхронный user gesture, поэтому здесь запрос точно пройдёт
        // (см. комментарий в Canvas.requestPointerLock).
        "  if (window.__wantsPointerLock && document.pointerLockElement !== canvas) {" +
        "    var p = canvas.requestPointerLock();" +
        "    if (p && p.catch) { p.catch(function(e2) {}); }" +
        "  }" +
        "  e.preventDefault();" +
        "}, false);" +
        // mouseup: навешан на window (чтобы ловить отпускание за пределами
        // canvas). Позиция curX/curY уже актуальна от предыдущего mousemove.
        "window.addEventListener('mouseup', function(e) {" +
        "  handler(2, curX, curY, 0, 0, e.button, 0);" +
        "}, false);" +
        "canvas.addEventListener('wheel', function(e) {" +
        "  var w = e.deltaY < 0 ? 120 : -120;" +
        "  handler(3, 0, 0, 0, 0, -1, w);" +
        "  e.preventDefault();" +
        "}, { passive: false });" +
        "canvas.addEventListener('contextmenu', function(e) { e.preventDefault(); }, false);" +
        "canvas.tabIndex = 0;")
    private static native void installListeners(HTMLCanvasElement canvas, DomMouseHandler handler);
}
