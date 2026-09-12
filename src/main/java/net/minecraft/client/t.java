/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class t
extends fa {
    public t() {
        this.h = true;
    }

    public t(double d2, double d3, double d4, double d5, boolean bl2) {
        this.a = d2;
        this.b = d3;
        this.d = d4;
        this.c = d5;
        this.g = bl2;
        this.h = true;
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readDouble();
            this.b = dataInputStream.readDouble();
            this.d = dataInputStream.readDouble();
            this.c = dataInputStream.readDouble();
            super.a(dataInputStream);
        } catch (IOException ex) {
            Logger.getLogger(t.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeDouble(this.a);
            dataOutputStream.writeDouble(this.b);
            dataOutputStream.writeDouble(this.d);
            dataOutputStream.writeDouble(this.c);
            super.a(dataOutputStream);
        } catch (IOException ex) {
            Logger.getLogger(t.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 33;
    }
}

