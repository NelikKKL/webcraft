/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class u
extends fr {
    private Map b = new HashMap();

    public void a(MobSpawner bl2, double d2, double d3, double d4, float f2) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d3), (float)((float)d4 + 0.5f));
        lw lw2 = (lw)this.b.get(bl2.b);
        if (lw2 == null) {
            lw2 = EntityRegistry.a(bl2.b, null);
            this.b.put(bl2.b, lw2);
        }
        if (lw2 != null) {
            lw2.a(bl2.e);
            float f3 = 0.4375f;
            GL11.glTranslatef(0.0f, 0.4f, 0.0f);
            GL11.glRotatef((float)((float)(bl2.d + (bl2.c - bl2.d) * (double)f2) * 10.0f), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.4f, 0.0f);
            GL11.glScalef((float)f3, (float)f3, (float)f3);
            lw2.c(d2, d3, d4, 0.0f, 0.0f);
            mn.a.a(lw2, 0.0, 0.0, 0.0, 0.0f, f2);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void a(TileEntityRegistry ji2, double d2, double d3, double d4, float f2) {
        this.a((MobSpawner)ji2, d2, d3, d4, f2);
    }
}

