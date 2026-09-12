/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;

public class om
extends oz {
    @Override
    public void a() {
        this.b = new ei(gg.l, 1.0, 0.0);
        this.c = true;
        this.d = true;
        this.e = true;
        this.g = -1;
    }

    @Override
    public Vector3D a(float f2, float f3) {
        return Vector3D.b(0.2f, 0.03f, 0.03f);
    }

    @Override
    protected void b() {
        float f2 = 0.1f;
        for (int i2 = 0; i2 <= 15; ++i2) {
            float f3 = 1.0f - (float)i2 / 15.0f;
            this.f[i2] = (1.0f - f3) / (f3 * 3.0f + 1.0f) * (1.0f - f2) + f2;
        }
    }

    @Override
    public bd c() {
        return new kj(this.a, this.a.u);
    }

    @Override
    public ai a(File file) {
        File file2 = new File(file, "DIM-1");
        file2.mkdirs();
        return new mu(file2, true);
    }

    @Override
    public boolean a(int n2, int n3) {
        int n4 = this.a.a(n2, n3);
        if (n4 == Block.z.bh) {
            return false;
        }
        if (n4 == 0) {
            return false;
        }
        return Block.o[n4];
    }

    @Override
    public float a(long l2, float f2) {
        return 0.5f;
    }

    @Override
    public boolean d() {
        return false;
    }
}

