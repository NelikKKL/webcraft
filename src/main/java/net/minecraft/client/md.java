/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class md
extends nh {
    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            super.a(dataInputStream);
            this.b = dataInputStream.readByte();
            this.c = dataInputStream.readByte();
            this.d = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(md.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            super.a(dataOutputStream);
            dataOutputStream.writeByte(this.b);
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.d);
        } catch (IOException ex) {
            Logger.getLogger(md.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 7;
    }
}

