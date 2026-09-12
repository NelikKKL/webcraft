/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class kg
extends Item {
    private int a;

    public kg(int n2, int n3) {
        super(n2);
        this.aX = 1;
        this.aY = 32 << n3;
        if (n3 == 3) {
            this.aY *= 4;
        }
        this.a = 4 + n3 * 2;
    }

    @Override
    public float a(InventoryItem fp2, Block nq2) {
        return 1.5f;
    }

    @Override
    public void a(InventoryItem fp2, Mob hf2) {
        fp2.b(1);
    }

    @Override
    public void a(InventoryItem fp2, int n2, int n3, int n4, int n5) {
        fp2.b(2);
    }

    @Override
    public int a(lw lw2) {
        return this.a;
    }

    @Override
    public boolean a() {
        return true;
    }
}

