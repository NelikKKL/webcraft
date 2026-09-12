/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class dz {
    public final int a;
    public final hi b;

    public dz(hi hi2, int n2) {
        this.b = hi2;
        this.a = n2;
    }

    public void a() {
        this.d();
    }

    public boolean a(InventoryItem fp2) {
        return true;
    }

    public InventoryItem b() {
        return this.b.c(this.a);
    }

    public void b(InventoryItem fp2) {
        this.b.a(this.a, fp2);
        this.d();
    }

    public int c() {
        return -1;
    }

    public void d() {
        this.b.h();
    }

    public int e() {
        return this.b.o_();
    }
}

