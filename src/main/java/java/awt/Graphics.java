package java.awt;

import java.awt.image.BufferedImage;

/**
 * Минимальная реализация — покрывает только реально используемые в
 * decomp-коде методы (проверено grep по всему дереву):
 *  - drawImage(BufferedImage, x, y, observer) — реальная функциональность,
 *    используется в fu.java (тайлинг анимированных текстур) и lf.java
 *    (конвертер старых скинов). Простой непрозрачный блит без масштабирования
 *    (все реальные вызовы рисуют исходное изображение 1:1, без scale).
 *  - setColor/fillRect/dispose — используются только в Minecraft.java внутри
 *    ветки `if (this.k != null)`, которая в веб-порте никогда не исполняется
 *    (this.k — AWT canvas — всегда null, см. README.md) — реализованы как
 *    no-op ради компиляции, но никогда реально не запускаются.
 */
public class Graphics {
    private final BufferedImage target;

    public Graphics(BufferedImage target) {
        this.target = target;
    }

    public void drawImage(BufferedImage img, int x, int y, Object observer) {
        if (img == null || target == null) return;
        int w = img.getWidth();
        int h = img.getHeight();
        int[] row = new int[w];
        for (int srcY = 0; srcY < h; srcY++) {
            int dstY = y + srcY;
            if (dstY < 0 || dstY >= target.getHeight()) continue;
            img.getRGB(0, srcY, w, 1, row, 0, w);
            int startX = Math.max(0, -x);
            int endX = Math.min(w, target.getWidth() - x);
            if (endX <= startX) continue;
            target.setRGB(x + startX, dstY, endX - startX, 1, row, startX, w);
        }
    }

    public void setColor(Color c) {
        // no-op: см. javadoc класса — эта ветка никогда не исполняется в веб-порте.
    }

    public void fillRect(int x, int y, int w, int h) {
        // no-op: см. javadoc класса.
    }

    public void dispose() {
        // no-op: нет реального системного ресурса (window handle и т.п.) для освобождения.
    }
}
