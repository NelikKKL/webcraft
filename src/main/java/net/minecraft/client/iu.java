/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class iu
extends gk {
    public int a;
    public String b;
    public String c;
    public long d;
    public byte e;

    public iu() {
    }

    public iu(String string, String string2, int n2) {
        this.b = string;
        this.c = string2;
        this.a = n2;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readUTF();
            this.c = dataInputStream.readUTF();
            this.d = dataInputStream.readLong();
            this.e = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(iu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeUTF(this.c);
            dataOutputStream.writeLong(this.d);
            dataOutputStream.writeByte(this.e);
        } catch (IOException ex) {
            Logger.getLogger(iu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 4 + this.b.length() + this.c.length() + 4 + 5;
    }
}

