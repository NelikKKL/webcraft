/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class BaseAI
extends Mob {
    private bt a;
    protected lw g;
    protected boolean h = false;

    public BaseAI(Session cy2) {
        super(cy2);
    }

    @Override
    protected void b_() {
        int n2;
        int n3;
        this.h = false;
        float f2 = 16.0f;
        if (this.g == null) {
            this.g = this.c_();
            if (this.g != null) {
                this.a = this.as.a(this, this.g, f2);
            }
        } else if (!this.g.E()) {
            this.g = null;
        } else {
            float f3 = this.g.e(this);
            if (this.d(this.g)) {
                this.a(this.g, f3);
            }
        }
        if (!(this.h || this.g == null || this.a != null && this.bd.nextInt(20) != 0)) {
            this.a = this.as.a(this, this.g, f2);
        } else if (this.a == null && this.bd.nextInt(80) == 0 || this.bd.nextInt(80) == 0) {
            boolean bl2 = false;
            n3 = -1;
            n2 = -1;
            int n4 = -1;
            float f4 = -99999.0f;
            for (int i2 = 0; i2 < 10; ++i2) {
                int n5;
                int n6;
                int n7 = TrigLookup.b(this.aw + (double)this.bd.nextInt(13) - 6.0);
                float f5 = this.a(n7, n6 = TrigLookup.b(this.ax + (double)this.bd.nextInt(7) - 3.0), n5 = TrigLookup.b(this.ay + (double)this.bd.nextInt(13) - 6.0));
                if (!(f5 > f4)) continue;
                f4 = f5;
                n3 = n7;
                n2 = n6;
                n4 = n5;
                bl2 = true;
            }
            if (bl2) {
                this.a = this.as.a((lw)this, n3, n2, n4, 10.0f);
            }
        }
        int n8 = TrigLookup.b(this.aG.b);
        n3 = this.h_() ? 1 : 0;
        n2 = this.L() ? 1 : 0;
        this.aD = 0.0f;
        if (this.a == null || this.bd.nextInt(100) == 0) {
            super.b_();
            this.a = null;
            return;
        }
        Vector3D ao2 = this.a.a(this);
        double d2 = this.aP * 2.0f;
        while (ao2 != null && ao2.d(this.aw, ao2.y, this.ay) < d2 * d2) {
            this.a.a();
            if (this.a.b()) {
                ao2 = null;
                this.a = null;
                continue;
            }
            ao2 = this.a.a(this);
        }
        this.ak = false;
        if (ao2 != null) {
            float f6;
            double d3 = ao2.x - this.aw;
            double d4 = ao2.z - this.ay;
            double d5 = ao2.y - (double)n8;
            float f7 = (float)(Math.atan2(d4, d3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.ai = this.am;
            for (f6 = f7 - this.aC; f6 < -180.0f; f6 += 360.0f) {
            }
            while (f6 >= 180.0f) {
                f6 -= 360.0f;
            }
            if (f6 > 30.0f) {
                f6 = 30.0f;
            }
            if (f6 < -30.0f) {
                f6 = -30.0f;
            }
            this.aC += f6;
            if (this.h && this.g != null) {
                double d6 = this.g.aw - this.aw;
                double d7 = this.g.ay - this.ay;
                float f8 = this.aC;
                this.aC = (float)(Math.atan2(d7, d6) * 180.0 / 3.1415927410125732) - 90.0f;
                f6 = (f8 - this.aC + 90.0f) * (float)Math.PI / 180.0f;
                this.ah = -TrigLookup.a(f6) * this.ai * 1.0f;
                this.ai = TrigLookup.b(f6) * this.ai * 1.0f;
            }
            if (d5 > 0.0) {
                this.ak = true;
            }
        }
        if (this.g != null) {
            this.b(this.g, 30.0f);
        }
        if (this.aI) {
            this.ak = true;
        }
        if (this.bd.nextFloat() < 0.8f && (n3 != 0 || n2 != 0)) {
            this.ak = true;
        }
    }

    protected void a(lw lw2, float f2) {
    }

    protected float a(int n2, int n3, int n4) {
        return 0.0f;
    }

    protected lw c_() {
        return null;
    }

    @Override
    public boolean a() {
        int n2 = TrigLookup.b(this.aw);
        int n3 = TrigLookup.b(this.aG.b);
        int n4 = TrigLookup.b(this.ay);
        return super.a() && this.a(n2, n3, n4) >= 0.0f;
    }
}

