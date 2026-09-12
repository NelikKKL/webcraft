/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class ix
implements hi {
    private String a;
    private hi b;
    private hi c;

    public ix(String string, hi hi2, hi hi3) {
        this.a = string;
        this.b = hi2;
        this.c = hi3;
    }

    @Override
    public int c() {
        return this.b.c() + this.c.c();
    }

    @Override
    public String d() {
        return this.a;
    }

    @Override
    public InventoryItem c(int n2) {
        if (n2 >= this.b.c()) {
            return this.c.c(n2 - this.b.c());
        }
        return this.b.c(n2);
    }

    @Override
    public InventoryItem a(int n2, int n3) {
        if (n2 >= this.b.c()) {
            return this.c.a(n2 - this.b.c(), n3);
        }
        return this.b.a(n2, n3);
    }

    @Override
    public void a(int n2, InventoryItem fp2) {
        if (n2 >= this.b.c()) {
            this.c.a(n2 - this.b.c(), fp2);
        } else {
            this.b.a(n2, fp2);
        }
    }

    @Override
    public int o_() {
        return this.b.o_();
    }

    @Override
    public void h() {
        this.b.h();
        this.c.h();
    }
}

