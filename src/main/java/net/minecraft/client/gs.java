/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.IOException;

public class gs
implements bd {
    private ha c;
    private bd d;
    private ai e;
    private ha[] f = new ha[1024];
    private Session g;
    int a = -999999999;
    int b = -999999999;
    private ha h;

    public gs(Session cy2, ai ai2, bd bd2) {
        this.c = new ha(cy2, new byte[32768], 0, 0);
        this.c.q = true;
        this.c.p = true;
        this.g = cy2;
        this.e = ai2;
        this.d = bd2;
    }

    @Override
    public boolean a(int n2, int n3) {
        if (n2 == this.a && n3 == this.b && this.h != null) {
            return true;
        }
        int n4 = n2 & 0x1F;
        int n5 = n3 & 0x1F;
        int n6 = n4 + n5 * 32;
        return this.f[n6] != null && (this.f[n6] == this.c || this.f[n6].a(n2, n3));
    }

    @Override
    public ha b(int n2, int n3) {
        if (n2 == this.a && n3 == this.b && this.h != null) {
            return this.h;
        }
        int n4 = n2 & 0x1F;
        int n5 = n3 & 0x1F;
        int n6 = n4 + n5 * 32;
        if (!this.a(n2, n3)) {
            ha ha2;
            if (this.f[n6] != null) {
                this.f[n6].f();
                this.b(this.f[n6]);
                this.a(this.f[n6]);
            }
            if ((ha2 = this.c(n2, n3)) == null) {
                ha2 = this.d == null ? this.c : this.d.b(n2, n3);
            }
            this.f[n6] = ha2;
            ha2.d();
            if (this.f[n6] != null) {
                this.f[n6].e();
            }
            if (!this.f[n6].n && this.a(n2 + 1, n3 + 1) && this.a(n2, n3 + 1) && this.a(n2 + 1, n3)) {
                this.a(this, n2, n3);
            }
            if (this.a(n2 - 1, n3) && !this.b((int)(n2 - 1), (int)n3).n && this.a(n2 - 1, n3 + 1) && this.a(n2, n3 + 1) && this.a(n2 - 1, n3)) {
                this.a(this, n2 - 1, n3);
            }
            if (this.a(n2, n3 - 1) && !this.b((int)n2, (int)(n3 - 1)).n && this.a(n2 + 1, n3 - 1) && this.a(n2, n3 - 1) && this.a(n2 + 1, n3)) {
                this.a(this, n2, n3 - 1);
            }
            if (this.a(n2 - 1, n3 - 1) && !this.b((int)(n2 - 1), (int)(n3 - 1)).n && this.a(n2 - 1, n3 - 1) && this.a(n2, n3 - 1) && this.a(n2 - 1, n3)) {
                this.a(this, n2 - 1, n3 - 1);
            }
        }
        this.a = n2;
        this.b = n3;
        this.h = this.f[n6];
        return this.f[n6];
    }

    private ha c(int n2, int n3) {
        if (this.e == null) {
            return null;
        }
        try {
            ha ha2 = this.e.a(this.g, n2, n3);
            if (ha2 != null) {
                ha2.s = this.g.e;
            }
            return ha2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void a(ha ha2) {
        if (this.e == null) {
            return;
        }
        try {
            this.e.b(this.g, ha2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void b(ha ha2) {
        if (this.e == null) {
            return;
        }
        ha2.s = this.g.e;
        this.e.a(this.g, ha2);
    }

    @Override
    public void a(bd bd2, int n2, int n3) {
        ha ha2 = this.b(n2, n3);
        if (!ha2.n) {
            ha2.n = true;
            if (this.d != null) {
                this.d.a(bd2, n2, n3);
                ha2.g();
            }
        }
    }

    @Override
    public boolean a(boolean bl2, pu pu2) {
        int n2;
        int n3 = 0;
        int n4 = 0;
        if (pu2 != null) {
            for (n2 = 0; n2 < this.f.length; ++n2) {
                if (this.f[n2] == null || !this.f[n2].a(bl2)) continue;
                ++n4;
            }
        }
        n2 = 0;
        for (int i2 = 0; i2 < this.f.length; ++i2) {
            if (this.f[i2] == null) continue;
            if (bl2 && !this.f[i2].p) {
                this.a(this.f[i2]);
            }
            if (!this.f[i2].a(bl2)) continue;
            this.b(this.f[i2]);
            this.f[i2].o = false;
            if (++n3 == 2 && !bl2) {
                return false;
            }
            if (pu2 == null || ++n2 % 10 != 0) continue;
            pu2.a(n2 * 100 / n4);
        }
        if (bl2) {
            if (this.e == null) {
                return true;
            }
            this.e.b();
        }
        return true;
    }

    @Override
    public boolean a() {
        if (this.e != null) {
            this.e.a();
        }
        return this.d.a();
    }

    @Override
    public boolean b() {
        return true;
    }
}

