/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class fz
extends Block {
    protected fz(int n2, int n3) {
        super(n2, n3, hb.s);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.b(true);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.a(n2, n3 - 1, n4);
        if (n5 == 0 || !Block.registry[n5].a()) {
            return false;
        }
        return cy2.f(n2, n3 - 1, n4).c();
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        this.h(cy2, n2, n3, n4);
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        if (!this.a(cy2, n2, n3, n4)) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void a_(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = Item.aB.id;
        float f2 = 0.7f;
        double d2 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.5;
        double d3 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.5;
        double d4 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.5;
        DroppedItem eo2 = new DroppedItem(cy2, (double)n2 + d2, (double)n3 + d3, (double)n4 + d4, new InventoryItem(n6));
        eo2.c = 10;
        cy2.a(eo2);
        cy2.d(n2, n3, n4, 0);
    }

    @Override
    public int a(int n2, Random random) {
        return Item.aB.id;
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (cy2.a(ch.Block, n2, n3, n4) > 11) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
    }

    @Override
    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        hb hb2 = pk2.f(n2, n3, n4);
        if (n5 == 1) {
            return true;
        }
        if (hb2 == this.bs) {
            return false;
        }
        return super.b(pk2, n2, n3, n4, n5);
    }
}

