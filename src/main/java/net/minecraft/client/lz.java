/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lz
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.e = dataInputStream.readByte();
            this.b = dataInputStream.readInt();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readInt();
        } catch (IOException ex) {
            Logger.getLogger(lz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeByte(this.e);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
        } catch (IOException ex) {
            Logger.getLogger(lz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 17;
    }
}

