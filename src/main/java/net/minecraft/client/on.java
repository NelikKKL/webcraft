/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class on {
    public final int a;
    public final int b;
    public final int c;

    public on(int n2, int n3, int n4) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
    }

    public boolean equals(Object object) {
        if (object instanceof on) {
            on on2 = (on)object;
            return on2.a == this.a && on2.b == this.b && on2.c == this.c;
        }
        return false;
    }

    public int hashCode() {
        return this.a * 8976890 + this.b * 981131 + this.c;
    }
}

