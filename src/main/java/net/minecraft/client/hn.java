/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class hn
extends aq {
    public void a(Arrow lv2, double d2, double d3, double d4, float f2, float f3) {
        this.a("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d3), (float)((float)d4));
        GL11.glRotatef((float)(lv2.aE + (lv2.aC - lv2.aE) * f3 - 90.0f), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)(lv2.aF + (lv2.aD - lv2.aF) * f3), 0.0f, 0.0f, 1.0f);
        is is2 = is.a;
        int n2 = 0;
        float f4 = 0.0f;
        float f5 = 0.5f;
        float f6 = (float)(0 + n2 * 10) / 32.0f;
        float f7 = (float)(5 + n2 * 10) / 32.0f;
        float f8 = 0.0f;
        float f9 = 0.15625f;
        float f10 = (float)(5 + n2 * 10) / 32.0f;
        float f11 = (float)(10 + n2 * 10) / 32.0f;
        float f12 = 0.05625f;
        GL11.glEnable(32826);
        float f13 = (float)lv2.a - f3;
        if (f13 > 0.0f) {
            float f14 = -TrigLookup.a(f13 * 3.0f) * f13;
            GL11.glRotatef((float)f14, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef((float)f12, (float)f12, (float)f12);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f((float)f12, 0.0f, 0.0f);
        is2.b();
        is2.a(-7.0, -2.0, -2.0, f8, f10);
        is2.a(-7.0, -2.0, 2.0, f9, f10);
        is2.a(-7.0, 2.0, 2.0, f9, f11);
        is2.a(-7.0, 2.0, -2.0, f8, f11);
        is2.a();
        GL11.glNormal3f((float)(-f12), 0.0f, 0.0f);
        is2.b();
        is2.a(-7.0, 2.0, -2.0, f8, f10);
        is2.a(-7.0, 2.0, 2.0, f9, f10);
        is2.a(-7.0, -2.0, 2.0, f9, f11);
        is2.a(-7.0, -2.0, -2.0, f8, f11);
        is2.a();
        for (int i2 = 0; i2 < 4; ++i2) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, (float)f12);
            is2.b();
            is2.a(-8.0, -2.0, 0.0, f4, f6);
            is2.a(8.0, -2.0, 0.0, f5, f6);
            is2.a(8.0, 2.0, 0.0, f5, f7);
            is2.a(-8.0, 2.0, 0.0, f4, f7);
            is2.a();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    @Override
    public void a(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        this.a((Arrow)lw2, d2, d3, d4, f2, f3);
    }
}

