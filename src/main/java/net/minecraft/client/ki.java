/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ki
extends pp {
    private Block a;

    public ki(Session cy2, double d2, double d3, double d4, double d5, double d6, double d7, Block nq2) {
        super(cy2, d2, d3, d4, d5, d6, d7);
        this.a = nq2;
        this.b = nq2.bg;
        this.h = nq2.br;
        this.k = 0.6f;
        this.j = 0.6f;
        this.i = 0.6f;
        this.g /= 2.0f;
    }

    public ki a(int n2, int n3, int n4) {
        if (this.a == Block.u) {
            return this;
        }
        int n5 = this.a.b((pk)this.as, n2, n3, n4);
        this.i *= (float)(n5 >> 16 & 0xFF) / 255.0f;
        this.j *= (float)(n5 >> 8 & 0xFF) / 255.0f;
        this.k *= (float)(n5 & 0xFF) / 255.0f;
        return this;
    }

    @Override
    public int l_() {
        return 1;
    }

    @Override
    public void a(is is2, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)(this.b % 16) + this.c / 4.0f) / 16.0f;
        float f9 = f8 + 0.015609375f;
        float f10 = ((float)(this.b / 16) + this.d / 4.0f) / 16.0f;
        float f11 = f10 + 0.015609375f;
        float f12 = 0.1f * this.g;
        float f13 = (float)(this.at + (this.aw - this.at) * (double)f2 - l);
        float f14 = (float)(this.au + (this.ax - this.au) * (double)f2 - m);
        float f15 = (float)(this.av + (this.ay - this.av) * (double)f2 - n);
        float f16 = this.a(f2);
        is2.a(f16 * this.i, f16 * this.j, f16 * this.k);
        is2.a(f13 - f3 * f12 - f6 * f12, f14 - f4 * f12, f15 - f5 * f12 - f7 * f12, f8, f11);
        is2.a(f13 - f3 * f12 + f6 * f12, f14 + f4 * f12, f15 - f5 * f12 + f7 * f12, f8, f10);
        is2.a(f13 + f3 * f12 + f6 * f12, f14 + f4 * f12, f15 + f5 * f12 + f7 * f12, f9, f10);
        is2.a(f13 + f3 * f12 - f6 * f12, f14 - f4 * f12, f15 + f5 * f12 - f7 * f12, f9, f11);
    }
}

