/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.HashMap;
import java.util.Map;

public class TileEntityRegistry {
    static private Map nameToClass = new HashMap();
    static private Map classToName = new HashMap();
    public Session e;
    public int f;
    public int g;
    public int h;

    private static void a(Class clazz, String string) {
        if (classToName.containsKey(string)) {
            throw new IllegalArgumentException("Duplicate id: " + string);
        }
        nameToClass.put(string, clazz);
        classToName.put(clazz, string);
    }

    public void a(NBTCompoundTag iq2) {
        this.f = iq2.getInt("x");
        this.g = iq2.getInt("y");
        this.h = iq2.getInt("z");
    }

    public void b(NBTCompoundTag iq2) {
        String string = (String)classToName.get(this.getClass());
        if (string == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        iq2.storeString("id", string);
        iq2.storeInt("x", this.f);
        iq2.storeInt("y", this.g);
        iq2.storeInt("z", this.h);
    }

    public void b() {
    }

    public static TileEntityRegistry c(NBTCompoundTag iq2) {
        TileEntityRegistry ji2 = null;
        try {
            Class clazz = (Class)nameToClass.get(iq2.getString("id"));
            if (clazz != null) {
                ji2 = (TileEntityRegistry)clazz.newInstance();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (ji2 != null) {
            ji2.a(iq2);
        } else {
            System.out.println("Skipping TileEntity with id " + iq2.getString("id"));
        }
        return ji2;
    }

    public int f() {
        return this.e.e(this.f, this.g, this.h);
    }

    public void h() {
        this.e.b(this.f, this.g, this.h, this);
    }

    public double a(double d2, double d3, double d4) {
        double d5 = (double)this.f + 0.5 - d2;
        double d6 = (double)this.g + 0.5 - d3;
        double d7 = (double)this.h + 0.5 - d4;
        return d5 * d5 + d6 * d6 + d7 * d7;
    }

    public Block g() {
        return Block.registry[this.e.a(this.f, this.g, this.h)];
    }

    static {
        TileEntityRegistry.a(Furnace.class, "Furnace");
        TileEntityRegistry.a(Chest.class, "Chest");
        TileEntityRegistry.a(Sign.class, "Sign");
        TileEntityRegistry.a(MobSpawner.class, "MobSpawner");
    }
}

