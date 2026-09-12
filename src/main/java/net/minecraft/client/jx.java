/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class jx
extends fr {
    private kx b = new kx();

    public void a(Sign qc2, double d2, double d3, double d4, float f2) {
        float f3;
        Block nq2 = qc2.g();
        GL11.glPushMatrix();
        float f4 = 0.6666667f;
        if (nq2 == Block.aD) {
            GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d3 + 0.75f * f4), (float)((float)d4 + 0.5f));
            float f5 = (float)(qc2.f() * 360) / 16.0f;
            GL11.glRotatef((float)(-f5), 0.0f, 1.0f, 0.0f);
            this.b.b.h = true;
        } else {
            int n2 = qc2.f();
            f3 = 0.0f;
            if (n2 == 2) {
                f3 = 180.0f;
            }
            if (n2 == 4) {
                f3 = 90.0f;
            }
            if (n2 == 5) {
                f3 = -90.0f;
            }
            GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d3 + 0.75f * f4), (float)((float)d4 + 0.5f));
            GL11.glRotatef((float)(-f3), 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            this.b.b.h = false;
        }
        this.a("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef((float)f4, (float)(-f4), (float)(-f4));
        this.b.a();
        GL11.glPopMatrix();
        ls ls2 = this.a();
        f3 = 0.016666668f * f4;
        GL11.glTranslatef(0.0f, (float)(0.5f * f4), (float)(0.07f * f4));
        GL11.glScalef((float)f3, (float)(-f3), (float)f3);
        GL11.glNormal3f(0.0f, 0.0f, (float)(-1.0f * f3));
        GL11.glDepthMask((boolean)false);
        int n3 = 0;
        for (int i2 = 0; i2 < qc2.a.length; ++i2) {
            String string = qc2.a[i2];
            if (i2 == qc2.b) {
                string = "> " + string + " <";
                ls2.b(string, -ls2.a(string) / 2, i2 * 10 - qc2.a.length * 5, n3);
                continue;
            }
            ls2.b(string, -ls2.a(string) / 2, i2 * 10 - qc2.a.length * 5, n3);
        }
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void a(TileEntityRegistry ji2, double d2, double d3, double d4, float f2) {
        this.a((Sign)ji2, d2, d3, d4, f2);
    }
}

