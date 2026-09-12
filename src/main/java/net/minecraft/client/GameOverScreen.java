/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class GameOverScreen
extends bp {
    @Override
    public void a() {
        this.e.clear();
        this.e.add(new gh(1, this.c / 2 - 100, this.d / 4 + 72, "Respawn"));
        this.e.add(new gh(2, this.c / 2 - 100, this.d / 4 + 96, "Title menu"));
        if (this.b.i == null) {
            ((gh)this.e.get(1)).g = false;
        }
    }

    @Override
    protected void a(char c2, int n2) {
    }

    @Override
    protected void a(gh gh2) {
        if (gh2.f == 0) {
            // empty if block
        }
        if (gh2.f == 1) {
            this.b.g.r();
            this.b.a((bp)null);
        }
        if (gh2.f == 2) {
            this.b.a((Session)null);
            this.b.a(new dj());
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.a(0, 0, this.c, this.d, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.a(this.g, "Game over!", this.c / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        this.a(this.g, "Score: &e" + this.b.g.u(), this.c / 2, 100, 0xFFFFFF);
        super.a(n2, n3, f2);
    }

    @Override
    public boolean b() {
        return false;
    }
}

