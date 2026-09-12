/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;

public class Minecart
extends lw
implements hi {
    private InventoryItem[] h = new InventoryItem[36];
    public int a = 0;
    public int b = 0;
    public int c = 1;
    private boolean i = false;
    public int d;
    public int e;
    public double f;
    public double g;
    static private final int[][][] j = new int[][][]{new int[][]{{0, 0, -1}, {0, 0, 1}}, new int[][]{{-1, 0, 0}, {1, 0, 0}}, new int[][]{{-1, -1, 0}, {1, 0, 0}}, new int[][]{{-1, 0, 0}, {1, -1, 0}}, new int[][]{{0, 0, -1}, {0, -1, 1}}, new int[][]{{0, -1, -1}, {0, 0, 1}}, new int[][]{{0, 0, 1}, {1, 0, 0}}, new int[][]{{0, 0, 1}, {-1, 0, 0}}, new int[][]{{0, 0, -1}, {-1, 0, 0}}, new int[][]{{0, 0, -1}, {1, 0, 0}}};
    private int k;
    private double l;
    private double m;
    private double n;
    private double o;
    private double p;
    private double q;
    private double r;
    private double s;

    public Minecart(Session cy2) {
        super(cy2);
        this.ap = true;
        this.a(0.98f, 0.7f);
        this.aO = this.aQ / 2.0f;
        this.aT = false;
    }

    @Override
    public co a(lw lw2) {
        return lw2.aG;
    }

    @Override
    public co e() {
        return null;
    }

    @Override
    public boolean d_() {
        return true;
    }

    public Minecart(Session cy2, double d2, double d3, double d4, int n2) {
        this(cy2);
        this.b(d2, d3 + (double)this.aO, d4);
        this.az = 0.0;
        this.aA = 0.0;
        this.aB = 0.0;
        this.at = d2;
        this.au = d3;
        this.av = d4;
        this.d = n2;
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
        this.M();
        this.a += n2 * 10;
        if (this.a > 40) {
            this.a(Item.ax.id, 1, 0.0f);
            if (this.d == 1) {
                this.a(Block.au.bh, 1, 0.0f);
            } else if (this.d == 2) {
                this.a(Block.aB.bh, 1, 0.0f);
            }
            this.J();
        }
        return true;
    }

    @Override
    public void g() {
        System.out.println("Animating hurt");
        this.c = -this.c;
        this.b = 10;
        this.a += this.a * 10;
    }

    @Override
    public boolean n_() {
        return !this.aN;
    }

    @Override
    public void J() {
        for (int i2 = 0; i2 < this.c(); ++i2) {
            InventoryItem fp2 = this.c(i2);
            if (fp2 == null) continue;
            float f2 = this.bd.nextFloat() * 0.8f + 0.1f;
            float f3 = this.bd.nextFloat() * 0.8f + 0.1f;
            float f4 = this.bd.nextFloat() * 0.8f + 0.1f;
            while (fp2.count > 0) {
                int n2 = this.bd.nextInt(21) + 10;
                if (n2 > fp2.count) {
                    n2 = fp2.count;
                }
                fp2.count -= n2;
                DroppedItem eo2 = new DroppedItem(this.as, this.aw + (double)f2, this.ax + (double)f3, this.ay + -0.0, new InventoryItem(fp2.id, n2, fp2.damage));
                float f5 = 0.05f;
                eo2.az = (float)this.bd.nextGaussian() * f5;
                eo2.aA = (float)this.bd.nextGaussian() * f5 + 0.2f;
                eo2.aB = (float)this.bd.nextGaussian() * f5;
                this.as.a(eo2);
            }
        }
        super.J();
    }

    @Override
    public void e_() {
        double d2;
        int n2;
        int n3;
        if (this.b > 0) {
            --this.b;
        }
        if (this.a > 0) {
            --this.a;
        }
        if (this.as.z && this.k > 0) {
            if (this.k > 0) {
                double d3;
                double d4 = this.aw + (this.l - this.aw) / (double)this.k;
                double d5 = this.ax + (this.m - this.ax) / (double)this.k;
                double d6 = this.ay + (this.n - this.ay) / (double)this.k;
                for (d3 = this.o - (double)this.aC; d3 < -180.0; d3 += 360.0) {
                }
                while (d3 >= 180.0) {
                    d3 -= 360.0;
                }
                this.aC = (float)((double)this.aC + d3 / (double)this.k);
                this.aD = (float)((double)this.aD + (this.p - (double)this.aD) / (double)this.k);
                --this.k;
                this.b(d4, d5, d6);
                this.c(this.aC, this.aD);
            } else {
                this.b(this.aw, this.ax, this.ay);
                this.c(this.aC, this.aD);
            }
            return;
        }
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        this.aA -= (double)0.04f;
        int n4 = TrigLookup.b(this.aw);
        if (this.as.a(n4, (n3 = TrigLookup.b(this.ax)) - 1, n2 = TrigLookup.b(this.ay)) == Block.aG.bh) {
            --n3;
        }
        double d7 = 0.4;
        boolean bl2 = false;
        double d8 = 0.0078125;
        if (this.as.a(n4, n3, n2) == Block.aG.bh) {
            double d9;
            double d10;
            double d11;
            double d12;
            Vector3D ao2 = this.h(this.aw, this.ax, this.ay);
            int n5 = this.as.e(n4, n3, n2);
            this.ax = n3;
            if (n5 >= 2 && n5 <= 5) {
                this.ax = n3 + 1;
            }
            if (n5 == 2) {
                this.az -= d8;
            }
            if (n5 == 3) {
                this.az += d8;
            }
            if (n5 == 4) {
                this.aB += d8;
            }
            if (n5 == 5) {
                this.aB -= d8;
            }
            int[][] nArray = j[n5];
            double d13 = nArray[1][0] - nArray[0][0];
            double d14 = nArray[1][2] - nArray[0][2];
            double d15 = Math.sqrt(d13 * d13 + d14 * d14);
            double d16 = this.az * d13 + this.aB * d14;
            if (d16 < 0.0) {
                d13 = -d13;
                d14 = -d14;
            }
            double d17 = Math.sqrt(this.az * this.az + this.aB * this.aB);
            this.az = d17 * d13 / d15;
            this.aB = d17 * d14 / d15;
            double d18 = 0.0;
            double d19 = (double)n4 + 0.5 + (double)nArray[0][0] * 0.5;
            double d20 = (double)n2 + 0.5 + (double)nArray[0][2] * 0.5;
            double d21 = (double)n4 + 0.5 + (double)nArray[1][0] * 0.5;
            double d22 = (double)n2 + 0.5 + (double)nArray[1][2] * 0.5;
            d13 = d21 - d19;
            d14 = d22 - d20;
            if (d13 == 0.0) {
                this.aw = (double)n4 + 0.5;
                d18 = this.ay - (double)n2;
            } else if (d14 == 0.0) {
                this.ay = (double)n2 + 0.5;
                d18 = this.aw - (double)n4;
            } else {
                d12 = this.aw - d19;
                d11 = this.ay - d20;
                d18 = d10 = (d12 * d13 + d11 * d14) * 2.0;
            }
            this.aw = d19 + d13 * d18;
            this.ay = d20 + d14 * d18;
            this.b(this.aw, this.ax + (double)this.aO, this.ay);
            d12 = this.az;
            d11 = this.aB;
            if (this.aq != null) {
                d12 *= 0.75;
                d11 *= 0.75;
            }
            if (d12 < -d7) {
                d12 = -d7;
            }
            if (d12 > d7) {
                d12 = d7;
            }
            if (d11 < -d7) {
                d11 = -d7;
            }
            if (d11 > d7) {
                d11 = d7;
            }
            this.d(d12, 0.0, d11);
            if (nArray[0][1] != 0 && TrigLookup.b(this.aw) - n4 == nArray[0][0] && TrigLookup.b(this.ay) - n2 == nArray[0][2]) {
                this.b(this.aw, this.ax + (double)nArray[0][1], this.ay);
            } else if (nArray[1][1] != 0 && TrigLookup.b(this.aw) - n4 == nArray[1][0] && TrigLookup.b(this.ay) - n2 == nArray[1][2]) {
                this.b(this.aw, this.ax + (double)nArray[1][1], this.ay);
            }
            if (this.aq != null) {
                this.az *= (double)0.997f;
                this.aA *= 0.0;
                this.aB *= (double)0.997f;
            } else {
                if (this.d == 2) {
                    d10 = TrigLookup.a(this.f * this.f + this.g * this.g);
                    if (d10 > 0.01) {
                        bl2 = true;
                        this.f /= d10;
                        this.g /= d10;
                        double d23 = 0.04;
                        this.az *= (double)0.8f;
                        this.aA *= 0.0;
                        this.aB *= (double)0.8f;
                        this.az += this.f * d23;
                        this.aB += this.g * d23;
                    } else {
                        this.az *= (double)0.9f;
                        this.aA *= 0.0;
                        this.aB *= (double)0.9f;
                    }
                }
                this.az *= (double)0.96f;
                this.aA *= 0.0;
                this.aB *= (double)0.96f;
            }
            Vector3D ao3 = this.h(this.aw, this.ax, this.ay);
            if (ao3 != null && ao2 != null) {
                double d24 = (ao2.y - ao3.y) * 0.05;
                d17 = Math.sqrt(this.az * this.az + this.aB * this.aB);
                if (d17 > 0.0) {
                    this.az = this.az / d17 * (d17 + d24);
                    this.aB = this.aB / d17 * (d17 + d24);
                }
                this.b(this.aw, ao3.y, this.ay);
            }
            int n6 = TrigLookup.b(this.aw);
            int n7 = TrigLookup.b(this.ay);
            if (n6 != n4 || n7 != n2) {
                d17 = Math.sqrt(this.az * this.az + this.aB * this.aB);
                this.az = d17 * (double)(n6 - n4);
                this.aB = d17 * (double)(n7 - n2);
            }
            if (this.d == 2 && (d9 = (double)TrigLookup.a(this.f * this.f + this.g * this.g)) > 0.01 && this.az * this.az + this.aB * this.aB > 0.001) {
                this.f /= d9;
                this.g /= d9;
                if (this.f * this.az + this.g * this.aB < 0.0) {
                    this.f = 0.0;
                    this.g = 0.0;
                } else {
                    this.f = this.az;
                    this.g = this.aB;
                }
            }
        } else {
            if (this.az < -d7) {
                this.az = -d7;
            }
            if (this.az > d7) {
                this.az = d7;
            }
            if (this.aB < -d7) {
                this.aB = -d7;
            }
            if (this.aB > d7) {
                this.aB = d7;
            }
            if (this.aH) {
                this.az *= 0.5;
                this.aA *= 0.5;
                this.aB *= 0.5;
            }
            this.d(this.az, this.aA, this.aB);
            if (!this.aH) {
                this.az *= (double)0.95f;
                this.aA *= (double)0.95f;
                this.aB *= (double)0.95f;
            }
        }
        this.aD = 0.0f;
        double d25 = this.at - this.aw;
        double d26 = this.av - this.ay;
        if (d25 * d25 + d26 * d26 > 0.001) {
            this.aC = (float)(Math.atan2(d26, d25) * 180.0 / Math.PI);
            if (this.i) {
                this.aC += 180.0f;
            }
        }
        for (d2 = (double)(this.aC - this.aE); d2 >= 180.0; d2 -= 360.0) {
        }
        while (d2 < -180.0) {
            d2 += 360.0;
        }
        if (d2 < -170.0 || d2 >= 170.0) {
            this.aC += 180.0f;
            this.i = !this.i;
        }
        this.c(this.aC, this.aD);
        List list = this.as.b(this, this.aG.b(0.2f, 0.0, 0.2f));
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); ++i2) {
                lw lw2 = (lw)list.get(i2);
                if (lw2 == this.aq || !lw2.d_() || !(lw2 instanceof Minecart)) continue;
                lw2.g(this);
            }
        }
        if (this.aq != null && this.aq.aN) {
            this.aq = null;
        }
        if (bl2 && this.bd.nextInt(4) == 0) {
            --this.e;
            if (this.e < 0) {
                this.g = 0.0;
                this.f = 0.0;
            }
            this.as.a("largesmoke", this.aw, this.ax + 0.8, this.ay, 0.0, 0.0, 0.0);
        }
    }

    public Vector3D a(double d2, double d3, double d4, double d5) {
        int n2;
        int n3;
        int n4 = TrigLookup.b(d2);
        if (this.as.a(n4, (n3 = TrigLookup.b(d3)) - 1, n2 = TrigLookup.b(d4)) == Block.aG.bh) {
            --n3;
        }
        if (this.as.a(n4, n3, n2) == Block.aG.bh) {
            int n5 = this.as.e(n4, n3, n2);
            d3 = n3;
            if (n5 >= 2 && n5 <= 5) {
                d3 = n3 + 1;
            }
            int[][] nArray = j[n5];
            double d6 = nArray[1][0] - nArray[0][0];
            double d7 = nArray[1][2] - nArray[0][2];
            double d8 = Math.sqrt(d6 * d6 + d7 * d7);
            if (nArray[0][1] != 0 && TrigLookup.b(d2 += (d6 /= d8) * d5) - n4 == nArray[0][0] && TrigLookup.b(d4 += (d7 /= d8) * d5) - n2 == nArray[0][2]) {
                d3 += (double)nArray[0][1];
            } else if (nArray[1][1] != 0 && TrigLookup.b(d2) - n4 == nArray[1][0] && TrigLookup.b(d4) - n2 == nArray[1][2]) {
                d3 += (double)nArray[1][1];
            }
            return this.h(d2, d3, d4);
        }
        return null;
    }

    public Vector3D h(double d2, double d3, double d4) {
        int n2;
        int n3;
        int n4 = TrigLookup.b(d2);
        if (this.as.a(n4, (n3 = TrigLookup.b(d3)) - 1, n2 = TrigLookup.b(d4)) == Block.aG.bh) {
            --n3;
        }
        if (this.as.a(n4, n3, n2) == Block.aG.bh) {
            int n5 = this.as.e(n4, n3, n2);
            d3 = n3;
            if (n5 >= 2 && n5 <= 5) {
                d3 = n3 + 1;
            }
            int[][] nArray = j[n5];
            double d5 = 0.0;
            double d6 = (double)n4 + 0.5 + (double)nArray[0][0] * 0.5;
            double d7 = (double)n3 + 0.5 + (double)nArray[0][1] * 0.5;
            double d8 = (double)n2 + 0.5 + (double)nArray[0][2] * 0.5;
            double d9 = (double)n4 + 0.5 + (double)nArray[1][0] * 0.5;
            double d10 = (double)n3 + 0.5 + (double)nArray[1][1] * 0.5;
            double d11 = (double)n2 + 0.5 + (double)nArray[1][2] * 0.5;
            double d12 = d9 - d6;
            double d13 = (d10 - d7) * 2.0;
            double d14 = d11 - d8;
            if (d12 == 0.0) {
                d2 = (double)n4 + 0.5;
                d5 = d4 - (double)n2;
            } else if (d14 == 0.0) {
                d4 = (double)n2 + 0.5;
                d5 = d2 - (double)n4;
            } else {
                double d15;
                double d16 = d2 - d6;
                double d17 = d4 - d8;
                d5 = d15 = (d16 * d12 + d17 * d14) * 2.0;
            }
            d2 = d6 + d12 * d5;
            d3 = d7 + d13 * d5;
            d4 = d8 + d14 * d5;
            if (d13 < 0.0) {
                d3 += 1.0;
            }
            if (d13 > 0.0) {
                d3 += 0.5;
            }
            return Vector3D.b(d2, d3, d4);
        }
        return null;
    }

    @Override
    protected void a(NBTCompoundTag iq2) {
        iq2.storeInt("Type", this.d);
        if (this.d == 2) {
            iq2.storeDouble("PushX", this.f);
            iq2.storeDouble("PushZ", this.g);
            iq2.storeShort("Fuel", (short)this.e);
        } else if (this.d == 1) {
            NBTListTag ly2 = new NBTListTag();
            for (int i2 = 0; i2 < this.h.length; ++i2) {
                if (this.h[i2] == null) continue;
                NBTCompoundTag iq3 = new NBTCompoundTag();
                iq3.storeByte("Slot", (byte)i2);
                this.h[i2].a(iq3);
                ly2.a(iq3);
            }
            iq2.storeTag("Items", ly2);
        }
    }

    @Override
    protected void b(NBTCompoundTag iq2) {
        this.d = iq2.getInt("Type");
        if (this.d == 2) {
            this.f = iq2.getDouble("PushX");
            this.g = iq2.getDouble("PushZ");
            this.e = iq2.getShort("Fuel");
        } else if (this.d == 1) {
            NBTListTag ly2 = iq2.getListTag("Items");
            this.h = new InventoryItem[this.c()];
            for (int i2 = 0; i2 < ly2.c(); ++i2) {
                NBTCompoundTag iq3 = (NBTCompoundTag)ly2.a(i2);
                int n2 = iq3.getByte("Slot") & 0xFF;
                if (n2 < 0 || n2 >= this.h.length) continue;
                this.h[n2] = new InventoryItem(iq3);
            }
        }
    }

    @Override
    public float i_() {
        return 0.0f;
    }

    @Override
    public void g(lw lw2) {
        double d2;
        double d3;
        double d4;
        if (this.as.z) {
            return;
        }
        if (lw2 == this.aq) {
            return;
        }
        if (lw2 instanceof Mob && !(lw2 instanceof Player) && this.d == 0 && this.az * this.az + this.aB * this.aB > 0.01 && this.aq == null && lw2.ar == null) {
            lw2.h(this);
        }
        if ((d4 = (d3 = lw2.aw - this.aw) * d3 + (d2 = lw2.ay - this.ay) * d2) >= (double)1.0E-4f) {
            d4 = TrigLookup.a(d4);
            d3 /= d4;
            d2 /= d4;
            double d5 = 1.0 / d4;
            if (d5 > 1.0) {
                d5 = 1.0;
            }
            d3 *= d5;
            d2 *= d5;
            d3 *= (double)0.1f;
            d2 *= (double)0.1f;
            d3 *= (double)(1.0f - this.bb);
            d2 *= (double)(1.0f - this.bb);
            d3 *= 0.5;
            d2 *= 0.5;
            if (lw2 instanceof Minecart) {
                double d6 = lw2.az + this.az;
                double d7 = lw2.aB + this.aB;
                if (((Minecart)lw2).d == 2 && this.d != 2) {
                    this.az *= (double)0.2f;
                    this.aB *= (double)0.2f;
                    this.g(lw2.az - d3, 0.0, lw2.aB - d2);
                    lw2.az *= (double)0.7f;
                    lw2.aB *= (double)0.7f;
                } else if (((Minecart)lw2).d != 2 && this.d == 2) {
                    lw2.az *= (double)0.2f;
                    lw2.aB *= (double)0.2f;
                    lw2.g(this.az + d3, 0.0, this.aB + d2);
                    this.az *= (double)0.7f;
                    this.aB *= (double)0.7f;
                } else {
                    this.az *= (double)0.2f;
                    this.aB *= (double)0.2f;
                    this.g((d6 /= 2.0) - d3, 0.0, (d7 /= 2.0) - d2);
                    lw2.az *= (double)0.2f;
                    lw2.aB *= (double)0.2f;
                    lw2.g(d6 + d3, 0.0, d7 + d2);
                }
            } else {
                this.g(-d3, 0.0, -d2);
                lw2.g(d3 / 4.0, 0.0, d2 / 4.0);
            }
        }
    }

    @Override
    public int c() {
        return 27;
    }

    @Override
    public InventoryItem c(int n2) {
        return this.h[n2];
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        if (this.h[n2] != null) {
            if (this.h[n2].count <= n3) {
                InventoryItem fp2 = this.h[n2];
                this.h[n2] = null;
                return fp2;
            }
            InventoryItem fp3 = this.h[n2].a(n3);
            if (this.h[n2].count == 0) {
                this.h[n2] = null;
            }
            return fp3;
        }
        return null;
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        this.h[n2] = fp2;
        if (fp2 != null && fp2.count > this.o_()) {
            fp2.count = this.o_();
        }
    }

    @Override
    public String d() {
        return "Minecart";
    }

    @Override
    public int o_() {
        return 64;
    }

    @Override
    public void h() {
    }

    @Override
    public boolean a(Player eb2) {
        if (this.d == 0) {
            if (this.aq != null && this.aq instanceof Player && this.aq != eb2) {
                return true;
            }
            if (!this.as.z) {
                eb2.h(this);
            }
        } else if (this.d == 1) {
            eb2.a((hi)this); // TODO: It wasn't originally casted to (hi), I guessed
        } else if (this.d == 2) {
            InventoryItem fp2 = eb2.e.a();
            if (fp2 != null && fp2.id == Item.k.id) {
                if (--fp2.count == 0) {
                    eb2.e.a(eb2.e.d, null);
                }
                this.e += 1200;
            }
            this.f = this.aw - eb2.aw;
            this.g = this.ay - eb2.ay;
        }
        return true;
    }

    @Override
    public void a(double d2, double d3, double d4, float f2, float f3, int n2) {
        this.l = d2;
        this.m = d3;
        this.n = d4;
        this.o = f2;
        this.p = f3;
        this.k = n2 + 2;
        this.az = this.q;
        this.aA = this.r;
        this.aB = this.s;
    }

    @Override
    public void a(double d2, double d3, double d4) {
        this.q = this.az = d2;
        this.r = this.aA = d3;
        this.s = this.aB = d4;
    }
}

