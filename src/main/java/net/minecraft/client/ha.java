/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ha {
    public static boolean a;
    public byte[] b;
    public boolean c;
    public Session d;
    public oo e;
    public oo f;
    public oo g;
    public byte[] h;
    public int i;
    public final int j;
    public final int k;
    public Map l = new HashMap();
    public List[] m = new List[8];
    public boolean n = false;
    public boolean o = false;
    public boolean p;
    public boolean q = false;
    public boolean r = false;
    public long s = 0L;

    public ha(Session cy2, int n2, int n3) {
        this.d = cy2;
        this.j = n2;
        this.k = n3;
        this.h = new byte[256];
        for (int i2 = 0; i2 < this.m.length; ++i2) {
            this.m[i2] = new ArrayList();
        }
    }

    public ha(Session cy2, byte[] byArray, int n2, int n3) {
        this(cy2, n2, n3);
        this.b = byArray;
        this.e = new oo(byArray.length);
        this.f = new oo(byArray.length);
        this.g = new oo(byArray.length);
    }

    public boolean a(int n2, int n3) {
        return n2 == this.j && n3 == this.k;
    }

    public int b(int n2, int n3) {
        return this.h[n3 << 4 | n2] & 0xFF;
    }

    public void a() {
    }

    public void b() {
        int n2 = 127;
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int i3 = 0; i3 < 16; ++i3) {
                int n3;
                int n4 = i2 << 11 | i3 << 7;
                for (n3 = 127; n3 > 0 && Block.q[this.b[n4 + n3 - 1]] == 0; --n3) {
                }
                this.h[i3 << 4 | i2] = (byte)n3;
                if (n3 >= n2) continue;
                n2 = n3;
            }
        }
        this.i = n2;
        this.o = true;
    }

    public void c() {
        int n2;
        int n3;
        int n4 = 127;
        for (n3 = 0; n3 < 16; ++n3) {
            for (n2 = 0; n2 < 16; ++n2) {
                this.h[n2 << 4 | n3] = -128;
                this.g(n3, 127, n2);
                if ((this.h[n2 << 4 | n3] & 0xFF) >= n4) continue;
                n4 = this.h[n2 << 4 | n3] & 0xFF;
            }
        }
        this.i = n4;
        for (n3 = 0; n3 < 16; ++n3) {
            for (n2 = 0; n2 < 16; ++n2) {
                this.c(n3, n2);
            }
        }
        this.o = true;
    }

    public void d() {
        int n2 = 32;
        for (int i2 = 0; i2 < 16; ++i2) {
            for (int i3 = 0; i3 < 16; ++i3) {
                int n3;
                int n4;
                int n5 = i2 << 11 | i3 << 7;
                for (n4 = 0; n4 < 128; ++n4) {
                    n3 = Block.s[this.b[n5 + n4]];
                    if (n3 <= 0) continue;
                    this.g.a(i2, n4, i3, n3);
                }
                n4 = 15;
                n3 = n2 - 2;
                while (n3 < 128 && n4 > 0) {
                    byte by2 = this.b[n5 + ++n3];
                    int n6 = Block.q[by2];
                    int n7 = Block.s[by2];
                    if (n6 == 0) {
                        n6 = 1;
                    }
                    if (n7 > (n4 -= n6)) {
                        n4 = n7;
                    }
                    if (n4 < 0) {
                        n4 = 0;
                    }
                    this.g.a(i2, n3, i3, n4);
                }
            }
        }
        this.d.a(ch.Block, this.j * 16, n2 - 1, this.k * 16, this.j * 16 + 16, n2 + 1, this.k * 16 + 16);
        this.o = true;
    }

    private void c(int n2, int n3) {
        int n4 = this.b(n2, n3);
        int n5 = this.j * 16 + n2;
        int n6 = this.k * 16 + n3;
        this.f(n5 - 1, n6, n4);
        this.f(n5 + 1, n6, n4);
        this.f(n5, n6 - 1, n4);
        this.f(n5, n6 + 1, n4);
    }

    private void f(int n2, int n3, int n4) {
        int n5 = this.d.d(n2, n3);
        if (n5 > n4) {
            this.d.a(ch.Sky, n2, n4, n3, n2, n5, n3);
        } else if (n5 < n4) {
            this.d.a(ch.Sky, n2, n5, n3, n2, n4, n3);
        }
        this.o = true;
    }

    private void g(int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9 = n8 = this.h[n4 << 4 | n2] & 0xFF;
        if (n3 > n8) {
            n9 = n3;
        }
        int n10 = n2 << 11 | n4 << 7;
        while (n9 > 0 && Block.q[this.b[n10 + n9 - 1]] == 0) {
            --n9;
        }
        if (n9 == n8) {
            return;
        }
        this.d.f(n2, n4, n9, n8);
        this.h[n4 << 4 | n2] = (byte)n9;
        if (n9 < this.i) {
            this.i = n9;
        } else {
            n7 = 127;
            for (n6 = 0; n6 < 16; ++n6) {
                for (n5 = 0; n5 < 16; ++n5) {
                    if ((this.h[n5 << 4 | n6] & 0xFF) >= n7) continue;
                    n7 = this.h[n5 << 4 | n6] & 0xFF;
                }
            }
            this.i = n7;
        }
        n7 = this.j * 16 + n2;
        n6 = this.k * 16 + n4;
        if (n9 < n8) {
            for (n5 = n9; n5 < n8; ++n5) {
                this.f.a(n2, n5, n4, 15);
            }
        } else {
            this.d.a(ch.Sky, n7, n8, n6, n7, n9, n6);
            for (n5 = n8; n5 < n9; ++n5) {
                this.f.a(n2, n5, n4, 0);
            }
        }
        n5 = 15;
        int n11 = n9;
        while (n9 > 0 && n5 > 0) {
            int n12;
            if ((n12 = Block.q[this.a(n2, --n9, n4)]) == 0) {
                n12 = 1;
            }
            if ((n5 -= n12) < 0) {
                n5 = 0;
            }
            this.f.a(n2, n9, n4, n5);
        }
        while (n9 > 0 && Block.q[this.a(n2, n9 - 1, n4)] == 0) {
            --n9;
        }
        if (n9 != n11) {
            this.d.a(ch.Sky, n7 - 1, n9, n6 - 1, n7 + 1, n11, n6 + 1);
        }
        this.o = true;
    }

    public int a(int n2, int n3, int n4) {
        return this.b[n2 << 11 | n4 << 7 | n3];
    }

    public boolean a(int n2, int n3, int n4, int n5, int n6) {
        byte by2 = (byte)n5;
        int n7 = this.h[n4 << 4 | n2] & 0xFF;
        int n8 = this.b[n2 << 11 | n4 << 7 | n3] & 0xFF;
        if (n8 == n5 && this.e.a(n2, n3, n4) == n6) {
            return false;
        }
        int n9 = this.j * 16 + n2;
        int n10 = this.k * 16 + n4;
        this.b[n2 << 11 | n4 << 7 | n3] = by2;
        if (n8 != 0 && !this.d.z) {
            Block.registry[n8].b(this.d, n9, n3, n10);
        }
        this.e.a(n2, n3, n4, n6);
        if (!this.d.q.e) {
            if (Block.q[by2] != 0) {
                if (n3 >= n7) {
                    this.g(n2, n3 + 1, n4);
                }
            } else if (n3 == n7 - 1) {
                this.g(n2, n3, n4);
            }
            this.d.a(ch.Sky, n9, n3, n10, n9, n3, n10);
        }
        this.d.a(ch.Block, n9, n3, n10, n9, n3, n10);
        this.c(n2, n4);
        if (n5 != 0) {
            Block.registry[n5].e(this.d, n9, n3, n10);
        }
        this.e.a(n2, n3, n4, n6);
        this.o = true;
        return true;
    }

    public boolean a(int n2, int n3, int n4, int n5) {
        byte by2 = (byte)n5;
        int n6 = this.h[n4 << 4 | n2] & 0xFF;
        int n7 = this.b[n2 << 11 | n4 << 7 | n3] & 0xFF;
        if (n7 == n5) {
            return false;
        }
        int n8 = this.j * 16 + n2;
        int n9 = this.k * 16 + n4;
        this.b[n2 << 11 | n4 << 7 | n3] = by2;
        if (n7 != 0) {
            Block.registry[n7].b(this.d, n8, n3, n9);
        }
        this.e.a(n2, n3, n4, 0);
        if (Block.q[by2] != 0) {
            if (n3 >= n6) {
                this.g(n2, n3 + 1, n4);
            }
        } else if (n3 == n6 - 1) {
            this.g(n2, n3, n4);
        }
        this.d.a(ch.Sky, n8, n3, n9, n8, n3, n9);
        this.d.a(ch.Block, n8, n3, n9, n8, n3, n9);
        this.c(n2, n4);
        if (n5 != 0 && !this.d.z) {
            Block.registry[n5].e(this.d, n8, n3, n9);
        }
        this.o = true;
        return true;
    }

    public int b(int n2, int n3, int n4) {
        return this.e.a(n2, n3, n4);
    }

    public void b(int n2, int n3, int n4, int n5) {
        this.o = true;
        this.e.a(n2, n3, n4, n5);
    }

    public int a(ch ch2, int n2, int n3, int n4) {
        if (ch2 == ch.Sky) {
            return this.f.a(n2, n3, n4);
        }
        if (ch2 == ch.Block) {
            return this.g.a(n2, n3, n4);
        }
        return 0;
    }

    public void a(ch ch2, int n2, int n3, int n4, int n5) {
        this.o = true;
        if (ch2 == ch.Sky) {
            this.f.a(n2, n3, n4, n5);
        } else if (ch2 == ch.Block) {
            this.g.a(n2, n3, n4, n5);
        } else {
            return;
        }
    }

    public int c(int n2, int n3, int n4, int n5) {
        int n6;
        int n7 = this.f.a(n2, n3, n4);
        if (n7 > 0) {
            a = true;
        }
        if ((n6 = this.g.a(n2, n3, n4)) > (n7 -= n5)) {
            n7 = n6;
        }
        return n7;
    }

    public void a(lw lw2) {
        int n2;
        if (this.q) {
            return;
        }
        this.r = true;
        int n3 = TrigLookup.b(lw2.aw / 16.0);
        int n4 = TrigLookup.b(lw2.ay / 16.0);
        if (n3 != this.j || n4 != this.k) {
            System.out.println("Wrong location! " + lw2);
            Thread.dumpStack();
        }
        if ((n2 = TrigLookup.b(lw2.ax / 16.0)) < 0) {
            n2 = 0;
        }
        if (n2 >= this.m.length) {
            n2 = this.m.length - 1;
        }
        lw2.bn = true;
        lw2.bo = this.j;
        lw2.bp = n2;
        lw2.bq = this.k;
        this.m[n2].add(lw2);
    }

    public void b(lw lw2) {
        this.a(lw2, lw2.bp);
    }

    public void a(lw lw2, int n2) {
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 >= this.m.length) {
            n2 = this.m.length - 1;
        }
        this.m[n2].remove(lw2);
    }

    public boolean c(int n2, int n3, int n4) {
        return n3 >= (this.h[n4 << 4 | n2] & 0xFF);
    }

    public TileEntityRegistry d(int n2, int n3, int n4) {
        on on2 = new on(n2, n3, n4);
        TileEntityRegistry ji2 = (TileEntityRegistry)this.l.get(on2);
        if (ji2 == null) {
            int n5 = this.a(n2, n3, n4);
            if (!Block.p[n5]) {
                return null;
            }
            lh lh2 = (lh)Block.registry[n5];
            lh2.e(this.d, this.j * 16 + n2, n3, this.k * 16 + n4);
            ji2 = (TileEntityRegistry)this.l.get(on2);
        }
        return ji2;
    }

    public void a(TileEntityRegistry ji2) {
        int n2 = ji2.f - this.j * 16;
        int n3 = ji2.g;
        int n4 = ji2.h - this.k * 16;
        this.a(n2, n3, n4, ji2);
    }

    public void a(int n2, int n3, int n4, TileEntityRegistry ji2) {
        on on2 = new on(n2, n3, n4);
        ji2.e = this.d;
        ji2.f = this.j * 16 + n2;
        ji2.g = n3;
        ji2.h = this.k * 16 + n4;
        if (this.a(n2, n3, n4) == 0 || !(Block.registry[this.a(n2, n3, n4)] instanceof lh)) {
            System.out.println("Attempted to place a tile entity where there was no entity tile!");
            return;
        }
        if (this.c) {
            if (this.l.get(on2) != null) {
                this.d.c.remove(this.l.get(on2));
            }
            this.d.c.add(ji2);
        }
        this.l.put(on2, ji2);
    }

    public void e(int n2, int n3, int n4) {
        on on2 = new on(n2, n3, n4);
        if (this.c) {
            this.d.c.remove(this.l.remove(on2));
        }
    }

    public void e() {
        this.c = true;
        this.d.c.addAll(this.l.values());
        for (int i2 = 0; i2 < this.m.length; ++i2) {
            this.d.a(this.m[i2]);
        }
    }

    public void f() {
        this.c = false;
        this.d.c.removeAll(this.l.values());
        for (int i2 = 0; i2 < this.m.length; ++i2) {
            this.d.b(this.m[i2]);
        }
    }

    public void g() {
        this.o = true;
    }

    public void a(lw lw2, co co2, List list) {
        int n2 = TrigLookup.b((co2.b - 2.0) / 16.0);
        int n3 = TrigLookup.b((co2.e + 2.0) / 16.0);
        if (n2 < 0) {
            n2 = 0;
        }
        if (n3 >= this.m.length) {
            n3 = this.m.length - 1;
        }
        for (int i2 = n2; i2 <= n3; ++i2) {
            List list2 = this.m[i2];
            for (int i3 = 0; i3 < list2.size(); ++i3) {
                lw lw3 = (lw)list2.get(i3);
                if (lw3 == lw2 || !lw3.aG.a(co2)) continue;
                list.add(lw3);
            }
        }
    }

    public void a(Class clazz, co co2, List list) {
        int n2 = TrigLookup.b((co2.b - 2.0) / 16.0);
        int n3 = TrigLookup.b((co2.e + 2.0) / 16.0);
        if (n2 < 0) {
            n2 = 0;
        }
        if (n3 >= this.m.length) {
            n3 = this.m.length - 1;
        }
        for (int i2 = n2; i2 <= n3; ++i2) {
            List list2 = this.m[i2];
            for (int i3 = 0; i3 < list2.size(); ++i3) {
                lw lw2 = (lw)list2.get(i3);
                if (!clazz.isAssignableFrom(lw2.getClass()) || !lw2.aG.a(co2)) continue;
                list.add(lw2);
            }
        }
    }

    public boolean a(boolean bl2) {
        if (this.p) {
            return false;
        }
        if (this.r && this.d.e != this.s) {
            return true;
        }
        return this.o;
    }

    public int a(byte[] byArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int n10;
        int n11;
        int n12;
        for (n12 = n2; n12 < n5; ++n12) {
            for (n11 = n4; n11 < n7; ++n11) {
                n10 = n12 << 11 | n11 << 7 | n3;
                n9 = n6 - n3;
                System.arraycopy(byArray, n8, this.b, n10, n9);
                n8 += n9;
            }
        }
        this.b();
        for (n12 = n2; n12 < n5; ++n12) {
            for (n11 = n4; n11 < n7; ++n11) {
                n10 = (n12 << 11 | n11 << 7 | n3) >> 1;
                n9 = (n6 - n3) / 2;
                System.arraycopy(byArray, n8, this.e.a, n10, n9);
                n8 += n9;
            }
        }
        for (n12 = n2; n12 < n5; ++n12) {
            for (n11 = n4; n11 < n7; ++n11) {
                n10 = (n12 << 11 | n11 << 7 | n3) >> 1;
                n9 = (n6 - n3) / 2;
                System.arraycopy(byArray, n8, this.g.a, n10, n9);
                n8 += n9;
            }
        }
        for (n12 = n2; n12 < n5; ++n12) {
            for (n11 = n4; n11 < n7; ++n11) {
                n10 = (n12 << 11 | n11 << 7 | n3) >> 1;
                n9 = (n6 - n3) / 2;
                System.arraycopy(byArray, n8, this.f.a, n10, n9);
                n8 += n9;
            }
        }
        return n8;
    }

    public Random a(long l2) {
        return new Random(this.d.u + (long)(this.j * this.j * 4987142) + (long)(this.j * 5947611) + (long)(this.k * this.k) * 4392871L + (long)(this.k * 389711) ^ l2);
    }
}

