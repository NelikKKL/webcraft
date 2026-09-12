/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class pr
extends ec {
    public pr(gm gm2, gm gm3, float f2) {
        super(gm2, f2);
        this.a(gm3);
    }

    protected boolean a(Sheep bx2, int n2) {
        this.a("/mob/sheep_fur.png");
        return n2 == 0 && !bx2.a;
    }

    @Override
    protected boolean a(Mob hf2, int n2) {
        return this.a((Sheep)hf2, n2);
    }
}

