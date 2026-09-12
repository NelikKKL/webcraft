/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class hz
extends Block {
    public hz(int n2, int n3) {
        super(n2, n3, hb.d);
    }

    @Override
    public int a(int n2, Random random) {
        if (this.bh == Block.I.bh) {
            return Item.k.id;
        }
        if (this.bh == Block.aw.bh) {
            return Item.l.id;
        }
        return this.bh;
    }

    @Override
    public int a(Random random) {
        return 1;
    }
}

