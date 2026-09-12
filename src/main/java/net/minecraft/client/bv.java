/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class bv
extends js {
    private int a;

    public bv(int n2) {
        this.a = n2;
    }

    @Override
    public boolean a(Session cy2, Random random, int n2, int n3, int n4) {
        int n5;
        int n6;
        n2 -= 8;
        n4 -= 8;
        while (n3 > 0 && cy2.a(n2, n3, n4) == 0) {
            --n3;
        }
        n3 -= 4;
        boolean[] blArray = new boolean[2048];
        int n7 = random.nextInt(4) + 4;
        for (n6 = 0; n6 < n7; ++n6) {
            double d2 = random.nextDouble() * 6.0 + 3.0;
            double d3 = random.nextDouble() * 4.0 + 2.0;
            double d4 = random.nextDouble() * 6.0 + 3.0;
            double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;
            double d6 = random.nextDouble() * (8.0 - d3 - 4.0) + 2.0 + d3 / 2.0;
            double d7 = random.nextDouble() * (16.0 - d4 - 2.0) + 1.0 + d4 / 2.0;
            for (int i2 = 1; i2 < 15; ++i2) {
                for (int i3 = 1; i3 < 15; ++i3) {
                    for (int i4 = 1; i4 < 7; ++i4) {
                        double d8 = ((double)i2 - d5) / (d2 / 2.0);
                        double d9 = ((double)i4 - d6) / (d3 / 2.0);
                        double d10 = ((double)i3 - d7) / (d4 / 2.0);
                        double d11 = d8 * d8 + d9 * d9 + d10 * d10;
                        if (!(d11 < 1.0)) continue;
                        blArray[(i2 * 16 + i3) * 8 + i4] = true;
                    }
                }
            }
        }
        for (n6 = 0; n6 < 16; ++n6) {
            for (int i5 = 0; i5 < 16; ++i5) {
                for (n5 = 0; n5 < 8; ++n5) {
                    boolean bl2;
                    boolean bl3 = bl2 = !blArray[(n6 * 16 + i5) * 8 + n5] && (n6 < 15 && blArray[((n6 + 1) * 16 + i5) * 8 + n5] || n6 > 0 && blArray[((n6 - 1) * 16 + i5) * 8 + n5] || i5 < 15 && blArray[(n6 * 16 + (i5 + 1)) * 8 + n5] || i5 > 0 && blArray[(n6 * 16 + (i5 - 1)) * 8 + n5] || n5 < 7 && blArray[(n6 * 16 + i5) * 8 + (n5 + 1)] || n5 > 0 && blArray[(n6 * 16 + i5) * 8 + (n5 - 1)]);
                    if (!bl2) continue;
                    hb hb2 = cy2.f(n2 + n6, n3 + n5, n4 + i5);
                    if (n5 >= 4 && hb2.d()) {
                        return false;
                    }
                    if (n5 >= 4 || hb2.a() || cy2.a(n2 + n6, n3 + n5, n4 + i5) == this.a) continue;
                    return false;
                }
            }
        }
        for (n6 = 0; n6 < 16; ++n6) {
            for (int i6 = 0; i6 < 16; ++i6) {
                for (n5 = 0; n5 < 8; ++n5) {
                    if (!blArray[(n6 * 16 + i6) * 8 + n5]) continue;
                    cy2.d(n2 + n6, n3 + n5, n4 + i6, n5 >= 4 ? 0 : this.a);
                }
            }
        }
        for (n6 = 0; n6 < 16; ++n6) {
            for (int i7 = 0; i7 < 16; ++i7) {
                for (n5 = 4; n5 < 8; ++n5) {
                    if (!blArray[(n6 * 16 + i7) * 8 + n5] || cy2.a(n2 + n6, n3 + n5 - 1, n4 + i7) != Block.v.bh || cy2.a(ch.Sky, n2 + n6, n3 + n5, n4 + i7) <= 0) continue;
                    cy2.d(n2 + n6, n3 + n5 - 1, n4 + i7, Block.u.bh);
                }
            }
        }
        return true;
    }
}

