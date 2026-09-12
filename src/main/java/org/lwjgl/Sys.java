package org.lwjgl;

import org.teavm.jso.JSBody;

/**
 * Шим org.lwjgl.Sys. Единственный реально используемый метод во всём
 * decomp-дереве (проверено grep) — openURL, вызывается из экрана выбора
 * текстур-пака ("Open texture pack folder"). В десктопной версии это
 * открывает системный файловый менеджер на `file://` пути — не имеет
 * смысла в браузере (нет доступа к локальной ФС пользователя за пределами
 * File API с явным выбором файла пользователем). Для НЕ-file:// URL
 * (обычные http/https ссылки, если когда-либо понадобятся) — пытаемся
 * открыть в новой вкладке через window.open; для file:// — no-op с логом
 * в консоль (нет доступного действия в браузере).
 */
public final class Sys {
    private Sys() {}

    public static boolean openURL(String url) {
        if (url.startsWith("file://")) {
            logUnsupported(url);
            return false;
        }
        return openInNewTab(url);
    }

    @JSBody(params = { "url" }, script = "window.open(url, '_blank'); return true;")
    private static native boolean openInNewTab(String url);

    @JSBody(params = { "url" }, script =
        "console.warn('Sys.openURL: file:// paths are not accessible from a browser:', url);")
    private static native void logUnsupported(String url);
}
