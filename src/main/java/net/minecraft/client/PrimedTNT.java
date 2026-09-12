/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class PrimedTNT
extends lw {
    public int a = 0;

    public PrimedTNT(Session cy2) {
        super(cy2);
        this.ap = true;
        this.a(0.98f, 0.98f);
        this.aO = this.aQ / 2.0f;
    }

    public PrimedTNT(Session cy2, double d2, double d3, double d4) {
        this(cy2);
        this.b(d2, d3, d4);
        float f2 = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.az = -TrigLookup.a(f2 * (float)Math.PI / 180.0f) * 0.02f;
        this.aA = 0.2f;
        this.aB = -TrigLookup.b(f2 * (float)Math.PI / 180.0f) * 0.02f;
        this.aT = false;
        this.a = 80;
        this.at = d2;
        this.au = d3;
        this.av = d4;
    }

    @Override
    public boolean n_() {
        return !this.aN;
    }

    @Override
    public void e_() {
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        this.aA -= (double)0.04f;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.98f;
        this.aA *= (double)0.98f;
        this.aB *= (double)0.98f;
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
            this.aA *= -0.5;
        }
        if (this.a-- <= 0) {
            this.J();
            this.k();
        } else {
            this.as.a("smoke", this.aw, this.ax + 0.5, this.ay, 0.0, 0.0, 0.0);
        }
    }

    private void k() {
        float f2 = 4.0f;
        this.as.a(null, this.aw, this.ax, this.ay, f2);
    }

    @Override
    protected void a(NBTCompoundTag iq2) {
        iq2.storeByte("Fuse", (byte)this.a);
    }

    @Override
    protected void b(NBTCompoundTag iq2) {
        this.a = iq2.getByte("Fuse");
    }

    @Override
    public float i_() {
        return 0.0f;
    }
}

