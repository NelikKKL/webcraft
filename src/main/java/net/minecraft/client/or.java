/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class or {
    final int a;
    Object b;
    or c;
    final int d;

    or(int n2, int n3, Object object, or or2) {
        this.b = object;
        this.c = or2;
        this.a = n3;
        this.d = n2;
    }

    public final int a() {
        return this.a;
    }

    public final Object b() {
        return this.b;
    }

    public final boolean equals(Object object) {
        Object object2;
        Object object3;
        Integer n2;
        if (!(object instanceof or)) {
            return false;
        }
        or or2 = (or)object;
        Integer n3 = this.a();
        return (n3 == (n2 = Integer.valueOf(or2.a())) || n3 != null && ((Object)n3).equals(n2)) && ((object3 = this.b()) == (object2 = or2.b()) || object3 != null && object3.equals(object2));
    }

    public final int hashCode() {
        return ge.d(this.a);
    }

    public final String toString() {
        return this.a() + "=" + this.b();
    }
}

