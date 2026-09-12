package net.minecraft.client.awtshim;


/**
 * Минимальная, но НАСТОЯЩАЯ (не заглушка) реализация BufferedImage для
 * web-порта. TeaVM classlib не включает java.awt вообще — этот класс
 * пишется с нуля, реализуя ровно ту часть контракта, что реально
 * используется в decomp-коде Minecraft Alpha 1.2.6 (см. PATCHES.md и
 * TODO.md, раздел про загрузку ресурсов):
 *
 *  - getRGB/setRGB в bulk-форме (startX,startY,w,h,int[],offset,scansize) —
 *    единственная используемая форма во всём decomp-дереве (проверено
 *    построчным grep).
 *  - getGraphics()/getRaster().getDataBuffer() — используются в fu.java
 *    (тайлинг анимированных текстур) и lf.java (конвертер старых 64x32
 *    скинов) — обе реальная игровая функциональность, не мёртвый код.
 *
 * НЕ наследует java.awt.Image: реальный java.awt.Image нужен только в
 * ah.java (изометрический preview-инструмент, не часть игры — исключён из
 * сборки, см. TODO.md), поэтому не добавляем лишний тип ради него.
 *
 * Хранилище — Java int[] в packed ARGB (0xAARRGGBB на пиксель), тот же
 * формат, что использует настоящий BufferedImage.TYPE_INT_ARGB.
 */
public class BufferedImage {

    public static final int TYPE_INT_RGB = 1;
    public static final int TYPE_INT_ARGB = 2;

    private final int width;
    private final int height;
    private final int type;
    final int[] pixels; // package-private: WritableRaster/DataBufferInt читают напрямую тот же массив

    public BufferedImage(int width, int height, int type) {
        this.width = width;
        this.height = height;
        this.type = type;
        this.pixels = new int[width * height];
        if (type == TYPE_INT_RGB) {
            java.util.Arrays.fill(this.pixels, 0xFF000000); // непрозрачный чёрный по умолчанию, как в реальном TYPE_INT_RGB
        }
    }

    /** Используется ResourceIO/ImageIO при декодировании прелоаднутых ресурсов — оборачивает уже готовый ARGB массив без копирования. */
    public static BufferedImage wrapArgb(int width, int height, int[] argb) {
        return new BufferedImage(width, height, argb);
    }

    /** Приватный конструктор для wrapArgb — переиспользует существующий int[] без копирования (в отличие от публичного конструктора, который всегда аллоцирует новый пустой массив). */
    private BufferedImage(int width, int height, int[] sharedPixels) {
        this.width = width;
        this.height = height;
        this.type = TYPE_INT_ARGB;
        this.pixels = sharedPixels;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getType() { return type; }

    /** Единственная используемая в decomp форма (проверено grep). Семантика 1:1 с реальным java.awt.image.BufferedImage. */
    public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
        if (rgbArray == null) {
            rgbArray = new int[offset + h * scansize];
        }
        for (int row = 0; row < h; row++) {
            int srcRowBase = (startY + row) * width + startX;
            int dstRowBase = offset + row * scansize;
            for (int col = 0; col < w; col++) {
                rgbArray[dstRowBase + col] = pixels[srcRowBase + col];
            }
        }
        return rgbArray;
    }

    public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
        boolean forceOpaque = (type == TYPE_INT_RGB);
        for (int row = 0; row < h; row++) {
            int dstRowBase = (startY + row) * width + startX;
            int srcRowBase = offset + row * scansize;
            for (int col = 0; col < w; col++) {
                int v = rgbArray[srcRowBase + col];
                pixels[dstRowBase + col] = forceOpaque ? (v | 0xFF000000) : v;
            }
        }
    }

    public Graphics getGraphics() {
        return new Graphics(this);
    }

    public WritableRaster getRaster() {
        return new WritableRaster(this);
    }
}
