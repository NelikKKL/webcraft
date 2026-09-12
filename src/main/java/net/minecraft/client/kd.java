/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class kd
extends nh {
    public kd() {
        this.g = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        super.a(dataInputStream);
        try {
            this.b = dataInputStream.readByte();
            this.c = dataInputStream.readByte();
            this.d = dataInputStream.readByte();
            this.e = dataInputStream.readByte();
            this.f = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(kd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        super.a(dataOutputStream);
        try {
            dataOutputStream.writeByte(this.b);
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.d);
            dataOutputStream.writeByte(this.e);
            dataOutputStream.writeByte(this.f);
        } catch (IOException ex) {
            Logger.getLogger(kd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 9;
    }
}

