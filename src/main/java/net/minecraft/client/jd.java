/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class jd
extends pp {
    private float a;
    private double o;
    private double p;
    private double q;

    public jd(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7) {
        super(cy2, d2, d3, d4, d5, d6, d7);
        this.az = d5;
        this.aA = d6;
        this.aB = d7;
        this.o = this.aw = d2;
        this.p = this.ax = d3;
        this.q = this.ay = d4;
        float f2 = this.bd.nextFloat() * 0.6f + 0.4f;
        this.a = this.g = this.bd.nextFloat() * 0.2f + 0.5f;
        this.j = this.k = 1.0f * f2;
        this.i = this.k;
        this.j *= 0.3f;
        this.i *= 0.9f;
        this.f = (int)(Math.random() * 10.0) + 40;
        this.ba = true;
        this.b = (int)(Math.random() * 8.0);
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.e + f2) / (float)this.f;
        f8 = 1.0f - f8;
        f8 *= f8;
        f8 = 1.0f - f8;
        this.g = this.a * f8;
        super.a(is2, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public float a(float f2) {
        float f3 = super.a(f2);
        float f4 = (float)this.e / (float)this.f;
        f4 *= f4;
        f4 *= f4;
        return f3 * (1.0f - f4) + f4;
    }

    @Override
    public void e_() {
        float f2;
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        float f3 = f2 = (float)this.e / (float)this.f;
        f2 = -f2 + f2 * f2 * 2.0f;
        f2 = 1.0f - f2;
        this.aw = this.o + this.az * (double)f2;
        this.ax = this.p + this.aA * (double)f2 + (double)(1.0f - f3);
        this.ay = this.q + this.aB * (double)f2;
        if (this.e++ >= this.f) {
            this.J();
        }
    }
}

