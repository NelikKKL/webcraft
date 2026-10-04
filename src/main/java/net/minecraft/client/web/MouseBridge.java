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

    // Курсор-стрелка, зашитая прямо в код (раньше — отдельный файл cursor.png).
    // Исходник 32x47 был сильно больше системной стрелки (~12x19 CSS px), поэтому
    // здесь он уменьшен до 14x20 (1x) и 28x40 (2x, для HiDPI-экранов — через
    // CSS image-set браузер показывает его всё равно как 14x20 CSS px).
    // Hotspot (1,1) — кончик стрелки.
    private static final String CURSOR_1X = "iVBORw0KGgoAAAANSUhEUgAAAA4AAAAUCAYAAAC9BQwsAAACwElEQVR42o3TPUibQRzH8d9zd88Tnzw0lCyFOkhBcGpdujg5FTFoasEnVAJ9IIGgFAu6OJ4J2EkCxUJrLEqxbUriUHA0Ojg4uKlY1HQTU1ExGB5fmuR5/h3qS/ruzfe5+8N9DwDY7u7uXQBCSilM0+S47qpUKhMnJydJAJBS1l0bHx0dvSIiOj09fQ5AsSzrevjs7OxNIpFw5ufniYheAGD9/f2ef2EGAKqqMtu2WWdnZ3l1dfUZEb0cGxtzAPC/YQYAjuPAMAxwznkwGKwuLy/3EdHrbDYLALy1tVX8EQIAEcHr9cLj8Yju7m5naWkpSkST2WyWNTU1Kb/iS8g5B2MMuq7DMAze09PjLC4uhonobSqVUm3bVqSU4jfIGIMQAoqiwOPxwOfzccuyqgsLCyEiemfbdl08HldisZh6CV3XBeccQggIIcAYg6Zp8Pv9ore3tzo3N/doY2Pjo2ma3lQq5UopxU+jCiGgaRpUVYUQAkQEXddFNBqtzMzMBNLpdHZ6etqIx+NVUTuqpmkol8uk/FgYHBxEQ0MDOY6jlstlcM4fhEKhT+3t7U8uoaZpKBaLFA6Hlfr6egwPD7uO47Dm5uaNZDL5wefzsZWVla/BYHDfMIzLVicTiYQ7MDBApVLptFAofGtra3MDgYC7s7NTjEQi9wHcAGD89JgHBweptbU1KhQKRcuynubz+dzU1BQ1NjaWzzMcAYCtrS1PJpPhRKQAAEql0vvj4+PjaDRqAfDNzs5G8vm829LSUunr63OLxeKXiYmJW6ZpXiEAyv7+/tDIyMhjAN5YLObVdf324eHh59HRUero6KC9vb1qLpe7d14Yq41ABcCllGJ8fFwFINLpdGx7e9ve3Nyk9fX1tN/v92UyGQ7g6kYiUmpOuujyZldX18OhoaEIgDsAVCkl+98XvehSA6CdZ6bUbvgOMoVGqq8GsUUAAAAASUVORK5CYII=";
    private static final String CURSOR_2X = "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";

    @JSBody(params = { "canvas", "x1", "x2" }, script =
        "var u1 = 'url(data:image/png;base64,' + x1 + ')';" +
        "var u2 = 'url(data:image/png;base64,' + x2 + ')';" +
        "var candidates = [" +
        "  'image-set(' + u1 + ' 1x, ' + u2 + ' 2x) 1 1, auto'," +
        "  '-webkit-image-set(' + u1 + ' 1x, ' + u2 + ' 2x) 1 1, auto'," +
        "  u1 + ' 1 1, auto'" +
        "];" +
        "for (var i = 0; i < candidates.length; i++) {" +
        "  canvas.style.cursor = '';" +
        "  canvas.style.cursor = candidates[i];" +
        "  if (canvas.style.cursor) break;" +   // браузер принял значение
        "}")
    private static native void applyCursor(HTMLCanvasElement canvas, String x1, String x2);

    public static void install(MouseCallback cb) {
        callback = cb;
        DomMouseHandler handler = (type, x, y, dx, dy, button, wheel) -> callback.onEvent(type, x, y, dx, dy, button, wheel);
        HTMLCanvasElement canvas = Canvas.get();
        applyCursor(canvas, CURSOR_1X, CURSOR_2X);
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
        // ИСПРАВЛЕНО (правая кнопка не ставила блоки): в DOM e.button: 0=левая,
        // 1=СРЕДНЯЯ, 2=ПРАВАЯ, а в LWJGL (и в decomp Minecraft.java) 0=левая,
        // 1=ПРАВАЯ, 2=средняя. Без перевода ПКМ приходил как '2' (средняя)
        // и вместо установки блока срабатывал pick-block (t()).
        "function mapButton(b) { return b === 1 ? 2 : (b === 2 ? 1 : b); }" +
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
        "  handler(1, curX, curY, 0, 0, mapButton(e.button), 0);" +
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
        "  handler(2, curX, curY, 0, 0, mapButton(e.button), 0);" +
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
