/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class fm
extends js {
    @Override
    public boolean a(Session cy2, Random random, int n2, int n3, int n4) {
        for (int i2 = 0; i2 < 20; ++i2) {
            int n5;
            int n6;
            int n7 = n2 + random.nextInt(4) - random.nextInt(4);
            if (cy2.a(n7, n6 = n3, n5 = n4 + random.nextInt(4) - random.nextInt(4)) != 0 || cy2.f(n7 - 1, n6 - 1, n5) != hb.f && cy2.f(n7 + 1, n6 - 1, n5) != hb.f && cy2.f(n7, n6 - 1, n5 - 1) != hb.f && cy2.f(n7, n6 - 1, n5 + 1) != hb.f) continue;
            int n8 = 2 + random.nextInt(random.nextInt(3) + 1);
            for (int i3 = 0; i3 < n8; ++i3) {
                if (!Block.aX.g(cy2, n7, n6 + i3, n5)) continue;
                cy2.a(n7, n6 + i3, n5, Block.aX.bh);
            }
        }
        return true;
    }
}

