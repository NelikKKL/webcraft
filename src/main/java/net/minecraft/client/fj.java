/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import paulscode.sound.codecs.CodecJOrbis;

public class fj
extends CodecJOrbis {
    @Override
    protected InputStream openInputStream() {
        InputStream ret = null;
        try {
            ret = new in(this, this.url, this.urlConnection.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(fj.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}

