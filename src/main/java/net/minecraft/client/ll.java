/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ll
extends nh {
    public ll() {
        this.g = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            super.a(dataInputStream);
            this.e = dataInputStream.readByte();
            this.f = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(ll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            super.a(dataOutputStream);
            dataOutputStream.writeByte(this.e);
            dataOutputStream.writeByte(this.f);
        } catch (IOException ex) {
            Logger.getLogger(ll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 6;
    }
}

