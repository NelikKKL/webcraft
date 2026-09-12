/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public class pl
extends Block {
    protected pl(int n2, int n3) {
        super(n2, n3, hb.n);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public int g() {
        return 12;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        if (cy2.g(n2 - 1, n3, n4)) {
            return true;
        }
        if (cy2.g(n2 + 1, n3, n4)) {
            return true;
        }
        if (cy2.g(n2, n3, n4 - 1)) {
            return true;
        }
        if (cy2.g(n2, n3, n4 + 1)) {
            return true;
        }
        return cy2.g(n2, n3 - 1, n4);
    }

    @Override
    public void d(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.e(n2, n3, n4);
        int n7 = n6 & 8;
        n6 &= 7;
        if (n5 == 1 && cy2.g(n2, n3 - 1, n4)) {
            n6 = 5 + cy2.l.nextInt(2);
        }
        if (n5 == 2 && cy2.g(n2, n3, n4 + 1)) {
            n6 = 4;
        }
        if (n5 == 3 && cy2.g(n2, n3, n4 - 1)) {
            n6 = 3;
        }
        if (n5 == 4 && cy2.g(n2 + 1, n3, n4)) {
            n6 = 2;
        }
        if (n5 == 5 && cy2.g(n2 - 1, n3, n4)) {
            n6 = 1;
        }
        cy2.b(n2, n3, n4, n6 + n7);
    }

    @Override
    public void e(Session cy2, int n2, int n3, int n4) {
        if (cy2.g(n2 - 1, n3, n4)) {
            cy2.b(n2, n3, n4, 1);
        } else if (cy2.g(n2 + 1, n3, n4)) {
            cy2.b(n2, n3, n4, 2);
        } else if (cy2.g(n2, n3, n4 - 1)) {
            cy2.b(n2, n3, n4, 3);
        } else if (cy2.g(n2, n3, n4 + 1)) {
            cy2.b(n2, n3, n4, 4);
        } else if (cy2.g(n2, n3 - 1, n4)) {
            cy2.b(n2, n3, n4, 5 + cy2.l.nextInt(2));
        }
        this.h(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (this.h(cy2, n2, n3, n4)) {
            int n6 = cy2.e(n2, n3, n4) & 7;
            boolean bl2 = false;
            if (!cy2.g(n2 - 1, n3, n4) && n6 == 1) {
                bl2 = true;
            }
            if (!cy2.g(n2 + 1, n3, n4) && n6 == 2) {
                bl2 = true;
            }
            if (!cy2.g(n2, n3, n4 - 1) && n6 == 3) {
                bl2 = true;
            }
            if (!cy2.g(n2, n3, n4 + 1) && n6 == 4) {
                bl2 = true;
            }
            if (!cy2.g(n2, n3 - 1, n4) && n6 == 5) {
                bl2 = true;
            }
            if (bl2) {
                this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
                cy2.d(n2, n3, n4, 0);
            }
        }
    }

    private boolean h(Session cy2, int n2, int n3, int n4) {
        if (!this.a(cy2, n2, n3, n4)) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        int n5 = pk2.e(n2, n3, n4) & 7;
        float f2 = 0.1875f;
        if (n5 == 1) {
            this.a(0.0f, 0.2f, 0.5f - f2, f2 * 2.0f, 0.8f, 0.5f + f2);
        } else if (n5 == 2) {
            this.a(1.0f - f2 * 2.0f, 0.2f, 0.5f - f2, 1.0f, 0.8f, 0.5f + f2);
        } else if (n5 == 3) {
            this.a(0.5f - f2, 0.2f, 0.0f, 0.5f + f2, 0.8f, f2 * 2.0f);
        } else if (n5 == 4) {
            this.a(0.5f - f2, 0.2f, 1.0f - f2 * 2.0f, 0.5f + f2, 0.8f, 1.0f);
        } else {
            f2 = 0.25f;
            this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, 0.6f, 0.5f + f2);
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Player eb2) {
        this.a(cy2, n2, n3, n4, eb2);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        if (cy2.z) {
            return true;
        }
        int n5 = cy2.e(n2, n3, n4);
        int n6 = n5 & 7;
        int n7 = 8 - (n5 & 8);
        cy2.b(n2, n3, n4, n6 + n7);
        cy2.b(n2, n3, n4, n2, n3, n4);
        cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "random.click", 0.3f, n7 > 0 ? 0.6f : 0.5f);
        cy2.g(n2, n3, n4, this.bh);
        if (n6 == 1) {
            cy2.g(n2 - 1, n3, n4, this.bh);
        } else if (n6 == 2) {
            cy2.g(n2 + 1, n3, n4, this.bh);
        } else if (n6 == 3) {
            cy2.g(n2, n3, n4 - 1, this.bh);
        } else if (n6 == 4) {
            cy2.g(n2, n3, n4 + 1, this.bh);
        } else {
            cy2.g(n2, n3 - 1, n4, this.bh);
        }
        return true;
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4) {
        int n5 = cy2.e(n2, n3, n4);
        if ((n5 & 8) > 0) {
            cy2.g(n2, n3, n4, this.bh);
            int n6 = n5 & 7;
            if (n6 == 1) {
                cy2.g(n2 - 1, n3, n4, this.bh);
            } else if (n6 == 2) {
                cy2.g(n2 + 1, n3, n4, this.bh);
            } else if (n6 == 3) {
                cy2.g(n2, n3, n4 - 1, this.bh);
            } else if (n6 == 4) {
                cy2.g(n2, n3, n4 + 1, this.bh);
            } else {
                cy2.g(n2, n3 - 1, n4, this.bh);
            }
        }
        super.b(cy2, n2, n3, n4);
    }

    @Override
    public boolean c(pk pk2, int n2, int n3, int n4, int n5) {
        return (pk2.e(n2, n3, n4) & 8) > 0;
    }

    @Override
    public boolean c(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.e(n2, n3, n4);
        if ((n6 & 8) == 0) {
            return false;
        }
        int n7 = n6 & 7;
        if (n7 == 5 && n5 == 1) {
            return true;
        }
        if (n7 == 4 && n5 == 2) {
            return true;
        }
        if (n7 == 3 && n5 == 3) {
            return true;
        }
        if (n7 == 2 && n5 == 4) {
            return true;
        }
        return n7 == 1 && n5 == 5;
    }

    @Override
    public boolean e() {
        return true;
    }
}

