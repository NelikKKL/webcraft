/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class nw
extends Item {
    public nw(int n2) {
        super(n2);
        this.aX = 1;
    }

    @Override
    public InventoryItem a(InventoryItem fp2, Session cy2, Player eb2) {
        float f2;
        float f3;
        float f4;
        double d2;
        float f5;
        float f6 = 1.0f;
        float f7 = eb2.aF + (eb2.aD - eb2.aF) * f6;
        float f8 = eb2.aE + (eb2.aC - eb2.aE) * f6;
        double d3 = eb2.at + (eb2.aw - eb2.at) * (double)f6;
        double d4 = eb2.au + (eb2.ax - eb2.au) * (double)f6 + 1.62 - (double)eb2.aO;
        double d5 = eb2.av + (eb2.ay - eb2.av) * (double)f6;
        Vector3D ao2 = Vector3D.b(d3, d4, d5);
        float f9 = TrigLookup.b(-f8 * ((float)Math.PI / 180) - (float)Math.PI);
        float f10 = TrigLookup.a(-f8 * ((float)Math.PI / 180) - (float)Math.PI);
        float f11 = f10 * (f5 = -TrigLookup.b(-f7 * ((float)Math.PI / 180)));
        Vector3D ao3 = ao2.c((double)f11 * 5.0, (double)(f4 = (f3 = TrigLookup.a(-f7 * ((float)Math.PI / 180)))) * 5.0, (double)(f2 = f9 * f5) * 5.0);
        nx nx2 = cy2.a(ao2, ao3, true);
        if (nx2 == null) {
            return fp2;
        }
        if (nx2.a == 0) {
            int n2 = nx2.b;
            int n3 = nx2.c;
            int n4 = nx2.d;
            if (!cy2.z) {
                cy2.a(new Boat(cy2, (float)n2 + 0.5f, (float)n3 + 1.5f, (float)n4 + 0.5f));
            }
            --fp2.count;
        }
        return fp2;
    }
}

