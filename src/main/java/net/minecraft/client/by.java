/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class by
extends Item {
    public by(int n2) {
        super(n2);
        this.aX = 16;
    }

    @Override
    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        --fp2.count;
        cy2.a((lw)eb2, "random.bow", 0.5f, 0.4f / (b.nextFloat() * 0.4f + 0.8f));
        if (!cy2.z) {
            cy2.a(new Snowball(cy2, eb2));
        }
        return fp2;
    }
}

