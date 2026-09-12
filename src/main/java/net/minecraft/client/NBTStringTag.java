/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTStringTag
extends NBTTag {
    public String a;

    public NBTStringTag() {
    }

    public NBTStringTag(String string) {
        this.a = string;
        if (string == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeUTF(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTStringTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(NBTStringTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 8;
    }

    public String toString() {
        return "" + this.a;
    }
}

