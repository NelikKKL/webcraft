/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ig
extends ok {
    protected ig(int n2, int n3) {
        super(n2, n3);
        this.bg = n3;
        this.b(true);
        float f2 = 0.5f;
        this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, 0.25f, 0.5f + f2);
    }

    @Override
    protected boolean b(int n2) {
        return n2 == Block.aA.bh;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        float f2;
        int n5;
        super.a(cy2, n2, n3, n4, random);
        if (cy2.j(n2, n3 + 1, n4) >= 9 && (n5 = cy2.e(n2, n3, n4)) < 7 && random.nextInt((int)(100.0f / (f2 = this.i(cy2, n2, n3, n4)))) == 0) {
            cy2.b(n2, n3, n4, ++n5);
        }
    }

    private float i(Session cy2, int n2, int n3, int n4) {
        float f2 = 1.0f;
        int n5 = cy2.a(n2, n3, n4 - 1);
        int n6 = cy2.a(n2, n3, n4 + 1);
        int n7 = cy2.a(n2 - 1, n3, n4);
        int n8 = cy2.a(n2 + 1, n3, n4);
        int n9 = cy2.a(n2 - 1, n3, n4 - 1);
        int n10 = cy2.a(n2 + 1, n3, n4 - 1);
        int n11 = cy2.a(n2 + 1, n3, n4 + 1);
        int n12 = cy2.a(n2 - 1, n3, n4 + 1);
        boolean bl2 = n7 == this.bh || n8 == this.bh;
        boolean bl3 = n5 == this.bh || n6 == this.bh;
        boolean bl4 = n9 == this.bh || n10 == this.bh || n11 == this.bh || n12 == this.bh;
        for (int i2 = n2 - 1; i2 <= n2 + 1; ++i2) {
            for (int i3 = n4 - 1; i3 <= n4 + 1; ++i3) {
                int n13 = cy2.a(i2, n3 - 1, i3);
                float f3 = 0.0f;
                if (n13 == Block.aA.bh) {
                    f3 = 1.0f;
                    if (cy2.e(i2, n3 - 1, i3) > 0) {
                        f3 = 3.0f;
                    }
                }
                if (i2 != n2 || i3 != n4) {
                    f3 /= 4.0f;
                }
                f2 += f3;
            }
        }
        if (bl4 || bl2 && bl3) {
            f2 /= 2.0f;
        }
        return f2;
    }

    @Override
    public int a(int n2, int n3) {
        if (n3 < 0) {
            n3 = 7;
        }
        return this.bg + n3;
    }

    @Override
    public int g() {
        return 6;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, int n5) {
        super.b(cy2, n2, n3, n4, n5);
        if (!cy2.z) {
            for (int i2 = 0; i2 < 3; ++i2) {
                if (cy2.l.nextInt(15) > n5) continue;
                float f2 = 0.7f;
                float f3 = cy2.l.nextFloat() * f2 + (1.0f - f2) * 0.5f;
                float f4 = cy2.l.nextFloat() * f2 + (1.0f - f2) * 0.5f;
                float f5 = cy2.l.nextFloat() * f2 + (1.0f - f2) * 0.5f;
                DroppedItem eo2 = new DroppedItem(cy2, (float)n2 + f3, (float)n3 + f4, (float)n4 + f5, new InventoryItem(Item.Q));
                eo2.c = 10;
                cy2.a(eo2);
            }
        }
    }

    @Override
    public int a(int n2, Random random) {
        if (n2 == 7) {
            return Item.R.id;
        }
        return -1;
    }

    @Override
    public int a(Random random) {
        return 1;
    }
}

