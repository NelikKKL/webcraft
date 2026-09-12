/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class br
extends lh {
    protected br(int n2, int n3) {
        super(n2, n3, hb.d);
    }

    @Override
    protected TileEntityRegistry a_() {
        return new MobSpawner();
    }

    @Override
    public int a(int n2, Random random) {
        return 0;
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public boolean a() {
        return false;
    }
}

