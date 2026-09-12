/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ik
extends gm {
    ka a;
    ka b;
    ka c;
    ka d;

    public ik(int n2) {
        this.a = new ka(0, n2);
        this.a.a(-4.0f, 16.0f, -4.0f, 8, 8, 8);
        if (n2 > 0) {
            this.a = new ka(0, n2);
            this.a.a(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            this.b = new ka(32, 0);
            this.b.a(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            this.c = new ka(32, 4);
            this.c.a(1.25f, 18.0f, -3.5f, 2, 2, 2);
            this.d = new ka(32, 8);
            this.d.a(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }

    @Override
    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
    }

    @Override
    public void b(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a(f2, f3, f4, f5, f6, f7);
        this.a.a(f7);
        if (this.b != null) {
            this.b.a(f7);
            this.c.a(f7);
            this.d.a(f7);
        }
    }
}

