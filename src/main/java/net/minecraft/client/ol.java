/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ol
extends cb {
    static private Block[] bb = new Block[]{Block.u, Block.v, Block.E, Block.F, Block.aS, Block.aU, Block.aW};

    public ol(int n2, int n3) {
        super(n2, 1, n3, bb);
    }

    @Override
    public boolean a(Block nq2) {
        if (nq2 == Block.aS) {
            return true;
        }
        return nq2 == Block.aU;
    }
}

