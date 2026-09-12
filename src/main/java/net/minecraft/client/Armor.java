/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class Armor
extends ec {
    private dc a;
    private dc g;
    private dc h;
    static private final String[] i = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

    public Armor() {
        super(new dc(0.0f), 0.5f);
        this.a = (dc)this.e;
        this.g = new dc(1.0f);
        this.h = new dc(0.5f);
    }

    protected boolean a(Player eb2, int n2) {
        Item dx2;
        InventoryItem fp2 = eb2.e.d(3 - n2);
        if (fp2 != null && (dx2 = fp2.a()) instanceof oj) {
            oj oj2 = (oj)dx2;
            this.a("/armor/" + i[oj2.bd] + "_" + (n2 == 2 ? 2 : 1) + ".png");
            dc dc2 = n2 == 2 ? this.h : this.g;
            dc2.a.h = n2 == 0;
            dc2.b.h = n2 == 0;
            dc2.c.h = n2 == 1 || n2 == 2;
            dc2.d.h = n2 == 1;
            dc2.e.h = n2 == 1;
            dc2.f.h = n2 == 2 || n2 == 3;
            dc2.g.h = n2 == 2 || n2 == 3;
            this.a(dc2);
            return true;
        }
        return false;
    }

    public void a(Player eb2, double d2, double d3, double d4, float f2, float f3) {
        float f4;
        InventoryItem fp2 = eb2.e.a();
        this.a.i = fp2 != null;
        this.h.i = this.a.i;
        this.g.i = this.a.i;
        this.h.j = this.a.j = eb2.p();
        this.g.j = this.a.j;
        double d5 = d3 - (double)eb2.aO;
        if (eb2.bw) {
            d5 -= 0.125;
        }
        super.a(eb2, d2, d5, d4, f2, f3);
        this.a.j = false;
        this.h.j = false;
        this.g.j = false;
        this.a.i = false;
        this.h.i = false;
        this.g.i = false;
        float f5 = 1.6f;
        float f6 = 0.016666668f * f5;
        float f7 = eb2.e(this.b.h);
        float f8 = f4 = eb2.p() ? 32.0f : 64.0f;
        if (f7 < f4) {
            f6 = (float)((double)f6 * (Math.sqrt(f7) / 2.0));
            ls ls2 = this.a();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)d2 + 0.0f), (float)((float)d3 + 2.3f), (float)((float)d4));
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-this.b.i), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)this.b.j, 1.0f, 0.0f, 0.0f);
            GL11.glScalef((float)(-f6), (float)(-f6), (float)f6);
            String string = eb2.name;
            GL11.glDisable(2896);
            if (!eb2.p()) {
                GL11.glDepthMask((boolean)false);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                is is2 = is.a;
                GL11.glDisable(3553);
                is2.b();
                int n2 = ls2.a(string) / 2;
                is2.a(0.0f, 0.0f, 0.0f, 0.25f);
                is2.a((double)(-n2 - 1), -1.0, 0.0);
                is2.a((double)(-n2 - 1), 8.0, 0.0);
                is2.a((double)(n2 + 1), 8.0, 0.0);
                is2.a((double)(n2 + 1), -1.0, 0.0);
                is2.a();
                GL11.glEnable(3553);
                ls2.b(string, -ls2.a(string) / 2, 0, 0x20FFFFFF);
                GL11.glEnable(2929);
                GL11.glDepthMask((boolean)true);
                ls2.b(string, -ls2.a(string) / 2, 0, -1);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            } else {
                GL11.glTranslatef(0.0f, (float)(0.25f / f6), 0.0f);
                GL11.glDepthMask((boolean)false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                is is3 = is.a;
                GL11.glDisable(3553);
                is3.b();
                int n3 = ls2.a(string) / 2;
                is3.a(0.0f, 0.0f, 0.0f, 0.25f);
                is3.a((double)(-n3 - 1), -1.0, 0.0);
                is3.a((double)(-n3 - 1), 8.0, 0.0);
                is3.a((double)(n3 + 1), 8.0, 0.0);
                is3.a((double)(n3 + 1), -1.0, 0.0);
                is3.a();
                GL11.glEnable(3553);
                GL11.glDepthMask((boolean)true);
                ls2.b(string, -ls2.a(string) / 2, 0, 0x20FFFFFF);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
    }

    protected void a(Player eb2, float f2) {
        InventoryItem fp2;
        InventoryItem fp3 = eb2.e.d(3);
        if (fp3 != null && fp3.a().id < 256) {
            GL11.glPushMatrix();
            this.a.a.b(0.0625f);
            if (bk.a(Block.registry[fp3.id].g())) {
                float f3 = 0.625f;
                GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef((float)f3, (float)(-f3), (float)f3);
            }
            this.b.f.a(fp3);
            GL11.glPopMatrix();
        }
        if ((fp2 = eb2.e.a()) != null) {
            GL11.glPushMatrix();
            this.a.d.b(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (eb2.n != null) {
                fp2 = new InventoryItem(Item.B.id);
            }
            if (fp2.id < 256 && bk.a(Block.registry[fp2.id].g())) {
                float f4 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef((float)(f4 *= 0.75f), (float)(-f4), (float)f4);
            } else if (Item.registry[fp2.id].a()) {
                float f5 = 0.625f;
                if (Item.registry[fp2.id].b()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef((float)f5, (float)(-f5), (float)f5);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            } else {
                float f6 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef((float)f6, (float)f6, (float)f6);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            this.b.f.a(fp2);
            GL11.glPopMatrix();
        }
    }

    protected void b(Player eb2, float f2) {
        float f3 = 0.9375f;
        GL11.glScalef((float)f3, (float)f3, (float)f3);
    }

    public void b() {
        this.a.k = 0.0f;
        this.a.a(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.a.d.a(0.0625f);
    }

    @Override
    protected void a(Mob hf2, float f2) {
        this.b((Player)hf2, f2);
    }

    @Override
    protected boolean a(Mob hf2, int n2) {
        return this.a((Player)hf2, n2);
    }

    @Override
    protected void b(Mob hf2, float f2) {
        this.a((Player)hf2, f2);
    }

    @Override
    public void a(Mob hf2, double d2, double d3, double d4, float f2, float f3) {
        this.a((Player)hf2, d2, d3, d4, f2, f3);
    }

    @Override
    public void a(lw lw2, double d2, double d3, double d4, float f2, float f3) {
        this.a((Player)lw2, d2, d3, d4, f2, f3);
    }
}

