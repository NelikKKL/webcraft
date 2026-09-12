/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class os
extends Block {
    protected os(int n2) {
        super(n2, hb.b);
        this.bg = 3;
        this.b(true);
    }

    @Override
    public int a(pk pk2, int n2, int n3, int n4, int n5) {
        if (n5 == 1) {
            return 0;
        }
        if (n5 == 0) {
            return 2;
        }
        hb hb2 = pk2.f(n2, n3 + 1, n4);
        if (hb2 == hb.s || hb2 == hb.t) {
            return 68;
        }
        return 3;
    }

    @Override
    public int b(pk pk2, int n2, int n3, int n4) {
        pk2.a().a(n2, n4, 1, 1);
        double d2 = pk2.a().a[0];
        double d3 = pk2.a().b[0];
        return ev.a(d2, d3);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        int n5;
        int n6;
        int n7;
        if (cy2.j(n2, n3 + 1, n4) < 4 && cy2.f(n2, n3 + 1, n4).b()) {
            if (random.nextInt(4) != 0) {
                return;
            }
            cy2.d(n2, n3, n4, Block.v.bh);
        } else if (cy2.j(n2, n3 + 1, n4) >= 9 && cy2.a(n7 = n2 + random.nextInt(3) - 1, n6 = n3 + random.nextInt(5) - 3, n5 = n4 + random.nextInt(3) - 1) == Block.v.bh && cy2.j(n7, n6 + 1, n5) >= 4 && !cy2.f(n7, n6 + 1, n5).b()) {
            cy2.d(n7, n6, n5, Block.u.bh);
        }
    }

    @Override
    public int a(int n2, Random random) {
        return Block.v.a(0, random);
    }
}

