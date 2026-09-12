/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTLongTag
extends NBTTag {
    public long a;

    public NBTLongTag() {
    }

    public NBTLongTag(long l2) {
        this.a = l2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeLong(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTLongTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readLong();
        } catch (IOException ex) {
            Logger.getLogger(NBTLongTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 4;
    }

    public String toString() {
        return "" + this.a;
    }
}

