/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class nv
extends Item {
    public nv(int n2) {
        super(n2);
        this.aY = 64;
        this.aX = 1;
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            return false;
        }
        if (!cy2.f(n2, n3, n4).a()) {
            return false;
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
        if (!Block.aD.a(cy2, n2, n3, n4)) {
            return false;
        }
        if (n5 == 1) {
            cy2.b(n2, n3, n4, Block.aD.bh, TrigLookup.b((double)((eb2.aC + 180.0f) * 16.0f / 360.0f) + 0.5) & 0xF);
        } else {
            cy2.b(n2, n3, n4, Block.aI.bh, n5);
        }
        --fp2.count;
        Sign qc2 = (Sign)cy2.b(n2, n3, n4);
        if (qc2 != null) {
            eb2.a(qc2);
        }
        return true;
    }
}

