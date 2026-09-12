/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.List;

class oc {
    private Session b;
    private int c;
    private int d;
    private int e;
    private int f;
    private List g = new ArrayList();
    final jn a;

    public oc(jn jn2, Session cy2, int n2, int n3, int n4) {
        this.a = jn2;
        this.b = cy2;
        this.c = n2;
        this.d = n3;
        this.e = n4;
        this.f = cy2.e(n2, n3, n4);
        this.a();
    }

    private void a() {
        this.g.clear();
        if (this.f == 0) {
            this.g.add(new on(this.c, this.d, this.e - 1));
            this.g.add(new on(this.c, this.d, this.e + 1));
        } else if (this.f == 1) {
            this.g.add(new on(this.c - 1, this.d, this.e));
            this.g.add(new on(this.c + 1, this.d, this.e));
        } else if (this.f == 2) {
            this.g.add(new on(this.c - 1, this.d, this.e));
            this.g.add(new on(this.c + 1, this.d + 1, this.e));
        } else if (this.f == 3) {
            this.g.add(new on(this.c - 1, this.d + 1, this.e));
            this.g.add(new on(this.c + 1, this.d, this.e));
        } else if (this.f == 4) {
            this.g.add(new on(this.c, this.d + 1, this.e - 1));
            this.g.add(new on(this.c, this.d, this.e + 1));
        } else if (this.f == 5) {
            this.g.add(new on(this.c, this.d, this.e - 1));
            this.g.add(new on(this.c, this.d + 1, this.e + 1));
        } else if (this.f == 6) {
            this.g.add(new on(this.c + 1, this.d, this.e));
            this.g.add(new on(this.c, this.d, this.e + 1));
        } else if (this.f == 7) {
            this.g.add(new on(this.c - 1, this.d, this.e));
            this.g.add(new on(this.c, this.d, this.e + 1));
        } else if (this.f == 8) {
            this.g.add(new on(this.c - 1, this.d, this.e));
            this.g.add(new on(this.c, this.d, this.e - 1));
        } else if (this.f == 9) {
            this.g.add(new on(this.c + 1, this.d, this.e));
            this.g.add(new on(this.c, this.d, this.e - 1));
        }
    }

    private void b() {
        for (int i2 = 0; i2 < this.g.size(); ++i2) {
            oc oc2 = this.a((on)this.g.get(i2));
            if (oc2 == null || !oc2.b(this)) {
                this.g.remove(i2--);
                continue;
            }
            this.g.set(i2, new on(oc2.c, oc2.d, oc2.e));
        }
    }

    private boolean a(int n2, int n3, int n4) {
        if (this.b.a(n2, n3, n4) == this.a.bh) {
            return true;
        }
        if (this.b.a(n2, n3 + 1, n4) == this.a.bh) {
            return true;
        }
        return this.b.a(n2, n3 - 1, n4) == this.a.bh;
    }

    private oc a(on on2) {
        if (this.b.a(on2.a, on2.b, on2.c) == this.a.bh) {
            return new oc(this.a, this.b, on2.a, on2.b, on2.c);
        }
        if (this.b.a(on2.a, on2.b + 1, on2.c) == this.a.bh) {
            return new oc(this.a, this.b, on2.a, on2.b + 1, on2.c);
        }
        if (this.b.a(on2.a, on2.b - 1, on2.c) == this.a.bh) {
            return new oc(this.a, this.b, on2.a, on2.b - 1, on2.c);
        }
        return null;
    }

    private boolean b(oc oc2) {
        for (int i2 = 0; i2 < this.g.size(); ++i2) {
            on on2 = (on)this.g.get(i2);
            if (on2.a != oc2.c || on2.c != oc2.e) continue;
            return true;
        }
        return false;
    }

    private boolean b(int n2, int n3, int n4) {
        for (int i2 = 0; i2 < this.g.size(); ++i2) {
            on on2 = (on)this.g.get(i2);
            if (on2.a != n2 || on2.c != n4) continue;
            return true;
        }
        return false;
    }

    private int c() {
        int n2 = 0;
        if (this.a(this.c, this.d, this.e - 1)) {
            ++n2;
        }
        if (this.a(this.c, this.d, this.e + 1)) {
            ++n2;
        }
        if (this.a(this.c - 1, this.d, this.e)) {
            ++n2;
        }
        if (this.a(this.c + 1, this.d, this.e)) {
            ++n2;
        }
        return n2;
    }

    private boolean c(oc oc2) {
        if (this.b(oc2)) {
            return true;
        }
        if (this.g.size() == 2) {
            return false;
        }
        if (this.g.size() == 0) {
            return true;
        }
        on on2 = (on)this.g.get(0);
        if (oc2.d == this.d && on2.b == this.d) {
            return true;
        }
        return true;
    }

