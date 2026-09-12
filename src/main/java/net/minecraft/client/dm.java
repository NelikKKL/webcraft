/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class dm {
    private pk a;
    private gw b = new gw();
    private ge c = new ge();
    private b[] d = new b[32];

    public dm(pk pk2) {
        this.a = pk2;
    }

    public bt a(lw lw2, lw lw3, float f2) {
        return this.a(lw2, lw3.aw, lw3.aG.b, lw3.ay, f2);
    }

    public bt a(lw lw2, int n2, int n3, int n4, float f2) {
        return this.a(lw2, (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f, f2);
    }

    private bt a(lw lw2, double d2, double d3, double d4, float f2) {
        this.b.a();
        this.c.a();
        b b2 = this.a(TrigLookup.b(lw2.aG.a), TrigLookup.b(lw2.aG.b), TrigLookup.b(lw2.aG.c));
        b b3 = this.a(TrigLookup.b(d2 - (double)(lw2.aP / 2.0f)), TrigLookup.b(d3), TrigLookup.b(d4 - (double)(lw2.aP / 2.0f)));
        b b4 = new b(TrigLookup.d(lw2.aP + 1.0f), TrigLookup.d(lw2.aQ + 1.0f), TrigLookup.d(lw2.aP + 1.0f));
        bt bt2 = this.a(lw2, b2, b3, b4, f2);
        return bt2;
    }

    private bt a(lw lw2, b b2, b b3, b b4, float f2) {
        b2.f = 0.0f;
        b2.h = b2.g = b2.a(b3);
        this.b.a();
        this.b.a(b2);
        b b5 = b2;
        while (!this.b.c()) {
            b b6 = this.b.b();
            if (b6.d == b3.d) {
                return this.a(b2, b3);
            }
            if (b6.a(b3) < b5.a(b3)) {
                b5 = b6;
            }
            b6.j = true;
            int n2 = this.b(lw2, b6, b4, b3, f2);
            for (int i2 = 0; i2 < n2; ++i2) {
                b b7 = this.d[i2];
                float f3 = b6.f + b6.a(b7);
                if (b7.a() && !(f3 < b7.f)) continue;
                b7.i = b6;
                b7.f = f3;
                b7.g = b7.a(b3);
                if (b7.a()) {
                    this.b.a(b7, b7.f + b7.g);
                    continue;
                }
                b7.h = b7.f + b7.g;
                this.b.a(b7);
            }
        }
        if (b5 == b2) {
            return null;
        }
        return this.a(b2, b5);
    }

    private int b(lw lw2, b b2, b b3, b b4, float f2) {
        int n2 = 0;
        int n3 = 0;
        if (this.a(lw2, b2.a, b2.b + 1, b2.c, b3) > 0) {
            n3 = 1;
        }
        b b5 = this.a(lw2, b2.a, b2.b, b2.c + 1, b3, n3);
        b b6 = this.a(lw2, b2.a - 1, b2.b, b2.c, b3, n3);
        b b7 = this.a(lw2, b2.a + 1, b2.b, b2.c, b3, n3);
        b b8 = this.a(lw2, b2.a, b2.b, b2.c - 1, b3, n3);
        if (b5 != null && !b5.j && b5.a(b4) < f2) {
            this.d[n2++] = b5;
        }
        if (b6 != null && !b6.j && b6.a(b4) < f2) {
            this.d[n2++] = b6;
        }
        if (b7 != null && !b7.j && b7.a(b4) < f2) {
            this.d[n2++] = b7;
        }
        if (b8 != null && !b8.j && b8.a(b4) < f2) {
            this.d[n2++] = b8;
        }
        return n2;
    }

    private b a(lw lw2, int n2, int n3, int n4, b b2, int n5) {
        b b3 = null;
        if (this.a(lw2, n2, n3, n4, b2) > 0) {
            b3 = this.a(n2, n3, n4);
        }
        if (b3 == null && this.a(lw2, n2, n3 + n5, n4, b2) > 0) {
            b3 = this.a(n2, n3 + n5, n4);
            n3 += n5;
        }
        if (b3 != null) {
            int n6 = 0;
            int n7 = 0;
            while (n3 > 0 && (n7 = this.a(lw2, n2, n3 - 1, n4, b2)) > 0) {
                if (n7 < 0) {
                    return null;
                }
                if (++n6 >= 4) {
                    return null;
                }
                --n3;
            }
            if (n3 > 0) {
                b3 = this.a(n2, n3, n4);
            }
        }
        return b3;
    }

    private final b a(int n2, int n3, int n4) {
        int n5 = n2 | n3 << 10 | n4 << 20;
        b b2 = (b)this.c.a(n5);
        if (b2 == null) {
            b2 = new b(n2, n3, n4);
            this.c.a(n5, b2);
        }
        return b2;
    }

    private int a(lw lw2, int n2, int n3, int n4, b b2) {
        for (int i2 = n2; i2 < n2 + b2.a; ++i2) {
            for (int i3 = n3; i3 < n3 + b2.b; ++i3) {
                for (int i4 = n4; i4 < n4 + b2.c; ++i4) {
                    hb hb2 = this.a.f(n2, n3, n4);
                    if (hb2.c()) {
                        return 0;
                    }
                    if (hb2 != hb.f && hb2 != hb.g) continue;
                    return -1;
                }
            }
        }
        return 1;
    }

    private bt a(b b2, b b3) {
        int n2 = 1;
        b b4 = b3;
        while (b4.i != null) {
            ++n2;
            b4 = b4.i;
        }
        b[] bArray = new b[n2];
        b4 = b3;
        bArray[--n2] = b4;
        while (b4.i != null) {
            b4 = b4.i;
            bArray[--n2] = b4;
        }
        return new bt(bArray);
    }
}

