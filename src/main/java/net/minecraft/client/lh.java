/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public abstract class lh
extends Block {
    protected lh(int n2, hb hb2) {
        super(n2, hb2);
        lh.p[n2] = true;
    }

    protected lh(int n2, int n3, hb hb2) {
        super(n2, n3, hb2);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        super.e(cy2, n2, n3, n4);
        cy2.a(n2, n3, n4, this.a_());
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        super.b(cy2, n2, n3, n4);
        cy2.l(n2, n3, n4);
    }

    protected abstract TileEntityRegistry a_();
}

