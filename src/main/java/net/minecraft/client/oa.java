/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class oa
extends Block {
    protected oa(int n2) {
        super(n2, hb.b);
        this.bg = 87;
        this.b(true);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.d(255);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return co.b(n2 + 0, n3 + 0, n4 + 0, n2 + 1, n3 + 1, n4 + 1);
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
    public int a(int n2, int n3) {
        if (n2 == 1 && n3 > 0) {
            return this.bg - 1;
        }
        if (n2 == 1) {
            return this.bg;
        }
        return 2;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (random.nextInt(5) == 0) {
            if (this.i(cy2, n2, n3, n4)) {
                cy2.b(n2, n3, n4, 7);
            } else {
                int n5 = cy2.e(n2, n3, n4);
                if (n5 > 0) {
                    cy2.b(n2, n3, n4, n5 - 1);
                } else if (!this.h(cy2, n2, n3, n4)) {
                    cy2.d(n2, n3, n4, Block.v.bh);
                }
            }
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, lw lw2) {
        if (cy2.l.nextInt(4) == 0) {
            cy2.d(n2, n3, n4, Block.v.bh);
        }
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        int n5 = 0;
        for (int i2 = n2 - n5; i2 <= n2 + n5; ++i2) {
            for (int i3 = n4 - n5; i3 <= n4 + n5; ++i3) {
                if (cy2.a(i2, n3 + 1, i3) != Block.az.bh) continue;
                return true;
            }
        }
        return false;
    }

    private boolean i(Session cy2, int n2, int n3, int n4) {
        for (int i2 = n2 - 4; i2 <= n2 + 4; ++i2) {
            for (int i3 = n3; i3 <= n3 + 1; ++i3) {
                for (int i4 = n4 - 4; i4 <= n4 + 4; ++i4) {
                    if (cy2.f(i2, i3, i4) != hb.f) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        super.a(cy2, n2, n3, n4, n5);
        hb hb2 = cy2.f(n2, n3 + 1, n4);
        if (hb2.a()) {
            cy2.d(n2, n3, n4, Block.v.bh);
        }
    }

    @Override
    public int a(int n2, Random random) {
        return Block.v.a(0, random);
    }
}

