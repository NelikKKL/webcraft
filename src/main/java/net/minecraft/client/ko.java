/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ko
extends pp {
    private float a;

    public ko(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7) {
        super(cy2, d2, d3, d4, d5, d6, d7);
        this.az = this.az * (double)0.01f + d5;
        this.aA = this.aA * (double)0.01f + d6;
        this.aB = this.aB * (double)0.01f + d7;
        d2 += (double)((this.bd.nextFloat() - this.bd.nextFloat()) * 0.05f);
        d3 += (double)((this.bd.nextFloat() - this.bd.nextFloat()) * 0.05f);
        d4 += (double)((this.bd.nextFloat() - this.bd.nextFloat()) * 0.05f);
        this.a = this.g;
        this.k = 1.0f;
        this.j = 1.0f;
        this.i = 1.0f;
        this.f = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.ba = true;
        this.b = 48;
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.e + f2) / (float)this.f;
        this.g = this.a * (1.0f - f8 * f8 * 0.5f);
        super.a(is2, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public float a(float f2) {
        float f3 = ((float)this.e + f2) / (float)this.f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        float f4 = super.a(f2);
        return f4 * f3 + (1.0f - f3);
    }

    @Override
    public void e_() {
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        if (this.e++ >= this.f) {
            this.J();
        }
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.96f;
        this.aA *= (double)0.96f;
        this.aB *= (double)0.96f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
    }
}

