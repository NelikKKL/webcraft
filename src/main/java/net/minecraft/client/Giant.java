/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Giant
extends Monster {
    public Giant(Session cy2) {
        super(cy2);
        this.z = "/mob/zombie.png";
        this.am = 0.5f;
        this.f = 50;
        this.J *= 10;
        this.aO *= 6.0f;
        this.a(this.aP * 6.0f, this.aQ * 6.0f);
    }

    @Override
    protected float a(int n2, int n3, int n4) {
        return this.as.c(n2, n3, n4) - 0.5f;
    }
}

