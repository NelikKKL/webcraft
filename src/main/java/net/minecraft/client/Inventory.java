/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Inventory
implements hi {
    public InventoryItem[] storage = new InventoryItem[37];
    public InventoryItem[] b = new InventoryItem[4];
    public InventoryItem[] c = new InventoryItem[4];
    public int d = 0;
    private Player owner;
    public InventoryItem holding;
    public boolean f = false;

    public Inventory(Player eb2) {
        this.owner = eb2;
    }

    public InventoryItem a() {
        return this.storage[this.d];
    }

    private int f(int n2) {
        for (int i2 = 0; i2 < this.storage.length; ++i2) {
            if (this.storage[i2] == null || this.storage[i2].id != n2) continue;
            return i2;
        }
        return -1;
    }

    private int g(int n2) {
        for (int i2 = 0; i2 < this.storage.length; ++i2) {
            if (this.storage[i2] == null || this.storage[i2].id != n2 || this.storage[i2].count >= this.storage[i2].c() || this.storage[i2].count >= this.o_()) continue;
            return i2;
        }
        return -1;
    }

    private int j() {
        for (int i2 = 0; i2 < this.storage.length; ++i2) {
            if (this.storage[i2] != null) continue;
            return i2;
        }
        return -1;
    }

    public void a(int n2, boolean bl2) {
        int n3 = this.f(n2);
        if (n3 >= 0 && n3 < 9) {
            this.d = n3;
            return;
        }
    }

    public void a(int n2) {
        if (n2 > 0) {
            n2 = 1;
        }
        if (n2 < 0) {
            n2 = -1;
        }
        this.d -= n2;
        while (this.d < 0) {
            this.d += 9;
        }
        while (this.d >= 9) {
            this.d -= 9;
        }
    }

    private int b(int n2, int n3) {
        int n4;
        int n5 = this.g(n2);
        if (n5 < 0) {
            n5 = this.j();
        }
        if (n5 < 0) {
            return n3;
        }
        if (this.storage[n5] == null) {
            this.storage[n5] = new InventoryItem(n2, 0);
        }
        if ((n4 = n3) > this.storage[n5].c() - this.storage[n5].count) {
            n4 = this.storage[n5].c() - this.storage[n5].count;
        }
        if (n4 > this.o_() - this.storage[n5].count) {
            n4 = this.o_() - this.storage[n5].count;
        }
        if (n4 == 0) {
            return n3;
        }
        this.storage[n5].count += n4;
        this.storage[n5].probablyCooldown = 5;
        return n3 -= n4;
    }

    public void b() {
        for (int i2 = 0; i2 < this.storage.length; ++i2) {
            if (this.storage[i2] == null || this.storage[i2].probablyCooldown <= 0) continue;
            --this.storage[i2].probablyCooldown;
        }
    }

    public boolean b(int n2) {
        int n3 = this.f(n2);
        if (n3 < 0) {
            return false;
        }
        if (--this.storage[n3].count <= 0) {
            this.storage[n3] = null;
        }
        return true;
    }

    public boolean a(InventoryItem fp2) {
        int n2;
        if (fp2.damage == 0) {
            fp2.count = this.b(fp2.id, fp2.count);
            if (fp2.count == 0) {
                return true;
            }
        }
        if ((n2 = this.j()) >= 0) {
            this.storage[n2] = fp2;
            this.storage[n2].probablyCooldown = 5;
            return true;
        }
        return false;
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        InventoryItem[] fpArray = this.storage;
        if (n2 >= this.storage.length) {
            fpArray = this.b;
            n2 -= this.storage.length;
        }
        if (fpArray[n2] != null) {
            if (fpArray[n2].count <= n3) {
                InventoryItem fp2 = fpArray[n2];
                fpArray[n2] = null;
                return fp2;
            }
            InventoryItem fp3 = fpArray[n2].a(n3);
            if (fpArray[n2].count == 0) {
                fpArray[n2] = null;
            }
            return fp3;
        }
        return null;
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        InventoryItem[] fpArray = this.storage;
        if (n2 >= fpArray.length) {
            n2 -= fpArray.length;
            fpArray = this.b;
        }
        if (n2 >= fpArray.length) {
            n2 -= fpArray.length;
            fpArray = this.c;
        }
        fpArray[n2] = fp2;
    }

    public float a(Block nq2) {
        float f2 = 1.0f;
        if (this.storage[this.d] != null) {
            f2 *= this.storage[this.d].a(nq2);
        }
        return f2;
    }

    public NBTListTag a(NBTListTag ly2) {
        NBTCompoundTag iq2;
        int n2;
        for (n2 = 0; n2 < this.storage.length; ++n2) {
            if (this.storage[n2] == null) continue;
            iq2 = new NBTCompoundTag();
            iq2.storeByte("Slot", (byte)n2);
            this.storage[n2].a(iq2);
            ly2.a(iq2);
        }
        for (n2 = 0; n2 < this.b.length; ++n2) {
            if (this.b[n2] == null) continue;
            iq2 = new NBTCompoundTag();
            iq2.storeByte("Slot", (byte)(n2 + 100));
            this.b[n2].a(iq2);
            ly2.a(iq2);
        }
        for (n2 = 0; n2 < this.c.length; ++n2) {
            if (this.c[n2] == null) continue;
            iq2 = new NBTCompoundTag();
            iq2.storeByte("Slot", (byte)(n2 + 80));
            this.c[n2].a(iq2);
            ly2.a(iq2);
        }
        return ly2;
    }

    public void b(NBTListTag ly2) {
        this.storage = new InventoryItem[36];
        this.b = new InventoryItem[4];
        this.c = new InventoryItem[4];
        for (int i2 = 0; i2 < ly2.c(); ++i2) {
            NBTCompoundTag iq2 = (NBTCompoundTag)ly2.a(i2);
            int n2 = iq2.getByte("Slot") & 0xFF;
            if (n2 >= 0 && n2 < this.storage.length) {
                this.storage[n2] = new InventoryItem(iq2);
            }
            if (n2 >= 80 && n2 < this.c.length + 80) {
                this.c[n2 - 80] = new InventoryItem(iq2);
            }
            if (n2 < 100 || n2 >= this.b.length + 100) continue;
            this.b[n2 - 100] = new InventoryItem(iq2);
        }
    }

    @Override
    public int c() {
        return this.storage.length + 4;
    }

    @Override
    public InventoryItem c(int n2) {
        InventoryItem[] fpArray = this.storage;
        if (n2 >= fpArray.length) {
            n2 -= fpArray.length;
            fpArray = this.b;
        }
        if (n2 >= fpArray.length) {
            n2 -= fpArray.length;
            fpArray = this.c;
        }
        return fpArray[n2];
    }

    @Override
    public String d() {
        return "Inventory";
    }

    @Override
    public int o_() {
        return 64;
    }

    public int a(lw lw2) {
        InventoryItem fp2 = this.c(this.d);
        if (fp2 != null) {
            return fp2.a(lw2);
        }
        return 1;
    }

    public boolean b(Block nq2) {
        if (nq2.bs != hb.d && nq2.bs != hb.e && nq2.bs != hb.t && nq2.bs != hb.s) {
            return true;
        }
        InventoryItem fp2 = this.c(this.d);
        if (fp2 != null) {
            return fp2.b(nq2);
        }
        return false;
    }

    public InventoryItem d(int n2) {
        return this.b[n2];
    }

    public int f() {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            if (this.b[i2] == null || !(this.b[i2].a() instanceof oj)) continue;
            int n5 = this.b[i2].d();
            int n6 = this.b[i2].damage;
            int n7 = n5 - n6;
            n3 += n7;
            n4 += n5;
            int n8 = ((oj)this.b[i2].a()).bc;
            n2 += n8;
        }
        if (n4 == 0) {
            return 0;
        }
        return (n2 - 1) * n3 / n4 + 1;
    }

    public void e(int n2) {
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            if (this.b[i2] == null || !(this.b[i2].a() instanceof oj)) continue;
            this.b[i2].b(n2);
            if (this.b[i2].count != 0) continue;
            this.b[i2].a(this.owner);
            this.b[i2] = null;
        }
    }

    public void g() {
        int n2;
        for (n2 = 0; n2 < this.storage.length; ++n2) {
            if (this.storage[n2] == null) continue;
            this.owner.a(this.storage[n2], true);
            this.storage[n2] = null;
        }
        for (n2 = 0; n2 < this.b.length; ++n2) {
            if (this.b[n2] == null) continue;
            this.owner.a(this.b[n2], true);
            this.b[n2] = null;
        }
    }

    @Override
    public void h() {
        this.f = true;
    }

    public boolean a(Inventory fo2) {
        int n2;
        for (n2 = 0; n2 < this.storage.length; ++n2) {
            if (this.a(fo2.storage[n2], this.storage[n2])) continue;
            return false;
        }
        for (n2 = 0; n2 < this.b.length; ++n2) {
            if (this.a(fo2.b[n2], this.b[n2])) continue;
            return false;
        }
        for (n2 = 0; n2 < this.c.length; ++n2) {
            if (this.a(fo2.c[n2], this.c[n2])) continue;
            return false;
        }
        return true;
    }

    private boolean a(InventoryItem fp2, InventoryItem fp3) {
        if (fp2 == null && fp3 == null) {
            return true;
        }
        if (fp2 == null || fp3 == null) {
            return false;
        }
        return fp2.id == fp3.id && fp2.count == fp3.count && fp2.damage == fp3.damage;
    }

    public Inventory i() {
        int n2;
        Inventory fo2 = new Inventory(null);
        for (n2 = 0; n2 < this.storage.length; ++n2) {
            fo2.storage[n2] = this.storage[n2] != null ? this.storage[n2].e() : null;
        }
        for (n2 = 0; n2 < this.b.length; ++n2) {
            fo2.b[n2] = this.b[n2] != null ? this.b[n2].e() : null;
        }
        for (n2 = 0; n2 < this.c.length; ++n2) {
            fo2.c[n2] = this.c[n2] != null ? this.c[n2].e() : null;
        }
        return fo2;
    }
}

