/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class je
extends Block {
    protected je(int n2, int n3) {
        super(n2, n3, hb.u);
        this.b(true);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.a(n2, n3 + 1, n4) == 0) {
            int n5 = 1;
            while (cy2.a(n2, n3 - n5, n4) == this.bh) {
                ++n5;
            }
            if (n5 < 3) {
                int n6 = cy2.e(n2, n3, n4);
                if (n6 == 15) {
                    cy2.d(n2, n3 + 1, n4, this.bh);
                    cy2.b(n2, n3, n4, 0);
                } else {
                    cy2.b(n2, n3, n4, n6 + 1);
                }
            }
        }
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        float f2 = 0.0625f;
        return co.b((float)n2 + f2, n3, (float)n4 + f2, (float)(n2 + 1) - f2, (float)(n3 + 1) - f2, (float)(n4 + 1) - f2);
    }

    @Override
    public co f(Session cy2, int n2, int n3, int n4) {
        float f2 = 0.0625f;
        return co.b((float)n2 + f2, n3, (float)n4 + f2, (float)(n2 + 1) - f2, n3 + 1, (float)(n4 + 1) - f2);
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return this.bg - 1;
        }
        if (n2 == 0) {
            return this.bg + 1;
        }
        return this.bg;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public int g() {
        return 13;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        if (!super.a(cy2, n2, n3, n4)) {
            return false;
        }
        return this.g(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (!this.g(cy2, n2, n3, n4)) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
    }

    @Override
    public boolean g(Session cy2, int n2, int n3, int n4) {
        if (cy2.f(n2 - 1, n3, n4).a()) {
            return false;
        }
        if (cy2.f(n2 + 1, n3, n4).a()) {
            return false;
        }
        if (cy2.f(n2, n3, n4 - 1).a()) {
            return false;
        }
        if (cy2.f(n2, n3, n4 + 1).a()) {
            return false;
        }
        int n5 = cy2.a(n2, n3 - 1, n4);
        return n5 == Block.aV.bh || n5 == Block.E.bh;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2) {
        lw2.a(null, 1);
    }
}

