/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class na
extends of {
    final int c;
    final ne d;

    na(ne ne2, ex ex2, hi hi2, int n2, int n3, int n4, int n5) {
        super(ex2, hi2, n2, n3, n4);
        this.d = ne2;
        this.c = n5;
    }

    @Override
    public int e() {
        return 1;
    }

    @Override
    public boolean a(InventoryItem fp2) {
        if (fp2.a() instanceof oj) {
            return ((oj)fp2.a()).bb == this.c;
        }
        System.out.println(fp2.a().id + ", " + this.c);
        if (fp2.a().id == Block.ba.bh) {
            return this.c == 0;
        }
        return false;
    }

    @Override
    public int c() {
        return 15 + this.c * 16;
    }
}

