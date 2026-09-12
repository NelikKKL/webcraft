/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class jn
extends Block {
    protected jn(int n2, int n3) {
        super(n2, n3, hb.n);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public nx a(Session cy2, int n2, int n3, int n4, Vector3D ao2, Vector3D ao3) {
        this.a((pk)cy2, n2, n3, n4);
        return super.a(cy2, n2, n3, n4, ao2, ao3);
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        int n5 = pk2.e(n2, n3, n4);
        if (n5 >= 2 && n5 <= 5) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        } else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }

    @Override
    public int a(int n2, int n3) {
        if (n3 >= 6) {
            return this.bg - 16;
        }
        return this.bg;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public int g() {
        return 9;
    }

    @Override
    public int a(Random random) {
        return 1;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return cy2.g(n2, n3 - 1, n4);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        if (!cy2.z) {
            cy2.b(n2, n3, n4, 15);
            this.h(cy2, n2, n3, n4);
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.z) {
            return;
        }
        int n6 = cy2.e(n2, n3, n4);
        boolean bl2 = false;
        if (!cy2.g(n2, n3 - 1, n4)) {
            bl2 = true;
        }
        if (n6 == 2 && !cy2.g(n2 + 1, n3, n4)) {
            bl2 = true;
        }
        if (n6 == 3 && !cy2.g(n2 - 1, n3, n4)) {
            bl2 = true;
        }
        if (n6 == 4 && !cy2.g(n2, n3, n4 - 1)) {
            bl2 = true;
        }
        if (n6 == 5 && !cy2.g(n2, n3, n4 + 1)) {
            bl2 = true;
        }
        if (bl2) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        } else if (n5 > 0 && Block.registry[n5].e() && oc.a(new oc(this, cy2, n2, n3, n4)) == 3) {
            this.h(cy2, n2, n3, n4);
        }
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        if (cy2.z) {
            return;
        }
        new oc(this, cy2, n2, n3, n4).a(cy2.o(n2, n3, n4));
    }
}

