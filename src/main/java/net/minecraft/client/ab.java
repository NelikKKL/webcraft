/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ab {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NBTCompoundTag a(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(inputStream));){
            return ab.a((DataInput)dataInputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(NBTCompoundTag iq2, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream));){
            ab.a(iq2, (DataOutput)dataOutputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NBTCompoundTag a(byte[] byArray) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(byArray)));){
            NBTCompoundTag iq2 = ab.a((DataInput)dataInputStream);
            return iq2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] a(NBTCompoundTag iq2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(byteArrayOutputStream));){
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static NBTCompoundTag a(DataInput dataInput) throws IOException {
        NBTTag fd2 = NBTTag.read(dataInput);
        if (fd2 instanceof NBTCompoundTag) {
            return (NBTCompoundTag)fd2;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void a(NBTCompoundTag iq2, DataOutput dataOutput) throws IOException {
        NBTTag.serialize(iq2, dataOutput);
    }
}

