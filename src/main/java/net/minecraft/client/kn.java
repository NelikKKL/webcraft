/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class kn
extends bp {
    private bp a;
    private String h;
    private String i;
    private int j;

    public kn(bp bp2, String string, String string2, int n2) {
        this.a = bp2;
        this.h = string;
        this.i = string2;
        this.j = n2;
    }

    @Override
    public void a() {
        this.e.add(new r(0, this.c / 2 - 155 + 0, this.d / 6 + 96, "Yes"));
        this.e.add(new r(1, this.c / 2 - 155 + 160, this.d / 6 + 96, "No"));
    }

    @Override
    protected void a(gh gh2) {
        this.a.a(gh2.f == 0, this.j);
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        this.a(this.g, this.h, this.c / 2, 70, 0xFFFFFF);
        this.a(this.g, this.i, this.c / 2, 90, 0xFFFFFF);
        super.a(n2, n3, f2);
    }
}

