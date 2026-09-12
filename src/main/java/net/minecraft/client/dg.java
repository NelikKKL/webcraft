/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class dg
extends Block {
    protected dg(int n2, int n3) {
        super(n2, n3, hb.c);
    }

    @Override
    public int a(int n2) {
        return this.bg + (n2 == 1 ? 1 : 0);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        int n5 = cy2.e(n2, n3, n4);
        if (n5 > 0) {
            this.e(cy2, n2, n3, n4, n5);
            return true;
        }
        return false;
    }

    public void e(Session cy2, int n2, int n3, int n4, int n5) {
        cy2.a((String)null, n2, n3, n4);
        cy2.b(n2, n3, n4, 0);
        int n6 = Item.aU.id + n5 - 1;
        float f2 = 0.7f;
        double d2 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.5;
        double d3 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.2 + 0.6;
        double d4 = (double)(cy2.l.nextFloat() * f2) + (double)(1.0f - f2) * 0.5;
        DroppedItem eo2 = new DroppedItem(cy2, (double)n2 + d2, (double)n3 + d3, (double)n4 + d4, new InventoryItem(n6));
        eo2.c = 10;
        cy2.a(eo2);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5, float f2) {
        if (cy2.z) {
            return;
        }
        if (n5 > 0) {
            this.e(cy2, n2, n3, n4, n5);
        }
        super.a(cy2, n2, n3, n4, n5, f2);
    }
}

