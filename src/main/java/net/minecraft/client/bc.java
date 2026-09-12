/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class bc
extends Item {
    private int a;

    public bc(int n2) {
        super(n2);
        this.a = n2 + 256;
        this.setIcon(Block.registry[n2 + 256].a(2));
    }

    @Override
    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.a(n2, n3, n4) == Block.aS.bh) {
            n5 = 0;
        } else {
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
        }
        if (fp2.count == 0) {
            return false;
        }
        if (cy2.a(this.a, n2, n3, n4, false)) {
            Block nq2 = Block.registry[this.a];
            if (cy2.d(n2, n3, n4, this.a)) {
                Block.registry[this.a].d(cy2, n2, n3, n4, n5);
                Block.registry[this.a].a(cy2, n2, n3, n4, (Mob)eb2);
                cy2.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), nq2.bq.d(), (nq2.bq.b() + 1.0f) / 2.0f, nq2.bq.c() * 0.8f);
                --fp2.count;
            }
        }
        return true;
    }
}

