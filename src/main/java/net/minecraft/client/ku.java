/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ku {
    private Minecraft a;
    private InventoryItem b = null;
    private float c = 0.0f;
    private float d = 0.0f;
    private bk e = new bk();

    public ku(Minecraft minecraft) {
        this.a = minecraft;
    }

    public void a(InventoryItem fp2) {
        GL11.glPushMatrix();
        if (fp2.id < 256 && bk.a(Block.registry[fp2.id].g())) {
            GL11.glBindTexture(3553, (int)this.a.n.a("/terrain.png"));
            this.e.a(Block.registry[fp2.id]);
        } else {
            float f2;
            float f3;
            float f4;
            int n2;
            if (fp2.id < 256) {
                GL11.glBindTexture(3553, (int)this.a.n.a("/terrain.png"));
            } else {
                GL11.glBindTexture(3553, (int)this.a.n.a("/gui/items.png"));
            }
            is is2 = is.a;
            float f5 = ((float)(fp2.b() % 16 * 16) + 0.0f) / 256.0f;
            float f6 = ((float)(fp2.b() % 16 * 16) + 15.99f) / 256.0f;
            float f7 = ((float)(fp2.b() / 16 * 16) + 0.0f) / 256.0f;
            float f8 = ((float)(fp2.b() / 16 * 16) + 15.99f) / 256.0f;
            float f9 = 1.0f;
            float f10 = 0.0f;
            float f11 = 0.3f;
            GL11.glEnable(32826);
            GL11.glTranslatef((float)(-f10), (float)(-f11), 0.0f);
            float f12 = 1.5f;
            GL11.glScalef((float)f12, (float)f12, (float)f12);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
            float f13 = 0.0625f;
            is2.b();
            is2.b(0.0f, 0.0f, 1.0f);
            is2.a(0.0, 0.0, 0.0, f6, f8);
            is2.a(f9, 0.0, 0.0, f5, f8);
            is2.a(f9, 1.0, 0.0, f5, f7);
            is2.a(0.0, 1.0, 0.0, f6, f7);
            is2.a();
            is2.b();
            is2.b(0.0f, 0.0f, -1.0f);
            is2.a(0.0, 1.0, 0.0f - f13, f6, f7);
            is2.a(f9, 1.0, 0.0f - f13, f5, f7);
            is2.a(f9, 0.0, 0.0f - f13, f5, f8);
            is2.a(0.0, 0.0, 0.0f - f13, f6, f8);
            is2.a();
            is2.b();
            is2.b(-1.0f, 0.0f, 0.0f);
            for (n2 = 0; n2 < 16; ++n2) {
                f4 = (float)n2 / 16.0f;
                f3 = f6 + (f5 - f6) * f4 - 0.001953125f;
                f2 = f9 * f4;
                is2.a(f2, 0.0, 0.0f - f13, f3, f8);
                is2.a(f2, 0.0, 0.0, f3, f8);
                is2.a(f2, 1.0, 0.0, f3, f7);
                is2.a(f2, 1.0, 0.0f - f13, f3, f7);
            }
            is2.a();
            is2.b();
            is2.b(1.0f, 0.0f, 0.0f);
            for (n2 = 0; n2 < 16; ++n2) {
                f4 = (float)n2 / 16.0f;
                f3 = f6 + (f5 - f6) * f4 - 0.001953125f;
                f2 = f9 * f4 + 0.0625f;
                is2.a(f2, 1.0, 0.0f - f13, f3, f7);
                is2.a(f2, 1.0, 0.0, f3, f7);
                is2.a(f2, 0.0, 0.0, f3, f8);
                is2.a(f2, 0.0, 0.0f - f13, f3, f8);
            }
            is2.a();
            is2.b();
            is2.b(0.0f, 1.0f, 0.0f);
            for (n2 = 0; n2 < 16; ++n2) {
                f4 = (float)n2 / 16.0f;
                f3 = f8 + (f7 - f8) * f4 - 0.001953125f;
                f2 = f9 * f4 + 0.0625f;
                is2.a(0.0, f2, 0.0, f6, f3);
                is2.a(f9, f2, 0.0, f5, f3);
                is2.a(f9, f2, 0.0f - f13, f5, f3);
                is2.a(0.0, f2, 0.0f - f13, f6, f3);
            }
            is2.a();
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            for (n2 = 0; n2 < 16; ++n2) {
                f4 = (float)n2 / 16.0f;
                f3 = f8 + (f7 - f8) * f4 - 0.001953125f;
                f2 = f9 * f4;
                is2.a(f9, f2, 0.0, f5, f3);
                is2.a(0.0, f2, 0.0, f6, f3);
                is2.a(0.0, f2, 0.0f - f13, f6, f3);
                is2.a(f9, f2, 0.0f - f13, f5, f3);
            }
            is2.a();
            GL11.glDisable(32826);
        }
        GL11.glPopMatrix();
    }

    public void a(float f2) {
        float f3 = this.d + (this.c - this.d) * f2;
        bq bq2 = this.a.g;
        GL11.glPushMatrix();
        GL11.glRotatef((float)(bq2.aF + (bq2.aD - bq2.aF) * f2), 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((float)(bq2.aE + (bq2.aC - bq2.aE) * f2), 0.0f, 1.0f, 0.0f);
        lclass.b();
        GL11.glPopMatrix();
        float f4 = this.a.e.c(TrigLookup.b(bq2.aw), TrigLookup.b(bq2.ax), TrigLookup.b(bq2.ay));
        GL11.glColor4f((float)f4, (float)f4, (float)f4, 1.0f);
        InventoryItem fp2 = this.b;
        if (bq2.n != null) {
            fp2 = new InventoryItem(Item.B.id);
        }
        if (fp2 != null) {
            GL11.glPushMatrix();
            float f5 = 0.8f;
            float f6 = bq2.d(f2);
            float f7 = TrigLookup.a(f6 * (float)Math.PI);
            float f8 = TrigLookup.a(TrigLookup.c(f6) * (float)Math.PI);
            GL11.glTranslatef((float)(-f8 * 0.4f), (float)(TrigLookup.a(TrigLookup.c(f6) * (float)Math.PI * 2.0f) * 0.2f), (float)(-f7 * 0.2f));
            GL11.glTranslatef((float)(0.7f * f5), (float)(-0.65f * f5 - (1.0f - f3) * 0.6f), (float)(-0.9f * f5));
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            f6 = bq2.d(f2);
            f7 = TrigLookup.a(f6 * f6 * (float)Math.PI);
            f8 = TrigLookup.a(TrigLookup.c(f6) * (float)Math.PI);
            GL11.glRotatef((float)(-f7 * 20.0f), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-f8 * 20.0f), 0.0f, 0.0f, 1.0f);
            GL11.glRotatef((float)(-f8 * 80.0f), 1.0f, 0.0f, 0.0f);
            f6 = 0.4f;
            GL11.glScalef((float)f6, (float)f6, (float)f6);
            if (fp2.a().b()) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            this.a(fp2);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            float f9 = 0.8f;
            float f10 = bq2.d(f2);
            float f11 = TrigLookup.a(f10 * (float)Math.PI);
            float f12 = TrigLookup.a(TrigLookup.c(f10) * (float)Math.PI);
            GL11.glTranslatef((float)(-f12 * 0.3f), (float)(TrigLookup.a(TrigLookup.c(f10) * (float)Math.PI * 2.0f) * 0.4f), (float)(-f11 * 0.4f));
            GL11.glTranslatef((float)(0.8f * f9), (float)(-0.75f * f9 - (1.0f - f3) * 0.6f), (float)(-0.9f * f9));
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            f10 = bq2.d(f2);
            f11 = TrigLookup.a(f10 * f10 * (float)Math.PI);
            f12 = TrigLookup.a(TrigLookup.c(f10) * (float)Math.PI);
            GL11.glRotatef((float)(f12 * 70.0f), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-f11 * 20.0f), 0.0f, 0.0f, 1.0f);
            GL11.glBindTexture(3553, (int)this.a.n.a(this.a.g.bl, this.a.g.A()));
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            aq aq2 = mn.a.a(this.a.g);
            Armor ce2 = (Armor)aq2;
            f12 = 1.0f;
            GL11.glScalef((float)f12, (float)f12, (float)f12);
            ce2.b();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        lclass.a();
    }

    public void b(float f2) {
        int n2;
        GL11.glDisable(3008);
        if (this.a.g.bg > 0 || this.a.g.bv) {
            n2 = this.a.n.a("/terrain.png");
            GL11.glBindTexture(3553, (int)n2);
            this.d(f2);
        }
        if (this.a.g.O()) {
            n2 = TrigLookup.b(this.a.g.aw);
            int n3 = TrigLookup.b(this.a.g.ax);
            int n4 = TrigLookup.b(this.a.g.ay);
            int n5 = this.a.n.a("/terrain.png");
            GL11.glBindTexture(3553, (int)n5);
            int n6 = this.a.e.a(n2, n3, n4);
            if (Block.registry[n6] != null) {
                this.a(f2, Block.registry[n6].a(2));
            }
        }
        if (this.a.g.a(hb.f)) {
            n2 = this.a.n.a("/misc/water.png");
            GL11.glBindTexture(3553, (int)n2);
            this.c(f2);
        }
        GL11.glEnable(3008);
    }

    private void a(float f2, int n2) {
        is is2 = is.a;
        float f3 = this.a.g.a(f2);
        f3 = 0.1f;
        GL11.glColor4f((float)f3, (float)f3, (float)f3, 0.5f);
        GL11.glPushMatrix();
        float f4 = -1.0f;
        float f5 = 1.0f;
        float f6 = -1.0f;
        float f7 = 1.0f;
        float f8 = -0.5f;
        float f9 = 0.0078125f;
        float f10 = (float)(n2 % 16) / 256.0f - f9;
        float f11 = ((float)(n2 % 16) + 15.99f) / 256.0f + f9;
        float f12 = (float)(n2 / 16) / 256.0f - f9;
        float f13 = ((float)(n2 / 16) + 15.99f) / 256.0f + f9;
        is2.b();
        is2.a(f4, f6, f8, f11, f13);
        is2.a(f5, f6, f8, f10, f13);
        is2.a(f5, f7, f8, f10, f12);
        is2.a(f4, f7, f8, f11, f12);
        is2.a();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void c(float f2) {
        is is2 = is.a;
        float f3 = this.a.g.a(f2);
        GL11.glColor4f((float)f3, (float)f3, (float)f3, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f4 = 4.0f;
        float f5 = -1.0f;
        float f6 = 1.0f;
        float f7 = -1.0f;
        float f8 = 1.0f;
        float f9 = -0.5f;
        float f10 = -this.a.g.aC / 64.0f;
        float f11 = this.a.g.aD / 64.0f;
        is2.b();
        is2.a(f5, f7, f9, f4 + f10, f4 + f11);
        is2.a(f6, f7, f9, 0.0f + f10, f4 + f11);
        is2.a(f6, f8, f9, 0.0f + f10, 0.0f + f11);
        is2.a(f5, f8, f9, f4 + f10, 0.0f + f11);
        is2.a();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    private void d(float f2) {
        is is2 = is.a;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float f3 = 1.0f;
        for (int i2 = 0; i2 < 2; ++i2) {
            GL11.glPushMatrix();
            int n2 = Block.ar.bg + i2 * 16;
            int n3 = (n2 & 0xF) << 4;
            int n4 = n2 & 0xF0;
            float f4 = (float)n3 / 256.0f;
            float f5 = ((float)n3 + 15.99f) / 256.0f;
            float f6 = (float)n4 / 256.0f;
            float f7 = ((float)n4 + 15.99f) / 256.0f;
            float f8 = (0.0f - f3) / 2.0f;
            float f9 = f8 + f3;
            float f10 = 0.0f - f3 / 2.0f;
            float f11 = f10 + f3;
            float f12 = -0.5f;
            GL11.glTranslatef((float)((float)(-(i2 * 2 - 1)) * 0.24f), -0.3f, 0.0f);
            GL11.glRotatef((float)((float)(i2 * 2 - 1) * 10.0f), 0.0f, 1.0f, 0.0f);
            is2.b();
            is2.a(f8, f10, f12, f5, f7);
            is2.a(f9, f10, f12, f4, f7);
            is2.a(f9, f11, f12, f4, f6);
            is2.a(f8, f11, f12, f5, f6);
            is2.a();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    public void a() {
        float f2;
        this.d = this.c;
        bq bq2 = this.a.g;
        InventoryItem fp2 = bq2.e.a();
        InventoryItem fp3 = fp2;
        float f3 = fp3 == this.b ? 1.0f : 0.0f;
        float f4 = f3 - this.c;
        if (f4 < -0.4f) {
            f4 = -0.4f;
        }
        if (f4 > 0.4f) {
            f4 = 0.4f;
        }
        this.c += f4;
        if (this.c < 0.1f) {
            this.b = fp3;
        }
    }

    public void b() {
        this.c = 0.0f;
    }

    public void c() {
        this.c = 0.0f;
    }
}

