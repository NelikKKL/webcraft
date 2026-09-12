/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class eu
extends Item {
    private hb a;

    public eu(int n2, hb hb2) {
        super(n2);
        this.a = hb2;
        this.aY = 64;
        this.aX = 1;
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        if (n5 != 1) {
            return false;
        }
        Block nq2 = this.a == hb.c ? Block.aE : Block.aL;
        if (!nq2.a(cy2, n2, ++n3, n4)) {
            return false;
        }
        int n6 = TrigLookup.b((double)((eb2.aC + 180.0f) * 4.0f / 360.0f) - 0.5) & 3;
        int n7 = 0;
        int n8 = 0;
        if (n6 == 0) {
            n8 = 1;
        }
        if (n6 == 1) {
            n7 = -1;
        }
        if (n6 == 2) {
            n8 = -1;
        }
        if (n6 == 3) {
            n7 = 1;
        }
        int n9 = (cy2.g(n2 - n7, n3, n4 - n8) ? 1 : 0) + (cy2.g(n2 - n7, n3 + 1, n4 - n8) ? 1 : 0);
        int n10 = (cy2.g(n2 + n7, n3, n4 + n8) ? 1 : 0) + (cy2.g(n2 + n7, n3 + 1, n4 + n8) ? 1 : 0);
        boolean bl2 = cy2.a(n2 - n7, n3, n4 - n8) == nq2.bh || cy2.a(n2 - n7, n3 + 1, n4 - n8) == nq2.bh;
        boolean bl3 = cy2.a(n2 + n7, n3, n4 + n8) == nq2.bh || cy2.a(n2 + n7, n3 + 1, n4 + n8) == nq2.bh;
        boolean bl4 = false;
        if (bl2 && !bl3) {
            bl4 = true;
        } else if (n10 > n9) {
            bl4 = true;
        }
        if (bl4) {
            n6 = n6 - 1 & 3;
            n6 += 4;
        }
        cy2.d(n2, n3, n4, nq2.bh);
        cy2.b(n2, n3, n4, n6);
        cy2.d(n2, n3 + 1, n4, nq2.bh);
        cy2.b(n2, n3 + 1, n4, n6 + 8);
        --fp2.count;
        return true;
    }
}

