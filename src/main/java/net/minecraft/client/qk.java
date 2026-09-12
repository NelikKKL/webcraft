/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class qk
extends Item {
    private int a;

    public qk(int n2, int n3) {
        super(n2);
        this.a = n3;
        this.aX = 1;
    }

    @Override
    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        --fp2.count;
        eb2.c(this.a);
        return fp2;
    }
}

