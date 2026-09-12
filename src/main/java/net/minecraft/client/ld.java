/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public abstract class ld
extends Block {
    protected ld(int n2, hb hb2) {
        super(n2, (hb2 == hb.g ? 14 : 12) * 16 + 13, hb2);
        float f2 = 0.0f;
        float f3 = 0.0f;
        this.a(0.0f + f3, 0.0f + f2, 0.0f + f3, 1.0f + f3, 1.0f + f2, 1.0f + f3);
        this.b(true);
    }

    public static float b(int n2) {
        if (n2 >= 8) {
            n2 = 0;
        }
        float f2 = (float)(n2 + 1) / 9.0f;
        return f2;
    }

    @Override
    public int a(int n2) {
        if (n2 == 0 || n2 == 1) {
            return this.bg;
        }
        return this.bg + 1;
    }

    protected int h(Session cy2, int n2, int n3, int n4) {
        if (cy2.f(n2, n3, n4) != this.bs) {
            return -1;
        }
        return cy2.e(n2, n3, n4);
    }

    protected int c(pk pk2, int n2, int n3, int n4) {
        if (pk2.f(n2, n3, n4) != this.bs) {
            return -1;
        }
        int n5 = pk2.e(n2, n3, n4);
        if (n5 >= 8) {
            n5 = 0;
        }
        return n5;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean a(int n2, boolean bl2) {
        return bl2 && n2 == 0;
    }

    @Override
    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        hb hb2 = pk2.f(n2, n3, n4);
        if (hb2 == this.bs) {
            return false;
        }
        if (hb2 == hb.r) {
            return false;
        }
        if (n5 == 1) {
            return true;
        }
        return super.b(pk2, n2, n3, n4, n5);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public int g() {
        return 4;
    }

    @Override
    public int a(int n2, Random random) {
        return 0;
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    private Vector3D e(pk pk2, int n2, int n3, int n4) {
        int n5;
        Vector3D ao2 = Vector3D.b(0.0, 0.0, 0.0);
        int n6 = this.c(pk2, n2, n3, n4);
        for (n5 = 0; n5 < 4; ++n5) {
            int n7;
            int n8;
            int n9 = n2;
            int n10 = n3;
            int n11 = n4;
            if (n5 == 0) {
                --n9;
            }
            if (n5 == 1) {
                --n11;
            }
            if (n5 == 2) {
                ++n9;
            }
            if (n5 == 3) {
                ++n11;
            }
            if ((n8 = this.c(pk2, n9, n10, n11)) < 0) {
                if (pk2.f(n9, n10, n11).c() || (n8 = this.c(pk2, n9, n10 - 1, n11)) < 0) continue;
                n7 = n8 - (n6 - 8);
                ao2 = ao2.c((n9 - n2) * n7, (n10 - n3) * n7, (n11 - n4) * n7);
                continue;
            }
            if (n8 < 0) continue;
            n7 = n8 - n6;
            ao2 = ao2.c((n9 - n2) * n7, (n10 - n3) * n7, (n11 - n4) * n7);
        }
        if (pk2.e(n2, n3, n4) >= 8) {
            n5 = 0;
            if (n5 != 0 || this.b(pk2, n2, n3, n4 - 1, 2)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2, n3, n4 + 1, 3)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2 - 1, n3, n4, 4)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2 + 1, n3, n4, 5)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2, n3 + 1, n4 - 1, 2)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2, n3 + 1, n4 + 1, 3)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2 - 1, n3 + 1, n4, 4)) {
                n5 = 1;
            }
            if (n5 != 0 || this.b(pk2, n2 + 1, n3 + 1, n4, 5)) {
                n5 = 1;
            }
            if (n5 != 0) {
                ao2 = ao2.b().c(0.0, -6.0, 0.0);
            }
        }
        ao2 = ao2.b();
        return ao2;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2, Vector3D ao2) {
        Vector3D ao3 = this.e((pk)cy2, n2, n3, n4);
        ao2.x += ao3.x;
        ao2.y += ao3.y;
        ao2.z += ao3.z;
    }

    @Override
    public int d() {
        if (this.bs == hb.f) {
            return 5;
        }
        if (this.bs == hb.g) {
            return 30;
        }
        return 0;
    }

    @Override
    public float d(pk pk2, int n2, int n3, int n4) {
        float f2;
        float f3 = pk2.c(n2, n3, n4);
        return f3 > (f2 = pk2.c(n2, n3 + 1, n4)) ? f3 : f2;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        super.a(cy2, n2, n3, n4, random);
    }

    @Override
    public int c() {
        return this.bs == hb.f ? 1 : 0;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        int n5;
        if (this.bs == hb.f && random.nextInt(64) == 0 && (n5 = cy2.e(n2, n3, n4)) > 0 && n5 < 8) {
            cy2.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), "liquid.water", random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f);
        }
        if (this.bs == hb.g && cy2.f(n2, n3 + 1, n4) == hb.a && !cy2.g(n2, n3 + 1, n4) && random.nextInt(100) == 0) {
            double d2 = (float)n2 + random.nextFloat();
            double d3 = (double)n3 + this.bo;
            double d4 = (float)n4 + random.nextFloat();
            cy2.a("lava", d2, d3, d4, 0.0, 0.0, 0.0);
        }
    }

    public static double a(pk pk2, int n2, int n3, int n4, hb hb2) {
        Vector3D ao2 = null;
        if (hb2 == hb.f) {
            ao2 = ((ld)Block.A).e(pk2, n2, n3, n4);
        }
        if (hb2 == hb.g) {
            ao2 = ((ld)Block.C).e(pk2, n2, n3, n4);
        }
        if (ao2.x == 0.0 && ao2.z == 0.0) {
            return -1000.0;
        }
        return Math.atan2(ao2.z, ao2.x) - 1.5707963267948966;
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        this.j(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        this.j(cy2, n2, n3, n4);
    }

    private void j(Session cy2, int n2, int n3, int n4) {
        if (cy2.a(n2, n3, n4) != this.bh) {
            return;
        }
        if (this.bs == hb.g) {
            boolean bl2 = false;
            if (bl2 || cy2.f(n2, n3, n4 - 1) == hb.f) {
                bl2 = true;
            }
            if (bl2 || cy2.f(n2, n3, n4 + 1) == hb.f) {
                bl2 = true;
            }
            if (bl2 || cy2.f(n2 - 1, n3, n4) == hb.f) {
                bl2 = true;
            }
            if (bl2 || cy2.f(n2 + 1, n3, n4) == hb.f) {
                bl2 = true;
            }
            if (bl2 || cy2.f(n2, n3 + 1, n4) == hb.f) {
                bl2 = true;
            }
            if (bl2) {
                int n5 = cy2.e(n2, n3, n4);
                if (n5 == 0) {
                    cy2.d(n2, n3, n4, Block.ap.bh);
                } else if (n5 <= 4) {
                    cy2.d(n2, n3, n4, Block.w.bh);
                }
                this.i(cy2, n2, n3, n4);
            }
        }
    }

    protected void i(Session cy2, int n2, int n3, int n4) {
        cy2.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), "random.fizz", 0.5f, 2.6f + (cy2.l.nextFloat() - cy2.l.nextFloat()) * 0.8f);
        for (int i2 = 0; i2 < 8; ++i2) {
            cy2.a("largesmoke", (double)n2 + Math.random(), (double)n3 + 1.2, (double)n4 + Math.random(), 0.0, 0.0, 0.0);
        }
    }
}

