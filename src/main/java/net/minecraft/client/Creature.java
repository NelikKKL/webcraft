/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public abstract class Creature
extends BaseAI {
    public Creature(Session cy2) {
        super(cy2);
    }

    @Override
    protected float a(int n2, int n3, int n4) {
        if (this.as.a(n2, n3 - 1, n4) == Block.u.bh) {
            return 10.0f;
        }
        return this.as.c(n2, n3, n4) - 0.5f;
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
    public boolean a() {
        int n2;
        int n3;
        int n4 = TrigLookup.b(this.aw);
        return this.as.a(n4, (n3 = TrigLookup.b(this.aG.b)) - 1, n2 = TrigLookup.b(this.ay)) == Block.u.bh && this.as.j(n4, n3, n2) > 8 && super.a();
    }

    @Override
    public int b() {
        return 120;
    }
}

