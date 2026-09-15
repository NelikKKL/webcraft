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
 * Все ресурсы упакованы в один web/assets.akrile — собственный архивный
 * формат (см. PATCHES.md, "akrile — замена JSZip") вместо стандартного
 * .zip: WASM-библиотека `akrile` (ES-модуль, инициализируется и
 * прокидывается в window.Akrile из index.html ДО вызова window.main() —
 * см. index.html) с API, намеренно похожим на JSZip (loadAsync/forEach/
 * file.async), поэтому вся структура кода ниже почти не отличается от
 * версии с настоящим JSZip. PNG внутри архива декодируется браузером
 * (через createImageBitmap), а не Java-кодом — писать свой PNG-декодер
 * не нужно и не имеет смысла.
 *
 * Прогресс загрузки (0.0–1.0) прокидывается напрямую в DOM через
 * window.__setLoadingProgress (см. index.html) — 0–80% на скачивание
 * архива (по Content-Length, если сервер его отдаёт), 80–100% на
 * распаковку и декодирование картинок.
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
     * Скачивает web/assets.akrile, распаковывает через window.Akrile
     * (см. index.html — инициализируется до вызова main(), т.е. до
     * попадания сюда), декодирует каждый PNG и кладёт результат в
     * ResourceCache. onComplete вызывается один раз, когда все файлы
     * обработаны (отдельные ошибки декодирования отдельных файлов
     * логируются и пропускаются — не блокируют остальные).
     */
    public static void preloadAll(OnComplete onComplete) {
        ResourceReadyCallback onResource = ResourceCache::put;
        JsCallback onDone = onComplete::done;
        preloadArchiveNative(onResource, onDone);
    }

    @JSBody(params = { "onResource", "onDone" }, script =
        "function setProgress(f, text) { if (window.__setLoadingProgress) window.__setLoadingProgress(f, text); }" +
        "setProgress(0, 'Downloading assets\u2026');" +
        "fetch('assets.akrile').then(function(resp) {" +
        "  if (!resp.ok) { throw new Error('HTTP ' + resp.status + ' fetching assets.akrile'); }" +
        "  var total = parseInt(resp.headers.get('Content-Length') || '0', 10);" +
        "  if (!resp.body || !total) {" +
        // Нет доступа к потоковому чтению или сервер не прислал
        // Content-Length (например, сжатие на лету) — просто ждём весь
        // ответ целиком без промежуточного прогресса на этом этапе.
        "    return resp.arrayBuffer();" +
        "  }" +
        "  var reader = resp.body.getReader();" +
        "  var received = 0;" +
        "  var chunks = [];" +
        "  function pump() {" +
        "    return reader.read().then(function(result) {" +
        "      if (result.done) {" +
        "        var blob = new Blob(chunks);" +
        "        return blob.arrayBuffer();" +
        "      }" +
        "      chunks.push(result.value);" +
        "      received += result.value.length;" +
        "      setProgress(Math.min(0.8, received / total * 0.8), 'Downloading assets\u2026');" +
        "      return pump();" +
        "    });" +
        "  }" +
        "  return pump();" +
        "}).then(function(arrayBuffer) {" +
        "  setProgress(0.8, 'Unpacking assets\u2026');" +
        "  return window.Akrile.loadAsync(arrayBuffer);" +
        "}).then(function(zip) {" +
        "  var entries = [];" +
        // У AkrileFile нет свойства .dir (в отличие от JSZip) — папки в
        // архиве отличаются только именем, оканчивающимся на "/" (см.
        // zip-to-akrile.js: archive.folder(name) для директорий).
        "  zip.forEach(function(name, entry) { if (!name.endsWith('/')) entries.push(entry); });" +
        "  var total = entries.length;" +
        "  var done = 0;" +
        "  function next() {" +
        "    if (done >= total) { setProgress(1, 'Starting\u2026'); onDone(); return; }" +
        "    var entry = entries[done];" +
        "    entry.async('blob').then(function(blob) {" +
        "      return createImageBitmap(blob);" +
        "    }).then(function(bitmap) {" +
        "      var canvas = document.createElement('canvas');" +
        "      canvas.width = bitmap.width;" +
        "      canvas.height = bitmap.height;" +
        "      var ctx = canvas.getContext('2d');" +
        "      ctx.drawImage(bitmap, 0, 0);" +
        "      var imgData = ctx.getImageData(0, 0, bitmap.width, bitmap.height);" +
        "      onResource('/' + entry.name, bitmap.width, bitmap.height, new Uint8Array(imgData.data.buffer));" +
        "    }).catch(function(err) {" +
        "      console.warn('Resource decode failed (skipped):', entry.name, err);" +
        "    }).then(function() {" +
        "      done++;" +
        "      setProgress(0.8 + (done / total) * 0.2, 'Unpacking assets\u2026 (' + done + '/' + total + ')');" +
        "      next();" +
        "    });" +
        "  }" +
        "  next();" +
        "}).catch(function(err) {" +
        "  console.error('Fatal: failed to load assets.akrile', err);" +
        "  if (window.__showCrashScreen) {" +
        "    window.__showCrashScreen('Failed to load game assets:\\n' + (err && err.stack ? err.stack : String(err)));" +
        "  }" +
        "});")
    private static native void preloadArchiveNative(ResourceReadyCallback onResource, JsCallback onDone);
}
