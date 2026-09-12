/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class cs
implements pk {
    private int a;
    private int b;
    private ha[][] c;
    private Session d;

    public cs(Session cy2, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.d = cy2;
        this.a = n2 >> 4;
        this.b = n4 >> 4;
        int n8 = n5 >> 4;
        int n9 = n7 >> 4;
        this.c = new ha[n8 - this.a + 1][n9 - this.b + 1];
        for (int i2 = this.a; i2 <= n8; ++i2) {
            for (int i3 = this.b; i3 <= n9; ++i3) {
                this.c[i2 - this.a][i3 - this.b] = cy2.c(i2, i3);
            }
        }
    }

    @Override
    public int a(int n2, int n3, int n4) {
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            return 0;
        }
        int n5 = (n2 >> 4) - this.a;
        int n6 = (n4 >> 4) - this.b;
        return this.c[n5][n6].a(n2 & 0xF, n3, n4 & 0xF);
    }

    @Override
    public TileEntityRegistry b(int n2, int n3, int n4) {
        int n5 = (n2 >> 4) - this.a;
        int n6 = (n4 >> 4) - this.b;
        return this.c[n5][n6].d(n2 & 0xF, n3, n4 & 0xF);
    }

    @Override
    public float c(int n2, int n3, int n4) {
        return this.d.q.f[this.d(n2, n3, n4)];
    }

    public int d(int n2, int n3, int n4) {
        return this.a(n2, n3, n4, true);
    }

    public int a(int n2, int n3, int n4, boolean bl2) {
        int n5;
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return 15;
        }
        if (bl2 && ((n5 = this.a(n2, n3, n4)) == Block.ak.bh || n5 == Block.aA.bh)) {
            int n6 = this.a(n2, n3 + 1, n4, false);
            int n7 = this.a(n2 + 1, n3, n4, false);
            int n8 = this.a(n2 - 1, n3, n4, false);
            int n9 = this.a(n2, n3, n4 + 1, false);
            int n10 = this.a(n2, n3, n4 - 1, false);
            if (n7 > n6) {
                n6 = n7;
            }
            if (n8 > n6) {
                n6 = n8;
            }
            if (n9 > n6) {
                n6 = n9;
            }
            if (n10 > n6) {
                n6 = n10;
            }
            return n6;
        }
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            n5 = 15 - this.d.f;
            if (n5 < 0) {
                n5 = 0;
            }
            return n5;
        }
        n5 = (n2 >> 4) - this.a;
        int n11 = (n4 >> 4) - this.b;
        return this.c[n5][n11].c(n2 & 0xF, n3, n4 & 0xF, this.d.f);
    }

    @Override
    public int e(int n2, int n3, int n4) {
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            return 0;
        }
        int n5 = (n2 >> 4) - this.a;
        int n6 = (n4 >> 4) - this.b;
        return this.c[n5][n6].b(n2 & 0xF, n3, n4 & 0xF);
    }

    @Override
    public hb f(int n2, int n3, int n4) {
        int n5 = this.a(n2, n3, n4);
        if (n5 == 0) {
            return hb.a;
        }
        return Block.registry[n5].bs;
    }

    @Override
    public boolean g(int n2, int n3, int n4) {
        Block nq2 = Block.registry[this.a(n2, n3, n4)];
        if (nq2 == null) {
            return false;
        }
        return nq2.a();
    }

    @Override
    public po a() {
        return this.d.a();
    }
}

