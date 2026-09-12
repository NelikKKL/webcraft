/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class c
extends lh {
    private Random a = new Random();

    protected c(int n2) {
        super(n2, hb.c);
        this.bg = 26;
    }

    @Override
    public int a(pk pk2, int n2, int n3, int n4, int n5) {
        if (n5 == 1) {
            return this.bg - 1;
        }
        if (n5 == 0) {
            return this.bg - 1;
        }
        int n6 = pk2.a(n2, n3, n4 - 1);
        int n7 = pk2.a(n2, n3, n4 + 1);
        int n8 = pk2.a(n2 - 1, n3, n4);
        int n9 = pk2.a(n2 + 1, n3, n4);
        if (n6 == this.bh || n7 == this.bh) {
            if (n5 == 2 || n5 == 3) {
                return this.bg;
            }
            int n10 = 0;
            if (n6 == this.bh) {
                n10 = -1;
            }
            int n11 = pk2.a(n2 - 1, n3, n6 == this.bh ? n4 - 1 : n4 + 1);
            int n12 = pk2.a(n2 + 1, n3, n6 == this.bh ? n4 - 1 : n4 + 1);
            if (n5 == 4) {
                n10 = -1 - n10;
            }
            int n13 = 5;
            if ((Block.o[n8] || Block.o[n11]) && !Block.o[n9] && !Block.o[n12]) {
                n13 = 5;
            }
            if ((Block.o[n9] || Block.o[n12]) && !Block.o[n8] && !Block.o[n11]) {
                n13 = 4;
            }
            return (n5 == n13 ? this.bg + 16 : this.bg + 32) + n10;
        }
        if (n8 == this.bh || n9 == this.bh) {
            if (n5 == 4 || n5 == 5) {
                return this.bg;
            }
            int n14 = 0;
            if (n8 == this.bh) {
                n14 = -1;
            }
            int n15 = pk2.a(n8 == this.bh ? n2 - 1 : n2 + 1, n3, n4 - 1);
            int n16 = pk2.a(n8 == this.bh ? n2 - 1 : n2 + 1, n3, n4 + 1);
            if (n5 == 3) {
                n14 = -1 - n14;
            }
            int n17 = 3;
            if ((Block.o[n6] || Block.o[n15]) && !Block.o[n7] && !Block.o[n16]) {
                n17 = 3;
            }
            if ((Block.o[n7] || Block.o[n16]) && !Block.o[n6] && !Block.o[n15]) {
                n17 = 2;
            }
            return (n5 == n17 ? this.bg + 16 : this.bg + 32) + n14;
        }
        int n18 = 3;
        if (Block.o[n6] && !Block.o[n7]) {
            n18 = 3;
        }
        if (Block.o[n7] && !Block.o[n6]) {
            n18 = 2;
        }
        if (Block.o[n8] && !Block.o[n9]) {
            n18 = 5;
        }
        if (Block.o[n9] && !Block.o[n8]) {
            n18 = 4;
        }
        return n5 == n18 ? this.bg + 1 : this.bg;
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return this.bg - 1;
        }
        if (n2 == 0) {
            return this.bg - 1;
        }
        if (n2 == 3) {
            return this.bg + 1;
        }
        return this.bg;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        int n5 = 0;
        if (cy2.a(n2 - 1, n3, n4) == this.bh) {
            ++n5;
        }
        if (cy2.a(n2 + 1, n3, n4) == this.bh) {
            ++n5;
        }
        if (cy2.a(n2, n3, n4 - 1) == this.bh) {
            ++n5;
        }
        if (cy2.a(n2, n3, n4 + 1) == this.bh) {
            ++n5;
        }
        if (n5 > 1) {
            return false;
        }
        if (this.h(cy2, n2 - 1, n3, n4)) {
            return false;
        }
        if (this.h(cy2, n2 + 1, n3, n4)) {
            return false;
        }
        if (this.h(cy2, n2, n3, n4 - 1)) {
            return false;
        }
        return !this.h(cy2, n2, n3, n4 + 1);
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        if (cy2.a(n2, n3, n4) != this.bh) {
            return false;
        }
        if (cy2.a(n2 - 1, n3, n4) == this.bh) {
            return true;
        }
        if (cy2.a(n2 + 1, n3, n4) == this.bh) {
            return true;
        }
        if (cy2.a(n2, n3, n4 - 1) == this.bh) {
            return true;
        }
        return cy2.a(n2, n3, n4 + 1) == this.bh;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        Chest ga2 = (Chest)cy2.b(n2, n3, n4);
        for (int i2 = 0; i2 < ga2.c(); ++i2) {
            InventoryItem fp2 = ga2.c(i2);
            if (fp2 == null) continue;
            float f2 = this.a.nextFloat() * 0.8f + 0.1f;
            float f3 = this.a.nextFloat() * 0.8f + 0.1f;
            float f4 = this.a.nextFloat() * 0.8f + 0.1f;
            while (fp2.count > 0) {
                int n5 = this.a.nextInt(21) + 10;
                if (n5 > fp2.count) {
                    n5 = fp2.count;
                }
                fp2.count -= n5;
                DroppedItem eo2 = new DroppedItem(cy2, (float)n2 + f2, (float)n3 + f3, (float)n4 + f4, new InventoryItem(fp2.id, n5, fp2.damage));
                float f5 = 0.05f;
                eo2.az = (float)this.a.nextGaussian() * f5;
                eo2.aA = (float)this.a.nextGaussian() * f5 + 0.2f;
                eo2.aB = (float)this.a.nextGaussian() * f5;
                cy2.a(eo2);
            }
        }
        super.b(cy2, n2, n3, n4);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        hi hi2 = (Chest)cy2.b(n2, n3, n4);
        if (cy2.g(n2, n3 + 1, n4)) {
            return true;
        }
        if (cy2.a(n2 - 1, n3, n4) == this.bh && cy2.g(n2 - 1, n3 + 1, n4)) {
            return true;
        }
        if (cy2.a(n2 + 1, n3, n4) == this.bh && cy2.g(n2 + 1, n3 + 1, n4)) {
            return true;
        }
        if (cy2.a(n2, n3, n4 - 1) == this.bh && cy2.g(n2, n3 + 1, n4 - 1)) {
            return true;
        }
        if (cy2.a(n2, n3, n4 + 1) == this.bh && cy2.g(n2, n3 + 1, n4 + 1)) {
            return true;
        }
        if (cy2.a(n2 - 1, n3, n4) == this.bh) {
            hi2 = new ix("Large chest", (Chest)cy2.b(n2 - 1, n3, n4), hi2);
        }
        if (cy2.a(n2 + 1, n3, n4) == this.bh) {
            hi2 = new ix("Large chest", hi2, (Chest)cy2.b(n2 + 1, n3, n4));
        }
        if (cy2.a(n2, n3, n4 - 1) == this.bh) {
            hi2 = new ix("Large chest", (Chest)cy2.b(n2, n3, n4 - 1), hi2);
        }
        if (cy2.a(n2, n3, n4 + 1) == this.bh) {
            hi2 = new ix("Large chest", hi2, (Chest)cy2.b(n2, n3, n4 + 1));
        }
        eb2.a(hi2);
        return true;
    }

    @Override
    protected TileEntityRegistry a_() {
        return new Chest();
    }
}

