/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Skeleton
extends Monster {
    static private final InventoryItem a = new InventoryItem(Item.i, 1);

    public Skeleton(Session cy2) {
        super(cy2);
        this.z = "/mob/skeleton.png";
    }

    @Override
    protected String d() {
        return "mob.skeleton";
    }

    @Override
    protected String f_() {
        return "mob.skeletonhurt";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.skeletonhurt";
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
    protected void a(lw lw2, float f2) {
        if (f2 < 10.0f) {
            double d2 = lw2.aw - this.aw;
            double d3 = lw2.ay - this.ay;
            if (this.P == 0) {
                Arrow lv2 = new Arrow(this.as, this);
                lv2.ax += (double)1.4f;
                double d4 = lw2.ax - (double)0.2f - lv2.ax;
                float f3 = TrigLookup.a(d2 * d2 + d3 * d3) * 0.2f;
                this.as.a(this, "random.bow", 1.0f, 1.0f / (this.bd.nextFloat() * 0.4f + 0.8f));
                this.as.a(lv2);
                lv2.a(d2, d4 + (double)f3, d3, 0.6f, 12.0f);
                this.P = 30;
            }
            this.aC = (float)(Math.atan2(d3, d2) * 180.0 / 3.1415927410125732) - 90.0f;
            this.h = true;
        }
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
    protected int g_() {
        return Item.j.id;
    }

    @Override
    public InventoryItem l() {
        return a;
    }
}

