/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;

public class mp
extends bq {
    private ib bx;
    private int by = 0;
    private double bz;
    private double bA;
    private double bB;
    private double bC;
    private float bD;
    private float bE;
    private Inventory bF = new Inventory(null);
    private boolean bG = false;
    private boolean bH = false;
    private int bI = 0;

    public mp(Minecraft minecraft, Session cy2, ea ea2, ib ib2) {
        super(minecraft, cy2, ea2, 0);
        this.bx = ib2;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        return false;
    }

    @Override
    public void c(int n2) {
    }

    @Override
    public void e_() {
        if (!this.as.d(TrigLookup.b(this.aw), 64, TrigLookup.b(this.ay))) {
            return;
        }
        super.e_();
        this.P();
    }

    @Override
    public void o() {
    }

    public void P() {
        boolean bl2;
        boolean bl3;
        if (this.by++ == 20) {
            this.Q();
            this.by = 0;
        }
        if ((bl3 = this.p()) != this.bH) {
            if (bl3) {
                this.bx.a((gk)new ii(this, 104));
            } else {
                this.bx.a((gk)new ii(this, 105));
            }
            this.bH = bl3;
        }
        double d2 = this.aw - this.bz;
        double d3 = this.aG.b - this.bA;
        double d4 = this.ax - this.bB;
        double d5 = this.ay - this.bC;
        double d6 = this.aC - this.bD;
        double d7 = this.aD - this.bE;
        boolean bl4 = d3 != 0.0 || d4 != 0.0 || d2 != 0.0 || d5 != 0.0;
        boolean bl5 = bl2 = d6 != 0.0 || d7 != 0.0;
        if (this.ar != null) {
            if (bl2) {
                this.bx.a((gk)new t(this.az, -999.0, -999.0, this.aB, this.aH));
            } else {
                this.bx.a((gk)new cr(this.az, -999.0, -999.0, this.aB, this.aC, this.aD, this.aH));
            }
            bl4 = false;
        } else if (bl4 && bl2) {
            this.bx.a((gk)new cr(this.aw, this.aG.b, this.ax, this.ay, this.aC, this.aD, this.aH));
            this.bI = 0;
        } else if (bl4) {
            this.bx.a((gk)new t(this.aw, this.aG.b, this.ax, this.ay, this.aH));
            this.bI = 0;
        } else if (bl2) {
            this.bx.a((gk)new nz(this.aC, this.aD, this.aH));
            this.bI = 0;
        } else {
            this.bx.a((gk)new fa(this.aH));
            this.bI = this.bG != this.aH || this.bI > 20 ? 0 : ++this.bI;
        }
        this.bG = this.aH;
        if (bl4) {
            this.bz = this.aw;
            this.bA = this.aG.b;
            this.bB = this.ax;
            this.bC = this.ay;
        }
        if (bl2) {
            this.bD = this.aC;
            this.bE = this.aD;
        }
    }

    private void Q() {
        if (!this.e.a(this.bF)) {
            this.bx.a((gk)new p(-1, this.e.storage));
            this.bx.a((gk)new p(-2, this.e.c));
            this.bx.a((gk)new p(-3, this.e.b));
            this.bF = this.e.i();
        }
    }

    @Override
    protected void a(DroppedItem eo2) {
        id id2 = new id(eo2);
        this.bx.a((gk)id2);
        eo2.aw = (double)id2.b / 32.0;
        eo2.ax = (double)id2.c / 32.0;
        eo2.ay = (double)id2.d / 32.0;
        eo2.az = (double)id2.e / 128.0;
        eo2.aA = (double)id2.f / 128.0;
        eo2.aB = (double)id2.g / 128.0;
    }

    @Override
    public void a(String string) {
        this.bx.a((gk)new jr(string));
    }

    @Override
    public void z() {
        super.z();
        this.bx.a((gk)new ii(this, 1));
    }

    @Override
    public void r() {
        this.Q();
        this.bx.a((gk)new jk());
    }

    @Override
    protected void b(int n2) {
        this.J -= n2;
    }
}

