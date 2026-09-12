/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class hv
extends Session {
    private LinkedList A = new LinkedList();
    private ib B;
    private nr C;
    private boolean D = false;
    private ge E = new ge();
    private Set F = new HashSet();
    private Set G = new HashSet();

    public hv(ib ib2, long l2, int n2) {
        super("MpServer", oz.a(n2), l2);
        this.B = ib2;
        this.m = 8;
        this.n = 64;
        this.o = 8;
    }

    @Override
    public void i() {
        Object object;
        int n2;
        ++this.e;
        int n3 = this.a(1.0f);
        if (n3 != this.f) {
            this.f = n3;
            for (n2 = 0; n2 < this.r.size(); ++n2) {
                ((jv)this.r.get(n2)).e();
            }
        }
        for (n2 = 0; n2 < 10 && !this.G.isEmpty(); ++n2) {
            object = (lw)this.G.iterator().next();
            if (this.b.contains(object)) continue;
            this.a((lw)object);
        }
        this.B.a();
        for (n2 = 0; n2 < this.A.size(); ++n2) {
            object = (ms)this.A.get(n2);
            if (--((ms)object).d != 0) continue;
            super.a(((ms)object).a, ((ms)object).b, ((ms)object).c, ((ms)object).e, ((ms)object).f);
            super.h(((ms)object).a, ((ms)object).b, ((ms)object).c);
            this.A.remove(n2--);
        }
    }

    public void c(int n2, int n3, int n4, int n5, int n6, int n7) {
        for (int i2 = 0; i2 < this.A.size(); ++i2) {
            ms ms2 = (ms)this.A.get(i2);
            if (ms2.a < n2 || ms2.b < n3 || ms2.c < n4 || ms2.a > n5 || ms2.b > n6 || ms2.c > n7) continue;
            this.A.remove(i2--);
        }
    }

    @Override
    protected bd a(File file) {
        this.C = new nr(this);
        return this.C;
    }

    @Override
    public void b() {
        this.m = 8;
        this.n = 64;
        this.o = 8;
    }

    @Override
    protected void j() {
    }

    @Override
    public void h(int n2, int n3, int n4, int n5) {
    }

    @Override
    public boolean a(boolean bl2) {
        return false;
    }

    public void a(int n2, int n3, boolean bl2) {
        if (bl2) {
            this.C.d(n2, n3);
        } else {
            this.C.c(n2, n3);
        }
        if (!bl2) {
            this.b(n2 * 16, 0, n3 * 16, n2 * 16 + 15, 128, n3 * 16 + 15);
        }
    }

    @Override
    public boolean a(lw lw2) {
        boolean bl2 = super.a(lw2);
        this.F.add(lw2);
        if (!bl2) {
            this.G.add(lw2);
        }
        return bl2;
    }

    @Override
    public void d(lw lw2) {
        super.d(lw2);
        this.F.remove(lw2);
    }

    @Override
    protected void b(lw lw2) {
        super.b(lw2);
        if (this.G.contains(lw2)) {
            this.G.remove(lw2);
        }
    }

    @Override
    protected void c(lw lw2) {
        super.c(lw2);
        if (this.F.contains(lw2)) {
            this.G.add(lw2);
        }
    }

    public void a(int n2, lw lw2) {
        lw lw3 = this.b(n2);
        if (lw3 != null) {
            this.d(lw3);
        }
        this.F.add(lw2);
        lw2.an = n2;
        if (!this.a(lw2)) {
            this.G.add(lw2);
        }
        this.E.a(n2, lw2);
    }

    public lw b(int n2) {
        return (lw)this.E.a(n2);
    }

    public lw c(int n2) {
        lw lw2 = (lw)this.E.b(n2);
        if (lw2 != null) {
            this.F.remove(lw2);
            this.d(lw2);
        }
        return lw2;
    }

    @Override
    public boolean c(int n2, int n3, int n4, int n5) {
        int n6 = this.a(n2, n3, n4);
        int n7 = this.e(n2, n3, n4);
        if (super.c(n2, n3, n4, n5)) {
            this.A.add(new ms(this, n2, n3, n4, n6, n7));
            return true;
        }
        return false;
    }

    @Override
    public boolean a(int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.a(n2, n3, n4);
        int n8 = this.e(n2, n3, n4);
        if (super.a(n2, n3, n4, n5, n6)) {
            this.A.add(new ms(this, n2, n3, n4, n7, n8));
            return true;
        }
        return false;
    }

    @Override
    public boolean a(int n2, int n3, int n4, int n5) {
        int n6 = this.a(n2, n3, n4);
        int n7 = this.e(n2, n3, n4);
        if (super.a(n2, n3, n4, n5)) {
            this.A.add(new ms(this, n2, n3, n4, n6, n7));
            return true;
        }
        return false;
    }

    public boolean c(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3, n4, n2, n3, n4);
        if (super.a(n2, n3, n4, n5, n6)) {
            this.e(n2, n3, n4, n5);
            return true;
        }
        return false;
    }

    @Override
    public void b(int n2, int n3, int n4, TileEntityRegistry ji2) {
        if (this.D) {
            return;
        }
        this.B.a((gk)new py(n2, n3, n4, ji2));
    }

    @Override
    public void m() {
        this.B.a((gk)new qi("Quitting"));
    }
}

