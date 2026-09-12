/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class oj
extends Item {
    static private final int[] be = new int[]{3, 8, 6, 3};
    static private final int[] bf = new int[]{11, 16, 15, 13};
    public final int a;
    public final int bb;
    public final int bc;
    public final int bd;

    public oj(int n2, int n3, int n4, int n5) {
        super(n2);
        this.a = n3;
        this.bb = n5;
        this.bd = n4;
        this.bc = be[n5];
        this.aY = bf[n5] * 3 << n3;
        this.aX = 1;
    }
}

