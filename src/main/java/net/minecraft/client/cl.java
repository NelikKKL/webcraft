/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.BufferedImage;

public class cl {
    public BufferedImage a;
    public int b = 1;
    public int c = -1;
    public boolean d = false;

    public cl(String string, ie ie2) {
        // ИСПРАВЛЕНО (WASM-GC сборка): mq.start() запускал поток загрузки скина по
        // HTTP. Thread.start() не поддерживается на WEBASSEMBLY_GC-таргете TeaVM, а
        // в веб-порте эта загрузка и так не работала (CORS на minecraft.net,
        // ImageIO.read поддерживает только ресурсы из ResourceIO). Результат тот же,
        // что и при 404: this.a остаётся null и используется стандартный скин.
    }
}

