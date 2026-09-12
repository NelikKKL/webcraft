/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ar
extends of {
    private final hi c;

    public ar(ex ex2, hi hi2, hi hi3, int n2, int n3, int n4) {
        super(ex2, hi3, n2, n3, n4);
        this.c = hi2;
    }

    @Override
    public boolean a(InventoryItem fp2) {
        return false;
    }

    @Override
    public void a() {
        for (int i2 = 0; i2 < this.c.c(); ++i2) {
            if (this.c.c(i2) == null) continue;
            this.c.a(i2, 1);
        }
    }
}

