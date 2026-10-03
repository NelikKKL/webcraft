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
            // ПАТЧЕНО для web-порта: Thread.stop() не поддерживается TeaVM
            // ("Method java.lang.Thread.stop()V was not found") — давно
            // deprecated и небезопасный метод (принудительное убийство
            // потока), в реальном JDK тоже не рекомендуется. Убран —
            // это best-effort cleanup сетевых потоков после 5-секундного
            // тайм-аута, уже был обёрнут в try/catch(Throwable), то есть
            // отсутствие эффекта здесь уже допустимое поведение по
            // исходному замыслу кода. К тому же в веб-порте jq/pf/ph
            // (сетевые read/write потоки) физически недостижимы в рантайме,
            // т.к. наш Socket (netshim) всегда бросает ConnectException до
            // того, как jq вообще успевает их создать (см. PATCHES.md).
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

