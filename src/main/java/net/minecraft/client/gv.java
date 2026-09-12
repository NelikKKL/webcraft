/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class gv
extends Block {
    protected gv(int n2, hb hb2) {
        super(n2, hb2);
        this.bg = 97;
        if (hb2 == hb.e) {
            ++this.bg;
        }
        float f2 = 0.5f;
        float f3 = 1.0f;
        this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, f3, 0.5f + f2);
    }

    @Override
    public int a(int n2, int n3) {
        if (n2 == 0 || n2 == 1) {
            return this.bg;
        }
        int n4 = this.c(n3);
        if ((n4 == 0 || n4 == 2) ^ n2 <= 3) {
            return this.bg;
        }
        int n5 = n4 / 2 + (n2 & 1 ^ n4);
        int n6 = this.bg - (n3 & 8) * 2;
        if (((n5 += (n3 & 4) / 4) & 1) != 0) {
            n6 = -n6;
        }
        return n6;
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
        return 7;
    }

    @Override
    public co f(Session cy2, int n2, int n3, int n4) {
        this.a((pk)cy2, n2, n3, n4);
        return super.f(cy2, n2, n3, n4);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        this.a((pk)cy2, n2, n3, n4);
        return super.d(cy2, n2, n3, n4);
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        this.b(this.c(pk2.e(n2, n3, n4)));
    }

    public void b(int n2) {
        float f2 = 0.1875f;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        if (n2 == 0) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f2);
        }
        if (n2 == 1) {
            this.a(1.0f - f2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (n2 == 2) {
            this.a(0.0f, 0.0f, 1.0f - f2, 1.0f, 1.0f, 1.0f);
        }
        if (n2 == 3) {
            this.a(0.0f, 0.0f, 0.0f, f2, 1.0f, 1.0f);
        }
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Player eb2) {
        this.a(cy2, n2, n3, n4, eb2);
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4, Player eb2) {
        if (this.bs == hb.e) {
            return true;
        }
        int n5 = cy2.e(n2, n3, n4);
        if ((n5 & 8) != 0) {
            if (cy2.a(n2, n3 - 1, n4) == this.bh) {
                this.a(cy2, n2, n3 - 1, n4, eb2);
            }
            return true;
        }
        if (cy2.a(n2, n3 + 1, n4) == this.bh) {
            cy2.b(n2, n3 + 1, n4, (n5 ^ 4) + 8);
        }
        cy2.b(n2, n3, n4, n5 ^ 4);
        cy2.b(n2, n3 - 1, n4, n2, n3, n4);
        if (Math.random() < 0.5) {
            cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "random.door_open", 1.0f, cy2.l.nextFloat() * 0.1f + 0.9f);
        } else {
            cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "random.door_close", 1.0f, cy2.l.nextFloat() * 0.1f + 0.9f);
        }
        return true;
    }

    public void a(Session cy2, int n2, int n3, int n4, boolean bl2) {
        boolean bl3;
        int n5 = cy2.e(n2, n3, n4);
        if ((n5 & 8) != 0) {
            if (cy2.a(n2, n3 - 1, n4) == this.bh) {
                this.a(cy2, n2, n3 - 1, n4, bl2);
            }
            return;
        }
        boolean bl4 = bl3 = (cy2.e(n2, n3, n4) & 4) > 0;
        if (bl3 == bl2) {
            return;
        }
        if (cy2.a(n2, n3 + 1, n4) == this.bh) {
            cy2.b(n2, n3 + 1, n4, (n5 ^ 4) + 8);
        }
        cy2.b(n2, n3, n4, n5 ^ 4);
        cy2.b(n2, n3 - 1, n4, n2, n3, n4);
        if (Math.random() < 0.5) {
            cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "random.door_open", 1.0f, cy2.l.nextFloat() * 0.1f + 0.9f);
        } else {
            cy2.a((double)n2 + 0.5, (double)n3 + 0.5, (double)n4 + 0.5, "random.door_close", 1.0f, cy2.l.nextFloat() * 0.1f + 0.9f);
        }
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.e(n2, n3, n4);
        if ((n6 & 8) != 0) {
            if (cy2.a(n2, n3 - 1, n4) != this.bh) {
                cy2.d(n2, n3, n4, 0);
            }
            if (n5 > 0 && Block.registry[n5].e()) {
                this.a(cy2, n2, n3 - 1, n4, n5);
            }
        } else {
            boolean bl2 = false;
            if (cy2.a(n2, n3 + 1, n4) != this.bh) {
                cy2.d(n2, n3, n4, 0);
                bl2 = true;
            }
            if (!cy2.g(n2, n3 - 1, n4)) {
                cy2.d(n2, n3, n4, 0);
                bl2 = true;
                if (cy2.a(n2, n3 + 1, n4) == this.bh) {
                    cy2.d(n2, n3 + 1, n4, 0);
                }
            }
            if (bl2) {
                this.b_(cy2, n2, n3, n4, n6);
            } else if (n5 > 0 && Block.registry[n5].e()) {
                boolean bl3 = cy2.o(n2, n3, n4) || cy2.o(n2, n3 + 1, n4);
                this.a(cy2, n2, n3, n4, bl3);
            }
        }
    }

    @Override
    public int a(int n2, Random random) {
        if ((n2 & 8) != 0) {
            return 0;
        }
        if (this.bs == hb.e) {
            return Item.az.id;
        }
        return Item.at.id;
    }

    @Override
    public nx a(Session cy2, int n2, int n3, int n4, Vector3D ao2, Vector3D ao3) {
        this.a((pk)cy2, n2, n3, n4);
        return super.a(cy2, n2, n3, n4, ao2, ao3);
    }

    public int c(int n2) {
        if ((n2 & 4) == 0) {
            return n2 - 1 & 3;
        }
        return n2 & 3;
    }

    @Override
    public boolean a(Session cy2, int n2, int n3, int n4) {
        if (n3 >= 127) {
            return false;
        }
        return cy2.g(n2, n3 - 1, n4) && super.a(cy2, n2, n3, n4) && super.a(cy2, n2, n3 + 1, n4);
    }
}

