/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTCompoundTag
extends NBTTag {
    private Map tags = new HashMap();

    @Override
    void getRawValue(DataOutput dataOutput) {
        try {
            for (Object fd2 : this.tags.values()) {
                NBTTag.serialize((NBTTag) fd2, dataOutput);
            }
            dataOutput.writeByte(0);
        } catch (IOException ex) {
            Logger.getLogger(NBTCompoundTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void readRawValue(DataInput dataInput) {
        NBTTag fd2;
        this.tags.clear();
        try {
            while ((fd2 = NBTTag.read(dataInput)).getTagType() != 0) {
                this.tags.put(fd2.getName(), fd2);
            }
        } catch (IOException ex) {
            Logger.getLogger(NBTCompoundTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte getTagType() {
        return 10;
    }

    public void storeTag(String string, NBTTag fd2) {
        this.tags.put(string, fd2.setName(string));
    }

    public void storeByte(String string, byte by2) {
        this.tags.put(string, new NBTByteTag(by2).setName(string));
    }

    public void storeShort(String string, short s2) {
        this.tags.put(string, new NBTShortTag(s2).setName(string));
    }

    public void storeInt(String string, int n2) {
        this.tags.put(string, new NBTIntTag(n2).setName(string));
    }

    public void storeLong(String string, long l2) {
        this.tags.put(string, new NBTLongTag(l2).setName(string));
    }

    public void storeFloat(String string, float f2) {
        this.tags.put(string, new NBTFloatTag(f2).setName(string));
    }

    public void storeDouble(String string, double d2) {
        this.tags.put(string, new NBTDoubleTag(d2).setName(string));
    }

    public void storeString(String string, String string2) {
        this.tags.put(string, new NBTStringTag(string2).setName(string));
    }

    public void storeByteArray(String string, byte[] byArray) {
        this.tags.put(string, new NBTByteArrayTag(byArray).setName(string));
    }

    public void storeCompoundTag(String string, NBTCompoundTag iq2) {
        this.tags.put(string, iq2.setName(string));
    }

    public void storeBoolean(String string, boolean bl2) {
        this.storeByte(string, bl2 ? (byte)1 : 0);
    }

    public boolean has(String string) {
        return this.tags.containsKey(string);
    }

    public byte getByte(String string) {
        if (!this.tags.containsKey(string)) {
            return 0;
        }
        return ((NBTByteTag)this.tags.get((Object)string)).a;
    }

    public short getShort(String string) {
        if (!this.tags.containsKey(string)) {
            return 0;
        }
        return ((NBTShortTag)this.tags.get((Object)string)).a;
    }

    public int getInt(String string) {
        if (!this.tags.containsKey(string)) {
            return 0;
        }
        return ((NBTIntTag)this.tags.get((Object)string)).a;
    }

    public long getLong(String string) {
        if (!this.tags.containsKey(string)) {
            return 0L;
        }
        return ((NBTLongTag)this.tags.get((Object)string)).a;
    }

    public float getFloat(String string) {
        if (!this.tags.containsKey(string)) {
            return 0.0f;
        }
        return ((NBTFloatTag)this.tags.get((Object)string)).a;
    }

    public double getDouble(String string) {
        if (!this.tags.containsKey(string)) {
            return 0.0;
        }
        return ((NBTDoubleTag)this.tags.get((Object)string)).a;
    }

    public String getString(String string) {
        if (!this.tags.containsKey(string)) {
            return "";
        }
        return ((NBTStringTag)this.tags.get((Object)string)).a;
    }

    public byte[] getByteArray(String string) {
        if (!this.tags.containsKey(string)) {
            return new byte[0];
        }
        return ((NBTByteArrayTag)this.tags.get((Object)string)).a;
    }

    public NBTCompoundTag getCompoundTag(String string) {
        if (!this.tags.containsKey(string)) {
            return new NBTCompoundTag();
        }
        return (NBTCompoundTag)this.tags.get(string);
    }

    public NBTListTag getListTag(String string) {
        if (!this.tags.containsKey(string)) {
            return new NBTListTag();
        }
        return (NBTListTag)this.tags.get(string);
    }

    public boolean isTrue(String string) {
        return this.getByte(string) != 0;
    }

    public String toString() {
        return "" + this.tags.size() + " entries";
    }
}

