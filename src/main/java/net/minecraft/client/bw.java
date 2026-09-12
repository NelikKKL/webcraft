/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class bw {
    public Session a;
    private int C = -1;
    static private is D = is.a;
    public static int b = 0;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public boolean o = false;
    public boolean[] p = new boolean[2];
    public int q;
    public int r;
    public int s;
    public float t;
    public boolean u;
    public co v;
    public int w;
    public boolean x = true;
    public boolean y;
    public int z;
    public boolean A;
    private boolean E = false;
    public List B = new ArrayList();
    private List F;

    public bw(Session cy2, List list, int n2, int n3, int n4, int n5, int n6) {
        this.a = cy2;
        this.F = list;
        this.g = this.h = n5;
        this.f = this.h;
        this.t = TrigLookup.c(this.f * this.f + this.g * this.g + this.h * this.h) / 2.0f;
        this.C = n6;
        this.c = -999;
        this.a(n2, n3, n4);
        this.u = false;
    }

    public void a(int n2, int n3, int n4) {
        if (n2 == this.c && n3 == this.d && n4 == this.e) {
            return;
        }
        this.b();
        this.c = n2;
        this.d = n3;
        this.e = n4;
        this.q = n2 + this.f / 2;
        this.r = n3 + this.g / 2;
        this.s = n4 + this.h / 2;
        this.l = n2 & 0x3FF;
        this.m = n3;
        this.n = n4 & 0x3FF;
        this.i = n2 - this.l;
        this.j = n3 - this.m;
        this.k = n4 - this.n;
        float f2 = 2.0f;
        this.v = co.a((float)n2 - f2, (float)n3 - f2, (float)n4 - f2, (float)(n2 + this.f) + f2, (float)(n3 + this.g) + f2, (float)(n4 + this.h) + f2);
        GL11.glNewList((int)(this.C + 2), 4864);
        af.a(co.b((float)this.l - f2, (float)this.m - f2, (float)this.n - f2, (float)(this.l + this.f) + f2, (float)(this.m + this.g) + f2, (float)(this.n + this.h) + f2));
        GL11.glEndList();
        this.f();
    }

    private void g() {
        GL11.glTranslatef((float)this.l, (float)this.m, (float)this.n);
    }

    public void a() {
        if (!this.u) {
            return;
        }
        ++b;
        int n2 = this.c;
        int n3 = this.d;
        int n4 = this.e;
        int n5 = this.c + this.f;
        int n6 = this.d + this.g;
        int n7 = this.e + this.h;
        for (int i2 = 0; i2 < 2; ++i2) {
            this.p[i2] = true;
        }
        ha.a = false;
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.B);
        this.B.clear();
        int n8 = 1;
        cs cs2 = new cs(this.a, n2 - n8, n3 - n8, n4 - n8, n5 + n8, n6 + n8, n7 + n8);
        bk bk2 = new bk(cs2);
        for (int i3 = 0; i3 < 2; ++i3) {
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            for (int i4 = n3; i4 < n6; ++i4) {
                for (int i5 = n4; i5 < n7; ++i5) {
                    for (int i6 = n2; i6 < n5; ++i6) {
                        Block nq2;
                        int n9;
                        TileEntityRegistry ji2;
                        int n10 = cs2.a(i6, i4, i5);
                        if (n10 <= 0) continue;
                        if (!bl4) {
                            bl4 = true;
                            GL11.glNewList((int)(this.C + i3), 4864);
                            GL11.glPushMatrix();
                            this.g();
                            float f2 = 1.000001f;
                            GL11.glTranslatef((float)((float)(-this.h) / 2.0f), (float)((float)(-this.g) / 2.0f), (float)((float)(-this.h) / 2.0f));
                            GL11.glScalef((float)f2, (float)f2, (float)f2);
                            GL11.glTranslatef((float)((float)this.h / 2.0f), (float)((float)this.g / 2.0f), (float)((float)this.h / 2.0f));
                            D.b();
                            D.b((double)(-this.c), (double)(-this.d), (double)(-this.e));
                        }
                        if (i3 == 0 && Block.p[n10] && gz.a.a(ji2 = cs2.b(i6, i4, i5))) {
                            this.B.add(ji2);
                        }
                        if ((n9 = (nq2 = Block.registry[n10]).c()) != i3) {
                            bl2 = true;
                            continue;
                        }
                        if (n9 != i3) continue;
                        bl3 |= bk2.a(nq2, i6, i4, i5);
                    }
                }
            }
            if (bl4) {
                D.a();
                GL11.glPopMatrix();
                GL11.glEndList();
                D.b(0.0, 0.0, 0.0);
            } else {
                bl3 = false;
            }
            if (bl3) {
                this.p[i3] = false;
            }
            if (!bl2) break;
        }
        HashSet hashSet2 = new HashSet();
        hashSet2.addAll(this.B);
        hashSet2.removeAll(hashSet);
        this.F.addAll(hashSet2);
        hashSet.removeAll(this.B);
        this.F.removeAll(hashSet);
        this.A = ha.a;
        this.E = true;
    }

    public float a(lw lw2) {
        float f2 = (float)(lw2.aw - (double)this.q);
        float f3 = (float)(lw2.ax - (double)this.r);
        float f4 = (float)(lw2.ay - (double)this.s);
        return f2 * f2 + f3 * f3 + f4 * f4;
    }

    public void b() {
        for (int i2 = 0; i2 < 2; ++i2) {
            this.p[i2] = true;
        }
        this.o = false;
        this.E = false;
    }

    public void c() {
        this.b();
        this.a = null;
    }

    public int a(int n2) {
        if (!this.o) {
            return -1;
        }
        if (!this.p[n2]) {
            return this.C + n2;
        }
        return -1;
    }

    public void a(qf qf2) {
        this.o = qf2.a(this.v);
    }

    public void d() {
        GL11.glCallList((int)(this.C + 2));
    }

    public boolean e() {
        if (!this.E) {
            return false;
        }
        return this.p[0] && this.p[1];
    }

    public void f() {
        this.u = true;
    }
}

