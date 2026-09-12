/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.net.ConnectException;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;

class oy
extends Thread {
    final Minecraft a;
    final String b;
    final int c;
    final og d;

    oy(og og2, Minecraft minecraft, String string, int n2) {
        this.d = og2;
        this.a = minecraft;
        this.b = string;
        this.c = n2;
    }

    @Override
    public void run() {
        try {
            og.a(this.d, new ib(this.a, this.b, this.c));
            if (og.a(this.d)) {
                return;
            }
            og.b(this.d).a((gk)new hw(this.a.i.b));
        }
        catch (UnknownHostException unknownHostException) {
            if (og.a(this.d)) {
                return;
            }
            this.a.a(new ct("Failed to connect to the server", "Unknown host '" + this.b + "'"));
        }
        catch (ConnectException connectException) {
            if (og.a(this.d)) {
                return;
            }
            this.a.a(new ct("Failed to connect to the server", connectException.getMessage()));
        }
        catch (Exception exception) {
            if (og.a(this.d)) {
                return;
            }
            exception.printStackTrace();
            this.a.a(new ct("Failed to connect to the server", exception.toString()));
        }
    }
}

