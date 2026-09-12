/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class mw
extends Item {
    private String a;

    protected mw(int n2, String string) {
        super(n2);
        this.a = string;
        this.aX = 1;
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.a(n2, n3, n4) == Block.aY.bh && cy2.e(n2, n3, n4) == 0) {
            cy2.b(n2, n3, n4, this.id - Item.aU.id + 1);
            cy2.a(this.a, n2, n3, n4);
            --fp2.count;
            return true;
        }
        return false;
    }
}

