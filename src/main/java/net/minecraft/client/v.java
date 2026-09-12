/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class v
extends Block {
    public v(int n2, int n3) {
        super(n2, n3, hb.p);
    }

    @Override
    public int a(int n2) {
        if (n2 == 0) {
            return this.bg + 2;
        }
        if (n2 == 1) {
            return this.bg + 1;
        }
        return this.bg;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (n5 > 0 && Block.registry[n5].e() && cy2.o(n2, n3, n4)) {
            this.b(cy2, n2, n3, n4, 0);
            cy2.d(n2, n3, n4, 0);
        }
    }

    @Override
    public int a(Random random) {
        return 0;
    }

    @Override
    public void c(Session cy2, int n2, int n3, int n4) {
        PrimedTNT kr2 = new PrimedTNT(cy2, (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f);
        kr2.a = cy2.l.nextInt(kr2.a / 4) + kr2.a / 8;
        cy2.a(kr2);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, int n5) {
        if (cy2.z) {
            return;
        }
        PrimedTNT kr2 = new PrimedTNT(cy2, (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f);
        cy2.a(kr2);
        cy2.a(kr2, "random.fuse", 1.0f, 1.0f);
    }
}

