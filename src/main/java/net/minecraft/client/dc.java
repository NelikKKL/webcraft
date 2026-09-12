/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class dc
extends gm {
    public ka a = new ka(0, 0);
    public ka b;
    public ka c;
    public ka d;
    public ka e;
    public ka f;
    public ka g;
    public boolean h = false;
    public boolean i = false;
    public boolean j = false;

    public dc() {
        this(0.0f);
    }

    public dc(float f2) {
        this(f2, 0.0f);
    }

    public dc(float f2, float f3) {
        this.a.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f2);
        this.a.a(0.0f, 0.0f + f3, 0.0f);
        this.b = new ka(32, 0);
        this.b.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f2 + 0.5f);
        this.b.a(0.0f, 0.0f + f3, 0.0f);
        this.c = new ka(16, 16);
        this.c.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, f2);
        this.c.a(0.0f, 0.0f + f3, 0.0f);
        this.d = new ka(40, 16);
        this.d.a(-3.0f, -2.0f, -2.0f, 4, 12, 4, f2);
        this.d.a(-5.0f, 2.0f + f3, 0.0f);
        this.e = new ka(40, 16);
        this.e.g = true;
        this.e.a(-1.0f, -2.0f, -2.0f, 4, 12, 4, f2);
        this.e.a(5.0f, 2.0f + f3, 0.0f);
        this.f = new ka(0, 16);
        this.f.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, f2);
        this.f.a(-2.0f, 12.0f + f3, 0.0f);
        this.g = new ka(0, 16);
        this.g.g = true;
        this.g.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, f2);
        this.g.a(2.0f, 12.0f + f3, 0.0f);
    }

    @Override
    public void b(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a(f2, f3, f4, f5, f6, f7);
        this.a.a(f7);
        this.c.a(f7);
        this.d.a(f7);
        this.e.a(f7);
        this.f.a(f7);
        this.g.a(f7);
        this.b.a(f7);
    }

    @Override
    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a.e = f5 / 57.295776f;
        this.a.d = f6 / 57.295776f;
        this.b.e = this.a.e;
        this.b.d = this.a.d;
        this.d.d = TrigLookup.b(f2 * 0.6662f + (float)Math.PI) * 2.0f * f3 * 0.5f;
        this.e.d = TrigLookup.b(f2 * 0.6662f) * 2.0f * f3 * 0.5f;
        this.d.f = 0.0f;
        this.e.f = 0.0f;
        this.f.d = TrigLookup.b(f2 * 0.6662f) * 1.4f * f3;
        this.g.d = TrigLookup.b(f2 * 0.6662f + (float)Math.PI) * 1.4f * f3;
        this.f.e = 0.0f;
        this.g.e = 0.0f;
        if (this.l) {
            this.d.d += -0.62831855f;
            this.e.d += -0.62831855f;
            this.f.d = -1.2566371f;
            this.g.d = -1.2566371f;
            this.f.e = 0.31415927f;
            this.g.e = -0.31415927f;
        }
        if (this.h) {
            this.e.d = this.e.d * 0.5f - 0.31415927f;
        }
        if (this.i) {
            this.d.d = this.d.d * 0.5f - 0.31415927f;
        }
        this.d.e = 0.0f;
        this.e.e = 0.0f;
        if (this.k > -9990.0f) {
            float f8 = this.k;
            this.c.e = TrigLookup.a(TrigLookup.c(f8) * (float)Math.PI * 2.0f) * 0.2f;
            this.d.c = TrigLookup.a(this.c.e) * 5.0f;
            this.d.a = -TrigLookup.b(this.c.e) * 5.0f;
            this.e.c = -TrigLookup.a(this.c.e) * 5.0f;
            this.e.a = TrigLookup.b(this.c.e) * 5.0f;
            this.d.e += this.c.e;
            this.e.e += this.c.e;
            this.e.d += this.c.e;
            f8 = 1.0f - this.k;
            f8 *= f8;
            f8 *= f8;
            f8 = 1.0f - f8;
            float f9 = TrigLookup.a(f8 * (float)Math.PI);
            float f10 = TrigLookup.a(this.k * (float)Math.PI) * -(this.a.d - 0.7f) * 0.75f;
            this.d.d = (float)((double)this.d.d - ((double)f9 * 1.2 + (double)f10));
            this.d.e += this.c.e * 2.0f;
            this.d.f = TrigLookup.a(this.k * (float)Math.PI) * -0.4f;
        }
        if (this.j) {
            this.c.d = 0.5f;
            this.f.d -= 0.0f;
            this.g.d -= 0.0f;
            this.d.d += 0.4f;
            this.e.d += 0.4f;
            this.f.c = 4.0f;
            this.g.c = 4.0f;
            this.f.b = 9.0f;
            this.g.b = 9.0f;
            this.a.b = 1.0f;
        } else {
            this.c.d = 0.0f;
            this.f.c = 0.0f;
            this.g.c = 0.0f;
            this.f.b = 12.0f;
            this.g.b = 12.0f;
            this.a.b = 0.0f;
        }
        this.d.f += TrigLookup.b(f4 * 0.09f) * 0.05f + 0.05f;
        this.e.f -= TrigLookup.b(f4 * 0.09f) * 0.05f + 0.05f;
        this.d.d += TrigLookup.a(f4 * 0.067f) * 0.05f;
        this.e.d -= TrigLookup.a(f4 * 0.067f) * 0.05f;
    }
}

