/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class an
extends Block {
    private boolean a;

    public an(int n2, int n3, boolean bl2) {
        super(n2, n3, hb.d);
        if (bl2) {
            this.b(true);
        }
        this.a = bl2;
    }

    @Override
    public int d() {
        return 30;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Player eb2) {
        this.h(cy2, n2, n3, n4);
        super.b(cy2, n2, n3, n4, eb2);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, lw lw2) {
        this.h(cy2, n2, n3, n4);
        super.b(cy2, n2, n3, n4, lw2);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        this.h(cy2, n2, n3, n4);
        return super.a(cy2, n2, n3, n4, eb2);
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        this.i(cy2, n2, n3, n4);
        if (this.bh == Block.aN.bh) {
            cy2.d(n2, n3, n4, Block.aO.bh);
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (this.bh == Block.aO.bh) {
            cy2.d(n2, n3, n4, Block.aN.bh);
        }
    }

    @Override
    public int a(int n2, Random random) {
        return Item.aA.id;
    }

    @Override
    public int a(Random random) {
        return 4 + random.nextInt(2);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        if (this.a) {
            this.i(cy2, n2, n3, n4);
        }
    }

    private void i(Session cy2, int n2, int n3, int n4) {
        Random random = cy2.l;
        double d2 = 0.0625;
        for (int i2 = 0; i2 < 6; ++i2) {
            double d3 = (float)n2 + random.nextFloat();
            double d4 = (float)n3 + random.nextFloat();
            double d5 = (float)n4 + random.nextFloat();
            if (i2 == 0 && !cy2.g(n2, n3 + 1, n4)) {
                d4 = (double)(n3 + 1) + d2;
            }
            if (i2 == 1 && !cy2.g(n2, n3 - 1, n4)) {
                d4 = (double)(n3 + 0) - d2;
            }
            if (i2 == 2 && !cy2.g(n2, n3, n4 + 1)) {
                d5 = (double)(n4 + 1) + d2;
            }
            if (i2 == 3 && !cy2.g(n2, n3, n4 - 1)) {
                d5 = (double)(n4 + 0) - d2;
            }
            if (i2 == 4 && !cy2.g(n2 + 1, n3, n4)) {
                d3 = (double)(n2 + 1) + d2;
            }
            if (i2 == 5 && !cy2.g(n2 - 1, n3, n4)) {
                d3 = (double)(n2 + 0) - d2;
            }
            if (!(d3 < (double)n2 || d3 > (double)(n2 + 1) || d4 < 0.0 || d4 > (double)(n3 + 1) || d5 < (double)n4) && !(d5 > (double)(n4 + 1))) continue;
            cy2.a("reddust", d3, d4, d5, 0.0, 0.0, 0.0);
        }
    }
}

