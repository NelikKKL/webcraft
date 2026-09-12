/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTByteTag
extends NBTTag {
    public byte a;

    public NBTByteTag() {
    }

    public NBTByteTag(byte by2) {
        this.a = by2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeByte(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTByteTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readByte();
        } catch (IOException ex) {
            Logger.getLogger(NBTByteTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 1;
    }

    public String toString() {
        return "" + this.a;
    }
}

