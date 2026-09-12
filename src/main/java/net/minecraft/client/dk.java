/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.io.File;
import java.util.List;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class dk
extends bp {
    protected bp a;
    private int h = 0;
    private int i = 32;
    private int j = this.d - 55 + 4;
    private int l = 0;
    private int m = this.c;
    private int n = -2;
    private int o = -1;
    private String p = "";

    public dk(bp bp2) {
        this.a = bp2;
    }

    @Override
    public void a() {
        this.e.add(new r(5, this.c / 2 - 154, this.d - 48, "Open texture pack folder"));
        this.e.add(new r(6, this.c / 2 + 4, this.d - 48, "Done"));
        this.b.C.a();
        this.p = new File(this.b.D, "texturepacks").getAbsolutePath();
        this.i = 32;
        this.j = this.d - 58 + 4;
        this.l = 0;
        this.m = this.c;
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) {
            return;
        }
        if (gh2.f == 5) {
            Sys.openURL((String)("file://" + this.p));
        }
        if (gh2.f == 6) {
            this.b.n.b();
            this.b.a(this.a);
        }
    }

    @Override
    protected void a(int n2, int n3, int n4) {
        super.a(n2, n3, n4);
    }

    @Override
    protected void b(int n2, int n3, int n4) {
        super.b(n2, n3, n4);
    }

    @Override
    public void a(int n2, int n3, float f2) {
        int n4;
        int n5;
        this.i();
        if (this.o <= 0) {
            this.b.C.a();
            this.o += 20;
        }
        List list = this.b.C.b();
        if (Mouse.isButtonDown(0)) {
            if (this.n == -1) {
                if (n3 >= this.i && n3 <= this.j) {
                    n5 = this.c / 2 - 110;
                    int n6 = this.c / 2 + 110;
                    int n7 = (n3 - this.i + this.h - 2) / 36;
                    if (n2 >= n5 && n2 <= n6 && n7 >= 0 && n7 < list.size() && this.b.C.a((d)list.get(n7))) {
                        this.b.n.b();
                    }
                    this.n = n3;
                } else {
                    this.n = -2;
                }
            } else if (this.n >= 0) {
                this.h -= n3 - this.n;
                this.n = n3;
            }
        } else {
            if (this.n < 0 || this.n == n3) {
                // empty if block
            }
            this.n = -1;
        }
        n5 = list.size() * 36 - (this.j - this.i - 4);
        if (n5 < 0) {
            n5 /= 2;
        }
        if (this.h < 0) {
            this.h = 0;
        }
        if (this.h > n5) {
            this.h = n5;
        }
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        is is2 = is.a;
        GL11.glBindTexture(3553, (int)this.b.n.a("/gui/background.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f3 = 32.0f;
        is2.b();
        is2.b(0x202020);
        is2.a(this.l, this.j, 0.0, (float)this.l / f3, (float)(this.j + this.h) / f3);
        is2.a(this.m, this.j, 0.0, (float)this.m / f3, (float)(this.j + this.h) / f3);
        is2.a(this.m, this.i, 0.0, (float)this.m / f3, (float)(this.i + this.h) / f3);
        is2.a(this.l, this.i, 0.0, (float)this.l / f3, (float)(this.i + this.h) / f3);
        is2.a();
        for (n4 = 0; n4 < list.size(); ++n4) {
            d d2 = (d)list.get(n4);
            int n8 = this.c / 2 - 92 - 16;
            int n9 = 36 + n4 * 36 - this.h;
            int n10 = 32;
            int n11 = 32;
            if (d2 == this.b.C.a) {
                int n12 = this.c / 2 - 110;
                int n13 = this.c / 2 + 110;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(3553);
                is2.b();
                is2.b(0x808080);
                is2.a(n12, n9 + n10 + 2, 0.0, 0.0, 1.0);
                is2.a(n13, n9 + n10 + 2, 0.0, 1.0, 1.0);
                is2.a(n13, n9 - 2, 0.0, 1.0, 0.0);
                is2.a(n12, n9 - 2, 0.0, 0.0, 0.0);
                is2.b(0);
                is2.a(n12 + 1, n9 + n10 + 1, 0.0, 0.0, 1.0);
                is2.a(n13 - 1, n9 + n10 + 1, 0.0, 1.0, 1.0);
                is2.a(n13 - 1, n9 - 1, 0.0, 1.0, 0.0);
                is2.a(n12 + 1, n9 - 1, 0.0, 0.0, 0.0);
                is2.a();
                GL11.glEnable(3553);
            }
            d2.c(this.b);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            is2.b();
            is2.b(0xFFFFFF);
            is2.a(n8, n9 + n10, 0.0, 0.0, 1.0);
            is2.a(n8 + n11, n9 + n10, 0.0, 1.0, 1.0);
            is2.a(n8 + n11, n9, 0.0, 1.0, 0.0);
            is2.a(n8, n9, 0.0, 0.0, 0.0);
            is2.a();
            this.b(this.g, d2.a, n8 + n11 + 2, n9 + 1, 0xFFFFFF);
            this.b(this.g, d2.b, n8 + n11 + 2, n9 + 12, 0x808080);
            this.b(this.g, d2.c, n8 + n11 + 2, n9 + 12 + 10, 0x808080);
        }
        n4 = 4;
        this.a(0, this.i, 255, 255);
        this.a(this.j, this.d, 255, 255);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        is2.b();
        is2.a(0, 0);
        is2.a(this.l, this.i + n4, 0.0, 0.0, 1.0);
        is2.a(this.m, this.i + n4, 0.0, 1.0, 1.0);
        is2.a(0, 255);
        is2.a(this.m, this.i, 0.0, 1.0, 0.0);
        is2.a(this.l, this.i, 0.0, 0.0, 0.0);
        is2.a();
        is2.b();
        is2.a(0, 255);
        is2.a(this.l, this.j, 0.0, 0.0, 1.0);
        is2.a(this.m, this.j, 0.0, 1.0, 1.0);
        is2.a(0, 0);
        is2.a(this.m, this.j - n4, 0.0, 1.0, 0.0);
        is2.a(this.l, this.j - n4, 0.0, 0.0, 0.0);
        is2.a();
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        this.a(this.g, "Select Texture Pack", this.c / 2, 16, 0xFFFFFF);
        this.a(this.g, "(Place texture pack files here)", this.c / 2 - 77, this.d - 26, 0x808080);
        super.a(n2, n3, f2);
    }

    @Override
    public void g() {
        super.g();
        --this.o;
    }

    public void a(int n2, int n3, int n4, int n5) {
        is is2 = is.a;
        GL11.glBindTexture(3553, (int)this.b.n.a("/gui/background.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f2 = 32.0f;
        is2.b();
        is2.a(0x404040, n5);
        is2.a(0.0, n3, 0.0, 0.0, (float)n3 / f2);
        is2.a(this.c, n3, 0.0, (float)this.c / f2, (float)n3 / f2);
        is2.a(0x404040, n4);
        is2.a(this.c, n2, 0.0, (float)this.c / f2, (float)n2 / f2);
        is2.a(0.0, n2, 0.0, 0.0, (float)n2 / f2);
        is2.a();
    }
}

