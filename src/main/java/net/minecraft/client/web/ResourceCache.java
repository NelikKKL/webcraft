package net.minecraft.client.web;

import org.teavm.jso.typedarrays.Uint8Array;

import java.util.HashMap;
import java.util.Map;

/**
 * Синхронный кэш "готовых" изображений, заполняется один раз при старте
 * (см. ResourcePreloader) до того, как игровой код впервые обратится к
 * ImageIO/getResourceAsStream. После заполнения все обращения — это
 * простой синхронный Map.get(), что и требуется для совместимости с
 * decomp-кодом, написанным в расчёте на синхронную загрузку с classpath.
 */
public final class ResourceCache {

    private ResourceCache() {}

    public static final class Entry {
        public final int width;
        public final int height;
        /** Packed ARGB (0xAARRGGBB на пиксель), формат, который java.awt.image.BufferedImage.getRGB/setRGB используют по контракту. */
        public final int[] argb;

        Entry(int width, int height, int[] argb) {
            this.width = width;
            this.height = height;
            this.argb = argb;
        }
    }

    private static final Map<String, Entry> cache = new HashMap<>();

    /**
     * Вызывается из ResourcePreloader (через JS) один раз на каждый успешно
     * загруженный файл. rgba — Uint8Array из Canvas ImageData: 4 байта на
     * пиксель в порядке R,G,B,A (НЕ путать с Java-порядком ARGB!) —
     * конвертация в packed ARGB происходит здесь.
     */
    static void put(String path, int width, int height, Uint8Array rgba) {
        int[] argb = new int[width * height];
        for (int i = 0; i < argb.length; i++) {
            int base = i * 4;
            int r = rgba.get(base) & 0xFF;
            int g = rgba.get(base + 1) & 0xFF;
            int b = rgba.get(base + 2) & 0xFF;
            int a = rgba.get(base + 3) & 0xFF;
            argb[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
        cache.put(normalize(path), new Entry(width, height, argb));
    }

    /** Возвращает запись по пути ресурса (тот же формат, что передаётся в getResource/getResourceAsStream) либо null, если ресурс не был прелоаднут/не найден. */
    public static Entry get(String path) {
        return cache.get(normalize(path));
    }

    public static boolean has(String path) {
        return cache.containsKey(normalize(path));
    }

    /** decomp обращается то с ведущим слэшем, то без — нормализуем к единому виду (без ведущего слэша), как это делает Class.getResource(String) при разрешении relative/absolute путей. */
    private static String normalize(String path) {
        return path.startsWith("/") ? path.substring(1) : path;
    }
}
