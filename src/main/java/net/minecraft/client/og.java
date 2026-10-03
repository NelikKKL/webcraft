/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;

public class og
extends bp {
    private ib a;
    private boolean h = false;

    public og(Minecraft minecraft, String string, int n2) {
        minecraft.a((Session)null);
        new oy(this, minecraft, string, n2).start();
    }

    @Override
    public void g() {
        if (this.a != null) {
            this.a.a();
        }
    }

    @Override
    protected void a(char c2, int n2) {
    }

    @Override
    public void a() {
        this.e.clear();
        this.e.add(new gh(0, this.c / 2 - 100, this.d / 4 + 120 + 12, "Cancel"));
    }

    @Override
    protected void a(gh gh2) {
        if (gh2.f == 0) {
            this.h = true;
            if (this.a != null) {
                this.a.b();
            }
            this.b.a(new dj());
        }
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        if (this.a == null) {
            this.a(this.g, "Connecting to the server...", this.c / 2, this.d / 2 - 50, 0xFFFFFF);
            this.a(this.g, "", this.c / 2, this.d / 2 - 10, 0xFFFFFF);
        } else {
            this.a(this.g, "Logging in...", this.c / 2, this.d / 2 - 50, 0xFFFFFF);
            this.a(this.g, this.a.a, this.c / 2, this.d / 2 - 10, 0xFFFFFF);
        }
        super.a(n2, n3, f2);
    }

    static ib a(og og2, ib ib2) {
        og2.a = ib2;
        return og2.a;
    }

    static boolean a(og og2) {
        return og2.h;
    }

    static ib b(og og2) {
        return og2.a;
    }
}

