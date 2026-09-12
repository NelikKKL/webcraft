/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;
import java.util.Random;

public class ap
extends Block {
    private lg a;

    protected ap(int n2, int n3, lg lg2) {
        super(n2, n3, hb.d);
        this.a = lg2;
        this.b(true);
        float f2 = 0.0625f;
        this.a(f2, 0.0f, f2, 1.0f - f2, 0.03125f, 1.0f - f2);
    }

    @Override
    public int d() {
        return 20;
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
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return cy2.g(n2, n3 - 1, n4);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        boolean bl2 = false;
        if (!cy2.g(n2, n3 - 1, n4)) {
            bl2 = true;
        }
        if (bl2) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.z) {
            return;
        }
        if (cy2.e(n2, n3, n4) == 0) {
            return;
        }
        this.h(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2) {
        if (cy2.z) {
            return;
        }
        if (cy2.e(n2, n3, n4) == 1) {
            return;
        }
        this.h(cy2, n2, n3, n4);
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        boolean bl2 = cy2.e(n2, n3, n4) == 1;
        boolean bl3 = false;
        float f2 = 0.125f;
        List list = null;
        if (this.a == lg.everything /*originally a*/) {
            list = cy2.b(null, co.b((float)n2 + f2, n3, (float)n4 + f2, (float)(n2 + 1) - f2, (double)n3 + 0.25, (float)(n4 + 1) - f2));
        }
        if (this.a == lg.mobs /*originally b*/) {
            list = cy2.a(Mob.class, co.b((float)n2 + f2, n3, (float)n4 + f2, (float)(n2 + 1) - f2, (double)n3 + 0.25, (float)(n4 + 1) - f2));
        }
        if (this.a == lg.players /*originally c*/) {
            list = cy2.a(Player.class, co.b((float)n2 + f2, n3, (float)n4 + f2, (float)(n2 + 1) - f2, (double)n3 + 0.25, (float)(n4 + 1) - f2));
        }
        if (list.size() > 0) {
            bl3 = true;
        }
        if (bl3 && !bl2) {
            cy2.b(n2, n3, n4, 1);
            cy2.g(n2, n3, n4, this.bh);
            cy2.g(n2, n3 - 1, n4, this.bh);
            cy2.b(n2, n3, n4, n2, n3, n4);
            cy2.a((double)n2 + 0.5, (double)n3 + 0.1, (double)n4 + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!bl3 && bl2) {
            cy2.b(n2, n3, n4, 0);
            cy2.g(n2, n3, n4, this.bh);
            cy2.g(n2, n3 - 1, n4, this.bh);
            cy2.b(n2, n3, n4, n2, n3, n4);
            cy2.a((double)n2 + 0.5, (double)n3 + 0.1, (double)n4 + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (bl3) {
            cy2.h(n2, n3, n4, this.bh);
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        if (n5 > 0) {
            cy2.g(n2, n3, n4, this.bh);
            cy2.g(n2, n3 - 1, n4, this.bh);
        }
        super.b(cy2, n2, n3, n4);
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        boolean bl2 = pk2.e(n2, n3, n4) == 1;
        float f2 = 0.0625f;
        if (bl2) {
            this.a(f2, 0.0f, f2, 1.0f - f2, 0.03125f, 1.0f - f2);
        } else {
            this.a(f2, 0.0f, f2, 1.0f - f2, 0.0625f, 1.0f - f2);
        }
    }

    @Override
    public boolean c(pk pk2, int n2, int n3, int n4, int n5) {
        return pk2.e(n2, n3, n4) > 0;
    }

    @Override
    public boolean c(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.e(n2, n3, n4) == 0) {
            return false;
        }
        return n5 == 1;
    }

    @Override
    public boolean e() {
        return true;
    }

    @Override
    public void f() {
        float f2 = 0.5f;
        float f3 = 0.125f;
        float f4 = 0.5f;
        this.a(0.5f - f2, 0.5f - f3, 0.5f - f4, 0.5f + f2, 0.5f + f3, 0.5f + f4);
    }
}

