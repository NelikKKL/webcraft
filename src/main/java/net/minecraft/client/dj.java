/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class dj
extends bp {
    static private final Random h = new Random();
    String[] a = new String[]{" *   * * *   * *** *** *** *** *** ***", " ** ** * **  * *   *   * * * * *    * ", " * * * * * * * **  *   **  *** **   * ", " *   * * *  ** *   *   * * * * *    * ", " *   * * *   * *** *** * * * * *    * "};
    private lr[][] i;
    private float j = 0.0f;
    private String l = "missingno";

    public dj() {
        try {
            ArrayList<String> arrayList = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dj.class.getResourceAsStream("/title/splashes.txt")));
            String string = "";
            while ((string = bufferedReader.readLine()) != null) {
                if ((string = string.trim()).length() <= 0) continue;
                arrayList.add(string);
            }
            this.l = (String)arrayList.get(h.nextInt(arrayList.size()));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void g() {
        this.j += 1.0f;
        if (this.i != null) {
            for (int i2 = 0; i2 < this.i.length; ++i2) {
                for (int i3 = 0; i3 < this.i[i2].length; ++i3) {
                    this.i[i2][i3].a();
                }
            }
        }
    }

    @Override
    protected void a(char c2, int n2) {
    }

    @Override
    public void a() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9) {
            this.l = "Happy birthday, ez!";
        } else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1) {
            this.l = "Happy birthday, Notch!";
        } else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.l = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.l = "Happy new year!";
        }
        this.e.add(new gh(1, this.c / 2 - 100, this.d / 4 + 48, "Singleplayer"));
        this.e.add(new gh(2, this.c / 2 - 100, this.d / 4 + 72, "Multiplayer"));
        this.e.add(new gh(3, this.c / 2 - 100, this.d / 4 + 96, "Mods and Texture Packs"));
        this.e.add(new gh(0, this.c / 2 - 100, this.d / 4 + 120 + 12, "Options..."));
        if (this.b.i == null) {
            ((gh)this.e.get(1)).g = false;
        }
    }

    @Override
    protected void a(gh gh2) {
        if (gh2.f == 0) {
            this.b.a(new bf(this, this.b.y));
        }
        if (gh2.f == 1) {
            this.b.a(new le(this));
        }
        if (gh2.f == 2) {
            this.b.a(new hd(this));
        }
        if (gh2.f == 3) {
            this.b.a(new dk(this));
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        is is2 = is.a;
        this.a(f2);
        GL11.glBindTexture(3553, (int)this.b.n.a("/gui/logo.png"));
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        is2.b(0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.c / 2 + 90), 70.0f, 0.0f);
        GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        float f3 = 1.8f - TrigLookup.e(TrigLookup.a((float)(System.currentTimeMillis() % 1000L) / 1000.0f * (float)Math.PI * 2.0f) * 0.1f);
        f3 = f3 * 100.0f / (float)(this.g.a(this.l) + 32);
        GL11.glScalef((float)f3, (float)f3, (float)f3);
        this.a(this.g, this.l, 0, -8, 0xFFFF00);
        GL11.glPopMatrix();
        this.b(this.g, "Minecraft Alpha v1.2.6", 2, 2, 0x505050);
        String string = "Copyright Mojang Specifications. Do not distribute.";
        this.b(this.g, string, this.c - this.g.a(string) - 2, this.d - 10, 0xFFFFFF);
        super.a(n2, n3, f2);
    }

    private void a(float f2) {
        int n2;
        if (this.i == null) {
            this.i = new lr[this.a[0].length()][this.a.length];
            for (int i2 = 0; i2 < this.i.length; ++i2) {
                for (n2 = 0; n2 < this.i[i2].length; ++n2) {
                    this.i[i2][n2] = new lr(this, i2, n2);
                }
            }
        }
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        kl kl2 = new kl(this.b.c, this.b.d);
        n2 = 120 * kl2.a;
        GLU.gluPerspective(70.0f, (float)((float)this.b.c / (float)n2), 0.05f, 100.0f);
        GL11.glViewport(0, (int)(this.b.d - n2), (int)this.b.c, (int)n2);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glDisable(2884);
        GL11.glCullFace(1029);
        GL11.glDepthMask((boolean)true);
        bk bk2 = new bk();
        for (int i3 = 0; i3 < 3; ++i3) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.4f, 0.6f, -13.0f);
            if (i3 == 0) {
                GL11.glClear(256);
                GL11.glTranslatef(0.0f, -0.4f, 0.0f);
                GL11.glScalef(0.98f, 1.0f, 1.0f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (i3 == 1) {
                GL11.glDisable(3042);
                GL11.glClear(256);
            }
            if (i3 == 2) {
                GL11.glEnable(3042);
                GL11.glBlendFunc(768, 1);
            }
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(0.89f, 1.0f, 0.4f);
            GL11.glTranslatef((float)((float)(-this.a[0].length()) * 0.5f), (float)((float)(-this.a.length) * 0.5f), 0.0f);
            GL11.glBindTexture(3553, (int)this.b.n.a("/terrain.png"));
            if (i3 == 0) {
                GL11.glBindTexture(3553, (int)this.b.n.a("/title/black.png"));
            }
            for (int i4 = 0; i4 < this.a.length; ++i4) {
                for (int i5 = 0; i5 < this.a[i4].length(); ++i5) {
                    char c2 = this.a[i4].charAt(i5);
                    if (c2 == ' ') continue;
                    GL11.glPushMatrix();
                    lr lr2 = this.i[i5][i4];
                    float f3 = (float)(lr2.b + (lr2.a - lr2.b) * (double)f2);
                    float f4 = 1.0f;
                    float f5 = 1.0f;
                    float f6 = 0.0f;
                    if (i3 == 0) {
                        f4 = f3 * 0.04f + 1.0f;
                        f5 = 1.0f / f4;
                        f3 = 0.0f;
                    }
                    GL11.glTranslatef((float)i5, (float)i4, (float)f3);
                    GL11.glScalef((float)f4, (float)f4, (float)f4);
                    GL11.glRotatef((float)f6, 0.0f, 1.0f, 0.0f);
                    bk2.a(Block.t, f5);
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
        }
        GL11.glDisable(3042);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glViewport(0, 0, (int)this.b.c, (int)this.b.d);
        GL11.glEnable(2884);
    }

    static Random j() {
        return h;
    }
}

