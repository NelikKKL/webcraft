/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ca
extends Block {
    protected ca(int n2, int n3) {
        super(n2, n3, hb.n);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        float f2 = 0.125f;
        if (n5 == 2) {
            this.a(0.0f, 0.0f, 1.0f - f2, 1.0f, 1.0f, 1.0f);
        }
        if (n5 == 3) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f2);
        }
        if (n5 == 4) {
            this.a(1.0f - f2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (n5 == 5) {
            this.a(0.0f, 0.0f, 0.0f, f2, 1.0f, 1.0f);
        }
        return super.d(cy2, n2, n3, n4);
    }

    @Override
    public co f(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        float f2 = 0.125f;
        if (n5 == 2) {
            this.a(0.0f, 0.0f, 1.0f - f2, 1.0f, 1.0f, 1.0f);
        }
        if (n5 == 3) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f2);
        }
        if (n5 == 4) {
            this.a(1.0f - f2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (n5 == 5) {
            this.a(0.0f, 0.0f, 0.0f, f2, 1.0f, 1.0f);
        }
        return super.f(cy2, n2, n3, n4);
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
        return 8;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        if (cy2.g(n2 - 1, n3, n4)) {
            return true;
        }
        if (cy2.g(n2 + 1, n3, n4)) {
            return true;
        }
        if (cy2.g(n2, n3, n4 - 1)) {
            return true;
        }
        return cy2.g(n2, n3, n4 + 1);
    }

    @Override
    public void d(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.e(n2, n3, n4);
        if ((n6 == 0 || n5 == 2) && cy2.g(n2, n3, n4 + 1)) {
            n6 = 2;
        }
        if ((n6 == 0 || n5 == 3) && cy2.g(n2, n3, n4 - 1)) {
            n6 = 3;
        }
        if ((n6 == 0 || n5 == 4) && cy2.g(n2 + 1, n3, n4)) {
            n6 = 4;
        }
        if ((n6 == 0 || n5 == 5) && cy2.g(n2 - 1, n3, n4)) {
            n6 = 5;
        }
        cy2.b(n2, n3, n4, n6);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.e(n2, n3, n4);
        boolean bl2 = false;
        if (n6 == 2 && cy2.g(n2, n3, n4 + 1)) {
            bl2 = true;
        }
        if (n6 == 3 && cy2.g(n2, n3, n4 - 1)) {
            bl2 = true;
        }
        if (n6 == 4 && cy2.g(n2 + 1, n3, n4)) {
            bl2 = true;
        }
        if (n6 == 5 && cy2.g(n2 - 1, n3, n4)) {
            bl2 = true;
        }
        if (!bl2) {
            this.b_(cy2, n2, n3, n4, n6);
            cy2.d(n2, n3, n4, 0);
        }
        super.a(cy2, n2, n3, n4, n5);
    }

    @Override
    public int a(Random random) {
        return 1;
    }
}

