/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.Random;

public class Block {
    public static final SoundSteps stonestepsound = new SoundSteps("stone", 1.0f, 1.0f);
    public static final SoundSteps woodstepsound = new SoundSteps("wood", 1.0f, 1.0f);
    public static final SoundSteps gravelstepsound = new SoundSteps("gravel", 1.0f, 1.0f);
    public static final SoundSteps grassstepsound = new SoundSteps("grass", 1.0f, 1.0f);
    public static final SoundSteps h = new SoundSteps("stone", 1.0f, 1.0f);
    public static final SoundSteps i = new SoundSteps("stone", 1.0f, 1.5f);
    public static final SoundSteps j = new SoundStepsGlass("stone", 1.0f, 1.0f);
    public static final SoundSteps woolstepsound = new SoundSteps("cloth", 1.0f, 1.0f);
    public static final SoundSteps sandstepsound = new SoundStepsGravel("sand", 1.0f, 1.0f);
    public static final Block[] registry = new Block[256];
    public static final boolean[] n = new boolean[256];
    public static final boolean[] o = new boolean[256];
    public static final boolean[] p = new boolean[256];
    public static final int[] q = new int[256];
    public static final boolean[] r = new boolean[256];
    public static final int[] s = new int[256];
    public static final Block t = new cn(1, 1).c(1.5f).b(10.0f).a(h);
    public static final os u = (os)new os(2).c(0.6f).a(grassstepsound);
    public static final Block v = new jf(3, 2).c(0.5f).a(gravelstepsound);
    public static final Block w = new Block(4, 16, hb.d).c(2.0f).b(10.0f).a(h);
    public static final Block x = new Block(5, 4, hb.c).c(2.0f).b(5.0f).a(woodstepsound);
    public static final Block y = new ej(6, 15).c(0.0f).a(grassstepsound);
    public static final Block z = new Block(7, 17, hb.d).c(-1.0f).b(6000000.0f).a(h);
    public static final Block A = new ja(8, hb.f).c(100.0f).d(3);
    public static final Block B = new ir(9, hb.f).c(100.0f).d(3);
    public static final Block C = new ja(10, hb.g).c(0.0f).a(1.0f).d(255);
    public static final Block D = new ir(11, hb.g).c(100.0f).a(1.0f).d(255);
    public static final Block E = new dw(12, 18).c(0.5f).a(sandstepsound);
    public static final Block F = new ic(13, 19).c(0.6f).a(gravelstepsound);
    public static final Block G = new hz(14, 32).c(3.0f).b(5.0f).a(h);
    public static final Block H = new hz(15, 33).c(3.0f).b(5.0f).a(h);
    public static final Block I = new hz(16, 34).c(3.0f).b(5.0f).a(h);
    public static final Block J = new ny(17).c(2.0f).a(woodstepsound);
    public static final km K = (km)new km(18, 52).c(0.2f).d(1).a(grassstepsound);
    public static final Block L = new pd(19).c(0.6f).a(grassstepsound);
    public static final Block M = new de(20, 49, hb.o, false).c(0.3f).a(j);
    public static final Block N = null;
    public static final Block O = null;
    public static final Block P = null;
    public static final Block Q = null;
    public static final Block R = null;
    public static final Block S = null;
    public static final Block T = null;
    public static final Block U = null;
    public static final Block V = null;
    public static final Block W = null;
    public static final Block X = null;
    public static final Block Y = null;
    public static final Block Z = null;
    public static final Block aa = null;
    public static final Block ab = new Block(35, 64, hb.k).c(0.8f).a(woolstepsound);
    public static final Block ac = null;
    public static final ok ad = (ok)new ok(37, 13).c(0.0f).a(grassstepsound);
    public static final ok ae = (ok)new ok(38, 12).c(0.0f).a(grassstepsound);
    public static final ok af = (ok)new mr(39, 29).c(0.0f).a(grassstepsound).a(0.125f);
    public static final ok ag = (ok)new mr(40, 28).c(0.0f).a(grassstepsound);
    public static final Block ah = new e(41, 39).c(3.0f).b(10.0f).a(i);
    public static final Block ai = new e(42, 38).c(5.0f).b(10.0f).a(i);
    public static final Block aj = new qj(43, true).c(2.0f).b(10.0f).a(h);
    public static final Block ak = new qj(44, false).c(2.0f).b(10.0f).a(h);
    public static final Block al = new Block(45, 7, hb.d).c(2.0f).b(10.0f).a(h);
    public static final Block am = new v(46, 8).c(0.0f).a(grassstepsound);
    public static final Block an = new eh(47, 35).c(1.5f).a(woodstepsound);
    public static final Block ao = new Block(48, 36, hb.d).c(2.0f).b(10.0f).a(h);
    public static final Block ap = new cw(49, 37).c(10.0f).b(2000.0f).a(h);
    public static final Block aq = new ob(50, 80).c(0.0f).a(0.9375f).a(woodstepsound);
    public static final qh ar = (qh)new qh(51, 31).c(0.0f).a(1.0f).a(woodstepsound);
    public static final Block as = new br(52, 65).c(5.0f).a(i);
    public static final Block at = new mb(53, x);
    public static final Block au = new c(54).c(2.5f).a(woodstepsound);
    public static final Block av = new lu(55, 84).c(0.0f).a(stonestepsound);
    public static final Block aw = new hz(56, 50).c(3.0f).b(5.0f).a(h);
    public static final Block ax = new e(57, 40).c(5.0f).b(10.0f).a(i);
    public static final Block ay = new dd(58).c(2.5f).a(woodstepsound);
    public static final Block az = new ig(59, 88).c(0.0f).a(grassstepsound);
    public static final Block aA = new oa(60).c(0.6f).a(gravelstepsound);
    public static final Block aB = new mj(61, false).c(3.5f).a(h);
    public static final Block aC = new mj(62, true).c(3.5f).a(h).a(0.875f);
    public static final Block aD = new ni(63, Sign.class, true).c(1.0f).a(woodstepsound);
    public static final Block aE = new gv(64, hb.c).c(3.0f).a(woodstepsound);
    public static final Block aF = new ca(65, 83).c(0.4f).a(woodstepsound);
    public static final Block aG = new jn(66, 128).c(0.7f).a(i);
    public static final Block aH = new mb(67, w);
    public static final Block aI = new ni(68, Sign.class, false).c(1.0f).a(woodstepsound);
    public static final Block aJ = new pl(69, 96).c(0.5f).a(woodstepsound);
    public static final Block aK = new ap(70, Block.t.bg, lg.mobs).c(0.5f).a(h);
    public static final Block aL = new gv(71, hb.e).c(5.0f).a(i);
    public static final Block aM = new ap(72, Block.x.bg, lg.everything).c(0.5f).a(woodstepsound);
    public static final Block aN = new an(73, 51, false).c(3.0f).b(5.0f).a(h);
    public static final Block aO = new an(74, 51, true).a(0.625f).c(3.0f).b(5.0f).a(h);
    public static final Block aP = new boclass(75, 115, false).c(0.0f).a(woodstepsound);
    public static final Block aQ = new boclass(76, 99, true).c(0.0f).a(0.5f).a(woodstepsound);
    public static final Block aR = new iy(77, Block.t.bg).c(0.5f).a(h);
    public static final Block aS = new fz(78, 66).c(0.1f).a(woolstepsound);
    public static final Block aT = new ih(79, 67).c(0.5f).d(3).a(j);
    public static final Block aU = new s(80, 66).c(0.2f).a(woolstepsound);
    public static final Block aV = new je(81, 70).c(0.4f).a(woolstepsound);
    public static final Block aW = new lj(82, 72).c(0.6f).a(gravelstepsound);
    public static final Block aX = new kz(83, 73).c(0.0f).a(grassstepsound);
    public static final Block aY = new dg(84, 74).c(2.0f).b(10.0f).a(h);
    public static final Block aZ = new gd(85, 4).c(2.0f).b(5.0f).a(woodstepsound);
    public static final Block ba = new cx(86, 102, false).c(1.0f).a(woodstepsound);
    public static final Block bb = new qb(87, 103).c(0.4f).a(h);
    public static final Block bc = new it(88, 104).c(0.5f).a(sandstepsound);
    public static final Block bd = new hk(89, 105, hb.o).c(0.3f).a(j).a(1.0f);
    public static final x be = (x)new x(90, 14).c(-1.0f).a(j).a(0.75f);
    public static final Block bf = new cx(91, 102, true).c(1.0f).a(woodstepsound).a(1.0f);
    public int bg;
    public final int bh;
    protected float bi;
    protected float bj;
    public double bk;
    public double bl;
    public double bm;
    public double bn;
    public double bo;
    public double bp;
    public SoundSteps bq = stonestepsound;
    public float br = 1.0f;
    public final hb bs;
    public float bt = 0.6f;

