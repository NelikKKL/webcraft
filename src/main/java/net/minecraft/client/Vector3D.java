/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;

public class Vector3D { // todo: probably not correct name
    static private List d = new ArrayList();
    static private int e = 0;
    public double x;
    public double y;
    public double z;

    public static Vector3D a(double d2, double d3, double d4) {
        return new Vector3D(d2, d3, d4);
    }

    public static void a() {
        e = 0;
    }

    public static Vector3D b(double d2, double d3, double d4) {
        if (e >= d.size()) {
            d.add(Vector3D.a(0.0, 0.0, 0.0));
        }
        return ((Vector3D)d.get(e++)).mutate(d2, d3, d4);
    }

    private Vector3D(double d2, double d3, double d4) {
        if (d2 == -0.0) {
            d2 = 0.0;
        }
        if (d3 == -0.0) {
            d3 = 0.0;
        }
        if (d4 == -0.0) {
            d4 = 0.0;
        }
        this.x = d2;
        this.y = d3;
        this.z = d4;
    }

    private Vector3D mutate(double d2, double d3, double d4) {
        this.x = d2;
        this.y = d3;
        this.z = d4;
        return this;
    }

    public Vector3D a(Vector3D ao2) {
        return ao2.b(ao2.x - this.x, ao2.y - this.y, ao2.z - this.z);
    }

    public Vector3D b() {
        double d2 = TrigLookup.a(this.x * this.x + this.y * this.y + this.z * this.z);
        if (d2 < 1.0E-4) {
            return Vector3D.b(0.0, 0.0, 0.0);
        }
        return Vector3D.b(this.x / d2, this.y / d2, this.z / d2);
    }

    public Vector3D b(Vector3D ao2) {
        return ao2.b(this.y * ao2.z - this.z * ao2.y, this.z * ao2.x - this.x * ao2.z, this.x * ao2.y - this.y * ao2.x);
    }

    public Vector3D c(double d2, double d3, double d4) {
        return Vector3D.b(this.x + d2, this.y + d3, this.z + d4);
    }

    public double c(Vector3D ao2) {
        double d2 = ao2.x - this.x;
        double d3 = ao2.y - this.y;
        double d4 = ao2.z - this.z;
        return TrigLookup.a(d2 * d2 + d3 * d3 + d4 * d4);
    }

    public double d(Vector3D ao2) {
        double d2 = ao2.x - this.x;
        double d3 = ao2.y - this.y;
        double d4 = ao2.z - this.z;
        return d2 * d2 + d3 * d3 + d4 * d4;
    }

    public double d(double d2, double d3, double d4) {
        double d5 = d2 - this.x;
        double d6 = d3 - this.y;
        double d7 = d4 - this.z;
        return d5 * d5 + d6 * d6 + d7 * d7;
    }

    public double c() {
        return TrigLookup.a(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3D a(Vector3D ao2, double d2) {
        double d3 = ao2.x - this.x;
        double d4 = ao2.y - this.y;
        double d5 = ao2.z - this.z;
        if (d3 * d3 < (double)1.0E-7f) {
            return null;
        }
        double d6 = (d2 - this.x) / d3;
        if (d6 < 0.0 || d6 > 1.0) {
            return null;
        }
        return ao2.b(this.x + d3 * d6, this.y + d4 * d6, this.z + d5 * d6);
    }

    public Vector3D b(Vector3D ao2, double d2) {
        double d3 = ao2.x - this.x;
        double d4 = ao2.y - this.y;
        double d5 = ao2.z - this.z;
        if (d4 * d4 < (double)1.0E-7f) {
            return null;
        }
        double d6 = (d2 - this.y) / d4;
        if (d6 < 0.0 || d6 > 1.0) {
            return null;
        }
        return ao2.b(this.x + d3 * d6, this.y + d4 * d6, this.z + d5 * d6);
    }

    public Vector3D c(Vector3D ao2, double d2) {
        double d3 = ao2.x - this.x;
        double d4 = ao2.y - this.y;
        double d5 = ao2.z - this.z;
        if (d5 * d5 < (double)1.0E-7f) {
            return null;
        }
        double d6 = (d2 - this.z) / d5;
        if (d6 < 0.0 || d6 > 1.0) {
            return null;
        }
        return ao2.b(this.x + d3 * d6, this.y + d4 * d6, this.z + d5 * d6);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public void a(float f2) {
        float f3 = TrigLookup.b(f2);
        float f4 = TrigLookup.a(f2);
        double d2 = this.x;
        double d3 = this.y * (double)f3 + this.z * (double)f4;
        double d4 = this.z * (double)f3 - this.y * (double)f4;
        this.x = d2;
        this.y = d3;
        this.z = d4;
    }

    public void b(float f2) {
        float f3 = TrigLookup.b(f2);
        float f4 = TrigLookup.a(f2);
        double d2 = this.x * (double)f3 + this.z * (double)f4;
        double d3 = this.y;
        double d4 = this.z * (double)f3 - this.x * (double)f4;
        this.x = d2;
        this.y = d3;
        this.z = d4;
    }
}

