/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class nc {
    public ew[] a;
    public int b = 0;
    private boolean c = false;

    public nc(ew[] ewArray) {
        this.a = ewArray;
        this.b = ewArray.length;
    }

    public nc(ew[] ewArray, int n2, int n3, int n4, int n5) {
        this(ewArray);
        float f2 = 0.0015625f;
        float f3 = 0.003125f;
        ewArray[0] = ewArray[0].a((float)n4 / 64.0f - f2, (float)n3 / 32.0f + f3);
        ewArray[1] = ewArray[1].a((float)n2 / 64.0f + f2, (float)n3 / 32.0f + f3);
        ewArray[2] = ewArray[2].a((float)n2 / 64.0f + f2, (float)n5 / 32.0f - f3);
        ewArray[3] = ewArray[3].a((float)n4 / 64.0f - f2, (float)n5 / 32.0f - f3);
    }

    public void a() {
        ew[] ewArray = new ew[this.a.length];
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            ewArray[i2] = this.a[this.a.length - i2 - 1];
        }
        this.a = ewArray;
    }

    public void a(is is2, float f2) {
        Vector3D ao2 = this.a[1].a.a(this.a[0].a);
        Vector3D ao3 = this.a[1].a.a(this.a[2].a);
        Vector3D ao4 = ao3.b(ao2).b();
        is2.b();
        if (this.c) {
            is2.b(-((float)ao4.x), -((float)ao4.y), -((float)ao4.z));
        } else {
            is2.b((float)ao4.x, (float)ao4.y, (float)ao4.z);
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            ew ew2 = this.a[i2];
            is2.a((float)ew2.a.x * f2, (float)ew2.a.y * f2, (float)ew2.a.z * f2, ew2.b, ew2.c);
        }
        is2.a();
    }
}

