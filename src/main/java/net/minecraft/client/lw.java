/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;
import java.util.Random;

public abstract class lw {
    static private int a = 0;
    public int an = a++;
    public double ao = 1.0;
    public boolean ap = false;
    public lw aq;
    public lw ar;
    public Session as;
    public double at;
    public double au;
    public double av;
    public double aw;
    public double ax;
    public double ay;
    public double az;
    public double aA;
    public double aB;
    public float aC;
    public float aD;
    public float aE;
    public float aF;
    public final co aG = co.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public boolean aH = false;
    public boolean aI;
    public boolean aJ;
    public boolean aK = false;
    public boolean aL = false;
    public boolean aM = true;
    public boolean aN = false;
    public float aO = 0.0f;
    public float aP = 0.6f;
    public float aQ = 1.8f;
    public float aR = 0.0f;
    public float aS = 0.0f;
    protected boolean aT = true;
    protected float aU = 0.0f;
    private int b = 1;
    public double aV;
    public double aW;
    public double aX;
    public float aY = 0.0f;
    public float aZ = 0.0f;
    public boolean ba = false;
    public float bb = 0.0f;
    public boolean bc = false;
    protected Random bd = new Random();
    public int be = 0;
    public int bf = 1;
    public int bg = 0;
    protected int bh = 300;
    protected boolean bi = false;
    public int bj = 0;
    public int bk = 300;
    private boolean c = true;
    public String bl;
    protected boolean bm = false;
    private double d;
    private double e;
    public boolean bn = false;
    public int bo;
    public int bp;
    public int bq;
    public int br;
    public int bs;
    public int bt;
    public boolean bu;
    public boolean bv;
    public boolean bw;

    public lw(Session cy2) {
        this.as = cy2;
        this.b(0.0, 0.0, 0.0);
    }

    public boolean equals(Object object) {
        if (object instanceof lw) {
            return ((lw)object).an == this.an;
        }
        return false;
    }

    public int hashCode() {
        return this.an;
    }

    protected void t() {
        if (this.as == null) {
            return;
        }
        while (this.ax > 0.0) {
            this.b(this.aw, this.ax, this.ay);
            if (this.as.a(this, this.aG).size() == 0) break;
            this.ax += 1.0;
        }
        this.aB = 0.0;
        this.aA = 0.0;
        this.az = 0.0;
        this.aD = 0.0f;
    }

    public void J() {
        this.aN = true;
    }

    protected void a(float f2, float f3) {
        this.aP = f2;
        this.aQ = f3;
    }

    protected void c(float f2, float f3) {
        this.aC = f2;
        this.aD = f3;
    }

    public void b(double d2, double d3, double d4) {
        this.aw = d2;
        this.ax = d3;
        this.ay = d4;
        float f2 = this.aP / 2.0f;
        float f3 = this.aQ;
        this.aG.c(d2 - (double)f2, d3 - (double)this.aO + (double)this.aY, d4 - (double)f2, d2 + (double)f2, d3 - (double)this.aO + (double)this.aY + (double)f3, d4 + (double)f2);
    }

    public void d(float f2, float f3) {
        float f4 = this.aD;
        float f5 = this.aC;
        this.aC = (float)((double)this.aC + (double)f2 * 0.15);
        this.aD = (float)((double)this.aD - (double)f3 * 0.15);
        if (this.aD < -90.0f) {
            this.aD = -90.0f;
        }
        if (this.aD > 90.0f) {
            this.aD = 90.0f;
        }
        this.aF += this.aD - f4;
        this.aE += this.aC - f5;
    }

    public void e_() {
        this.B();
    }

