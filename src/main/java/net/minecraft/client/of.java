/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class of
extends dz {
    private final ex c;
    public final int e;
    public final int f;

    public of(ex ex2, hi hi2, int n2, int n3, int n4) {
        super(hi2, n2);
        this.c = ex2;
        this.e = n3;
        this.f = n4;
    }

    public boolean a(int n2, int n3) {
        int n4 = (this.c.c - this.c.a) / 2;
        int n5 = (this.c.d - this.c.h) / 2;
        return (n2 -= n4) >= this.e - 1 && n2 < this.e + 16 + 1 && (n3 -= n5) >= this.f - 1 && n3 < this.f + 16 + 1;
    }
}

