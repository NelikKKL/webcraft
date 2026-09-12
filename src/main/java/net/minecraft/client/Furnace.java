/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class Furnace
extends TileEntityRegistry
implements hi {
    private InventoryItem[] a = new InventoryItem[3];
    private int burnTime = 0;
    private int c = 0;
    private int cookTime = 0;

    @Override
    public int c() {
        return this.a.length;
    }

    @Override
    public InventoryItem c(int n2) {
        return this.a[n2];
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        if (this.a[n2] != null) {
            if (this.a[n2].count <= n3) {
                InventoryItem fp2 = this.a[n2];
                this.a[n2] = null;
                return fp2;
            }
            InventoryItem fp3 = this.a[n2].a(n3);
            if (this.a[n2].count == 0) {
                this.a[n2] = null;
            }
            return fp3;
        }
        return null;
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        this.a[n2] = fp2;
        if (fp2 != null && fp2.count > this.o_()) {
            fp2.count = this.o_();
        }
    }

    @Override
    public String d() {
        return "Chest";
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        NBTListTag ly2 = iq2.getListTag("Items");
        this.a = new InventoryItem[this.c()];
        for (int i2 = 0; i2 < ly2.c(); ++i2) {
            NBTCompoundTag iq3 = (NBTCompoundTag)ly2.a(i2);
            byte by2 = iq3.getByte("Slot");
            if (by2 < 0 || by2 >= this.a.length) continue;
            this.a[by2] = new InventoryItem(iq3);
        }
        this.burnTime = iq2.getShort("BurnTime");
        this.cookTime = iq2.getShort("CookTime");
        this.c = this.a(this.a[1]);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        iq2.storeShort("BurnTime", (short)this.burnTime);
        iq2.storeShort("CookTime", (short)this.cookTime);
        NBTListTag ly2 = new NBTListTag();
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (this.a[i2] == null) continue;
            NBTCompoundTag iq3 = new NBTCompoundTag();
            iq3.storeByte("Slot", (byte)i2);
            this.a[i2].a(iq3);
            ly2.a(iq3);
        }
        iq2.storeTag("Items", ly2);
    }

    @Override
    public int o_() {
        return 64;
    }

    public int a(int n2) {
        return this.cookTime * n2 / 200;
    }

    public int b(int n2) {
        if (this.c == 0) {
            this.c = 200;
        }
        return this.burnTime * n2 / this.c;
    }

    public boolean a() {
        return this.burnTime > 0;
    }

    @Override
    public void b() {
        boolean bl2 = this.burnTime > 0;
        boolean bl3 = false;
        if (this.burnTime > 0) {
            --this.burnTime;
        }
        if (!this.e.z) {
            if (this.burnTime == 0 && this.j()) {
                this.c = this.burnTime = this.a(this.a[1]);
                if (this.burnTime > 0) {
                    bl3 = true;
                    if (this.a[1] != null) {
                        --this.a[1].count;
                        if (this.a[1].count == 0) {
                            this.a[1] = null;
                        }
                    }
                }
            }
            if (this.a() && this.j()) {
                ++this.cookTime;
                if (this.cookTime == 200) {
                    this.cookTime = 0;
                    this.i();
                    bl3 = true;
                }
            } else {
                this.cookTime = 0;
            }
            if (bl2 != this.burnTime > 0) {
                bl3 = true;
                mj.a(this.burnTime > 0, this.e, this.f, this.g, this.h);
            }
        }
        if (bl3) {
            this.h();
        }
    }

    private boolean j() {
        if (this.a[0] == null) {
            return false;
        }
        int n2 = this.d(this.a[0].a().id);
        if (n2 < 0) {
            return false;
        }
        if (this.a[2] == null) {
            return true;
        }
        if (this.a[2].id != n2) {
            return false;
        }
        if (this.a[2].count < this.o_() && this.a[2].count < this.a[2].c()) {
            return true;
        }
        return this.a[2].count < Item.registry[n2].c();
    }

    public void i() {
        if (!this.j()) {
            return;
        }
        int n2 = this.d(this.a[0].a().id);
        if (this.a[2] == null) {
            this.a[2] = new InventoryItem(n2, 1);
        } else if (this.a[2].id == n2) {
            ++this.a[2].count;
        }
        --this.a[0].count;
        if (this.a[0].count <= 0) {
            this.a[0] = null;
        }
    }

    private int d(int n2) {
        if (n2 == Block.H.bh) {
            return Item.m.id;
        }
        if (n2 == Block.G.bh) {
            return Item.n.id;
        }
        if (n2 == Block.aw.bh) {
            return Item.l.id;
        }
        if (n2 == Block.E.bh) {
            return Block.M.bh;
        }
        if (n2 == Item.ao.id) {
            return Item.ap.id;
        }
        if (n2 == Item.aS.id) {
            return Item.aT.id;
        }
        if (n2 == Block.w.bh) {
            return Block.t.bh;
        }
        if (n2 == Item.aG.id) {
            return Item.aF.id;
        }
        return -1;
    }

    private int a(InventoryItem fp2) {
        if (fp2 == null) {
            return 0;
        }
        int n2 = fp2.a().id;
        if (n2 < 256 && Block.registry[n2].bs == hb.c) {
            return 300;
        }
        if (n2 == Item.B.id) {
            return 100;
        }
        if (n2 == Item.k.id) {
            return 1600;
        }
        if (n2 == Item.aw.id) {
            return 20000;
        }
        return 0;
    }
}

