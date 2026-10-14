package net.minecraft.client;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.client.web.WorldStore;

/** JVM-side regression test for the byte representation used by browser world saves. */
public final class WorldStoreCodecTest {
    public static void main(String[] args) {
        Random random = new Random(126);
        for (int length = 0; length < 2048; length++) {
            byte[] bytes = new byte[length];
            random.nextBytes(bytes);
            byte[] decoded = WorldStore.decode(WorldStore.encode(bytes));
            if (!Arrays.equals(bytes, decoded)) {
                throw new AssertionError("Base64 round-trip failed at length " + length);
            }
        }
        if (!"world/World1/level.dat".equals(WorldStore.levelKey("World1"))) {
            throw new AssertionError("Unexpected level key");
        }
        if (!"world/World1/DIM-1/chunks/-2.7.dat".equals(WorldStore.chunkKey("World1/DIM-1", -2, 7))) {
            throw new AssertionError("Unexpected chunk key");
        }
        System.out.println("WORLD STORE CODEC OK");
    }
}
