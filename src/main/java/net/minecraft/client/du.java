/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class du
extends bp {
    private ib a;
    private int h = 0;

    public du(ib ib2) {
        this.a = ib2;
    }

    @Override
    protected void a(char c2, int n2) {
    }

    @Override
    public void a() {
        this.e.clear();
    }

    @Override
    public void g() {
        ++this.h;
        if (this.h % 20 == 0) {
            this.a.a(new hl());
        }
        if (this.a != null) {
            this.a.a();
        }
    }

    @Override
    protected void a(gh gh2) {
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.b(0);
        this.a(this.g, "Downloading terrain", this.c / 2, this.d / 2 - 50, 0xFFFFFF);
        super.a(n2, n3, f2);
    }
}

