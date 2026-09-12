/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class id
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public byte e;
    public byte f;
    public byte g;
    public int h;
    public int i;

    public id() {
    }

    public id(DroppedItem eo2) {
        this.a = eo2.an;
        this.h = eo2.a.id;
        this.i = eo2.a.count;
        this.b = TrigLookup.b(eo2.aw * 32.0);
        this.c = TrigLookup.b(eo2.ax * 32.0);
        this.d = TrigLookup.b(eo2.ay * 32.0);
        this.e = (byte)(eo2.az * 128.0);
        this.f = (byte)(eo2.aA * 128.0);
        this.g = (byte)(eo2.aB * 128.0);
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.h = dataInputStream.readShort();
            this.i = dataInputStream.readByte();
            this.b = dataInputStream.readInt();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.readByte();
            this.f = dataInputStream.readByte();
            this.g = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(id.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeShort(this.h);
            dataOutputStream.writeByte(this.i);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeByte(this.e);
            dataOutputStream.writeByte(this.f);
            dataOutputStream.writeByte(this.g);
        } catch (IOException ex) {
            Logger.getLogger(id.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 22;
    }
}

