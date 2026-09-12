/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import net.minecraft.client.Minecraft;

public class jh
extends le {
    public jh(bp bp2) {
        super(bp2);
        this.h = "Delete world";
    }

    @Override
    public void j() {
        this.e.add(new gh(6, this.c / 2 - 100, this.d / 6 + 168, "Cancel"));
    }

    @Override
    public void c(int n2) {
        String string = this.d(n2);
        if (string != null) {
            this.b.a(new kn(this, "Are you sure you want to delete this world?", "'" + string + "' will be lost forever!", n2));
        }
    }

    @Override
    public void a(boolean bl2, int n2) {
        if (bl2) {
            File file = Minecraft.b();
            Session.b(file, this.d(n2));
        }
        this.b.a(this.a);
    }
}

