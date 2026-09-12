/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class m
extends ec {
    protected dc a;

    public m(dc dc2, float f2) {
        super(dc2, f2);
        this.a = dc2;
    }

    @Override
    protected void b(Mob hf2, float f2) {
        InventoryItem fp2 = hf2.l();
        if (fp2 != null) {
            GL11.glPushMatrix();
            this.a.d.b(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (fp2.id < 256 && bk.a(Block.registry[fp2.id].g())) {
                float f3 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef((float)(f3 *= 0.75f), (float)(-f3), (float)f3);
            } else if (Item.registry[fp2.id].a()) {
                float f4 = 0.625f;
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef((float)f4, (float)(-f4), (float)f4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            } else {
                float f5 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef((float)f5, (float)f5, (float)f5);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            this.b.f.a(fp2);
            GL11.glPopMatrix();
        }
    }
}

