/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
public abstract class fr {
    protected gz a;

    public abstract void a(TileEntityRegistry var1, double var2, double var4, double var6, float var8);

    protected void a(String string) {
        fu fu2 = this.a.e;
        fu2.b(fu2.a(string));
    }

    public void a(gz gz2) {
        this.a = gz2;
    }

    public ls a() {
        return this.a.a();
    }
}

