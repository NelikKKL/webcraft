/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ot
extends Mob {
    public ot(Session cy2) {
        super(cy2);
    }

    @Override
    protected void c(float f2) {
    }

    @Override
    public void b(float f2, float f3) {
        if (this.h_()) {
            this.a(f2, f3, 0.02f);
            this.d(this.az, this.aA, this.aB);
            this.az *= (double)0.8f;
            this.aA *= (double)0.8f;
            this.aB *= (double)0.8f;
        } else if (this.L()) {
            this.a(f2, f3, 0.02f);
            this.d(this.az, this.aA, this.aB);
            this.az *= 0.5;
            this.aA *= 0.5;
            this.aB *= 0.5;
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
            this.d(this.az, this.aA, this.aB);
            this.az *= (double)f4;
            this.aA *= (double)f4;
            this.aB *= (double)f4;
        }
        this.V = this.W;
        double d2 = this.aw - this.at;
        double d3 = this.ay - this.av;
        float f6 = TrigLookup.a(d2 * d2 + d3 * d3) * 4.0f;
        if (f6 > 1.0f) {
            f6 = 1.0f;
        }
        this.W += (f6 - this.W) * 0.4f;
        this.X += this.W;
    }

    @Override
    public boolean D() {
        return false;
    }
}

