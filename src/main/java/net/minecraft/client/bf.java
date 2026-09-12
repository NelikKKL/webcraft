/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class bf
extends bp {
    private bp h;
    protected String a = "Options";
    private gq i;

    public bf(bp bp2, gq gq2) {
        this.h = bp2;
        this.i = gq2;
    }

    @Override
    public void a() {
        for (int i2 = 0; i2 < this.i.w; ++i2) {
            int n2 = this.i.b(i2);
            if (n2 == 0) {
                this.e.add(new r(i2, this.c / 2 - 155 + i2 % 2 * 160, this.d / 6 + 24 * (i2 >> 1), this.i.d(i2)));
                continue;
            }
            this.e.add(new oh(i2, this.c / 2 - 155 + i2 % 2 * 160, this.d / 6 + 24 * (i2 >> 1), i2, this.i.d(i2), this.i.c(i2)));
        }
        this.e.add(new gh(100, this.c / 2 - 100, this.d / 6 + 120 + 12, "Controls..."));
        this.e.add(new gh(200, this.c / 2 - 100, this.d / 6 + 168, "Done"));
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) {
            return;
        }
        if (gh2.f < 100) {
            this.i.b(gh2.f, 1);
            gh2.e = this.i.d(gh2.f);
        }
        if (gh2.f == 100) {
            this.b.y.b();
            this.b.a(new nm(this, this.i));
        }
        if (gh2.f == 200) {
            this.b.y.b();
            this.b.a(this.h);
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        this.a(this.g, this.a, this.c / 2, 20, 0xFFFFFF);
        super.a(n2, n3, f2);
    }
}

