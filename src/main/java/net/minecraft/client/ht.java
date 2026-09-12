/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class ht
extends ec {
    private gm a;

    public ht(gm gm2, gm gm3, float f2) {
        super(gm2, f2);
        this.a = gm3;
    }

    protected boolean a(Slime ns2, int n2) {
        if (n2 == 0) {
            this.a(this.a);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return true;
        }
        if (n2 == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return false;
    }

    protected void a(Slime ns2, float f2) {
        float f3 = (ns2.b + (ns2.a - ns2.b) * f2) / ((float)ns2.c * 0.5f + 1.0f);
        float f4 = 1.0f / (f3 + 1.0f);
        float f5 = ns2.c;
        GL11.glScalef((float)(f4 * f5), (float)(1.0f / f4 * f5), (float)(f4 * f5));
    }

    @Override
    protected void a(Mob hf2, float f2) {
        this.a((Slime)hf2, f2);
    }

    @Override
    protected boolean a(Mob hf2, int n2) {
        return this.a((Slime)hf2, n2);
    }
}

