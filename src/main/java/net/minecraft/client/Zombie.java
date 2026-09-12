/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Zombie
extends Monster {
    public Zombie(Session cy2) {
        super(cy2);
        this.z = "/mob/zombie.png";
        this.am = 0.5f;
        this.f = 5;
    }

    @Override
    public void k() {
        float f2;
        if (this.as.d() && (f2 = this.a(1.0f)) > 0.5f && this.as.i(TrigLookup.b(this.aw), TrigLookup.b(this.ax), TrigLookup.b(this.ay)) && this.bd.nextFloat() * 30.0f < (f2 - 0.4f) * 2.0f) {
            this.bg = 300;
        }
        super.k();
    }

    @Override
    protected String d() {
        return "mob.zombie";
    }

    @Override
    protected String f_() {
        return "mob.zombiehurt";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.zombiedeath";
    }

    @Override
    protected int g_() {
        return Item.J.id;
    }
}

