/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class pa {
    private String[][] a = new String[][]{{"X", "X", "#"}};
    private Object[][] b = new Object[][]{{Block.x, Block.w, Item.m, Item.l, Item.n}, {Item.p, Item.t, Item.o, Item.x, Item.E}};

    public void a(RecipeRegistry en2) {
        for (int i2 = 0; i2 < this.b[0].length; ++i2) {
            Object object = this.b[0][i2];
            for (int i3 = 0; i3 < this.b.length - 1; ++i3) {
                Item dx2 = (Item)this.b[i3 + 1][i2];
                en2.a(new InventoryItem(dx2), this.a[i3], Character.valueOf('#'), Item.B, Character.valueOf('X'), object);
            }
        }
        en2.a(new InventoryItem(Item.i, 1), " #X", "# X", " #X", Character.valueOf('X'), Item.I, Character.valueOf('#'), Item.B);
        en2.a(new InventoryItem(Item.j, 4), "X", "#", "Y", Character.valueOf('Y'), Item.J, Character.valueOf('X'), Item.an, Character.valueOf('#'), Item.B);
    }
}

