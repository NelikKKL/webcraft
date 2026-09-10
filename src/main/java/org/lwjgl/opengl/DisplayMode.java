package org.lwjgl.opengl;

/** Шим org.lwjgl.opengl.DisplayMode. Веб-версии не важны частота обновления/битность — только размер canvas. */
public final class DisplayMode {
    private final int width;
    private final int height;

    public DisplayMode(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getBitsPerPixel() { return 24; }
    public int getFrequency() { return 60; }
    public boolean isFullscreenCapable() { return true; }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
