/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class hs
extends gk {
    public int a;
    public String b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;
    public int h;

    public hs() {
    }

    public hs(Player eb2) {
        this.a = eb2.an;
        this.b = eb2.name;
        this.c = TrigLookup.b(eb2.aw * 32.0);
        this.d = TrigLookup.b(eb2.ax * 32.0);
        this.e = TrigLookup.b(eb2.ay * 32.0);
        this.f = (byte)(eb2.aC * 256.0f / 360.0f);
        this.g = (byte)(eb2.aD * 256.0f / 360.0f);
        InventoryItem fp2 = eb2.e.a();
        this.h = fp2 == null ? 0 : fp2.id;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readUTF();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readByte();
            this.g = dataInputStream.readByte();
            this.h = dataInputStream.readShort();
        } catch (IOException ex) {
            Logger.getLogger(hs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeUTF(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeByte(this.f);
            dataOutputStream.writeByte(this.g);
            dataOutputStream.writeShort(this.h);
        } catch (IOException ex) {
            Logger.getLogger(hs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 28;
    }
}

