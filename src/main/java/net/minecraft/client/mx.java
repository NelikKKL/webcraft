/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mx
extends gk {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public mx() {
        this.j = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            this.b = dataInputStream.read();
            this.c = dataInputStream.readInt();
            this.d = dataInputStream.read();
            this.e = dataInputStream.read();
        } catch (IOException ex) {
            Logger.getLogger(mx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.write(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.write(this.d);
            dataOutputStream.write(this.e);
        } catch (IOException ex) {
            Logger.getLogger(mx.class.getName()).log(Level.SEVERE, null, ex);
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

