/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.BufferedImage;

public class kw {
    public BufferedImage a;
    public Session b;
    public int c;
    public int d;
    public boolean e = false;
    public boolean f = false;
    public int g = 0;
    public boolean h = false;

    public kw(Session cy2, int n2, int n3) {
        this.b = cy2;
        this.a(n2, n3);
    }

    public void a(int n2, int n3) {
        this.e = false;
        this.c = n2;
        this.d = n3;
        this.g = 0;
        this.h = false;
    }

    public void a(Session cy2, int n2, int n3) {
        this.b = cy2;
        this.a(n2, n3);
    }
}

