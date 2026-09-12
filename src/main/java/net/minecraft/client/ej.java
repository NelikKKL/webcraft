/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ej
extends ok {
    protected ej(int n2, int n3) {
        super(n2, n3);
        float f2 = 0.4f;
        this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, f2 * 2.0f, 0.5f + f2);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        super.a(cy2, n2, n3, n4, random);
        if (cy2.j(n2, n3 + 1, n4) >= 9 && random.nextInt(5) == 0) {
            int n5 = cy2.e(n2, n3, n4);
            if (n5 < 15) {
                cy2.b(n2, n3, n4, n5 + 1);
            } else {
                cy2.a(n2, n3, n4, 0);
                js js2 = new qa();
                if (random.nextInt(10) == 0) {
                    js2 = new fe();
                }
                if (!((js)js2).a(cy2, random, n2, n3, n4)) {
                    cy2.a(n2, n3, n4, this.bh);
                }
            }
        }
    }
}

