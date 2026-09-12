/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ks {
    public boolean a = false;
    private Random h = new Random();
    private Session i;
    public double b;
    public double c;
    public double d;
    public lw e;
    public float f;
    public Set g = new HashSet();

    public ks(Session cy2, lw lw2, double d2, double d3, double d4, float f2) {
        this.i = cy2;
        this.e = lw2;
        this.f = f2;
        this.b = d2;
        this.c = d3;
        this.d = d4;
    }

    public void a() {
        double d2;
        double d3;
        double d4;
        int n2;
        int n3;
        int n4;
        float f2 = this.f;
        int n5 = 16;
        for (n4 = 0; n4 < n5; ++n4) {
            for (n3 = 0; n3 < n5; ++n3) {
                for (n2 = 0; n2 < n5; ++n2) {
                    if (n4 != 0 && n4 != n5 - 1 && n3 != 0 && n3 != n5 - 1 && n2 != 0 && n2 != n5 - 1) continue;
                    double d5 = (float)n4 / ((float)n5 - 1.0f) * 2.0f - 1.0f;
                    double d6 = (float)n3 / ((float)n5 - 1.0f) * 2.0f - 1.0f;
                    double d7 = (float)n2 / ((float)n5 - 1.0f) * 2.0f - 1.0f;
                    double d8 = Math.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
                    d5 /= d8;
                    d6 /= d8;
                    d7 /= d8;
                    d4 = this.b;
                    d3 = this.c;
                    d2 = this.d;
                    float f3 = 0.3f;
                    for (float f4 = this.f * (0.7f + this.i.l.nextFloat() * 0.6f); f4 > 0.0f; f4 -= f3 * 0.75f) {
                        int n6;
                        int n7;
                        int n8 = TrigLookup.b(d4);
                        int n9 = this.i.a(n8, n7 = TrigLookup.b(d3), n6 = TrigLookup.b(d2));
                        if (n9 > 0) {
                            f4 -= (Block.registry[n9].a(this.e) + 0.3f) * f3;
                        }
                        if (f4 > 0.0f) {
                            this.g.add(new on(n8, n7, n6));
                        }
                        d4 += d5 * (double)f3;
                        d3 += d6 * (double)f3;
                        d2 += d7 * (double)f3;
                    }
                }
            }
        }
        this.f *= 2.0f;
        n4 = TrigLookup.b(this.b - (double)this.f - 1.0);
        n3 = TrigLookup.b(this.b + (double)this.f + 1.0);
        n2 = TrigLookup.b(this.c - (double)this.f - 1.0);
        int n10 = TrigLookup.b(this.c + (double)this.f + 1.0);
        int n11 = TrigLookup.b(this.d - (double)this.f - 1.0);
        int n12 = TrigLookup.b(this.d + (double)this.f + 1.0);
        List list = this.i.b(this.e, co.b(n4, n2, n11, n3, n10, n12));
        Vector3D ao2 = Vector3D.b(this.b, this.c, this.d);
        for (int i2 = 0; i2 < list.size(); ++i2) {
            lw lw2 = (lw)list.get(i2);
            double d9 = lw2.f(this.b, this.c, this.d) / (double)this.f;
            if (!(d9 <= 1.0)) continue;
            d4 = lw2.aw - this.b;
            d3 = lw2.ax - this.c;
            d2 = lw2.ay - this.d;
            double d10 = TrigLookup.a(d4 * d4 + d3 * d3 + d2 * d2);
            d4 /= d10;
            d3 /= d10;
            d2 /= d10;
            double d11 = this.i.a(ao2, lw2.aG);
            double d12 = (1.0 - d9) * d11;
            lw2.a(this.e, (int)((d12 * d12 + d12) / 2.0 * 8.0 * (double)this.f + 1.0));
            double d13 = d12;
            lw2.az += d4 * d13;
            lw2.aA += d3 * d13;
            lw2.aB += d2 * d13;
        }
        this.f = f2;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.g);
        if (this.a) {
            for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
                on on2 = (on)arrayList.get(i3);
                int n13 = on2.a;
                int n14 = on2.b;
                int n15 = on2.c;
                int n16 = this.i.a(n13, n14, n15);
                int n17 = this.i.a(n13, n14 - 1, n15);
                if (n16 != 0 || !Block.o[n17] || this.h.nextInt(3) != 0) continue;
                this.i.d(n13, n14, n15, Block.ar.bh);
            }
        }
    }

    public void b() {
        this.i.a(this.b, this.c, this.d, "random.explode", 4.0f, (1.0f + (this.i.l.nextFloat() - this.i.l.nextFloat()) * 0.2f) * 0.7f);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.g);
        for (int i2 = arrayList.size() - 1; i2 >= 0; --i2) {
            on on2 = (on)arrayList.get(i2);
            int n2 = on2.a;
            int n3 = on2.b;
            int n4 = on2.c;
            int n5 = this.i.a(n2, n3, n4);
            for (int i3 = 0; i3 < 1; ++i3) {
                double d2 = (float)n2 + this.i.l.nextFloat();
                double d3 = (float)n3 + this.i.l.nextFloat();
                double d4 = (float)n4 + this.i.l.nextFloat();
                double d5 = d2 - this.b;
                double d6 = d3 - this.c;
                double d7 = d4 - this.d;
                double d8 = TrigLookup.a(d5 * d5 + d6 * d6 + d7 * d7);
                d5 /= d8;
                d6 /= d8;
                d7 /= d8;
                double d9 = 0.5 / (d8 / (double)this.f + 0.1);
                this.i.a("explode", (d2 + this.b * 1.0) / 2.0, (d3 + this.c * 1.0) / 2.0, (d4 + this.d * 1.0) / 2.0, d5 *= (d9 *= (double)(this.i.l.nextFloat() * this.i.l.nextFloat() + 0.3f)), d6 *= d9, d7 *= d9);
                this.i.a("smoke", d2, d3, d4, d5, d6, d7);
            }
            if (n5 <= 0) continue;
            Block.registry[n5].a(this.i, n2, n3, n4, this.i.e(n2, n3, n4), 0.3f);
            this.i.d(n2, n3, n4, 0);
            Block.registry[n5].c(this.i, n2, n3, n4);
        }
    }
}

