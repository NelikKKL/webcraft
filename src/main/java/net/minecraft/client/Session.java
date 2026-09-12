/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Session
implements pk {
    public boolean a = false;
    private List A = new ArrayList();
    public List b = new ArrayList();
    private List B = new ArrayList();
    private TreeSet C = new TreeSet();
    private Set D = new HashSet();
    public List c = new ArrayList();
    public List d = new ArrayList();
    public long e = 0L;
    private long E = 0xFFFFFFL;
    public int f = 0;
    protected int g = new Random().nextInt();
    protected int h = 1013904223;
    public boolean i = false;
    private long F = System.currentTimeMillis();
    protected int j = 40;
    public int k;
    public Random l = new Random();
    public int m;
    public int n;
    public int o;
    public boolean p = false;
    public final oz q;
    protected List r = new ArrayList();
    private bd G;
    public File s;
    public File t;
    public long u = 0L;
    private NBTCompoundTag H;
    public long v = 0L;
    public final String w;
    public boolean x;
    private ArrayList I = new ArrayList();
    private int J = 0;
    static int y = 0;
    private Set K = new HashSet();
    private int L = this.l.nextInt(12000);
    private List M = new ArrayList();
    public boolean z = false;

    public static NBTCompoundTag a(File file, String string) {
        File file2 = new File(file, "saves");
        File file3 = new File(file2, string);
        if (!file3.exists()) {
            return null;
        }
        File file4 = new File(file3, "level.dat");
        if (file4.exists()) {
            try {
                NBTCompoundTag iq2 = ab.a(new FileInputStream(file4));
                NBTCompoundTag iq3 = iq2.getCompoundTag("Data");
                return iq3;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static void b(File file, String string) {
        File file2 = new File(file, "saves");
        File file3 = new File(file2, string);
        if (!file3.exists()) {
            return;
        }
        Session.a(file3.listFiles());
        file3.delete();
    }

    private static void a(File[] fileArray) {
        for (int i2 = 0; i2 < fileArray.length; ++i2) {
            if (fileArray[i2].isDirectory()) {
                Session.a(fileArray[i2].listFiles());
            }
            fileArray[i2].delete();
        }
    }

    @Override
    public po a() {
        return this.q.b;
    }

    public Session(File file, String string) {
        this(file, string, new Random().nextLong());
    }

    public Session(String string, oz oz2, long l2) {
        this.w = string;
        this.u = l2;
        this.q = oz2;
        oz2.a(this);
        this.G = this.a(this.t);
        this.h();
    }

    public Session(Session cy2, oz oz2) {
        this.F = cy2.F;
        this.s = cy2.s;
        this.t = cy2.t;
        this.w = cy2.w;
        this.u = cy2.u;
        this.e = cy2.e;
        this.m = cy2.m;
        this.n = cy2.n;
        this.o = cy2.o;
        this.v = cy2.v;
        this.q = oz2;
        oz2.a(this);
        this.G = this.a(this.t);
        this.h();
    }

    public Session(File file, String string, long l2) {
        this(file, string, l2, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Session(File file, String string, long l2, oz oz2) {
        Object object;
        Object object2;
        this.s = file;
        this.w = string;
        file.mkdirs();
        this.t = new File(file, string);
        this.t.mkdirs();
        try {
            object2 = new File(this.t, "session.lock");
            object = new DataOutputStream(new FileOutputStream((File)object2));
            try {
                ((DataOutputStream)object).writeLong(this.F);
            }
            finally {
                ((FilterOutputStream)object).close();
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
        object2 = new oz();
        object = new File(this.t, "level.dat");
        boolean bl2 = this.p = !((File)object).exists();
        if (((File)object).exists()) {
            try {
                NBTCompoundTag iq2 = ab.a(new FileInputStream((File)object));
                NBTCompoundTag iq3 = iq2.getCompoundTag("Data");
                this.u = iq3.getLong("RandomSeed");
                this.m = iq3.getInt("SpawnX");
                this.n = iq3.getInt("SpawnY");
                this.o = iq3.getInt("SpawnZ");
                this.e = iq3.getLong("Time");
                this.v = iq3.getLong("SizeOnDisk");
                if (iq3.has("Player")) {
                    this.H = iq3.getCompoundTag("Player");
                    int n2 = this.H.getInt("Dimension");
                    if (n2 == -1) {
                        object2 = new om();
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (oz2 != null) {
            object2 = oz2;
        }
        boolean bl3 = false;
        if (this.u == 0L) {
            this.u = l2;
            bl3 = true;
        }
        this.q = (oz)object2;
        this.q.a(this);
        this.G = this.a(this.t);
        if (bl3) {
            this.x = true;
            this.m = 0;
            this.n = 64;
            this.o = 0;
            while (!this.q.a(this.m, this.o)) {
                this.m += this.l.nextInt(64) - this.l.nextInt(64);
                this.o += this.l.nextInt(64) - this.l.nextInt(64);
            }
            this.x = false;
        }
        this.h();
    }

    protected bd a(File file) {
        return new gs(this, this.q.a(file), this.q.c());
    }

    public void b() {
        if (this.n <= 0) {
            this.n = 64;
        }
        while (this.a(this.m, this.o) == 0) {
            this.m += this.l.nextInt(8) - this.l.nextInt(8);
            this.o += this.l.nextInt(8) - this.l.nextInt(8);
        }
    }

    public int a(int n2, int n3) {
        int n4 = 63;
        while (this.a(n2, n4 + 1, n3) != 0) {
            ++n4;
        }
        return this.a(n2, n4, n3);
    }

    public void c() {
    }

    public void a(Player eb2) {
        try {
            if (this.H != null) {
                eb2.e(this.H);
                this.H = null;
            }
            this.a((lw)eb2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(boolean bl2, pu pu2) {
        if (!this.G.b()) {
            return;
        }
        if (pu2 != null) {
            pu2.b("Saving level");
        }
        this.p();
        if (pu2 != null) {
            pu2.d("Saving chunks");
        }
        this.G.a(bl2, pu2);
    }

    private void p() {
        NBTCompoundTag iq2;
        this.n();
        NBTCompoundTag iq3 = new NBTCompoundTag();
        iq3.storeLong("RandomSeed", this.u);
        iq3.storeInt("SpawnX", this.m);
        iq3.storeInt("SpawnY", this.n);
        iq3.storeInt("SpawnZ", this.o);
        iq3.storeLong("Time", this.e);
        iq3.storeLong("SizeOnDisk", this.v);
        iq3.storeLong("LastPlayed", System.currentTimeMillis());
        Player eb2 = null;
        if (this.d.size() > 0) {
            eb2 = (Player)this.d.get(0);
        }
        if (eb2 != null) {
            iq2 = new NBTCompoundTag();
            eb2.d(iq2);
            iq3.storeCompoundTag("Player", iq2);
        }
        iq2 = new NBTCompoundTag();
        iq2.storeTag("Data", (NBTTag)iq3);
        try {
            File file = new File(this.t, "level.dat_new");
            File file2 = new File(this.t, "level.dat_old");
            File file3 = new File(this.t, "level.dat");
            ab.a(iq2, new FileOutputStream(file));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file.renameTo(file3);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean a(int n2) {
        if (!this.G.b()) {
            return true;
        }
        if (n2 == 0) {
            this.p();
        }
        return this.G.a(false, null);
    }

    @Override
    public int a(int n2, int n3, int n4) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return 0;
        }
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            return 0;
        }
        return this.c(n2 >> 4, n4 >> 4).a(n2 & 0xF, n3, n4 & 0xF);
    }

    public boolean d(int n2, int n3, int n4) {
        if (n3 < 0 || n3 >= 128) {
            return false;
        }
        return this.g(n2 >> 4, n4 >> 4);
    }

    public boolean a(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n6 < 0 || n3 >= 128) {
            return false;
        }
        n2 >>= 4;
        n3 >>= 4;
        n4 >>= 4;
        n5 >>= 4;
        n6 >>= 4;
        n7 >>= 4;
        for (int i2 = n2; i2 <= n5; ++i2) {
            for (int i3 = n4; i3 <= n7; ++i3) {
                if (this.g(i2, i3)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean g(int n2, int n3) {
        return this.G.a(n2, n3);
    }

    public ha b(int n2, int n3) {
        return this.c(n2 >> 4, n3 >> 4);
    }

    public ha c(int n2, int n3) {
        return this.G.b(n2, n3);
    }

    public boolean a(int n2, int n3, int n4, int n5, int n6) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return false;
        }
        if (n3 < 0) {
            return false;
        }
        if (n3 >= 128) {
            return false;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        return ha2.a(n2 & 0xF, n3, n4 & 0xF, n5, n6);
    }

    public boolean a(int n2, int n3, int n4, int n5) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return false;
        }
        if (n3 < 0) {
            return false;
        }
        if (n3 >= 128) {
            return false;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        return ha2.a(n2 & 0xF, n3, n4 & 0xF, n5);
    }

    @Override
    public hb f(int n2, int n3, int n4) {
        int n5 = this.a(n2, n3, n4);
        if (n5 == 0) {
            return hb.a;
        }
        return Block.registry[n5].bs;
    }

    @Override
    public int e(int n2, int n3, int n4) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return 0;
        }
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            return 0;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        return ha2.b(n2 &= 0xF, n3, n4 &= 0xF);
    }

    public void b(int n2, int n3, int n4, int n5) {
        if (this.c(n2, n3, n4, n5)) {
            this.e(n2, n3, n4, this.a(n2, n3, n4));
        }
    }

    public boolean c(int n2, int n3, int n4, int n5) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return false;
        }
        if (n3 < 0) {
            return false;
        }
        if (n3 >= 128) {
            return false;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        ha2.b(n2 &= 0xF, n3, n4 &= 0xF, n5);
        return true;
    }

    public boolean d(int n2, int n3, int n4, int n5) {
        if (this.a(n2, n3, n4, n5)) {
            this.e(n2, n3, n4, n5);
            return true;
        }
        return false;
    }

    public boolean b(int n2, int n3, int n4, int n5, int n6) {
        if (this.a(n2, n3, n4, n5, n6)) {
            this.e(n2, n3, n4, n5);
            return true;
        }
        return false;
    }

    public void h(int n2, int n3, int n4) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(n2, n3, n4);
        }
    }

    protected void e(int n2, int n3, int n4, int n5) {
        this.h(n2, n3, n4);
        this.g(n2, n3, n4, n5);
    }

    public void f(int n2, int n3, int n4, int n5) {
        if (n4 > n5) {
            int n6 = n5;
            n5 = n4;
            n4 = n6;
        }
        this.b(n2, n4, n3, n2, n5, n3);
    }

    public void b(int n2, int n3, int n4, int n5, int n6, int n7) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).b(n2, n3, n4, n5, n6, n7);
        }
    }

    public void g(int n2, int n3, int n4, int n5) {
        this.l(n2 - 1, n3, n4, n5);
        this.l(n2 + 1, n3, n4, n5);
        this.l(n2, n3 - 1, n4, n5);
        this.l(n2, n3 + 1, n4, n5);
        this.l(n2, n3, n4 - 1, n5);
        this.l(n2, n3, n4 + 1, n5);
    }

    private void l(int n2, int n3, int n4, int n5) {
        if (this.i || this.z) {
            return;
        }
        Block nq2 = Block.registry[this.a(n2, n3, n4)];
        if (nq2 != null) {
            nq2.a(this, n2, n3, n4, n5);
        }
    }

    public boolean i(int n2, int n3, int n4) {
        return this.c(n2 >> 4, n4 >> 4).c(n2 & 0xF, n3, n4 & 0xF);
    }

    public int j(int n2, int n3, int n4) {
        return this.a(n2, n3, n4, true);
    }

    public int a(int n2, int n3, int n4, boolean bl2) {
        int n5;
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return 15;
        }
        if (bl2 && ((n5 = this.a(n2, n3, n4)) == Block.ak.bh || n5 == Block.aA.bh)) {
            int n6 = this.a(n2, n3 + 1, n4, false);
            int n7 = this.a(n2 + 1, n3, n4, false);
            int n8 = this.a(n2 - 1, n3, n4, false);
            int n9 = this.a(n2, n3, n4 + 1, false);
            int n10 = this.a(n2, n3, n4 - 1, false);
            if (n7 > n6) {
                n6 = n7;
            }
            if (n8 > n6) {
                n6 = n8;
            }
            if (n9 > n6) {
                n6 = n9;
            }
            if (n10 > n6) {
                n6 = n10;
            }
            return n6;
        }
        if (n3 < 0) {
            return 0;
        }
        if (n3 >= 128) {
            n5 = 15 - this.f;
            if (n5 < 0) {
                n5 = 0;
            }
            return n5;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        return ha2.c(n2 &= 0xF, n3, n4 &= 0xF, this.f);
    }

    public boolean k(int n2, int n3, int n4) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return false;
        }
        if (n3 < 0) {
            return false;
        }
        if (n3 >= 128) {
            return true;
        }
        if (!this.g(n2 >> 4, n4 >> 4)) {
            return false;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        return ha2.c(n2 &= 0xF, n3, n4 &= 0xF);
    }

    public int d(int n2, int n3) {
        if (n2 < -32000000 || n3 < -32000000 || n2 >= 32000000 || n3 > 32000000) {
            return 0;
        }
        if (!this.g(n2 >> 4, n3 >> 4)) {
            return 0;
        }
        ha ha2 = this.c(n2 >> 4, n3 >> 4);
        return ha2.b(n2 & 0xF, n3 & 0xF);
    }

    public void a(ch ch2, int n2, int n3, int n4, int n5) {
        int n6;
        if (this.q.e && ch2 == ch.Sky) {
            return;
        }
        if (!this.d(n2, n3, n4)) {
            return;
        }
        if (ch2 == ch.Sky) {
            if (this.k(n2, n3, n4)) {
                n5 = 15;
            }
        } else if (ch2 == ch.Block && Block.s[n6 = this.a(n2, n3, n4)] > n5) {
            n5 = Block.s[n6];
        }
        if (this.a(ch2, n2, n3, n4) != n5) {
            this.a(ch2, n2, n3, n4, n2, n3, n4);
        }
    }

    public int a(ch ch2, int n2, int n3, int n4) {
        if (n3 < 0 || n3 >= 128 || n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return ch2.c;
        }
        int n5 = n2 >> 4;
        int n6 = n4 >> 4;
        if (!this.g(n5, n6)) {
            return 0;
        }
        ha ha2 = this.c(n5, n6);
        return ha2.a(ch2, n2 & 0xF, n3, n4 & 0xF);
    }

    public void b(ch ch2, int n2, int n3, int n4, int n5) {
        if (n2 < -32000000 || n4 < -32000000 || n2 >= 32000000 || n4 > 32000000) {
            return;
        }
        if (n3 < 0) {
            return;
        }
        if (n3 >= 128) {
            return;
        }
        if (!this.g(n2 >> 4, n4 >> 4)) {
            return;
        }
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        ha2.a(ch2, n2 & 0xF, n3, n4 & 0xF, n5);
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(n2, n3, n4);
        }
    }

    @Override
    public float c(int n2, int n3, int n4) {
        return this.q.f[this.j(n2, n3, n4)];
    }

    public boolean d() {
        return this.f < 4;
    }

    public nx a(Vector3D ao2, Vector3D ao3) {
        return this.a(ao2, ao3, false);
    }

    public nx a(Vector3D ao2, Vector3D ao3, boolean bl2) {
        if (Double.isNaN(ao2.x) || Double.isNaN(ao2.y) || Double.isNaN(ao2.z)) {
            return null;
        }
        if (Double.isNaN(ao3.x) || Double.isNaN(ao3.y) || Double.isNaN(ao3.z)) {
            return null;
        }
        int n2 = TrigLookup.b(ao3.x);
        int n3 = TrigLookup.b(ao3.y);
        int n4 = TrigLookup.b(ao3.z);
        int n5 = TrigLookup.b(ao2.x);
        int n6 = TrigLookup.b(ao2.y);
        int n7 = TrigLookup.b(ao2.z);
        int n8 = 200;
        while (n8-- >= 0) {
            nx nx2;
            if (Double.isNaN(ao2.x) || Double.isNaN(ao2.y) || Double.isNaN(ao2.z)) {
                return null;
            }
            if (n5 == n2 && n6 == n3 && n7 == n4) {
                return null;
            }
            double d2 = 999.0;
            double d3 = 999.0;
            double d4 = 999.0;
            if (n2 > n5) {
                d2 = (double)n5 + 1.0;
            }
            if (n2 < n5) {
                d2 = (double)n5 + 0.0;
            }
            if (n3 > n6) {
                d3 = (double)n6 + 1.0;
            }
            if (n3 < n6) {
                d3 = (double)n6 + 0.0;
            }
            if (n4 > n7) {
                d4 = (double)n7 + 1.0;
            }
            if (n4 < n7) {
                d4 = (double)n7 + 0.0;
            }
            double d5 = 999.0;
            double d6 = 999.0;
            double d7 = 999.0;
            double d8 = ao3.x - ao2.x;
            double d9 = ao3.y - ao2.y;
            double d10 = ao3.z - ao2.z;
            if (d2 != 999.0) {
                d5 = (d2 - ao2.x) / d8;
            }
            if (d3 != 999.0) {
                d6 = (d3 - ao2.y) / d9;
            }
            if (d4 != 999.0) {
                d7 = (d4 - ao2.z) / d10;
            }
            int n9 = 0;
            if (d5 < d6 && d5 < d7) {
                n9 = n2 > n5 ? 4 : 5;
                ao2.x = d2;
                ao2.y += d9 * d5;
                ao2.z += d10 * d5;
            } else if (d6 < d7) {
                n9 = n3 > n6 ? 0 : 1;
                ao2.x += d8 * d6;
                ao2.y = d3;
                ao2.z += d10 * d6;
            } else {
                n9 = n4 > n7 ? 2 : 3;
                ao2.x += d8 * d7;
                ao2.y += d9 * d7;
                ao2.z = d4;
            }
            Vector3D ao4 = ao2.b(ao2.x, ao2.y, ao2.z);
            ao4.x = TrigLookup.b(ao2.x);
            n5 = (int)ao4.x;
            if (n9 == 5) {
                --n5;
                ao4.x += 1.0;
            }
            ao4.y = TrigLookup.b(ao2.y);
            n6 = (int)ao4.y;
            if (n9 == 1) {
                --n6;
                ao4.y += 1.0;
            }
            ao4.z = TrigLookup.b(ao2.z);
            n7 = (int)ao4.z;
            if (n9 == 3) {
                --n7;
                ao4.z += 1.0;
            }
            int n10 = this.a(n5, n6, n7);
            int n11 = this.e(n5, n6, n7);
            Block nq2 = Block.registry[n10];
            if (n10 <= 0 || !nq2.a(n11, bl2) || (nx2 = nq2.a(this, n5, n6, n7, ao2, ao3)) == null) continue;
            return nx2;
        }
        return null;
    }

    public void a(lw lw2, String string, float f2, float f3) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(string, lw2.aw, lw2.ax - (double)lw2.aO, lw2.ay, f2, f3);
        }
    }

    public void a(double d2, double d3, double d4, String string, float f2, float f3) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(string, d2, d3, d4, f2, f3);
        }
    }

    public void a(String string, int n2, int n3, int n4) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(string, n2, n3, n4);
        }
    }

    public void a(String string, double d2, double d3, double d4, double d5, double d6, double d7) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(string, d2, d3, d4, d5, d6, d7);
        }
    }

    public boolean a(lw lw2) {
        int n2 = TrigLookup.b(lw2.aw / 16.0);
        int n3 = TrigLookup.b(lw2.ay / 16.0);
        boolean bl2 = false;
        if (lw2 instanceof Player) {
            bl2 = true;
        }
        if (bl2 || this.g(n2, n3)) {
            if (lw2 instanceof Player) {
                this.d.add((Player)lw2);
                System.out.println("Player count: " + this.d.size());
            }
            this.c(n2, n3).a(lw2);
            this.b.add(lw2);
            this.b(lw2);
            return true;
        }
        return false;
    }

    protected void b(lw lw2) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(lw2);
        }
    }

    protected void c(lw lw2) {
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).b(lw2);
        }
    }

    public void d(lw lw2) {
        lw2.J();
        if (lw2 instanceof Player) {
            this.d.remove((Player)lw2);
        }
    }

    public void a(jv jv2) {
        this.r.add(jv2);
    }

    public void b(jv jv2) {
        this.r.remove(jv2);
    }

    public List a(lw lw2, co co2) {
        this.I.clear();
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n6; i3 < n7; ++i3) {
                if (!this.d(i2, 64, i3)) continue;
                for (int i4 = n4 - 1; i4 < n5; ++i4) {
                    Block nq2 = Block.registry[this.a(i2, i4, i3)];
                    if (nq2 == null) continue;
                    nq2.a(this, i2, i4, i3, co2, this.I);
                }
            }
        }
        double d2 = 0.25;
        List list = this.b(lw2, co2.b(d2, d2, d2));
        for (int i5 = 0; i5 < list.size(); ++i5) {
            co co3 = ((lw)list.get(i5)).e();
            if (co3 != null && co3.a(co2)) {
                this.I.add(co3);
            }
            if ((co3 = lw2.a((lw)list.get(i5))) == null || !co3.a(co2)) continue;
            this.I.add(co3);
        }
        return this.I;
    }

    public int a(float f2) {
        float f3 = this.b(f2);
        float f4 = 1.0f - (TrigLookup.b(f3 * (float)Math.PI * 2.0f) * 2.0f + 0.5f);
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        return (int)(f4 * 11.0f);
    }

    public Vector3D a(lw lw2, float f2) {
        float f3 = this.b(f2);
        float f4 = TrigLookup.b(f3 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        int n2 = TrigLookup.b(lw2.aw);
        int n3 = TrigLookup.b(lw2.ay);
        float f5 = (float)this.a().b(n2, n3);
        int n4 = this.a().a(n2, n3).a(f5);
        float f6 = (float)(n4 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n4 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n4 & 0xFF) / 255.0f;
        return Vector3D.b(f6 *= f4, f7 *= f4, f8 *= f4);
    }

    public float b(float f2) {
        return this.q.a(this.e, f2);
    }

    public Vector3D c(float f2) {
        float f3 = this.b(f2);
        float f4 = TrigLookup.b(f3 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        float f5 = (float)(this.E >> 16 & 0xFFL) / 255.0f;
        float f6 = (float)(this.E >> 8 & 0xFFL) / 255.0f;
        float f7 = (float)(this.E & 0xFFL) / 255.0f;
        return Vector3D.b(f5 *= f4 * 0.9f + 0.1f, f6 *= f4 * 0.9f + 0.1f, f7 *= f4 * 0.85f + 0.15f);
    }

    public Vector3D d(float f2) {
        float f3 = this.b(f2);
        return this.q.a(f3, f2);
    }

    public int e(int n2, int n3) {
        int n4;
        ha ha2 = this.b(n2, n3);
        for (n4 = 127; this.f(n2, n4, n3).c() && n4 > 0; --n4) {
        }
        n2 &= 0xF;
        n3 &= 0xF;
        while (n4 > 0) {
            int n5 = ha2.a(n2, n4, n3);
            if (n5 == 0 || !Block.registry[n5].bs.c() && !Block.registry[n5].bs.d()) {
                --n4;
                continue;
            }
            return n4 + 1;
        }
        return -1;
    }

    public int f(int n2, int n3) {
        return this.b(n2, n3).b(n2 & 0xF, n3 & 0xF);
    }

    public float e(float f2) {
        float f3 = this.b(f2);
        float f4 = 1.0f - (TrigLookup.b(f3 * (float)Math.PI * 2.0f) * 2.0f + 0.75f);
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        return f4 * f4 * 0.5f;
    }

    public void h(int n2, int n3, int n4, int n5) {
        kq kq2 = new kq(n2, n3, n4, n5);
        int n6 = 8;
        if (this.a) {
            int n7;
            if (this.a(kq2.a - n6, kq2.b - n6, kq2.c - n6, kq2.a + n6, kq2.b + n6, kq2.c + n6) && (n7 = this.a(kq2.a, kq2.b, kq2.c)) == kq2.d && n7 > 0) {
                Block.registry[n7].a(this, kq2.a, kq2.b, kq2.c, this.l);
            }
            return;
        }
        if (this.a(n2 - n6, n3 - n6, n4 - n6, n2 + n6, n3 + n6, n4 + n6)) {
            if (n5 > 0) {
                kq2.a((long)Block.registry[n5].d() + this.e);
            }
            if (!this.D.contains(kq2)) {
                this.D.add(kq2);
                this.C.add(kq2);
            }
        }
    }

    public void e() {
        int n2;
        int n3;
        Object object;
        int n4;
        this.b.removeAll(this.B);
        for (n4 = 0; n4 < this.B.size(); ++n4) {
            object = (lw)this.B.get(n4);
            n3 = ((lw)object).bo;
            n2 = ((lw)object).bq;
            if (!((lw)object).bn || !this.g(n3, n2)) continue;
            this.c(n3, n2).b((lw)object);
        }
        for (n4 = 0; n4 < this.B.size(); ++n4) {
            this.c((lw)this.B.get(n4));
        }
        this.B.clear();
        for (n4 = 0; n4 < this.b.size(); ++n4) {
            object = (lw)this.b.get(n4);
            if (((lw)object).ar != null) {
                if (!((lw)object).ar.aN && ((lw)object).ar.aq == object) continue;
                ((lw)object).ar.aq = null;
                ((lw)object).ar = null;
            }
            if (!((lw)object).aN) {
                this.e((lw)object);
            }
            if (!((lw)object).aN) continue;
            n3 = ((lw)object).bo;
            n2 = ((lw)object).bq;
            if (((lw)object).bn && this.g(n3, n2)) {
                this.c(n3, n2).b((lw)object);
            }
            this.b.remove(n4--);
            this.c((lw)object);
        }
        for (n4 = 0; n4 < this.c.size(); ++n4) {
            object = (TileEntityRegistry)this.c.get(n4);
            ((TileEntityRegistry)object).b();
        }
    }

    public void e(lw lw2) {
        this.a(lw2, true);
    }

    public void a(lw lw2, boolean bl2) {
        int n2 = TrigLookup.b(lw2.aw);
        int n3 = TrigLookup.b(lw2.ay);
        int n4 = 16;
        if (!bl2 && !this.a(n2 - n4, 0, n3 - n4, n2 + n4, 128, n3 + n4)) {
            return;
        }
        lw2.aV = lw2.aw;
        lw2.aW = lw2.ax;
        lw2.aX = lw2.ay;
        lw2.aE = lw2.aC;
        lw2.aF = lw2.aD;
        if (bl2 && lw2.bn) {
            if (lw2.ar != null) {
                lw2.s();
            } else {
                lw2.e_();
            }
        }
        if (Double.isNaN(lw2.aw) || Double.isInfinite(lw2.aw)) {
            lw2.aw = lw2.aV;
        }
        if (Double.isNaN(lw2.ax) || Double.isInfinite(lw2.ax)) {
            lw2.ax = lw2.aW;
        }
        if (Double.isNaN(lw2.ay) || Double.isInfinite(lw2.ay)) {
            lw2.ay = lw2.aX;
        }
        if (Double.isNaN(lw2.aD) || Double.isInfinite(lw2.aD)) {
            lw2.aD = lw2.aF;
        }
        if (Double.isNaN(lw2.aC) || Double.isInfinite(lw2.aC)) {
            lw2.aC = lw2.aE;
        }
        int n5 = TrigLookup.b(lw2.aw / 16.0);
        int n6 = TrigLookup.b(lw2.ax / 16.0);
        int n7 = TrigLookup.b(lw2.ay / 16.0);
        if (!lw2.bn || lw2.bo != n5 || lw2.bp != n6 || lw2.bq != n7) {
            if (lw2.bn && this.g(lw2.bo, lw2.bq)) {
                this.c(lw2.bo, lw2.bq).a(lw2, lw2.bp);
            }
            if (this.g(n5, n7)) {
                lw2.bn = true;
                this.c(n5, n7).a(lw2);
            } else {
                lw2.bn = false;
            }
        }
        if (bl2 && lw2.bn && lw2.aq != null) {
            if (lw2.aq.aN || lw2.aq.ar != lw2) {
                lw2.aq.ar = null;
                lw2.aq = null;
            } else {
                this.e(lw2.aq);
            }
        }
    }

    public boolean a(co co2) {
        List list = this.b(null, co2);
        for (int i2 = 0; i2 < list.size(); ++i2) {
            lw lw2 = (lw)list.get(i2);
            if (lw2.aN || !lw2.ap) continue;
            return false;
        }
        return true;
    }

    public boolean b(co co2) {
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        if (co2.a < 0.0) {
            --n2;
        }
        if (co2.b < 0.0) {
            --n4;
        }
        if (co2.c < 0.0) {
            --n6;
        }
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n4; i3 < n5; ++i3) {
                for (int i4 = n6; i4 < n7; ++i4) {
                    Block nq2 = Block.registry[this.a(i2, i3, i4)];
                    if (nq2 == null || !nq2.bs.d()) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean c(co co2) {
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n4; i3 < n5; ++i3) {
                for (int i4 = n6; i4 < n7; ++i4) {
                    int n8 = this.a(i2, i3, i4);
                    if (n8 != Block.ar.bh && n8 != Block.C.bh && n8 != Block.D.bh) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean a(co co2, hb hb2, lw lw2) {
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        boolean bl2 = false;
        Vector3D ao2 = Vector3D.b(0.0, 0.0, 0.0);
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n4; i3 < n5; ++i3) {
                for (int i4 = n6; i4 < n7; ++i4) {
                    double d2;
                    Block nq2 = Block.registry[this.a(i2, i3, i4)];
                    if (nq2 == null || nq2.bs != hb2 || !((double)n5 >= (d2 = (double)((float)(i3 + 1) - ld.b(this.e(i2, i3, i4)))))) continue;
                    bl2 = true;
                    nq2.a(this, i2, i3, i4, lw2, ao2);
                }
            }
        }
        if (ao2.c() > 0.0) {
            ao2 = ao2.b();
            double d3 = 0.004;
            lw2.az += ao2.x * d3;
            lw2.aA += ao2.y * d3;
            lw2.aB += ao2.z * d3;
        }
        return bl2;
    }

    public boolean a(co co2, hb hb2) {
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n4; i3 < n5; ++i3) {
                for (int i4 = n6; i4 < n7; ++i4) {
                    Block nq2 = Block.registry[this.a(i2, i3, i4)];
                    if (nq2 == null || nq2.bs != hb2) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean b(co co2, hb hb2) {
        int n2 = TrigLookup.b(co2.a);
        int n3 = TrigLookup.b(co2.d + 1.0);
        int n4 = TrigLookup.b(co2.b);
        int n5 = TrigLookup.b(co2.e + 1.0);
        int n6 = TrigLookup.b(co2.c);
        int n7 = TrigLookup.b(co2.f + 1.0);
        for (int i2 = n2; i2 < n3; ++i2) {
            for (int i3 = n4; i3 < n5; ++i3) {
                for (int i4 = n6; i4 < n7; ++i4) {
                    Block nq2 = Block.registry[this.a(i2, i3, i4)];
                    if (nq2 == null || nq2.bs != hb2) continue;
                    int n8 = this.e(i2, i3, i4);
                    double d2 = i3 + 1;
                    if (n8 < 8) {
                        d2 = (double)(i3 + 1) - (double)n8 / 8.0;
                    }
                    if (!(d2 >= co2.b)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public ks a(lw lw2, double d2, double d3, double d4, float f2) {
        return this.a(lw2, d2, d3, d4, f2, false);
    }

    public ks a(lw lw2, double d2, double d3, double d4, float f2, boolean bl2) {
        ks ks2 = new ks(this, lw2, d2, d3, d4, f2);
        ks2.a = bl2;
        ks2.a();
        ks2.b();
        return ks2;
    }

    public float a(Vector3D ao2, co co2) {
        double d2 = 1.0 / ((co2.d - co2.a) * 2.0 + 1.0);
        double d3 = 1.0 / ((co2.e - co2.b) * 2.0 + 1.0);
        double d4 = 1.0 / ((co2.f - co2.c) * 2.0 + 1.0);
        int n2 = 0;
        int n3 = 0;
        float f2 = 0.0f;
        while (f2 <= 1.0f) {
            float f3 = 0.0f;
            while (f3 <= 1.0f) {
                float f4 = 0.0f;
                while (f4 <= 1.0f) {
                    double d5 = co2.a + (co2.d - co2.a) * (double)f2;
                    double d6 = co2.b + (co2.e - co2.b) * (double)f3;
                    double d7 = co2.c + (co2.f - co2.c) * (double)f4;
                    if (this.a(ao2.b(d5, d6, d7), ao2) == null) {
                        ++n2;
                    }
                    ++n3;
                    f4 = (float)((double)f4 + d4);
                }
                f3 = (float)((double)f3 + d3);
            }
            f2 = (float)((double)f2 + d2);
        }
        return (float)n2 / (float)n3;
    }

    public void i(int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            --n3;
        }
        if (n5 == 1) {
            ++n3;
        }
        if (n5 == 2) {
            --n4;
        }
        if (n5 == 3) {
            ++n4;
        }
        if (n5 == 4) {
            --n2;
        }
        if (n5 == 5) {
            ++n2;
        }
        if (this.a(n2, n3, n4) == Block.ar.bh) {
            this.a((float)n2 + 0.5f, (double)((float)n3 + 0.5f), (double)((float)n4 + 0.5f), "random.fizz", 0.5f, 2.6f + (this.l.nextFloat() - this.l.nextFloat()) * 0.8f);
            this.d(n2, n3, n4, 0);
        }
    }

    public lw a(Class clazz) {
        return null;
    }

    public String f() {
        return "All: " + this.b.size();
    }

    @Override
    public TileEntityRegistry b(int n2, int n3, int n4) {
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        if (ha2 != null) {
            return ha2.d(n2 & 0xF, n3, n4 & 0xF);
        }
        return null;
    }

    public void a(int n2, int n3, int n4, TileEntityRegistry ji2) {
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        if (ha2 != null) {
            ha2.a(n2 & 0xF, n3, n4 & 0xF, ji2);
        }
    }

    public void l(int n2, int n3, int n4) {
        ha ha2 = this.c(n2 >> 4, n4 >> 4);
        if (ha2 != null) {
            ha2.e(n2 & 0xF, n3, n4 & 0xF);
        }
    }

    @Override
    public boolean g(int n2, int n3, int n4) {
        Block nq2 = Block.registry[this.a(n2, n3, n4)];
        if (nq2 == null) {
            return false;
        }
        return nq2.a();
    }

    public void a(pu pu2) {
        this.a(true, pu2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean g() {
        if (this.J >= 50) {
            return false;
        }
        ++this.J;
        try {
            int n2 = 5000;
            while (this.A.size() > 0) {
                if (--n2 <= 0) {
                    boolean bl2 = true;
                    return bl2;
                }
                ((mc)this.A.remove(this.A.size() - 1)).a(this);
            }
            boolean bl3 = false;
            return bl3;
        }
        finally {
            --this.J;
        }
    }

    public void a(ch ch2, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.a(ch2, n2, n3, n4, n5, n6, n7, true);
    }

    public void a(ch ch2, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2) {
        if (this.q.e && ch2 == ch.Sky) {
            return;
        }
        if (++y == 50) {
            --y;
            return;
        }
        int n8 = (n5 + n2) / 2;
        int n9 = (n7 + n4) / 2;
        if (!this.d(n8, 64, n9)) {
            --y;
            return;
        }
        int n10 = this.A.size();
        if (bl2) {
            int n11 = 4;
            if (n11 > n10) {
                n11 = n10;
            }
            for (int i2 = 0; i2 < n11; ++i2) {
                mc mc2 = (mc)this.A.get(this.A.size() - i2 - 1);
                if (mc2.a != ch2 || !mc2.a(n2, n3, n4, n5, n6, n7)) continue;
                --y;
                return;
            }
        }
        this.A.add(new mc(ch2, n2, n3, n4, n5, n6, n7));
        if (this.A.size() > 100000) {
            this.A.clear();
        }
        --y;
    }

    public void h() {
        int n2 = this.a(1.0f);
        if (n2 != this.f) {
            this.f = n2;
        }
    }

    public void i() {
        bg.a(this);
        this.G.a();
        int n2 = this.a(1.0f);
        if (n2 != this.f) {
            this.f = n2;
            for (int i2 = 0; i2 < this.r.size(); ++i2) {
                ((jv)this.r.get(i2)).e();
            }
        }
        ++this.e;
        if (this.e % (long)this.j == 0L) {
            this.a(false, null);
        }
        this.a(false);
        this.j();
    }

    protected void j() {
        int n2;
        int n3;
        int n4;
        int n5;
        this.K.clear();
        for (int i2 = 0; i2 < this.d.size(); ++i2) {
            Object object = (Player)this.d.get(i2);
            n5 = TrigLookup.b(((Player)object).aw / 16.0);
            n4 = TrigLookup.b(((Player)object).ay / 16.0);
            int n6 = 9;
            for (n3 = -n6; n3 <= n6; ++n3) {
                for (n2 = -n6; n2 <= n6; ++n2) {
                    this.K.add(new qm(n3 + n5, n2 + n4));
                }
            }
        }
        if (this.L > 0) {
            --this.L;
        }
        for (Object object : this.K) {
            int n7;
            int n8;
            int n9;
            n5 = ((qm)object).a * 16;
            n4 = ((qm)object).b * 16;
            ha ha2 = this.c(((qm)object).a, ((qm)object).b);
            if (this.L == 0) {
                Player eb2;
                this.g = this.g * 3 + this.h;
                n3 = this.g >> 2;
                n2 = n3 & 0xF;
                n9 = n3 >> 8 & 0xF;
                n8 = n3 >> 16 & 0x7F;
                n7 = ha2.a(n2, n8, n9);
                if (n7 == 0 && this.j(n2 += n5, n8, n9 += n4) <= this.l.nextInt(8) && this.a(ch.Sky, n2, n8, n9) <= 0 && (eb2 = this.a((double)n2 + 0.5, (double)n8 + 0.5, (double)n9 + 0.5, 8.0)) != null && eb2.e((double)n2 + 0.5, (double)n8 + 0.5, (double)n9 + 0.5) > 4.0) {
                    this.a((double)n2 + 0.5, (double)n8 + 0.5, (double)n9 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.l.nextFloat() * 0.2f);
                    this.L = this.l.nextInt(12000) + 6000;
                }
            }
            for (n3 = 0; n3 < 80; ++n3) {
                this.g = this.g * 3 + this.h;
                n2 = this.g >> 2;
                n9 = n2 & 0xF;
                n8 = n2 >> 8 & 0xF;
                n7 = n2 >> 16 & 0x7F;
                byte by2 = ha2.b[n9 << 11 | n8 << 7 | n7];
                if (!Block.n[by2]) continue;
                Block.registry[by2].a(this, n9 + n5, n7, n8 + n4, this.l);
            }
        }
    }

    public boolean a(boolean bl2) {
        int n2 = this.C.size();
        if (n2 != this.D.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (n2 > 1000) {
            n2 = 1000;
        }
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3;
            kq kq2 = (kq)this.C.first();
            if (!bl2 && kq2.e > this.e) break;
            this.C.remove(kq2);
            this.D.remove(kq2);
            int n4 = 8;
            if (!this.a(kq2.a - n4, kq2.b - n4, kq2.c - n4, kq2.a + n4, kq2.b + n4, kq2.c + n4) || (n3 = this.a(kq2.a, kq2.b, kq2.c)) != kq2.d || n3 <= 0) continue;
            Block.registry[n3].a(this, kq2.a, kq2.b, kq2.c, this.l);
        }
        return this.C.size() != 0;
    }

    public void m(int n2, int n3, int n4) {
        int n5 = 16;
        Random random = new Random();
        for (int i2 = 0; i2 < 1000; ++i2) {
            int n6;
            int n7;
            int n8 = n2 + this.l.nextInt(n5) - this.l.nextInt(n5);
            int n9 = this.a(n8, n7 = n3 + this.l.nextInt(n5) - this.l.nextInt(n5), n6 = n4 + this.l.nextInt(n5) - this.l.nextInt(n5));
            if (n9 <= 0) continue;
            Block.registry[n9].b(this, n8, n7, n6, random);
        }
    }

    public List b(lw lw2, co co2) {
        this.M.clear();
        int n2 = TrigLookup.b((co2.a - 2.0) / 16.0);
        int n3 = TrigLookup.b((co2.d + 2.0) / 16.0);
        int n4 = TrigLookup.b((co2.c - 2.0) / 16.0);
        int n5 = TrigLookup.b((co2.f + 2.0) / 16.0);
        for (int i2 = n2; i2 <= n3; ++i2) {
            for (int i3 = n4; i3 <= n5; ++i3) {
                if (!this.g(i2, i3)) continue;
                this.c(i2, i3).a(lw2, co2, this.M);
            }
        }
        return this.M;
    }

    public List a(Class clazz, co co2) {
        int n2 = TrigLookup.b((co2.a - 2.0) / 16.0);
        int n3 = TrigLookup.b((co2.d + 2.0) / 16.0);
        int n4 = TrigLookup.b((co2.c - 2.0) / 16.0);
        int n5 = TrigLookup.b((co2.f + 2.0) / 16.0);
        ArrayList arrayList = new ArrayList();
        for (int i2 = n2; i2 <= n3; ++i2) {
            for (int i3 = n4; i3 <= n5; ++i3) {
                if (!this.g(i2, i3)) continue;
                this.c(i2, i3).a(clazz, co2, arrayList);
            }
        }
        return arrayList;
    }

    public List k() {
        return this.b;
    }

    public void b(int n2, int n3, int n4, TileEntityRegistry ji2) {
        if (this.d(n2, n3, n4)) {
            this.b(n2, n4).g();
        }
        for (int i2 = 0; i2 < this.r.size(); ++i2) {
            ((jv)this.r.get(i2)).a(n2, n3, n4, ji2);
        }
    }

    public int b(Class clazz) {
        int n2 = 0;
        for (int i2 = 0; i2 < this.b.size(); ++i2) {
            lw lw2 = (lw)this.b.get(i2);
            if (!clazz.isAssignableFrom(lw2.getClass())) continue;
            ++n2;
        }
        return n2;
    }

    public void a(List list) {
        this.b.addAll(list);
        for (int i2 = 0; i2 < list.size(); ++i2) {
            this.b((lw)list.get(i2));
        }
    }

    public void b(List list) {
        this.B.addAll(list);
    }

    public void l() {
        while (this.G.a()) {
        }
    }

    public boolean a(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = this.a(n3, n4, n5);
        Block nq2 = Block.registry[n6];
        Block nq3 = Block.registry[n2];
        co co2 = nq3.d(this, n3, n4, n5);
        if (bl2) {
            co2 = null;
        }
        if (co2 != null && !this.a(co2)) {
            return false;
        }
        if (nq2 == Block.A || nq2 == Block.B || nq2 == Block.C || nq2 == Block.D || nq2 == Block.ar || nq2 == Block.aS) {
            return true;
        }
        return n2 > 0 && nq2 == null && nq3.a(this, n3, n4, n5);
    }

    public bt a(lw lw2, lw lw3, float f2) {
        int n2 = TrigLookup.b(lw2.aw);
        int n3 = TrigLookup.b(lw2.ax);
        int n4 = TrigLookup.b(lw2.ay);
        int n5 = (int)(f2 + 16.0f);
        int n6 = n2 - n5;
        int n7 = n3 - n5;
        int n8 = n4 - n5;
        int n9 = n2 + n5;
        int n10 = n3 + n5;
        int n11 = n4 + n5;
        cs cs2 = new cs(this, n6, n7, n8, n9, n10, n11);
        return new dm(cs2).a(lw2, lw3, f2);
    }

    public bt a(lw lw2, int n2, int n3, int n4, float f2) {
        int n5 = TrigLookup.b(lw2.aw);
        int n6 = TrigLookup.b(lw2.ax);
        int n7 = TrigLookup.b(lw2.ay);
        int n8 = (int)(f2 + 8.0f);
        int n9 = n5 - n8;
        int n10 = n6 - n8;
        int n11 = n7 - n8;
        int n12 = n5 + n8;
        int n13 = n6 + n8;
        int n14 = n7 + n8;
        cs cs2 = new cs(this, n9, n10, n11, n12, n13, n14);
        return new dm(cs2).a(lw2, n2, n3, n4, f2);
    }

    public boolean j(int n2, int n3, int n4, int n5) {
        int n6 = this.a(n2, n3, n4);
        if (n6 == 0) {
            return false;
        }
        return Block.registry[n6].c(this, n2, n3, n4, n5);
    }

    public boolean n(int n2, int n3, int n4) {
        if (this.j(n2, n3 - 1, n4, 0)) {
            return true;
        }
        if (this.j(n2, n3 + 1, n4, 1)) {
            return true;
        }
        if (this.j(n2, n3, n4 - 1, 2)) {
            return true;
        }
        if (this.j(n2, n3, n4 + 1, 3)) {
            return true;
        }
        if (this.j(n2 - 1, n3, n4, 4)) {
            return true;
        }
        return this.j(n2 + 1, n3, n4, 5);
    }

    public boolean k(int n2, int n3, int n4, int n5) {
        if (this.g(n2, n3, n4)) {
            return this.n(n2, n3, n4);
        }
        int n6 = this.a(n2, n3, n4);
        if (n6 == 0) {
            return false;
        }
        return Block.registry[n6].c((pk)this, n2, n3, n4, n5);
    }

    public boolean o(int n2, int n3, int n4) {
        if (this.k(n2, n3 - 1, n4, 0)) {
            return true;
        }
        if (this.k(n2, n3 + 1, n4, 1)) {
            return true;
        }
        if (this.k(n2, n3, n4 - 1, 2)) {
            return true;
        }
        if (this.k(n2, n3, n4 + 1, 3)) {
            return true;
        }
        if (this.k(n2 - 1, n3, n4, 4)) {
            return true;
        }
        return this.k(n2 + 1, n3, n4, 5);
    }

    public Player a(lw lw2, double d2) {
        return this.a(lw2.aw, lw2.ax, lw2.ay, d2);
    }

    public Player a(double d2, double d3, double d4, double d5) {
        double d6 = -1.0;
        Player eb2 = null;
        for (int i2 = 0; i2 < this.d.size(); ++i2) {
            Player eb3 = (Player)this.d.get(i2);
            double d7 = eb3.e(d2, d3, d4);
            if (!(d5 < 0.0) && !(d7 < d5 * d5) || d6 != -1.0 && !(d7 < d6)) continue;
            d6 = d7;
            eb2 = eb3;
        }
        return eb2;
    }

    public void a(int n2, int n3, int n4, int n5, int n6, int n7, byte[] byArray) {
        int n8 = n2 >> 4;
        int n9 = n4 >> 4;
        int n10 = n2 + n5 - 1 >> 4;
        int n11 = n4 + n7 - 1 >> 4;
        int n12 = 0;
        int n13 = n3;
        int n14 = n3 + n6;
        if (n13 < 0) {
            n13 = 0;
        }
        if (n14 > 128) {
            n14 = 128;
        }
        for (int i2 = n8; i2 <= n10; ++i2) {
            int n15 = n2 - i2 * 16;
            int n16 = n2 + n5 - i2 * 16;
            if (n15 < 0) {
                n15 = 0;
            }
            if (n16 > 16) {
                n16 = 16;
            }
            for (int i3 = n9; i3 <= n11; ++i3) {
                int n17 = n4 - i3 * 16;
                int n18 = n4 + n7 - i3 * 16;
                if (n17 < 0) {
                    n17 = 0;
                }
                if (n18 > 16) {
                    n18 = 16;
                }
                n12 = this.c(i2, i3).a(byArray, n15, n13, n17, n16, n14, n18, n12);
                this.b(i2 * 16 + n15, n13, i3 * 16 + n17, i2 * 16 + n16, n14, i3 * 16 + n18);
            }
        }
    }

    public void m() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void n() {
        try {
            File file = new File(this.t, "session.lock");
            try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));){
                if (dataInputStream.readLong() != this.F) {
                    throw new np("The save is being accessed from another location, aborting");
                }
            }
        }
        catch (IOException iOException) {
            throw new np("Failed to check session lock, aborting");
        }
    }

    public void a(long l2) {
        this.e = l2;
    }

    public void f(lw lw2) {
        int n2 = TrigLookup.b(lw2.aw / 16.0);
        int n3 = TrigLookup.b(lw2.ay / 16.0);
        int n4 = 2;
        for (int i2 = n2 - n4; i2 <= n2 + n4; ++i2) {
            for (int i3 = n3 - n4; i3 <= n3 + n4; ++i3) {
                this.c(i2, i3);
            }
        }
        if (!this.b.contains(lw2)) {
            this.b.add(lw2);
        }
    }

    public boolean a(Player eb2, int n2, int n3, int n4) {
        return true;
    }

    public void a(lw lw2, byte by2) {
    }

    public void o() {
        int n2;
        int n3;
        lw lw2;
        int n4;
        this.b.removeAll(this.B);
        for (n4 = 0; n4 < this.B.size(); ++n4) {
            lw2 = (lw)this.B.get(n4);
            n3 = lw2.bo;
            n2 = lw2.bq;
            if (!lw2.bn || !this.g(n3, n2)) continue;
            this.c(n3, n2).b(lw2);
        }
        for (n4 = 0; n4 < this.B.size(); ++n4) {
            this.c((lw)this.B.get(n4));
        }
        this.B.clear();
        for (n4 = 0; n4 < this.b.size(); ++n4) {
            lw2 = (lw)this.b.get(n4);
            if (lw2.ar != null) {
                if (!lw2.ar.aN && lw2.ar.aq == lw2) continue;
                lw2.ar.aq = null;
                lw2.ar = null;
            }
            if (!lw2.aN) continue;
            n3 = lw2.bo;
            n2 = lw2.bq;
            if (lw2.bn && this.g(n3, n2)) {
                this.c(n3, n2).b(lw2);
            }
            this.b.remove(n4--);
            this.c(lw2);
        }
    }
}

