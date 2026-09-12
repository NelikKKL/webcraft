/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ee
extends pp {
    public ee(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7) {
        super(cy2, d2, d3, d4, d5, d6, d7);
        this.az = d5 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.aA = d6 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.aB = d7 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.j = this.k = this.bd.nextFloat() * 0.3f + 0.7f;
        this.i = this.k;
        this.g = this.bd.nextFloat() * this.bd.nextFloat() * 6.0f + 1.0f;
        this.f = (int)(16.0 / ((double)this.bd.nextFloat() * 0.8 + 0.2)) + 2;
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        super.a(is2, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public void e_() {
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        if (this.e++ >= this.f) {
            this.J();
        }
        this.b = 7 - this.e * 8 / this.f;
        this.aA += 0.004;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.9f;
        this.aA *= (double)0.9f;
        this.aB *= (double)0.9f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
    }
}

