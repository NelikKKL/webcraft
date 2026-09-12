/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
class pe
extends Thread {
    final jq a;

    pe(jq jq2) {
        this.a = jq2;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
            if (jq.e(this.a).isAlive()) {
                try {
                    jq.e(this.a).stop();
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (jq.f(this.a).isAlive()) {
                try {
                    jq.f(this.a).stop();
                }
                catch (Throwable throwable) {}
            }
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

