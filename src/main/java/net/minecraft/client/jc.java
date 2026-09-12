/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class jc
extends ex {
    public fn j = new fn();

    public jc(Inventory fo2) {
        int n2;
        int n3;
        this.i.add(new ar(this, this.j.a, this.j.b, 0, 124, 35));
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 3; ++n2) {
                this.i.add(new of(this, this.j.a, n2 + n3 * 3, 30 + n2 * 18, 17 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.i.add(new of(this, fo2, n2 + (n3 + 1) * 9, 8 + n2 * 18, 84 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.i.add(new of(this, fo2, n3, 8 + n3 * 18, 142));
        }
    }

    @Override
    public void h() {
        super.h();
        this.j.a(this.b.g);
    }

    @Override
    protected void j() {
        this.g.b("Crafting", 28, 6, 0x404040);
        this.g.b("Inventory", 8, this.h - 96 + 2, 0x404040);
    }

    @Override
    protected void a(float f2) {
        int n2 = this.b.n.a("/gui/crafting.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.b.n.b(n2);
        int n3 = (this.c - this.a) / 2;
        int n4 = (this.d - this.h) / 2;
        this.b(n3, n4, 0, 0, this.a, this.h);
    }
}

