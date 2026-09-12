/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTTag {
    private String name = null;

    abstract void getRawValue(DataOutput var1);

    abstract void readRawValue(DataInput var1);

    public abstract byte getTagType();

    public String getName() {
        if (this.name == null) {
            return "";
        }
        return this.name;
    }

    public NBTTag setName(String string) {
        this.name = string;
        return this;
    }

    public static NBTTag read(DataInput dataInput) throws IOException {
        byte by2 = dataInput.readByte();
        if (by2 == 0) {
            return new NBTEndTag();
        }
        NBTTag fd2 = NBTTag.createTagFromType(by2);
        fd2.name = dataInput.readUTF();
        fd2.readRawValue(dataInput);
        return fd2;
    }

    public static void serialize(NBTTag fd2, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(fd2.getTagType());
        if (fd2.getTagType() == 0) {
            return;
        }
        dataOutput.writeUTF(fd2.getName());
        fd2.getRawValue(dataOutput);
    }

    public static NBTTag createTagFromType(byte type) {
        switch (type) {
            case 0: {
                return new NBTEndTag();
            }
            case 1: {
                return new NBTByteTag();
            }
            case 2: {
                return new NBTShortTag();
            }
            case 3: {
                return new NBTIntTag();
            }
            case 4: {
                return new NBTLongTag();
            }
            case 5: {
                return new NBTFloatTag();
            }
            case 6: {
                return new NBTDoubleTag();
            }
            case 7: {
                return new NBTByteArrayTag();
            }
            case 8: {
                return new NBTStringTag();
            }
            case 9: {
                return new NBTListTag();
            }
            case 10: {
                return new NBTCompoundTag();
            }
        }
        return null;
    }

    public static String tagTypeToName(byte type) {
        switch (type) {
            case 0: {
                return "TAG_End";
            }
            case 1: {
                return "TAG_Byte";
            }
            case 2: {
                return "TAG_Short";
            }
            case 3: {
                return "TAG_Int";
            }
            case 4: {
                return "TAG_Long";
            }
            case 5: {
                return "TAG_Float";
            }
            case 6: {
                return "TAG_Double";
            }
            case 7: {
                return "TAG_Byte_Array";
            }
            case 8: {
                return "TAG_String";
            }
            case 9: {
                return "TAG_List";
            }
            case 10: {
                return "TAG_Compound";
            }
        }
        return "UNKNOWN";
    }
}

