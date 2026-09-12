/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;

class mz
extends Session {
    final ah A;

    mz(ah ah2, File file, String string) {
        super(file, string);
        this.A = ah2;
    }

    @Override
    protected bd a(File file) {
        return new cu(this, new mu(file, false));
    }
}

