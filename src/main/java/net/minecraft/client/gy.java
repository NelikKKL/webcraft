/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public enum gy {
    monster(cz.class, 100),
    creature(Creature.class, 20);

    public final Class c;
    public final int d;

    private gy(Class var3, int var4) {
        this.c = var3;
        this.d = var4;
    }
}

