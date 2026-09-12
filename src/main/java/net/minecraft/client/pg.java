/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;

public class pg
extends iv {
    private int c = -1;
    private int d = -1;
    private int e = -1;
    private float f = 0.0f;
    private float g = 0.0f;
    private float h = 0.0f;
    private int i = 0;
    private boolean j = false;
    private ib k;
    private int l = 0;

    public pg(Minecraft minecraft, ib ib2) {
        super(minecraft);
        this.k = ib2;
    }

    @Override
    public void a(Player eb2) {
        eb2.aC = -180.0f;
    }

    @Override
    public boolean b(int n2, int n3, int n4, int n5) {
        this.k.a((gk)new gc(3, n2, n3, n4, n5));
        int n6 = this.a.e.a(n2, n3, n4);
        int n7 = this.a.e.e(n2, n3, n4);
        boolean bl2 = super.b(n2, n3, n4, n5);
        InventoryItem fp2 = this.a.g.w();
        if (fp2 != null) {
            fp2.a(n6, n2, n3, n4);
            if (fp2.count == 0) {
                fp2.a(this.a.g);
                this.a.g.x();
            }
        }
        return bl2;
    }

    @Override
    public void a(int n2, int n3, int n4, int n5) {
        this.j = true;
        this.k.a((gk)new gc(0, n2, n3, n4, n5));
        int n6 = this.a.e.a(n2, n3, n4);
        if (n6 > 0 && this.f == 0.0f) {
            Block.registry[n6].b(this.a.e, n2, n3, n4, this.a.g);
        }
        if (n6 > 0 && Block.registry[n6].a(this.a.g) >= 1.0f) {
            this.b(n2, n3, n4, n5);
        }
    }

    @Override
    public void a() {
        if (!this.j) {
            return;
        }
        this.j = false;
        this.k.a((gk)new gc(2, 0, 0, 0, 0));
        this.f = 0.0f;
        this.i = 0;
    }

    @Override
    public void c(int n2, int n3, int n4, int n5) {
        this.j = true;
        this.e();
        this.k.a((gk)new gc(1, n2, n3, n4, n5));
        if (this.i > 0) {
            --this.i;
            return;
        }
        if (n2 == this.c && n3 == this.d && n4 == this.e) {
            int n6 = this.a.e.a(n2, n3, n4);
            if (n6 == 0) {
                return;
            }
            Block nq2 = Block.registry[n6];
            this.f += nq2.a(this.a.g);
            if (this.h % 4.0f == 0.0f && nq2 != null) {
                this.a.A.b(nq2.bq.d(), (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f, (nq2.bq.b() + 1.0f) / 8.0f, nq2.bq.c() * 0.5f);
            }
            this.h += 1.0f;
            if (this.f >= 1.0f) {
                this.b(n2, n3, n4, n5);
                this.f = 0.0f;
                this.g = 0.0f;
                this.h = 0.0f;
                this.i = 5;
            }
        } else {
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 0.0f;
            this.c = n2;
            this.d = n3;
            this.e = n4;
        }
    }

    @Override
    public void a(float f2) {
        if (this.f <= 0.0f) {
            this.a.u.b = 0.0f;
            this.a.f.i = 0.0f;
        } else {
            float f3;
            this.a.u.b = f3 = this.g + (this.f - this.g) * f2;
            this.a.f.i = f3;
        }
    }

    @Override
    public float b() {
        return 4.0f;
    }

    @Override
    public void a(Session cy2) {
        super.a(cy2);
    }

    @Override
    public void c() {
        this.e();
        this.g = this.f;
        this.a.A.c();
    }

    private void e() {
        InventoryItem fp2 = this.a.g.e.a();
        int n2 = 0;
        if (fp2 != null) {
            n2 = fp2.id;
        }
        if (n2 != this.l) {
            this.l = n2;
            this.k.a((gk)new eq(0, this.l));
        }
    }

    @Override
    public boolean a(Player eb2, Session cy2, InventoryItem fp2, int n2, int n3, int n4, int n5) {
        this.e();
        this.k.a((gk)new ed(fp2 != null ? fp2.id : -1, n2, n3, n4, n5));
        return super.a(eb2, cy2, fp2, n2, n3, n4, n5);
    }

    @Override
    public boolean a(Player eb2, Session cy2, InventoryItem fp2) {
        this.e();
        this.k.a((gk)new ed(fp2 != null ? fp2.id : -1, -1, -1, -1, 255));
        return super.a(eb2, cy2, fp2);
    }

    @Override
    public Player b(Session cy2) {
        return new mp(this.a, cy2, this.a.i, this.k);
    }

    @Override
    public void b(Player eb2, lw lw2) {
        this.e();
        this.k.a((gk)new a(eb2.an, lw2.an, 1));
        eb2.c(lw2);
    }

    @Override
    public void a(Player eb2, lw lw2) {
        this.e();
        this.k.a((gk)new a(eb2.an, lw2.an, 0));
        eb2.a_(lw2);
    }
}

