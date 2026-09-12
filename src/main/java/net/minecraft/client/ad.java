/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class ad {
    public byte[] a = new byte[1024];
    public int b;
    public boolean c = false;
    public int d = 0;
    public int e = 1;
    public int f = 0;

    public ad(int n2) {
        this.b = n2;
    }

    public void a() {
    }

    public void a(fu fu2) {
        if (this.f == 0) {
            GL11.glBindTexture(3553, (int)fu2.a("/terrain.png"));
        } else if (this.f == 1) {
            GL11.glBindTexture(3553, (int)fu2.a("/gui/items.png"));
        }
    }
}

