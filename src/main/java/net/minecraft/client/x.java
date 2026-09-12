/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class x
extends fy {
    public x(int n2, int n3) {
        super(n2, n3, hb.x, false);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        if (pk2.a(n2 - 1, n3, n4) == this.bh || pk2.a(n2 + 1, n3, n4) == this.bh) {
            float f2 = 0.5f;
            float f3 = 0.125f;
            this.a(0.5f - f2, 0.0f, 0.5f - f3, 0.5f + f2, 1.0f, 0.5f + f3);
        } else {
            float f4 = 0.125f;
            float f5 = 0.5f;
            this.a(0.5f - f4, 0.0f, 0.5f - f5, 0.5f + f4, 1.0f, 0.5f + f5);
        }
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }

    public boolean a_(Session cy2, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7 = 0;
        int n8 = 0;
        if (cy2.a(n2 - 1, n3, n4) == Block.ap.bh || cy2.a(n2 + 1, n3, n4) == Block.ap.bh) {
            n7 = 1;
        }
        if (cy2.a(n2, n3, n4 - 1) == Block.ap.bh || cy2.a(n2, n3, n4 + 1) == Block.ap.bh) {
            n8 = 1;
        }
        System.out.println(n7 + ", " + n8);
        if (n7 == n8) {
            return false;
        }
        if (cy2.a(n2 - n7, n3, n4 - n8) == 0) {
            n2 -= n7;
            n4 -= n8;
        }
        for (n6 = -1; n6 <= 2; ++n6) {
            for (n5 = -1; n5 <= 3; ++n5) {
                boolean bl2;
                boolean bl3 = bl2 = n6 == -1 || n6 == 2 || n5 == -1 || n5 == 3;
                if (!(n6 != -1 && n6 != 2 || n5 != -1 && n5 != 3)) continue;
                int n9 = cy2.a(n2 + n7 * n6, n3 + n5, n4 + n8 * n6);
                if (!(bl2 ? n9 != Block.ap.bh : n9 != 0 && n9 != Block.ar.bh)) continue;
                return false;
            }
        }
        cy2.i = true;
        for (n6 = 0; n6 < 2; ++n6) {
            for (n5 = 0; n5 < 3; ++n5) {
                cy2.d(n2 + n7 * n6, n3 + n5, n4 + n8 * n6, Block.be.bh);
            }
        }
        cy2.i = false;
        return true;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        boolean bl2;
        int n6;
        int n7 = 0;
        int n8 = 1;
        if (cy2.a(n2 - 1, n3, n4) == this.bh || cy2.a(n2 + 1, n3, n4) == this.bh) {
            n7 = 1;
            n8 = 0;
        }
        int n9 = n3;
        while (cy2.a(n2, n9 - 1, n4) == this.bh) {
            --n9;
        }
        if (cy2.a(n2, n9 - 1, n4) != Block.ap.bh) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
        for (n6 = 1; n6 < 4 && cy2.a(n2, n9 + n6, n4) == this.bh; ++n6) {
        }
        if (n6 != 3 || cy2.a(n2, n9 + n6, n4) != Block.ap.bh) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
        boolean bl3 = cy2.a(n2 - 1, n3, n4) == this.bh || cy2.a(n2 + 1, n3, n4) == this.bh;
        boolean bl4 = bl2 = cy2.a(n2, n3, n4 - 1) == this.bh || cy2.a(n2, n3, n4 + 1) == this.bh;
        if (bl3 && bl2) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
        if (!(cy2.a(n2 + n7, n3, n4 + n8) == Block.ap.bh && cy2.a(n2 - n7, n3, n4 - n8) == this.bh || cy2.a(n2 - n7, n3, n4 - n8) == Block.ap.bh && cy2.a(n2 + n7, n3, n4 + n8) == this.bh)) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
    }

    @Override
    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        return true;
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public int c() {
        return 1;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2) {
        if (cy2.z) {
            return;
        }
        lw2.q();
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        if (random.nextInt(100) == 0) {
            cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "portal.portal", 1.0f, random.nextFloat() * 0.4f + 0.8f);
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            double d2 = (float)n2 + random.nextFloat();
            double d3 = (float)n3 + random.nextFloat();
            double d4 = (float)n4 + random.nextFloat();
            double d5 = 0.0;
            double d6 = 0.0;
            double d7 = 0.0;
            int n5 = random.nextInt(2) * 2 - 1;
            d5 = ((double)random.nextFloat() - 0.5) * 0.5;
            d6 = ((double)random.nextFloat() - 0.5) * 0.5;
            d7 = ((double)random.nextFloat() - 0.5) * 0.5;
            if (cy2.a(n2 - 1, n3, n4) == this.bh || cy2.a(n2 + 1, n3, n4) == this.bh) {
                d4 = (double)n4 + 0.5 + 0.25 * (double)n5;
                d7 = random.nextFloat() * 2.0f * (float)n5;
            } else {
                d2 = (double)n2 + 0.5 + 0.25 * (double)n5;
                d5 = random.nextFloat() * 2.0f * (float)n5;
            }
            cy2.a("portal", d2, d3, d4, d5, d6, d7);
        }
    }
}

