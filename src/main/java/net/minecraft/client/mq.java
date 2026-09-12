/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.awtshim.ImageIO;

class mq
extends Thread {
    final String a;
    final ie b;
    final cl c;

    mq(cl cl2, String string, ie ie2) {
        this.c = cl2;
        this.a = string;
        this.b = ie2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        HttpURLConnection httpURLConnection = null;
        try {
            URL uRL = new URL(this.a);
            httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 404) {
                return;
            }
            this.c.a = this.b == null ? ImageIO.read(httpURLConnection.getInputStream()) : this.b.a(ImageIO.read(httpURLConnection.getInputStream()));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();
        }
    }
}

