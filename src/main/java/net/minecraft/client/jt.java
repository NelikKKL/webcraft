/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;

public class jt
extends iv {
    public jt(Minecraft minecraft) {
        super(minecraft);
        this.b = true;
    }

    @Override
    public void b(Player eb2) {
        for (int i2 = 0; i2 < 9; ++i2) {
            if (eb2.e.storage[i2] == null) {
                this.a.g.e.storage[i2] = new InventoryItem(((Block)ea.a.get((int)i2)).bh);
                continue;
            }
            this.a.g.e.storage[i2].count = 1;
        }
    }

    @Override
    public boolean d() {
        return false;
    }

    @Override
    public void a(Session cy2) {
        super.a(cy2);
    }

    @Override
    public void c() {
    }
}

