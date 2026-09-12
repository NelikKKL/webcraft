/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class cp
extends js {
    @Override
    public boolean a(Session cy2, Random random, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8 = 3;
        int n9 = random.nextInt(2) + 2;
        int n10 = random.nextInt(2) + 2;
        int n11 = 0;
        for (n7 = n2 - n9 - 1; n7 <= n2 + n9 + 1; ++n7) {
            for (n6 = n3 - 1; n6 <= n3 + n8 + 1; ++n6) {
                for (n5 = n4 - n10 - 1; n5 <= n4 + n10 + 1; ++n5) {
                    hb hb2 = cy2.f(n7, n6, n5);
                    if (n6 == n3 - 1 && !hb2.a()) {
                        return false;
                    }
                    if (n6 == n3 + n8 + 1 && !hb2.a()) {
                        return false;
                    }
                    if (n7 != n2 - n9 - 1 && n7 != n2 + n9 + 1 && n5 != n4 - n10 - 1 && n5 != n4 + n10 + 1 || n6 != n3 || cy2.a(n7, n6, n5) != 0 || cy2.a(n7, n6 + 1, n5) != 0) continue;
                    ++n11;
                }
            }
        }
        if (n11 < 1 || n11 > 5) {
            return false;
        }
        for (n7 = n2 - n9 - 1; n7 <= n2 + n9 + 1; ++n7) {
            for (n6 = n3 + n8; n6 >= n3 - 1; --n6) {
                for (n5 = n4 - n10 - 1; n5 <= n4 + n10 + 1; ++n5) {
                    if (n7 == n2 - n9 - 1 || n6 == n3 - 1 || n5 == n4 - n10 - 1 || n7 == n2 + n9 + 1 || n6 == n3 + n8 + 1 || n5 == n4 + n10 + 1) {
                        if (n6 >= 0 && !cy2.f(n7, n6 - 1, n5).a()) {
                            cy2.d(n7, n6, n5, 0);
                            continue;
                        }
                        if (!cy2.f(n7, n6, n5).a()) continue;
                        if (n6 == n3 - 1 && random.nextInt(4) != 0) {
                            cy2.d(n7, n6, n5, Block.ao.bh);
                            continue;
                        }
                        cy2.d(n7, n6, n5, Block.w.bh);
                        continue;
                    }
                    cy2.d(n7, n6, n5, 0);
                }
            }
        }
        block6: for (n7 = 0; n7 < 2; ++n7) {
            for (n6 = 0; n6 < 3; ++n6) {
                int n12;
                int n13;
                n5 = n2 + random.nextInt(n9 * 2 + 1) - n9;
                if (cy2.a(n5, n13 = n3, n12 = n4 + random.nextInt(n10 * 2 + 1) - n10) != 0) continue;
                int n14 = 0;
                if (cy2.f(n5 - 1, n13, n12).a()) {
                    ++n14;
                }
                if (cy2.f(n5 + 1, n13, n12).a()) {
                    ++n14;
                }
                if (cy2.f(n5, n13, n12 - 1).a()) {
                    ++n14;
                }
                if (cy2.f(n5, n13, n12 + 1).a()) {
                    ++n14;
                }
                if (n14 != 1) continue;
                cy2.d(n5, n13, n12, Block.au.bh);
                Chest ga2 = (Chest)cy2.b(n5, n13, n12);
                for (int i2 = 0; i2 < 8; ++i2) {
                    InventoryItem fp2 = this.a(random);
                    if (fp2 == null) continue;
                    ga2.a(random.nextInt(ga2.c()), fp2);
                }
                continue block6;
            }
        }
        cy2.d(n2, n3, n4, Block.as.bh);
        MobSpawner bl2 = (MobSpawner)cy2.b(n2, n3, n4);
        bl2.b = this.b(random);
        return true;
    }

    private InventoryItem a(Random random) {
        int n2 = random.nextInt(11);
        if (n2 == 0) {
            return new InventoryItem(Item.ay);
        }
        if (n2 == 1) {
            return new InventoryItem(Item.m, random.nextInt(4) + 1);
        }
        if (n2 == 2) {
            return new InventoryItem(Item.S);
        }
        if (n2 == 3) {
            return new InventoryItem(Item.R, random.nextInt(4) + 1);
        }
        if (n2 == 4) {
            return new InventoryItem(Item.K, random.nextInt(4) + 1);
        }
        if (n2 == 5) {
            return new InventoryItem(Item.I, random.nextInt(4) + 1);
        }
        if (n2 == 6) {
            return new InventoryItem(Item.au);
        }
        if (n2 == 7 && random.nextInt(100) == 0) {
            return new InventoryItem(Item.ar);
        }
        if (n2 == 8 && random.nextInt(2) == 0) {
            return new InventoryItem(Item.aA, random.nextInt(4) + 1);
        }
        if (n2 == 9 && random.nextInt(10) == 0) {
            return new InventoryItem(Item.registry[Item.aU.id + random.nextInt(2)]);
        }
        return null;
    }

    private String b(Random random) {
        int n2 = random.nextInt(4);
        if (n2 == 0) {
            return "Skeleton";
        }
        if (n2 == 1) {
            return "Zombie";
        }
        if (n2 == 2) {
            return "Zombie";
        }
        if (n2 == 3) {
            return "Spider";
        }
        return "";
    }
}

