/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class jj
extends ex {
    private Furnace j;

    public jj(Inventory fo2, Furnace lt2) {
        int n2;
        this.j = lt2;
        this.i.add(new of(this, lt2, 0, 56, 17));
        this.i.add(new of(this, lt2, 1, 56, 53));
        this.i.add(new of(this, lt2, 2, 116, 35));
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.i.add(new of(this, fo2, i2 + (n2 + 1) * 9, 8 + i2 * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.i.add(new of(this, fo2, n2, 8 + n2 * 18, 142));
        }
    }

    @Override
    protected void j() {
        this.g.b("Furnace", 60, 6, 0x404040);
        this.g.b("Inventory", 8, this.h - 96 + 2, 0x404040);
    }

    @Override
    protected void a(float f2) {
        int n2;
        int n3 = this.b.n.a("/gui/furnace.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.b.n.b(n3);
        int n4 = (this.c - this.a) / 2;
        int n5 = (this.d - this.h) / 2;
        this.b(n4, n5, 0, 0, this.a, this.h);
        if (this.j.a()) {
            n2 = this.j.b(12);
            this.b(n4 + 56, n5 + 36 + 12 - n2, 176, 12 - n2, 14, n2 + 2);
        }
        n2 = this.j.a(24);
        this.b(n4 + 79, n5 + 34, 176, 14, n2 + 1, 16);
    }
}

