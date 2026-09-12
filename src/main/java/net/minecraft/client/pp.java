/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class pp
extends lw {
    protected int b;
    protected float c;
    protected float d;
    protected int e = 0;
    protected int f = 0;
    protected float g;
    protected float h;
    protected float i;
    protected float j;
    protected float k;
    public static double l;
    public static double m;
    public static double n;

    public pp(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7) {
        super(cy2);
        this.a(0.2f, 0.2f);
        this.aO = this.aQ / 2.0f;
        this.b(d2, d3, d4);
        this.k = 1.0f;
        this.j = 1.0f;
        this.i = 1.0f;
        this.az = d5 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.aA = d6 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.aB = d7 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        float f2 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        float f3 = TrigLookup.a(this.az * this.az + this.aA * this.aA + this.aB * this.aB);
        this.az = this.az / (double)f3 * (double)f2 * (double)0.4f;
        this.aA = this.aA / (double)f3 * (double)f2 * (double)0.4f + (double)0.1f;
        this.aB = this.aB / (double)f3 * (double)f2 * (double)0.4f;
        this.c = this.bd.nextFloat() * 3.0f;
        this.d = this.bd.nextFloat() * 3.0f;
        this.g = (this.bd.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.f = (int)(4.0f / (this.bd.nextFloat() * 0.9f + 0.1f));
        this.e = 0;
        this.aT = false;
    }

    public pp b(float f2) {
        this.az *= (double)f2;
        this.aA = (this.aA - (double)0.1f) * (double)f2 + (double)0.1f;
        this.aB *= (double)f2;
        return this;
    }

    public pp d(float f2) {
        this.a(0.2f * f2, 0.2f * f2);
        this.g *= f2;
        return this;
    }

    @Override
    public void e_() {
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        if (this.e++ >= this.f) {
            this.J();
        }
        this.aA -= 0.04 * (double)this.h;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.98f;
        this.aA *= (double)0.98f;
        this.aB *= (double)0.98f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
        }
    }

    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = (float)(this.b % 16) / 16.0f;
        float f9 = f8 + 0.0624375f;
        float f10 = (float)(this.b / 16) / 16.0f;
        float f11 = f10 + 0.0624375f;
        float f12 = 0.1f * this.g;
        float f13 = (float)(this.at + (this.aw - this.at) * (double)f2 - l);
        float f14 = (float)(this.au + (this.ax - this.au) * (double)f2 - m);
        float f15 = (float)(this.av + (this.ay - this.av) * (double)f2 - n);
        float f16 = this.a(f2);
        is2.a(this.i * f16, this.j * f16, this.k * f16);
        is2.a(f13 - f3 * f12 - f6 * f12, f14 - f4 * f12, f15 - f5 * f12 - f7 * f12, f8, f11);
        is2.a(f13 - f3 * f12 + f6 * f12, f14 + f4 * f12, f15 - f5 * f12 + f7 * f12, f8, f10);
        is2.a(f13 + f3 * f12 + f6 * f12, f14 + f4 * f12, f15 + f5 * f12 + f7 * f12, f9, f10);
        is2.a(f13 + f3 * f12 - f6 * f12, f14 - f4 * f12, f15 + f5 * f12 - f7 * f12, f9, f11);
    }

    public int l_() {
        return 0;
    }

    @Override
    public void a(NBTCompoundTag iq2) {
    }

    @Override
    public void b(NBTCompoundTag iq2) {
    }
}

