/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class nz
extends fa {
    public nz() {
        this.i = true;
    }

    public nz(float f2, float f3, boolean bl2) {
        this.e = f2;
        this.f = f3;
        this.g = bl2;
        this.i = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.e = dataInputStream.readFloat();
            this.f = dataInputStream.readFloat();
            super.a(dataInputStream);
        } catch (IOException ex) {
            Logger.getLogger(nz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeFloat(this.e);
            dataOutputStream.writeFloat(this.f);
            super.a(dataOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(nz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 9;
    }
}

