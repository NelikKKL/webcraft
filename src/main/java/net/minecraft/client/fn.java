/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class fn
extends ax {
    public CraftingTable a = new CraftingTable(this, 3, 3);
    public hi b = new oq();

    @Override
    public void a(hi hi2) {
        int[] nArray = new int[9];
        for (int i2 = 0; i2 < 3; ++i2) {
            for (int i3 = 0; i3 < 3; ++i3) {
                int n2 = i2 + i3 * 3;
                InventoryItem fp2 = this.a.c(n2);
                nArray[n2] = fp2 == null ? -1 : fp2.id;
            }
        }
        this.b.a(0, RecipeRegistry.a().a(nArray));
    }

    @Override
    public void a(Player eb2) {
        super.a(eb2);
        for (int i2 = 0; i2 < 9; ++i2) {
            InventoryItem fp2 = this.a.c(i2);
            if (fp2 == null) continue;
            eb2.a(fp2);
        }
    }
}

