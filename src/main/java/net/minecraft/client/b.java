/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class b {
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    int e = -1;
    float f;
    float g;
    float h;
    b i;
    public boolean j = false;

    public b(int n2, int n3, int n4) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n2 | n3 << 10 | n4 << 20;
    }

    public float a(b b2) {
        float f2 = b2.a - this.a;
        float f3 = b2.b - this.b;
        float f4 = b2.c - this.c;
        return TrigLookup.c(f2 * f2 + f3 * f3 + f4 * f4);
    }

    public boolean equals(Object object) {
        return ((b)object).d == this.d;
    }

    public int hashCode() {
        return this.d;
    }

    public boolean a() {
        return this.e >= 0;
    }

    public String toString() {
        return this.a + ", " + this.b + ", " + this.c;
    }
}

