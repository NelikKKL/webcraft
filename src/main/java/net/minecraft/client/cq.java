/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cq
extends gk {
    public int a;

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readByte();
        } catch (IOException ex) {
            Logger.getLogger(cq.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.a);
        } catch (IOException ex) {
            Logger.getLogger(cq.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 1;
    }
}

