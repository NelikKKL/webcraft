/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class fg
extends gm {
    public ka a;
    public ka b;
    public ka c;
    public ka d;
    public ka e;
    public ka f;
    public ka g;

    public fg() {
        float f2 = 0.0f;
        int n2 = 4;
        this.a = new ka(0, 0);
        this.a.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f2);
        this.a.a(0.0f, n2, 0.0f);
        this.b = new ka(32, 0);
        this.b.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, f2 + 0.5f);
        this.b.a(0.0f, n2, 0.0f);
        this.c = new ka(16, 16);
        this.c.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, f2);
        this.c.a(0.0f, n2, 0.0f);
        this.d = new ka(0, 16);
        this.d.a(-2.0f, 0.0f, -2.0f, 4, 6, 4, f2);
        this.d.a(-2.0f, 12 + n2, 4.0f);
        this.e = new ka(0, 16);
        this.e.a(-2.0f, 0.0f, -2.0f, 4, 6, 4, f2);
        this.e.a(2.0f, 12 + n2, 4.0f);
        this.f = new ka(0, 16);
        this.f.a(-2.0f, 0.0f, -2.0f, 4, 6, 4, f2);
        this.f.a(-2.0f, 12 + n2, -4.0f);
        this.g = new ka(0, 16);
        this.g.a(-2.0f, 0.0f, -2.0f, 4, 6, 4, f2);
        this.g.a(2.0f, 12 + n2, -4.0f);
    }

    @Override
    public void b(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a(f2, f3, f4, f5, f6, f7);
        this.a.a(f7);
        this.c.a(f7);
        this.d.a(f7);
        this.e.a(f7);
        this.f.a(f7);
        this.g.a(f7);
    }

    @Override
    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a.e = f5 / 57.295776f;
        this.a.d = f6 / 57.295776f;
        this.d.d = TrigLookup.b(f2 * 0.6662f) * 1.4f * f3;
        this.e.d = TrigLookup.b(f2 * 0.6662f + (float)Math.PI) * 1.4f * f3;
        this.f.d = TrigLookup.b(f2 * 0.6662f + (float)Math.PI) * 1.4f * f3;
        this.g.d = TrigLookup.b(f2 * 0.6662f) * 1.4f * f3;
    }
}

