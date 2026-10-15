/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import org.lwjgl.opengl.GL11;

public class bk {
    private pk a;
    private int b = -1;
    private boolean c = false;
    private boolean d = false;

    public bk(pk pk2) {
        this.a = pk2;
    }

    public bk() {
    }

    public void a(Block nq2, int n2, int n3, int n4, int n5) {
        this.b = n5;
        this.a(nq2, n2, n3, n4);
        this.b = -1;
    }

    public boolean a(Block nq2, int n2, int n3, int n4) {
        int n5 = nq2.g();
        nq2.a(this.a, n2, n3, n4);
        if (n5 == 0) {
            return this.k(nq2, n2, n3, n4);
        }
        if (n5 == 4) {
            return this.j(nq2, n2, n3, n4);
        }
        if (n5 == 13) {
            return this.l(nq2, n2, n3, n4);
        }
        if (n5 == 1) {
            return this.h(nq2, n2, n3, n4);
        }
        if (n5 == 6) {
            return this.i(nq2, n2, n3, n4);
        }
        if (n5 == 2) {
            return this.b(nq2, n2, n3, n4);
        }
        if (n5 == 3) {
            return this.d(nq2, n2, n3, n4);
        }
        if (n5 == 5) {
            return this.e(nq2, n2, n3, n4);
        }
        if (n5 == 8) {
            return this.g(nq2, n2, n3, n4);
        }
        if (n5 == 7) {
            return this.o(nq2, n2, n3, n4);
        }
        if (n5 == 9) {
            return this.f(nq2, n2, n3, n4);
        }
        if (n5 == 10) {
            return this.n(nq2, n2, n3, n4);
        }
        if (n5 == 11) {
            return this.m(nq2, n2, n3, n4);
        }
        if (n5 == 12) {
            return this.c(nq2, n2, n3, n4);
        }
        return false;
    }

    public boolean b(Block nq2, int n2, int n3, int n4) {
        int n5 = this.a.e(n2, n3, n4);
        is is2 = is.a;
        float f2 = nq2.d(this.a, n2, n3, n4);
        if (Block.s[nq2.bh] > 0) {
            f2 = 1.0f;
        }
        is2.a(f2, f2, f2);
        double d2 = 0.4f;
        double d3 = 0.5 - d2;
        double d4 = 0.2f;
        if (n5 == 1) {
            this.a(nq2, (double)n2 - d3, (double)n3 + d4, n4, -d2, 0.0);
        } else if (n5 == 2) {
            this.a(nq2, (double)n2 + d3, (double)n3 + d4, n4, d2, 0.0);
        } else if (n5 == 3) {
            this.a(nq2, n2, (double)n3 + d4, (double)n4 - d3, 0.0, -d2);
        } else if (n5 == 4) {
            this.a(nq2, n2, (double)n3 + d4, (double)n4 + d3, 0.0, d2);
        } else {
            this.a(nq2, n2, n3, n4, 0.0, 0.0);
        }
        return true;
    }

    public boolean c(Block nq2, int n2, int n3, int n4) {
        boolean bl2;
        int n5 = this.a.e(n2, n3, n4);
        int n6 = n5 & 7;
        boolean bl3 = (n5 & 8) > 0;
        is is2 = is.a;
        boolean bl4 = bl2 = this.b >= 0;
        if (!bl2) {
            this.b = Block.w.bg;
        }
        float f2 = 0.25f;
        float f3 = 0.1875f;
        float f4 = 0.1875f;
        if (n6 == 5) {
            nq2.a(0.5f - f3, 0.0f, 0.5f - f2, 0.5f + f3, f4, 0.5f + f2);
        } else if (n6 == 6) {
            nq2.a(0.5f - f2, 0.0f, 0.5f - f3, 0.5f + f2, f4, 0.5f + f3);
        } else if (n6 == 4) {
            nq2.a(0.5f - f3, 0.5f - f2, 1.0f - f4, 0.5f + f3, 0.5f + f2, 1.0f);
        } else if (n6 == 3) {
            nq2.a(0.5f - f3, 0.5f - f2, 0.0f, 0.5f + f3, 0.5f + f2, f4);
        } else if (n6 == 2) {
            nq2.a(1.0f - f4, 0.5f - f2, 0.5f - f3, 1.0f, 0.5f + f2, 0.5f + f3);
        } else if (n6 == 1) {
            nq2.a(0.0f, 0.5f - f2, 0.5f - f3, f4, 0.5f + f2, 0.5f + f3);
        }
        this.k(nq2, n2, n3, n4);
        if (!bl2) {
            this.b = -1;
        }
        float f5 = nq2.d(this.a, n2, n3, n4);
        if (Block.s[nq2.bh] > 0) {
            f5 = 1.0f;
        }
        is2.a(f5, f5, f5);
        int n7 = nq2.a(0);
        if (this.b >= 0) {
            n7 = this.b;
        }
        int n8 = (n7 & 0xF) << 4;
        int n9 = n7 & 0xF0;
        float f6 = (float)n8 / 256.0f;
        float f7 = ((float)n8 + 15.99f) / 256.0f;
        float f8 = (float)n9 / 256.0f;
        float f9 = ((float)n9 + 15.99f) / 256.0f;
        Vector3D[] aoArray = new Vector3D[8];
        float f10 = 0.0625f;
        float f11 = 0.0625f;
        float f12 = 0.625f;
        aoArray[0] = Vector3D.b(-f10, 0.0, -f11);
        aoArray[1] = Vector3D.b(f10, 0.0, -f11);
        aoArray[2] = Vector3D.b(f10, 0.0, f11);
        aoArray[3] = Vector3D.b(-f10, 0.0, f11);
        aoArray[4] = Vector3D.b(-f10, f12, -f11);
        aoArray[5] = Vector3D.b(f10, f12, -f11);
        aoArray[6] = Vector3D.b(f10, f12, f11);
        aoArray[7] = Vector3D.b(-f10, f12, f11);
        for (int i2 = 0; i2 < 8; ++i2) {
            if (bl3) {
                aoArray[i2].z -= 0.0625;
                aoArray[i2].a(0.69813174f);
            } else {
                aoArray[i2].z += 0.0625;
                aoArray[i2].a(-0.69813174f);
            }
            if (n6 == 6) {
                aoArray[i2].b(1.5707964f);
            }
            if (n6 < 5) {
                aoArray[i2].y -= 0.375;
                aoArray[i2].a(1.5707964f);
                if (n6 == 4) {
                    aoArray[i2].b(0.0f);
                }
                if (n6 == 3) {
                    aoArray[i2].b((float)Math.PI);
                }
                if (n6 == 2) {
                    aoArray[i2].b(1.5707964f);
                }
                if (n6 == 1) {
                    aoArray[i2].b(-1.5707964f);
                }
                aoArray[i2].x += (double)n2 + 0.5;
                aoArray[i2].y += (double)((float)n3 + 0.5f);
                aoArray[i2].z += (double)n4 + 0.5;
                continue;
            }
            aoArray[i2].x += (double)n2 + 0.5;
            aoArray[i2].y += (double)((float)n3 + 0.125f);
            aoArray[i2].z += (double)n4 + 0.5;
        }
        Vector3D ao2 = null;
        Vector3D ao3 = null;
        Vector3D ao4 = null;
        Vector3D ao5 = null;
        for (int i3 = 0; i3 < 6; ++i3) {
            if (i3 == 0) {
                f6 = (float)(n8 + 7) / 256.0f;
                f7 = ((float)(n8 + 9) - 0.01f) / 256.0f;
                f8 = (float)(n9 + 6) / 256.0f;
                f9 = ((float)(n9 + 8) - 0.01f) / 256.0f;
            } else if (i3 == 2) {
                f6 = (float)(n8 + 7) / 256.0f;
                f7 = ((float)(n8 + 9) - 0.01f) / 256.0f;
                f8 = (float)(n9 + 6) / 256.0f;
                f9 = ((float)(n9 + 16) - 0.01f) / 256.0f;
            }
            if (i3 == 0) {
                ao2 = aoArray[0];
                ao3 = aoArray[1];
                ao4 = aoArray[2];
                ao5 = aoArray[3];
            } else if (i3 == 1) {
                ao2 = aoArray[7];
                ao3 = aoArray[6];
                ao4 = aoArray[5];
                ao5 = aoArray[4];
            } else if (i3 == 2) {
                ao2 = aoArray[1];
                ao3 = aoArray[0];
                ao4 = aoArray[4];
                ao5 = aoArray[5];
            } else if (i3 == 3) {
                ao2 = aoArray[2];
                ao3 = aoArray[1];
                ao4 = aoArray[5];
                ao5 = aoArray[6];
            } else if (i3 == 4) {
                ao2 = aoArray[3];
                ao3 = aoArray[2];
                ao4 = aoArray[6];
                ao5 = aoArray[7];
            } else if (i3 == 5) {
                ao2 = aoArray[0];
                ao3 = aoArray[3];
                ao4 = aoArray[7];
                ao5 = aoArray[4];
            }
            is2.a(ao2.x, ao2.y, ao2.z, f6, f9);
            is2.a(ao3.x, ao3.y, ao3.z, f7, f9);
            is2.a(ao4.x, ao4.y, ao4.z, f7, f8);
            is2.a(ao5.x, ao5.y, ao5.z, f6, f8);
        }
        return true;
    }

