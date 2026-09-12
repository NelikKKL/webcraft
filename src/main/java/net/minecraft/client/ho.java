/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Comparator;

public class ho
implements Comparator {
    private Player a;

    public ho(Player eb2) {
        this.a = eb2;
    }

    public int a(bw bw2, bw bw3) {
        double d2;
        boolean bl2 = bw2.o;
        boolean bl3 = bw3.o;
        if (bl2 && !bl3) {
            return 1;
        }
        if (bl3 && !bl2) {
            return -1;
        }
        double d3 = bw2.a(this.a);
        if (d3 < (d2 = (double)bw3.a(this.a))) {
            return 1;
        }
        if (d3 > d2) {
            return -1;
        }
        return bw2.w < bw3.w ? 1 : -1;
    }

    public int compare(Object object, Object object2) {
        return this.a((bw)object, (bw)object2);
    }
}

