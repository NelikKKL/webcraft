/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class jw
extends aq {
    public void a(hj hj2, double d2, double d3, double d4, float f2, float f3) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d3), (float)((float)d4));
        GL11.glEnable(32826);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        int n2 = 1;
        int n3 = 2;
        this.a("/particles.png");
        is is2 = is.a;
        float f4 = (float)(n2 * 8 + 0) / 128.0f;
        float f5 = (float)(n2 * 8 + 8) / 128.0f;
        float f6 = (float)(n3 * 8 + 0) / 128.0f;
        float f7 = (float)(n3 * 8 + 8) / 128.0f;
        float f8 = 1.0f;
        float f9 = 0.5f;
        float f10 = 0.5f;
        GL11.glRotatef((float)(180.0f - this.b.i), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)(-this.b.j), 1.0f, 0.0f, 0.0f);
        is2.b();
        is2.b(0.0f, 1.0f, 0.0f);
        is2.a(0.0f - f9, 0.0f - f10, 0.0, f4, f7);
        is2.a(f8 - f9, 0.0f - f10, 0.0, f5, f7);
        is2.a(f8 - f9, 1.0f - f10, 0.0, f5, f6);
        is2.a(0.0f - f9, 1.0f - f10, 0.0, f4, f6);
        is2.a();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        if (hj2.b != null) {
            float f11 = (hj2.b.aE + (hj2.b.aC - hj2.b.aE) * f3) * (float)Math.PI / 180.0f;
            float f12 = (hj2.b.aF + (hj2.b.aD - hj2.b.aF) * f3) * (float)Math.PI / 180.0f;
            double d5 = TrigLookup.a(f11);
            double d6 = TrigLookup.b(f11);
            double d7 = TrigLookup.a(f12);
            double d8 = TrigLookup.b(f12);
            double d9 = hj2.b.at + (hj2.b.aw - hj2.b.at) * (double)f3 - d6 * 0.7 - d5 * 0.5 * d8;
            double d10 = hj2.b.au + (hj2.b.ax - hj2.b.au) * (double)f3 - d7 * 0.5;
            double d11 = hj2.b.av + (hj2.b.ay - hj2.b.av) * (double)f3 - d5 * 0.7 + d6 * 0.5 * d8;
            if (this.b.k.y) {
                f11 = (hj2.b.t + (hj2.b.s - hj2.b.t) * f3) * (float)Math.PI / 180.0f;
                d5 = TrigLookup.a(f11);
                d6 = TrigLookup.b(f11);
                d9 = hj2.b.at + (hj2.b.aw - hj2.b.at) * (double)f3 - d6 * 0.35 - d5 * 0.85;
                d10 = hj2.b.au + (hj2.b.ax - hj2.b.au) * (double)f3 - 0.45;
                d11 = hj2.b.av + (hj2.b.ay - hj2.b.av) * (double)f3 - d5 * 0.35 + d6 * 0.85;
            }
            double d12 = hj2.at + (hj2.aw - hj2.at) * (double)f3;
            double d13 = hj2.au + (hj2.ax - hj2.au) * (double)f3 + 0.25;
            double d14 = hj2.av + (hj2.ay - hj2.av) * (double)f3;
            double d15 = (float)(d9 - d12);
            double d16 = (float)(d10 - d13);
            double d17 = (float)(d11 - d14);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            is2.a(3);
            is2.b(0);
            int n4 = 16;
            for (int i2 = 0; i2 <= n4; ++i2) {
                float f13 = (float)i2 / (float)n4;
                is2.a(d2 + d15 * (double)f13, d3 + d16 * (double)(f13 * f13 + f13) * 0.5 + 0.25, d4 + d17 * (double)f13);
            }
            is2.a();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }

    @Override
    public void a(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        this.a((hj)lw2, d2, d3, d4, f2, f3);
    }
}

