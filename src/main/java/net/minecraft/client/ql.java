/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class ql
extends ec {
    public ql() {
        super(new lm(), 1.0f);
        this.a(new lm());
    }

    protected float a(Spider be2) {
        return 180.0f;
    }

    protected boolean a(Spider be2, int n2) {
        if (n2 != 0) {
            return false;
        }
        if (n2 != 0) {
            return false;
        }
        this.a("/mob/spider_eyes.png");
        float f2 = (1.0f - be2.a(1.0f)) * 0.5f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (float)f2);
        return true;
    }

    @Override
    protected float a(Mob hf2) {
        return this.a((Spider)hf2);
    }

    @Override
    protected boolean a(Mob hf2, int n2) {
        return this.a((Spider)hf2, n2);
    }
}

