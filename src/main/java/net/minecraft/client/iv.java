/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

public class iv {
    protected final Minecraft a;
    public boolean b = false;

    public iv(Minecraft minecraft) {
        this.a = minecraft;
    }

    public void a(Session cy2) {
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.b(n2, n3, n4, n5);
    }

    public boolean b(int n2, int n3, int n4, int n5) {
        this.a.h.a(n2, n3, n4);
        Session cy2 = this.a.e;
        Block nq2 = Block.registry[cy2.a(n2, n3, n4)];
        int n6 = cy2.e(n2, n3, n4);
        boolean bl2 = cy2.d(n2, n3, n4, 0);
        if (nq2 != null && bl2) {
            this.a.A.b(nq2.bq.a(), (float)n2 + 0.5f, (float)n3 + 0.5f, (float)n4 + 0.5f, (nq2.bq.b() + 1.0f) / 2.0f, nq2.bq.c() * 0.8f);
            nq2.b(cy2, n2, n3, n4, n6);
        }
        return bl2;
    }

    public void c(int n2, int n3, int n4, int n5) {
    }

    public void a() {
    }

    public void a(float f2) {
    }

    public float b() {
        return 5.0f;
    }

    public boolean a(Player eb2, Session cy2, InventoryItem fp2) {
        int n2 = fp2.count;
        InventoryItem fp3 = fp2.a(cy2, eb2);
        if (fp3 != fp2 || fp3 != null && fp3.count != n2) {
            eb2.e.storage[eb2.e.d] = fp3;
            if (fp3.count == 0) {
                eb2.e.storage[eb2.e.d] = null;
            }
            return true;
        }
        return false;
    }

    public void a(Player eb2) {
    }

    public void c() {
    }

    public boolean d() {
        return true;
    }

    public void b(Player eb2) {
    }

    public boolean a(Player eb2, Session cy2, InventoryItem fp2, int n2, int n3, int n4, int n5) {
        int n6 = cy2.a(n2, n3, n4);
        if (n6 > 0 && Block.registry[n6].a(cy2, n2, n3, n4, eb2)) {
            return true;
        }
        if (fp2 == null) {
            return false;
        }
        return fp2.a(eb2, cy2, n2, n3, n4, n5);
    }

    public Player b(Session cy2) {
        return new bq(this.a, cy2, this.a.i, cy2.q.g);
    }

    public void a(Player eb2, lw lw2) {
        eb2.a_(lw2);
    }

    public void b(Player eb2, lw lw2) {
        eb2.c(lw2);
    }
}

