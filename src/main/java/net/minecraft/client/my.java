/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class my
extends Thread {
    final ah a;

    my(ah ah2) {
        this.a = ah2;
    }

    @Override
    public void run() {
        while (ah.a(this.a)) {
            this.a.d();
            try {
                Thread.sleep(1L);
            }
            catch (Exception exception) {}
        }
    }
}

