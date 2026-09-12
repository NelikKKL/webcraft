/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class s
extends Block {
    protected s(int n2, int n3) {
        super(n2, n3, hb.t);
        this.b(true);
    }

    @Override
    public int a(int n2, Random random) {
        return Item.aB.id;
    }

    @Override
    public int a(Random random) {
        return 4;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.a(ch.Block, n2, n3, n4) > 11) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
    }
}

