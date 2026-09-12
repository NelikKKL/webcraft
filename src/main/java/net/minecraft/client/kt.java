/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class kt
extends Item {
    public kt(int n2) {
        super(n2);
        this.aX = 1;
    }

    @Override
    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        if (eb2.e.b(Item.j.id)) {
            cy2.a((lw)eb2, "random.bow", 1.0f, 1.0f / (b.nextFloat() * 0.4f + 0.8f));
            if (!cy2.z) {
                cy2.a(new Arrow(cy2, eb2));
            }
        }
        return fp2;
    }
}

