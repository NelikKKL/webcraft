/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class MobSpawner
extends TileEntityRegistry {
    public int a = 20;
    public String b = "Pig";
    public double c;
    public double d = 0.0;

    public boolean a() {
        return this.e.a((double)this.f + 0.5, (double)this.g + 0.5, (double)this.h + 0.5, 16.0) != null;
    }

    @Override
    public void b() {
        this.d = this.c;
        if (!this.a()) {
            return;
        }
        double d2 = (float)this.f + this.e.l.nextFloat();
        double d3 = (float)this.g + this.e.l.nextFloat();
        double d4 = (float)this.h + this.e.l.nextFloat();
        this.e.a("smoke", d2, d3, d4, 0.0, 0.0, 0.0);
        this.e.a("flame", d2, d3, d4, 0.0, 0.0, 0.0);
        this.c += (double)(1000.0f / ((float)this.a + 200.0f));
        while (this.c > 360.0) {
            this.c -= 360.0;
            this.d -= 360.0;
        }
        if (this.a == -1) {
            this.c();
        }
        if (this.a > 0) {
            --this.a;
            return;
        }
        int n2 = 4;
        for (int i2 = 0; i2 < n2; ++i2) {
            Mob hf2 = (Mob)EntityRegistry.a(this.b, this.e);
            if (hf2 == null) {
                return;
            }
            int n3 = this.e.a(hf2.getClass(), co.b(this.f, this.g, this.h, this.f + 1, this.g + 1, this.h + 1).b(8.0, 4.0, 8.0)).size();
            if (n3 >= 6) {
                this.c();
                return;
            }
            if (hf2 == null) continue;
            double d5 = (double)this.f + (this.e.l.nextDouble() - this.e.l.nextDouble()) * 4.0;
            double d6 = this.g + this.e.l.nextInt(3) - 1;
            double d7 = (double)this.h + (this.e.l.nextDouble() - this.e.l.nextDouble()) * 4.0;
            hf2.c(d5, d6, d7, this.e.l.nextFloat() * 360.0f, 0.0f);
            if (!hf2.a()) continue;
            this.e.a(hf2);
            for (int i3 = 0; i3 < 20; ++i3) {
                d2 = (double)this.f + 0.5 + ((double)this.e.l.nextFloat() - 0.5) * 2.0;
                d3 = (double)this.g + 0.5 + ((double)this.e.l.nextFloat() - 0.5) * 2.0;
                d4 = (double)this.h + 0.5 + ((double)this.e.l.nextFloat() - 0.5) * 2.0;
                this.e.a("smoke", d2, d3, d4, 0.0, 0.0, 0.0);
                this.e.a("flame", d2, d3, d4, 0.0, 0.0, 0.0);
            }
            hf2.C();
            this.c();
        }
        super.b();
    }

    private void c() {
        this.a = 200 + this.e.l.nextInt(600);
    }

    @Override
    public void a(NBTCompoundTag iq2) {
        super.a(iq2);
        this.b = iq2.getString("EntityId");
        this.a = iq2.getShort("Delay");
    }

    @Override
    public void b(NBTCompoundTag iq2) {
        super.b(iq2);
        iq2.storeString("EntityId", this.b);
        iq2.storeShort("Delay", (short)this.a);
    }
}

