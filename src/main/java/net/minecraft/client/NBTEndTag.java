/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;

public class NBTEndTag
extends NBTTag {
    @Override
    void readRawValue(DataInput dataInput) {
    }

    @Override
    void getRawValue(DataOutput dataOutput) {
    }

    @Override
    public byte getTagType() {
        return 0;
    }

    public String toString() {
        return "END";
    }
}