    public void B() {
        if (this.ar != null && this.ar.aN) {
            this.ar = null;
        }
        ++this.be;
        this.aR = this.aS;
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        this.aF = this.aD;
        this.aE = this.aC;
        if (this.h_()) {
            if (!this.bi && !this.c) {
                float f2;
                float f3;
                float f4 = TrigLookup.a(this.az * this.az * (double)0.2f + this.aA * this.aA + this.aB * this.aB * (double)0.2f) * 0.2f;
                if (f4 > 1.0f) {
                    f4 = 1.0f;
                }
                this.as.a(this, "random.splash", f4, 1.0f + (this.bd.nextFloat() - this.bd.nextFloat()) * 0.4f);
                float f5 = TrigLookup.b(this.aG.b);
                int n2 = 0;
                while ((float)n2 < 1.0f + this.aP * 20.0f) {
                    f3 = (this.bd.nextFloat() * 2.0f - 1.0f) * this.aP;
                    f2 = (this.bd.nextFloat() * 2.0f - 1.0f) * this.aP;
                    this.as.a("bubble", this.aw + (double)f3, f5 + 1.0f, this.ay + (double)f2, this.az, this.aA - (double)(this.bd.nextFloat() * 0.2f), this.aB);
                    ++n2;
                }
                n2 = 0;
                while ((float)n2 < 1.0f + this.aP * 20.0f) {
                    f3 = (this.bd.nextFloat() * 2.0f - 1.0f) * this.aP;
                    f2 = (this.bd.nextFloat() * 2.0f - 1.0f) * this.aP;
                    this.as.a("splash", this.aw + (double)f3, f5 + 1.0f, this.ay + (double)f2, this.az, this.aA, this.aB);
                    ++n2;
                }
            }
            this.aU = 0.0f;
            this.bi = true;
            this.bg = 0;
        } else {
            this.bi = false;
        }
        if (this.as.z) {
            this.bg = 0;
        } else if (this.bg > 0) {
            if (this.bm) {
                this.bg -= 4;
                if (this.bg < 0) {
                    this.bg = 0;
                }
            } else {
                if (this.bg % 20 == 0) {
                    this.a(null, 1);
                }
                --this.bg;
            }
        }
        if (this.L()) {
            this.K();
        }
        if (this.ax < -64.0) {
            this.H();
        }
        this.c = false;
    }

    protected void K() {
        if (!this.bm) {
            this.a(null, 4);
            this.bg = 600;
        }
    }

    protected void H() {
        this.J();
    }

    public boolean c(double d2, double d3, double d4) {
        co co2 = this.aG.c(d2, d3, d4);
        List list = this.as.a(this, co2);
        if (list.size() > 0) {
            return false;
        }
        return !this.as.b(co2);
    }

