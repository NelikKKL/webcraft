/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class db
extends pp {
    private float a;

    public db(Session cy2, double d2, double d3, double d4) {
        super(cy2, d2, d3, d4, 0.0, 0.0, 0.0);
        this.az *= (double)0.8f;
        this.aA *= (double)0.8f;
        this.aB *= (double)0.8f;
        this.aA = this.bd.nextFloat() * 0.4f + 0.05f;
        this.k = 1.0f;
        this.j = 1.0f;
        this.i = 1.0f;
        this.g *= this.bd.nextFloat() * 2.0f + 0.2f;
        this.a = this.g;
        this.f = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.ba = false;
        this.b = 49;
    }

    @Override
    public float a(float f2) {
        return 1.0f;
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.e + f2) / (float)this.f;
        this.g = this.a * (1.0f - f8 * f8);
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
        float f2 = (float)this.e / (float)this.f;
        if (this.bd.nextFloat() > f2) {
            this.as.a("smoke", this.aw, this.ax, this.ay, this.az, this.aA, this.aB);
        }
        this.aA -= 0.03;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.999f;
        this.aA *= (double)0.999f;
        this.aB *= (double)0.999f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
    }
}

