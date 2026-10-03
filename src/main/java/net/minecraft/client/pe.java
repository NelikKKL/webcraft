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
        // ИСПРАВЛЕНО (WASM-GC сборка): Thread.sleep(5000L) убран (Fiber недоступен).
        // ПАТЧЕНО для web-порта: Thread.stop() не поддерживается TeaVM
        // ("Method java.lang.Thread.stop()V was not found"), поэтому принудительное
        // завершение сетевых потоков тоже убрано. В веб-порте jq/pf/ph физически
        // недостижимы в рантайме (netshim.Socket всегда бросает ConnectException
        // до их создания, см. PATCHES.md), а сам pe больше не запускается.
    }
}
