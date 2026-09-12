/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ed
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public ed() {
    }

    public ed(int n2, int n3, int n4, int n5, int n6) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
        this.e = n6;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readShort();
            this.b = dataInputStream.readInt();
            this.c = dataInputStream.read();
            this.d = dataInputStream.readInt();
            this.e = dataInputStream.read();
        } catch (IOException ex) {
            Logger.getLogger(ed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeShort(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.write(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.write(this.e);
        } catch (IOException ex) {
            Logger.getLogger(ed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 12;
    }
}

