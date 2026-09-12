/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Sheep
extends Creature {
    public boolean a = false;

    public Sheep(Session cy2) {
        super(cy2);
        this.z = "/mob/sheep.png";
        this.a(0.9f, 1.3f);
    }

    @Override
    public boolean a(lw lw2, int n2) {
        if (!this.as.z && !this.a && lw2 instanceof Mob) {
            this.a = true;
            int n3 = 1 + this.bd.nextInt(3);
            for (int i2 = 0; i2 < n3; ++i2) {
                DroppedItem eo2 = this.a(Block.ab.bh, 1, 1.0f);
                eo2.aA += (double)(this.bd.nextFloat() * 0.05f);
                eo2.az += (double)((this.bd.nextFloat() - this.bd.nextFloat()) * 0.1f);
                eo2.aB += (double)((this.bd.nextFloat() - this.bd.nextFloat()) * 0.1f);
            }
        }
        return super.a(lw2, n2);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        iq2.storeBoolean("Sheared", this.a);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        this.a = iq2.isTrue("Sheared");
    }

    @Override
    protected String d() {
        return "mob.sheep";
    }

    @Override
    protected String f_() {
        return "mob.sheep";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.sheep";
    }
}

