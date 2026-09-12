/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ky
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public byte e;
    public byte f;

    public ky() {
    }

    public ky(lw lw2) {
        this.a = lw2.an;
        this.b = TrigLookup.b(lw2.aw * 32.0);
        this.c = TrigLookup.b(lw2.ax * 32.0);
        this.d = TrigLookup.b(lw2.ay * 32.0);
        this.e = (byte)(lw2.aC * 256.0f / 360.0f);
        this.f = (byte)(lw2.aD * 256.0f / 360.0f);
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readInt();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readInt();
            this.e = (byte)dataInputStream.read();
            this.f = (byte)dataInputStream.read();
        } catch (IOException ex) {
            Logger.getLogger(ky.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.write(this.e);
            dataOutputStream.write(this.f);
        } catch (IOException ex) {
            Logger.getLogger(ky.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 34;
    }
}

