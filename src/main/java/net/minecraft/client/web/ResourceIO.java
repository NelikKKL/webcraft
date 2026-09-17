package net.minecraft.client.web;

import java.io.IOException;
import java.io.InputStream;

/**
 * Замена вызовам вида `SomeClass.class.getResourceAsStream(path)` /
 * `SomeClass.class.getResource(path)` в оригинальном decomp-коде (см.
 * PATCHES.md — список всех патченных файлов и обоснование).
 *
 * ПОЧЕМУ ПАТЧИТЬ, А НЕ ПОЛАГАТЬСЯ НА java.lang.Class: поведение
 * Class.getResourceAsStream в TeaVM classlib для рантайм-путей не
 * гарантировано (и не проверяемо в этой среде разработки без сети/сборки)
 * — везде, где оригинал использует classpath-ресурсы, явно перенаправляем
 * на свой код с полностью предсказуемым поведением, завязанным на
 * ResourceCache (см. ResourcePreloader — синхронный доступ после
 * однократной асинхронной fetch-прелоадки перед стартом игры).
 *
 * Для картиночных ресурсов возвращается CachedImageInputStream — маркерный
 * подкласс InputStream, который наш ImageIO.read(InputStream) распознаёт и
 * разворачивает в BufferedImage напрямую из уже готовых ARGB-пикселей, без
 * реального байтового чтения (PNG уже декодирован в браузере на этапе
 * прелоадки — см. ResourcePreloader.java).
 */
public final class ResourceIO {

    private ResourceIO() {}

    /**
     * Для картиночных ресурсов (все реальные вызовы getResourceAsStream на
     * путях, которые в итоге идут в ImageIO.read — см. PATCHES.md). Если
     * ресурс не был прелоаднут (опечатка в пути / файл отсутствует), метод
     * не бросает исключение сразу — возвращает поток, чтение из которого
     * (через ImageIO.read) даст null, ровно как это ведёт себя
     * Class.getResourceAsStream(unknownPath) в реальной JVM (ImageIO.read
     * на null/пустом стриме также возвращает null или кидает IOException —
     * оригинальный decomp-код такие случаи уже оборачивает в try/catch).
     */
    public static InputStream getImageResourceAsStream(String path) {
        return new CachedImageInputStream(path);
    }

    /**
     * Для текстовых ресурсов (например, /title/splashes.txt), которые
     * вызывающий код читает побайтово через InputStreamReader/BufferedReader
     * — в отличие от картинок, здесь НУЖНО настоящее байтовое чтение, а не
     * просто маркер для ImageIO. Текст уже полностью прелоаднут (см.
     * ResourcePreloader — декодирован через TextDecoder на JS-стороне) и
     * лежит в ResourceCache как готовая Java String — здесь просто
     * заворачиваем её UTF-8 байты в обычный ByteArrayInputStream, который
     * поддерживает реальное чтение.
     *
     * Если ресурс не был прелоаднут (опечатка в пути / файл отсутствует),
     * возвращает null — ровно как ведёт себя Class.getResourceAsStream на
     * несуществующем пути в реальной JVM (вызывающий код уже оборачивает
     * такие случаи в try/catch, см. PATCHES.md).
     */
    public static InputStream getTextResourceAsStream(String path) {
        String text = ResourceCache.getText(path);
        if (text == null) {
            return null;
        }
        return new java.io.ByteArrayInputStream(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    /**
     * Маркерный InputStream для картинок — реальных байтов не содержит
     * (в этом нет нужды, см. javadoc класса), только путь для последующего
     * поиска в ResourceCache внутри ImageIO.read(InputStream).
     */
    public static final class CachedImageInputStream extends InputStream {
        public final String path;

        CachedImageInputStream(String path) {
            this.path = path;
        }

        @Override
        public int read() throws IOException {
            // Реальное байтовое чтение не поддерживается и не нужно — все
            // потребители этого потока идут через ImageIO.read(InputStream),
            // который распознаёт этот тип и не вызывает read() напрямую.
            // Если сюда всё же попал вызывающий код, ожидающий байты (не
            // через ImageIO), это баг интеграции — сообщаем явно, а не
            // молча возвращаем мусор.
            throw new IOException(
                "CachedImageInputStream поддерживает только чтение через ImageIO.read(InputStream) — " +
                "прямое байтовое чтение не реализовано (см. ResourceIO.java)");
        }

        @Override
        public void close() {
            // no-op: нет реального системного ресурса для освобождения.
        }
    }
}
