/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class py
extends gk {
    public int a;
    public int b;
    public int c;
    public byte[] d;
    public NBTCompoundTag e;

    public py() {
        this.j = true;
    }

    public py(int n2, int n3, int n4, TileEntityRegistry ji2) {
        this.j = true;
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.e = new NBTCompoundTag();
        ji2.b(this.e);
        try {
            this.d = ab.a(this.e);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readShort();
            this.c = dataInputStream.readInt();
            int n2 = dataInputStream.readShort() & 0xFFFF;
            this.d = new byte[n2];
            dataInputStream.readFully(this.d);
            this.e = ab.a(this.d);
        } catch (IOException ex) {
            Logger.getLogger(py.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeShort(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeShort((short)this.d.length);
            dataOutputStream.write(this.d);
        } catch (IOException ex) {
            Logger.getLogger(py.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return this.d.length + 2 + 10;
    }
}

