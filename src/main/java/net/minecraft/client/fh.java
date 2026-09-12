/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class fh
extends pp {
    float a;

    public fh(Session cy2, double d2, double d3, double d4) {
        this(cy2, d2, d3, d4, 1.0f);
    }

    public fh(Session cy2, double d2, double d3, double d4, float f2) {
        super(cy2, d2, d3, d4, 0.0, 0.0, 0.0);
        this.az *= (double)0.1f;
        this.aA *= (double)0.1f;
        this.aB *= (double)0.1f;
        this.i = (float)(Math.random() * (double)0.3f) + 0.7f;
        this.j = this.k = (float)(Math.random() * (double)0.1f);
        this.g *= 0.75f;
        this.g *= f2;
        this.a = this.g;
        this.f = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.f = (int)((float)this.f * f2);
        this.ba = false;
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.e + f2) / (float)this.f * 32.0f;
        if (f8 < 0.0f) {
            f8 = 0.0f;
        }
        if (f8 > 1.0f) {
            f8 = 1.0f;
        }
        this.g = this.a * f8;
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
        this.d(this.az, this.aA, this.aB);
        if (this.ax == this.au) {
            this.az *= 1.1;
            this.aB *= 1.1;
        }
        this.az *= (double)0.96f;
        this.aA *= (double)0.96f;
        this.aB *= (double)0.96f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
    }
}

