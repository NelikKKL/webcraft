/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import net.minecraft.client.Minecraft;

public class le
extends bp {
    protected bp a;
    protected String h = "Select world";
    private boolean i = false;

    public le(bp bp2) {
        this.a = bp2;
    }

    @Override
    public void a() {
        File file = Minecraft.b();
        for (int i2 = 0; i2 < 5; ++i2) {
            NBTCompoundTag iq2 = Session.a(file, "World" + (i2 + 1));
            if (iq2 == null) {
                this.e.add(new gh(i2, this.c / 2 - 100, this.d / 6 + 24 * i2, "- empty -"));
                continue;
            }
            String string = "World " + (i2 + 1);
            long l2 = iq2.getLong("SizeOnDisk");
            string = string + " (" + (float)(l2 / 1024L * 100L / 1024L) / 100.0f + " MB)";
            this.e.add(new gh(i2, this.c / 2 - 100, this.d / 6 + 24 * i2, string));
        }
        this.j();
    }

    protected String d(int n2) {
        File file = Minecraft.b();
        return Session.a(file, "World" + n2) != null ? "World" + n2 : null;
    }

    public void j() {
        this.e.add(new gh(5, this.c / 2 - 100, this.d / 6 + 120 + 12, "Delete world..."));
        this.e.add(new gh(6, this.c / 2 - 100, this.d / 6 + 168, "Cancel"));
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) {
            return;
        }
        if (gh2.f < 5) {
            this.c(gh2.f + 1);
        } else if (gh2.f == 5) {
            this.b.a(new jh(this));
        } else if (gh2.f == 6) {
            this.b.a(this.a);
        }
    }

    public void c(int n2) {
        this.b.a((bp)null);
        if (this.i) {
            return;
        }
        this.i = true;
        this.b.b = new jg(this.b);
        this.b.b("World" + n2);
        this.b.a((bp)null);
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        this.a(this.g, this.h, this.c / 2, 20, 0xFFFFFF);
        super.a(n2, n3, f2);
    }
}

