/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class g
extends ec {
    public g() {
        super(new fg(), 0.5f);
    }

    protected void a(Creeper dq2, float f2) {
        Creeper dq3 = dq2;
        float f3 = dq3.b(f2);
        float f4 = 1.0f + TrigLookup.a(f3 * 100.0f) * f3 * 0.01f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        f3 *= f3;
        f3 *= f3;
        float f5 = (1.0f + f3 * 0.4f) * f4;
        float f6 = (1.0f + f3 * 0.1f) / f4;
        GL11.glScalef((float)f5, (float)f6, (float)f5);
    }

    protected int a(Creeper dq2, float f2, float f3) {
        Creeper dq3 = dq2;
        float f4 = dq3.b(f3);
        if ((int)(f4 * 10.0f) % 2 == 0) {
            return 0;
        }
        int n2 = (int)(f4 * 0.2f * 255.0f);
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 > 255) {
            n2 = 255;
        }
        int n3 = 255;
        int n4 = 255;
        int n5 = 255;
        return n2 << 24 | n3 << 16 | n4 << 8 | n5;
    }

    @Override
    protected void a(Mob hf2, float f2) {
        this.a((Creeper)hf2, f2);
    }

    @Override
    protected int a(Mob hf2, float f2, float f3) {
        return this.a((Creeper)hf2, f2, f3);
    }
}

