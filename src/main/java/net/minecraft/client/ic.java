/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ic
extends dw {
    public ic(int n2, int n3) {
        super(n2, n3);
    }

    @Override
    public int a(int n2, Random random) {
        if (random.nextInt(10) == 0) {
            return Item.an.id;
        }
        return this.bh;
    }
}

