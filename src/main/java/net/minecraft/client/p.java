/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class p
extends gk {
    public int a;
    public InventoryItem[] b;

    public p() {
    }

    public p(int n2, InventoryItem[] fpArray) {
        this.a = n2;
        this.b = new InventoryItem[fpArray.length];
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2] = fpArray[i2] == null ? null : fpArray[i2].e();
        }
    }

    @Override
    public void a(DataInputStream dataInputStream) {
        try {
            this.a = dataInputStream.readInt();
            int n2 = dataInputStream.readShort();
            this.b = new InventoryItem[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                short s2 = dataInputStream.readShort();
                if (s2 < 0) continue;
                byte by2 = dataInputStream.readByte();
                short s3 = dataInputStream.readShort();
                this.b[i2] = new InventoryItem(s2, by2, s3);
            }
        } catch (IOException ex) {
            Logger.getLogger(p.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeShort(this.b.length);
            for (int i2 = 0; i2 < this.b.length; ++i2) {
                if (this.b[i2] == null) {
                    dataOutputStream.writeShort(-1);
                    continue;
                }
                dataOutputStream.writeShort((short)this.b[i2].id);
                dataOutputStream.writeByte((byte)this.b[i2].count);
                dataOutputStream.writeShort((short)this.b[i2].damage);
            }
        } catch (IOException ex) {
            Logger.getLogger(p.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void a(mo mo2) {
        mo2.a(this);
    }

    @Override
    public int a() {
        return 6 + this.b.length * 5;
    }
}