    public void d(double d2, double d3, double d4) {
        int n2;
        int n3;
        double d5;
        int n4;
        int n5;
        boolean bl2;
        if (this.ba) {
            this.aG.d(d2, d3, d4);
            this.aw = (this.aG.a + this.aG.d) / 2.0;
            this.ax = this.aG.b + (double)this.aO - (double)this.aY;
            this.ay = (this.aG.c + this.aG.f) / 2.0;
            return;
        }
        double d6 = this.aw;
        double d7 = this.ay;
        double d8 = d2;
        double d9 = d3;
        double d10 = d4;
        co co2 = this.aG.c();
        boolean bl3 = bl2 = this.aH && this.p();
        if (bl2) {
            double d11 = 0.05;
            while (d2 != 0.0 && this.as.a(this, this.aG.c(d2, -1.0, 0.0)).size() == 0) {
                d2 = d2 < d11 && d2 >= -d11 ? 0.0 : (d2 > 0.0 ? (d2 -= d11) : (d2 += d11));
                d8 = d2;
            }
            while (d4 != 0.0 && this.as.a(this, this.aG.c(0.0, -1.0, d4)).size() == 0) {
                d4 = d4 < d11 && d4 >= -d11 ? 0.0 : (d4 > 0.0 ? (d4 -= d11) : (d4 += d11));
                d10 = d4;
            }
        }
        List list = this.as.a(this, this.aG.a(d2, d3, d4));
        for (n5 = 0; n5 < list.size(); ++n5) {
            d3 = ((co)list.get(n5)).b(this.aG, d3);
        }
        this.aG.d(0.0, d3, 0.0);
        if (!this.aM && d9 != d3) {
            d4 = 0.0;
            d3 = 0.0;
            d2 = 0.0;
        }
        n5 = this.aH || d9 != d3 && d9 < 0.0 ? 1 : 0;
        for (n4 = 0; n4 < list.size(); ++n4) {
            d2 = ((co)list.get(n4)).a(this.aG, d2);
        }
        this.aG.d(d2, 0.0, 0.0);
        if (!this.aM && d8 != d2) {
            d4 = 0.0;
            d3 = 0.0;
            d2 = 0.0;
        }
        for (n4 = 0; n4 < list.size(); ++n4) {
            d4 = ((co)list.get(n4)).c(this.aG, d4);
        }
        this.aG.d(0.0, 0.0, d4);
        if (!this.aM && d10 != d4) {
            d4 = 0.0;
            d3 = 0.0;
            d2 = 0.0;
        }
        if (this.aZ > 0.0f && n5 != 0 && this.aY < 0.05f && (d8 != d2 || d10 != d4)) {
            double d12 = d2;
            d5 = d3;
            double d13 = d4;
            d2 = d8;
            d3 = this.aZ;
            d4 = d10;
            co co3 = this.aG.c();
            this.aG.b(co2);
            list = this.as.a(this, this.aG.a(d2, d3, d4));
            for (n3 = 0; n3 < list.size(); ++n3) {
                d3 = ((co)list.get(n3)).b(this.aG, d3);
            }
            this.aG.d(0.0, d3, 0.0);
            if (!this.aM && d9 != d3) {
                d4 = 0.0;
                d3 = 0.0;
                d2 = 0.0;
            }
            for (n3 = 0; n3 < list.size(); ++n3) {
                d2 = ((co)list.get(n3)).a(this.aG, d2);
            }
            this.aG.d(d2, 0.0, 0.0);
            if (!this.aM && d8 != d2) {
                d4 = 0.0;
                d3 = 0.0;
                d2 = 0.0;
            }
            for (n3 = 0; n3 < list.size(); ++n3) {
                d4 = ((co)list.get(n3)).c(this.aG, d4);
            }
            this.aG.d(0.0, 0.0, d4);
            if (!this.aM && d10 != d4) {
                d4 = 0.0;
                d3 = 0.0;
                d2 = 0.0;
            }
            if (d12 * d12 + d13 * d13 >= d2 * d2 + d4 * d4) {
                d2 = d12;
                d3 = d5;
                d4 = d13;
                this.aG.b(co3);
            } else {
                this.aY = (float)((double)this.aY + 0.5);
            }
        }
        this.aw = (this.aG.a + this.aG.d) / 2.0;
        this.ax = this.aG.b + (double)this.aO - (double)this.aY;
        this.ay = (this.aG.c + this.aG.f) / 2.0;
        this.aI = d8 != d2 || d10 != d4;
        this.aJ = d9 != d3;
        this.aH = d9 != d3 && d9 < 0.0;
        this.aK = this.aI || this.aJ;
        this.a(d3, this.aH);
        if (d8 != d2) {
            this.az = 0.0;
        }
        if (d9 != d3) {
            this.aA = 0.0;
        }
        if (d10 != d4) {
            this.aB = 0.0;
        }
        double d14 = this.aw - d6;
        d5 = this.ay - d7;
        if (this.aT && !bl2) {
            this.aS = (float)((double)this.aS + (double)TrigLookup.a(d14 * d14 + d5 * d5) * 0.6);
            int n6 = TrigLookup.b(this.aw);
            n2 = TrigLookup.b(this.ax - (double)0.2f - (double)this.aO);
            int n7 = TrigLookup.b(this.ay);
            n3 = this.as.a(n6, n2, n7);
            if (this.aS > (float)this.b && n3 > 0) {
                ++this.b;
                SoundSteps bi2 = Block.registry[n3].bq;
                if (this.as.a(n6, n2 + 1, n7) == Block.aS.bh) {
                    bi2 = Block.aS.bq;
                    this.as.a(this, bi2.d(), bi2.b() * 0.15f, bi2.c());
                } else if (!Block.registry[n3].bs.d()) {
                    this.as.a(this, bi2.d(), bi2.b() * 0.15f, bi2.c());
                }
                Block.registry[n3].b(this.as, n6, n2, n7, this);
            }
        }
        int n8 = TrigLookup.b(this.aG.a);
        n2 = TrigLookup.b(this.aG.b);
        int n9 = TrigLookup.b(this.aG.c);
        n3 = TrigLookup.b(this.aG.d);
        int n10 = TrigLookup.b(this.aG.e);
        int n11 = TrigLookup.b(this.aG.f);
        for (int i2 = n8; i2 <= n3; ++i2) {
            for (int i3 = n2; i3 <= n10; ++i3) {
                for (int i4 = n9; i4 <= n11; ++i4) {
                    int n12 = this.as.a(i2, i3, i4);
                    if (n12 <= 0) continue;
                    Block.registry[n12].a(this.as, i2, i3, i4, this);
                }
            }
        }
        this.aY *= 0.4f;
        n8 = this.h_() ? 1 : 0;
        if (this.as.c(this.aG)) {
            this.a(1);
            if (n8 == 0) {
                ++this.bg;
                if (this.bg == 0) {
                    this.bg = 300;
                }
            }
        } else if (this.bg <= 0) {
            this.bg = -this.bf;
        }
        if (n8 != 0 && this.bg > 0) {
            this.as.a(this, "random.fizz", 0.7f, 1.6f + (this.bd.nextFloat() - this.bd.nextFloat()) * 0.4f);
            this.bg = -this.bf;
        }
    }

