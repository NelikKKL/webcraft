/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class eh
extends Block {
    public eh(int n2, int n3) {
        super(n2, n3, hb.c);
    }

    @Override
    public int a(int n2) {
        if (n2 <= 1) {
            return 4;
        }
        return this.bg;
    }

    @Override
    public int a(Random random) {
        return 0;
    }
}

