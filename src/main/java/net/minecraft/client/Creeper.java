/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Creeper
extends Monster {
    int a;
    int b;
    int c = 30;
    int d = -1;
    int e = -1;

    public Creeper(Session cy2) {
        super(cy2);
        this.z = "/mob/creeper.png";
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
    public void a(byte by2) {
        super.a(by2);
        if (by2 == 4) {
            if (this.a == 0) {
                this.as.a(this, "random.fuse", 1.0f, 0.5f);
            }
            this.d = 1;
        }
        if (by2 == 5) {
            this.d = -1;
        }
    }

    @Override
    public void e_() {
        this.b = this.a;
        if (this.as.z) {
            this.a += this.d;
            if (this.a < 0) {
                this.a = 0;
            }
            if (this.a >= this.c) {
                this.a = this.c;
            }
        }
        super.e_();
    }

    @Override
    protected void b_() {
        if (this.e != this.d) {
            this.e = this.d;
            if (this.d > 0) {
                this.as.a((lw)this, 4);
            } else {
                this.as.a((lw)this, 5);
            }
        }
        this.b = this.a;
        if (this.as.z) {
            super.b_();
        } else {
            if (this.a > 0 && this.d < 0) {
                --this.a;
            }
            if (this.d >= 0) {
                this.d = 2;
            }
            super.b_();
            if (this.d != 1) {
                this.d = -1;
            }
        }
    }

    @Override
    protected String f_() {
        return "mob.creeper";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.creeperdeath";
    }

    @Override
    public void b(lw lw2) {
        super.b(lw2);
        if (lw2 instanceof Skeleton) {
            this.b(Item.aU.id + this.bd.nextInt(2), 1);
        }
    }

    @Override
    protected void a(lw lw2, float f2) {
        if (this.d <= 0 && f2 < 3.0f || this.d > 0 && f2 < 7.0f) {
            if (this.a == 0) {
                this.as.a(this, "random.fuse", 1.0f, 0.5f);
            }
            this.d = 1;
            ++this.a;
            if (this.a == this.c) {
                this.as.a((lw)this, this.aw, this.ax, this.ay, 3.0f);
                this.J();
            }
            this.h = true;
        }
    }

    public float b(float f2) {
        return ((float)this.b + (float)(this.a - this.b) * f2) / (float)(this.c - 2);
    }

    @Override
    protected int g_() {
        return Item.K.id;
    }
}