    protected void a(double d2, boolean bl2) {
        if (bl2) {
            if (this.aU > 0.0f) {
                this.c(this.aU);
                this.aU = 0.0f;
            }
        } else if (d2 < 0.0) {
            this.aU = (float)((double)this.aU - d2);
        }
    }

    public boolean p() {
        return false;
    }

    public co e() {
        return null;
    }

    protected void a(int n2) {
        if (!this.bm) {
            this.a(null, n2);
        }
    }

    protected void c(float f2) {
    }

    public boolean h_() {
        return this.as.a(this.aG.b(0.0, -0.4f, 0.0), hb.f, this);
    }

    public boolean a(hb hb2) {
        int n2;
        int n3;
        double d2 = this.ax + (double)this.v();
        int n4 = TrigLookup.b(this.aw);
        int n5 = this.as.a(n4, n3 = TrigLookup.d(TrigLookup.b(d2)), n2 = TrigLookup.b(this.ay));
        if (n5 != 0 && Block.registry[n5].bs == hb2) {
            float f2 = ld.b(this.as.e(n4, n3, n2)) - 0.11111111f;
            float f3 = (float)(n3 + 1) - f2;
            return d2 < (double)f3;
        }
        return false;
    }

    public float v() {
        return 0.0f;
    }

    public boolean L() {
        return this.as.a(this.aG.b(0.0, -0.4f, 0.0), hb.g);
    }

    public void a(float f2, float f3, float f4) {
        float f5 = TrigLookup.c(f2 * f2 + f3 * f3);
        if (f5 < 0.01f) {
            return;
        }
        if (f5 < 1.0f) {
            f5 = 1.0f;
        }
        f5 = f4 / f5;
        float f6 = TrigLookup.a(this.aC * (float)Math.PI / 180.0f);
        float f7 = TrigLookup.b(this.aC * (float)Math.PI / 180.0f);
        this.az += (double)((f2 *= f5) * f7 - (f3 *= f5) * f6);
        this.aB += (double)(f3 * f7 + f2 * f6);
    }

    public float a(float f2) {
        int n2 = TrigLookup.b(this.aw);
        double d2 = (this.aG.e - this.aG.b) * 0.66;
        int n3 = TrigLookup.b(this.ax - (double)this.aO + d2);
        int n4 = TrigLookup.b(this.ay);
        return this.as.c(n2, n3, n4);
    }

    public void a(Session cy2) {
        this.as = cy2;
    }

    public void b(double d2, double d3, double d4, float f2, float f3) {
        this.at = this.aw = d2;
        this.au = this.ax = d3;
        this.av = this.ay = d4;
        this.aE = this.aC = f2;
        this.aF = this.aD = f3;
        this.aY = 0.0f;
        double d5 = this.aE - f2;
        if (d5 < -180.0) {
            this.aE += 360.0f;
        }
        if (d5 >= 180.0) {
            this.aE -= 360.0f;
        }
        this.b(this.aw, this.ax, this.ay);
        this.c(f2, f3);
    }

    public void c(double d2, double d3, double d4, float f2, float f3) {
        this.at = this.aw = d2;
        this.au = this.ax = d3 + (double)this.aO;
        this.av = this.ay = d4;
        this.aC = f2;
        this.aD = f3;
        this.b(this.aw, this.ax, this.ay);
    }

    public float e(lw lw2) {
        float f2 = (float)(this.aw - lw2.aw);
        float f3 = (float)(this.ax - lw2.ax);
        float f4 = (float)(this.ay - lw2.ay);
        return TrigLookup.c(f2 * f2 + f3 * f3 + f4 * f4);
    }

    public double e(double d2, double d3, double d4) {
        double d5 = this.aw - d2;
        double d6 = this.ax - d3;
        double d7 = this.ay - d4;
        return d5 * d5 + d6 * d6 + d7 * d7;
    }

