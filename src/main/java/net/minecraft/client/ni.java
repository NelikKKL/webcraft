/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.Random;

public class ni
extends lh {
    private Class a;
    private boolean b;

    protected ni(int n2, Class clazz, boolean bl2) {
        super(n2, hb.c);
        this.b = bl2;
        this.bg = 4;
        this.a = clazz;
        float f2 = 0.25f;
        float f3 = 1.0f;
        this.a(0.099999994f, 0.0f, 0.099999994f, 0.9f, f3, 0.9f);
    }

    @Override
    public co d(Session cy2, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public co f(Session cy2, int n2, int n3, int n4) {
        this.a((pk)cy2, n2, n3, n4);
        return super.f(cy2, n2, n3, n4);
    }

    @Override
    public void a(pk pk2, int n2, int n3, int n4) {
        if (this.b) {
            return;
        }
        int n5 = pk2.e(n2, n3, n4);
        float f2 = 0.28125f;
        float f3 = 0.78125f;
        float f4 = 0.0f;
        float f5 = 1.0f;
        float f6 = 0.125f;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        if (n5 == 2) {
            this.a(f4, f2, 1.0f - f6, f5, f3, 1.0f);
        }
        if (n5 == 3) {
            this.a(f4, f2, 0.0f, f5, f3, f6);
        }
        if (n5 == 4) {
            this.a(1.0f - f6, f2, f4, 1.0f, f3, f5);
        }
        if (n5 == 5) {
            this.a(0.0f, f2, f4, f6, f3, f5);
        }
    }

    @Override
    public int g() {
        return -1;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    protected TileEntityRegistry a_() {
        try {
            return (TileEntityRegistry)this.a.newInstance();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int a(int n2, Random random) {
        return Item.as.id;
    }

    @Override
    public void a(Session cy2, int n2, int n3, int n4, int n5) {
        boolean bl2 = false;
        if (this.b) {
            if (!cy2.f(n2, n3 - 1, n4).a()) {
                bl2 = true;
            }
        } else {
            int n6 = cy2.e(n2, n3, n4);
            bl2 = true;
            if (n6 == 2 && cy2.f(n2, n3, n4 + 1).a()) {
                bl2 = false;
            }
            if (n6 == 3 && cy2.f(n2, n3, n4 - 1).a()) {
                bl2 = false;
            }
            if (n6 == 4 && cy2.f(n2 + 1, n3, n4).a()) {
                bl2 = false;
            }
            if (n6 == 5 && cy2.f(n2 - 1, n3, n4).a()) {
                bl2 = false;
            }
        }
        if (bl2) {
            this.b_(cy2, n2, n3, n4, cy2.e(n2, n3, n4));
            cy2.d(n2, n3, n4, 0);
        }
        super.a(cy2, n2, n3, n4, n5);
    }
}

