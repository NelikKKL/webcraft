/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class q
extends ax {
    public CraftingTable a;
    public hi b = new oq();

    public q(InventoryItem[] fpArray) {
        this.a = new CraftingTable(this, fpArray);
        this.a(this.a);
    }

    @Override
    public void a(hi hi2) {
        int[] nArray = new int[9];
        for (int i2 = 0; i2 < 3; ++i2) {
            for (int i3 = 0; i3 < 3; ++i3) {
                InventoryItem fp2;
                int n2 = -1;
                if (i2 < 2 && i3 < 2 && (fp2 = this.a.c(i2 + i3 * 2)) != null) {
                    n2 = fp2.id;
                }
                nArray[i2 + i3 * 3] = n2;
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

