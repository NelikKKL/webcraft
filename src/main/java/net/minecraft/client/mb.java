/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.Random;

public class mb
extends Block {
    private Block a;

    protected mb(int n2, Block nq2) {
        super(n2, nq2.bg, nq2.bs);
        this.a = nq2;
        this.c(nq2.bi);
        this.b(nq2.bj / 3.0f);
        this.a(nq2.bq);
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return super.d(cy2, n2, n3, n4);
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public int g() {
        return 10;
    }

    @Override
    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        return super.b(pk2, n2, n3, n4, n5);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, co co2, ArrayList arrayList) {
        int n5 = cy2.e(n2, n3, n4);
        if (n5 == 0) {
            this.a(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
            this.a(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
        } else if (n5 == 1) {
            this.a(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
            this.a(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
        } else if (n5 == 2) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
            this.a(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
        } else if (n5 == 3) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
            this.a(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            super.a(cy2, n2, n3, n4, co2, arrayList);
        }
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        this.a.b(cy2, n2, n3, n4, random);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Player eb2) {
        this.a.b(cy2, n2, n3, n4, eb2);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, int n5) {
        this.a.b(cy2, n2, n3, n4, n5);
    }

    @Override
    public float d(pk pk2, int n2, int n3, int n4) {
        return this.a.d(pk2, n2, n3, n4);
    }

    @Override
    public float a(lw lw2) {
        return this.a.a(lw2);
    }

    @Override
    public int c() {
        return this.a.c();
    }

    @Override
    public int a(int n2, Random random) {
        return this.a.a(n2, random);
    }

    @Override
    public int a(Random random) {
        return this.a.a(random);
    }

    @Override
    public int a(int n2, int n3) {
        return this.a.a(n2, n3);
    }

    @Override
    public int a(int n2) {
        return this.a.a(n2);
    }

    @Override
    public int a(pk pk2, int n2, int n3, int n4, int n5) {
        return this.a.a(pk2, n2, n3, n4, n5);
    }

    @Override
    public int d() {
        return this.a.d();
    }

    @Override
    public co f(Session cy2, int n2, int n3, int n4) {
        return this.a.f(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2, Vector3D ao2) {
        this.a.a(cy2, n2, n3, n4, lw2, ao2);
    }

    @Override
    public boolean h() {
        return this.a.h();
    }

    @Override
    public boolean a(int n2, boolean bl2) {
        return this.a.a(n2, bl2);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return this.a.a(cy2, n2, n3, n4);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        this.a(cy2, n2, n3, n4, 0);
        this.a.e(cy2, n2, n3, n4);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        this.a.b(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5, float f2) {
        this.a.a(cy2, n2, n3, n4, n5, f2);
    }

    @Override
    public void b_(Session cy2, int n2, int n3, int n4, int n5) {
        this.a.b_(cy2, n2, n3, n4, n5);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, lw lw2) {
        this.a.b(cy2, n2, n3, n4, lw2);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        this.a.a(cy2, n2, n3, n4, random);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        return this.a.a(cy2, n2, n3, n4, eb2);
    }

    @Override
    public void c(Session cy2, int n2, int n3, int n4) {
        this.a.c(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Mob hf2) {
        int n5 = TrigLookup.b((double)(hf2.aC * 4.0f / 360.0f) + 0.5) & 3;
        if (n5 == 0) {
            cy2.b(n2, n3, n4, 2);
        }
        if (n5 == 1) {
            cy2.b(n2, n3, n4, 1);
        }
        if (n5 == 2) {
            cy2.b(n2, n3, n4, 3);
        }
        if (n5 == 3) {
            cy2.b(n2, n3, n4, 0);
        }
    }
}

