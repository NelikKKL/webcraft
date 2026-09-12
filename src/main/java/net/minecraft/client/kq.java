/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class kq
implements Comparable {
    static private long f = 0L;
    public int a;
    public int b;
    public int c;
    public int d;
    public long e;
    private long g = f++;

    public kq(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public boolean equals(Object object) {
        if (object instanceof kq) {
            kq kq2 = (kq)object;
            return this.a == kq2.a && this.b == kq2.b && this.c == kq2.c && this.d == kq2.d;
        }
        return false;
    }

    public int hashCode() {
        return (this.a * 128 * 1024 + this.c * 128 + this.b) * 256 + this.d;
    }

    public kq a(long l2) {
        this.e = l2;
        return this;
    }

    public int a(kq kq2) {
        if (this.e < kq2.e) {
            return -1;
        }
        if (this.e > kq2.e) {
            return 1;
        }
        if (this.g < kq2.g) {
            return -1;
        }
        if (this.g > kq2.g) {
            return 1;
        }
        return 0;
    }

    public int compareTo(Object object) {
        return this.a((kq)object);
    }
}

