/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ir
extends ld {
    protected ir(int n2, hb hb2) {
        super(n2, hb2);
        this.b(false);
        if (hb2 == hb.g) {
            this.b(true);
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        super.a(cy2, n2, n3, n4, n5);
        if (cy2.a(n2, n3, n4) == this.bh) {
            this.j(cy2, n2, n3, n4);
        }
    }

    private void j(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        cy2.i = true;
        cy2.a(n2, n3, n4, this.bh - 1, n5);
        cy2.b(n2, n3, n4, n2, n3, n4);
        cy2.h(n2, n3, n4, this.bh - 1);
        cy2.i = false;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        if (this.bs == hb.g) {
            int n5 = random.nextInt(3);
            for (int i2 = 0; i2 < n5; ++i2) {
                int n6 = cy2.a(n2 += random.nextInt(3) - 1, ++n3, n4 += random.nextInt(3) - 1);
                if (n6 == 0) {
                    if (!this.k(cy2, n2 - 1, n3, n4) && !this.k(cy2, n2 + 1, n3, n4) && !this.k(cy2, n2, n3, n4 - 1) && !this.k(cy2, n2, n3, n4 + 1) && !this.k(cy2, n2, n3 - 1, n4) && !this.k(cy2, n2, n3 + 1, n4)) continue;
                    cy2.d(n2, n3, n4, Block.ar.bh);
                    return;
                }
                if (!Block.registry[n6].bs.c()) continue;
                return;
            }
        }
    }

    private boolean k(Session cy2, int n2, int n3, int n4) {
        return cy2.f(n2, n3, n4).e();
    }
}

