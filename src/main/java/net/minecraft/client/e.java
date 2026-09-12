/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class e
extends Block {
    public e(int n2, int n3) {
        super(n2, hb.e);
        this.bg = n3;
    }

    @Override
    public int a(int n2) {
        return this.bg - 16;
    }
}

