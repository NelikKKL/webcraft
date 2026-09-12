/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTIntTag
extends NBTTag {
    public int a;

    public NBTIntTag() {
    }

    public NBTIntTag(int n2) {
        this.a = n2;
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            dataOutput.writeInt(this.a);
        } catch (IOException ex) {
            Logger.getLogger(NBTIntTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        try {
            this.a = dataInput.readInt();
        } catch (IOException ex) {
            Logger.getLogger(NBTIntTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 3;
    }

    public String toString() {
        return "" + this.a;
    }
}

