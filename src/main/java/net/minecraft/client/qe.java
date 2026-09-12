/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class qe
extends Item {
    public qe(int n2) {
        super(n2);
        this.aY = 64;
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        Painting kp2;
        if (n5 == 0) {
            return false;
        }
        if (n5 == 1) {
            return false;
        }
        int n6 = 0;
        if (n5 == 4) {
            n6 = 1;
        }
        if (n5 == 3) {
            n6 = 2;
        }
        if (n5 == 5) {
            n6 = 3;
        }
        if ((kp2 = new Painting(cy2, n2, n3, n4, n6)).k()) {
            cy2.a(kp2);
            --fp2.count;
        }
        return true;
    }
}

