package net.minecraft.client.awtshim;

/**
 * Реализует ровно то, что реально используется в decomp (проверено grep):
 * HSBtoRGB/getHSBColor (расчёт цвета неба — см. gg.java, и, вероятно,
 * lightmap-подобных градиентов — см. nl.java) и константу BLACK (только
 * для компиляции мёртвой AWT-ветки в Minecraft.java, см. Graphics.java).
 *
 * Формула HSBtoRGB — стандартный алгоритм HSB→RGB, идентичный
 * java.awt.Color.HSBtoRGB (тот же результат побитово для тех же входов).
 */
public final class Color {

    public static final Color BLACK = new Color(0xFF000000);
    public static final Color WHITE = new Color(0xFFFFFFFF);
    public static final Color red = new Color(0xFFFF0000);

    private final int rgb;

    public Color(int rgb) {
        this.rgb = rgb | 0xFF000000;
    }

    public Color(int rgba, boolean hasAlpha) {
        this.rgb = hasAlpha ? rgba : (rgba | 0xFF000000);
    }

    public int getRGB() {
        return rgb;
    }

    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }

    /** Стандартный алгоритм HSB(HSV)->RGB, побитово идентичный java.awt.Color.HSBtoRGB. */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r, g, b;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - saturation * (1.0f - f));
            switch ((int) h) {
                case 0: r = up(brightness); g = up(t); b = up(p); break;
                case 1: r = up(q); g = up(brightness); b = up(p); break;
                case 2: r = up(p); g = up(brightness); b = up(t); break;
                case 3: r = up(p); g = up(q); b = up(brightness); break;
                case 4: r = up(t); g = up(p); b = up(brightness); break;
                default: r = up(brightness); g = up(p); b = up(q); break;
            }
        }
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    private static int up(float v) {
        return (int) (v * 255.0f + 0.5f);
    }
}
