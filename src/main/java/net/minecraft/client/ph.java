/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class ph
extends Thread {
    final jq a;

    ph(jq jq2, String string) {
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
            ++jq.c;
        }
        try {
            while (jq.a(this.a)) {
                jq.d(this.a);
            }
        }
        finally {
            object = jq.a;
            synchronized (object) {
                --jq.c;
            }
        }
    }
}

