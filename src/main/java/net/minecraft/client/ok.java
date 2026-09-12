/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ok
extends Block {
    protected ok(int n2, int n3) {
        super(n2, hb.i);
        this.bg = n3;
        this.b(true);
        float f2 = 0.2f;
        this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, f2 * 3.0f, 0.5f + f2);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return this.b(cy2.a(n2, n3 - 1, n4));
    }

    protected boolean b(int n2) {
        return n2 == Block.u.bh || n2 == Block.v.bh || n2 == Block.aA.bh;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        super.a(cy2, n2, n3, n4, n5);
        this.h(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        this.h(cy2, n2, n3, n4);
    }

    protected final void h(Session cy2, int n2, int n3, int n4) {
        if (!this.g(cy2, n2, n3, n4)) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
    }

    @Override
    public boolean g(Session cy2, int n2, int n3, int n4) {
        return (cy2.j(n2, n3, n4) >= 8 || cy2.i(n2, n3, n4)) && this.b(cy2.a(n2, n3 - 1, n4));
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
    public boolean b() {
        return false;
    }

    @Override
    public int g() {
        return 1;
    }
}