    public double f(double d2, double d3, double d4) {
        double d5 = this.aw - d2;
        double d6 = this.ax - d3;
        double d7 = this.ay - d4;
        return TrigLookup.a(d5 * d5 + d6 * d6 + d7 * d7);
    }

    public double f(lw lw2) {
        double d2 = this.aw - lw2.aw;
        double d3 = this.ax - lw2.ax;
        double d4 = this.ay - lw2.ay;
        return d2 * d2 + d3 * d3 + d4 * d4;
    }

    public void b(Player eb2) {
    }

    public void g(lw lw2) {
        if (lw2.aq == this || lw2.ar == this) {
            return;
        }
        double d2 = lw2.aw - this.aw;
        double d3 = lw2.ay - this.ay;
        double d4 = TrigLookup.a(d2, d3);
        if (d4 >= (double)0.01f) {
            d4 = TrigLookup.a(d4);
            d2 /= d4;
            d3 /= d4;
            double d5 = 1.0 / d4;
            if (d5 > 1.0) {
                d5 = 1.0;
            }
            d2 *= d5;
            d3 *= d5;
            d2 *= (double)0.05f;
            d3 *= (double)0.05f;
            this.g(-(d2 *= (double)(1.0f - this.bb)), 0.0, -(d3 *= (double)(1.0f - this.bb)));
            lw2.g(d2, 0.0, d3);
        }
    }

    public void g(double d2, double d3, double d4) {
        this.az += d2;
        this.aA += d3;
        this.aB += d4;
    }

    protected void M() {
        this.aL = true;
    }

    public boolean a(lw lw2, int n2) {
        this.M();
        return false;
    }

    public boolean n_() {
        return false;
    }

    public boolean d_() {
        return false;
    }

    public void c(lw lw2, int n2) {
    }

    public boolean a(Vector3D ao2) {
        double d2 = this.aw - ao2.x;
        double d3 = this.ax - ao2.y;
        double d4 = this.ay - ao2.z;
        double d5 = d2 * d2 + d3 * d3 + d4 * d4;
        return this.a(d5);
    }

    public boolean a(double d2) {
        double d3 = this.aG.b();
        return d2 < (d3 *= 64.0 * this.ao) * d3;
    }

    public String A() {
        return null;
    }

    public boolean c(NBTCompoundTag iq2) {
        String string = this.N();
        if (this.aN || string == null) {
            return false;
        }
        iq2.storeString("id", string);
        this.d(iq2);
        return true;
    }

    public void d(NBTCompoundTag iq2) {
        iq2.storeTag("Pos", this.a(new double[]{this.aw, this.ax, this.ay}));
        iq2.storeTag("Motion", this.a(new double[]{this.az, this.aA, this.aB}));
        iq2.storeTag("Rotation", this.a(new float[]{this.aC, this.aD}));
        iq2.storeFloat("FallDistance", this.aU);
        iq2.storeShort("Fire", (short)this.bg);
        iq2.storeShort("Air", (short)this.bk);
        iq2.storeBoolean("OnGround", this.aH);
        this.a(iq2);
    }

    public void e(NBTCompoundTag iq2) {
        NBTListTag ly2 = iq2.getListTag("Pos");
        NBTListTag ly3 = iq2.getListTag("Motion");
        NBTListTag ly4 = iq2.getListTag("Rotation");
        this.b(0.0, 0.0, 0.0);
        this.az = ((NBTDoubleTag)ly3.a(0)).a;
        this.aA = ((NBTDoubleTag)ly3.a(1)).a;
        this.aB = ((NBTDoubleTag)ly3.a(2)).a;
        this.aV = this.aw = ((NBTDoubleTag)ly2.a(0)).a;
        this.at = this.aw;
        this.aW = this.ax = ((NBTDoubleTag)ly2.a(1)).a;
        this.au = this.ax;
        this.aX = this.ay = ((NBTDoubleTag)ly2.a(2)).a;
        this.av = this.ay;
        this.aE = this.aC = ((NBTFloatTag)ly4.a(0)).a;
        this.aF = this.aD = ((NBTFloatTag)ly4.a(1)).a;
        this.aU = iq2.getFloat("FallDistance");
        this.bg = iq2.getShort("Fire");
        this.bk = iq2.getShort("Air");
        this.aH = iq2.isTrue("OnGround");
        this.b(this.aw, this.ax, this.ay);
        this.b(iq2);
    }

