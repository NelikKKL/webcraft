/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Sign
extends TileEntityRegistry {
    public String[] a = new String[]{"", "", "", ""};
    public int b = -1;

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        iq2.storeString("Text1", this.a[0]);
        iq2.storeString("Text2", this.a[1]);
        iq2.storeString("Text3", this.a[2]);
        iq2.storeString("Text4", this.a[3]);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        for (int i2 = 0; i2 < 4; ++i2) {
            this.a[i2] = iq2.getString("Text" + (i2 + 1));
            if (this.a[i2].length() <= 15) continue;
            this.a[i2] = this.a[i2].substring(0, 15);
        }
    }
}

