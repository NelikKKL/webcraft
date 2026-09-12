/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class bh
extends pp {
    public bh(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7) {
        super(cy2, d2, d3, d4, d5, d6, d7);
        this.i = 1.0f;
        this.j = 1.0f;
        this.k = 1.0f;
        this.b = 32;
        this.a(0.02f, 0.02f);
        this.g *= this.bd.nextFloat() * 0.6f + 0.2f;
        this.az = d5 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.aA = d6 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.aB = d7 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.f = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void e_() {
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        this.aA += 0.002;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.85f;
        this.aA *= (double)0.85f;
        this.aB *= (double)0.85f;
        if (this.as.f(TrigLookup.b(this.aw), TrigLookup.b(this.ax), TrigLookup.b(this.ay)) != hb.f) {
            this.J();
        }
        if (this.f-- <= 0) {
            this.J();
        }
    }
}

