/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ob
extends Block {
    protected ob(int n2, int n3) {
        super(n2, n3, hb.n);
        this.b(true);
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
        return 2;
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
        if (n5 == 1 && cy2.g(n2, n3 - 1, n4)) {
            n6 = 5;
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
        cy2.b(n2, n3, n4, n6);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, Random random) {
        super.a(cy2, n2, n3, n4, random);
        if (cy2.e(n2, n3, n4) == 0) {
            this.e(cy2, n2, n3, n4);
        }
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
            cy2.b(n2, n3, n4, 5);
        }
        this.h(cy2, n2, n3, n4);
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        if (this.h(cy2, n2, n3, n4)) {
            int n6 = cy2.e(n2, n3, n4);
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
    public nx a(Session cy2, int n2, int n3, int n4, Vector3D ao2, Vector3D ao3) {
        int n5 = cy2.e(n2, n3, n4) & 7;
        float f2 = 0.15f;
        if (n5 == 1) {
            this.a(0.0f, 0.2f, 0.5f - f2, f2 * 2.0f, 0.8f, 0.5f + f2);
        } else if (n5 == 2) {
            this.a(1.0f - f2 * 2.0f, 0.2f, 0.5f - f2, 1.0f, 0.8f, 0.5f + f2);
        } else if (n5 == 3) {
            this.a(0.5f - f2, 0.2f, 0.0f, 0.5f + f2, 0.8f, f2 * 2.0f);
        } else if (n5 == 4) {
            this.a(0.5f - f2, 0.2f, 1.0f - f2 * 2.0f, 0.5f + f2, 0.8f, 1.0f);
        } else {
            f2 = 0.1f;
            this.a(0.5f - f2, 0.0f, 0.5f - f2, 0.5f + f2, 0.6f, 0.5f + f2);
        }
        return super.a(cy2, n2, n3, n4, ao2, ao3);
    }

    @Override
    public void b(Session cy2, int n2, int n3, int n4, Random random) {
        int n5 = cy2.e(n2, n3, n4);
        double d2 = (float)n2 + 0.5f;
        double d3 = (float)n3 + 0.7f;
        double d4 = (float)n4 + 0.5f;
        double d5 = 0.22f;
        double d6 = 0.27f;
        if (n5 == 1) {
            cy2.a("smoke", d2 - d6, d3 + d5, d4, 0.0, 0.0, 0.0);
            cy2.a("flame", d2 - d6, d3 + d5, d4, 0.0, 0.0, 0.0);
        } else if (n5 == 2) {
            cy2.a("smoke", d2 + d6, d3 + d5, d4, 0.0, 0.0, 0.0);
            cy2.a("flame", d2 + d6, d3 + d5, d4, 0.0, 0.0, 0.0);
        } else if (n5 == 3) {
            cy2.a("smoke", d2, d3 + d5, d4 - d6, 0.0, 0.0, 0.0);
            cy2.a("flame", d2, d3 + d5, d4 - d6, 0.0, 0.0, 0.0);
        } else if (n5 == 4) {
            cy2.a("smoke", d2, d3 + d5, d4 + d6, 0.0, 0.0, 0.0);
            cy2.a("flame", d2, d3 + d5, d4 + d6, 0.0, 0.0, 0.0);
        } else {
            cy2.a("smoke", d2, d3, d4, 0.0, 0.0, 0.0);
            cy2.a("flame", d2, d3, d4, 0.0, 0.0, 0.0);
        }
    }
}

