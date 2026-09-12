/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class gw {
    private b[] a = new b[1024];
    private int b = 0;

    public b a(b b2) {
        if (b2.e >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.b == this.a.length) {
            b[] bArray = new b[this.b << 1];
            System.arraycopy(this.a, 0, bArray, 0, this.b);
            this.a = bArray;
        }
        this.a[this.b] = b2;
        b2.e = this.b;
        this.a(this.b++);
        return b2;
    }

    public void a() {
        this.b = 0;
    }

    public b b() {
        b b2 = this.a[0];
        this.a[0] = this.a[--this.b];
        this.a[this.b] = null;
        if (this.b > 0) {
            this.b(0);
        }
        b2.e = -1;
        return b2;
    }

    public void a(b b2, float f2) {
        float f3 = b2.h;
        b2.h = f2;
        if (f2 < f3) {
            this.a(b2.e);
        } else {
            this.b(b2.e);
        }
    }

    private void a(int n2) {
        b b2 = this.a[n2];
        float f2 = b2.h;
        while (n2 > 0) {
            int n3 = n2 - 1 >> 1;
            b b3 = this.a[n3];
            if (!(f2 < b3.h)) break;
            this.a[n2] = b3;
            b3.e = n2;
            n2 = n3;
        }
        this.a[n2] = b2;
        b2.e = n2;
    }

    private void b(int n2) {
        b b2 = this.a[n2];
        float f2 = b2.h;
        while (true) {
            float f3;
            b b3;
            int n3 = 1 + (n2 << 1);
            int n4 = n3 + 1;
            if (n3 >= this.b) break;
            b b4 = this.a[n3];
            float f4 = b4.h;
            if (n4 >= this.b) {
                b3 = null;
                f3 = Float.POSITIVE_INFINITY;
            } else {
                b3 = this.a[n4];
                f3 = b3.h;
            }
            if (f4 < f3) {
                if (!(f4 < f2)) break;
                this.a[n2] = b4;
                b4.e = n2;
                n2 = n3;
                continue;
            }
            if (!(f3 < f2)) break;
            this.a[n2] = b3;
            b3.e = n2;
            n2 = n4;
        }
        this.a[n2] = b2;
        b2.e = n2;
    }

    public boolean c() {
        return this.b == 0;
    }
}

