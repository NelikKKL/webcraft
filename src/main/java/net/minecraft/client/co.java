/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;

public class co {
    static private List g = new ArrayList();
    static private int h = 0;
    public double a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;

    public static co a(double d2, double d3, double d4, double d5, double d6, double d7) {
        return new co(d2, d3, d4, d5, d6, d7);
    }

    public static void a() {
        h = 0;
    }

    public static co b(double d2, double d3, double d4, double d5, double d6, double d7) {
        if (h >= g.size()) {
            g.add(co.a(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        }
        return ((co)g.get(h++)).c(d2, d3, d4, d5, d6, d7);
    }

    private co(double d2, double d3, double d4, double d5, double d6, double d7) {
        this.a = d2;
        this.b = d3;
        this.c = d4;
        this.d = d5;
        this.e = d6;
        this.f = d7;
    }

    public co c(double d2, double d3, double d4, double d5, double d6, double d7) {
        this.a = d2;
        this.b = d3;
        this.c = d4;
        this.d = d5;
        this.e = d6;
        this.f = d7;
        return this;
    }

    public co a(double d2, double d3, double d4) {
        double d5 = this.a;
        double d6 = this.b;
        double d7 = this.c;
        double d8 = this.d;
        double d9 = this.e;
        double d10 = this.f;
        if (d2 < 0.0) {
            d5 += d2;
        }
        if (d2 > 0.0) {
            d8 += d2;
        }
        if (d3 < 0.0) {
            d6 += d3;
        }
        if (d3 > 0.0) {
            d9 += d3;
        }
        if (d4 < 0.0) {
            d7 += d4;
        }
        if (d4 > 0.0) {
            d10 += d4;
        }
        return co.b(d5, d6, d7, d8, d9, d10);
    }

    public co b(double d2, double d3, double d4) {
        double d5 = this.a - d2;
        double d6 = this.b - d3;
        double d7 = this.c - d4;
        double d8 = this.d + d2;
        double d9 = this.e + d3;
        double d10 = this.f + d4;
        return co.b(d5, d6, d7, d8, d9, d10);
    }

    public co c(double d2, double d3, double d4) {
        return co.b(this.a + d2, this.b + d3, this.c + d4, this.d + d2, this.e + d3, this.f + d4);
    }

    public double a(co co2, double d2) {
        double d3;
        if (co2.e <= this.b || co2.b >= this.e) {
            return d2;
        }
        if (co2.f <= this.c || co2.c >= this.f) {
            return d2;
        }
        if (d2 > 0.0 && co2.d <= this.a && (d3 = this.a - co2.d) < d2) {
            d2 = d3;
        }
        if (d2 < 0.0 && co2.a >= this.d && (d3 = this.d - co2.a) > d2) {
            d2 = d3;
        }
        return d2;
    }

    public double b(co co2, double d2) {
        double d3;
        if (co2.d <= this.a || co2.a >= this.d) {
            return d2;
        }
        if (co2.f <= this.c || co2.c >= this.f) {
            return d2;
        }
        if (d2 > 0.0 && co2.e <= this.b && (d3 = this.b - co2.e) < d2) {
            d2 = d3;
        }
        if (d2 < 0.0 && co2.b >= this.e && (d3 = this.e - co2.b) > d2) {
            d2 = d3;
        }
        return d2;
    }

    public double c(co co2, double d2) {
        double d3;
        if (co2.d <= this.a || co2.a >= this.d) {
            return d2;
        }
        if (co2.e <= this.b || co2.b >= this.e) {
            return d2;
        }
        if (d2 > 0.0 && co2.f <= this.c && (d3 = this.c - co2.f) < d2) {
            d2 = d3;
        }
        if (d2 < 0.0 && co2.c >= this.f && (d3 = this.f - co2.c) > d2) {
            d2 = d3;
        }
        return d2;
    }

    public boolean a(co co2) {
        if (co2.d <= this.a || co2.a >= this.d) {
            return false;
        }
        if (co2.e <= this.b || co2.b >= this.e) {
            return false;
        }
        return !(co2.f <= this.c) && !(co2.c >= this.f);
    }

    public co d(double d2, double d3, double d4) {
        this.a += d2;
        this.b += d3;
        this.c += d4;
        this.d += d2;
        this.e += d3;
        this.f += d4;
        return this;
    }

    public boolean a(Vector3D ao2) {
        if (ao2.x <= this.a || ao2.x >= this.d) {
            return false;
        }
        if (ao2.y <= this.b || ao2.y >= this.e) {
            return false;
        }
        return !(ao2.z <= this.c) && !(ao2.z >= this.f);
    }

    public double b() {
        double d2 = this.d - this.a;
        double d3 = this.e - this.b;
        double d4 = this.f - this.c;
        return (d2 + d3 + d4) / 3.0;
    }

    public co c() {
        return co.b(this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public nx a(Vector3D ao2, Vector3D ao3) {
        Vector3D ao4 = ao2.a(ao3, this.a);
        Vector3D ao5 = ao2.a(ao3, this.d);
        Vector3D ao6 = ao2.b(ao3, this.b);
        Vector3D ao7 = ao2.b(ao3, this.e);
        Vector3D ao8 = ao2.c(ao3, this.c);
        Vector3D ao9 = ao2.c(ao3, this.f);
        if (!this.b(ao4)) {
            ao4 = null;
        }
        if (!this.b(ao5)) {
            ao5 = null;
        }
        if (!this.c(ao6)) {
            ao6 = null;
        }
        if (!this.c(ao7)) {
            ao7 = null;
        }
        if (!this.d(ao8)) {
            ao8 = null;
        }
        if (!this.d(ao9)) {
            ao9 = null;
        }
        Vector3D ao10 = null;
        if (ao4 != null && (ao10 == null || ao2.d(ao4) < ao2.d(ao10))) {
            ao10 = ao4;
        }
        if (ao5 != null && (ao10 == null || ao2.d(ao5) < ao2.d(ao10))) {
            ao10 = ao5;
        }
        if (ao6 != null && (ao10 == null || ao2.d(ao6) < ao2.d(ao10))) {
            ao10 = ao6;
        }
        if (ao7 != null && (ao10 == null || ao2.d(ao7) < ao2.d(ao10))) {
            ao10 = ao7;
        }
        if (ao8 != null && (ao10 == null || ao2.d(ao8) < ao2.d(ao10))) {
            ao10 = ao8;
        }
        if (ao9 != null && (ao10 == null || ao2.d(ao9) < ao2.d(ao10))) {
            ao10 = ao9;
        }
        if (ao10 == null) {
            return null;
        }
        int n2 = -1;
        if (ao10 == ao4) {
            n2 = 4;
        }
        if (ao10 == ao5) {
            n2 = 5;
        }
        if (ao10 == ao6) {
            n2 = 0;
        }
        if (ao10 == ao7) {
            n2 = 1;
        }
        if (ao10 == ao8) {
            n2 = 2;
        }
        if (ao10 == ao9) {
            n2 = 3;
        }
        return new nx(0, 0, 0, n2, ao10);
    }

    private boolean b(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.y >= this.b && ao2.y <= this.e && ao2.z >= this.c && ao2.z <= this.f;
    }

    private boolean c(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.x >= this.a && ao2.x <= this.d && ao2.z >= this.c && ao2.z <= this.f;
    }

    private boolean d(Vector3D ao2) {
        if (ao2 == null) {
            return false;
        }
        return ao2.x >= this.a && ao2.x <= this.d && ao2.y >= this.b && ao2.y <= this.e;
    }

    public void b(co co2) {
        this.a = co2.a;
        this.b = co2.b;
        this.c = co2.c;
        this.d = co2.d;
        this.e = co2.e;
        this.f = co2.f;
    }
}

