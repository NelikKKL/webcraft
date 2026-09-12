/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;

public class oz {
    public Session a;
    public po b;
    public boolean c = false;
    public boolean d = false;
    public boolean e = false;
    public float[] f = new float[16];
    public int g = 0;
    private float[] h = new float[4];

    public final void a(Session cy2) {
        this.a = cy2;
        this.a();
        this.b();
    }

    protected void b() {
        float f2 = 0.05f;
        for (int i2 = 0; i2 <= 15; ++i2) {
            float f3 = 1.0f - (float)i2 / 15.0f;
            this.f[i2] = (1.0f - f3) / (f3 * 3.0f + 1.0f) * (1.0f - f2) + f2;
        }
    }

    protected void a() {
        this.b = new po(this.a);
    }

    public bd c() {
        return new px(this.a, this.a.u);
    }

    public ai a(File file) {
        return new mu(file, true);
    }

    public boolean a(int n2, int n3) {
        int n4 = this.a.a(n2, n3);
        return n4 == Block.E.bh;
    }

    public float a(long l2, float f2) {
        int n2 = (int)(l2 % 24000L);
        float f3 = ((float)n2 + f2) / 24000.0f - 0.25f;
        if (f3 < 0.0f) {
            f3 += 1.0f;
        }
        if (f3 > 1.0f) {
            f3 -= 1.0f;
        }
        float f4 = f3;
        f3 = 1.0f - (float)((Math.cos((double)f3 * Math.PI) + 1.0) / 2.0);
        f3 = f4 + (f3 - f4) / 3.0f;
        return f3;
    }

    public float[] b(float f2, float f3) {
        float f4;
        float f5 = 0.4f;
        float f6 = TrigLookup.b(f2 * (float)Math.PI * 2.0f) - 0.0f;
        if (f6 >= -0.0f - f5 && f6 <= -0.0f + f5) {
            float f7 = (f6 - -0.0f) / f5 * 0.5f + 0.5f;
            float f8 = 1.0f - (1.0f - TrigLookup.a(f7 * (float)Math.PI)) * 0.99f;
            f8 *= f8;
            this.h[0] = f7 * 0.3f + 0.7f;
            this.h[1] = f7 * f7 * 0.7f + 0.2f;
            this.h[2] = f7 * f7 * 0.0f + 0.2f;
            this.h[3] = f8;
            return this.h;
        }
        return null;
    }

    public Vector3D a(float f2, float f3) {
        float f4 = TrigLookup.b(f2 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        float f5 = 0.7529412f;
        float f6 = 0.84705883f;
        float f7 = 1.0f;
        return Vector3D.b(f5 *= f4 * 0.94f + 0.06f, f6 *= f4 * 0.94f + 0.06f, f7 *= f4 * 0.91f + 0.09f);
    }

    public boolean d() {
        return true;
    }

    public static oz a(int n2) {
        if (n2 == 0) {
            return new oz();
        }
        if (n2 == -1) {
            return new om();
        }
        return null;
    }
}

