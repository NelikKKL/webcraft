/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fv
extends gk {
    public int a;
    public byte b;
    public int c;
    public int d;
    public int e;
    public byte f;
    public byte g;

    public fv() {
    }

    public fv(Mob hf2) {
        this.a = hf2.an;
        this.b = (byte)EntityRegistry.a(hf2);
        this.c = TrigLookup.b(hf2.aw * 32.0);
        this.d = TrigLookup.b(hf2.ax * 32.0);
        this.e = TrigLookup.b(hf2.ay * 32.0);
        this.f = (byte)(hf2.aC * 256.0f / 360.0f);
        this.g = (byte)(hf2.aD * 256.0f / 360.0f);
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.readByte();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.readInt();
            this.f = dataInputStream.readByte();
            this.g = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(fv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeByte(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeByte(this.f);
            dataOutputStream.writeByte(this.g);
        } catch (IOException ex) {
            Logger.getLogger(fv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 19;
    }
}

