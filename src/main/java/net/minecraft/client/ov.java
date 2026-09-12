/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ov
extends gk {
    public int a;
    public int b;
    public short[] c;
    public byte[] d;
    public byte[] e;
    public int f;

    public ov() {
        this.j = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readInt();
            this.f = dataInputStream.readShort() & 0xFFFF;
            this.c = new short[this.f];
            this.d = new byte[this.f];
            this.e = new byte[this.f];
            for (int i2 = 0; i2 < this.f; ++i2) {
                this.c[i2] = dataInputStream.readShort();
            }
            dataInputStream.readFully(this.d);
            dataInputStream.readFully(this.e);
        } catch (IOException ex) {
            Logger.getLogger(ov.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeShort((short)this.f);
            for (int i2 = 0; i2 < this.f; ++i2) {
                dataOutputStream.writeShort(this.c[i2]);
            }
            dataOutputStream.write(this.d);
            dataOutputStream.write(this.e);
        } catch (IOException ex) {
            Logger.getLogger(ov.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 10 + this.f * 4;
    }
}

