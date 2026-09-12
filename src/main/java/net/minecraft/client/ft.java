/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.BufferedImage;
import net.minecraft.client.awtshim.ImageIO;

public class ft {
    static private final int[] a = new int[65536];

    public static int a(double d2, double d3) {
        int n2 = (int)((1.0 - d2) * 255.0);
        int n3 = (int)((1.0 - (d3 *= d2)) * 255.0);
        return a[n3 << 8 | n2];
    }

    static {
        try {
            BufferedImage bufferedImage = ImageIO.read("/misc/foliagecolor.png");
            bufferedImage.getRGB(0, 0, 256, 256, a, 0, 256);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

