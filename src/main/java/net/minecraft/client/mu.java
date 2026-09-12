/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class mu
implements ai {
    private File a;
    private boolean b;

    public mu(File file, boolean bl2) {
        this.a = file;
        this.b = bl2;
    }

    private File a(int n2, int n3) {
        String string = "c." + Integer.toString(n2, 36) + "." + Integer.toString(n3, 36) + ".dat";
        String string2 = Integer.toString(n2 & 0x3F, 36);
        String string3 = Integer.toString(n3 & 0x3F, 36);
        File file = new File(this.a, string2);
        if (!file.exists()) {
            if (this.b) {
                file.mkdir();
            } else {
                return null;
            }
        }
        if (!(file = new File(file, string3)).exists()) {
            if (this.b) {
                file.mkdir();
            } else {
                return null;
            }
        }
        if (!(file = new File(file, string)).exists() && !this.b) {
            return null;
        }
        return file;
    }

    @Override
    public ha a(Session cy2, int n2, int n3) {
        File file = this.a(n2, n3);
        if (file != null && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                NBTCompoundTag iq2 = ab.a(fileInputStream);
                if (!iq2.has("Level")) {
                    System.out.println("Chunk file at " + n2 + "," + n3 + " is missing level data, skipping");
                    return null;
                }
                if (!iq2.getCompoundTag("Level").has("Blocks")) {
                    System.out.println("Chunk file at " + n2 + "," + n3 + " is missing block data, skipping");
                    return null;
                }
                ha ha2 = mu.a(cy2, iq2.getCompoundTag("Level"));
                if (!ha2.a(n2, n3)) {
                    System.out.println("Chunk file at " + n2 + "," + n3 + " is in the wrong location; relocating. (Expected " + n2 + ", " + n3 + ", got " + ha2.j + ", " + ha2.k + ")");
                    iq2.storeInt("xPos", n2);
                    iq2.storeInt("zPos", n3);
                    ha2 = mu.a(cy2, iq2.getCompoundTag("Level"));
                }
                return ha2;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void a(Session cy2, ha ha2) {
        cy2.n();
        File file = this.a(ha2.j, ha2.k);
        if (file.exists()) {
            cy2.v -= file.length();
        }
        try {
            File file2 = new File(this.a, "tmp_chunk.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            NBTCompoundTag iq2 = new NBTCompoundTag();
            NBTCompoundTag iq3 = new NBTCompoundTag();
            iq2.storeTag("Level", (NBTTag)iq3);
            this.a(ha2, cy2, iq3);
            ab.a(iq2, fileOutputStream);
            fileOutputStream.close();
            if (file.exists()) {
                file.delete();
            }
            file2.renameTo(file);
            cy2.v += file.length();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(ha ha2, Session cy2, NBTCompoundTag iq2) {
        NBTCompoundTag iq3;
        cy2.n();
        iq2.storeInt("xPos", ha2.j);
        iq2.storeInt("zPos", ha2.k);
        iq2.storeLong("LastUpdate", cy2.e);
        iq2.storeByteArray("Blocks", ha2.b);
        iq2.storeByteArray("Data", ha2.e.a);
        iq2.storeByteArray("SkyLight", ha2.f.a);
        iq2.storeByteArray("BlockLight", ha2.g.a);
        iq2.storeByteArray("HeightMap", ha2.h);
        iq2.storeBoolean("TerrainPopulated", ha2.n);
        ha2.r = false;
        NBTListTag ly2 = new NBTListTag();
        for (int i2 = 0; i2 < ha2.m.length; ++i2) {
            for (Object object : ha2.m[i2]) {
                ha2.r = true;
                iq3 = new NBTCompoundTag();
                if (!((lw)object).c(iq3)) continue;
                ly2.a(iq3);
            }
        }
        iq2.storeTag("Entities", ly2);
        NBTListTag ly3 = new NBTListTag();
        for (Object object : ha2.l.values()) {
            iq3 = new NBTCompoundTag();
            ((TileEntityRegistry)object).b(iq3);
            ly3.a(iq3);
        }
        iq2.storeTag("TileEntities", ly3);
    }

    public static ha a(Session cy2, NBTCompoundTag iq2) {
        NBTListTag ly2;
        Object object;
        NBTListTag ly3;
        int n2 = iq2.getInt("xPos");
        int n3 = iq2.getInt("zPos");
        ha ha2 = new ha(cy2, n2, n3);
        ha2.b = iq2.getByteArray("Blocks");
        ha2.e = new oo(iq2.getByteArray("Data"));
        ha2.f = new oo(iq2.getByteArray("SkyLight"));
        ha2.g = new oo(iq2.getByteArray("BlockLight"));
        ha2.h = iq2.getByteArray("HeightMap");
        ha2.n = iq2.isTrue("TerrainPopulated");
        if (!ha2.e.a()) {
            ha2.e = new oo(ha2.b.length);
        }
        if (ha2.h == null || !ha2.f.a()) {
            ha2.h = new byte[256];
            ha2.f = new oo(ha2.b.length);
            ha2.c();
        }
        if (!ha2.g.a()) {
            ha2.g = new oo(ha2.b.length);
            ha2.a();
        }
        if ((ly3 = iq2.getListTag("Entities")) != null) {
            for (int i2 = 0; i2 < ly3.c(); ++i2) {
                NBTCompoundTag iq3 = (NBTCompoundTag)ly3.a(i2);
                object = EntityRegistry.a(iq3, cy2);
                ha2.r = true;
                if (object == null) continue;
                ha2.a((lw)object);
            }
        }
        if ((ly2 = iq2.getListTag("TileEntities")) != null) {
            for (int i3 = 0; i3 < ly2.c(); ++i3) {
                object = (NBTCompoundTag)ly2.a(i3);
                TileEntityRegistry ji2 = TileEntityRegistry.c((NBTCompoundTag)object);
                if (ji2 == null) continue;
                ha2.a(ji2);
            }
        }
        return ha2;
    }

    @Override
    public void a() {
    }

    @Override
    public void b() {
    }

    @Override
    public void b(Session cy2, ha ha2) {
    }
}

