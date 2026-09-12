/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class lj
extends Block {
    public lj(int n2, int n3) {
        super(n2, n3, hb.v);
    }

    @Override
    public int a(int n2, Random random) {
        return Item.aG.id;
    }

    @Override
    public int a(Random random) {
        return 4;
    }
}

