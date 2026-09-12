/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class pc
extends pp {
    public pc(Session cy2, double d2, double d3, double d4) {
        super(cy2, d2, d3, d4, 0.0, 0.0, 0.0);
        this.az *= (double)0.3f;
        this.aA = (float)Math.random() * 0.2f + 0.1f;
        this.aB *= (double)0.3f;
        this.i = 1.0f;
        this.j = 1.0f;
        this.k = 1.0f;
        this.b = 19 + this.bd.nextInt(4);
        this.a(0.01f, 0.01f);
        this.h = 0.06f;
        this.f = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        super.a(is2, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public void e_() {
        double d2;
        hb hb2;
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        this.aA -= (double)this.h;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.98f;
        this.aA *= (double)0.98f;
        this.aB *= (double)0.98f;
        if (this.f-- <= 0) {
            this.J();
        }
        if (this.aH) {
            if (Math.random() < 0.5) {
                this.J();
            }
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
        if (((hb2 = this.as.f(TrigLookup.b(this.aw), TrigLookup.b(this.ax), TrigLookup.b(this.ay))).d() || hb2.a()) && this.ax < (d2 = (double)((float)(TrigLookup.b(this.ax) + 1) - ld.b(this.as.e(TrigLookup.b(this.aw), TrigLookup.b(this.ax), TrigLookup.b(this.ay)))))) {
            this.J();
        }
    }
}

