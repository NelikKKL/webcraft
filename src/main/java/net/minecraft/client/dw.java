/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class dw
extends Block {
    public static boolean a = false;

    public dw(int n2, int n3) {
        super(n2, n3, hb.m);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        cy2.h(n2, n3, n4, this.bh);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        cy2.h(n2, n3, n4, this.bh);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        this.h(cy2, n2, n3, n4);
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        int n5 = n2;
        int n6 = n3;
        int n7 = n4;
        if (dw.b_(cy2, n5, n6 - 1, n7) && n6 >= 0) {
            FallingSand gb2 = new FallingSand(cy2, (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f, this.bh);
            if (a) {
                while (!gb2.aN) {
                    gb2.e_();
                }
            } else {
                cy2.a(gb2);
            }
        }
    }

    @Override
    public int d() {
        return 3;
    }

    public static boolean b_(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.a(n2, n3, n4);
        if (n5 == 0) {
            return true;
        }
        if (n5 == Block.ar.bh) {
            return true;
        }
        hb hb2 = Block.registry[n5].bs;
        if (hb2 == hb.f) {
            return true;
        }
        return hb2 == hb.g;
    }
}

