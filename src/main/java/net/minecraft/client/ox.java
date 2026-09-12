/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ox
extends js {
    @Override
    public boolean a(Session cy2, Random random, int n2, int n3, int n4) {
        for (int i2 = 0; i2 < 64; ++i2) {
            int n5;
            int n6;
            int n7 = n2 + random.nextInt(8) - random.nextInt(8);
            if (cy2.a(n7, n6 = n3 + random.nextInt(4) - random.nextInt(4), n5 = n4 + random.nextInt(8) - random.nextInt(8)) != 0 || cy2.a(n7, n6 - 1, n5) != Block.u.bh || !Block.ba.a(cy2, n7, n6, n5)) continue;
            cy2.a(n7, n6, n5, Block.ba.bh, random.nextInt(4));
        }
        return true;
    }
}

