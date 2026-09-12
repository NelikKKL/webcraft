/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fa
extends gk {
    public double a;
    public double b;
    public double c;
    public double d;
    public float e;
    public float f;
    public boolean g;
    public boolean h;
    public boolean i;

    public fa() {
    }

    public fa(boolean bl2) {
        this.g = bl2;
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.g = dataInputStream.read() != 0;
        } catch (IOException ex) {
            Logger.getLogger(fa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.write(this.g ? 1 : 0);
        } catch (IOException ex) {
            Logger.getLogger(fa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int a() {
        return 1;
    }
}

