/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.Color;

public class gg {
    public static final gg a = new gg().b(588342).a("Rainforest").a(2094168);
    public static final gg b = new ml().b(522674).a("Swampland").a(9154376);
    public static final gg c = new gg().b(10215459).a("Seasonal Forest");
    public static final gg d = new gg().b(353825).a("Forest").a(5159473);
    public static final gg e = new di().b(14278691).a("Savanna");
    public static final gg f = new gg().b(10595616).a("Shrubland");
    public static final gg g = new gg().b(3060051).a("Taiga").b().a(8107825);
    public static final gg h = new di().b(16421912).a("Desert");
    public static final gg i = new di().b(16767248).a("Plains");
    public static final gg j = new di().b(16772499).a("Ice Desert").b().a(12899129);
    public static final gg k = new gg().b(5762041).a("Tundra").b().a(12899129);
    public static final gg l = new k().b(0xFF0000).a("Hell");
    public String m;
    public int n;
    public byte o;
    public byte p;
    public int q;
    protected Class[] monsters;
    protected Class[] creatures;
    static private gg[] t = new gg[4096];

    public gg() {
        this.o = (byte)Block.u.bh;
        this.p = (byte)Block.v.bh;
        this.q = 5169201;
        this.monsters = new Class[]{Spider.class, Zombie.class, Skeleton.class, Creeper.class};
        this.creatures = new Class[]{Sheep.class, Pig.class, Chicken.class, Cow.class};
    }

    public static void a() {
        for (int i2 = 0; i2 < 64; ++i2) {
            for (int i3 = 0; i3 < 64; ++i3) {
                gg.t[i2 + i3 * 64] = gg.a((float)i2 / 63.0f, (float)i3 / 63.0f);
            }
        }
        gg.h.o = gg.h.p = (byte)Block.E.bh;
        gg.j.o = gg.j.p = (byte)Block.E.bh;
    }

    protected gg b() {
        return this;
    }

    protected gg a(String string) {
        this.m = string;
        return this;
    }

    protected gg a(int n2) {
        this.q = n2;
        return this;
    }

    protected gg b(int n2) {
        this.n = n2;
        return this;
    }

    public static gg a(double d2, double d3) {
        int n2 = (int)(d2 * 63.0);
        int n3 = (int)(d3 * 63.0);
        return t[n2 + n3 * 64];
    }

    public static gg a(float f2, float f3) {
        f3 *= f2;
        if (f2 < 0.1f) {
            return k;
        }
        if (f3 < 0.2f) {
            if (f2 < 0.5f) {
                return k;
            }
            if (f2 < 0.95f) {
                return e;
            }
            return h;
        }
        if (f3 > 0.5f && f2 < 0.7f) {
            return b;
        }
        if (f2 < 0.5f) {
            return g;
        }
        if (f2 < 0.97f) {
            if (f3 < 0.35f) {
                return f;
            }
            return d;
        }
        if (f3 < 0.45f) {
            return i;
        }
        if (f3 < 0.9f) {
            return c;
        }
        return a;
    }

    public int a(float f2) {
        if ((f2 /= 3.0f) < -1.0f) {
            f2 = -1.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        return Color.getHSBColor(0.62222224f - f2 * 0.05f, 0.5f + f2 * 0.1f, 1.0f).getRGB();
    }

    public Class[] a(gy gy2) {
        if (gy2 == gy.monster) {
            return this.monsters;
        }
        if (gy2 == gy.creature) {
            return this.creatures;
        }
        return null;
    }

    static {
        gg.a();
    }
}

