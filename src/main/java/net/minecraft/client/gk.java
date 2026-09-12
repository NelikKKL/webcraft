/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class gk {
    static private Map a = new HashMap();
    static private Map b = new HashMap();
    public boolean j = false;

    static void a(int n2, Class clazz) {
        if (a.containsKey(n2)) {
            throw new IllegalArgumentException("Duplicate packet id:" + n2);
        }
        if (b.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class:" + clazz);
        }
        a.put(n2, clazz);
        b.put(clazz, n2);
    }

    public static gk a(int n2) {
        try {
            Class clazz = (Class)a.get(n2);
            if (clazz == null) {
                return null;
            }
            return (gk)clazz.newInstance();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Skipping packet with id " + n2);
            return null;
        }
    }

    public final int b() {
        return (Integer)b.get(this.getClass());
    }

    public static gk b(DataInputStream dataInputStream) throws IOException {
        int n2 = dataInputStream.read();
        if (n2 == -1) {
            return null;
        }
        gk gk2 = gk.a(n2);
        if (gk2 == null) {
            throw new IOException("Bad packet id " + n2);
        }
        gk2.a(dataInputStream);
        return gk2;
    }

    public static void a(gk gk2, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(gk2.b());
        gk2.a(dataOutputStream);
    }

    public abstract void a(DataInputStream var1);

    public abstract void a(DataOutputStream var1);

    public abstract void a(mo var1);

    public abstract int a();

    static {
        gk.a(0, hl.class);
        gk.a(1, iu.class);
        gk.a(2, hw.class);
        gk.a(3, jr.class);
        gk.a(4, ek.class);
        gk.a(5, p.class);
        gk.a(6, kv.class);
        gk.a(7, a.class);
        gk.a(8, cq.class);
        gk.a(9, jk.class);
        gk.a(10, fa.class);
        gk.a(11, t.class);
        gk.a(12, nz.class);
        gk.a(13, cr.class);
        gk.a(14, gc.class);
        gk.a(15, ed.class);
        gk.a(16, eq.class);
        gk.a(17, mt.class);
        gk.a(18, ii.class);
        gk.a(20, hs.class);
        gk.a(21, id.class);
        gk.a(22, bu.class);
        gk.a(23, lz.class);
        gk.a(24, fv.class);
        gk.a(28, dv.class);
        gk.a(29, li.class);
        gk.a(30, nh.class);
        gk.a(31, md.class);
        gk.a(32, ll.class);
        gk.a(33, kd.class);
        gk.a(34, ky.class);
        gk.a(38, fs.class);
        gk.a(39, io.class);
        gk.a(50, lq.class);
        gk.a(51, ci.class);
        gk.a(52, ov.class);
        gk.a(53, mx.class);
        gk.a(59, py.class);
        gk.a(60, lc.class);
        gk.a(255, qi.class);
    }
}

