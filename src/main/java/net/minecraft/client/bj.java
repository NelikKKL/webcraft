/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class bj
extends Item {
    public bj(int n2) {
        super(n2);
        this.aY = 64;
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public boolean b() {
        return true;
    }

    @Override
    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        if (eb2.n != null) {
            int n2 = eb2.n.k();
            fp2.b(n2);
            eb2.z();
        } else {
            cy2.a((lw)eb2, "random.bow", 0.5f, 0.4f / (b.nextFloat() * 0.4f + 0.8f));
            if (!cy2.z) {
                cy2.a(new hj(cy2, eb2));
            }
            eb2.z();
        }
        return fp2;
    }
}

