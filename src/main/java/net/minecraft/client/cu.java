/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.IOException;

public class cu
implements bd {
    private ha[] b = new ha[256];
    private Session c;
    private ai d;
    byte[] a = new byte[32768];

    public cu(Session cy2, ai ai2) {
        this.c = cy2;
        this.d = ai2;
    }

    @Override
    public boolean a(int n2, int n3) {
        int n4 = n2 & 0xF | (n3 & 0xF) * 16;
        return this.b[n4] != null && this.b[n4].a(n2, n3);
    }

    @Override
    public ha b(int n2, int n3) {
        int n4 = n2 & 0xF | (n3 & 0xF) * 16;
        try {
            if (!this.a(n2, n3)) {
                ha ha2 = this.c(n2, n3);
                if (ha2 == null) {
                    ha2 = new ha(this.c, this.a, n2, n3);
                    ha2.q = true;
                    ha2.p = true;
                }
                this.b[n4] = ha2;
            }
            return this.b[n4];
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private synchronized ha c(int n2, int n3) {
        return this.d.a(this.c, n2, n3);
    }

    @Override
    public void a(bd bd2, int n2, int n3) {
    }

    @Override
    public boolean a(boolean bl2, pu pu2) {
        return true;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }
}

