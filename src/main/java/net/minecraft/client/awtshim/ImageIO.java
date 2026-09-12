package net.minecraft.client.awtshim;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.web.ResourceCache;
import net.minecraft.client.web.ResourceIO;

/**
 * Замена настоящему javax.imageio.ImageIO (PNG-декодер и т.п. не
 * реализуются в Java-коде — не нужно и не имеет смысла в браузере, где
 * есть нативный, быстрый декодер изображений). PNG уже декодирован в
 * браузере ДО того, как игровой код первый раз обращается к ImageIO — см.
 * ResourcePreloader.java (fetch + createImageBitmap + canvas getImageData,
 * результат лежит в ResourceCache).
 *
 * read(String) — НОВАЯ форма, не существующая в реальном javax.imageio.
 * ImageIO. Используется вместо read(URL) в патченных вызовах вида
 * `ImageIO.read(SomeClass.class.getResource("/path"))` →
 * `ImageIO.read("/path")` (см. PATCHES.md) — устраняет необходимость
 * эмулировать java.net.URL ради единственного случая использования.
 *
 * read(InputStream) — для вызовов через d.java/ResourceIO
 * (`ImageIO.read(fu.class.getResourceAsStream(string))`-подобные места) —
 * распознаёт ResourceIO.CachedImageInputStream и делегирует в read(String).
 */
public final class ImageIO {

    private ImageIO() {}

    public static BufferedImage read(String resourcePath) throws IOException {
        ResourceCache.Entry entry = ResourceCache.get(resourcePath);
        if (entry == null) {
            // Соответствует поведению реального ImageIO.read на отсутствующем
            // ресурсе (IOException) — большинство decomp call-сайтов уже
            // оборачивают ImageIO.read в try/catch (Exception).
            throw new IOException("Ресурс не найден в ResourceCache (не был прелоаднут?): " + resourcePath);
        }
        // Клонируем ARGB-массив: разные независимые чтения одного и того же
        // файла НЕ должны делить один и тот же изменяемый массив — decomp-код
        // местами мутирует полученный BufferedImage напрямую через
        // getRaster().getDataBuffer() (см. lf.java) или setRGB.
        return BufferedImage.wrapArgb(entry.width, entry.height, entry.argb.clone());
    }

    public static BufferedImage read(InputStream stream) throws IOException {
        if (stream instanceof ResourceIO.CachedImageInputStream) {
            return read(((ResourceIO.CachedImageInputStream) stream).path);
        }
        throw new IOException(
            "ImageIO.read(InputStream) в веб-порте поддерживает только потоки от " +
            "ResourceIO.getImageResourceAsStream(...) — см. PATCHES.md");
    }
}
