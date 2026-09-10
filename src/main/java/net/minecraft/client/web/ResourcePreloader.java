package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Прелоадер ресурсов. Оригинальный decomp-код грузит текстуры синхронно
 * через ImageIO.read(getResourceAsStream(path)) в произвольный момент
 * (конструкторы мобов, TextureManager и т.д.) — в браузере эквивалент
 * (fetch) асинхронный. Решение: до старта игрового цикла (до вызова
 * настоящего Minecraft-кода) прогружаем ВСЕ известные PNG-ресурсы разом
 * через fetch()+decode на JS-стороне, складываем результат в Java-side
 * ResourceCache (см. ResourceCache.java) как уже готовые RGBA-байты.
 * После этого java.awt.image.BufferedImage / javax.imageio.ImageIO шимы
 * читают из ResourceCache синхронно — ровно так, как ожидает decomp-код,
 * без единой правки в игровой логике.
 *
 * PNG декодируется браузером (через <img>/createImageBitmap), а не
 * Java-кодом — писать свой PNG-декодер (zlib inflate + PNG-фильтры) не
 * нужно и не имеет смысла, раз браузер это уже умеет быстро и надёжно.
 */
public final class ResourcePreloader {

    private ResourcePreloader() {}

    public interface OnComplete {
        void done();
    }

    @JSFunctor
    interface JsCallback extends JSObject {
        void call();
    }

    @JSFunctor
    interface ResourceReadyCallback extends JSObject {
        /** Java получает путь + ширину/высоту + сырые RGBA байты одной картинки. */
        void onResource(String path, int width, int height, Uint8Array rgba);
    }

    /**
     * Запускает загрузку всех путей из ResourceManifest.ALL_PATHS. Для
     * каждого успешно загруженного файла кладёт RGBA-байты в ResourceCache
     * (через onResource -> ResourceCache.put), затем один раз вызывает
     * onComplete, когда ВСЕ fetch завершились (успешно или с ошибкой —
     * отсутствующий файл не должен блокировать остальные навсегда;
     * ошибки логируются в консоль и пропускаются, отсутствующий ресурс
     * будет просто недоступен через ResourceCache, как если бы файла не
     * было на classpath в оригинале).
     */
    public static void preloadAll(String[] paths, OnComplete onComplete) {
        ResourceReadyCallback onResource = ResourceCache::put;
        JsCallback onDone = onComplete::done;
        preloadAllNative(paths, onResource, onDone);
    }

    @JSBody(params = { "paths", "onResource", "onDone" }, script =
        "var remaining = paths.length;" +
        "if (remaining === 0) { onDone.call(); return; }" +
        "function finishOne() {" +
        "  remaining--;" +
        "  if (remaining <= 0) onDone.call();" +
        "}" +
        "paths.forEach(function(path) {" +
        "  fetch('assets' + path).then(function(resp) {" +
        "    if (!resp.ok) { throw new Error('HTTP ' + resp.status); }" +
        "    return resp.blob();" +
        "  }).then(function(blob) {" +
        "    return createImageBitmap(blob);" +
        "  }).then(function(bitmap) {" +
        "    var canvas = document.createElement('canvas');" +
        "    canvas.width = bitmap.width;" +
        "    canvas.height = bitmap.height;" +
        "    var ctx = canvas.getContext('2d');" +
        "    ctx.drawImage(bitmap, 0, 0);" +
        "    var imgData = ctx.getImageData(0, 0, bitmap.width, bitmap.height);" +
        "    onResource.onResource(path, bitmap.width, bitmap.height, new Uint8Array(imgData.data.buffer));" +
        "    finishOne();" +
        "  }).catch(function(err) {" +
        "    console.warn('Resource preload failed (skipped): assets' + path, err);" +
        "    finishOne();" +
        "  });" +
        "});")
    private static native void preloadAllNative(String[] paths, ResourceReadyCallback onResource, JsCallback onDone);
}
