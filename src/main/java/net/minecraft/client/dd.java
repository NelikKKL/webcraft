/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class dd
extends Block {
    protected dd(int n2) {
        super(n2, hb.c);
        this.bg = 59;
    }

    @Override
    public int a(int n2) {
        if (n2 == 1) {
            return this.bg - 16;
        }
        if (n2 == 0) {
            return Block.x.a(0);
        }
        if (n2 == 2 || n2 == 4) {
            return this.bg + 1;
        }
        return this.bg;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        eb2.m();
        return true;
    }
}

