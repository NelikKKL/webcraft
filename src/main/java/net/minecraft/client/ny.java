/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ny
extends Block {
    protected ny(int n2) {
        super(n2, hb.c);
        this.bg = 20;
    }

    @Override
    public int a(Random random) {
        return 1;
    }

    @Override
    public int a(int n2, Random random) {
        return Block.J.bh;
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return 21;
        }
        if (n2 == 0) {
            return 21;
        }
        return 20;
    }
}

