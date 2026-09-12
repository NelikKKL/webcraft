/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class pz
extends ec {
    private float a;

    public pz(gm gm2, float f2, float f3) {
        super(gm2, f2 * f3);
        this.a = f3;
    }

    protected void a(Giant ip2, float f2) {
        GL11.glScalef((float)this.a, (float)this.a, (float)this.a);
    }

    @Override
    protected void a(Mob hf2, float f2) {
        this.a((Giant)hf2, f2);
    }
}

