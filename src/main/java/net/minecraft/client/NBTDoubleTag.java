/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTDoubleTag
extends NBTTag {
    public double a;

    public NBTDoubleTag() {
    }

    public NBTDoubleTag(double d2) {
        this.a = d2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeDouble(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTDoubleTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readDouble();
        } catch (IOException ex) {
            Logger.getLogger(NBTDoubleTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 6;
    }

    public String toString() {
        return "" + this.a;
    }
}

