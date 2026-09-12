/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ac
extends cb {
    static private Block[] bb = new Block[]{Block.w, Block.aj, Block.ak, Block.t, Block.ao, Block.H, Block.ai, Block.I, Block.ah, Block.G, Block.aw, Block.ax, Block.aT, Block.bb};
    private int bc;

    public ac(int n2, int n3) {
        super(n2, 2, n3, bb);
        this.bc = n3;
    }

    @Override
    public boolean a(Block nq2) {
        if (nq2 == Block.ap) {
            return this.bc == 3;
        }
        if (nq2 == Block.ax || nq2 == Block.aw) {
            return this.bc >= 2;
        }
        if (nq2 == Block.ah || nq2 == Block.G) {
            return this.bc >= 2;
        }
        if (nq2 == Block.ai || nq2 == Block.H) {
            return this.bc >= 1;
        }
        if (nq2 == Block.aN || nq2 == Block.aO) {
            return this.bc >= 2;
        }
        if (nq2.bs == hb.d) {
            return true;
        }
        return nq2.bs == hb.e;
    }
}

