/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class dl {
    protected int a = 8;
    protected Random b = new Random();

    public void a(bd bd2, Session cy2, int n2, int n3, byte[] byArray) {
        int n4 = this.a;
        this.b.setSeed(cy2.u);
        long l2 = this.b.nextLong() / 2L * 2L + 1L;
        long l3 = this.b.nextLong() / 2L * 2L + 1L;
        for (int i2 = n2 - n4; i2 <= n2 + n4; ++i2) {
            for (int i3 = n3 - n4; i3 <= n3 + n4; ++i3) {
                this.b.setSeed((long)i2 * l2 + (long)i3 * l3 ^ cy2.u);
                this.a(cy2, i2, i3, n2, n3, byArray);
            }
        }
    }

    protected void a(Session cy2, int n2, int n3, int n4, int n5, byte[] byArray) {
    }
}

