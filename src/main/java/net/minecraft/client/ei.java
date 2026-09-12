/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Arrays;

public class ei
extends po {
    private gg e;
    private double f;
    private double g;

    public ei(gg gg2, double d2, double d3) {
        this.e = gg2;
        this.f = d2;
        this.g = d3;
    }

    @Override
    public gg a(qm qm2) {
        return this.e;
    }

    @Override
    public gg a(int n2, int n3) {
        return this.e;
    }

    @Override
    public double b(int n2, int n3) {
        return this.f;
    }

    @Override
    public gg[] a(int n2, int n3, int n4, int n5) {
        this.d = this.a(this.d, n2, n3, n4, n5);
        return this.d;
    }

    @Override
    public double[] a(double[] dArray, int n2, int n3, int n4, int n5) {
        if (dArray == null || dArray.length < n4 * n5) {
            dArray = new double[n4 * n5];
        }
        Arrays.fill(dArray, 0, n4 * n5, this.f);
        return dArray;
    }

    @Override
    public gg[] a(gg[] ggArray, int n2, int n3, int n4, int n5) {
        if (ggArray == null || ggArray.length < n4 * n5) {
            ggArray = new gg[n4 * n5];
            this.a = new double[n4 * n5];
            this.b = new double[n4 * n5];
        }
        Arrays.fill(ggArray, 0, n4 * n5, this.e);
        Arrays.fill(this.b, 0, n4 * n5, this.g);
        Arrays.fill(this.a, 0, n4 * n5, this.f);
        return ggArray;
    }
}

