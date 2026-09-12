/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class bt {
    private final b[] b;
    public final int a;
    private int c;

    public bt(b[] bArray) {
        this.b = bArray;
        this.a = bArray.length;
    }

    public void a() {
        ++this.c;
    }

    public boolean b() {
        return this.c >= this.b.length;
    }

    public Vector3D a(lw lw2) {
        double d2 = (double)this.b[this.c].a + (double)((int)(lw2.aP + 1.0f)) * 0.5;
        double d3 = this.b[this.c].b;
        double d4 = (double)this.b[this.c].c + (double)((int)(lw2.aP + 1.0f)) * 0.5;
        return Vector3D.b(d2, d3, d4);
    }
}

