/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Pig
extends Creature {
    public boolean a = false;

    public Pig(Session cy2) {
        super(cy2);
        this.z = "/mob/pig.png";
        this.a(0.9f, 0.9f);
        this.a = false;
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        iq2.storeBoolean("Saddle", this.a);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        this.a = iq2.isTrue("Saddle");
    }

    @Override
    protected String d() {
        return "mob.pig";
    }

    @Override
    protected String f_() {
        return "mob.pig";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.pigdeath";
    }

    @Override
    public boolean a(Player eb2) {
        if (this.a) {
            eb2.h(this);
            return true;
        }
        return false;
    }

    @Override
    protected int g_() {
        return Item.ao.id;
    }
}

