/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;

public class Mob
extends lw {
    public int o = 20;
    public float p;
    public float q;
    public float r;
    public float s = 0.0f;
    public float t = 0.0f;
    protected float u;
    protected float v;
    protected float w;
    protected float x;
    protected boolean y = true;
    protected String z = "/mob/char.png";
    protected boolean A = true;
    protected float B = 0.0f;
    protected String C = null;
    protected float D = 1.0f;
    protected int E = 0;
    protected float F = 0.0f;
    public boolean G = false;
    public float H;
    public float I;
    public int J = 10;
    public int K;
    private int a;
    public int L;
    public int M;
    public float N = 0.0f;
    public int O = 0;
    public int P = 0;
    public float Q;
    public float R;
    protected boolean S = false;
    public int T = -1;
    public float U = (float)(Math.random() * (double)0.9f + (double)0.1f);
    public float V;
    public float W;
    public float X;
    protected int Y;
    protected double Z;
    protected double aa;
    protected double ab;
    protected double ac;
    protected double ad;
    float ae = 0.0f;
    protected int af = 0;
    protected int ag = 0;
    protected float ah;
    protected float ai;
    protected float aj;
    protected boolean ak = false;
    protected float al = 0.0f;
    protected float am = 0.7f;
    private lw b;
    private int c = 0;

    public Mob(Session cy2) {
        super(cy2);
        this.ap = true;
        this.r = (float)(Math.random() + 1.0) * 0.01f;
        this.b(this.aw, this.ax, this.ay);
        this.p = (float)Math.random() * 12398.0f;
        this.aC = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.q = 1.0f;
        this.aZ = 0.5f;
    }

    public boolean d(lw lw2) {
        return this.as.a(
                Vector3D.b(
                        this.aw,
                        this.ax + (double)this.v(),
                        this.ay
                ),
                Vector3D.b(
                        lw2.aw,
                        lw2.ax + (double)lw2.v(),
                        lw2.ay
                )
        ) == null;
    }

    @Override
    public String A() {
        return this.z;
    }

    @Override
    public boolean n_() {
        return !this.aN;
    }

    @Override
    public boolean d_() {
        return !this.aN;
    }

    @Override
    public float v() {
        return this.aQ * 0.85f;
    }

    public int b() {
        return 80;
    }

    @Override
    public void B() {
        this.H = this.I;
        super.B();
        if (this.bd.nextInt(1000) < this.a++) {
            this.a = -this.b();
            String string = this.d();
            if (string != null) {
                this.as.a(this, string, this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
            }
        }
        if (this.E() && this.O()) {
            this.a(null, 1);
        }
        if (this.bm || this.as.z) {
            this.bg = 0;
        }
        if (this.E() && this.a(hb.f)) {
            --this.bk;
            if (this.bk == -20) {
                this.bk = 0;
                for (int i2 = 0; i2 < 8; ++i2) {
                    float f2 = this.bd.nextFloat() - this.bd.nextFloat();
                    float f3 = this.bd.nextFloat() - this.bd.nextFloat();
                    float f4 = this.bd.nextFloat() - this.bd.nextFloat();
                    this.as.a("bubble", this.aw + (double)f2, this.ax + (double)f3, this.ay + (double)f4, this.az, this.aA, this.aB);
                }
                this.a(null, 2);
            }
            this.bg = 0;
        } else {
            this.bk = this.bh;
        }
        this.Q = this.R;
        if (this.P > 0) {
            --this.P;
        }
        if (this.L > 0) {
            --this.L;
        }
        if (this.bj > 0) {
            --this.bj;
        }
        if (this.J <= 0) {
            ++this.O;
            if (this.O > 20) {
                this.G();
                this.J();
                for (int i3 = 0; i3 < 20; ++i3) {
                    double d2 = this.bd.nextGaussian() * 0.02;
                    double d3 = this.bd.nextGaussian() * 0.02;
                    double d4 = this.bd.nextGaussian() * 0.02;
                    this.as.a("explode", this.aw + (double)(this.bd.nextFloat() * this.aP * 2.0f) - (double)this.aP, this.ax + (double)(this.bd.nextFloat() * this.aQ), this.ay + (double)(this.bd.nextFloat() * this.aP * 2.0f) - (double)this.aP, d2, d3, d4);
                }
            }
        }
        this.x = this.w;
        this.t = this.s;
        this.aE = this.aC;
        this.aF = this.aD;
    }

    public void C() {
        for (int i2 = 0; i2 < 20; ++i2) {
            double d2 = this.bd.nextGaussian() * 0.02;
            double d3 = this.bd.nextGaussian() * 0.02;
            double d4 = this.bd.nextGaussian() * 0.02;
            double d5 = 10.0;
            this.as.a("explode", this.aw + (double)(this.bd.nextFloat() * this.aP * 2.0f) - (double)this.aP - d2 * d5, this.ax + (double)(this.bd.nextFloat() * this.aQ) - d3 * d5, this.ay + (double)(this.bd.nextFloat() * this.aP * 2.0f) - (double)this.aP - d4 * d5, d2, d3, d4);
        }
    }

    @Override
    public void s() {
        super.s();
        this.u = this.v;
        this.v = 0.0f;
    }

    @Override
    public void a(double d2, double d3, double d4, float f2, float f3, int n2) {
        this.aO = 0.0f;
        this.Z = d2;
        this.aa = d3;
        this.ab = d4;
        this.ac = f2;
        this.ad = f3;
        this.Y = n2;
    }

    @Override
    public void e_() {
        boolean bl2;
        float f2;
        float f3;
        super.e_();
        this.k();
        double d2 = this.aw - this.at;
        double d3 = this.ay - this.av;
        float f4 = TrigLookup.a(d2 * d2 + d3 * d3);
        float f5 = this.s;
        float f6 = 0.0f;
        this.u = this.v;
        float f7 = 0.0f;
        if (!(f4 <= 0.05f)) {
            f7 = 1.0f;
            f6 = f4 * 3.0f;
            f5 = (float)Math.atan2(d3, d2) * 180.0f / (float)Math.PI - 90.0f;
        }
        if (this.I > 0.0f) {
            f5 = this.aC;
        }
        if (!this.aH) {
            f7 = 0.0f;
        }
        this.v += (f7 - this.v) * 0.3f;
        for (f3 = f5 - this.s; f3 < -180.0f; f3 += 360.0f) {
        }
        while (f3 >= 180.0f) {
            f3 -= 360.0f;
        }
        this.s += f3 * 0.3f;
        for (f2 = this.aC - this.s; f2 < -180.0f; f2 += 360.0f) {
        }
        while (f2 >= 180.0f) {
            f2 -= 360.0f;
        }
        boolean bl3 = bl2 = f2 < -90.0f || f2 >= 90.0f;
        if (f2 < -75.0f) {
            f2 = -75.0f;
        }
        if (f2 >= 75.0f) {
            f2 = 75.0f;
        }
        this.s = this.aC - f2;
        if (f2 * f2 > 2500.0f) {
            this.s += f2 * 0.2f;
        }
        if (bl2) {
            f6 *= -1.0f;
        }
        while (this.aC - this.aE < -180.0f) {
            this.aE -= 360.0f;
        }
        while (this.aC - this.aE >= 180.0f) {
            this.aE += 360.0f;
        }
        while (this.s - this.t < -180.0f) {
            this.t -= 360.0f;
        }
        while (this.s - this.t >= 180.0f) {
            this.t += 360.0f;
        }
        while (this.aD - this.aF < -180.0f) {
            this.aF -= 360.0f;
        }
        while (this.aD - this.aF >= 180.0f) {
            this.aF += 360.0f;
        }
        this.w += f6;
    }

    @Override
    protected void a(float f2, float f3) {
        super.a(f2, f3);
    }

    public void c(int n2) {
        if (this.J <= 0) {
            return;
        }
        this.J += n2;
        if (this.J > 20) {
            this.J = 20;
        }
        this.bj = this.o / 2;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        if (this.as.z) {
            return false;
        }
        this.ag = 0;
        if (this.J <= 0) {
            return false;
        }
        this.W = 1.5f;
        boolean bl2 = true;
        if ((float)this.bj > (float)this.o / 2.0f) {
            if (n2 <= this.af) {
                return false;
            }
            this.b(n2 - this.af);
            this.af = n2;
            bl2 = false;
        } else {
            this.af = n2;
            this.K = this.J;
            this.bj = this.o;
            this.b(n2);
            this.M = 10;
            this.L = 10;
        }
        this.N = 0.0f;
        if (bl2) {
            this.as.a((lw)this, 2);
            this.M();
            if (lw2 != null) {
                double d2 = lw2.aw - this.aw;
                double d3 = lw2.ay - this.ay;
                while (d2 * d2 + d3 * d3 < 1.0E-4) {
                    d2 = (Math.random() - Math.random()) * 0.01;
                    d3 = (Math.random() - Math.random()) * 0.01;
                }
                this.N = (float)(Math.atan2(d3, d2) * 180.0 / 3.1415927410125732) - this.aC;
                this.a(lw2, n2, d2, d3);
            } else {
                this.N = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.J <= 0) {
            if (bl2) {
                this.as.a(this, this.soundOnDeath(), this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
            }
            this.b(lw2);
        } else if (bl2) {
            this.as.a(this, this.f_(), this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
        }
        return true;
    }

    @Override
    public void g() {
        this.M = 10;
        this.L = 10;
        this.N = 0.0f;
    }

    protected void b(int n2) {
        this.J -= n2;
    }

    protected float h() {
        return 1.0f;
    }

    protected String d() {
        return null;
    }

    protected String f_() {
        return "random.hurt";
    }

    protected String soundOnDeath() {
        return "random.hurt";
    }

    public void a(lw lw2, int n2, double d2, double d3) {
        float f2 = TrigLookup.a(d2 * d2 + d3 * d3);
        float f3 = 0.4f;
        this.az /= 2.0;
        this.aA /= 2.0;
        this.aB /= 2.0;
        this.az -= d2 / (double)f2 * (double)f3;
        this.aA += (double)0.4f;
        this.aB -= d3 / (double)f2 * (double)f3;
        if (this.aA > (double)0.4f) {
            this.aA = 0.4f;
        }
    }

    public void b(lw lw2) {
        int n2;
        if (this.E > 0 && lw2 != null) {
            lw2.c(this, this.E);
        }
        this.S = true;
        if (!this.as.z && (n2 = this.g_()) > 0) {
            int n3 = this.bd.nextInt(3);
            for (int i2 = 0; i2 < n3; ++i2) {
                this.b(n2, 1);
            }
        }
        this.as.a((lw)this, 3);
    }

    protected int g_() {
        return 0;
    }

    @Override
    protected void c(float f2) {
        int n2 = (int)Math.ceil(f2 - 3.0f);
        if (n2 > 0) {
            this.a(null, n2);
            int n3 = this.as.a(TrigLookup.b(this.aw), TrigLookup.b(this.ax - (double)0.2f - (double)this.aO), TrigLookup.b(this.ay));
            if (n3 > 0) {
                SoundSteps bi2 = Block.registry[n3].bq;
                this.as.a(this, bi2.d(), bi2.b() * 0.5f, bi2.c() * 0.75f);
            }
        }
    }

    public void b(float f2, float f3) {
        if (this.h_()) {
            double d2 = this.ax;
            this.a(f2, f3, 0.02f);
            this.d(this.az, this.aA, this.aB);
            this.az *= (double)0.8f;
            this.aA *= (double)0.8f;
            this.aB *= (double)0.8f;
            this.aA -= 0.02;
            if (this.aI && this.c(this.az, this.aA + (double)0.6f - this.ax + d2, this.aB)) {
                this.aA = 0.3f;
            }
        } else if (this.L()) {
            double d3 = this.ax;
            this.a(f2, f3, 0.02f);
            this.d(this.az, this.aA, this.aB);
            this.az *= 0.5;
            this.aA *= 0.5;
            this.aB *= 0.5;
            this.aA -= 0.02;
            if (this.aI && this.c(this.az, this.aA + (double)0.6f - this.ax + d3, this.aB)) {
                this.aA = 0.3f;
            }
        } else {
            float f4 = 0.91f;
            if (this.aH) {
                f4 = 0.54600006f;
                int n2 = this.as.a(TrigLookup.b(this.aw), TrigLookup.b(this.aG.b) - 1, TrigLookup.b(this.ay));
                if (n2 > 0) {
                    f4 = Block.registry[n2].bt * 0.91f;
                }
            }
            float f5 = 0.16277136f / (f4 * f4 * f4);
            this.a(f2, f3, this.aH ? 0.1f * f5 : 0.02f);
            f4 = 0.91f;
            if (this.aH) {
                f4 = 0.54600006f;
                int n3 = this.as.a(TrigLookup.b(this.aw), TrigLookup.b(this.aG.b) - 1, TrigLookup.b(this.ay));
                if (n3 > 0) {
                    f4 = Block.registry[n3].bt * 0.91f;
                }
            }
            if (this.D()) {
                this.aU = 0.0f;
                if (this.aA < -0.15) {
                    this.aA = -0.15;
                }
            }
            this.d(this.az, this.aA, this.aB);
            if (this.aI && this.D()) {
                this.aA = 0.2;
            }
            this.aA -= 0.08;
            this.aA *= (double)0.98f;
            this.az *= (double)f4;
            this.aB *= (double)f4;
        }
        this.V = this.W;
        double d4 = this.aw - this.at;
        double d5 = this.ay - this.av;
        float f6 = TrigLookup.a(d4 * d4 + d5 * d5) * 4.0f;
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        this.W += (f6 - this.W) * 0.4f;
        this.X += this.W;
    }

    public boolean D() {
        int n2;
        int n3;
        int n4 = TrigLookup.b(this.aw);
        return this.as.a(n4, n3 = TrigLookup.b(this.aG.b), n2 = TrigLookup.b(this.ay)) == Block.aF.bh || this.as.a(n4, n3 + 1, n2) == Block.aF.bh;
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        iq2.storeShort("Health", (short)this.J);
        iq2.storeShort("HurtTime", (short)this.L);
        iq2.storeShort("DeathTime", (short)this.O);
        iq2.storeShort("AttackTime", (short)this.P);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        this.J = iq2.getShort("Health");
        if (!iq2.has("Health")) {
            this.J = 10;
        }
        this.L = iq2.getShort("HurtTime");
        this.O = iq2.getShort("DeathTime");
        this.P = iq2.getShort("AttackTime");
    }

    @Override
    public boolean E() {
        return !this.aN && this.J > 0;
    }

    public void k() {
        if (this.Y > 0) {
            double d2;
            double d3 = this.aw + (this.Z - this.aw) / (double)this.Y;
            double d4 = this.ax + (this.aa - this.ax) / (double)this.Y;
            double d5 = this.ay + (this.ab - this.ay) / (double)this.Y;
            for (d2 = this.ac - (double)this.aC; d2 < -180.0; d2 += 360.0) {
            }
            while (d2 >= 180.0) {
                d2 -= 360.0;
            }
            this.aC = (float)((double)this.aC + d2 / (double)this.Y);
            this.aD = (float)((double)this.aD + (this.ad - (double)this.aD) / (double)this.Y);
            --this.Y;
            this.b(d3, d4, d5);
            this.c(this.aC, this.aD);
        }
        if (this.J <= 0) {
            this.ak = false;
            this.ah = 0.0f;
            this.ai = 0.0f;
            this.aj = 0.0f;
        } else if (!this.G) {
            this.b_();
        }
        boolean bl2 = this.h_();
        boolean bl3 = this.L();
        if (this.ak) {
            if (bl2) {
                this.aA += (double)0.04f;
            } else if (bl3) {
                this.aA += (double)0.04f;
            } else if (this.aH) {
                this.F();
            }
        }
        this.ah *= 0.98f;
        this.ai *= 0.98f;
        this.aj *= 0.9f;
        this.b(this.ah, this.ai);
        List list = this.as.b(this, this.aG.b(0.2f, 0.0, 0.2f));
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); ++i2) {
                lw lw2 = (lw)list.get(i2);
                if (!lw2.d_()) continue;
                lw2.g(this);
            }
        }
    }

    protected void F() {
        this.aA = 0.42f;
    }

    protected void b_() {
        ++this.ag;
        Player eb2 = this.as.a((lw)this, -1.0);
        if (eb2 != null) {
            double d2 = eb2.aw - this.aw;
            double d3 = eb2.ax - this.ax;
            double d4 = eb2.ay - this.ay;
            double d5 = d2 * d2 + d3 * d3 + d4 * d4;
            if (d5 > 16384.0) {
                this.J();
            }
            if (this.ag > 600 && this.bd.nextInt(800) == 0) {
                if (d5 < 1024.0) {
                    this.ag = 0;
                } else {
                    this.J();
                }
            }
        }
        this.ah = 0.0f;
        this.ai = 0.0f;
        float f2 = 8.0f;
        if (this.bd.nextFloat() < 0.02f) {
            eb2 = this.as.a((lw)this, (double)f2);
            if (eb2 != null) {
                this.b = eb2;
                this.c = 10 + this.bd.nextInt(20);
            } else {
                this.aj = (this.bd.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.b != null) {
            this.b(this.b, 10.0f);
            if (this.c-- <= 0 || this.b.aN || this.b.f(this) > (double)(f2 * f2)) {
                this.b = null;
            }
        } else {
            if (this.bd.nextFloat() < 0.05f) {
                this.aj = (this.bd.nextFloat() - 0.5f) * 20.0f;
            }
            this.aC += this.aj;
            this.aD = this.al;
        }
        boolean bl2 = this.h_();
        boolean bl3 = this.L();
        if (bl2 || bl3) {
            this.ak = this.bd.nextFloat() < 0.8f;
        }
    }

    public void b(lw lw2, float f2) {
        double d2;
        double d3 = lw2.aw - this.aw;
        double d4 = lw2.ay - this.ay;
        if (lw2 instanceof Mob) {
            Mob hf2 = (Mob)lw2;
            d2 = hf2.ax + (double)hf2.v() - (this.ax + (double)this.v());
        } else {
            d2 = (lw2.aG.b + lw2.aG.e) / 2.0 - (this.ax + (double)this.v());
        }
        double d5 = TrigLookup.a(d3 * d3 + d4 * d4);
        float f3 = (float)(Math.atan2(d4, d3) * 180.0 / 3.1415927410125732) - 90.0f;
        float f4 = (float)(Math.atan2(d2, d5) * 180.0 / 3.1415927410125732);
        this.aD = -this.b(this.aD, f4, f2);
        this.aC = this.b(this.aC, f3, f2);
    }

    private float b(float f2, float f3, float f4) {
        float f5;
        for (f5 = f3 - f2; f5 < -180.0f; f5 += 360.0f) {
        }
        while (f5 >= 180.0f) {
            f5 -= 360.0f;
        }
        if (f5 > f4) {
            f5 = f4;
        }
        if (f5 < -f4) {
            f5 = -f4;
        }
        return f2 + f5;
    }

    public void G() {
    }

    public boolean a() {
        return this.as.a(this.aG) && this.as.a((lw)this, this.aG).size() == 0 && !this.as.b(this.aG);
    }

    @Override
    protected void H() {
        this.a(null, 4);
    }

    public float d(float f2) {
        float f3 = this.I - this.H;
        if (f3 < 0.0f) {
            f3 += 1.0f;
        }
        return this.H + f3 * f2;
    }

    public Vector3D e(float f2) {
        if (f2 == 1.0f) {
            return Vector3D.b(this.aw, this.ax, this.ay);
        }
        double d2 = this.at + (this.aw - this.at) * (double)f2;
        double d3 = this.au + (this.ax - this.au) * (double)f2;
        double d4 = this.av + (this.ay - this.av) * (double)f2;
        return Vector3D.b(d2, d3, d4);
    }

    @Override
    public Vector3D I() {
        return this.f(1.0f);
    }

    public Vector3D f(float f2) {
        if (f2 == 1.0f) {
            float f3 = TrigLookup.b(-this.aC * ((float)Math.PI / 180) - (float)Math.PI);
            float f4 = TrigLookup.a(-this.aC * ((float)Math.PI / 180) - (float)Math.PI);
            float f5 = -TrigLookup.b(-this.aD * ((float)Math.PI / 180));
            float f6 = TrigLookup.a(-this.aD * ((float)Math.PI / 180));
            return Vector3D.b(f4 * f5, f6, f3 * f5);
        }
        float f7 = this.aF + (this.aD - this.aF) * f2;
        float f8 = this.aE + (this.aC - this.aE) * f2;
        float f9 = TrigLookup.b(-f8 * ((float)Math.PI / 180) - (float)Math.PI);
        float f10 = TrigLookup.a(-f8 * ((float)Math.PI / 180) - (float)Math.PI);
        float f11 = -TrigLookup.b(-f7 * ((float)Math.PI / 180));
        float f12 = TrigLookup.a(-f7 * ((float)Math.PI / 180));
        return Vector3D.b(f10 * f11, f12, f9 * f11);
    }

    public nx a(double d2, float f2) {
        Vector3D ao2 = this.e(f2);
        Vector3D ao3 = this.f(f2);
        Vector3D ao4 = ao2.c(ao3.x * d2, ao3.y * d2, ao3.z * d2);
        return this.as.a(ao2, ao4);
    }

    public int i() {
        return 4;
    }

    public InventoryItem l() {
        return null;
    }

    @Override
    public void a(byte by2) {
        if (by2 == 2) {
            this.W = 1.5f;
            this.bj = this.o;
            this.M = 10;
            this.L = 10;
            this.N = 0.0f;
            this.as.a(this, this.f_(), this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
            this.a(null, 0);
        } else if (by2 == 3) {
            this.as.a(this, this.soundOnDeath(), this.h(), (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
            this.J = 0;
            this.b((lw)null);
        } else {
            super.a(by2);
        }
    }
}

