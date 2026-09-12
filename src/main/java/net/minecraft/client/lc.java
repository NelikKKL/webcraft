/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lc
extends gk {
    public double a;
    public double b;
    public double c;
    public float d;
    public Set e;

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readDouble();
            this.b = dataInputStream.readDouble();
            this.c = dataInputStream.readDouble();
            this.d = dataInputStream.readFloat();
            int n2 = dataInputStream.readInt();
            this.e = new HashSet();
            int n3 = (int)this.a;
            int n4 = (int)this.b;
            int n5 = (int)this.c;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n6 = dataInputStream.readByte() + n3;
                int n7 = dataInputStream.readByte() + n4;
                int n8 = dataInputStream.readByte() + n5;
                this.e.add(new on(n6, n7, n8));
            }
        } catch (IOException ex) {
            Logger.getLogger(lc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeDouble(this.a);
            dataOutputStream.writeDouble(this.b);
            dataOutputStream.writeDouble(this.c);
            dataOutputStream.writeFloat(this.d);
            dataOutputStream.writeInt(this.e.size());
            int n2 = (int)this.a;
            int n3 = (int)this.b;
            int n4 = (int)this.c;
            for (Object _on2 : this.e) { /* originally on on2 */
                on on2 = (on)_on2; /* line added*/
                int n5 = on2.a - n2;
                int n6 = on2.b - n3;
                int n7 = on2.c - n4;
                dataOutputStream.writeByte(n5);
                dataOutputStream.writeByte(n6);
                dataOutputStream.writeByte(n7);
            }
        } catch (IOException ex) {
            Logger.getLogger(lc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 32 + this.e.size() * 3;
    }
}

