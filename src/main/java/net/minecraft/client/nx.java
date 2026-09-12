/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class nx {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public Vector3D f;
    public lw g;

    public nx(int n2, int n3, int n4, int n5, Vector3D ao2) {
        this.a = 0;
        this.b = n2;
        this.c = n3;
        this.d = n4;
        this.e = n5;
        this.f = ao2.b(ao2.x, ao2.y, ao2.z);
    }

    public nx(lw lw2) {
        this.a = 1;
        this.g = lw2;
        this.f = Vector3D.b(lw2.aw, lw2.ax, lw2.ay);
    }
}

