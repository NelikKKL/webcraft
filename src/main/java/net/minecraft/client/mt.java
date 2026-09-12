/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mt
extends gk {
    public int a;
    public int b;
    public int c;

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readShort();
            this.b = dataInputStream.readByte();
            this.c = dataInputStream.readShort();
        } catch (IOException ex) {
            Logger.getLogger(mt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeShort(this.a);
            dataOutputStream.writeByte(this.b);
            dataOutputStream.writeShort(this.c);
        } catch (IOException ex) {
            Logger.getLogger(mt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 5;
    }
}

