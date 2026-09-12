/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class km
extends il {
    private int b;
    private int c = 0;

    protected km(int n2, int n3) {
        super(n2, n3, hb.h, false);
        this.b = n3;
    }

    @Override
    public int b(pk pk2, int n2, int n3, int n4) {
        pk2.a().a(n2, n4, 1, 1);
        double d2 = pk2.a().a[0];
        double d3 = pk2.a().b[0];
        return ft.a(d2, d3);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (this != null) {
            return;
        }
        this.c = 0;
        this.h(cy2, n2, n3, n4);
        super.a(cy2, n2, n3, n4, n5);
    }

    public void f(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.a(n2, n3, n4) != this.bh) {
            return;
        }
        int n6 = cy2.e(n2, n3, n4);
        if (n6 == 0 || n6 != n5 - 1) {
            return;
        }
        this.h(cy2, n2, n3, n4);
    }

    public void h(Session cy2, int n2, int n3, int n4) {
        if (this != null) {
            return;
        }
        if (this.c++ >= 100) {
            return;
        }
        int n5 = cy2.f(n2, n3 - 1, n4).a() ? 16 : 0;
        int n6 = cy2.e(n2, n3, n4);
        if (n6 == 0) {
            n6 = 1;
            cy2.b(n2, n3, n4, 1);
        }
        n5 = this.g(cy2, n2, n3 - 1, n4, n5);
        n5 = this.g(cy2, n2, n3, n4 - 1, n5);
        n5 = this.g(cy2, n2, n3, n4 + 1, n5);
        n5 = this.g(cy2, n2 - 1, n3, n4, n5);
        int n7 = (n5 = this.g(cy2, n2 + 1, n3, n4, n5)) - 1;
        if (n7 < 10) {
            n7 = 1;
        }
        if (n7 != n6) {
            cy2.b(n2, n3, n4, n7);
            this.f(cy2, n2, n3 - 1, n4, n6);
            this.f(cy2, n2, n3 + 1, n4, n6);
            this.f(cy2, n2, n3, n4 - 1, n6);
            this.f(cy2, n2, n3, n4 + 1, n6);
            this.f(cy2, n2 - 1, n3, n4, n6);
            this.f(cy2, n2 + 1, n3, n4, n6);
        }
    }

    private int g(Session cy2, int n2, int n3, int n4, int n5) {
        int n6;
        int n7 = cy2.a(n2, n3, n4);
        if (n7 == Block.J.bh) {
            return 16;
        }
        if (n7 == this.bh && (n6 = cy2.e(n2, n3, n4)) != 0 && n6 > n5) {
            return n6;
        }
        return n5;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (this != null) {
            return;
        }
        int n5 = cy2.e(n2, n3, n4);
        if (n5 == 0) {
            this.c = 0;
            this.h(cy2, n2, n3, n4);
        } else if (n5 == 1) {
            this.i(cy2, n2, n3, n4);
        } else if (random.nextInt(10) == 0) {
            this.h(cy2, n2, n3, n4);
        }
    }

    private void i(Session cy2, int n2, int n3, int n4) {
        this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
        cy2.d(n2, n3, n4, 0);
    }

    @Override
    public int a(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public int a(int n2, Random random) {
        return Block.y.bh;
    }

    @Override
    public boolean a() {
        return !this.a;
    }

    public void a(boolean bl2) {
        this.a = bl2;
        this.bg = this.b + (bl2 ? 0 : 1);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, lw lw2) {
        super.b(cy2, n2, n3, n4, lw2);
    }
}

