/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class hu
implements pu {
    private String a = "";
    private Minecraft b;
    private String c = "";
    private long d = System.currentTimeMillis();
    private boolean e = false;

    public hu(Minecraft minecraft) {
        this.b = minecraft;
    }

    public void a(String string) {
        this.e = false;
        this.c(string);
    }

    @Override
    public void b(String string) {
        this.e = true;
        this.c(this.c);
    }

    public void c(String string) {
        if (!this.b.H) {
            if (this.e) {
                return;
            }
            throw new pq();
        }
        this.c = string;
        kl kl2 = new kl(this.b.c, this.b.d);
        int n2 = kl2.a();
        int n3 = kl2.b();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)n2, (double)n3, 0.0, 100.0, 300.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -200.0f);
    }

    @Override
    public void d(String string) {
        if (!this.b.H) {
            if (this.e) {
                return;
            }
            throw new pq();
        }
        this.d = 0L;
        this.a = string;
        this.a(-1);
        this.d = 0L;
    }

    @Override
    public void a(int n2) {
        if (!this.b.H) {
            if (this.e) {
                return;
            }
            throw new pq();
        }
        long l2 = System.currentTimeMillis();
        if (l2 - this.d < 20L) {
            return;
        }
        this.d = l2;
        kl kl2 = new kl(this.b.c, this.b.d);
        int n3 = kl2.a();
        int n4 = kl2.b();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)n3, (double)n4, 0.0, 100.0, 300.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -200.0f);
        GL11.glClear(16640);
        is is2 = is.a;
        int n5 = this.b.n.a("/gui/background.png");
        GL11.glBindTexture(3553, (int)n5);
        float f2 = 32.0f;
        is2.b();
        is2.b(0x404040);
        is2.a(0.0, n4, 0.0, 0.0, (float)n4 / f2);
        is2.a(n3, n4, 0.0, (float)n3 / f2, (float)n4 / f2);
        is2.a(n3, 0.0, 0.0, (float)n3 / f2, 0.0);
        is2.a(0.0, 0.0, 0.0, 0.0, 0.0);
        is2.a();
        if (n2 >= 0) {
            int n6 = 100;
            int n7 = 2;
            int n8 = n3 / 2 - n6 / 2;
            int n9 = n4 / 2 + 16;
            GL11.glDisable(3553);
            is2.b();
            is2.b(0x808080);
            is2.a((double)n8, (double)n9, 0.0);
            is2.a((double)n8, (double)(n9 + n7), 0.0);
            is2.a((double)(n8 + n6), (double)(n9 + n7), 0.0);
            is2.a((double)(n8 + n6), (double)n9, 0.0);
            is2.b(0x80FF80);
            is2.a((double)n8, (double)n9, 0.0);
            is2.a((double)n8, (double)(n9 + n7), 0.0);
            is2.a((double)(n8 + n2), (double)(n9 + n7), 0.0);
            is2.a((double)(n8 + n2), (double)n9, 0.0);
            is2.a();
            GL11.glEnable(3553);
        }
        this.b.o.a(this.c, (n3 - this.b.o.a(this.c)) / 2, n4 / 2 - 4 - 16, 0xFFFFFF);
        this.b.o.a(this.a, (n3 - this.b.o.a(this.a)) / 2, n4 / 2 - 4 + 8, 0xFFFFFF);
        Display.update();
        try {
            Thread.yield();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

