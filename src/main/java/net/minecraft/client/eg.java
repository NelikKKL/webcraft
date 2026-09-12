/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class eg {
    private String[][] a = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    private Object[][] b = new Object[][]{{Block.x, Block.w, Item.m, Item.l, Item.n}, {Item.r, Item.v, Item.e, Item.z, Item.G}, {Item.q, Item.u, Item.d, Item.y, Item.F}, {Item.s, Item.w, Item.f, Item.A, Item.H}, {Item.L, Item.M, Item.N, Item.O, Item.P}};

    public void a(RecipeRegistry en2) {
        for (int i2 = 0; i2 < this.b[0].length; ++i2) {
            Object object = this.b[0][i2];
            for (int i3 = 0; i3 < this.b.length - 1; ++i3) {
                Item dx2 = (Item)this.b[i3 + 1][i2];
                en2.a(new InventoryItem(dx2), this.a[i3], Character.valueOf('#'), Item.B, Character.valueOf('X'), object);
            }
        }
    }
}

