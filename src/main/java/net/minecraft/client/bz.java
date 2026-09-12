/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class bz {
    protected Session a;
    private List[] b = new List[4];
    private fu c;
    private Random d = new Random();

    public bz(Session cy2, fu fu2) {
        if (cy2 != null) {
            this.a = cy2;
        }
        this.c = fu2;
        for (int i2 = 0; i2 < 4; ++i2) {
            this.b[i2] = new ArrayList();
        }
    }

    public void a(pp pp2) {
        int n2 = pp2.l_();
        this.b[n2].add(pp2);
    }

    public void a() {
        for (int i2 = 0; i2 < 4; ++i2) {
            for (int i3 = 0; i3 < this.b[i2].size(); ++i3) {
                pp pp2 = (pp)this.b[i2].get(i3);
                pp2.e_();
                if (!pp2.aN) continue;
                this.b[i2].remove(i3--);
            }
        }
    }

    public void a(lw lw2, float f2) {
        float f3 = TrigLookup.b(lw2.aC * (float)Math.PI / 180.0f);
        float f4 = TrigLookup.a(lw2.aC * (float)Math.PI / 180.0f);
        float f5 = -f4 * TrigLookup.a(lw2.aD * (float)Math.PI / 180.0f);
        float f6 = f3 * TrigLookup.a(lw2.aD * (float)Math.PI / 180.0f);
        float f7 = TrigLookup.b(lw2.aD * (float)Math.PI / 180.0f);
        pp.l = lw2.aV + (lw2.aw - lw2.aV) * (double)f2;
        pp.m = lw2.aW + (lw2.ax - lw2.aW) * (double)f2;
        pp.n = lw2.aX + (lw2.ay - lw2.aX) * (double)f2;
        for (int i2 = 0; i2 < 3; ++i2) {
            if (this.b[i2].size() == 0) continue;
            int n2 = 0;
            if (i2 == 0) {
                n2 = this.c.a("/particles.png");
            }
            if (i2 == 1) {
                n2 = this.c.a("/terrain.png");
            }
            if (i2 == 2) {
                n2 = this.c.a("/gui/items.png");
            }
            GL11.glBindTexture(3553, (int)n2);
            is is2 = is.a;
            is2.b();
            for (int i3 = 0; i3 < this.b[i2].size(); ++i3) {
                pp pp2 = (pp)this.b[i2].get(i3);
                pp2.a(is2, f2, f3, f7, f4, f5, f6);
            }
            is2.a();
        }
    }

    public void b(lw lw2, float f2) {
        int n2 = 3;
        if (this.b[n2].size() == 0) {
            return;
        }
        is is2 = is.a;
        for (int i2 = 0; i2 < this.b[n2].size(); ++i2) {
            pp pp2 = (pp)this.b[n2].get(i2);
            pp2.a(is2, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
    }

    public void a(Session cy2) {
        this.a = cy2;
        for (int i2 = 0; i2 < 4; ++i2) {
            this.b[i2].clear();
        }
    }

    public void a(int n2, int n3, int n4) {
        int n5 = this.a.a(n2, n3, n4);
        if (n5 == 0) {
            return;
        }
        Block nq2 = Block.registry[n5];
        int n6 = 4;
        for (int i2 = 0; i2 < n6; ++i2) {
            for (int i3 = 0; i3 < n6; ++i3) {
                for (int i4 = 0; i4 < n6; ++i4) {
                    double d2 = (double)n2 + ((double)i2 + 0.5) / (double)n6;
                    double d3 = (double)n3 + ((double)i3 + 0.5) / (double)n6;
                    double d4 = (double)n4 + ((double)i4 + 0.5) / (double)n6;
                    this.a(new ki(this.a, d2, d3, d4, d2 - (double)n2 - 0.5, d3 - (double)n3 - 0.5, d4 - (double)n4 - 0.5, nq2).a(n2, n3, n4));
                }
            }
        }
    }

    public void a(int n2, int n3, int n4, int n5) {
        int n6 = this.a.a(n2, n3, n4);
        if (n6 == 0) {
            return;
        }
        Block nq2 = Block.registry[n6];
        float f2 = 0.1f;
        double d2 = (double)n2 + this.d.nextDouble() * (nq2.bn - nq2.bk - (double)(f2 * 2.0f)) + (double)f2 + nq2.bk;
        double d3 = (double)n3 + this.d.nextDouble() * (nq2.bo - nq2.bl - (double)(f2 * 2.0f)) + (double)f2 + nq2.bl;
        double d4 = (double)n4 + this.d.nextDouble() * (nq2.bp - nq2.bm - (double)(f2 * 2.0f)) + (double)f2 + nq2.bm;
        if (n5 == 0) {
            d3 = (double)n3 + nq2.bl - (double)f2;
        }
        if (n5 == 1) {
            d3 = (double)n3 + nq2.bo + (double)f2;
        }
        if (n5 == 2) {
            d4 = (double)n4 + nq2.bm - (double)f2;
        }
        if (n5 == 3) {
            d4 = (double)n4 + nq2.bp + (double)f2;
        }
        if (n5 == 4) {
            d2 = (double)n2 + nq2.bk - (double)f2;
        }
        if (n5 == 5) {
            d2 = (double)n2 + nq2.bn + (double)f2;
        }
        this.a(new ki(this.a, d2, d3, d4, 0.0, 0.0, 0.0, nq2).a(n2, n3, n4).b(0.2f).d(0.6f));
    }

    public String b() {
        return "" + (this.b[0].size() + this.b[1].size() + this.b[2].size());
    }
}