    protected Block(int n2, hb hb2) {
        if (registry[n2] != null) {
            throw new IllegalArgumentException("Slot " + n2 + " is already occupied by " + registry[n2] + " when adding " + this);
        }
        this.bs = hb2;
        Block.registry[n2] = this;
        this.bh = n2;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.o[n2] = this.a();
        Block.q[n2] = this.a() ? 255 : 0;
        Block.r[n2] = this.i();
        Block.p[n2] = false;
    }

    protected Block(int n2, int n3, hb hb2) {
        this(n2, hb2);
        this.bg = n3;
    }

    protected Block a(SoundSteps bi2) {
        this.bq = bi2;
        return this;
    }

    protected Block d(int n2) {
        Block.q[this.bh] = n2;
        return this;
    }

    protected Block a(float f2) {
        Block.s[this.bh] = (int)(15.0f * f2);
        return this;
    }

    protected Block b(float f2) {
        this.bj = f2 * 3.0f;
        return this;
    }

    private boolean i() {
        return false;
    }

    public boolean b() {
        return true;
    }

    public int g() {
        return 0;
    }

    protected Block c(float f2) {
        this.bi = f2;
        if (this.bj < f2 * 5.0f) {
            this.bj = f2 * 5.0f;
        }
        return this;
    }

    protected void b(boolean bl2) {
        Block.n[this.bh] = bl2;
    }

    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.bk = f2;
        this.bl = f3;
        this.bm = f4;
        this.bn = f5;
        this.bo = f6;
        this.bp = f7;
    }

    public float d(pk pk2, int n2, int n3, int n4) {
        return pk2.c(n2, n3, n4);
    }

    public boolean b(pk pk2, int n2, int n3, int n4, int n5) {
        if (n5 == 0 && this.bl > 0.0) {
            return true;
        }
        if (n5 == 1 && this.bo < 1.0) {
            return true;
        }
        if (n5 == 2 && this.bm > 0.0) {
            return true;
        }
        if (n5 == 3 && this.bp < 1.0) {
            return true;
        }
        if (n5 == 4 && this.bk > 0.0) {
            return true;
        }
        if (n5 == 5 && this.bn < 1.0) {
            return true;
        }
        return !pk2.g(n2, n3, n4);
    }

    public int a(pk pk2, int n2, int n3, int n4, int n5) {
        return this.a(n5, pk2.e(n2, n3, n4));
    }

    public int a(int n2, int n3) {
        return this.a(n2);
    }

    public int a(int n2) {
        return this.bg;
    }

    public co f(Session cy2, int n2, int n3, int n4) {
        return co.b((double)n2 + this.bk, (double)n3 + this.bl, (double)n4 + this.bm, (double)n2 + this.bn, (double)n3 + this.bo, (double)n4 + this.bp);
    }

    public void a(Session cy2, int n2, int n3, int n4, co co2, ArrayList arrayList) {
        co co3 = this.d(cy2, n2, n3, n4);
        if (co3 != null && co2.a(co3)) {
            arrayList.add(co3);
        }
    }

    public co d(Session cy2, int n2, int n3, int n4) {
        return co.b((double)n2 + this.bk, (double)n3 + this.bl, (double)n4 + this.bm, (double)n2 + this.bn, (double)n3 + this.bo, (double)n4 + this.bp);
    }

    public boolean a() {
        return true;
    }

    public boolean a(int n2, boolean bl2) {
        return this.h();
    }

    public boolean h() {
        return true;
    }

    public void a(Session cy2, int n2, int n3, int n4, Random random) {
    }

    public void b(Session cy2, int n2, int n3, int n4, Random random) {
    }

    public void b(Session cy2, int n2, int n3, int n4, int n5) {
    }

    public void a(Session cy2, int n2, int n3, int n4, int n5) {
    }

    public int d() {
        return 10;
    }

    public void e(Session cy2, int n2, int n3, int n4) {
    }

    public void b(Session cy2, int n2, int n3, int n4) {
    }

    public int a(Random random) {
        return 1;
    }

    public int a(int n2, Random random) {
        return this.bh;
    }

    public float a(Player eb2) {
        if (this.bi < 0.0f) {
            return 0.0f;
        }
        if (!eb2.b(this)) {
            return 1.0f / this.bi / 100.0f;
        }
        return eb2.a(this) / this.bi / 30.0f;
    }

    public void b_(Session cy2, int n2, int n3, int n4, int n5) {
        this.a(cy2, n2, n3, n4, n5, 1.0f);
    }

    public void a(Session cy2, int n2, int n3, int n4, int n5, float f2) {
        if (cy2.z) {
            return;
        }
        int n6 = this.a(cy2.l);
        for (int i2 = 0; i2 < n6; ++i2) {
            int n7;
            if (cy2.l.nextFloat() > f2 || (n7 = this.a(n5, cy2.l)) <= 0) continue;
            float f3 = 0.7f;
            double d2 = (double)(cy2.l.nextFloat() * f3) + (double)(1.0f - f3) * 0.5;
            double d3 = (double)(cy2.l.nextFloat() * f3) + (double)(1.0f - f3) * 0.5;
            double d4 = (double)(cy2.l.nextFloat() * f3) + (double)(1.0f - f3) * 0.5;
            DroppedItem eo2 = new DroppedItem(cy2, (double)n2 + d2, (double)n3 + d3, (double)n4 + d4, new InventoryItem(n7));
            eo2.c = 10;
            cy2.a(eo2);
        }
    }

    public float a(lw lw2) {
        return this.bj / 5.0f;
    }

    public nx a(Session cy2, int n2, int n3, int n4, Vector3D ao2, Vector3D ao3) {
        this.a((pk)cy2, n2, n3, n4);
        ao2 = ao2.c(-n2, -n3, -n4);
        ao3 = ao3.c(-n2, -n3, -n4);
        Vector3D ao4 = ao2.a(ao3, this.bk);
        Vector3D ao5 = ao2.a(ao3, this.bn);
        Vector3D ao6 = ao2.b(ao3, this.bl);
        Vector3D ao7 = ao2.b(ao3, this.bo);
        Vector3D ao8 = ao2.c(ao3, this.bm);
        Vector3D ao9 = ao2.c(ao3, this.bp);
        if (!this.a(ao4)) {
            ao4 = null;
        }
        if (!this.a(ao5)) {
            ao5 = null;
        }
        if (!this.b(ao6)) {
            ao6 = null;
        }
        if (!this.b(ao7)) {
            ao7 = null;
        }
        if (!this.c(ao8)) {
            ao8 = null;
        }
        if (!this.c(ao9)) {
            ao9 = null;
        }
        Vector3D ao10 = null;
        if (ao4 != null && (ao10 == null || ao2.c(ao4) < ao2.c(ao10))) {
            ao10 = ao4;
        }
        if (ao5 != null && (ao10 == null || ao2.c(ao5) < ao2.c(ao10))) {
            ao10 = ao5;
        }
        if (ao6 != null && (ao10 == null || ao2.c(ao6) < ao2.c(ao10))) {
            ao10 = ao6;
        }
        if (ao7 != null && (ao10 == null || ao2.c(ao7) < ao2.c(ao10))) {
            ao10 = ao7;
        }
        if (ao8 != null && (ao10 == null || ao2.c(ao8) < ao2.c(ao10))) {
            ao10 = ao8;
        }
        if (ao9 != null && (ao10 == null || ao2.c(ao9) < ao2.c(ao10))) {
            ao10 = ao9;
        }
        if (ao10 == null) {
            return null;
        }
        int n5 = -1;
        if (ao10 == ao4) {
            n5 = 4;
        }
        if (ao10 == ao5) {
            n5 = 5;
        }
        if (ao10 == ao6) {
            n5 = 0;
        }
        if (ao10 == ao7) {
            n5 = 1;
        }
        if (ao10 == ao8) {
            n5 = 2;
        }
        if (ao10 == ao9) {
            n5 = 3;
        }
        return new nx(n2, n3, n4, n5, ao10.c(n2, n3, n4));
    }

    private boolean a(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.y >= this.bl && ao2.y <= this.bo && ao2.z >= this.bm && ao2.z <= this.bp;
    }

    private boolean b(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.x >= this.bk && ao2.x <= this.bn && ao2.z >= this.bm && ao2.z <= this.bp;
    }

    private boolean c(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.x >= this.bk && ao2.x <= this.bn && ao2.y >= this.bl && ao2.y <= this.bo;
    }

    public void c(Session cy2, int n2, int n3, int n4) {
    }

    public int c() {
        return 0;
    }

    public boolean a(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.a(n2, n3, n4);
        return n5 == 0 || Block.registry[n5].bs.d();
    }

    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        return false;
    }

    public void b(Session cy2, int n2, int n3, int n4, lw lw2) {
    }

    public void d(Session cy2, int n2, int n3, int n4, int n5) {
    }

    public void b(Session cy2, int n2, int n3, int n4, Player eb2) {
    }

    public void a(Session cy2, int n2, int n3, int n4, lw lw2, Vector3D ao2) {
    }

    public void a(pk pk2, int n2, int n3, int n4) {
    }

    public int b(pk pk2, int n2, int n3, int n4) {
        return 0xFFFFFF;
    }

    public boolean c(pk pk2, int n2, int n3, int n4, int n5) {
        return false;
    }

    public boolean e() {
        return false;
    }

    public void a(Session cy2, int n2, int n3, int n4, lw lw2) {
    }

    public boolean c(Session cy2, int n2, int n3, int n4, int n5) {
        return false;
    }

    public void f() {
    }

    public void a_(Session cy2, int n2, int n3, int n4, int n5) {
        this.b_(cy2, n2, n3, n4, n5);
    }

    public boolean g(Session cy2, int n2, int n3, int n4) {
        return true;
    }

    public void a(Session cy2, int n2, int n3, int n4, Mob hf2) {
    }

    static {
        for (int i2 = 0; i2 < 256; ++i2) {
            if (registry[i2] == null) continue;
            Item.registry[i2] = new bc(i2 - 256);
        }
    }
}

