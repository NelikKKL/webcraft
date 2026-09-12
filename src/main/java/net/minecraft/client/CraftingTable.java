/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class CraftingTable
implements hi {
    private InventoryItem[] a;
    private int b;
    private ax c;

    public CraftingTable(ax ax2, int n2, int n3) {
        this.b = n2 * n3;
        this.a = new InventoryItem[this.b];
        this.c = ax2;
    }

    public CraftingTable(ax ax2, InventoryItem[] fpArray) {
        this.b = fpArray.length;
        this.a = fpArray;
        this.c = ax2;
    }

    @Override
    public int c() {
        return this.b;
    }

    @Override
    public InventoryItem c(int n2) {
        return this.a[n2];
    }

    @Override
    public String d() {
        return "Crafting";
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        if (this.a[n2] != null) {
            if (this.a[n2].count <= n3) {
                InventoryItem fp2 = this.a[n2];
                this.a[n2] = null;
                this.c.a(this);
                return fp2;
            }
            InventoryItem fp3 = this.a[n2].a(n3);
            if (this.a[n2].count == 0) {
                this.a[n2] = null;
            }
            this.c.a(this);
            return fp3;
        }
        return null;
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        this.a[n2] = fp2;
        this.c.a(this);
    }

    @Override
    public int o_() {
        return 64;
    }

    @Override
    public void h() {
    }
}

