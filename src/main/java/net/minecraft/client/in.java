/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class in
extends InputStream {
    private int c;
    private InputStream d;
    byte[] a = new byte[1];
    final fj b;

    public in(fj fj2, URL uRL, InputStream inputStream) {
        this.b = fj2;
        this.d = inputStream;
        String string = uRL.getPath();
        string = string.substring(string.lastIndexOf("/") + 1);
        this.c = string.hashCode();
    }

    @Override
    public int read() throws IOException {
        int n2 = this.read(this.a, 0, 1);
        if (n2 < 0) {
            return n2;
        }
        return this.a[0];
    }

    @Override
    public int read(byte[] byArray, int n2, int n3) throws IOException {
        n3 = this.d.read(byArray, n2, n3);
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = n2 + i2;
            byte by2 = (byte)(byArray[n4] ^ this.c >> 8);
            byArray[n4] = by2;
            byte by3 = by2;
            this.c = this.c * 498729871 + 85731 * by3;
        }
        return n3;
    }
}

