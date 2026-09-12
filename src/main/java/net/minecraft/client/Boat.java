/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;

public class Boat
extends lw {
    public int a = 0;
    public int b = 0;
    public int c = 1;
    private int d;
    private double e;
    private double f;
    private double g;
    private double h;
    private double i;
    private double j;
    private double k;
    private double l;

    public Boat(Session cy2) {
        super(cy2);
        this.ap = true;
        this.a(1.5f, 0.6f);
        this.aO = this.aQ / 2.0f;
        this.aT = false;
    }

    @Override
    public co a(lw lw2) {
        return lw2.aG;
    }

    @Override
    public co e() {
        return this.aG;
    }

    @Override
    public boolean d_() {
        return true;
    }

    public Boat(Session cy2, double d2, double d3, double d4) {
        this(cy2);
        this.b(d2, d3 + (double)this.aO, d4);
        this.az = 0.0;
        this.aA = 0.0;
        this.aB = 0.0;
        this.at = d2;
        this.au = d3;
        this.av = d4;
    }

    @Override
    public double j() {
        return (double)this.aQ * 0.0 - (double)0.3f;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        if (this.as.z || this.aN) {
            return true;
        }
        this.c = -this.c;
        this.b = 10;
        this.a += n2 * 10;
        this.M();
        if (this.a > 40) {
            int n3;
            for (n3 = 0; n3 < 3; ++n3) {
                this.a(Block.x.bh, 1, 0.0f);
            }
            for (n3 = 0; n3 < 2; ++n3) {
                this.a(Item.B.id, 1, 0.0f);
            }
            this.J();
        }
        return true;
    }

    @Override
    public void g() {
        this.c = -this.c;
        this.b = 10;
        this.a += this.a * 10;
    }

    @Override
    public boolean n_() {
        return !this.aN;
    }

    @Override
    public void a(double d2, double d3, double d4, float f2, float f3, int n2) {
        this.e = d2;
        this.f = d3;
        this.g = d4;
        this.h = f2;
        this.i = f3;
        this.d = n2 + 4;
        this.az = this.j;
        this.aA = this.k;
        this.aB = this.l;
    }

    @Override
    public void a(double d2, double d3, double d4) {
        this.j = this.az = d2;
        this.k = this.aA = d3;
        this.l = this.aB = d4;
    }

    @Override
    public void e_() {
        double d2;
        double d3;
        double d4;
        super.e_();
        if (this.b > 0) {
            --this.b;
        }
        if (this.a > 0) {
            --this.a;
        }
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        int n2 = 5;
        double d5 = 0.0;
        for (int i2 = 0; i2 < n2; ++i2) {
            double d6 = this.aG.b + (this.aG.e - this.aG.b) * (double)(i2 + 0) / (double)n2 - 0.125;
            double d7 = this.aG.b + (this.aG.e - this.aG.b) * (double)(i2 + 1) / (double)n2 - 0.125;
            co co2 = co.b(this.aG.a, d6, this.aG.c, this.aG.d, d7, this.aG.f);
            if (!this.as.b(co2, hb.f)) continue;
            d5 += 1.0 / (double)n2;
        }
        if (this.as.z) {
            if (this.d > 0) {
                double d8;
                double d9 = this.aw + (this.e - this.aw) / (double)this.d;
                double d10 = this.ax + (this.f - this.ax) / (double)this.d;
                double d11 = this.ay + (this.g - this.ay) / (double)this.d;
                for (d8 = this.h - (double)this.aC; d8 < -180.0; d8 += 360.0) {
                }
                while (d8 >= 180.0) {
                    d8 -= 360.0;
                }
                this.aC = (float)((double)this.aC + d8 / (double)this.d);
                this.aD = (float)((double)this.aD + (this.i - (double)this.aD) / (double)this.d);
                --this.d;
                this.b(d9, d10, d11);
                this.c(this.aC, this.aD);
            } else {
                double d12 = this.aw + this.az;
                double d13 = this.ax + this.aA;
                double d14 = this.ay + this.aB;
                this.b(d12, d13, d14);
                if (this.aH) {
                    this.az *= 0.5;
                    this.aA *= 0.5;
                    this.aB *= 0.5;
                }
                this.az *= (double)0.99f;
                this.aA *= (double)0.95f;
                this.aB *= (double)0.99f;
            }
            return;
        }
        double d15 = d5 * 2.0 - 1.0;
        this.aA += (double)0.04f * d15;
        if (this.aq != null) {
            this.az += this.aq.az * 0.2;
            this.aB += this.aq.aB * 0.2;
        }
        if (this.az < -0.4) {
            this.az = -0.4;
        }
        if (this.az > 0.4) {
            this.az = 0.4;
        }
        if (this.aB < -0.4) {
            this.aB = -0.4;
        }
        if (this.aB > 0.4) {
            this.aB = 0.4;
        }
        if (this.aH) {
            this.az *= 0.5;
            this.aA *= 0.5;
            this.aB *= 0.5;
        }
        this.d(this.az, this.aA, this.aB);
        double d16 = Math.sqrt(this.az * this.az + this.aB * this.aB);
        if (d16 > 0.15) {
            double d17 = Math.cos((double)this.aC * Math.PI / 180.0);
            d3 = Math.sin((double)this.aC * Math.PI / 180.0);
            int n3 = 0;
            while ((double)n3 < 1.0 + d16 * 60.0) {
                double d18;
                double d19;
                double d20 = this.bd.nextFloat() * 2.0f - 1.0f;
                double d21 = (double)(this.bd.nextInt(2) * 2 - 1) * 0.7;
                if (this.bd.nextBoolean()) {
                    d19 = this.aw - d17 * d20 * 0.8 + d3 * d21;
                    d18 = this.ay - d3 * d20 * 0.8 - d17 * d21;
                    this.as.a("splash", d19, this.ax - 0.125, d18, this.az, this.aA, this.aB);
                } else {
                    d19 = this.aw + d17 + d3 * d20 * 0.7;
                    d18 = this.ay + d3 - d17 * d20 * 0.7;
                    this.as.a("splash", d19, this.ax - 0.125, d18, this.az, this.aA, this.aB);
                }
                ++n3;
            }
        }
        if (this.aI && d16 > 0.15) {
            if (!this.as.z) {
                int n4;
                this.J();
                for (n4 = 0; n4 < 3; ++n4) {
                    this.a(Block.x.bh, 1, 0.0f);
                }
                for (n4 = 0; n4 < 2; ++n4) {
                    this.a(Item.B.id, 1, 0.0f);
                }
            }
        } else {
            this.az *= (double)0.99f;
            this.aA *= (double)0.95f;
            this.aB *= (double)0.99f;
        }
        this.aD = 0.0f;
        double d22 = this.aC;
        d3 = this.at - this.aw;
        double d23 = this.av - this.ay;
        if (d3 * d3 + d23 * d23 > 0.001) {
            d22 = (float)(Math.atan2(d23, d3) * 180.0 / Math.PI);
        }
        for (d2 = d22 - (double)this.aC; d2 >= 180.0; d2 -= 360.0) {
        }
        while (d2 < -180.0) {
            d2 += 360.0;
        }
        if (d2 > 20.0) {
            d2 = 20.0;
        }
        if (d2 < -20.0) {
            d2 = -20.0;
        }
        this.aC = (float)((double)this.aC + d2);
        this.c(this.aC, this.aD);
        List list = this.as.b(this, this.aG.b(0.2f, 0.0, 0.2f));
        if (list != null && list.size() > 0) {
            for (int i3 = 0; i3 < list.size(); ++i3) {
                lw lw2 = (lw)list.get(i3);
                if (lw2 == this.aq || !lw2.d_() || !(lw2 instanceof Boat)) continue;
                lw2.g(this);
            }
        }
        if (this.aq != null && this.aq.aN) {
            this.aq = null;
        }
    }

    @Override
    public void j_() {
        if (this.aq == null) {
            return;
        }
        double d2 = Math.cos((double)this.aC * Math.PI / 180.0) * 0.4;
        double d3 = Math.sin((double)this.aC * Math.PI / 180.0) * 0.4;
        this.aq.b(this.aw + d2, this.ax + this.j() + this.aq.y(), this.ay + d3);
    }

    @Override
    protected void a(NBTCompoundTag iq2) {
    }

    @Override
    protected void b(NBTCompoundTag iq2) {
    }

    @Override
    public float i_() {
        return 0.0f;
    }

    @Override
    public boolean a(Player eb2) {
        if (this.aq != null && this.aq instanceof Player && this.aq != eb2) {
            return true;
        }
        if (!this.as.z) {
            eb2.h(this);
        }
        return true;
    }
}

