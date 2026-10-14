package net.minecraft.client;

import net.minecraft.client.web.ResourceCache;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

/** Regression test for the generated mask used to biome-tint only grass pixels on block sides. */
public final class GrassTintTest {
    public static void main(String[] args) throws Exception {
        int[] pixels = new int[256 * 256];
        int green = 0xFF44AA22;
        int dirt = 0xFF886644;
        pixels[0 * 256 + 3 * 16] = green; // a green pixel in side tile 3
        pixels[5 * 256 + 3 * 16] = dirt;  // a dirt pixel in side tile 3

        Constructor<ResourceCache.Entry> ctor = ResourceCache.Entry.class
                .getDeclaredConstructor(int.class, int.class, int[].class);
        ctor.setAccessible(true);
        ResourceCache.Entry atlas = ctor.newInstance(256, 256, pixels);

        Field cacheField = ResourceCache.class.getDeclaredField("cache");
        cacheField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, ResourceCache.Entry> cache = (Map<String, ResourceCache.Entry>) cacheField.get(null);
        cache.put("terrain.png", atlas);

        ResourceCache.Entry loaded = ResourceCache.get("/terrain.png");
        int maskX = 15 * 16;
        int maskY = 15 * 16;
        int tintedGrass = loaded.argb[maskY * 256 + maskX];
        int untintedDirt = loaded.argb[(maskY + 5) * 256 + maskX];

        if (tintedGrass != 0xFFFFFFFF) {
            throw new AssertionError("grass pixels should become opaque white in the tint mask: 0x"
                    + Integer.toHexString(tintedGrass));
        }
        if (untintedDirt != 0x00000000) {
            throw new AssertionError("dirt pixels should be transparent in the tint mask: 0x"
                    + Integer.toHexString(untintedDirt));
        }
        if (pixels[5 * 256 + 3 * 16] != dirt) {
            throw new AssertionError("source dirt texture must not be modified");
        }
        System.out.println("GRASS TINT OK");
    }
}
