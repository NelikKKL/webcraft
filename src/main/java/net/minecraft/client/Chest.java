/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Chest
extends TileEntityRegistry
implements hi {
    private InventoryItem[] storage = new InventoryItem[36];

    @Override
    public int c() {
        return 27;
    }

    @Override
    public InventoryItem c(int n2) {
        return this.storage[n2];
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        if (this.storage[n2] != null) {
            if (this.storage[n2].count <= n3) {
                InventoryItem fp2 = this.storage[n2];
                this.storage[n2] = null;
                this.h();
                return fp2;
            }
            InventoryItem fp3 = this.storage[n2].a(n3);
            if (this.storage[n2].count == 0) {
                this.storage[n2] = null;
            }
            this.h();
            return fp3;
        }
        return null;
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        this.storage[n2] = fp2;
        if (fp2 != null && fp2.count > this.o_()) {
            fp2.count = this.o_();
        }
        this.h();
    }

    @Override
    public String d() {
        return "Chest";
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        NBTListTag ly2 = iq2.getListTag("Items");
        this.storage = new InventoryItem[this.c()];
        for (int i2 = 0; i2 < ly2.c(); ++i2) {
            NBTCompoundTag iq3 = (NBTCompoundTag)ly2.a(i2);
            int n2 = iq3.getByte("Slot") & 0xFF;
            if (n2 < 0 || n2 >= this.storage.length) continue;
            this.storage[n2] = new InventoryItem(iq3);
        }
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        NBTListTag ly2 = new NBTListTag();
        for (int i2 = 0; i2 < this.storage.length; ++i2) {
            if (this.storage[i2] == null) continue;
            NBTCompoundTag iq3 = new NBTCompoundTag();
            iq3.storeByte("Slot", (byte)i2);
            this.storage[i2].a(iq3);
            ly2.a(iq3);
        }
        iq2.storeTag("Items", ly2);
    }

    @Override
    public int o_() {
        return 64;
    }
}