    protected final String N() {
        return EntityRegistry.b(this);
    }

    protected abstract void b(NBTCompoundTag var1);

    protected abstract void a(NBTCompoundTag var1);

    protected NBTListTag a(double ... dArray) {
        NBTListTag ly2 = new NBTListTag();
        for (double d2 : dArray) {
            ly2.a(new NBTDoubleTag(d2));
        }
        return ly2;
    }

    protected NBTListTag a(float ... fArray) {
        NBTListTag ly2 = new NBTListTag();
        for (float f2 : fArray) {
            ly2.a(new NBTFloatTag(f2));
        }
        return ly2;
    }

    public float i_() {
        return this.aQ / 2.0f;
    }

    public DroppedItem b(int n2, int n3) {
        return this.a(n2, n3, 0.0f);
    }

    public DroppedItem a(int n2, int n3, float f2) {
        DroppedItem eo2 = new DroppedItem(this.as, this.aw, this.ax + (double)f2, this.ay, new InventoryItem(n2, n3));
        eo2.c = 10;
        this.as.a(eo2);
        return eo2;
    }

    public boolean E() {
        return !this.aN;
    }

    public boolean O() {
        int n2 = TrigLookup.b(this.aw);
        int n3 = TrigLookup.b(this.ax + (double)this.v());
        int n4 = TrigLookup.b(this.ay);
        return this.as.g(n2, n3, n4);
    }

    public boolean a(Player eb2) {
        return false;
    }

    public co a(lw lw2) {
        return null;
    }

    public void s() {
        if (this.ar.aN) {
            this.ar = null;
            return;
        }
        this.az = 0.0;
        this.aA = 0.0;
        this.aB = 0.0;
        this.e_();
        this.ar.j_();
        this.e += (double)(this.ar.aC - this.ar.aE);
        this.d += (double)(this.ar.aD - this.ar.aF);
        while (this.e >= 180.0) {
            this.e -= 360.0;
        }
        while (this.e < -180.0) {
            this.e += 360.0;
        }
        while (this.d >= 180.0) {
            this.d -= 360.0;
        }
        while (this.d < -180.0) {
            this.d += 360.0;
        }
        double d2 = this.e * 0.5;
        double d3 = this.d * 0.5;
        float f2 = 10.0f;
        if (d2 > (double)f2) {
            d2 = f2;
        }
        if (d2 < (double)(-f2)) {
            d2 = -f2;
        }
        if (d3 > (double)f2) {
            d3 = f2;
        }
        if (d3 < (double)(-f2)) {
            d3 = -f2;
        }
        this.e -= d2;
        this.d -= d3;
        this.aC = (float)((double)this.aC + d2);
        this.aD = (float)((double)this.aD + d3);
    }

    public void j_() {
        this.aq.b(this.aw, this.ax + this.j() + this.aq.y(), this.ay);
    }

    public double y() {
        return this.aO;
    }

    public double j() {
        return (double)this.aQ * 0.75;
    }

    public void h(lw lw2) {
        this.d = 0.0;
        this.e = 0.0;
        if (lw2 == null) {
            if (this.ar != null) {
                this.c(this.ar.aw, this.ar.aG.b + (double)this.ar.aQ, this.ar.ay, this.aC, this.aD);
                this.ar.aq = null;
            }
            this.ar = null;
            return;
        }
        if (this.ar == lw2) {
            this.ar.aq = null;
            this.ar = null;
            this.c(lw2.aw, lw2.aG.b + (double)lw2.aQ, lw2.ay, this.aC, this.aD);
            return;
        }
        if (this.ar != null) {
            this.ar.aq = null;
        }
        if (lw2.aq != null) {
            lw2.aq.ar = null;
        }
        this.ar = lw2;
        lw2.aq = this;
    }

    public void a(double d2, double d3, double d4, float f2, float f3, int n2) {
        this.b(d2, d3, d4);
        this.c(f2, f3);
    }

    public float k_() {
        return 0.1f;
    }

    public Vector3D I() {
        return null;
    }

    public void q() {
    }

    public void a(double d2, double d3, double d4) {
        this.az = d2;
        this.aA = d3;
        this.aB = d4;
    }

    public void a(byte by2) {
    }

    public void g() {
    }
}

