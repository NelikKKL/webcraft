/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class o {
    private String[][] a = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] b = new Object[][]{{Item.aD, Block.ar, Item.m, Item.l, Item.n}, {Item.T, Item.X, Item.ab, Item.af, Item.aj}, {Item.U, Item.Y, Item.ac, Item.ag, Item.ak}, {Item.V, Item.Z, Item.ad, Item.ah, Item.al}, {Item.W, Item.aa, Item.ae, Item.ai, Item.am}};

    public void a(RecipeRegistry en2) {
        for (int i2 = 0; i2 < this.b[0].length; ++i2) {
            Object object = this.b[0][i2];
            for (int i3 = 0; i3 < this.b.length - 1; ++i3) {
                Item dx2 = (Item)this.b[i3 + 1][i2];
                en2.a(new InventoryItem(dx2), this.a[i3], Character.valueOf('X'), object);
            }
        }
    }
}

