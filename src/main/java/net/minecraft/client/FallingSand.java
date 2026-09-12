/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class FallingSand
extends lw {
    public int a;
    public int b = 0;

    public FallingSand(Session cy2) {
        super(cy2);
    }

    public FallingSand(Session cy2, float f2, float f3, float f4, int n2) {
        super(cy2);
        this.a = n2;
        this.ap = true;
        this.a(0.98f, 0.98f);
        this.aO = this.aQ / 2.0f;
        this.b(f2, f3, f4);
        this.az = 0.0;
        this.aA = 0.0;
        this.aB = 0.0;
        this.aT = false;
        this.at = f2;
        this.au = f3;
        this.av = f4;
    }

    @Override
    public boolean n_() {
        return !this.aN;
    }

    @Override
    public void e_() {
        if (this.a == 0) {
            this.J();
            return;
        }
        this.at = this.aw;
        this.au = this.ax;
        this.av = this.ay;
        ++this.b;
        this.aA -= (double)0.04f;
        this.d(this.az, this.aA, this.aB);
        this.az *= (double)0.98f;
        this.aA *= (double)0.98f;
        this.aB *= (double)0.98f;
        int n2 = TrigLookup.b(this.aw);
        int n3 = TrigLookup.b(this.ax);
        int n4 = TrigLookup.b(this.ay);
        if (this.as.a(n2, n3, n4) == this.a) {
            this.as.d(n2, n3, n4, 0);
        }
        if (this.aH) {
            this.az *= (double)0.7f;
            this.aB *= (double)0.7f;
            this.aA *= -0.5;
            this.J();
            if (!this.as.a(this.a, n2, n3, n4, true) || !this.as.d(n2, n3, n4, this.a)) {
                this.b(this.a, 1);
            }
        } else if (this.b > 100) {
            this.b(this.a, 1);
            this.J();
        }
    }

    @Override
    protected void a(NBTCompoundTag iq2) {
        iq2.storeByte("Tile", (byte)this.a);
    }

    @Override
    protected void b(NBTCompoundTag iq2) {
        this.a = iq2.getByte("Tile") & 0xFF;
    }

    @Override
    public float i_() {
        return 0.0f;
    }

    public Session k() {
        return this.as;
    }
}

