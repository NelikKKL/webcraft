/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTShortTag
extends NBTTag {
    public short a;

    public NBTShortTag() {
    }

    public NBTShortTag(short s2) {
        this.a = s2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeShort(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTShortTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readShort();
        } catch (IOException ex) {
            Logger.getLogger(NBTShortTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 2;
    }

    public String toString() {
        return "" + this.a;
    }
}

