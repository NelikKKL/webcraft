/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class lu
extends Block {
    private boolean a = true;

    public lu(int n2, int n3) {
        super(n2, n3, hb.n);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }

    @Override
    public int a(int n2, int n3) {
        return this.bg + (n3 > 0 ? 16 : 0);
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
        return 5;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        return cy2.g(n2, n3 - 1, n4);
    }

    private void h(Session cy2, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8 = cy2.e(n2, n3, n4);
        int n9 = 0;
        this.a = false;
        boolean bl2 = cy2.o(n2, n3, n4);
        this.a = true;
        if (bl2) {
            n9 = 15;
        } else {
            for (n7 = 0; n7 < 4; ++n7) {
                n6 = n2;
                n5 = n4;
                if (n7 == 0) {
                    --n6;
                }
                if (n7 == 1) {
                    ++n6;
                }
                if (n7 == 2) {
                    --n5;
                }
                if (n7 == 3) {
                    ++n5;
                }
                n9 = this.g(cy2, n6, n3, n5, n9);
                if (cy2.g(n6, n3, n5) && !cy2.g(n2, n3 + 1, n4)) {
                    n9 = this.g(cy2, n6, n3 + 1, n5, n9);
                    continue;
                }
                if (cy2.g(n6, n3, n5)) continue;
                n9 = this.g(cy2, n6, n3 - 1, n5, n9);
            }
            n9 = n9 > 0 ? --n9 : 0;
        }
        if (n8 != n9) {
            cy2.b(n2, n3, n4, n9);
            cy2.b(n2, n3, n4, n2, n3, n4);
            if (n9 > 0) {
                --n9;
            }
            for (n7 = 0; n7 < 4; ++n7) {
                int n10;
                n6 = n2;
                n5 = n4;
                int n11 = n3 - 1;
                if (n7 == 0) {
                    --n6;
                }
                if (n7 == 1) {
                    ++n6;
                }
                if (n7 == 2) {
                    --n5;
                }
                if (n7 == 3) {
                    ++n5;
                }
                if (cy2.g(n6, n3, n5)) {
                    n11 += 2;
                }
                if ((n10 = this.g(cy2, n6, n3, n5, -1)) >= 0 && n10 != n9) {
                    this.h(cy2, n6, n3, n5);
                }
                if ((n10 = this.g(cy2, n6, n11, n5, -1)) < 0 || n10 == n9) continue;
                this.h(cy2, n6, n11, n5);
            }
            if (n8 == 0 || n9 == 0) {
                cy2.g(n2, n3, n4, this.bh);
                cy2.g(n2 - 1, n3, n4, this.bh);
                cy2.g(n2 + 1, n3, n4, this.bh);
                cy2.g(n2, n3, n4 - 1, this.bh);
                cy2.g(n2, n3, n4 + 1, this.bh);
                cy2.g(n2, n3 - 1, n4, this.bh);
                cy2.g(n2, n3 + 1, n4, this.bh);
            }
        }
    }

    private void i(Session cy2, int n2, int n3, int n4) {
        if (cy2.a(n2, n3, n4) != this.bh) {
            return;
        }
        cy2.g(n2, n3, n4, this.bh);
        cy2.g(n2 - 1, n3, n4, this.bh);
        cy2.g(n2 + 1, n3, n4, this.bh);
        cy2.g(n2, n3, n4 - 1, this.bh);
        cy2.g(n2, n3, n4 + 1, this.bh);
        cy2.g(n2, n3 - 1, n4, this.bh);
        cy2.g(n2, n3 + 1, n4, this.bh);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        super.e(cy2, n2, n3, n4);
        if (cy2.z) {
            return;
        }
        this.h(cy2, n2, n3, n4);
        cy2.g(n2, n3 + 1, n4, this.bh);
        cy2.g(n2, n3 - 1, n4, this.bh);
        this.i(cy2, n2 - 1, n3, n4);
        this.i(cy2, n2 + 1, n3, n4);
        this.i(cy2, n2, n3, n4 - 1);
        this.i(cy2, n2, n3, n4 + 1);
        if (cy2.g(n2 - 1, n3, n4)) {
            this.i(cy2, n2 - 1, n3 + 1, n4);
        } else {
            this.i(cy2, n2 - 1, n3 - 1, n4);
        }
        if (cy2.g(n2 + 1, n3, n4)) {
            this.i(cy2, n2 + 1, n3 + 1, n4);
        } else {
            this.i(cy2, n2 + 1, n3 - 1, n4);
        }
        if (cy2.g(n2, n3, n4 - 1)) {
            this.i(cy2, n2, n3 + 1, n4 - 1);
        } else {
            this.i(cy2, n2, n3 - 1, n4 - 1);
        }
        if (cy2.g(n2, n3, n4 + 1)) {
            this.i(cy2, n2, n3 + 1, n4 + 1);
        } else {
            this.i(cy2, n2, n3 - 1, n4 + 1);
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        super.b(cy2, n2, n3, n4);
        if (cy2.z) {
            return;
        }
        cy2.g(n2, n3 + 1, n4, this.bh);
        cy2.g(n2, n3 - 1, n4, this.bh);
        this.h(cy2, n2, n3, n4);
        this.i(cy2, n2 - 1, n3, n4);
        this.i(cy2, n2 + 1, n3, n4);
        this.i(cy2, n2, n3, n4 - 1);
        this.i(cy2, n2, n3, n4 + 1);
        if (cy2.g(n2 - 1, n3, n4)) {
            this.i(cy2, n2 - 1, n3 + 1, n4);
        } else {
            this.i(cy2, n2 - 1, n3 - 1, n4);
        }
        if (cy2.g(n2 + 1, n3, n4)) {
            this.i(cy2, n2 + 1, n3 + 1, n4);
        } else {
            this.i(cy2, n2 + 1, n3 - 1, n4);
        }
        if (cy2.g(n2, n3, n4 - 1)) {
            this.i(cy2, n2, n3 + 1, n4 - 1);
        } else {
            this.i(cy2, n2, n3 - 1, n4 - 1);
        }
        if (cy2.g(n2, n3, n4 + 1)) {
            this.i(cy2, n2, n3 + 1, n4 + 1);
        } else {
            this.i(cy2, n2, n3 - 1, n4 + 1);
        }
    }

    private int g(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.a(n2, n3, n4) != this.bh) {
            return n5;
        }
        int n6 = cy2.e(n2, n3, n4);
        if (n6 > n5) {
            return n6;
        }
        return n5;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.z) {
            return;
        }
        int n6 = cy2.e(n2, n3, n4);
        boolean bl2 = this.a(cy2, n2, n3, n4);
        if (!bl2) {
            this.b_(cy2, n2, n3, n4, n6);
            cy2.d(n2, n3, n4, 0);
        } else {
            this.h(cy2, n2, n3, n4);
        }
        super.a(cy2, n2, n3, n4, n5);
    }

    @Override
    public int a(int n2, Random random) {
        return Item.aA.id;
    }

    @Override
    public boolean c(Session cy2, int n2, int n3, int n4, int n5) {
        if (!this.a) {
            return false;
        }
        return this.c((pk)cy2, n2, n3, n4, n5);
    }

    @Override
    public boolean c(pk pk2, int n2, int n3, int n4, int n5) {
        boolean bl2;
        if (!this.a) {
            return false;
        }
        if (pk2.e(n2, n3, n4) == 0) {
            return false;
        }
        if (n5 == 1) {
            return true;
        }
        boolean bl3 = lu.c(pk2, n2 - 1, n3, n4) || !pk2.g(n2 - 1, n3, n4) && lu.c(pk2, n2 - 1, n3 - 1, n4);
        boolean bl4 = lu.c(pk2, n2 + 1, n3, n4) || !pk2.g(n2 + 1, n3, n4) && lu.c(pk2, n2 + 1, n3 - 1, n4);
        boolean bl5 = lu.c(pk2, n2, n3, n4 - 1) || !pk2.g(n2, n3, n4 - 1) && lu.c(pk2, n2, n3 - 1, n4 - 1);
        boolean bl6 = bl2 = lu.c(pk2, n2, n3, n4 + 1) || !pk2.g(n2, n3, n4 + 1) && lu.c(pk2, n2, n3 - 1, n4 + 1);
        if (!pk2.g(n2, n3 + 1, n4)) {
            if (pk2.g(n2 - 1, n3, n4) && lu.c(pk2, n2 - 1, n3 + 1, n4)) {
                bl3 = true;
            }
            if (pk2.g(n2 + 1, n3, n4) && lu.c(pk2, n2 + 1, n3 + 1, n4)) {
                bl4 = true;
            }
            if (pk2.g(n2, n3, n4 - 1) && lu.c(pk2, n2, n3 + 1, n4 - 1)) {
                bl5 = true;
            }
            if (pk2.g(n2, n3, n4 + 1) && lu.c(pk2, n2, n3 + 1, n4 + 1)) {
                bl2 = true;
            }
        }
        if (!(bl5 || bl4 || bl3 || bl2 || n5 < 2 || n5 > 5)) {
            return true;
        }
        if (n5 == 2 && bl5 && !bl3 && !bl4) {
            return true;
        }
        if (n5 == 3 && bl2 && !bl3 && !bl4) {
            return true;
        }
        if (n5 == 4 && bl3 && !bl5 && !bl2) {
            return true;
        }
        return n5 == 5 && bl4 && !bl5 && !bl2;
    }

    @Override
    public boolean e() {
        return this.a;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.e(n2, n3, n4) > 0) {
            double d2 = (double)n2 + 0.5 + ((double)random.nextFloat() - 0.5) * 0.2;
            double d3 = (float)n3 + 0.0625f;
            double d4 = (double)n4 + 0.5 + ((double)random.nextFloat() - 0.5) * 0.2;
            cy2.a("reddust", d2, d3, d4, 0.0, 0.0, 0.0);
        }
    }

    public static boolean c(pk pk2, int n2, int n3, int n4) {
        int n5 = pk2.a(n2, n3, n4);
        if (n5 == Block.av.bh) {
            return true;
        }
        if (n5 == 0) {
            return false;
        }
        return Block.registry[n5].e();
    }
}

