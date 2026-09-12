/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class mv {
    private Object[][] a = new Object[][]{{Block.ah, Item.n}, {Block.ai, Item.m}, {Block.ax, Item.l}};

    public void a(RecipeRegistry en2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            Block nq2 = (Block)this.a[i2][0];
            Item dx2 = (Item)this.a[i2][1];
            en2.a(new InventoryItem(nq2), "###", "###", "###", Character.valueOf('#'), dx2);
            en2.a(new InventoryItem(dx2, 9), "#", Character.valueOf('#'), nq2);
        }
    }
}

