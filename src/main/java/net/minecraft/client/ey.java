/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ey
extends Item {
    public ey(int n2) {
        super(n2);
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            --n3;
        }
        if (n5 == 1) {
            ++n3;
        }
        if (n5 == 2) {
            --n4;
        }
        if (n5 == 3) {
            ++n4;
        }
        if (n5 == 4) {
            --n2;
        }
        if (n5 == 5) {
            ++n2;
        }
        if (cy2.a(n2, n3, n4) != 0) {
            return false;
        }
        if (Block.av.a(cy2, n2, n3, n4)) {
            --fp2.count;
            cy2.d(n2, n3, n4, Block.av.bh);
        }
        return true;
    }
}

