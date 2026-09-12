/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ma
implements qf {
    private cc a = nclass.a();
    private double b;
    private double c;
    private double d;

    @Override
    public void a(double d2, double d3, double d4) {
        this.b = d2;
        this.c = d3;
        this.d = d4;
    }

    public boolean a(double d2, double d3, double d4, double d5, double d6, double d7) {
        return this.a.a(d2 - this.b, d3 - this.c, d4 - this.d, d5 - this.b, d6 - this.c, d7 - this.d);
    }

    @Override
    public boolean a(co co2) {
        return this.a(co2.a, co2.b, co2.c, co2.d, co2.e, co2.f);
    }
}

