/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;

public class Snowball
extends lw {
    private int b = -1;
    private int c = -1;
    private int d = -1;
    private int e = 0;
    private boolean f = false;
    public int a = 0;
    private Mob g;
    private int h;
    private int i = 0;

    public Snowball(Session cy2) {
        super(cy2);
        this.a(0.25f, 0.25f);
    }

    @Override
    public boolean a(double d2) {
        double d3 = this.aG.b() * 4.0;
        return d2 < (d3 *= 64.0) * d3;
    }

    public Snowball(Session cy2, Mob hf2) {
        super(cy2);
        this.g = hf2;
        this.a(0.25f, 0.25f);
        this.c(hf2.aw, hf2.ax + (double)hf2.v(), hf2.ay, hf2.aC, hf2.aD);
        this.aw -= (double)(TrigLookup.b(this.aC / 180.0f * (float)Math.PI) * 0.16f);
        this.ax -= (double)0.1f;
        this.ay -= (double)(TrigLookup.a(this.aC / 180.0f * (float)Math.PI) * 0.16f);
        this.b(this.aw, this.ax, this.ay);
        this.aO = 0.0f;
        float f2 = 0.4f;
        this.az = -TrigLookup.a(this.aC / 180.0f * (float)Math.PI) * TrigLookup.b(this.aD / 180.0f * (float)Math.PI) * f2;
        this.aB = TrigLookup.b(this.aC / 180.0f * (float)Math.PI) * TrigLookup.b(this.aD / 180.0f * (float)Math.PI) * f2;
        this.aA = -TrigLookup.a(this.aD / 180.0f * (float)Math.PI) * f2;
        this.a(this.az, this.aA, this.aB, 1.5f, 1.0f);
    }

    public Snowball(Session cy2, double d2, double d3, double d4) {
        super(cy2);
        this.h = 0;
        this.a(0.25f, 0.25f);
        this.b(d2, d3, d4);
        this.aO = 0.0f;
    }

    public void a(double d2, double d3, double d4, float f2, float f3) {
        float f4 = TrigLookup.a(d2 * d2 + d3 * d3 + d4 * d4);
        d2 /= (double)f4;
        d3 /= (double)f4;
        d4 /= (double)f4;
        d2 += this.bd.nextGaussian() * (double)0.0075f * (double)f3;
        d3 += this.bd.nextGaussian() * (double)0.0075f * (double)f3;
        d4 += this.bd.nextGaussian() * (double)0.0075f * (double)f3;
        this.az = d2 *= (double)f2;
        this.aA = d3 *= (double)f2;
        this.aB = d4 *= (double)f2;
        float f5 = TrigLookup.a(d2 * d2 + d4 * d4);
        this.aE = this.aC = (float)(Math.atan2(d2, d4) * 180.0 / 3.1415927410125732);
        this.aF = this.aD = (float)(Math.atan2(d3, f5) * 180.0 / 3.1415927410125732);
        this.h = 0;
    }

    @Override
    public void a(double d2, double d3, double d4) {
        this.az = d2;
        this.aA = d3;
        this.aB = d4;
        if (this.aF == 0.0f && this.aE == 0.0f) {
            float f2 = TrigLookup.a(d2 * d2 + d4 * d4);
            this.aE = this.aC = (float)(Math.atan2(d2, d4) * 180.0 / 3.1415927410125732);
            this.aF = this.aD = (float)(Math.atan2(d3, f2) * 180.0 / 3.1415927410125732);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void e_() {
        block18: {
            this.aV = this.aw;
            this.aW = this.ax;
            this.aX = this.ay;
            super.e_();
            if (this.a > 0) {
                --this.a;
            }
            if (this.f) {
                int n2 = this.as.a(this.b, this.c, this.d);
                if (n2 != this.e) {
                    this.f = false;
                    this.az *= (double)(this.bd.nextFloat() * 0.2f);
                    this.aA *= (double)(this.bd.nextFloat() * 0.2f);
                    this.aB *= (double)(this.bd.nextFloat() * 0.2f);
                    this.h = 0;
                    this.i = 0;
                    break block18;
                } else {
                    ++this.h;
                    if (this.h == 1200) {
                        this.J();
                    }
                    return;
                }
            }
            ++this.i;
        }
        Vector3D ao2 = Vector3D.b(this.aw, this.ax, this.ay);
        Vector3D ao3 = Vector3D.b(this.aw + this.az, this.ax + this.aA, this.ay + this.aB);
        nx nx2 = this.as.a(ao2, ao3);
        ao2 = Vector3D.b(this.aw, this.ax, this.ay);
        ao3 = Vector3D.b(this.aw + this.az, this.ax + this.aA, this.ay + this.aB);
        if (nx2 != null) {
            ao3 = Vector3D.b(nx2.f.x, nx2.f.y, nx2.f.z);
        }
        if (!this.as.z) {
            lw lw2 = null;
            List list = this.as.b(this, this.aG.a(this.az, this.aA, this.aB).b(1.0, 1.0, 1.0));
            double d2 = 0.0;
            for (int i2 = 0; i2 < list.size(); ++i2) {
                double d3;
                float f2;
                co co2;
                nx nx3;
                lw lw3 = (lw)list.get(i2);
                if (!lw3.n_() || lw3 == this.g && this.i < 5 || (nx3 = (co2 = lw3.aG.b(0.3f, 0.3f, 0.3f)).a(ao2, ao3)) == null || !((d3 = ao2.c(nx3.f)) < d2) && d2 != 0.0) continue;
                lw2 = lw3;
                d2 = d3;
            }
            if (lw2 != null) {
                nx2 = new nx(lw2);
            }
        }
        if (nx2 != null) {
            if (nx2.g == null || nx2.g.a(this.g, 0)) {
                // empty if block
            }
            for (int i3 = 0; i3 < 8; ++i3) {
                this.as.a("snowballpoof", this.aw, this.ax, this.ay, 0.0, 0.0, 0.0);
            }
            this.J();
        }
        this.aw += this.az;
        this.ax += this.aA;
        this.ay += this.aB;
        float f3 = TrigLookup.a(this.az * this.az + this.aB * this.aB);
        this.aC = (float)(Math.atan2(this.az, this.aB) * 180.0 / 3.1415927410125732);
        this.aD = (float)(Math.atan2(this.aA, f3) * 180.0 / 3.1415927410125732);
        while (this.aD - this.aF < -180.0f) {
            this.aF -= 360.0f;
        }
        while (this.aD - this.aF >= 180.0f) {
            this.aF += 360.0f;
        }
        while (this.aC - this.aE < -180.0f) {
            this.aE -= 360.0f;
        }
        while (this.aC - this.aE >= 180.0f) {
            this.aE += 360.0f;
        }
        this.aD = this.aF + (this.aD - this.aF) * 0.2f;
        this.aC = this.aE + (this.aC - this.aE) * 0.2f;
        float f4 = 0.99f;
        float f5 = 0.03f;
        if (this.h_()) {
            for (int i4 = 0; i4 < 4; ++i4) {
                float f6 = 0.25f;
                this.as.a("bubble", this.aw - this.az * (double)f6, this.ax - this.aA * (double)f6, this.ay - this.aB * (double)f6, this.az, this.aA, this.aB);
            }
            f4 = 0.8f;
        }
        this.az *= (double)f4;
        this.aA *= (double)f4;
        this.aB *= (double)f4;
        this.aA -= (double)f5;
        this.b(this.aw, this.ax, this.ay);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        iq2.storeShort("xTile", (short)this.b);
        iq2.storeShort("yTile", (short)this.c);
        iq2.storeShort("zTile", (short)this.d);
        iq2.storeByte("inTile", (byte)this.e);
        iq2.storeByte("shake", (byte)this.a);
        iq2.storeByte("inGround", (byte)(this.f ? 1 : 0));
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        this.b = iq2.getShort("xTile");
        this.c = iq2.getShort("yTile");
        this.d = iq2.getShort("zTile");
        this.e = iq2.getByte("inTile") & 0xFF;
        this.a = iq2.getByte("shake") & 0xFF;
        this.f = iq2.getByte("inGround") == 1;
    }

    @Override
    public void b(Player eb2) {
        if (this.f && this.g == eb2 && this.a <= 0 && eb2.e.a(new InventoryItem(Item.j.id, 1))) {
            this.as.a(this, "random.pop", 0.2f, ((this.bd.nextFloat() - this.bd.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            eb2.b((lw)this, 1);
            this.J();
        }
    }

    @Override
    public float i_() {
        return 0.0f;
    }
}

