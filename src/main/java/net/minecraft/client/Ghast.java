/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Ghast
extends ot
implements cz {
    public int a = 0;
    public double b;
    public double c;
    public double d;
    private lw g = null;
    private int h = 0;
    public int e = 0;
    public int f = 0;

    public Ghast(Session cy2) {
        super(cy2);
        this.z = "/mob/ghast.png";
        this.a(4.0f, 4.0f);
        this.bm = true;
    }

    @Override
    protected void b_() {
        if (this.as.k == 0) {
            this.J();
        }
        this.e = this.f;
        double d2 = this.b - this.aw;
        double d3 = this.c - this.ax;
        double d4 = this.d - this.ay;
        double d5 = TrigLookup.a(d2 * d2 + d3 * d3 + d4 * d4);
        if (d5 < 1.0 || d5 > 60.0) {
            this.b = this.aw + (double)((this.bd.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.c = this.ax + (double)((this.bd.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.d = this.ay + (double)((this.bd.nextFloat() * 2.0f - 1.0f) * 16.0f);
        }
        if (this.a-- <= 0) {
            this.a += this.bd.nextInt(5) + 2;
            if (this.a(this.b, this.c, this.d, d5)) {
                this.az += d2 / d5 * 0.1;
                this.aA += d3 / d5 * 0.1;
                this.aB += d4 / d5 * 0.1;
            } else {
                this.b = this.aw;
                this.c = this.ax;
                this.d = this.ay;
            }
        }
        if (this.g != null && this.g.aN) {
            this.g = null;
        }
        if (this.g == null || this.h-- <= 0) {
            this.g = this.as.a((lw)this, 100.0);
            if (this.g != null) {
                this.h = 20;
            }
        }
        double d6 = 64.0;
        if (this.g != null && this.g.f(this) < d6 * d6) {
            double d7 = this.g.aw - this.aw;
            double d8 = this.g.aG.b + (double)(this.g.aQ / 2.0f) - (this.ax + (double)(this.aQ / 2.0f));
            double d9 = this.g.ay - this.ay;
            this.s = this.aC = -((float)Math.atan2(d7, d9)) * 180.0f / (float)Math.PI;
            if (this.d(this.g)) {
                if (this.f == 10) {
                    this.as.a(this, "mob.ghast.charge", this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
                }
                ++this.f;
                if (this.f == 20) {
                    this.as.a(this, "mob.ghast.fireball", this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
                    az az2 = new az(this.as, this, d7, d8, d9);
                    double d10 = 4.0;
                    Vector3D ao2 = this.f(1.0f);
                    az2.aw = this.aw + ao2.x * d10;
                    az2.ax = this.ax + (double)(this.aQ / 2.0f) + 0.5;
                    az2.ay = this.ay + ao2.z * d10;
                    this.as.a(az2);
                    this.f = -40;
                }
            } else if (this.f > 0) {
                --this.f;
            }
        } else {
            this.s = this.aC = -((float)Math.atan2(this.az, this.aB)) * 180.0f / (float)Math.PI;
            if (this.f > 0) {
                --this.f;
            }
        }
        this.z = this.f > 10 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
    }

    private boolean a(double d2, double d3, double d4, double d5) {
        double d6 = (this.b - this.aw) / d5;
        double d7 = (this.c - this.ax) / d5;
        double d8 = (this.d - this.ay) / d5;
        co co2 = this.aG.c();
        int n2 = 1;
        while ((double)n2 < d5) {
            co2.d(d6, d7, d8);
            if (this.as.a((lw)this, co2).size() > 0) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    protected String d() {
        return "mob.ghast.moan";
    }

    @Override
    protected String f_() {
        return "mob.ghast.scream";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.ghast.death";
    }

    @Override
    protected int g_() {
        return Item.K.id;
    }

    @Override
    protected float h() {
        return 10.0f;
    }

    @Override
    public boolean a() {
        return this.bd.nextInt(20) == 0 && super.a() && this.as.k > 0;
    }

    @Override
    public int i() {
        return 1;
    }
}

