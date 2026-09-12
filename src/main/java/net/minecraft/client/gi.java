/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import net.minecraft.client.Minecraft;

public class gi
extends Thread {
    final Minecraft a;

    public gi(Minecraft minecraft, String string) {
        super(string);
        this.a = minecraft;
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        while (this.a.H) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            }
            catch (InterruptedException interruptedException) {}
        }
    }
}

