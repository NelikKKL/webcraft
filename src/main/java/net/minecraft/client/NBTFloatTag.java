/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTFloatTag
extends NBTTag {
    public float a;

    public NBTFloatTag() {
    }

    public NBTFloatTag(float f2) {
        this.a = f2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeFloat(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTFloatTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readFloat();
        } catch (IOException ex) {
            Logger.getLogger(NBTFloatTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 5;
    }

    public String toString() {
        return "" + this.a;
    }
}

