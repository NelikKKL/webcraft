/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class nm
extends bp {
    private bp h;
    protected String a = "Controls";
    private gq i;
    private int j = -1;

    public nm(bp bp2, gq gq2) {
        this.h = bp2;
        this.i = gq2;
    }

    @Override
    public void a() {
        for (int i2 = 0; i2 < this.i.u.length; ++i2) {
            this.e.add(new r(i2, this.c / 2 - 155 + i2 % 2 * 160, this.d / 6 + 24 * (i2 >> 1), this.i.a(i2)));
        }
        this.e.add(new gh(200, this.c / 2 - 100, this.d / 6 + 168, "Done"));
    }

    @Override
    protected void a(gh gh2) {
        for (int i2 = 0; i2 < this.i.u.length; ++i2) {
            ((gh)this.e.get((int)i2)).e = this.i.a(i2);
        }
        if (gh2.f == 200) {
            this.b.a(this.h);
        } else {
            this.j = gh2.f;
            gh2.e = "> " + this.i.a(gh2.f) + " <";
        }
    }

    @Override
    protected void a(char c2, int n2) {
        if (this.j >= 0) {
            this.i.a(this.j, n2);
            ((gh)this.e.get((int)this.j)).e = this.i.a(this.j);
            this.j = -1;
        } else {
            super.a(c2, n2);
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        this.a(this.g, this.a, this.c / 2, 20, 0xFFFFFF);
        super.a(n2, n3, f2);
    }
}