    private void d(oc oc2) {
        this.g.add(new on(oc2.c, oc2.d, oc2.e));
        boolean bl2 = this.b(this.c, this.d, this.e - 1);
        boolean bl3 = this.b(this.c, this.d, this.e + 1);
        boolean bl4 = this.b(this.c - 1, this.d, this.e);
        boolean bl5 = this.b(this.c + 1, this.d, this.e);
        int n2 = -1;
        if (bl2 || bl3) {
            n2 = 0;
        }
        if (bl4 || bl5) {
            n2 = 1;
        }
        if (bl3 && bl5 && !bl2 && !bl4) {
            n2 = 6;
        }
        if (bl3 && bl4 && !bl2 && !bl5) {
            n2 = 7;
        }
        if (bl2 && bl4 && !bl3 && !bl5) {
            n2 = 8;
        }
        if (bl2 && bl5 && !bl3 && !bl4) {
            n2 = 9;
        }
        if (n2 == 0) {
            if (this.b.a(this.c, this.d + 1, this.e - 1) == this.a.bh) {
                n2 = 4;
            }
            if (this.b.a(this.c, this.d + 1, this.e + 1) == this.a.bh) {
                n2 = 5;
            }
        }
        if (n2 == 1) {
            if (this.b.a(this.c + 1, this.d + 1, this.e) == this.a.bh) {
                n2 = 2;
            }
            if (this.b.a(this.c - 1, this.d + 1, this.e) == this.a.bh) {
                n2 = 3;
            }
        }
        if (n2 < 0) {
            n2 = 0;
        }
        this.b.b(this.c, this.d, this.e, n2);
    }

    private boolean c(int n2, int n3, int n4) {
        oc oc2 = this.a(new on(n2, n3, n4));
        if (oc2 == null) {
            return false;
        }
        oc2.b();
        return oc2.c(this);
    }

    public void a(boolean bl2) {
        boolean bl3 = this.c(this.c, this.d, this.e - 1);
        boolean bl4 = this.c(this.c, this.d, this.e + 1);
        boolean bl5 = this.c(this.c - 1, this.d, this.e);
        boolean bl6 = this.c(this.c + 1, this.d, this.e);
        int n2 = -1;
        if ((bl3 || bl4) && !bl5 && !bl6) {
            n2 = 0;
        }
        if ((bl5 || bl6) && !bl3 && !bl4) {
            n2 = 1;
        }
        if (bl4 && bl6 && !bl3 && !bl5) {
            n2 = 6;
        }
        if (bl4 && bl5 && !bl3 && !bl6) {
            n2 = 7;
        }
        if (bl3 && bl5 && !bl4 && !bl6) {
            n2 = 8;
        }
        if (bl3 && bl6 && !bl4 && !bl5) {
            n2 = 9;
        }
        if (n2 == -1) {
            if (bl3 || bl4) {
                n2 = 0;
            }
            if (bl5 || bl6) {
                n2 = 1;
            }
            if (bl2) {
                if (bl4 && bl6) {
                    n2 = 6;
                }
                if (bl5 && bl4) {
                    n2 = 7;
                }
                if (bl6 && bl3) {
                    n2 = 9;
                }
                if (bl3 && bl5) {
                    n2 = 8;
                }
            } else {
                if (bl3 && bl5) {
                    n2 = 8;
                }
                if (bl6 && bl3) {
                    n2 = 9;
                }
                if (bl5 && bl4) {
                    n2 = 7;
                }
                if (bl4 && bl6) {
                    n2 = 6;
                }
            }
        }
        if (n2 == 0) {
            if (this.b.a(this.c, this.d + 1, this.e - 1) == this.a.bh) {
                n2 = 4;
            }
            if (this.b.a(this.c, this.d + 1, this.e + 1) == this.a.bh) {
                n2 = 5;
            }
        }
        if (n2 == 1) {
            if (this.b.a(this.c + 1, this.d + 1, this.e) == this.a.bh) {
                n2 = 2;
            }
            if (this.b.a(this.c - 1, this.d + 1, this.e) == this.a.bh) {
                n2 = 3;
            }
        }
        if (n2 < 0) {
            n2 = 0;
        }
        this.f = n2;
        this.a();
        this.b.b(this.c, this.d, this.e, n2);
        for (int i2 = 0; i2 < this.g.size(); ++i2) {
            oc oc2 = this.a((on)this.g.get(i2));
            if (oc2 == null) continue;
            oc2.b();
            if (!oc2.c(this)) continue;
            oc2.d(this);
        }
    }

    static int a(oc oc2) {
        return oc2.c();
    }
}

