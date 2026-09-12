/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class cx
extends Block {
    private boolean a;

    protected cx(int n2, int n3, boolean bl2) {
        super(n2, hb.w);
        this.bg = n3;
        this.b(true);
        this.a = bl2;
    }

    @Override
    public int a(int n2, int n3) {
        if (n2 == 1) {
            return this.bg;
        }
        if (n2 == 0) {
            return this.bg;
        }
        int n4 = this.bg + 1 + 16;
        if (this.a) {
            ++n4;
        }
        if (n3 == 0 && n2 == 2) {
            return n4;
        }
        if (n3 == 1 && n2 == 5) {
            return n4;
        }
        if (n3 == 2 && n2 == 3) {
            return n4;
        }
        if (n3 == 3 && n2 == 4) {
            return n4;
        }
        return this.bg + 16;
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return this.bg;
        }
        if (n2 == 0) {
            return this.bg;
        }
        if (n2 == 3) {
            return this.bg + 1 + 16;
        }
        return this.bg + 16;
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        super.e(cy2, n2, n3, n4);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.a(n2, n3, n4);
        return (n5 == 0 || Block.registry[n5].bs.d()) && cy2.g(n2, n3 - 1, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Mob hf2) {
        int n5 = TrigLookup.b((double)(hf2.aC * 4.0f / 360.0f) + 0.5) & 3;
        cy2.b(n2, n3, n4, n5);
    }
}

