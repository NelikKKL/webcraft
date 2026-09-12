/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class hw
extends gk {
    public String a;

    public hw() {
    }

    public hw(String string) {
        this.a = string;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(hw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeUTF(this.a);
        } catch (IOException ex) {
            Logger.getLogger(hw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 4 + this.a.length() + 4;
    }
}

