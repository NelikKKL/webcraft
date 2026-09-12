/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.List;

public class PigZombie
extends Zombie {
    private int a = 0;
    private int b = 0;
    static private final InventoryItem c = new InventoryItem(Item.E, 1);

    public PigZombie(Session cy2) {
        super(cy2);
        this.z = "/mob/pigzombie.png";
        this.am = 0.5f;
        this.f = 5;
        this.bm = true;
    }

    @Override
    public void e_() {
        float f2 = this.am = this.g != null ? 0.95f : 0.5f;
        if (this.b > 0 && --this.b == 0) {
            this.as.a(this, "mob.zombiepig.zpigangry", this.h() * 2.0f, ((this.bd.nextFloat() - this.bd.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.e_();
    }

    @Override
    public boolean a() {
        return this.as.k > 0 && this.as.a(this.aG) && this.as.a((lw)this, this.aG).size() == 0 && !this.as.b(this.aG);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        iq2.storeShort("Anger", (short)this.a);
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        this.a = iq2.getShort("Anger");
    }

    @Override
    protected lw c_() {
        if (this.a == 0) {
            return null;
        }
        return super.c_();
    }

    @Override
    public void k() {
        super.k();
    }

    @Override
    public boolean a(lw lw2, int n2) {
        if (lw2 instanceof Player) {
            List list = this.as.b(this, this.aG.b(32.0, 32.0, 32.0));
            for (int i2 = 0; i2 < list.size(); ++i2) {
                lw lw3 = (lw)list.get(i2);
                if (!(lw3 instanceof PigZombie)) continue;
                PigZombie pt2 = (PigZombie)lw3;
                pt2.c(lw2);
            }
            this.c(lw2);
        }
        return super.a(lw2, n2);
    }

    private void c(lw lw2) {
        this.g = lw2;
        this.a = 400 + this.bd.nextInt(400);
        this.b = this.bd.nextInt(40);
    }

    @Override
    protected String d() {
        return "mob.zombiepig.zpig";
    }

    @Override
    protected String f_() {
        return "mob.zombiepig.zpighurt";
    }

    @Override
    protected String soundOnDeath() {
        return "mob.zombiepig.zpigdeath";
    }

    @Override
    protected int g_() {
        return Item.ap.id;
    }

    @Override
    public InventoryItem l() {
        return c;
    }
}

