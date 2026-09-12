/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;

public class Painting
extends lw {
    private int c = 0;
    public int a = 0;
    private int d;
    private int e;
    private int f;
    public Paintings b;

    public Painting(Session cy2) {
        super(cy2);
        this.aO = 0.0f;
        this.a(0.5f, 0.5f);
    }

    public Painting(Session cy2, int n2, int n3, int n4, int n5) {
        this(cy2);
        this.d = n2;
        this.e = n3;
        this.f = n4;
        ArrayList<Paintings> arrayList = new ArrayList<Paintings>();
        Paintings[] flArray = Paintings.values();
        int n6 = flArray.length;
        for (int i2 = 0; i2 < n6; ++i2) {
            Paintings fl2;
            this.b = fl2 = flArray[i2];
            this.b(n5);
            if (!this.k()) continue;
            arrayList.add(fl2);
        }
        if (arrayList.size() > 0) {
            this.b = (Paintings)((Object)arrayList.get(this.bd.nextInt(arrayList.size())));
        }
        this.b(n5);
    }

    public void b(int n2) {
        this.a = n2;
        this.aE = this.aC = (float)(n2 * 90);
        float f2 = this.b.width;
        float f3 = this.b.height;
        float f4 = this.b.width;
        if (n2 == 0 || n2 == 2) {
            f4 = 0.5f;
        } else {
            f2 = 0.5f;
        }
        f2 /= 32.0f;
        f3 /= 32.0f;
        f4 /= 32.0f;
        float f5 = (float)this.d + 0.5f;
        float f6 = (float)this.e + 0.5f;
        float f7 = (float)this.f + 0.5f;
        float f8 = 0.5625f;
        if (n2 == 0) {
            f7 -= f8;
        }
        if (n2 == 1) {
            f5 -= f8;
        }
        if (n2 == 2) {
            f7 += f8;
        }
        if (n2 == 3) {
            f5 += f8;
        }
        if (n2 == 0) {
            f5 -= this.c(this.b.width);
        }
        if (n2 == 1) {
            f7 += this.c(this.b.width);
        }
        if (n2 == 2) {
            f5 += this.c(this.b.width);
        }
        if (n2 == 3) {
            f7 -= this.c(this.b.width);
        }
        this.b(f5, f6 += this.c(this.b.height), f7);
        float f9 = -0.00625f;
        this.aG.c(f5 - f2 - f9, f6 - f3 - f9, f7 - f4 - f9, f5 + f2 + f9, f6 + f3 + f9, f7 + f4 + f9);
    }

    private float c(int n2) {
        if (n2 == 32) {
            return 0.5f;
        }
        if (n2 == 64) {
            return 0.5f;
        }
        return 0.0f;
    }

    @Override
    public void e_() {
        if (this.c++ == 100 && !this.k()) {
            this.c = 0;
            this.J();
            this.as.a(new DroppedItem(this.as, this.aw, this.ax, this.ay, new InventoryItem(Item.aq)));
        }
    }

    public boolean k() {
        int n2;
        if (this.as.a((lw)this, this.aG).size() > 0) {
            return false;
        }
        int n3 = this.b.width / 16;
        int n4 = this.b.height / 16;
        int n5 = this.d;
        int n6 = this.e;
        int n7 = this.f;
        if (this.a == 0) {
            n5 = TrigLookup.b(this.aw - (double)((float)this.b.width / 32.0f));
        }
        if (this.a == 1) {
            n7 = TrigLookup.b(this.ay - (double)((float)this.b.width / 32.0f));
        }
        if (this.a == 2) {
            n5 = TrigLookup.b(this.aw - (double)((float)this.b.width / 32.0f));
        }
        if (this.a == 3) {
            n7 = TrigLookup.b(this.ay - (double)((float)this.b.width / 32.0f));
        }
        n6 = TrigLookup.b(this.ax - (double)((float)this.b.height / 32.0f));
        for (int i2 = 0; i2 < n3; ++i2) {
            for (n2 = 0; n2 < n4; ++n2) {
                hb hb2 = this.a == 0 || this.a == 2 ? this.as.f(n5 + i2, n6 + n2, this.f) : this.as.f(this.d, n6 + n2, n7 + i2);
                if (hb2.a()) continue;
                return false;
            }
        }
        List list = this.as.b(this, this.aG);
        for (n2 = 0; n2 < list.size(); ++n2) {
            if (!(list.get(n2) instanceof Painting)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean n_() {
        return true;
    }

    @Override
    public boolean a(lw lw2, int n2) {
        this.J();
        this.M();
        this.as.a(new DroppedItem(this.as, this.aw, this.ax, this.ay, new InventoryItem(Item.aq)));
        return true;
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        iq2.storeByte("Dir", (byte)this.a);
        iq2.storeString("Motive", this.b.name);
        iq2.storeInt("TileX", this.d);
        iq2.storeInt("TileY", this.e);
        iq2.storeInt("TileZ", this.f);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        this.a = iq2.getByte("Dir");
        this.d = iq2.getInt("TileX");
        this.e = iq2.getInt("TileY");
        this.f = iq2.getInt("TileZ");
        String string = iq2.getString("Motive");
        for (Paintings fl2 : Paintings.values()) {
            if (!fl2.name.equals(string)) continue;
            this.b = fl2;
        }
        if (this.b == null) {
            this.b = Paintings.Kebab;
        }
        this.b(this.a);
    }
}

