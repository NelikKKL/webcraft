/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public abstract class aq {
    protected mn b;
    private gm a = new dc();
    private bk e = new bk();
    protected float c = 0.0f;
    protected float d = 1.0f;

    public abstract void a(lw var1, double var2, double var4, double var6, float var8, float var9);

    protected void a(String string) {
        fu fu2 = this.b.e;
        fu2.b(fu2.a(string));
    }

    protected void a(String string, String string2) {
        fu fu2 = this.b.e;
        fu2.b(fu2.a(string, string2));
    }

    private void a(lw lw2, double d2, double d3, double d4, float f2) {
        GL11.glDisable(2896);
        int n2 = Block.ar.bg;
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        float f3 = (float)n3 / 256.0f;
        float f4 = ((float)n3 + 15.99f) / 256.0f;
        float f5 = (float)n4 / 256.0f;
        float f6 = ((float)n4 + 15.99f) / 256.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d3), (float)((float)d4));
        float f7 = lw2.aP * 1.4f;
        GL11.glScalef((float)f7, (float)f7, (float)f7);
        this.a("/terrain.png");
        is is2 = is.a;
        float f8 = 1.0f;
        float f9 = 0.5f;
        float f10 = 0.0f;
        float f11 = lw2.aQ / lw2.aP;
        GL11.glRotatef((float)(-this.b.i), 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, (float)(-0.4f + (float)((int)f11) * 0.02f));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        is2.b();
        while (f11 > 0.0f) {
            is2.a(f8 - f9, 0.0f - f10, 0.0, f4, f6);
            is2.a(0.0f - f9, 0.0f - f10, 0.0, f3, f6);
            is2.a(0.0f - f9, 1.4f - f10, 0.0, f3, f5);
            is2.a(f8 - f9, 1.4f - f10, 0.0, f4, f5);
            f11 -= 1.0f;
            f10 -= 1.0f;
            f8 *= 0.9f;
            GL11.glTranslatef(0.0f, 0.0f, -0.04f);
        }
        is2.a();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }

    private void c(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        fu fu2 = this.b.e;
        fu2.b(fu2.a("%clamp%/misc/shadow.png"));
        Session cy2 = this.b();
        GL11.glDepthMask((boolean)false);
        float f4 = this.c;
        double d5 = lw2.aV + (lw2.aw - lw2.aV) * (double)f3;
        double d6 = lw2.aW + (lw2.ax - lw2.aW) * (double)f3 + (double)lw2.i_();
        double d7 = lw2.aX + (lw2.ay - lw2.aX) * (double)f3;
        int n2 = TrigLookup.b(d5 - (double)f4);
        int n3 = TrigLookup.b(d5 + (double)f4);
        int n4 = TrigLookup.b(d6 - (double)f4);
        int n5 = TrigLookup.b(d6);
        int n6 = TrigLookup.b(d7 - (double)f4);
        int n7 = TrigLookup.b(d7 + (double)f4);
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        double d10 = d4 - d7;
        is is2 = is.a;
        is2.b();
        for (int i2 = n2; i2 <= n3; ++i2) {
            for (int i3 = n4; i3 <= n5; ++i3) {
                for (int i4 = n6; i4 <= n7; ++i4) {
                    int n8 = cy2.a(i2, i3 - 1, i4);
                    if (n8 <= 0 || cy2.j(i2, i3, i4) <= 3) continue;
                    this.a(Block.registry[n8], d2, d3 + (double)lw2.i_(), d4, i2, i3, i4, f2, f4, d8, d9 + (double)lw2.i_(), d10);
                }
            }
        }
        is2.a();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDepthMask((boolean)true);
    }

    private Session b() {
        return this.b.g;
    }

    private void a(Block nq2, double d2, double d3, double d4, int n2, int n3, int n4, float f2, float f3, double d5, double d6, double d7) {
        is is2 = is.a;
        if (!nq2.b()) {
            return;
        }
        double d8 = ((double)f2 - (d3 - ((double)n3 + d6)) / 2.0) * 0.5 * (double)this.b().c(n2, n3, n4);
        if (d8 < 0.0) {
            return;
        }
        if (d8 > 1.0) {
            d8 = 1.0;
        }
        is2.a(1.0f, 1.0f, 1.0f, (float)d8);
        double d9 = (double)n2 + nq2.bk + d5;
        double d10 = (double)n2 + nq2.bn + d5;
        double d11 = (double)n3 + nq2.bl + d6 + 0.015625;
        double d12 = (double)n4 + nq2.bm + d7;
        double d13 = (double)n4 + nq2.bp + d7;
        float f4 = (float)((d2 - d9) / 2.0 / (double)f3 + 0.5);
        float f5 = (float)((d2 - d10) / 2.0 / (double)f3 + 0.5);
        float f6 = (float)((d4 - d12) / 2.0 / (double)f3 + 0.5);
        float f7 = (float)((d4 - d13) / 2.0 / (double)f3 + 0.5);
        is2.a(d9, d11, d12, f4, f6);
        is2.a(d9, d11, d13, f4, f7);
        is2.a(d10, d11, d13, f5, f7);
        is2.a(d10, d11, d12, f5, f6);
    }

    public static void a(co co2, double d2, double d3, double d4) {
        GL11.glDisable(3553);
        is is2 = is.a;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        is2.b();
        is2.b(d2, d3, d4);
        is2.b(0.0f, 0.0f, -1.0f);
        is2.a(co2.a, co2.e, co2.c);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.a, co2.b, co2.c);
        is2.b(0.0f, 0.0f, 1.0f);
        is2.a(co2.a, co2.b, co2.f);
        is2.a(co2.d, co2.b, co2.f);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.a, co2.e, co2.f);
        is2.b(0.0f, -1.0f, 0.0f);
        is2.a(co2.a, co2.b, co2.c);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.d, co2.b, co2.f);
        is2.a(co2.a, co2.b, co2.f);
        is2.b(0.0f, 1.0f, 0.0f);
        is2.a(co2.a, co2.e, co2.f);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.a, co2.e, co2.c);
        is2.b(-1.0f, 0.0f, 0.0f);
        is2.a(co2.a, co2.b, co2.f);
        is2.a(co2.a, co2.e, co2.f);
        is2.a(co2.a, co2.e, co2.c);
        is2.a(co2.a, co2.b, co2.c);
        is2.b(1.0f, 0.0f, 0.0f);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.d, co2.b, co2.f);
        is2.b(0.0, 0.0, 0.0);
        is2.a();
        GL11.glEnable(3553);
    }

    public static void a(co co2) {
        is is2 = is.a;
        is2.b();
        is2.a(co2.a, co2.e, co2.c);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.a, co2.b, co2.c);
        is2.a(co2.a, co2.b, co2.f);
        is2.a(co2.d, co2.b, co2.f);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.a, co2.e, co2.f);
        is2.a(co2.a, co2.b, co2.c);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.d, co2.b, co2.f);
        is2.a(co2.a, co2.b, co2.f);
        is2.a(co2.a, co2.e, co2.f);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.a, co2.e, co2.c);
        is2.a(co2.a, co2.b, co2.f);
        is2.a(co2.a, co2.e, co2.f);
        is2.a(co2.a, co2.e, co2.c);
        is2.a(co2.a, co2.b, co2.c);
        is2.a(co2.d, co2.b, co2.c);
        is2.a(co2.d, co2.e, co2.c);
        is2.a(co2.d, co2.e, co2.f);
        is2.a(co2.d, co2.b, co2.f);
        is2.a();
    }

    public void a(mn mn2) {
        this.b = mn2;
    }

    public void b(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        double d5;
        float f4;
        if (this.b.k.i && this.c > 0.0f && (f4 = (float)((1.0 - (d5 = this.b.a(lw2.aw, lw2.ax, lw2.ay)) / 256.0) * (double)this.d)) > 0.0f) {
            this.c(lw2, d2, d3, d4, f4, f3);
        }
        if (lw2.bg > 0 || lw2.bv) {
            this.a(lw2, d2, d3, d4, f3);
        }
    }

    public ls a() {
        return this.b.a();
    }
}

