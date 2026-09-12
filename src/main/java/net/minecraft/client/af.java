/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class af
extends aq {
    private bk a = new bk();
    private Random e = new Random();

    public af() {
        this.c = 0.15f;
        this.d = 0.75f;
    }

    public void a(DroppedItem eo2, double d2, double d3, double d4, float f2, float f3) {
        this.e.setSeed(187L);
        InventoryItem fp2 = eo2.a;
        GL11.glPushMatrix();
        float f4 = TrigLookup.a(((float)eo2.b + f3) / 10.0f + eo2.d) * 0.1f + 0.1f;
        float f5 = (((float)eo2.b + f3) / 20.0f + eo2.d) * 57.295776f;
        int n2 = 1;
        if (eo2.a.count > 1) {
            n2 = 2;
        }
        if (eo2.a.count > 5) {
            n2 = 3;
        }
        if (eo2.a.count > 20) {
            n2 = 4;
        }
        GL11.glTranslatef((float)((float)d2), (float)((float)d3 + f4), (float)((float)d4));
        GL11.glEnable(32826);
        if (fp2.id < 256 && bk.a(Block.registry[fp2.id].g())) {
            GL11.glRotatef((float)f5, 0.0f, 1.0f, 0.0f);
            this.a("/terrain.png");
            float f6 = 0.25f;
            if (!Block.registry[fp2.id].b() && fp2.id != Block.ak.bh) {
                f6 = 0.5f;
            }
            GL11.glScalef((float)f6, (float)f6, (float)f6);
            for (int i2 = 0; i2 < n2; ++i2) {
                GL11.glPushMatrix();
                if (i2 > 0) {
                    float f7 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.2f / f6;
                    float f8 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.2f / f6;
                    float f9 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.2f / f6;
                    GL11.glTranslatef((float)f7, (float)f8, (float)f9);
                }
                this.a.a(Block.registry[fp2.id]);
                GL11.glPopMatrix();
            }
        } else {
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            int n3 = fp2.b();
            if (fp2.id < 256) {
                this.a("/terrain.png");
            } else {
                this.a("/gui/items.png");
            }
            is is2 = is.a;
            float f10 = (float)(n3 % 16 * 16 + 0) / 256.0f;
            float f11 = (float)(n3 % 16 * 16 + 16) / 256.0f;
            float f12 = (float)(n3 / 16 * 16 + 0) / 256.0f;
            float f13 = (float)(n3 / 16 * 16 + 16) / 256.0f;
            float f14 = 1.0f;
            float f15 = 0.5f;
            float f16 = 0.25f;
            for (int i3 = 0; i3 < n2; ++i3) {
                GL11.glPushMatrix();
                if (i3 > 0) {
                    float f17 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f18 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f19 = (this.e.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef((float)f17, (float)f18, (float)f19);
                }
                GL11.glRotatef((float)(180.0f - this.b.i), 0.0f, 1.0f, 0.0f);
                is2.b();
                is2.b(0.0f, 1.0f, 0.0f);
                is2.a(0.0f - f15, 0.0f - f16, 0.0, f10, f13);
                is2.a(f14 - f15, 0.0f - f16, 0.0, f11, f13);
                is2.a(f14 - f15, 1.0f - f16, 0.0, f11, f12);
                is2.a(0.0f - f15, 1.0f - f16, 0.0, f10, f12);
                is2.a();
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void a(ls ls2, fu fu2, InventoryItem fp2, int n2, int n3) {
        if (fp2 == null) {
            return;
        }
        if (fp2.id < 256 && bk.a(Block.registry[fp2.id].g())) {
            int n4 = fp2.id;
            fu2.b(fu2.a("/terrain.png"));
            Block nq2 = Block.registry[n4];
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(n2 - 2), (float)(n3 + 3), 0.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 8.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            this.a.a(nq2);
            GL11.glPopMatrix();
        } else if (fp2.b() >= 0) {
            GL11.glDisable(2896);
            if (fp2.id < 256) {
                fu2.b(fu2.a("/terrain.png"));
            } else {
                fu2.b(fu2.a("/gui/items.png"));
            }
            this.a(n2, n3, fp2.b() % 16 * 16, fp2.b() / 16 * 16, 16, 16);
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
    }

    public void b(ls ls2, fu fu2, InventoryItem fp2, int n2, int n3) {
        if (fp2 == null) {
            return;
        }
        if (fp2.count > 1) {
            String string = "" + fp2.count;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            ls2.a(string, n2 + 19 - 2 - ls2.a(string), n3 + 6 + 3, 0xFFFFFF);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
        if (fp2.damage > 0) {
            int n4 = 13 - fp2.damage * 13 / fp2.d();
            int n5 = 255 - fp2.damage * 255 / fp2.d();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            is is2 = is.a;
            int n6 = 255 - n5 << 16 | n5 << 8;
            int n7 = (255 - n5) / 4 << 16 | 0x3F00;
            this.a(is2, n2 + 2, n3 + 13, 13, 2, 0);
            this.a(is2, n2 + 2, n3 + 13, 12, 1, n7);
            this.a(is2, n2 + 2, n3 + 13, n4, 1, n6);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private void a(is is2, int n2, int n3, int n4, int n5, int n6) {
        is2.b();
        is2.b(n6);
        is2.a((double)(n2 + 0), (double)(n3 + 0), 0.0);
        is2.a((double)(n2 + 0), (double)(n3 + n5), 0.0);
        is2.a((double)(n2 + n4), (double)(n3 + n5), 0.0);
        is2.a((double)(n2 + n4), (double)(n3 + 0), 0.0);
        is2.a();
    }

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        float f2 = 0.0f;
        float f3 = 0.00390625f;
        float f4 = 0.00390625f;
        is is2 = is.a;
        is2.b();
        is2.a(n2 + 0, n3 + n7, f2, (float)(n4 + 0) * f3, (float)(n5 + n7) * f4);
        is2.a(n2 + n6, n3 + n7, f2, (float)(n4 + n6) * f3, (float)(n5 + n7) * f4);
        is2.a(n2 + n6, n3 + 0, f2, (float)(n4 + n6) * f3, (float)(n5 + 0) * f4);
        is2.a(n2 + 0, n3 + 0, f2, (float)(n4 + 0) * f3, (float)(n5 + 0) * f4);
        is2.a();
    }

    @Override
    public void a(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        this.a((DroppedItem)lw2, d2, d3, d4, f2, f3);
    }
}

