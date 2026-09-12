/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Chicken
extends Creature {
    public boolean a = false;
    public float b = 0.0f;
    public float c = 0.0f;
    public float d;
    public float e;
    public float f = 1.0f;
    public int i;

    public Chicken(Session cy2) {
        super(cy2);
        this.z = "/mob/chicken.png";
        this.a(0.3f, 0.4f);
        this.J = 4;
        this.i = this.bd.nextInt(6000) + 6000;
    }

    @Override
    public void k() {
        super.k();
        this.e = this.b;
        this.d = this.c;
        this.c = (float)((double)this.c + (double)(this.aH ? -1 : 4) * 0.3);
        if (this.c < 0.0f) {
            this.c = 0.0f;
        }
        if (this.c > 1.0f) {
            this.c = 1.0f;
        }
        if (!this.aH && this.f < 1.0f) {
            this.f = 1.0f;
        }
        this.f = (float)((double)this.f * 0.9);
        if (!this.aH && this.aA < 0.0) {
            this.aA *= 0.6;
        }
        this.b += this.f * 2.0f;
        if (!this.as.z && --this.i <= 0) {
            this.as.a(this, "mob.chickenplop", 1.0f, (this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f);
            this.b(Item.aN.id, 1);
            this.i = this.bd.nextInt(6000) + 6000;
        }
    }

    @Override
    protected void c(float f2) {
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
    protected String d() {
        return "mob.chicken";
    }

    @Override
    protected String f_() {
        return "mob.chickenhurt";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.chickenhurt";
    }

    @Override
    protected int g_() {
        return Item.J.id;
    }
}

