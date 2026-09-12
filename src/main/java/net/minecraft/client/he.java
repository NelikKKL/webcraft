/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class he
extends nn {
    private boolean[] f = new boolean[10];
    private gq g;

    public he(gq gq2) {
        this.g = gq2;
    }

    @Override
    public void a(int n2, boolean bl2) {
        int n3 = -1;
        if (n2 == this.g.k.b) {
            n3 = 0;
        }
        if (n2 == this.g.m.b) {
            n3 = 1;
        }
        if (n2 == this.g.l.b) {
            n3 = 2;
        }
        if (n2 == this.g.n.b) {
            n3 = 3;
        }
        if (n2 == this.g.o.b) {
            n3 = 4;
        }
        if (n2 == this.g.t.b) {
            n3 = 5;
        }
        if (n3 >= 0) {
            this.f[n3] = bl2;
        }
    }

    @Override
    public void a() {
        for (int i2 = 0; i2 < 10; ++i2) {
            this.f[i2] = false;
        }
    }

    @Override
    public void a(Player eb2) {
        this.a = 0.0f;
        this.b = 0.0f;
        if (this.f[0]) {
            this.b += 1.0f;
        }
        if (this.f[1]) {
            this.b -= 1.0f;
        }
        if (this.f[2]) {
            this.a += 1.0f;
        }
        if (this.f[3]) {
            this.a -= 1.0f;
        }
        this.d = this.f[4];
        this.e = this.f[5];
        if (this.e) {
            this.a = (float)((double)this.a * 0.3);
            this.b = (float)((double)this.b * 0.3);
        }
    }
}

