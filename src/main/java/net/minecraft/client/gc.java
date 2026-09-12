/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gc
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public gc() {
    }

    public gc(int n2, int n3, int n4, int n5, int n6) {
        this.e = n2;
        this.a = n3;
        this.b = n4;
        this.c = n5;
        this.d = n6;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.e = dataInputStream.read();
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.read();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.read();
        } catch (IOException ex) {
            Logger.getLogger(gc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.write(this.e);
            dataOutputStream.writeInt(this.a);
            dataOutputStream.write(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.write(this.d);
        } catch (IOException ex) {
            Logger.getLogger(gc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 11;
    }
}

