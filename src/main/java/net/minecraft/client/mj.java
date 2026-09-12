/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class mj
extends lh {
    private final boolean a;

    protected mj(int n2, boolean bl2) {
        super(n2, hb.d);
        this.a = bl2;
        this.bg = 45;
    }

    @Override
    public int a(int n2, Random random) {
        return Block.aB.bh;
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        super.e(cy2, n2, n3, n4);
        this.h(cy2, n2, n3, n4);
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.a(n2, n3, n4 - 1);
        int n6 = cy2.a(n2, n3, n4 + 1);
        int n7 = cy2.a(n2 - 1, n3, n4);
        int n8 = cy2.a(n2 + 1, n3, n4);
        int n9 = 3;
        if (Block.o[n5] && !Block.o[n6]) {
            n9 = 3;
        }
        if (Block.o[n6] && !Block.o[n5]) {
            n9 = 2;
        }
        if (Block.o[n7] && !Block.o[n8]) {
            n9 = 5;
        }
        if (Block.o[n8] && !Block.o[n7]) {
            n9 = 4;
        }
        cy2.b(n2, n3, n4, n9);
    }

    @Override
    public int a(pk pk2, int n2, int n3, int n4, int n5) {
        if (n5 == 1) {
            return Block.t.bg;
        }
        if (n5 == 0) {
            return Block.t.bg;
        }
        int n6 = pk2.e(n2, n3, n4);
        if (n5 != n6) {
            return this.bg;
        }
        if (this.a) {
            return this.bg + 16;
        }
        return this.bg - 1;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        if (!this.a) {
            return;
        }
        int n5 = cy2.e(n2, n3, n4);
        float f2 = (float)n2 + 0.5f;
        float f3 = (float)n3 + 0.0f + random.nextFloat() * 6.0f / 16.0f;
        float f4 = (float)n4 + 0.5f;
        float f5 = 0.52f;
        float f6 = random.nextFloat() * 0.6f - 0.3f;
        if (n5 == 4) {
            cy2.a("smoke", f2 - f5, f3, f4 + f6, 0.0, 0.0, 0.0);
            cy2.a("flame", f2 - f5, f3, f4 + f6, 0.0, 0.0, 0.0);
        } else if (n5 == 5) {
            cy2.a("smoke", f2 + f5, f3, f4 + f6, 0.0, 0.0, 0.0);
            cy2.a("flame", f2 + f5, f3, f4 + f6, 0.0, 0.0, 0.0);
        } else if (n5 == 2) {
            cy2.a("smoke", f2 + f6, f3, f4 - f5, 0.0, 0.0, 0.0);
            cy2.a("flame", f2 + f6, f3, f4 - f5, 0.0, 0.0, 0.0);
        } else if (n5 == 3) {
            cy2.a("smoke", f2 + f6, f3, f4 + f5, 0.0, 0.0, 0.0);
            cy2.a("flame", f2 + f6, f3, f4 + f5, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return Block.t.bh;
        }
        if (n2 == 0) {
            return Block.t.bh;
        }
        if (n2 == 3) {
            return this.bg - 1;
        }
        return this.bg;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        Furnace lt2 = (Furnace)cy2.b(n2, n3, n4);
        eb2.a(lt2);
        return true;
    }

    public static void a(boolean bl2, Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        TileEntityRegistry ji2 = cy2.b(n2, n3, n4);
        if (bl2) {
            cy2.d(n2, n3, n4, Block.aC.bh);
        } else {
            cy2.d(n2, n3, n4, Block.aB.bh);
        }
        cy2.b(n2, n3, n4, n5);
        cy2.a(n2, n3, n4, ji2);
    }

    @Override
    protected TileEntityRegistry a_() {
        return new Furnace();
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Mob hf2) {
        int n5 = TrigLookup.b((double)(hf2.aC * 4.0f / 360.0f) + 0.5) & 3;
        if (n5 == 0) {
            cy2.b(n2, n3, n4, 2);
        }
        if (n5 == 1) {
            cy2.b(n2, n3, n4, 5);
        }
        if (n5 == 2) {
            cy2.b(n2, n3, n4, 3);
        }
        if (n5 == 3) {
            cy2.b(n2, n3, n4, 4);
        }
    }
}

