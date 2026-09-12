/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTListTag
extends NBTTag {
    private List a = new ArrayList();
    private byte b;

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            this.b = this.a.size() > 0 ? ((NBTTag)this.a.get(0)).getTagType() : (byte)1;
            dataOutput.writeByte(this.b);
            dataOutput.writeInt(this.a.size());
            for (int i2 = 0; i2 < this.a.size(); ++i2) {
                ((NBTTag)this.a.get(i2)).getRawValue(dataOutput);
            }
        } catch (IOException ex) {
            Logger.getLogger(NBTListTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.b = dataInput.readByte();
            int n2 = dataInput.readInt();
            this.a = new ArrayList();
            for (int i2 = 0; i2 < n2; ++i2) {
                NBTTag fd2 = NBTTag.createTagFromType(this.b);
                fd2.readRawValue(dataInput);
                this.a.add(fd2);
            }
        } catch (IOException ex) {
            Logger.getLogger(NBTListTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 9;
    }

    public String toString() {
        return "" + this.a.size() + " entries of type " + NBTTag.tagTypeToName(this.b);
    }

    public void a(NBTTag fd2) {
        this.b = fd2.getTagType();
        this.a.add(fd2);
    }

    public NBTTag a(int n2) {
        return (NBTTag)this.a.get(n2);
    }

    public int c() {
        return this.a.size();
    }
}

