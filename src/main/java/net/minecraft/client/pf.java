/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class pf
extends Thread {
    final jq a;

    pf(jq jq2, String string) {
        super(string);
        this.a = jq2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object = jq.a;
        synchronized (object) {
            ++jq.b;
        }
        try {
            while (jq.a(this.a) && !jq.b(this.a)) {
                jq.c(this.a);
            }
        }
        finally {
            object = jq.a;
            synchronized (object) {
                --jq.b;
            }
        }
    }
}

