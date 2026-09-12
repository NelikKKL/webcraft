/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class gz {
    private Map m = new HashMap();
    public static gz a = new gz();
    private ls n;
    public static double b;
    public static double c;
    public static double d;
    public fu e;
    public Session f;
    public Player g;
    public float h;
    public float i;
    public double j;
    public double k;
    public double l;

    private gz() {
        this.m.put(Sign.class, new jx());
        this.m.put(MobSpawner.class, new u());
        for (Object _fr2 : this.m.values()) {
            fr fr2 = (fr)_fr2;
            fr2.a(this);
        }
    }

    public fr a(Class clazz) {
        fr fr2 = (fr)this.m.get(clazz);
        if (fr2 == null && clazz != TileEntityRegistry.class) {
            fr2 = this.a(clazz.getSuperclass());
            this.m.put(clazz, fr2);
        }
        return fr2;
    }

    public boolean a(TileEntityRegistry ji2) {
        return this.b(ji2) != null;
    }

    public fr b(TileEntityRegistry ji2) {
        if (ji2 == null) {
            return null;
        }
        return this.a(ji2.getClass());
    }

    public void a(Session cy2, fu fu2, ls ls2, Player eb2, float f2) {
        this.f = cy2;
        this.e = fu2;
        this.g = eb2;
        this.n = ls2;
        this.h = eb2.aE + (eb2.aC - eb2.aE) * f2;
        this.i = eb2.aF + (eb2.aD - eb2.aF) * f2;
        this.j = eb2.aV + (eb2.aw - eb2.aV) * (double)f2;
        this.k = eb2.aW + (eb2.ax - eb2.aW) * (double)f2;
        this.l = eb2.aX + (eb2.ay - eb2.aX) * (double)f2;
    }

    public void a(TileEntityRegistry ji2, float f2) {
        if (ji2.a(this.j, this.k, this.l) < 4096.0) {
            float f3 = this.f.c(ji2.f, ji2.g, ji2.h);
            GL11.glColor3f((float)f3, (float)f3, (float)f3);
            this.a(ji2, (double)ji2.f - b, (double)ji2.g - c, (double)ji2.h - d, f2);
        }
    }

    public void a(TileEntityRegistry ji2, double d2, double d3, double d4, float f2) {
        fr fr2 = this.b(ji2);
        if (fr2 != null) {
            fr2.a(ji2, d2, d3, d4, f2);
        }
    }

    public ls a() {
        return this.n;
    }
}

