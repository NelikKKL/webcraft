/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class ec
extends aq {
    protected gm e;
    protected gm f;

    public ec(gm gm2, float f2) {
        this.e = gm2;
        this.c = f2;
    }

    public void a(gm gm2) {
        this.f = gm2;
    }

    public void a(Mob hf2, double d2, double d3, double d4, float f2, float f3) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        this.e.k = this.c(hf2, f3);
        boolean bl2 = this.e.l = hf2.ar != null || hf2.bu;
        if (this.f != null) {
            this.f.l = this.e.l;
        }
        try {
            float f4;
            float f5 = hf2.t + (hf2.s - hf2.t) * f3;
            float f6 = hf2.aE + (hf2.aC - hf2.aE) * f3;
            float f7 = hf2.aF + (hf2.aD - hf2.aF) * f3;
            GL11.glTranslatef((float)((float)d2), (float)((float)d3), (float)((float)d4));
            float f8 = this.d(hf2, f3);
            GL11.glRotatef((float)(180.0f - f5), 0.0f, 1.0f, 0.0f);
            if (hf2.O > 0) {
                f4 = ((float)hf2.O + f3 - 1.0f) / 20.0f * 1.6f;
                if ((f4 = TrigLookup.c(f4)) > 1.0f) {
                    f4 = 1.0f;
                }
                GL11.glRotatef((float)(f4 * this.a(hf2)), 0.0f, 0.0f, 1.0f);
            }
            f4 = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.a(hf2, f3);
            GL11.glTranslatef(0.0f, (float)(-24.0f * f4 - 0.0078125f), 0.0f);
            float f9 = hf2.V + (hf2.W - hf2.V) * f3;
            float f10 = hf2.X - hf2.W * (1.0f - f3);
            if (f9 > 1.0f) {
                f9 = 1.0f;
            }
            this.a(hf2.bl, hf2.A());
            GL11.glEnable(3008);
            this.e.b(f10, f9, f8, f6 - f5, f7, f4);
            for (int i2 = 0; i2 < 4; ++i2) {
                if (!this.a(hf2, i2)) continue;
                this.f.b(f10, f9, f8, f6 - f5, f7, f4);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
            }
            this.b(hf2, f3);
            float f11 = hf2.a(f3);
            int n2 = this.a(hf2, f11, f3);
            if ((n2 >> 24 & 0xFF) > 0 || hf2.L > 0 || hf2.O > 0) {
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if (hf2.L > 0 || hf2.O > 0) {
                    GL11.glColor4f((float)f11, 0.0f, 0.0f, 0.4f);
                    this.e.b(f10, f9, f8, f6 - f5, f7, f4);
                    for (int i3 = 0; i3 < 4; ++i3) {
                        if (!this.a(hf2, i3)) continue;
                        GL11.glColor4f((float)f11, 0.0f, 0.0f, 0.4f);
                        this.f.b(f10, f9, f8, f6 - f5, f7, f4);
                    }
                }
                if ((n2 >> 24 & 0xFF) > 0) {
                    float f12 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                    float f13 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                    float f14 = (float)(n2 & 0xFF) / 255.0f;
                    float f15 = (float)(n2 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f12, (float)f13, (float)f14, (float)f15);
                    this.e.b(f10, f9, f8, f6 - f5, f7, f4);
                    for (int i4 = 0; i4 < 4; ++i4) {
                        if (!this.a(hf2, i4)) continue;
                        GL11.glColor4f((float)f12, (float)f13, (float)f14, (float)f15);
                        this.f.b(f10, f9, f8, f6 - f5, f7, f4);
                    }
                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    protected float c(Mob hf2, float f2) {
        return hf2.d(f2);
    }

    protected float d(Mob hf2, float f2) {
        return (float)hf2.be + f2;
    }

    protected void b(Mob hf2, float f2) {
    }

    protected boolean a(Mob hf2, int n2) {
        return false;
    }

    protected float a(Mob hf2) {
        return 90.0f;
    }

    protected int a(Mob hf2, float f2, float f3) {
        return 0;
    }

    protected void a(Mob hf2, float f2) {
    }

    @Override
    public void a(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        this.a((Mob)lw2, d2, d3, d4, f2, f3);
    }
}

