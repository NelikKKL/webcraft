/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class ne
extends ex {
    private q j;
    private float l;
    private float m;

    public ne(hi hi2, InventoryItem[] fpArray) {
        int n2;
        int n3;
        this.f = true;
        this.j = new q(fpArray);
        this.i.add(new ar(this, this.j.a, this.j.b, 0, 144, 36));
        for (n3 = 0; n3 < 2; ++n3) {
            for (n2 = 0; n2 < 2; ++n2) {
                this.i.add(new of(this, this.j.a, n2 + n3 * 2, 88 + n2 * 18, 26 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 4; ++n3) {
            n2 = n3;
            this.i.add(new na(this, this, hi2, hi2.c() - 1 - n3, 8, 8 + n3 * 18, n2));
        }
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.i.add(new of(this, hi2, n2 + (n3 + 1) * 9, 8 + n2 * 18, 84 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.i.add(new of(this, hi2, n3, 8 + n3 * 18, 142));
        }
    }

    @Override
    protected void j() {
        this.g.b("Crafting", 86, 16, 0x404040);
    }

    @Override
    public void a(int n2, int n3, float f2) {
        super.a(n2, n3, f2);
        this.l = n2;
        this.m = n3;
    }

    @Override
    protected void a(float f2) {
        int n2 = this.b.n.a("/gui/inventory.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.b.n.b(n2);
        int n3 = (this.c - this.a) / 2;
        int n4 = (this.d - this.h) / 2;
        this.b(n3, n4, 0, 0, this.a, this.h);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(n3 + 51), (float)(n4 + 75), 50.0f);
        float f3 = 30.0f;
        GL11.glScalef((float)(-f3), (float)f3, (float)f3);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        float f4 = this.b.g.s;
        float f5 = this.b.g.aC;
        float f6 = this.b.g.aD;
        float f7 = (float)(n3 + 51) - this.l;
        float f8 = (float)(n4 + 75 - 50) - this.m;
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        lclass.b();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)(-((float)Math.atan(f8 / 40.0f)) * 20.0f), 1.0f, 0.0f, 0.0f);
        this.b.g.s = (float)Math.atan(f7 / 40.0f) * 20.0f;
        this.b.g.aC = (float)Math.atan(f7 / 40.0f) * 40.0f;
        this.b.g.aD = -((float)Math.atan(f8 / 40.0f)) * 20.0f;
        GL11.glTranslatef(0.0f, (float)this.b.g.aO, 0.0f);
        mn.a.a(this.b.g, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.b.g.s = f4;
        this.b.g.aC = f5;
        this.b.g.aD = f6;
        GL11.glPopMatrix();
        lclass.a();
        GL11.glDisable(32826);
    }
}

