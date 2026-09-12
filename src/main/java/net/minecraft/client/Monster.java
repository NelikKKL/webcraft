/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Monster
extends BaseAI
implements cz {
    protected int f = 2;

    public Monster(Session cy2) {
        super(cy2);
        this.J = 20;
    }

    @Override
    public void k() {
        float f2 = this.a(1.0f);
        if (f2 > 0.5f) {
            this.ag += 2;
        }
        super.k();
    }

    @Override
    public void e_() {
        super.e_();
        if (this.as.k == 0) {
            this.J();
        }
    }

    @Override
    protected lw c_() {
        Player eb2 = this.as.a((lw)this, 16.0);
        if (eb2 != null && this.d(eb2)) {
            return eb2;
        }
        return null;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        if (super.a(lw2, n2)) {
            if (this.aq == lw2 || this.ar == lw2) {
                return true;
            }
            if (lw2 != this) {
                this.g = lw2;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void a(lw lw2, float f2) {
        if ((double)f2 < 2.5 && lw2.aG.e > this.aG.b && lw2.aG.b < this.aG.e) {
            this.P = 20;
            lw2.a(this, this.f);
        }
    }

    @Override
    protected float a(int n2, int n3, int n4) {
        return 0.5f - this.as.c(n2, n3, n4);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
    }

    @Override
    public boolean a() {
        int n2;
        int n3;
        int n4 = TrigLookup.b(this.aw);
        if (this.as.a(ch.Sky, n4, n3 = TrigLookup.b(this.aG.b), n2 = TrigLookup.b(this.ay)) > this.bd.nextInt(32)) {
            return false;
        }
        int n5 = this.as.j(n4, n3, n2);
        return n5 <= this.bd.nextInt(8) && super.a();
    }
}

