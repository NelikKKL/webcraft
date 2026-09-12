/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class qh
extends Block {
    private int[] a = new int[256];
    private int[] b = new int[256];

    protected qh(int n2, int n3) {
        super(n2, n3, hb.l);
        this.a(Block.x.bh, 5, 20);
        this.a(Block.J.bh, 5, 5);
        this.a(Block.K.bh, 30, 60);
        this.a(Block.an.bh, 30, 20);
        this.a(Block.am.bh, 15, 100);
        this.a(Block.ab.bh, 30, 60);
        this.b(true);
    }

    private void a(int n2, int n3, int n4) {
        this.a[n2] = n3;
        this.b[n2] = n4;
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
        return 3;
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public int d() {
        return 10;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        boolean bl2 = cy2.a(n2, n3 - 1, n4) == Block.bb.bh;
        int n5 = cy2.e(n2, n3, n4);
        if (n5 < 15) {
            cy2.b(n2, n3, n4, n5 + 1);
            cy2.h(n2, n3, n4, this.bh);
        }
        if (!bl2 && !this.h(cy2, n2, n3, n4)) {
            if (!cy2.g(n2, n3 - 1, n4) || n5 > 3) {
                cy2.d(n2, n3, n4, 0);
            }
            return;
        }
        if (!bl2 && !this.c((pk)cy2, n2, n3 - 1, n4) && n5 == 15 && random.nextInt(4) == 0) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
        if (n5 % 2 == 0 && n5 > 2) {
            this.a(cy2, n2 + 1, n3, n4, 300, random);
            this.a(cy2, n2 - 1, n3, n4, 300, random);
            this.a(cy2, n2, n3 - 1, n4, 250, random);
            this.a(cy2, n2, n3 + 1, n4, 250, random);
            this.a(cy2, n2, n3, n4 - 1, 300, random);
            this.a(cy2, n2, n3, n4 + 1, 300, random);
            for (int i2 = n2 - 1; i2 <= n2 + 1; ++i2) {
                for (int i3 = n4 - 1; i3 <= n4 + 1; ++i3) {
                    for (int i4 = n3 - 1; i4 <= n3 + 4; ++i4) {
                        int n6;
                        if (i2 == n2 && i4 == n3 && i3 == n4) continue;
                        int n7 = 100;
                        if (i4 > n3 + 1) {
                            n7 += (i4 - (n3 + 1)) * 100;
                        }
                        if ((n6 = this.i(cy2, i2, i4, i3)) <= 0 || random.nextInt(n7) > n6) continue;
                        cy2.d(i2, i4, i3, this.bh);
                    }
                }
            }
        }
    }

    private void a(Session cy2, int n2, int n3, int n4, int n5, Random random) {
        int n6 = this.b[cy2.a(n2, n3, n4)];
        if (random.nextInt(n5) < n6) {
            boolean bl2;
            boolean bl3 = bl2 = cy2.a(n2, n3, n4) == Block.am.bh;
            if (random.nextInt(2) == 0) {
                cy2.d(n2, n3, n4, this.bh);
            } else {
                cy2.d(n2, n3, n4, 0);
            }
            if (bl2) {
                Block.am.b(cy2, n2, n3, n4, 0);
            }
        }
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        if (this.c((pk)cy2, n2 + 1, n3, n4)) {
            return true;
        }
        if (this.c((pk)cy2, n2 - 1, n3, n4)) {
            return true;
        }
        if (this.c((pk)cy2, n2, n3 - 1, n4)) {
            return true;
        }
        if (this.c((pk)cy2, n2, n3 + 1, n4)) {
            return true;
        }
        if (this.c((pk)cy2, n2, n3, n4 - 1)) {
            return true;
        }
        return this.c((pk)cy2, n2, n3, n4 + 1);
    }

    private int i(Session cy2, int n2, int n3, int n4) {
        int n5 = 0;
        if (cy2.a(n2, n3, n4) != 0) {
            return 0;
        }
        n5 = this.g(cy2, n2 + 1, n3, n4, n5);
        n5 = this.g(cy2, n2 - 1, n3, n4, n5);
        n5 = this.g(cy2, n2, n3 - 1, n4, n5);
        n5 = this.g(cy2, n2, n3 + 1, n4, n5);
        n5 = this.g(cy2, n2, n3, n4 - 1, n5);
        n5 = this.g(cy2, n2, n3, n4 + 1, n5);
        return n5;
    }

    @Override
    public boolean h() {
        return false;
    }

    public boolean c(pk pk2, int n2, int n3, int n4) {
        return this.a[pk2.a(n2, n3, n4)] > 0;
    }

    public int g(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = this.a[cy2.a(n2, n3, n4)];
        if (n6 > n5) {
            return n6;
        }
        return n5;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return cy2.g(n2, n3 - 1, n4) || this.h(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (!cy2.g(n2, n3 - 1, n4) && !this.h(cy2, n2, n3, n4)) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        if (cy2.a(n2, n3 - 1, n4) == Block.ap.bh && Block.be.a_(cy2, n2, n3, n4)) {
            return;
        }
        if (!cy2.g(n2, n3 - 1, n4) && !this.h(cy2, n2, n3, n4)) {
            cy2.d(n2, n3, n4, 0);
            return;
        }
        cy2.h(n2, n3, n4, this.bh);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        block12: {
            float f2;
            float f3;
            float f4;
            int n5;
            block11: {
                if (random.nextInt(24) == 0) {
                    cy2.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f);
                }
                if (!cy2.g(n2, n3 - 1, n4) && !Block.ar.c((pk)cy2, n2, n3 - 1, n4)) break block11;
                for (int i2 = 0; i2 < 3; ++i2) {
                    float f5 = (float)n2 + random.nextFloat();
                    float f6 = (float)n3 + random.nextFloat() * 0.5f + 0.5f;
                    float f7 = (float)n4 + random.nextFloat();
                    cy2.a("largesmoke", f5, f6, f7, 0.0, 0.0, 0.0);
                }
                break block12;
            }
            if (Block.ar.c((pk)cy2, n2 - 1, n3, n4)) {
                for (n5 = 0; n5 < 2; ++n5) {
                    f4 = (float)n2 + random.nextFloat() * 0.1f;
                    f3 = (float)n3 + random.nextFloat();
                    f2 = (float)n4 + random.nextFloat();
                    cy2.a("largesmoke", f4, f3, f2, 0.0, 0.0, 0.0);
                }
            }
            if (Block.ar.c((pk)cy2, n2 + 1, n3, n4)) {
                for (n5 = 0; n5 < 2; ++n5) {
                    f4 = (float)(n2 + 1) - random.nextFloat() * 0.1f;
                    f3 = (float)n3 + random.nextFloat();
                    f2 = (float)n4 + random.nextFloat();
                    cy2.a("largesmoke", f4, f3, f2, 0.0, 0.0, 0.0);
                }
            }
            if (Block.ar.c((pk)cy2, n2, n3, n4 - 1)) {
                for (n5 = 0; n5 < 2; ++n5) {
                    f4 = (float)n2 + random.nextFloat();
                    f3 = (float)n3 + random.nextFloat();
                    f2 = (float)n4 + random.nextFloat() * 0.1f;
                    cy2.a("largesmoke", f4, f3, f2, 0.0, 0.0, 0.0);
                }
            }
            if (Block.ar.c((pk)cy2, n2, n3, n4 + 1)) {
                for (n5 = 0; n5 < 2; ++n5) {
                    f4 = (float)n2 + random.nextFloat();
                    f3 = (float)n3 + random.nextFloat();
                    f2 = (float)(n4 + 1) - random.nextFloat() * 0.1f;
                    cy2.a("largesmoke", f4, f3, f2, 0.0, 0.0, 0.0);
                }
            }
            if (!Block.ar.c((pk)cy2, n2, n3 + 1, n4)) break block12;
            for (n5 = 0; n5 < 2; ++n5) {
                f4 = (float)n2 + random.nextFloat();
                f3 = (float)(n3 + 1) - random.nextFloat() * 0.1f;
                f2 = (float)n4 + random.nextFloat();
                cy2.a("largesmoke", f4, f3, f2, 0.0, 0.0, 0.0);
            }
        }
    }
}

