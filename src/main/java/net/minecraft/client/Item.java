/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class Item {
    static protected Random b = new Random();
    public static Item[] registry = new Item[32000];
    public static Item d = new ol(0, 2).setIcon(82);
    public static Item e = new ac(1, 2).setIcon(98);
    public static Item f = new mg(2, 2).setIcon(114);
    public static Item g = new pw(3).setIcon(5);
    public static Item h = new qk(4, 4).setIcon(10);
    public static Item i = new kt(5).setIcon(21);
    public static Item j = new Item(6).setIcon(37);
    public static Item k = new Item(7).setIcon(7);
    public static Item l = new Item(8).setIcon(55);
    public static Item m = new Item(9).setIcon(23);
    public static Item n = new Item(10).setIcon(39);
    public static Item o = new kg(11, 2).setIcon(66);
    public static Item p = new kg(12, 0).setIcon(64);
    public static Item q = new ol(13, 0).setIcon(80);
    public static Item r = new ac(14, 0).setIcon(96);
    public static Item s = new mg(15, 0).setIcon(112);
    public static Item t = new kg(16, 1).setIcon(65);
    public static Item u = new ol(17, 1).setIcon(81);
    public static Item v = new ac(18, 1).setIcon(97);
    public static Item w = new mg(19, 1).setIcon(113);
    public static Item x = new kg(20, 3).setIcon(67);
    public static Item y = new ol(21, 3).setIcon(83);
    public static Item z = new ac(22, 3).setIcon(99);
    public static Item A = new mg(23, 3).setIcon(115);
    public static Item B = new Item(24).setIcon(53).e();
    public static Item C = new Item(25).setIcon(71);
    public static Item D = new au(26, 10).setIcon(72);
    public static Item E = new kg(27, 0).setIcon(68);
    public static Item F = new ol(28, 0).setIcon(84);
    public static Item G = new ac(29, 0).setIcon(100);
    public static Item H = new mg(30, 0).setIcon(116);
    public static Item I = new Item(31).setIcon(8);
    public static Item J = new Item(32).setIcon(24);
    public static Item K = new Item(33).setIcon(40);
    public static Item L = new gt(34, 0).setIcon(128);
    public static Item M = new gt(35, 1).setIcon(129);
    public static Item N = new gt(36, 2).setIcon(130);
    public static Item O = new gt(37, 3).setIcon(131);
    public static Item P = new gt(38, 1).setIcon(132);
    public static Item Q = new la(39, Block.az.bh).setIcon(9);
    public static Item R = new Item(40).setIcon(25);
    public static Item S = new qk(41, 5).setIcon(41);
    public static Item T = new oj(42, 0, 0, 0).setIcon(0);
    public static Item U = new oj(43, 0, 0, 1).setIcon(16);
    public static Item V = new oj(44, 0, 0, 2).setIcon(32);
    public static Item W = new oj(45, 0, 0, 3).setIcon(48);
    public static Item X = new oj(46, 1, 1, 0).setIcon(1);
    public static Item Y = new oj(47, 1, 1, 1).setIcon(17);
    public static Item Z = new oj(48, 1, 1, 2).setIcon(33);
    public static Item aa = new oj(49, 1, 1, 3).setIcon(49);
    public static Item ab = new oj(50, 2, 2, 0).setIcon(2);
    public static Item ac = new oj(51, 2, 2, 1).setIcon(18);
    public static Item ad = new oj(52, 2, 2, 2).setIcon(34);
    public static Item ae = new oj(53, 2, 2, 3).setIcon(50);
    public static Item af = new oj(54, 3, 3, 0).setIcon(3);
    public static Item ag = new oj(55, 3, 3, 1).setIcon(19);
    public static Item ah = new oj(56, 3, 3, 2).setIcon(35);
    public static Item ai = new oj(57, 3, 3, 3).setIcon(51);
    public static Item aj = new oj(58, 1, 4, 0).setIcon(4);
    public static Item ak = new oj(59, 1, 4, 1).setIcon(20);
    public static Item al = new oj(60, 1, 4, 2).setIcon(36);
    public static Item am = new oj(61, 1, 4, 3).setIcon(52);
    public static Item an = new Item(62).setIcon(6);
    public static Item ao = new qk(63, 3).setIcon(87);
    public static Item ap = new qk(64, 8).setIcon(88);
    public static Item aq = new qe(65).setIcon(26);
    public static Item ar = new qk(66, 42).setIcon(11);
    public static Item as = new nv(67).setIcon(42);
    public static Item at = new eu(68, hb.c).setIcon(43);
    public static Item au = new ag(69, 0).setIcon(74);
    public static Item av = new ag(70, Block.A.bh).setIcon(75);
    public static Item aw = new ag(71, Block.C.bh).setIcon(76);
    public static Item ax = new lb(72, 0).setIcon(135);
    public static Item ay = new lk(73).setIcon(104);
    public static Item az = new eu(74, hb.e).setIcon(44);
    public static Item aA = new ey(75).setIcon(56);
    public static Item aB = new by(76).setIcon(14);
    public static Item aC = new nw(77).setIcon(136);
    public static Item aD = new Item(78).setIcon(103);
    public static Item aE = new ag(79, -1).setIcon(77);
    public static Item aF = new Item(80).setIcon(22);
    public static Item aG = new Item(81).setIcon(57);
    public static Item aH = new hh(82, Block.aX).setIcon(27);
    public static Item aI = new Item(83).setIcon(58);
    public static Item aJ = new Item(84).setIcon(59);
    public static Item aK = new Item(85).setIcon(30);
    public static Item aL = new lb(86, 1).setIcon(151);
    public static Item aM = new lb(87, 2).setIcon(167);
    public static Item aN = new Item(88).setIcon(12);
    public static Item aO = new Item(89).setIcon(54);
    public static Item aP = new bj(90).setIcon(69);
    public static Item aQ = new Item(91).setIcon(70);
    public static Item aR = new Item(92).setIcon(73);
    public static Item aS = new qk(93, 2).setIcon(89);
    public static Item aT = new qk(94, 5).setIcon(90);
    public static Item aU = new mw(2000, "13").setIcon(240);
    public static Item aV = new mw(2001, "cat").setIcon(241);
    public final int id;
    protected int aX = 64;
    protected int aY = 32;
    protected int iconCoor;
    protected boolean ba = false;

    protected Item(int n2) {
        this.id = 256 + n2;
        if (registry[256 + n2] != null) {
            System.out.println("CONFLICT @ " + n2);
        }
        Item.registry[256 + n2] = this;
    }

    public Item setIcon(int n2) {
        this.iconCoor = n2;
        return this;
    }

    public int getIconCoor(InventoryItem fp2) {
        return this.iconCoor;
    }

    public boolean a(InventoryItem fp2, Player eb2, Session cy2, int n2, int n3, int n4, int n5) {
        return false;
    }

    public float a(InventoryItem fp2, Block nq2) {
        return 1.0f;
    }

    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        return fp2;
    }

    public int c() {
        return this.aX;
    }

    public int d() {
        return this.aY;
    }

    public void a(InventoryItem fp2, Mob hf2) {
    }

    public void a(InventoryItem fp2, int n2, int n3, int n4, int n5) {
    }

    public int a(lw lw2) {
        return 1;
    }

    public boolean a(Block nq2) {
        return false;
    }

    public void b(InventoryItem fp2, Mob hf2) {
    }

    public Item e() {
        this.ba = true;
        return this;
    }

    public boolean a() {
        return this.ba;
    }

    public boolean b() {
        return false;
    }
}

