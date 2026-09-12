/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class hc
extends gm {
    ka a;
    ka[] b = new ka[9];

    public hc() {
        int n2 = -16;
        this.a = new ka(0, 0);
        this.a.a(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.a.b += (float)(24 + n2);
        Random random = new Random(1660L);
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2] = new ka(0, 0);
            float f2 = (((float)(i2 % 3) - (float)(i2 / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float f3 = ((float)(i2 / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int n3 = random.nextInt(7) + 8;
            this.b[i2].a(-1.0f, 0.0f, -1.0f, 2, n3, 2);
            this.b[i2].a = f2;
            this.b[i2].c = f3;
            this.b[i2].b = 31 + n2;
        }
    }

    @Override
    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2].d = 0.2f * TrigLookup.a(f4 * 0.3f + (float)i2) + 0.4f;
        }
    }

    @Override
    public void b(float f2, float f3, float f4, float f5, float f6, float f7) {
        this.a(f2, f3, f4, f5, f6, f7);
        this.a.a(f7);
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2].a(f7);
        }
    }
}