    public boolean d(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        int n5 = nq2.a(0);
        if (this.b >= 0) {
            n5 = this.b;
        }
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        int n6 = (n5 & 0xF) << 4;
        int n7 = n5 & 0xF0;
        double d2 = (float)n6 / 256.0f;
        double d3 = ((float)n6 + 15.99f) / 256.0f;
        double d4 = (float)n7 / 256.0f;
        double d5 = ((float)n7 + 15.99f) / 256.0f;
        float f3 = 1.4f;
        if (this.a.g(n2, n3 - 1, n4) || Block.ar.c(this.a, n2, n3 - 1, n4)) {
            double d6 = (double)n2 + 0.5 + 0.2;
            double d7 = (double)n2 + 0.5 - 0.2;
            double d8 = (double)n4 + 0.5 + 0.2;
            double d9 = (double)n4 + 0.5 - 0.2;
            double d10 = (double)n2 + 0.5 - 0.3;
            double d11 = (double)n2 + 0.5 + 0.3;
            double d12 = (double)n4 + 0.5 - 0.3;
            double d13 = (double)n4 + 0.5 + 0.3;
            is2.a(d10, (float)n3 + f3, n4 + 1, d3, d4);
            is2.a(d6, n3 + 0, n4 + 1, d3, d5);
            is2.a(d6, n3 + 0, n4 + 0, d2, d5);
            is2.a(d10, (float)n3 + f3, n4 + 0, d2, d4);
            is2.a(d11, (float)n3 + f3, n4 + 0, d3, d4);
            is2.a(d7, n3 + 0, n4 + 0, d3, d5);
            is2.a(d7, n3 + 0, n4 + 1, d2, d5);
            is2.a(d11, (float)n3 + f3, n4 + 1, d2, d4);
            d2 = (float)n6 / 256.0f;
            d3 = ((float)n6 + 15.99f) / 256.0f;
            d4 = (float)(n7 + 16) / 256.0f;
            d5 = ((float)n7 + 15.99f + 16.0f) / 256.0f;
            is2.a(n2 + 1, (float)n3 + f3, d13, d3, d4);
            is2.a(n2 + 1, n3 + 0, d9, d3, d5);
            is2.a(n2 + 0, n3 + 0, d9, d2, d5);
            is2.a(n2 + 0, (float)n3 + f3, d13, d2, d4);
            is2.a(n2 + 0, (float)n3 + f3, d12, d3, d4);
            is2.a(n2 + 0, n3 + 0, d8, d3, d5);
            is2.a(n2 + 1, n3 + 0, d8, d2, d5);
            is2.a(n2 + 1, (float)n3 + f3, d12, d2, d4);
            d6 = (double)n2 + 0.5 - 0.5;
            d7 = (double)n2 + 0.5 + 0.5;
            d8 = (double)n4 + 0.5 - 0.5;
            d9 = (double)n4 + 0.5 + 0.5;
            d10 = (double)n2 + 0.5 - 0.4;
            d11 = (double)n2 + 0.5 + 0.4;
            d12 = (double)n4 + 0.5 - 0.4;
            d13 = (double)n4 + 0.5 + 0.4;
            is2.a(d10, (float)n3 + f3, n4 + 0, d2, d4);
            is2.a(d6, n3 + 0, n4 + 0, d2, d5);
            is2.a(d6, n3 + 0, n4 + 1, d3, d5);
            is2.a(d10, (float)n3 + f3, n4 + 1, d3, d4);
            is2.a(d11, (float)n3 + f3, n4 + 1, d2, d4);
            is2.a(d7, n3 + 0, n4 + 1, d2, d5);
            is2.a(d7, n3 + 0, n4 + 0, d3, d5);
            is2.a(d11, (float)n3 + f3, n4 + 0, d3, d4);
            d2 = (float)n6 / 256.0f;
            d3 = ((float)n6 + 15.99f) / 256.0f;
            d4 = (float)n7 / 256.0f;
            d5 = ((float)n7 + 15.99f) / 256.0f;
            is2.a(n2 + 0, (float)n3 + f3, d13, d2, d4);
            is2.a(n2 + 0, n3 + 0, d9, d2, d5);
            is2.a(n2 + 1, n3 + 0, d9, d3, d5);
            is2.a(n2 + 1, (float)n3 + f3, d13, d3, d4);
            is2.a(n2 + 1, (float)n3 + f3, d12, d2, d4);
            is2.a(n2 + 1, n3 + 0, d8, d2, d5);
            is2.a(n2 + 0, n3 + 0, d8, d3, d5);
            is2.a(n2 + 0, (float)n3 + f3, d12, d3, d4);
        } else {
            double d14;
            float f4 = 0.2f;
            float f5 = 0.0625f;
            if ((n2 + n3 + n4 & 1) == 1) {
                d2 = (float)n6 / 256.0f;
                d3 = ((float)n6 + 15.99f) / 256.0f;
                d4 = (float)(n7 + 16) / 256.0f;
                d5 = ((float)n7 + 15.99f + 16.0f) / 256.0f;
            }
            if ((n2 / 2 + n3 / 2 + n4 / 2 & 1) == 1) {
                d14 = d3;
                d3 = d2;
                d2 = d14;
            }
            if (Block.ar.c(this.a, n2 - 1, n3, n4)) {
                is2.a((float)n2 + f4, (float)n3 + f3 + f5, n4 + 1, d3, d4);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 1, d3, d5);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a((float)n2 + f4, (float)n3 + f3 + f5, n4 + 0, d2, d4);
                is2.a((float)n2 + f4, (float)n3 + f3 + f5, n4 + 0, d2, d4);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 1, d3, d5);
                is2.a((float)n2 + f4, (float)n3 + f3 + f5, n4 + 1, d3, d4);
            }
            if (Block.ar.c(this.a, n2 + 1, n3, n4)) {
                is2.a((float)(n2 + 1) - f4, (float)n3 + f3 + f5, n4 + 0, d2, d4);
                is2.a(n2 + 1 - 0, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a(n2 + 1 - 0, (float)(n3 + 0) + f5, n4 + 1, d3, d5);
                is2.a((float)(n2 + 1) - f4, (float)n3 + f3 + f5, n4 + 1, d3, d4);
                is2.a((float)(n2 + 1) - f4, (float)n3 + f3 + f5, n4 + 1, d3, d4);
                is2.a(n2 + 1 - 0, (float)(n3 + 0) + f5, n4 + 1, d3, d5);
                is2.a(n2 + 1 - 0, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a((float)(n2 + 1) - f4, (float)n3 + f3 + f5, n4 + 0, d2, d4);
            }
            if (Block.ar.c(this.a, n2, n3, n4 - 1)) {
                is2.a(n2 + 0, (float)n3 + f3 + f5, (float)n4 + f4, d3, d4);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 0, d3, d5);
                is2.a(n2 + 1, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a(n2 + 1, (float)n3 + f3 + f5, (float)n4 + f4, d2, d4);
                is2.a(n2 + 1, (float)n3 + f3 + f5, (float)n4 + f4, d2, d4);
                is2.a(n2 + 1, (float)(n3 + 0) + f5, n4 + 0, d2, d5);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 0, d3, d5);
                is2.a(n2 + 0, (float)n3 + f3 + f5, (float)n4 + f4, d3, d4);
            }
            if (Block.ar.c(this.a, n2, n3, n4 + 1)) {
                is2.a(n2 + 1, (float)n3 + f3 + f5, (float)(n4 + 1) - f4, d2, d4);
                is2.a(n2 + 1, (float)(n3 + 0) + f5, n4 + 1 - 0, d2, d5);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 1 - 0, d3, d5);
                is2.a(n2 + 0, (float)n3 + f3 + f5, (float)(n4 + 1) - f4, d3, d4);
                is2.a(n2 + 0, (float)n3 + f3 + f5, (float)(n4 + 1) - f4, d3, d4);
                is2.a(n2 + 0, (float)(n3 + 0) + f5, n4 + 1 - 0, d3, d5);
                is2.a(n2 + 1, (float)(n3 + 0) + f5, n4 + 1 - 0, d2, d5);
                is2.a(n2 + 1, (float)n3 + f3 + f5, (float)(n4 + 1) - f4, d2, d4);
            }
            if (Block.ar.c(this.a, n2, n3 + 1, n4)) {
                d14 = (double)n2 + 0.5 + 0.5;
                double d15 = (double)n2 + 0.5 - 0.5;
                double d16 = (double)n4 + 0.5 + 0.5;
                double d17 = (double)n4 + 0.5 - 0.5;
                double d18 = (double)n2 + 0.5 - 0.5;
                double d19 = (double)n2 + 0.5 + 0.5;
                double d20 = (double)n4 + 0.5 - 0.5;
                double d21 = (double)n4 + 0.5 + 0.5;
                d2 = (float)n6 / 256.0f;
                d3 = ((float)n6 + 15.99f) / 256.0f;
                d4 = (float)n7 / 256.0f;
                d5 = ((float)n7 + 15.99f) / 256.0f;
                f3 = -0.2f;
                if ((n2 + ++n3 + n4 & 1) == 0) {
                    is2.a(d18, (float)n3 + f3, n4 + 0, d3, d4);
                    is2.a(d14, n3 + 0, n4 + 0, d3, d5);
                    is2.a(d14, n3 + 0, n4 + 1, d2, d5);
                    is2.a(d18, (float)n3 + f3, n4 + 1, d2, d4);
                    d2 = (float)n6 / 256.0f;
                    d3 = ((float)n6 + 15.99f) / 256.0f;
                    d4 = (float)(n7 + 16) / 256.0f;
                    d5 = ((float)n7 + 15.99f + 16.0f) / 256.0f;
                    is2.a(d19, (float)n3 + f3, n4 + 1, d3, d4);
                    is2.a(d15, n3 + 0, n4 + 1, d3, d5);
                    is2.a(d15, n3 + 0, n4 + 0, d2, d5);
                    is2.a(d19, (float)n3 + f3, n4 + 0, d2, d4);
                } else {
                    is2.a(n2 + 0, (float)n3 + f3, d21, d3, d4);
                    is2.a(n2 + 0, n3 + 0, d17, d3, d5);
                    is2.a(n2 + 1, n3 + 0, d17, d2, d5);
                    is2.a(n2 + 1, (float)n3 + f3, d21, d2, d4);
                    d2 = (float)n6 / 256.0f;
                    d3 = ((float)n6 + 15.99f) / 256.0f;
                    d4 = (float)(n7 + 16) / 256.0f;
                    d5 = ((float)n7 + 15.99f + 16.0f) / 256.0f;
                    is2.a(n2 + 1, (float)n3 + f3, d20, d3, d4);
                    is2.a(n2 + 1, n3 + 0, d16, d3, d5);
                    is2.a(n2 + 0, n3 + 0, d16, d2, d5);
                    is2.a(n2 + 0, (float)n3 + f3, d20, d2, d4);
                }
            }
        }
        return true;
    }

    public boolean e(Block nq2, int n2, int n3, int n4) {
        boolean bl2;
        is is2 = is.a;
        int n5 = nq2.a(1, this.a.e(n2, n3, n4));
        if (this.b >= 0) {
            n5 = this.b;
        }
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        int n6 = (n5 & 0xF) << 4;
        int n7 = n5 & 0xF0;
        double d2 = (float)n6 / 256.0f;
        double d3 = ((float)n6 + 15.99f) / 256.0f;
        double d4 = (float)n7 / 256.0f;
        double d5 = ((float)n7 + 15.99f) / 256.0f;
        float f3 = 0.0f;
        float f4 = 0.03125f;
        boolean bl3 = lu.c(this.a, n2 - 1, n3, n4) || !this.a.g(n2 - 1, n3, n4) && lu.c(this.a, n2 - 1, n3 - 1, n4);
        boolean bl4 = lu.c(this.a, n2 + 1, n3, n4) || !this.a.g(n2 + 1, n3, n4) && lu.c(this.a, n2 + 1, n3 - 1, n4);
        boolean bl5 = lu.c(this.a, n2, n3, n4 - 1) || !this.a.g(n2, n3, n4 - 1) && lu.c(this.a, n2, n3 - 1, n4 - 1);
        boolean bl6 = bl2 = lu.c(this.a, n2, n3, n4 + 1) || !this.a.g(n2, n3, n4 + 1) && lu.c(this.a, n2, n3 - 1, n4 + 1);
        if (!this.a.g(n2, n3 + 1, n4)) {
            if (this.a.g(n2 - 1, n3, n4) && lu.c(this.a, n2 - 1, n3 + 1, n4)) {
                bl3 = true;
            }
            if (this.a.g(n2 + 1, n3, n4) && lu.c(this.a, n2 + 1, n3 + 1, n4)) {
                bl4 = true;
            }
            if (this.a.g(n2, n3, n4 - 1) && lu.c(this.a, n2, n3 + 1, n4 - 1)) {
                bl5 = true;
            }
            if (this.a.g(n2, n3, n4 + 1) && lu.c(this.a, n2, n3 + 1, n4 + 1)) {
                bl2 = true;
            }
        }
        float f5 = 0.3125f;
        float f6 = n2 + 0;
        float f7 = n2 + 1;
        float f8 = n4 + 0;
        float f9 = n4 + 1;
        int n8 = 0;
        if ((bl3 || bl4) && !bl5 && !bl2) {
            n8 = 1;
        }
        if ((bl5 || bl2) && !bl4 && !bl3) {
            n8 = 2;
        }
        if (n8 != 0) {
            d2 = (float)(n6 + 16) / 256.0f;
            d3 = ((float)(n6 + 16) + 15.99f) / 256.0f;
            d4 = (float)n7 / 256.0f;
            d5 = ((float)n7 + 15.99f) / 256.0f;
        }
        if (n8 == 0) {
            if (bl4 || bl5 || bl2 || bl3) {
                if (!bl3) {
                    f6 += f5;
                }
                if (!bl3) {
                    d2 += (double)(f5 / 16.0f);
                }
                if (!bl4) {
                    f7 -= f5;
                }
                if (!bl4) {
                    d3 -= (double)(f5 / 16.0f);
                }
                if (!bl5) {
                    f8 += f5;
                }
                if (!bl5) {
                    d4 += (double)(f5 / 16.0f);
                }
                if (!bl2) {
                    f9 -= f5;
                }
                if (!bl2) {
                    d5 -= (double)(f5 / 16.0f);
                }
            }
            is2.a(f7 + f3, (float)n3 + f4, f9 + f3, d3, d5);
            is2.a(f7 + f3, (float)n3 + f4, f8 - f3, d3, d4);
            is2.a(f6 - f3, (float)n3 + f4, f8 - f3, d2, d4);
            is2.a(f6 - f3, (float)n3 + f4, f9 + f3, d2, d5);
        }
        if (n8 == 1) {
            is2.a(f7 + f3, (float)n3 + f4, f9 + f3, d3, d5);
            is2.a(f7 + f3, (float)n3 + f4, f8 - f3, d3, d4);
            is2.a(f6 - f3, (float)n3 + f4, f8 - f3, d2, d4);
            is2.a(f6 - f3, (float)n3 + f4, f9 + f3, d2, d5);
        }
        if (n8 == 2) {
            is2.a(f7 + f3, (float)n3 + f4, f9 + f3, d3, d5);
            is2.a(f7 + f3, (float)n3 + f4, f8 - f3, d2, d5);
            is2.a(f6 - f3, (float)n3 + f4, f8 - f3, d2, d4);
            is2.a(f6 - f3, (float)n3 + f4, f9 + f3, d3, d4);
        }
        d2 = (float)(n6 + 16) / 256.0f;
        d3 = ((float)(n6 + 16) + 15.99f) / 256.0f;
        d4 = (float)n7 / 256.0f;
        d5 = ((float)n7 + 15.99f) / 256.0f;
        if (!this.a.g(n2, n3 + 1, n4)) {
            if (this.a.g(n2 - 1, n3, n4) && this.a.a(n2 - 1, n3 + 1, n4) == Block.av.bh) {
                is2.a((float)n2 + f4, (float)(n3 + 1) + f3, (float)(n4 + 1) + f3, d3, d4);
                is2.a((float)n2 + f4, (float)(n3 + 0) - f3, (float)(n4 + 1) + f3, d2, d4);
                is2.a((float)n2 + f4, (float)(n3 + 0) - f3, (float)(n4 + 0) - f3, d2, d5);
                is2.a((float)n2 + f4, (float)(n3 + 1) + f3, (float)(n4 + 0) - f3, d3, d5);
            }
            if (this.a.g(n2 + 1, n3, n4) && this.a.a(n2 + 1, n3 + 1, n4) == Block.av.bh) {
                is2.a((float)(n2 + 1) - f4, (float)(n3 + 0) - f3, (float)(n4 + 1) + f3, d2, d5);
                is2.a((float)(n2 + 1) - f4, (float)(n3 + 1) + f3, (float)(n4 + 1) + f3, d3, d5);
                is2.a((float)(n2 + 1) - f4, (float)(n3 + 1) + f3, (float)(n4 + 0) - f3, d3, d4);
                is2.a((float)(n2 + 1) - f4, (float)(n3 + 0) - f3, (float)(n4 + 0) - f3, d2, d4);
            }
            if (this.a.g(n2, n3, n4 - 1) && this.a.a(n2, n3 + 1, n4 - 1) == Block.av.bh) {
                is2.a((float)(n2 + 1) + f3, (float)(n3 + 0) - f3, (float)n4 + f4, d2, d5);
                is2.a((float)(n2 + 1) + f3, (float)(n3 + 1) + f3, (float)n4 + f4, d3, d5);
                is2.a((float)(n2 + 0) - f3, (float)(n3 + 1) + f3, (float)n4 + f4, d3, d4);
                is2.a((float)(n2 + 0) - f3, (float)(n3 + 0) - f3, (float)n4 + f4, d2, d4);
            }
            if (this.a.g(n2, n3, n4 + 1) && this.a.a(n2, n3 + 1, n4 + 1) == Block.av.bh) {
                is2.a((float)(n2 + 1) + f3, (float)(n3 + 1) + f3, (float)(n4 + 1) - f4, d3, d4);
                is2.a((float)(n2 + 1) + f3, (float)(n3 + 0) - f3, (float)(n4 + 1) - f4, d2, d4);
                is2.a((float)(n2 + 0) - f3, (float)(n3 + 0) - f3, (float)(n4 + 1) - f4, d2, d5);
                is2.a((float)(n2 + 0) - f3, (float)(n3 + 1) + f3, (float)(n4 + 1) - f4, d3, d5);
            }
        }
        return true;
    }

    public boolean f(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        int n5 = this.a.e(n2, n3, n4);
        int n6 = nq2.a(0, n5);
        if (this.b >= 0) {
            n6 = this.b;
        }
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        int n7 = (n6 & 0xF) << 4;
        int n8 = n6 & 0xF0;
        double d2 = (float)n7 / 256.0f;
        double d3 = ((float)n7 + 15.99f) / 256.0f;
        double d4 = (float)n8 / 256.0f;
        double d5 = ((float)n8 + 15.99f) / 256.0f;
        float f3 = 0.0625f;
        float f4 = n2 + 1;
        float f5 = n2 + 1;
        float f6 = n2 + 0;
        float f7 = n2 + 0;
        float f8 = n4 + 0;
        float f9 = n4 + 1;
        float f10 = n4 + 1;
        float f11 = n4 + 0;
        float f12 = (float)n3 + f3;
        float f13 = (float)n3 + f3;
        float f14 = (float)n3 + f3;
        float f15 = (float)n3 + f3;
        if (n5 == 1 || n5 == 2 || n5 == 3 || n5 == 7) {
            f4 = f7 = (float)(n2 + 1);
            f5 = f6 = (float)(n2 + 0);
            f8 = f9 = (float)(n4 + 1);
            f10 = f11 = (float)(n4 + 0);
        } else if (n5 == 8) {
            f4 = f5 = (float)(n2 + 0);
            f6 = f7 = (float)(n2 + 1);
            f8 = f11 = (float)(n4 + 1);
            f9 = f10 = (float)(n4 + 0);
        } else if (n5 == 9) {
            f4 = f7 = (float)(n2 + 0);
            f5 = f6 = (float)(n2 + 1);
            f8 = f9 = (float)(n4 + 0);
            f10 = f11 = (float)(n4 + 1);
        }
        if (n5 == 2 || n5 == 4) {
            f12 += 1.0f;
            f15 += 1.0f;
        } else if (n5 == 3 || n5 == 5) {
            f13 += 1.0f;
            f14 += 1.0f;
        }
        is2.a(f4, f12, f8, d3, d4);
        is2.a(f5, f13, f9, d3, d5);
        is2.a(f6, f14, f10, d2, d5);
        is2.a(f7, f15, f11, d2, d4);
        is2.a(f7, f15, f11, d2, d4);
        is2.a(f6, f14, f10, d2, d5);
        is2.a(f5, f13, f9, d3, d5);
        is2.a(f4, f12, f8, d3, d4);
        return true;
    }

    public boolean g(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        int n5 = nq2.a(0);
        if (this.b >= 0) {
            n5 = this.b;
        }
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        int n6 = (n5 & 0xF) << 4;
        int n7 = n5 & 0xF0;
        double d2 = (float)n6 / 256.0f;
        double d3 = ((float)n6 + 15.99f) / 256.0f;
        double d4 = (float)n7 / 256.0f;
        double d5 = ((float)n7 + 15.99f) / 256.0f;
        int n8 = this.a.e(n2, n3, n4);
        float f3 = 0.0f;
        float f4 = 0.05f;
        if (n8 == 5) {
            is2.a((float)n2 + f4, (float)(n3 + 1) + f3, (float)(n4 + 1) + f3, d2, d4);
            is2.a((float)n2 + f4, (float)(n3 + 0) - f3, (float)(n4 + 1) + f3, d2, d5);
            is2.a((float)n2 + f4, (float)(n3 + 0) - f3, (float)(n4 + 0) - f3, d3, d5);
            is2.a((float)n2 + f4, (float)(n3 + 1) + f3, (float)(n4 + 0) - f3, d3, d4);
        }
        if (n8 == 4) {
            is2.a((float)(n2 + 1) - f4, (float)(n3 + 0) - f3, (float)(n4 + 1) + f3, d3, d5);
            is2.a((float)(n2 + 1) - f4, (float)(n3 + 1) + f3, (float)(n4 + 1) + f3, d3, d4);
            is2.a((float)(n2 + 1) - f4, (float)(n3 + 1) + f3, (float)(n4 + 0) - f3, d2, d4);
            is2.a((float)(n2 + 1) - f4, (float)(n3 + 0) - f3, (float)(n4 + 0) - f3, d2, d5);
        }
        if (n8 == 3) {
            is2.a((float)(n2 + 1) + f3, (float)(n3 + 0) - f3, (float)n4 + f4, d3, d5);
            is2.a((float)(n2 + 1) + f3, (float)(n3 + 1) + f3, (float)n4 + f4, d3, d4);
            is2.a((float)(n2 + 0) - f3, (float)(n3 + 1) + f3, (float)n4 + f4, d2, d4);
            is2.a((float)(n2 + 0) - f3, (float)(n3 + 0) - f3, (float)n4 + f4, d2, d5);
        }
        if (n8 == 2) {
            is2.a((float)(n2 + 1) + f3, (float)(n3 + 1) + f3, (float)(n4 + 1) - f4, d2, d4);
            is2.a((float)(n2 + 1) + f3, (float)(n3 + 0) - f3, (float)(n4 + 1) - f4, d2, d5);
            is2.a((float)(n2 + 0) - f3, (float)(n3 + 0) - f3, (float)(n4 + 1) - f4, d3, d5);
            is2.a((float)(n2 + 0) - f3, (float)(n3 + 1) + f3, (float)(n4 + 1) - f4, d3, d4);
        }
        return true;
    }

    public boolean h(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        this.a(nq2, this.a.e(n2, n3, n4), (double)n2, (double)n3, (double)n4);
        return true;
    }

    public boolean i(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        float f2 = nq2.d(this.a, n2, n3, n4);
        is2.a(f2, f2, f2);
        this.b(nq2, this.a.e(n2, n3, n4), (double)n2, (double)((float)n3 - 0.0625f), (double)n4);
        return true;
    }

    public void a(Block nq2, double d2, double d3, double d4, double d5, double d6) {
        is is2 = is.a;
        int n2 = nq2.a(0);
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        float f2 = (float)n3 / 256.0f;
        float f3 = ((float)n3 + 15.99f) / 256.0f;
        float f4 = (float)n4 / 256.0f;
        float f5 = ((float)n4 + 15.99f) / 256.0f;
        double d7 = (double)f2 + 0.02734375;
        double d8 = (double)f4 + 0.0234375;
        double d9 = (double)f2 + 0.03515625;
        double d10 = (double)f4 + 0.03125;
        double d11 = (d2 += 0.5) - 0.5;
        double d12 = d2 + 0.5;
        double d13 = (d4 += 0.5) - 0.5;
        double d14 = d4 + 0.5;
        double d15 = 0.0625;
        double d16 = 0.625;
        is2.a(d2 + d5 * (1.0 - d16) - d15, d3 + d16, d4 + d6 * (1.0 - d16) - d15, d7, d8);
        is2.a(d2 + d5 * (1.0 - d16) - d15, d3 + d16, d4 + d6 * (1.0 - d16) + d15, d7, d10);
        is2.a(d2 + d5 * (1.0 - d16) + d15, d3 + d16, d4 + d6 * (1.0 - d16) + d15, d9, d10);
        is2.a(d2 + d5 * (1.0 - d16) + d15, d3 + d16, d4 + d6 * (1.0 - d16) - d15, d9, d8);
        is2.a(d2 - d15, d3 + 1.0, d13, f2, f4);
        is2.a(d2 - d15 + d5, d3 + 0.0, d13 + d6, f2, f5);
        is2.a(d2 - d15 + d5, d3 + 0.0, d14 + d6, f3, f5);
        is2.a(d2 - d15, d3 + 1.0, d14, f3, f4);
        is2.a(d2 + d15, d3 + 1.0, d14, f2, f4);
        is2.a(d2 + d5 + d15, d3 + 0.0, d14 + d6, f2, f5);
        is2.a(d2 + d5 + d15, d3 + 0.0, d13 + d6, f3, f5);
        is2.a(d2 + d15, d3 + 1.0, d13, f3, f4);
        is2.a(d11, d3 + 1.0, d4 + d15, f2, f4);
        is2.a(d11 + d5, d3 + 0.0, d4 + d15 + d6, f2, f5);
        is2.a(d12 + d5, d3 + 0.0, d4 + d15 + d6, f3, f5);
        is2.a(d12, d3 + 1.0, d4 + d15, f3, f4);
        is2.a(d12, d3 + 1.0, d4 - d15, f2, f4);
        is2.a(d12 + d5, d3 + 0.0, d4 - d15 + d6, f2, f5);
        is2.a(d11 + d5, d3 + 0.0, d4 - d15 + d6, f3, f5);
        is2.a(d11, d3 + 1.0, d4 - d15, f3, f4);
    }

    public void a(Block nq2, int n2, double d2, double d3, double d4) {
        is is2 = is.a;
        int n3 = nq2.a(0, n2);
        if (this.b >= 0) {
            n3 = this.b;
        }
        int n4 = (n3 & 0xF) << 4;
        int n5 = n3 & 0xF0;
        double d5 = (float)n4 / 256.0f;
        double d6 = ((float)n4 + 15.99f) / 256.0f;
        double d7 = (float)n5 / 256.0f;
        double d8 = ((float)n5 + 15.99f) / 256.0f;
        double d9 = d2 + 0.5 - (double)0.45f;
        double d10 = d2 + 0.5 + (double)0.45f;
        double d11 = d4 + 0.5 - (double)0.45f;
        double d12 = d4 + 0.5 + (double)0.45f;
        is2.a(d9, d3 + 1.0, d11, d5, d7);
        is2.a(d9, d3 + 0.0, d11, d5, d8);
        is2.a(d10, d3 + 0.0, d12, d6, d8);
        is2.a(d10, d3 + 1.0, d12, d6, d7);
        is2.a(d10, d3 + 1.0, d12, d5, d7);
        is2.a(d10, d3 + 0.0, d12, d5, d8);
        is2.a(d9, d3 + 0.0, d11, d6, d8);
        is2.a(d9, d3 + 1.0, d11, d6, d7);
        is2.a(d9, d3 + 1.0, d12, d5, d7);
        is2.a(d9, d3 + 0.0, d12, d5, d8);
        is2.a(d10, d3 + 0.0, d11, d6, d8);
        is2.a(d10, d3 + 1.0, d11, d6, d7);
        is2.a(d10, d3 + 1.0, d11, d5, d7);
        is2.a(d10, d3 + 0.0, d11, d5, d8);
        is2.a(d9, d3 + 0.0, d12, d6, d8);
        is2.a(d9, d3 + 1.0, d12, d6, d7);
    }

    public void b(Block nq2, int n2, double d2, double d3, double d4) {
        is is2 = is.a;
        int n3 = nq2.a(0, n2);
        if (this.b >= 0) {
            n3 = this.b;
        }
        int n4 = (n3 & 0xF) << 4;
        int n5 = n3 & 0xF0;
        double d5 = (float)n4 / 256.0f;
        double d6 = ((float)n4 + 15.99f) / 256.0f;
        double d7 = (float)n5 / 256.0f;
        double d8 = ((float)n5 + 15.99f) / 256.0f;
        double d9 = d2 + 0.5 - 0.25;
        double d10 = d2 + 0.5 + 0.25;
        double d11 = d4 + 0.5 - 0.5;
        double d12 = d4 + 0.5 + 0.5;
        is2.a(d9, d3 + 1.0, d11, d5, d7);
        is2.a(d9, d3 + 0.0, d11, d5, d8);
        is2.a(d9, d3 + 0.0, d12, d6, d8);
        is2.a(d9, d3 + 1.0, d12, d6, d7);
        is2.a(d9, d3 + 1.0, d12, d5, d7);
        is2.a(d9, d3 + 0.0, d12, d5, d8);
        is2.a(d9, d3 + 0.0, d11, d6, d8);
        is2.a(d9, d3 + 1.0, d11, d6, d7);
        is2.a(d10, d3 + 1.0, d12, d5, d7);
        is2.a(d10, d3 + 0.0, d12, d5, d8);
        is2.a(d10, d3 + 0.0, d11, d6, d8);
        is2.a(d10, d3 + 1.0, d11, d6, d7);
        is2.a(d10, d3 + 1.0, d11, d5, d7);
        is2.a(d10, d3 + 0.0, d11, d5, d8);
        is2.a(d10, d3 + 0.0, d12, d6, d8);
        is2.a(d10, d3 + 1.0, d12, d6, d7);
        d9 = d2 + 0.5 - 0.5;
        d10 = d2 + 0.5 + 0.5;
        d11 = d4 + 0.5 - 0.25;
        d12 = d4 + 0.5 + 0.25;
        is2.a(d9, d3 + 1.0, d11, d5, d7);
        is2.a(d9, d3 + 0.0, d11, d5, d8);
        is2.a(d10, d3 + 0.0, d11, d6, d8);
        is2.a(d10, d3 + 1.0, d11, d6, d7);
        is2.a(d10, d3 + 1.0, d11, d5, d7);
        is2.a(d10, d3 + 0.0, d11, d5, d8);
        is2.a(d9, d3 + 0.0, d11, d6, d8);
        is2.a(d9, d3 + 1.0, d11, d6, d7);
        is2.a(d10, d3 + 1.0, d12, d5, d7);
        is2.a(d10, d3 + 0.0, d12, d5, d8);
        is2.a(d9, d3 + 0.0, d12, d6, d8);
        is2.a(d9, d3 + 1.0, d12, d6, d7);
        is2.a(d9, d3 + 1.0, d12, d5, d7);
        is2.a(d9, d3 + 0.0, d12, d5, d8);
        is2.a(d10, d3 + 0.0, d12, d6, d8);
        is2.a(d10, d3 + 1.0, d12, d6, d7);
    }

    public boolean j(Block nq2, int n2, int n3, int n4) {
        float f2;
        float f3;
        float f4;
        int n5;
        int n6;
        is is2 = is.a;
        boolean bl2 = nq2.b(this.a, n2, n3 + 1, n4, 1);
        boolean bl3 = nq2.b(this.a, n2, n3 - 1, n4, 0);
        boolean[] blArray = new boolean[]{nq2.b(this.a, n2, n3, n4 - 1, 2), nq2.b(this.a, n2, n3, n4 + 1, 3), nq2.b(this.a, n2 - 1, n3, n4, 4), nq2.b(this.a, n2 + 1, n3, n4, 5)};
        if (!(bl2 || bl3 || blArray[0] || blArray[1] || blArray[2] || blArray[3])) {
            return false;
        }
        boolean bl4 = false;
        float f5 = 0.5f;
        float f6 = 1.0f;
        float f7 = 0.8f;
        float f8 = 0.6f;
        double d2 = 0.0;
        double d3 = 1.0;
        hb hb2 = nq2.bs;
        int n7 = this.a.e(n2, n3, n4);
        float f9 = this.a(n2, n3, n4, hb2);
        float f10 = this.a(n2, n3, n4 + 1, hb2);
        float f11 = this.a(n2 + 1, n3, n4 + 1, hb2);
        float f12 = this.a(n2 + 1, n3, n4, hb2);
        if (this.d || bl2) {
            bl4 = true;
            int n8 = nq2.a(1, n7);
            float f13 = (float)ld.a(this.a, n2, n3, n4, hb2);
            if (f13 > -999.0f) {
                n8 = nq2.a(2, n7);
            }
            n6 = (n8 & 0xF) << 4;
            n5 = n8 & 0xF0;
            double d4 = ((double)n6 + 8.0) / 256.0;
            double d5 = ((double)n5 + 8.0) / 256.0;
            if (f13 < -999.0f) {
                f13 = 0.0f;
            } else {
                d4 = (float)(n6 + 16) / 256.0f;
                d5 = (float)(n5 + 16) / 256.0f;
            }
            f4 = TrigLookup.a(f13) * 8.0f / 256.0f;
            f3 = TrigLookup.b(f13) * 8.0f / 256.0f;
            f2 = nq2.d(this.a, n2, n3, n4);
            is2.a(f6 * f2, f6 * f2, f6 * f2);
            is2.a(n2 + 0, (float)n3 + f9, n4 + 0, d4 - (double)f3 - (double)f4, d5 - (double)f3 + (double)f4);
            is2.a(n2 + 0, (float)n3 + f10, n4 + 1, d4 - (double)f3 + (double)f4, d5 + (double)f3 + (double)f4);
            is2.a(n2 + 1, (float)n3 + f11, n4 + 1, d4 + (double)f3 + (double)f4, d5 + (double)f3 - (double)f4);
            is2.a(n2 + 1, (float)n3 + f12, n4 + 0, d4 + (double)f3 - (double)f4, d5 - (double)f3 - (double)f4);
        }
        if (this.d || bl3) {
            float f14 = nq2.d(this.a, n2, n3 - 1, n4);
            is2.a(f5 * f14, f5 * f14, f5 * f14);
            this.a(nq2, (double)n2, (double)n3, (double)n4, nq2.a(0));
            bl4 = true;
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            float f15;
            float f16;
            float f17;
            int n9 = n2;
            n6 = n3;
            n5 = n4;
            if (i2 == 0) {
                --n5;
            }
            if (i2 == 1) {
                ++n5;
            }
            if (i2 == 2) {
                --n9;
            }
            if (i2 == 3) {
                ++n9;
            }
            int n10 = nq2.a(i2 + 2, n7);
            int n11 = (n10 & 0xF) << 4;
            int n12 = n10 & 0xF0;
            if (!this.d && !blArray[i2]) continue;
            if (i2 == 0) {
                f17 = f9;
                f4 = f12;
                f3 = n2;
                f16 = n2 + 1;
                f2 = n4;
                f15 = n4;
            } else if (i2 == 1) {
                f17 = f11;
                f4 = f10;
                f3 = n2 + 1;
                f16 = n2;
                f2 = n4 + 1;
                f15 = n4 + 1;
            } else if (i2 == 2) {
                f17 = f10;
                f4 = f9;
                f3 = n2;
                f16 = n2;
                f2 = n4 + 1;
                f15 = n4;
            } else {
                f17 = f12;
                f4 = f11;
                f3 = n2 + 1;
                f16 = n2 + 1;
                f2 = n4;
                f15 = n4 + 1;
            }
            bl4 = true;
            double d6 = (float)(n11 + 0) / 256.0f;
            double d7 = ((double)(n11 + 16) - 0.01) / 256.0;
            double d8 = ((float)n12 + (1.0f - f17) * 16.0f) / 256.0f;
            double d9 = ((float)n12 + (1.0f - f4) * 16.0f) / 256.0f;
            double d10 = ((double)(n12 + 16) - 0.01) / 256.0;
            float f18 = nq2.d(this.a, n9, n6, n5);
            f18 = i2 < 2 ? (f18 *= f7) : (f18 *= f8);
            is2.a(f6 * f18, f6 * f18, f6 * f18);
            is2.a(f3, (float)n3 + f17, f2, d6, d8);
            is2.a(f16, (float)n3 + f4, f15, d7, d9);
            is2.a(f16, n3 + 0, f15, d7, d10);
            is2.a(f3, n3 + 0, f2, d6, d10);
        }
        nq2.bl = d2;
        nq2.bo = d3;
        return bl4;
    }

    private float a(int n2, int n3, int n4, hb hb2) {
        int n5 = 0;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < 4; ++i2) {
            int n6 = n2 - (i2 & 1);
            int n7 = n3;
            int n8 = n4 - (i2 >> 1 & 1);
            if (this.a.f(n6, n7 + 1, n8) == hb2) {
                return 1.0f;
            }
            hb hb3 = this.a.f(n6, n7, n8);
            if (hb3 == hb2) {
                int n9 = this.a.e(n6, n7, n8);
                if (n9 >= 8 || n9 == 0) {
                    f2 += ld.b(n9) * 10.0f;
                    n5 += 10;
                }
                f2 += ld.b(n9);
                ++n5;
                continue;
            }
            if (hb3.a()) continue;
            f2 += 1.0f;
            ++n5;
        }
        return 1.0f - f2 / (float)n5;
    }

    public void a(Block nq2, Session cy2, int n2, int n3, int n4) {
        float f2 = 0.5f;
        float f3 = 1.0f;
        float f4 = 0.8f;
        float f5 = 0.6f;
        is is2 = is.a;
        is2.b();
        float f6 = nq2.d((pk)cy2, n2, n3, n4);
        float f7 = nq2.d((pk)cy2, n2, n3 - 1, n4);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f2 * f7, f2 * f7, f2 * f7);
        this.a(nq2, -0.5, -0.5, -0.5, nq2.a(0));
        f7 = nq2.d((pk)cy2, n2, n3 + 1, n4);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f3 * f7, f3 * f7, f3 * f7);
        this.b(nq2, -0.5, -0.5, -0.5, nq2.a(1));
        f7 = nq2.d((pk)cy2, n2, n3, n4 - 1);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f4 * f7, f4 * f7, f4 * f7);
        this.c(nq2, -0.5, -0.5, -0.5, nq2.a(2));
        f7 = nq2.d((pk)cy2, n2, n3, n4 + 1);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f4 * f7, f4 * f7, f4 * f7);
        this.d(nq2, -0.5, -0.5, -0.5, nq2.a(3));
        f7 = nq2.d((pk)cy2, n2 - 1, n3, n4);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f5 * f7, f5 * f7, f5 * f7);
        this.e(nq2, -0.5, -0.5, -0.5, nq2.a(4));
        f7 = nq2.d((pk)cy2, n2 + 1, n3, n4);
        if (f7 < f6) {
            f7 = f6;
        }
        is2.a(f5 * f7, f5 * f7, f5 * f7);
        this.f(nq2, -0.5, -0.5, -0.5, nq2.a(5));
        is2.a();
    }

    public boolean k(Block nq2, int n2, int n3, int n4) {
        int n5 = nq2.b(this.a, n2, n3, n4);
        float f2 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n5 & 0xFF) / 255.0f;
        return this.a(nq2, n2, n3, n4, f2, f3, f4);
    }

    public boolean a(Block nq2, int n2, int n3, int n4, float f2, float f3, float f4) {
        float f5;
        is is2 = is.a;
        boolean bl2 = false;
        float f6 = 0.5f;
        float f7 = 1.0f;
        float f8 = 0.8f;
        float f9 = 0.6f;
        float f10 = f7 * f2;
        float f11 = f7 * f3;
        float f12 = f7 * f4;
        if (nq2 == Block.u) {
            f4 = 1.0f;
            f3 = 1.0f;
            f2 = 1.0f;
        }
        float f13 = f6 * f2;
        float f14 = f8 * f2;
        float f15 = f9 * f2;
        float f16 = f6 * f3;
        float f17 = f8 * f3;
        float f18 = f9 * f3;
        float f19 = f6 * f4;
        float f20 = f8 * f4;
        float f21 = f9 * f4;
        float f22 = nq2.d(this.a, n2, n3, n4);
        if (this.d || nq2.b(this.a, n2, n3 - 1, n4, 0)) {
            f5 = nq2.d(this.a, n2, n3 - 1, n4);
            is2.a(f13 * f5, f16 * f5, f19 * f5);
            this.a(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 0));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3 + 1, n4, 1)) {
            f5 = nq2.d(this.a, n2, n3 + 1, n4);
            if (nq2.bo != 1.0 && !nq2.bs.d()) {
                f5 = f22;
            }
            is2.a(f10 * f5, f11 * f5, f12 * f5);
            this.b(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 1));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3, n4 - 1, 2)) {
            f5 = nq2.d(this.a, n2, n3, n4 - 1);
            if (nq2.bm > 0.0) {
                f5 = f22;
            }
            is2.a(f14 * f5, f17 * f5, f20 * f5);
            this.c(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 2));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3, n4 + 1, 3)) {
            f5 = nq2.d(this.a, n2, n3, n4 + 1);
            if (nq2.bp < 1.0) {
                f5 = f22;
            }
            is2.a(f14 * f5, f17 * f5, f20 * f5);
            this.d(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 3));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2 - 1, n3, n4, 4)) {
            f5 = nq2.d(this.a, n2 - 1, n3, n4);
            if (nq2.bk > 0.0) {
                f5 = f22;
            }
            is2.a(f15 * f5, f18 * f5, f21 * f5);
            this.e(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 4));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2 + 1, n3, n4, 5)) {
            f5 = nq2.d(this.a, n2 + 1, n3, n4);
            if (nq2.bn < 1.0) {
                f5 = f22;
            }
            is2.a(f15 * f5, f18 * f5, f21 * f5);
            this.f(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 5));
            bl2 = true;
        }
        return bl2;
    }

    public boolean l(Block nq2, int n2, int n3, int n4) {
        int n5 = nq2.b(this.a, n2, n3, n4);
        float f2 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n5 & 0xFF) / 255.0f;
        return this.b(nq2, n2, n3, n4, f2, f3, f4);
    }

    public boolean b(Block nq2, int n2, int n3, int n4, float f2, float f3, float f4) {
        float f5;
        is is2 = is.a;
        boolean bl2 = false;
        float f6 = 0.5f;
        float f7 = 1.0f;
        float f8 = 0.8f;
        float f9 = 0.6f;
        float f10 = f6 * f2;
        float f11 = f7 * f2;
        float f12 = f8 * f2;
        float f13 = f9 * f2;
        float f14 = f6 * f3;
        float f15 = f7 * f3;
        float f16 = f8 * f3;
        float f17 = f9 * f3;
        float f18 = f6 * f4;
        float f19 = f7 * f4;
        float f20 = f8 * f4;
        float f21 = f9 * f4;
        float f22 = 0.0625f;
        float f23 = nq2.d(this.a, n2, n3, n4);
        if (this.d || nq2.b(this.a, n2, n3 - 1, n4, 0)) {
            f5 = nq2.d(this.a, n2, n3 - 1, n4);
            is2.a(f10 * f5, f14 * f5, f18 * f5);
            this.a(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 0));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3 + 1, n4, 1)) {
            f5 = nq2.d(this.a, n2, n3 + 1, n4);
            if (nq2.bo != 1.0 && !nq2.bs.d()) {
                f5 = f23;
            }
            is2.a(f11 * f5, f15 * f5, f19 * f5);
            this.b(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 1));
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3, n4 - 1, 2)) {
            f5 = nq2.d(this.a, n2, n3, n4 - 1);
            if (nq2.bm > 0.0) {
                f5 = f23;
            }
            is2.a(f12 * f5, f16 * f5, f20 * f5);
            is2.c(0.0f, 0.0f, f22);
            this.c(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 2));
            is2.c(0.0f, 0.0f, -f22);
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2, n3, n4 + 1, 3)) {
            f5 = nq2.d(this.a, n2, n3, n4 + 1);
            if (nq2.bp < 1.0) {
                f5 = f23;
            }
            is2.a(f12 * f5, f16 * f5, f20 * f5);
            is2.c(0.0f, 0.0f, -f22);
            this.d(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 3));
            is2.c(0.0f, 0.0f, f22);
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2 - 1, n3, n4, 4)) {
            f5 = nq2.d(this.a, n2 - 1, n3, n4);
            if (nq2.bk > 0.0) {
                f5 = f23;
            }
            is2.a(f13 * f5, f17 * f5, f21 * f5);
            is2.c(f22, 0.0f, 0.0f);
            this.e(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 4));
            is2.c(-f22, 0.0f, 0.0f);
            bl2 = true;
        }
        if (this.d || nq2.b(this.a, n2 + 1, n3, n4, 5)) {
            f5 = nq2.d(this.a, n2 + 1, n3, n4);
            if (nq2.bn < 1.0) {
                f5 = f23;
            }
            is2.a(f13 * f5, f17 * f5, f21 * f5);
            is2.c(-f22, 0.0f, 0.0f);
            this.f(nq2, n2, n3, n4, nq2.a(this.a, n2, n3, n4, 5));
            is2.c(f22, 0.0f, 0.0f);
            bl2 = true;
        }
        return bl2;
    }

    public boolean m(Block nq2, int n2, int n3, int n4) {
        float f2;
        boolean bl2;
        boolean bl3 = false;
        float f3 = 0.375f;
        float f4 = 0.625f;
        nq2.a(f3, 0.0f, f3, f4, 1.0f, f4);
        this.k(nq2, n2, n3, n4);
        boolean bl4 = false;
        boolean bl5 = false;
        if (this.a.a(n2 - 1, n3, n4) == nq2.bh || this.a.a(n2 + 1, n3, n4) == nq2.bh) {
            bl4 = true;
        }
        if (this.a.a(n2, n3, n4 - 1) == nq2.bh || this.a.a(n2, n3, n4 + 1) == nq2.bh) {
            bl5 = true;
        }
        boolean bl6 = this.a.a(n2 - 1, n3, n4) == nq2.bh;
        boolean bl7 = this.a.a(n2 + 1, n3, n4) == nq2.bh;
        boolean bl8 = this.a.a(n2, n3, n4 - 1) == nq2.bh;
        boolean bl9 = bl2 = this.a.a(n2, n3, n4 + 1) == nq2.bh;
        if (!bl4 && !bl5) {
            bl4 = true;
        }
        f3 = 0.4375f;
        f4 = 0.5625f;
        float f5 = 0.75f;
        float f6 = 0.9375f;
        float f7 = bl6 ? 0.0f : f3;
        float f8 = bl7 ? 1.0f : f4;
        float f9 = bl8 ? 0.0f : f3;
        float f10 = f2 = bl2 ? 1.0f : f4;
        if (bl4) {
            nq2.a(f7, f5, f3, f8, f6, f4);
            this.k(nq2, n2, n3, n4);
        }
        if (bl5) {
            nq2.a(f3, f5, f9, f4, f6, f2);
            this.k(nq2, n2, n3, n4);
        }
        f5 = 0.375f;
        f6 = 0.5625f;
        if (bl4) {
            nq2.a(f7, f5, f3, f8, f6, f4);
            this.k(nq2, n2, n3, n4);
        }
        if (bl5) {
            nq2.a(f3, f5, f9, f4, f6, f2);
            this.k(nq2, n2, n3, n4);
        }
        nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return bl3;
    }

    public boolean n(Block nq2, int n2, int n3, int n4) {
        boolean bl2 = false;
        int n5 = this.a.e(n2, n3, n4);
        if (n5 == 0) {
            nq2.a(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            this.k(nq2, n2, n3, n4);
            nq2.a(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            this.k(nq2, n2, n3, n4);
        } else if (n5 == 1) {
            nq2.a(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            this.k(nq2, n2, n3, n4);
            nq2.a(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            this.k(nq2, n2, n3, n4);
        } else if (n5 == 2) {
            nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            this.k(nq2, n2, n3, n4);
            nq2.a(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            this.k(nq2, n2, n3, n4);
        } else if (n5 == 3) {
            nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            this.k(nq2, n2, n3, n4);
            nq2.a(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            this.k(nq2, n2, n3, n4);
        }
        nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return bl2;
    }

    public boolean o(Block nq2, int n2, int n3, int n4) {
        is is2 = is.a;
        gv gv2 = (gv)nq2;
        boolean bl2 = false;
        float f2 = 0.5f;
        float f3 = 1.0f;
        float f4 = 0.8f;
        float f5 = 0.6f;
        float f6 = nq2.d(this.a, n2, n3, n4);
        float f7 = nq2.d(this.a, n2, n3 - 1, n4);
        if (gv2.bl > 0.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f2 * f7, f2 * f7, f2 * f7);
        this.a(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 0));
        bl2 = true;
        f7 = nq2.d(this.a, n2, n3 + 1, n4);
        if (gv2.bo < 1.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f3 * f7, f3 * f7, f3 * f7);
        this.b(nq2, (double)n2, (double)n3, (double)n4, nq2.a(this.a, n2, n3, n4, 1));
        bl2 = true;
        f7 = nq2.d(this.a, n2, n3, n4 - 1);
        if (gv2.bm > 0.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f4 * f7, f4 * f7, f4 * f7);
        int n5 = nq2.a(this.a, n2, n3, n4, 2);
        if (n5 < 0) {
            this.c = true;
            n5 = -n5;
        }
        this.c(nq2, n2, n3, n4, n5);
        bl2 = true;
        this.c = false;
        f7 = nq2.d(this.a, n2, n3, n4 + 1);
        if (gv2.bp < 1.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f4 * f7, f4 * f7, f4 * f7);
        n5 = nq2.a(this.a, n2, n3, n4, 3);
        if (n5 < 0) {
            this.c = true;
            n5 = -n5;
        }
        this.d(nq2, n2, n3, n4, n5);
        bl2 = true;
        this.c = false;
        f7 = nq2.d(this.a, n2 - 1, n3, n4);
        if (gv2.bk > 0.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f5 * f7, f5 * f7, f5 * f7);
        n5 = nq2.a(this.a, n2, n3, n4, 4);
        if (n5 < 0) {
            this.c = true;
            n5 = -n5;
        }
        this.e(nq2, n2, n3, n4, n5);
        bl2 = true;
        this.c = false;
        f7 = nq2.d(this.a, n2 + 1, n3, n4);
        if (gv2.bn < 1.0) {
            f7 = f6;
        }
        if (Block.s[nq2.bh] > 0) {
            f7 = 1.0f;
        }
        is2.a(f5 * f7, f5 * f7, f5 * f7);
        n5 = nq2.a(this.a, n2, n3, n4, 5);
        if (n5 < 0) {
            this.c = true;
            n5 = -n5;
        }
        this.f(nq2, n2, n3, n4, n5);
        bl2 = true;
        this.c = false;
        return bl2;
    }

    public void a(Block nq2, double d2, double d3, double d4, int n2) {
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d5 = ((double)n3 + nq2.bk * 16.0) / 256.0;
        double d6 = ((double)n3 + nq2.bn * 16.0 - 0.01) / 256.0;
        double d7 = ((double)n4 + nq2.bm * 16.0) / 256.0;
        double d8 = ((double)n4 + nq2.bp * 16.0 - 0.01) / 256.0;
        if (nq2.bk < 0.0 || nq2.bn > 1.0) {
            d5 = ((float)n3 + 0.0f) / 256.0f;
            d6 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bm < 0.0 || nq2.bp > 1.0) {
            d7 = ((float)n4 + 0.0f) / 256.0f;
            d8 = ((float)n4 + 15.99f) / 256.0f;
        }
        double d9 = d2 + nq2.bk;
        double d10 = d2 + nq2.bn;
        double d11 = d3 + nq2.bl;
        double d12 = d4 + nq2.bm;
        double d13 = d4 + nq2.bp;
        is2.a(d9, d11, d13, d5, d8);
        is2.a(d9, d11, d12, d5, d7);
        is2.a(d10, d11, d12, d6, d7);
        is2.a(d10, d11, d13, d6, d8);
    }

    public void b(Block nq2, double d2, double d3, double d4, int n2) {
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d5 = ((double)n3 + nq2.bk * 16.0) / 256.0;
        double d6 = ((double)n3 + nq2.bn * 16.0 - 0.01) / 256.0;
        double d7 = ((double)n4 + nq2.bm * 16.0) / 256.0;
        double d8 = ((double)n4 + nq2.bp * 16.0 - 0.01) / 256.0;
        if (nq2.bk < 0.0 || nq2.bn > 1.0) {
            d5 = ((float)n3 + 0.0f) / 256.0f;
            d6 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bm < 0.0 || nq2.bp > 1.0) {
            d7 = ((float)n4 + 0.0f) / 256.0f;
            d8 = ((float)n4 + 15.99f) / 256.0f;
        }
        double d9 = d2 + nq2.bk;
        double d10 = d2 + nq2.bn;
        double d11 = d3 + nq2.bo;
        double d12 = d4 + nq2.bm;
        double d13 = d4 + nq2.bp;
        is2.a(d10, d11, d13, d6, d8);
        is2.a(d10, d11, d12, d6, d7);
        is2.a(d9, d11, d12, d5, d7);
        is2.a(d9, d11, d13, d5, d8);
    }

    public void c(Block nq2, double d2, double d3, double d4, int n2) {
        double d5;
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d6 = ((double)n3 + nq2.bk * 16.0) / 256.0;
        double d7 = ((double)n3 + nq2.bn * 16.0 - 0.01) / 256.0;
        double d8 = ((double)n4 + nq2.bl * 16.0) / 256.0;
        double d9 = ((double)n4 + nq2.bo * 16.0 - 0.01) / 256.0;
        if (this.c) {
            d5 = d6;
            d6 = d7;
            d7 = d5;
        }
        if (nq2.bk < 0.0 || nq2.bn > 1.0) {
            d6 = ((float)n3 + 0.0f) / 256.0f;
            d7 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bl < 0.0 || nq2.bo > 1.0) {
            d8 = ((float)n4 + 0.0f) / 256.0f;
            d9 = ((float)n4 + 15.99f) / 256.0f;
        }
        d5 = d2 + nq2.bk;
        double d10 = d2 + nq2.bn;
        double d11 = d3 + nq2.bl;
        double d12 = d3 + nq2.bo;
        double d13 = d4 + nq2.bm;
        is2.a(d5, d12, d13, d7, d8);
        is2.a(d10, d12, d13, d6, d8);
        is2.a(d10, d11, d13, d6, d9);
        is2.a(d5, d11, d13, d7, d9);
    }

    public void d(Block nq2, double d2, double d3, double d4, int n2) {
        double d5;
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d6 = ((double)n3 + nq2.bk * 16.0) / 256.0;
        double d7 = ((double)n3 + nq2.bn * 16.0 - 0.01) / 256.0;
        double d8 = ((double)n4 + nq2.bl * 16.0) / 256.0;
        double d9 = ((double)n4 + nq2.bo * 16.0 - 0.01) / 256.0;
        if (this.c) {
            d5 = d6;
            d6 = d7;
            d7 = d5;
        }
        if (nq2.bk < 0.0 || nq2.bn > 1.0) {
            d6 = ((float)n3 + 0.0f) / 256.0f;
            d7 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bl < 0.0 || nq2.bo > 1.0) {
            d8 = ((float)n4 + 0.0f) / 256.0f;
            d9 = ((float)n4 + 15.99f) / 256.0f;
        }
        d5 = d2 + nq2.bk;
        double d10 = d2 + nq2.bn;
        double d11 = d3 + nq2.bl;
        double d12 = d3 + nq2.bo;
        double d13 = d4 + nq2.bp;
        is2.a(d5, d12, d13, d6, d8);
        is2.a(d5, d11, d13, d6, d9);
        is2.a(d10, d11, d13, d7, d9);
        is2.a(d10, d12, d13, d7, d8);
    }

    public void e(Block nq2, double d2, double d3, double d4, int n2) {
        double d5;
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d6 = ((double)n3 + nq2.bm * 16.0) / 256.0;
        double d7 = ((double)n3 + nq2.bp * 16.0 - 0.01) / 256.0;
        double d8 = ((double)n4 + nq2.bl * 16.0) / 256.0;
        double d9 = ((double)n4 + nq2.bo * 16.0 - 0.01) / 256.0;
        if (this.c) {
            d5 = d6;
            d6 = d7;
            d7 = d5;
        }
        if (nq2.bm < 0.0 || nq2.bp > 1.0) {
            d6 = ((float)n3 + 0.0f) / 256.0f;
            d7 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bl < 0.0 || nq2.bo > 1.0) {
            d8 = ((float)n4 + 0.0f) / 256.0f;
            d9 = ((float)n4 + 15.99f) / 256.0f;
        }
        d5 = d2 + nq2.bk;
        double d10 = d3 + nq2.bl;
        double d11 = d3 + nq2.bo;
        double d12 = d4 + nq2.bm;
        double d13 = d4 + nq2.bp;
        is2.a(d5, d11, d13, d7, d8);
        is2.a(d5, d11, d12, d6, d8);
        is2.a(d5, d10, d12, d6, d9);
        is2.a(d5, d10, d13, d7, d9);
    }

    public void f(Block nq2, double d2, double d3, double d4, int n2) {
        double d5;
        is is2 = is.a;
        if (this.b >= 0) {
            n2 = this.b;
        }
        int n3 = (n2 & 0xF) << 4;
        int n4 = n2 & 0xF0;
        double d6 = ((double)n3 + nq2.bm * 16.0) / 256.0;
        double d7 = ((double)n3 + nq2.bp * 16.0 - 0.01) / 256.0;
        double d8 = ((double)n4 + nq2.bl * 16.0) / 256.0;
        double d9 = ((double)n4 + nq2.bo * 16.0 - 0.01) / 256.0;
        if (this.c) {
            d5 = d6;
            d6 = d7;
            d7 = d5;
        }
        if (nq2.bm < 0.0 || nq2.bp > 1.0) {
            d6 = ((float)n3 + 0.0f) / 256.0f;
            d7 = ((float)n3 + 15.99f) / 256.0f;
        }
        if (nq2.bl < 0.0 || nq2.bo > 1.0) {
            d8 = ((float)n4 + 0.0f) / 256.0f;
            d9 = ((float)n4 + 15.99f) / 256.0f;
        }
        d5 = d2 + nq2.bn;
        double d10 = d3 + nq2.bl;
        double d11 = d3 + nq2.bo;
        double d12 = d4 + nq2.bm;
        double d13 = d4 + nq2.bp;
        is2.a(d5, d10, d13, d6, d9);
        is2.a(d5, d10, d12, d7, d9);
        is2.a(d5, d11, d12, d7, d8);
        is2.a(d5, d11, d13, d6, d8);
    }

    public void a(Block nq2, float f2) {
        int n2 = nq2.g();
        is is2 = is.a;
        if (n2 == 0) {
            nq2.f();
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            float f3 = 0.5f;
            float f4 = 1.0f;
            float f5 = 0.8f;
            float f6 = 0.6f;
            is2.b();
            is2.a(f4, f4, f4, f2);
            this.a(nq2, 0.0, 0.0, 0.0, nq2.a(0));
            is2.a(f3, f3, f3, f2);
            this.b(nq2, 0.0, 0.0, 0.0, nq2.a(1));
            is2.a(f5, f5, f5, f2);
            this.c(nq2, 0.0, 0.0, 0.0, nq2.a(2));
            this.d(nq2, 0.0, 0.0, 0.0, nq2.a(3));
            is2.a(f6, f6, f6, f2);
            this.e(nq2, 0.0, 0.0, 0.0, nq2.a(4));
            this.f(nq2, 0.0, 0.0, 0.0, nq2.a(5));
            is2.a();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
    }

    public void a(Block nq2) {
        int n2 = -1;
        is is2 = is.a;
        int n3 = nq2.g();
        if (n3 == 0) {
            nq2.f();
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            this.a(nq2, 0.0, 0.0, 0.0, nq2.a(0));
            is2.a();
            is2.b();
            is2.b(0.0f, 1.0f, 0.0f);
            this.b(nq2, 0.0, 0.0, 0.0, nq2.a(1));
            is2.a();
            is2.b();
            is2.b(0.0f, 0.0f, -1.0f);
            this.c(nq2, 0.0, 0.0, 0.0, nq2.a(2));
            is2.a();
            is2.b();
            is2.b(0.0f, 0.0f, 1.0f);
            this.d(nq2, 0.0, 0.0, 0.0, nq2.a(3));
            is2.a();
            is2.b();
            is2.b(-1.0f, 0.0f, 0.0f);
            this.e(nq2, 0.0, 0.0, 0.0, nq2.a(4));
            is2.a();
            is2.b();
            is2.b(1.0f, 0.0f, 0.0f);
            this.f(nq2, 0.0, 0.0, 0.0, nq2.a(5));
            is2.a();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        } else if (n3 == 1) {
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            this.a(nq2, n2, -0.5, -0.5, -0.5);
            is2.a();
        } else if (n3 == 13) {
            nq2.f();
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            float f2 = 0.0625f;
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            this.a(nq2, 0.0, 0.0, 0.0, nq2.a(0));
            is2.a();
            is2.b();
            is2.b(0.0f, 1.0f, 0.0f);
            this.b(nq2, 0.0, 0.0, 0.0, nq2.a(1));
            is2.a();
            is2.b();
            is2.b(0.0f, 0.0f, -1.0f);
            is2.c(0.0f, 0.0f, f2);
            this.c(nq2, 0.0, 0.0, 0.0, nq2.a(2));
            is2.c(0.0f, 0.0f, -f2);
            is2.a();
            is2.b();
            is2.b(0.0f, 0.0f, 1.0f);
            is2.c(0.0f, 0.0f, -f2);
            this.d(nq2, 0.0, 0.0, 0.0, nq2.a(3));
            is2.c(0.0f, 0.0f, f2);
            is2.a();
            is2.b();
            is2.b(-1.0f, 0.0f, 0.0f);
            is2.c(f2, 0.0f, 0.0f);
            this.e(nq2, 0.0, 0.0, 0.0, nq2.a(4));
            is2.c(-f2, 0.0f, 0.0f);
            is2.a();
            is2.b();
            is2.b(1.0f, 0.0f, 0.0f);
            is2.c(-f2, 0.0f, 0.0f);
            this.f(nq2, 0.0, 0.0, 0.0, nq2.a(5));
            is2.c(f2, 0.0f, 0.0f);
            is2.a();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        } else if (n3 == 6) {
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            this.b(nq2, n2, -0.5, -0.5, -0.5);
            is2.a();
        } else if (n3 == 2) {
            is2.b();
            is2.b(0.0f, -1.0f, 0.0f);
            this.a(nq2, -0.5, -0.5, -0.5, 0.0, 0.0);
            is2.a();
        } else if (n3 == 10) {
            for (int i2 = 0; i2 < 2; ++i2) {
                if (i2 == 0) {
                    nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
                if (i2 == 1) {
                    nq2.a(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
                }
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                is2.b();
                is2.b(0.0f, -1.0f, 0.0f);
                this.a(nq2, 0.0, 0.0, 0.0, nq2.a(0));
                is2.a();
                is2.b();
                is2.b(0.0f, 1.0f, 0.0f);
                this.b(nq2, 0.0, 0.0, 0.0, nq2.a(1));
                is2.a();
                is2.b();
                is2.b(0.0f, 0.0f, -1.0f);
                this.c(nq2, 0.0, 0.0, 0.0, nq2.a(2));
                is2.a();
                is2.b();
                is2.b(0.0f, 0.0f, 1.0f);
                this.d(nq2, 0.0, 0.0, 0.0, nq2.a(3));
                is2.a();
                is2.b();
                is2.b(-1.0f, 0.0f, 0.0f);
                this.e(nq2, 0.0, 0.0, 0.0, nq2.a(4));
                is2.a();
                is2.b();
                is2.b(1.0f, 0.0f, 0.0f);
                this.f(nq2, 0.0, 0.0, 0.0, nq2.a(5));
                is2.a();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
        } else if (n3 == 11) {
            for (int i3 = 0; i3 < 4; ++i3) {
                float f3 = 0.125f;
                if (i3 == 0) {
                    nq2.a(0.5f - f3, 0.0f, 0.0f, 0.5f + f3, 1.0f, f3 * 2.0f);
                }
                if (i3 == 1) {
                    nq2.a(0.5f - f3, 0.0f, 1.0f - f3 * 2.0f, 0.5f + f3, 1.0f, 1.0f);
                }
                f3 = 0.0625f;
                if (i3 == 2) {
                    nq2.a(0.5f - f3, 1.0f - f3 * 3.0f, -f3 * 2.0f, 0.5f + f3, 1.0f - f3, 1.0f + f3 * 2.0f);
                }
                if (i3 == 3) {
                    nq2.a(0.5f - f3, 0.5f - f3 * 3.0f, -f3 * 2.0f, 0.5f + f3, 0.5f - f3, 1.0f + f3 * 2.0f);
                }
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                is2.b();
                is2.b(0.0f, -1.0f, 0.0f);
                this.a(nq2, 0.0, 0.0, 0.0, nq2.a(0));
                is2.a();
                is2.b();
                is2.b(0.0f, 1.0f, 0.0f);
                this.b(nq2, 0.0, 0.0, 0.0, nq2.a(1));
                is2.a();
                is2.b();
                is2.b(0.0f, 0.0f, -1.0f);
                this.c(nq2, 0.0, 0.0, 0.0, nq2.a(2));
                is2.a();
                is2.b();
                is2.b(0.0f, 0.0f, 1.0f);
                this.d(nq2, 0.0, 0.0, 0.0, nq2.a(3));
                is2.a();
                is2.b();
                is2.b(-1.0f, 0.0f, 0.0f);
                this.e(nq2, 0.0, 0.0, 0.0, nq2.a(4));
                is2.a();
                is2.b();
                is2.b(1.0f, 0.0f, 0.0f);
                this.f(nq2, 0.0, 0.0, 0.0, nq2.a(5));
                is2.a();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            nq2.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static boolean a(int n2) {
        if (n2 == 0) {
            return true;
        }
        if (n2 == 13) {
            return true;
        }
        if (n2 == 10) {
            return true;
        }
        return n2 == 11;
    }
}

