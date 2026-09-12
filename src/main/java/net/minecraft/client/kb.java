/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class kb {
    private Minecraft h;
    private float i = 0.0f;
    public ku a;
    private int j;
    private lw k = null;
    private long l = System.currentTimeMillis();
    private Random m = new Random();
    volatile int b = 0;
    volatile int c = 0;
    FloatBuffer d = ds.d(16);
    float e;
    float f;
    float g;
    private float n;
    private float o;

    public kb(Minecraft minecraft) {
        this.h = minecraft;
        this.a = new ku(minecraft);
    }

    public void a() {
        this.n = this.o;
        float f2 = this.h.e.c(TrigLookup.b(this.h.g.aw), TrigLookup.b(this.h.g.ax), TrigLookup.b(this.h.g.ay));
        float f3 = (float)(3 - this.h.y.e) / 3.0f;
        float f4 = f2 * (1.0f - f3) + f3;
        this.o += (f4 - this.o) * 0.1f;
        ++this.j;
        this.a.a();
        if (this.h.M) {
            this.c();
        }
    }

    public void a(float f2) {
        if (this.h.g == null) {
            return;
        }
        double d2 = this.h.b.b();
        this.h.x = this.h.g.a(d2, f2);
        double d3 = d2;
        Vector3D ao2 = this.h.g.e(f2);
        if (this.h.x != null) {
            d3 = this.h.x.f.c(ao2);
        }
        if (this.h.b instanceof jt) {
            d2 = 32.0;
            d3 = 32.0;
        } else {
            if (d3 > 3.0) {
                d3 = 3.0;
            }
            d2 = d3;
        }
        Vector3D ao3 = this.h.g.f(f2);
        Vector3D ao4 = ao2.c(ao3.x * d2, ao3.y * d2, ao3.z * d2);
        this.k = null;
        float f3 = 1.0f;
        List list = this.h.e.b(this.h.g, this.h.g.aG.a(ao3.x * d2, ao3.y * d2, ao3.z * d2).b(f3, f3, f3));
        double d4 = 0.0;
        for (int i2 = 0; i2 < list.size(); ++i2) {
            double d5;
            lw lw2 = (lw)list.get(i2);
            if (!lw2.n_()) continue;
            float f4 = lw2.k_();
            co co2 = lw2.aG.b(f4, f4, f4);
            nx nx2 = co2.a(ao2, ao4);
            if (co2.a(ao2)) {
                if (!(0.0 < d4) && d4 != 0.0) continue;
                this.k = lw2;
                d4 = 0.0;
                continue;
            }
            if (nx2 == null || !((d5 = ao2.c(nx2.f)) < d4) && d4 != 0.0) continue;
            this.k = lw2;
            d4 = d5;
        }
        if (this.k != null && !(this.h.b instanceof jt)) {
            this.h.x = new nx(this.k);
        }
    }

    private float d(float f2) {
        bq bq2 = this.h.g;
        float f3 = 70.0f;
        if (bq2.a(hb.f)) {
            f3 = 60.0f;
        }
        if (bq2.J <= 0) {
            float f4 = (float)bq2.O + f2;
            f3 /= (1.0f - 500.0f / (f4 + 500.0f)) * 2.0f + 1.0f;
        }
        return f3;
    }

    private void e(float f2) {
        float f3;
        bq bq2 = this.h.g;
        float f4 = (float)bq2.L - f2;
        if (bq2.J <= 0) {
            f3 = (float)bq2.O + f2;
            GL11.glRotatef((float)(40.0f - 8000.0f / (f3 + 200.0f)), 0.0f, 0.0f, 1.0f);
        }
        if (f4 < 0.0f) {
            return;
        }
        f4 /= (float)bq2.M;
        f4 = TrigLookup.a(f4 * f4 * f4 * f4 * (float)Math.PI);
        f3 = bq2.N;
        GL11.glRotatef((float)(-f3), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)(-f4 * 14.0f), 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)f3, 0.0f, 1.0f, 0.0f);
    }

    private void f(float f2) {
        if (this.h.y.y) {
            return;
        }
        bq bq2 = this.h.g;
        float f3 = bq2.aS - bq2.aR;
        float f4 = bq2.aS + f3 * f2;
        float f5 = bq2.h + (bq2.i - bq2.h) * f2;
        float f6 = bq2.Q + (bq2.R - bq2.Q) * f2;
        GL11.glTranslatef((float)(TrigLookup.a(f4 * (float)Math.PI) * f5 * 0.5f), (float)(-Math.abs(TrigLookup.b(f4 * (float)Math.PI) * f5)), 0.0f);
        GL11.glRotatef((float)(TrigLookup.a(f4 * (float)Math.PI) * f5 * 3.0f), 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(Math.abs(TrigLookup.b(f4 * (float)Math.PI + 0.2f) * f5) * 5.0f), 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((float)f6, 1.0f, 0.0f, 0.0f);
    }

    private void g(float f2) {
        bq bq2 = this.h.g;
        double d2 = bq2.at + (bq2.aw - bq2.at) * (double)f2;
        double d3 = bq2.au + (bq2.ax - bq2.au) * (double)f2;
        double d4 = bq2.av + (bq2.ay - bq2.av) * (double)f2;
        if (this.h.y.y) {
            double d5 = 4.0;
            float f3 = bq2.aC;
            float f4 = bq2.aD;
            if (Keyboard.isKeyDown(59)) {
                f4 += 180.0f;
                d5 += 2.0;
            }
            double d6 = (double)(-TrigLookup.a(f3 / 180.0f * (float)Math.PI) * TrigLookup.b(f4 / 180.0f * (float)Math.PI)) * d5;
            double d7 = (double)(TrigLookup.b(f3 / 180.0f * (float)Math.PI) * TrigLookup.b(f4 / 180.0f * (float)Math.PI)) * d5;
            double d8 = (double)(-TrigLookup.a(f4 / 180.0f * (float)Math.PI)) * d5;
            for (int i2 = 0; i2 < 8; ++i2) {
                double d9;
                nx nx2;
                float f5 = (i2 & 1) * 2 - 1;
                float f6 = (i2 >> 1 & 1) * 2 - 1;
                float f7 = (i2 >> 2 & 1) * 2 - 1;
                if ((nx2 = this.h.e.a(Vector3D.b(d2 + (double)(f5 *= 0.1f), d3 + (double)(f6 *= 0.1f), d4 + (double)(f7 *= 0.1f)), Vector3D.b(d2 - d6 + (double)f5 + (double)f7, d3 - d8 + (double)f6, d4 - d7 + (double)f7))) == null || !((d9 = nx2.f.c(Vector3D.b(d2, d3, d4))) < d5)) continue;
                d5 = d9;
            }
            if (Keyboard.isKeyDown(59)) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            GL11.glRotatef((float)(bq2.aD - f4), 1.0f, 0.0f, 0.0f);
            GL11.glRotatef((float)(bq2.aC - f3), 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, (float)((float)(-d5)));
            GL11.glRotatef((float)(f3 - bq2.aC), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(f4 - bq2.aD), 1.0f, 0.0f, 0.0f);
        } else {
            GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        }
        GL11.glRotatef((float)(bq2.aF + (bq2.aD - bq2.aF) * f2), 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((float)(bq2.aE + (bq2.aC - bq2.aE) * f2 + 180.0f), 0.0f, 1.0f, 0.0f);
    }

    private void a(float f2, int n2) {
        float f3;
        this.i = 256 >> this.h.y.e;
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        float f4 = 0.07f;
        if (this.h.y.g) {
            GL11.glTranslatef((float)((float)(-(n2 * 2 - 1)) * f4), 0.0f, 0.0f);
        }
        GLU.gluPerspective((float)this.d(f2), (float)((float)this.h.c / (float)this.h.d), 0.05f, (float)this.i);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.h.y.g) {
            GL11.glTranslatef((float)((float)(n2 * 2 - 1) * 0.1f), 0.0f, 0.0f);
        }
        this.e(f2);
        if (this.h.y.f) {
            this.f(f2);
        }
        if ((f3 = this.h.g.d + (this.h.g.c - this.h.g.d) * f2) > 0.0f) {
            float f5 = 5.0f / (f3 * f3 + 5.0f) - f3 * 0.04f;
            f5 *= f5;
            GL11.glRotatef((float)(f3 * f3 * 1500.0f), 0.0f, 1.0f, 1.0f);
            GL11.glScalef((float)(1.0f / f5), 1.0f, 1.0f);
            GL11.glRotatef((float)(-f3 * f3 * 1500.0f), 0.0f, 1.0f, 1.0f);
        }
        this.g(f2);
    }

    private void b(float f2, int n2) {
        GL11.glLoadIdentity();
        if (this.h.y.g) {
            GL11.glTranslatef((float)((float)(n2 * 2 - 1) * 0.1f), 0.0f, 0.0f);
        }
        GL11.glPushMatrix();
        this.e(f2);
        if (this.h.y.f) {
            this.f(f2);
        }
        if (!this.h.y.y && !Keyboard.isKeyDown(59)) {
            this.a.a(f2);
        }
        GL11.glPopMatrix();
        if (!this.h.y.y) {
            this.a.b(f2);
            this.e(f2);
        }
        if (this.h.y.f) {
            this.f(f2);
        }
    }

    public void b(float f2) {
        int n2;
        if (!Display.isActive()) {
            if (System.currentTimeMillis() - this.l > 500L) {
                this.h.g();
            }
        } else {
            this.l = System.currentTimeMillis();
        }
        if (this.h.L) {
            this.h.B.c();
            float f3 = this.h.y.c * 0.6f + 0.2f;
            float f4 = f3 * f3 * f3 * 8.0f;
            float f5 = (float)this.h.B.a * f4;
            float f6 = (float)this.h.B.b * f4;
            n2 = 1;
            if (this.h.y.d) {
                n2 = -1;
            }
            this.h.g.d(f5, f6 * (float)n2);
        }
        if (this.h.v) {
            return;
        }
        kl kl2 = new kl(this.h.c, this.h.d);
        int n3 = kl2.a();
        int n4 = kl2.b();
        int n5 = Mouse.getX() * n3 / this.h.c;
        n2 = n4 - Mouse.getY() * n4 / this.h.d - 1;
        if (this.h.e != null) {
            this.c(f2);
            if (!Keyboard.isKeyDown(59)) {
                this.h.u.a(f2, this.h.p != null, n5, n2);
            }
        } else {
            GL11.glViewport(0, 0, (int)this.h.c, (int)this.h.d);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClear(16640);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            this.b();
        }
        if (this.h.p != null) {
            GL11.glClear(256);
            this.h.p.a(n5, n2, f2);
        }
    }

    public void c(float f2) {
        this.a(f2);
        bq bq2 = this.h.g;
        f f3 = this.h.f;
        bz bz2 = this.h.h;
        double d2 = bq2.aV + (bq2.aw - bq2.aV) * (double)f2;
        double d3 = bq2.aW + (bq2.ax - bq2.aW) * (double)f2;
        double d4 = bq2.aX + (bq2.ay - bq2.aX) * (double)f2;
        for (int i2 = 0; i2 < 2; ++i2) {
            if (this.h.y.g) {
                if (i2 == 0) {
                    GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                } else {
                    GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                }
            }
            GL11.glViewport(0, 0, (int)this.h.c, (int)this.h.d);
            this.h(f2);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            this.a(f2, i2);
            nclass.a();
            if (this.h.y.e < 2) {
                this.a(-1);
                f3.a(f2);
            }
            GL11.glEnable(2912);
            this.a(1);
            ma ma2 = new ma();
            ma2.a(d2, d3, d4);
            this.h.f.a(ma2, f2);
            this.h.f.a(bq2, false);
            this.a(0);
            GL11.glEnable(2912);
            GL11.glBindTexture(3553, (int)this.h.n.a("/terrain.png"));
            lclass.a();
            f3.a(bq2, 0, (double)f2);
            lclass.b();
            f3.a(bq2.e(f2), ma2, f2);
            bz2.b(bq2, f2);
            lclass.a();
            this.a(0);
            bz2.a(bq2, f2);
            if (this.h.x != null && bq2.a(hb.f)) {
                GL11.glDisable(3008);
                f3.a(bq2, this.h.x, 0, bq2.e.a(), f2);
                f3.b(bq2, this.h.x, 0, bq2.e.a(), f2);
                GL11.glEnable(3008);
            }
            GL11.glBlendFunc(770, 771);
            this.a(0);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            GL11.glBindTexture(3553, (int)this.h.n.a("/terrain.png"));
            if (this.h.y.i) {
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                int n2 = f3.a(bq2, 1, (double)f2);
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                if (this.h.y.g) {
                    if (i2 == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                    }
                }
                if (n2 > 0) {
                    f3.a(1, (double)f2);
                }
            } else {
                f3.a(bq2, 1, (double)f2);
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if (this.h.x != null && !bq2.a(hb.f)) {
                GL11.glDisable(3008);
                f3.a(bq2, this.h.x, 0, bq2.e.a(), f2);
                f3.b(bq2, this.h.x, 0, bq2.e.a(), f2);
                GL11.glEnable(3008);
            }
            GL11.glDisable(2912);
            if (this.k != null) {
                // empty if block
            }
            this.a(0);
            GL11.glEnable(2912);
            f3.b(f2);
            GL11.glDisable(2912);
            this.a(1);
            GL11.glClear(256);
            this.b(f2, i2);
            if (this.h.y.g) continue;
            return;
        }
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
    }

    private void c() {
        if (!this.h.y.i) {
            return;
        }
        bq bq2 = this.h.g;
        Session cy2 = this.h.e;
        int n2 = TrigLookup.b(bq2.aw);
        int n3 = TrigLookup.b(bq2.ax);
        int n4 = TrigLookup.b(bq2.ay);
        int n5 = 16;
        for (int i2 = 0; i2 < 150; ++i2) {
            int n6 = n2 + this.m.nextInt(n5) - this.m.nextInt(n5);
            int n7 = n4 + this.m.nextInt(n5) - this.m.nextInt(n5);
            int n8 = cy2.f(n6, n7);
            int n9 = cy2.a(n6, n8 - 1, n7);
            if (n8 > n3 + n5 || n8 < n3 - n5) continue;
            float f2 = this.m.nextFloat();
            float f3 = this.m.nextFloat();
            if (n9 <= 0) continue;
            this.h.h.a(new pc(cy2, (float)n6 + f2, (double)((float)n8 + 0.1f) - Block.registry[n9].bl, (float)n7 + f3));
        }
    }

    public void b() {
        kl kl2 = new kl(this.h.c, this.h.d);
        int n2 = kl2.a();
        int n3 = kl2.b();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)n2, (double)n3, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }

    private void h(float f2) {
        Session cy2 = this.h.e;
        bq bq2 = this.h.g;
        float f3 = 1.0f / (float)(4 - this.h.y.e);
        f3 = 1.0f - (float)Math.pow(f3, 0.25);
        Vector3D ao2 = cy2.a((lw)this.h.g, f2);
        float f4 = (float)ao2.x;
        float f5 = (float)ao2.y;
        float f6 = (float)ao2.z;
        Vector3D ao3 = cy2.d(f2);
        this.e = (float)ao3.x;
        this.f = (float)ao3.y;
        this.g = (float)ao3.z;
        this.e += (f4 - this.e) * f3;
        this.f += (f5 - this.f) * f3;
        this.g += (f6 - this.g) * f3;
        if (bq2.a(hb.f)) {
            this.e = 0.02f;
            this.f = 0.02f;
            this.g = 0.2f;
        } else if (bq2.a(hb.g)) {
            this.e = 0.6f;
            this.f = 0.1f;
            this.g = 0.0f;
        }
        float f7 = this.n + (this.o - this.n) * f2;
        this.e *= f7;
        this.f *= f7;
        this.g *= f7;
        if (this.h.y.g) {
            float f8 = (this.e * 30.0f + this.f * 59.0f + this.g * 11.0f) / 100.0f;
            float f9 = (this.e * 30.0f + this.f * 70.0f) / 100.0f;
            float f10 = (this.e * 30.0f + this.g * 70.0f) / 100.0f;
            this.e = f8;
            this.f = f9;
            this.g = f10;
        }
        GL11.glClearColor((float)this.e, (float)this.f, (float)this.g, 0.0f);
    }

    private void a(int n2) {
        bq bq2 = this.h.g;
        GL11.glFog(2918, (FloatBuffer)this.a(this.e, this.f, this.g, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (bq2.a(hb.f)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1f);
            float f2 = 0.4f;
            float f3 = 0.4f;
            float f4 = 0.9f;
            if (this.h.y.g) {
                float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
                float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
                float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
                f2 = f5;
                f3 = f6;
                f4 = f7;
            }
        } else if (bq2.a(hb.g)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0f);
            float f8 = 0.4f;
            float f9 = 0.3f;
            float f10 = 0.3f;
            if (this.h.y.g) {
                float f11 = (f8 * 30.0f + f9 * 59.0f + f10 * 11.0f) / 100.0f;
                float f12 = (f8 * 30.0f + f9 * 70.0f) / 100.0f;
                float f13 = (f8 * 30.0f + f10 * 70.0f) / 100.0f;
                f8 = f11;
                f9 = f12;
                f10 = f13;
            }
        } else {
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, (float)(this.i * 0.25f));
            GL11.glFogf(2916, (float)this.i);
            if (n2 < 0) {
                GL11.glFogf(2915, 0.0f);
                GL11.glFogf(2916, (float)(this.i * 0.8f));
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            if (this.h.e.q.c) {
                GL11.glFogf(2915, 0.0f);
            }
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    private FloatBuffer a(float f2, float f3, float f4, float f5) {
        this.d.clear();
        this.d.put(f2).put(f3).put(f4).put(f5);
        this.d.flip();
        return this.d;
    }
}

