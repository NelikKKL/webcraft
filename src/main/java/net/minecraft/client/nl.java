/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class nl
extends nb {
    static private af d = new af();
    private List e = new ArrayList();
    private Random f = new Random();
    private Minecraft g;
    public String a = null;
    private int h = 0;
    private String i = "";
    private int j = 0;
    public float b;
    float c = 1.0f;

    public nl(Minecraft minecraft) {
        this.g = minecraft;
    }

    public void a(float f2, boolean bl2, int n2, int n3) {
        String string;
        int n4;
        int n5;
        int n6;
        boolean bl3;
        float f3;
        kl kl2 = new kl(this.g.c, this.g.d);
        int n7 = kl2.a();
        int n8 = kl2.b();
        ls ls2 = this.g.o;
        this.g.r.b();
        GL11.glEnable(3042);
        if (this.g.y.i) {
            this.a(this.g.g.a(f2), n7, n8);
        }
        InventoryItem fp2 = this.g.g.e.d(3);
        if (!this.g.y.y && fp2 != null && fp2.id == Block.ba.bh) {
            this.a(n7, n8);
        }
        if ((f3 = this.g.g.d + (this.g.g.c - this.g.g.d) * f2) > 0.0f) {
            this.b(f3, n7, n8);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBindTexture(3553, (int)this.g.n.a("/gui/gui.png"));
        Inventory fo2 = this.g.g.e;
        this.k = -90.0f;
        this.b(n7 / 2 - 91, n8 - 22, 0, 0, 182, 22);
        this.b(n7 / 2 - 91 - 1 + fo2.d * 20, n8 - 22 - 1, 0, 22, 24, 22);
        GL11.glBindTexture(3553, (int)this.g.n.a("/gui/icons.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(775, 769);
        this.b(n7 / 2 - 7, n8 / 2 - 7, 0, 0, 16, 16);
        GL11.glDisable(3042);
        boolean bl4 = bl3 = this.g.g.bj / 3 % 2 == 1;
        if (this.g.g.bj < 10) {
            bl3 = false;
        }
        int n9 = this.g.g.J;
        int n10 = this.g.g.K;
        this.f.setSeed(this.h * 312871);
        if (this.g.b.d()) {
            int n11;
            n6 = this.g.g.n();
            for (n5 = 0; n5 < 10; ++n5) {
                n4 = n8 - 32;
                if (n6 > 0) {
                    n11 = n7 / 2 + 91 - n5 * 8 - 9;
                    if (n5 * 2 + 1 < n6) {
                        this.b(n11, n4, 34, 9, 9, 9);
                    }
                    if (n5 * 2 + 1 == n6) {
                        this.b(n11, n4, 25, 9, 9, 9);
                    }
                    if (n5 * 2 + 1 > n6) {
                        this.b(n11, n4, 16, 9, 9, 9);
                    }
                }
                n11 = 0;
                if (bl3) {
                    n11 = 1;
                }
                int n12 = n7 / 2 - 91 + n5 * 8;
                if (n9 <= 4) {
                    n4 += this.f.nextInt(2);
                }
                this.b(n12, n4, 16 + n11 * 9, 0, 9, 9);
                if (bl3) {
                    if (n5 * 2 + 1 < n10) {
                        this.b(n12, n4, 70, 0, 9, 9);
                    }
                    if (n5 * 2 + 1 == n10) {
                        this.b(n12, n4, 79, 0, 9, 9);
                    }
                }
                if (n5 * 2 + 1 < n9) {
                    this.b(n12, n4, 52, 0, 9, 9);
                }
                if (n5 * 2 + 1 != n9) continue;
                this.b(n12, n4, 61, 0, 9, 9);
            }
            if (this.g.g.a(hb.f)) {
                n5 = (int)Math.ceil((double)(this.g.g.bk - 2) * 10.0 / 300.0);
                n4 = (int)Math.ceil((double)this.g.g.bk * 10.0 / 300.0) - n5;
                for (n11 = 0; n11 < n5 + n4; ++n11) {
                    if (n11 < n5) {
                        this.b(n7 / 2 - 91 + n11 * 8, n8 - 32 - 9, 16, 18, 9, 9);
                        continue;
                    }
                    this.b(n7 / 2 - 91 + n11 * 8, n8 - 32 - 9, 25, 18, 9, 9);
                }
            }
        }
        GL11.glDisable(3042);
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        lclass.b();
        GL11.glPopMatrix();
        for (n6 = 0; n6 < 9; ++n6) {
            n5 = n7 / 2 - 90 + n6 * 20 + 2;
            n4 = n8 - 16 - 3;
            this.a(n6, n5, n4, f2);
        }
        lclass.a();
        GL11.glDisable(32826);
        if (Keyboard.isKeyDown(61)) {
            ls2.a("Minecraft Alpha v1.2.6 (" + this.g.I + ")", 2, 2, 0xFFFFFF);
            ls2.a(this.g.m(), 2, 12, 0xFFFFFF);
            ls2.a(this.g.n(), 2, 22, 0xFFFFFF);
            ls2.a(this.g.o(), 2, 32, 0xFFFFFF);
            // ПАТЧЕНО для web-порта: Runtime.getRuntime().maxMemory/
            // totalMemory/freeMemory не поддерживаются TeaVM ("Method
            // java.lang.Runtime.maxMemory()J was not found") — у JS-кучи
            // нет прямого аналога этих понятий. Блок отображения памяти в
            // F3-экране убран, остальная отладочная информация (позиция
            // и т.д.) не тронута.
            this.b(ls2, "x: " + this.g.g.aw, 2, 64, 0xE0E0E0);
            this.b(ls2, "y: " + this.g.g.ax, 2, 72, 0xE0E0E0);
            this.b(ls2, "z: " + this.g.g.ay, 2, 80, 0xE0E0E0);
        } else {
            ls2.a("Minecraft Alpha v1.2.6", 2, 2, 0xFFFFFF);
        }
        if (this.j > 0) {
            float f4 = (float)this.j - f2;
            n5 = (int)(f4 * 256.0f / 20.0f);
            if (n5 > 255) {
                n5 = 255;
            }
            if (n5 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(n7 / 2), (float)(n8 - 48), 0.0f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                int n13 = Color.HSBtoRGB(f4 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                ls2.b(this.i, -ls2.a(this.i) / 2, -4, n13 + (n5 << 24));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
        int n14 = 10;
        n5 = 0;
        if (this.g.p instanceof dr) {
            n14 = 20;
            n5 = 1;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, (float)(n8 - 48), 0.0f);
        for (int i2 = 0; i2 < this.e.size() && i2 < n14; ++i2) {
            if (((me)this.e.get((int)i2)).b >= 200 && n5 == 0) continue;
            double d2 = (double)((me)this.e.get((int)i2)).b / 200.0;
            d2 = 1.0 - d2;
            if ((d2 *= 10.0) < 0.0) {
                d2 = 0.0;
            }
            if (d2 > 1.0) {
                d2 = 1.0;
            }
            d2 *= d2;
            int n15 = (int)(255.0 * d2);
            if (n5 != 0) {
                n15 = 255;
            }
            if (n15 <= 0) continue;
            int n16 = 2;
            int n17 = -i2 * 9;
            string = ((me)this.e.get((int)i2)).a;
            this.a(n16, n17 - 1, n16 + 320, n17 + 8, n15 / 2 << 24);
            GL11.glEnable(3042);
            ls2.a(string, n16, n17, 0xFFFFFF + (n15 << 24));
        }
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }

    private void a(int n2, int n3) {
        GL11.glDisable(2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, (int)this.g.n.a("%blur%/misc/pumpkinblur.png"));
        is is2 = is.a;
        is2.b();
        is2.a(0.0, n3, -90.0, 0.0, 1.0);
        is2.a(n2, n3, -90.0, 1.0, 1.0);
        is2.a(n2, 0.0, -90.0, 1.0, 0.0);
        is2.a(0.0, 0.0, -90.0, 0.0, 0.0);
        is2.a();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void a(float f2, int n2, int n3) {
        if ((f2 = 1.0f - f2) < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        this.c = (float)((double)this.c + (double)(f2 - this.c) * 0.01);
        GL11.glDisable(2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f((float)this.c, (float)this.c, (float)this.c, 1.0f);
        GL11.glBindTexture(3553, (int)this.g.n.a("%blur%/misc/vignette.png"));
        is is2 = is.a;
        is2.b();
        is2.a(0.0, n3, -90.0, 0.0, 1.0);
        is2.a(n2, n3, -90.0, 1.0, 1.0);
        is2.a(n2, 0.0, -90.0, 1.0, 0.0);
        is2.a(0.0, 0.0, -90.0, 0.0, 0.0);
        is2.a();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
    }

    private void b(float f2, int n2, int n3) {
        f2 *= f2;
        f2 *= f2;
        f2 = f2 * 0.8f + 0.2f;
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (float)f2);
        GL11.glBindTexture(3553, (int)this.g.n.a("/terrain.png"));
        float f3 = (float)(Block.be.bg % 16) / 16.0f;
        float f4 = (float)(Block.be.bg / 16) / 16.0f;
        float f5 = (float)(Block.be.bg % 16 + 1) / 16.0f;
        float f6 = (float)(Block.be.bg / 16 + 1) / 16.0f;
        is is2 = is.a;
        is2.b();
        is2.a(0.0, n3, -90.0, f3, f6);
        is2.a(n2, n3, -90.0, f5, f6);
        is2.a(n2, 0.0, -90.0, f5, f4);
        is2.a(0.0, 0.0, -90.0, f3, f4);
        is2.a();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void a(int n2, int n3, int n4, float f2) {
        InventoryItem fp2 = this.g.g.e.storage[n2];
        if (fp2 == null) {
            return;
        }
        float f3 = (float)fp2.probablyCooldown - 0.4f;
        if (f3 > 0.0f) {
            GL11.glPushMatrix();
            float f4 = 1.0f + f3 / 5.0f;
            GL11.glTranslatef((float)(n3 + 8), (float)(n4 + 12), 0.0f);
            GL11.glScalef((float)(1.0f / f4), (float)((f4 + 1.0f) / 2.0f), 1.0f);
            GL11.glTranslatef((float)(-(n3 + 8)), (float)(-(n4 + 12)), 0.0f);
        }
        d.a(this.g.o, this.g.n, fp2, n3, n4);
        if (f3 > 0.0f) {
            GL11.glPopMatrix();
        }
        d.b(this.g.o, this.g.n, fp2, n3, n4);
    }

    public void a() {
        if (this.j > 0) {
            --this.j;
        }
        ++this.h;
        for (int i2 = 0; i2 < this.e.size(); ++i2) {
            ++((me)this.e.get((int)i2)).b;
        }
    }

    public void a(String string) {
        while (this.g.o.a(string) > 320) {
            int n2;
            for (n2 = 1; n2 < string.length() && this.g.o.a(string.substring(0, n2 + 1)) <= 320; ++n2) {
            }
            this.a(string.substring(0, n2));
            string = string.substring(n2);
        }
        this.e.add(0, new me(string));
        while (this.e.size() > 50) {
            this.e.remove(this.e.size() - 1);
        }
    }

    public void b(String string) {
        this.i = "Now playing: " + string;
        this.j = 60;
    }
}

