/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class boclass
extends ob {
    private boolean a = false;
    static private List b = new ArrayList();

    @Override
    public int a(int n2, int n3) {
        if (n2 == 1) {
            return Block.av.a(n2, n3);
        }
        return super.a(n2, n3);
    }

    private boolean a(Session cy2, int n2, int n3, int n4, boolean bl2) {
        if (bl2) {
            b.add(new pn(n2, n3, n4, cy2.e));
        }
        int n5 = 0;
        for (int i2 = 0; i2 < b.size(); ++i2) {
            pn pn2 = (pn)b.get(i2);
            if (pn2.a != n2 || pn2.b != n3 || pn2.c != n4 || ++n5 < 8) continue;
            return true;
        }
        return false;
    }

    protected boclass(int n2, int n3, boolean bl2) {
        super(n2, n3);
        this.a = bl2;
        this.b(true);
    }

    @Override
    public int d() {
        return 2;
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        if (cy2.e(n2, n3, n4) == 0) {
            super.e(cy2, n2, n3, n4);
        }
        if (this.a) {
            cy2.g(n2, n3 - 1, n4, this.bh);
            cy2.g(n2, n3 + 1, n4, this.bh);
            cy2.g(n2 - 1, n3, n4, this.bh);
            cy2.g(n2 + 1, n3, n4, this.bh);
            cy2.g(n2, n3, n4 - 1, this.bh);
            cy2.g(n2, n3, n4 + 1, this.bh);
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        if (this.a) {
            cy2.g(n2, n3 - 1, n4, this.bh);
            cy2.g(n2, n3 + 1, n4, this.bh);
            cy2.g(n2 - 1, n3, n4, this.bh);
            cy2.g(n2 + 1, n3, n4, this.bh);
            cy2.g(n2, n3, n4 - 1, this.bh);
            cy2.g(n2, n3, n4 + 1, this.bh);
        }
    }

    @Override
    public boolean c(pk pk2, int n2, int n3, int n4, int n5) {
        if (!this.a) {
            return false;
        }
        int n6 = pk2.e(n2, n3, n4);
        if (n6 == 5 && n5 == 1) {
            return false;
        }
        if (n6 == 3 && n5 == 3) {
            return false;
        }
        if (n6 == 4 && n5 == 2) {
            return false;
        }
        if (n6 == 1 && n5 == 5) {
            return false;
        }
        return n6 != 2 || n5 != 4;
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        if (n5 == 5 && cy2.k(n2, n3 - 1, n4, 0)) {
            return true;
        }
        if (n5 == 3 && cy2.k(n2, n3, n4 - 1, 2)) {
            return true;
        }
        if (n5 == 4 && cy2.k(n2, n3, n4 + 1, 3)) {
            return true;
        }
        if (n5 == 1 && cy2.k(n2 - 1, n3, n4, 4)) {
            return true;
        }
        return n5 == 2 && cy2.k(n2 + 1, n3, n4, 5);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        boolean bl2 = this.h(cy2, n2, n3, n4);
        while (!b.isEmpty() && cy2.e - ((pn)boclass.b.get(0)).d > 100L) {
            b.remove(0);
        }
        if (this.a) {
            if (bl2) {
                cy2.b(n2, n3, n4, Block.aP.bh, cy2.e(n2, n3, n4));
                if (this.a(cy2, n2, n3, n4, true)) {
                    cy2.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), "random.fizz", 0.5f, 2.6f + (cy2.l.nextFloat() - cy2.l.nextFloat()) * 0.8f);
                    for (int i2 = 0; i2 < 5; ++i2) {
                        double d2 = (double)n2 + random.nextDouble() * 0.6 + 0.2;
                        double d3 = (double)n3 + random.nextDouble() * 0.6 + 0.2;
                        double d4 = (double)n4 + random.nextDouble() * 0.6 + 0.2;
                        cy2.a("smoke", d2, d3, d4, 0.0, 0.0, 0.0);
                    }
                }
            }
        } else if (!bl2 && !this.a(cy2, n2, n3, n4, false)) {
            cy2.b(n2, n3, n4, Block.aQ.bh, cy2.e(n2, n3, n4));
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        super.a(cy2, n2, n3, n4, n5);
        cy2.h(n2, n3, n4, this.bh);
    }

    @Override
    public boolean c(Session cy2, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            return this.c((pk)cy2, n2, n3, n4, n5);
        }
        return false;
    }

    @Override
    public int a(int n2, Random random) {
        return Block.aQ.bh;
    }

    @Override
    public boolean e() {
        return true;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        if (!this.a) {
            return;
        }
        int n5 = cy2.e(n2, n3, n4);
        double d2 = (double)((float)n2 + 0.5f) + (double)(random.nextFloat() - 0.5f) * 0.2;
        double d3 = (double)((float)n3 + 0.7f) + (double)(random.nextFloat() - 0.5f) * 0.2;
        double d4 = (double)((float)n4 + 0.5f) + (double)(random.nextFloat() - 0.5f) * 0.2;
        double d5 = 0.22f;
        double d6 = 0.27f;
        if (n5 == 1) {
            cy2.a("reddust", d2 - d6, d3 + d5, d4, 0.0, 0.0, 0.0);
        } else if (n5 == 2) {
            cy2.a("reddust", d2 + d6, d3 + d5, d4, 0.0, 0.0, 0.0);
        } else if (n5 == 3) {
            cy2.a("reddust", d2, d3 + d5, d4 - d6, 0.0, 0.0, 0.0);
        } else if (n5 == 4) {
            cy2.a("reddust", d2, d3 + d5, d4 + d6, 0.0, 0.0, 0.0);
        } else {
            cy2.a("reddust", d2, d3, d4, 0.0, 0.0, 0.0);
        }
    }
}

