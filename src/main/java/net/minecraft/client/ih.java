/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ih
extends fy {
    public ih(int n2, int n3) {
        super(n2, n3, hb.r, false);
        this.bt = 0.98f;
        this.b(true);
    }

    @Override
    public int c() {
        return 1;
    }

    @Override
    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        return super.b(pk2, n2, n3, n4, 1 - n5);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        hb hb2 = cy2.f(n2, n3 - 1, n4);
        if (hb2.c() || hb2.d()) {
            cy2.d(n2, n3, n4, Block.A.bh);
        }
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.a(ch.Block, n2, n3, n4) > 11 - Block.q[this.bh]) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, Block.B.bh);
        }
    }
}

