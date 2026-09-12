/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nr
implements bd {
    private ha a;
    private Map b = new HashMap();
    private List c = new ArrayList();
    private Session d;

    public nr(Session cy2) {
        this.a = new ha(cy2, new byte[32768], 0, 0);
        this.a.q = true;
        this.a.p = true;
        this.d = cy2;
    }

    @Override
    public boolean a(int n2, int n3) {
        _if if_ = new _if(n2, n3);
        return this.b.containsKey(if_);
    }

    public void c(int n2, int n3) {
        ha ha2 = this.b(n2, n3);
        if (!ha2.q) {
            ha2.f();
        }
        this.b.remove(new _if(n2, n3));
        this.c.remove(ha2);
    }

    public ha d(int n2, int n3) {
        _if if_ = new _if(n2, n3);
        byte[] byArray = new byte[32768];
        ha ha2 = new ha(this.d, byArray, n2, n3);
        Arrays.fill(ha2.f.a, (byte)-1);
        this.b.put(if_, ha2);
        ha2.c = true;
        return ha2;
    }

    @Override
    public ha b(int n2, int n3) {
        _if if_ = new _if(n2, n3);
        ha ha2 = (ha)this.b.get(if_);
        if (ha2 == null) {
            return this.a;
        }
        return ha2;
    }

    @Override
    public boolean a(boolean bl2, pu pu2) {
        return true;
    }

    @Override
    public boolean a() {
        return false;
    }

    @Override
    public boolean b() {
        return false;
    }

    @Override
    public void a(bd bd2, int n2, int n3) {
    }
}

