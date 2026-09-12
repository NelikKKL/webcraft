/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Comparator;

public class fx
implements Comparator {
    private lw a;

    public fx(lw lw2) {
        this.a = lw2;
    }

    public int a(bw bw2, bw bw3) {
        return bw2.a(this.a) < bw3.a(this.a) ? -1 : 1;
    }

    public int compare(Object object, Object object2) {
        return this.a((bw)object, (bw)object2);
    }
}

