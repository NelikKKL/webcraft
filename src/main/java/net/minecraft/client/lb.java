/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class lb
extends Item {
    public int a;

    public lb(int n2, int n3) {
        super(n2);
        this.aX = 1;
        this.a = n3;
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.a(n2, n3, n4);
        if (n6 == Block.aG.bh) {
            if (!cy2.z) {
                cy2.a(new Minecart(cy2, (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f, this.a));
            }
            --fp2.count;
            return true;
        }
        return false;
    }
}

