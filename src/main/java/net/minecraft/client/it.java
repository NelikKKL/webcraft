/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class it
extends Block {
    public it(int n2, int n3) {
        super(n2, n3, hb.m);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        float f2 = 0.125f;
        return co.b(n2, n3, n4, n2 + 1, (float)(n3 + 1) - f2, n4 + 1);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, lw lw2) {
        lw2.az *= 0.4;
        lw2.aB *= 0.4;
    }
}

