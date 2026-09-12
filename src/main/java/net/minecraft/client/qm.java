/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class qm {
    public int a;
    public int b;

    public qm(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public int hashCode() {
        return this.a << 8 | this.b;
    }

    public boolean equals(Object object) {
        qm qm2 = (qm)object;
        return qm2.a == this.a && qm2.b == this.b;
    }
}

