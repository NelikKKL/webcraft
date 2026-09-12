/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class ib
extends mo {
    private boolean c = false;
    private jq d;
    public String a;
    private Minecraft e;
    private hv f;
    private boolean g = false;
    Random b = new Random();

    public ib(Minecraft minecraft, String string, int n2) throws UnknownHostException, IOException {
        this.e = minecraft;
        Socket socket = new Socket(InetAddress.getByName(string), n2);
        this.d = new jq(socket, "Client", this);
    }

    public void a() {
        if (this.c) {
            return;
        }
        this.d.a();
    }

    @Override
    public void a(iu iu2) {
        this.e.b = new pg(this.e, this);
        this.f = new hv(this, iu2.d, iu2.e);
        this.f.z = true;
        this.e.a(this.f);
        this.e.a(new du(this));
        this.e.g.an = iu2.a;
        System.out.println("clientEntityId: " + iu2.a);
    }

    @Override
    public void a(id id2) {
        double d2 = (double)id2.b / 32.0;
        double d3 = (double)id2.c / 32.0;
        double d4 = (double)id2.d / 32.0;
        DroppedItem eo2 = new DroppedItem(this.f, d2, d3, d4, new InventoryItem(id2.h, id2.i));
        eo2.az = (double)id2.e / 128.0;
        eo2.aA = (double)id2.f / 128.0;
        eo2.aB = (double)id2.g / 128.0;
        eo2.br = id2.b;
        eo2.bs = id2.c;
        eo2.bt = id2.d;
        this.f.a(id2.a, eo2);
    }

    @Override
    public void a(lz lz2) {
        double d2 = (double)lz2.b / 32.0;
        double d3 = (double)lz2.c / 32.0;
        double d4 = (double)lz2.d / 32.0;
        lw lw2 = null;
        if (lz2.e == 10) {
            lw2 = new Minecart(this.f, d2, d3, d4, 0);
        }
        if (lz2.e == 11) {
            lw2 = new Minecart(this.f, d2, d3, d4, 1);
        }
        if (lz2.e == 12) {
            lw2 = new Minecart(this.f, d2, d3, d4, 2);
        }
        if (lz2.e == 90) {
            lw2 = new hj(this.f, d2, d3, d4);
        }
        if (lz2.e == 60) {
            lw2 = new Arrow(this.f, d2, d3, d4);
        }
        if (lz2.e == 61) {
            lw2 = new Snowball(this.f, d2, d3, d4);
        }
        if (lz2.e == 1) {
            lw2 = new Boat(this.f, d2, d3, d4);
        }
        if (lz2.e == 50) {
            lw2 = new PrimedTNT(this.f, d2, d3, d4);
        }
        if (lw2 != null) {
            lw2.br = lz2.b;
            lw2.bs = lz2.c;
            lw2.bt = lz2.d;
            lw2.aC = 0.0f;
            lw2.aD = 0.0f;
            lw2.an = lz2.a;
            this.f.a(lz2.a, lw2);
        }
    }

    @Override
    public void a(dv dv2) {
        lw lw2 = this.a(dv2.a);
        if (lw2 == null) {
            return;
        }
        lw2.a((double)dv2.b / 8000.0, (double)dv2.c / 8000.0, (double)dv2.d / 8000.0);
    }

    @Override
    public void a(hs hs2) {
        double d2 = (double)hs2.c / 32.0;
        double d3 = (double)hs2.d / 32.0;
        double d4 = (double)hs2.e / 32.0;
        float f2 = (float)(hs2.f * 360) / 256.0f;
        float f3 = (float)(hs2.g * 360) / 256.0f;
        ps ps2 = new ps(this.e.e, hs2.b);
        ps2.br = hs2.c;
        ps2.bs = hs2.d;
        ps2.bt = hs2.e;
        int n2 = hs2.h;
        ps2.e.storage[ps2.e.d] = n2 == 0 ? null : new InventoryItem(n2);
        ps2.b(d2, d3, d4, f2, f3);
        this.f.a(hs2.a, ps2);
    }

    @Override
    public void a(ky ky2) {
        lw lw2 = this.a(ky2.a);
        if (lw2 == null) {
            return;
        }
        lw2.br = ky2.b;
        lw2.bs = ky2.c;
        lw2.bt = ky2.d;
        double d2 = (double)lw2.br / 32.0;
        double d3 = (double)lw2.bs / 32.0 + 0.015625;
        double d4 = (double)lw2.bt / 32.0;
        float f2 = (float)(ky2.e * 360) / 256.0f;
        float f3 = (float)(ky2.f * 360) / 256.0f;
        lw2.a(d2, d3, d4, f2, f3, 3);
    }

    @Override
    public void a(nh nh2) {
        lw lw2 = this.a(nh2.a);
        if (lw2 == null) {
            return;
        }
        lw2.br += nh2.b;
        lw2.bs += nh2.c;
        lw2.bt += nh2.d;
        double d2 = (double)lw2.br / 32.0;
        double d3 = (double)lw2.bs / 32.0 + 0.015625;
        double d4 = (double)lw2.bt / 32.0;
        float f2 = nh2.g ? (float)(nh2.e * 360) / 256.0f : lw2.aC;
        float f3 = nh2.g ? (float)(nh2.f * 360) / 256.0f : lw2.aD;
        lw2.a(d2, d3, d4, f2, f3, 3);
    }

    @Override
    public void a(li li2) {
        this.f.c(li2.a);
    }

    @Override
    public void a(fa fa2) {
        bq bq2 = this.e.g;
        double d2 = bq2.aw;
        double d3 = bq2.ax;
        double d4 = bq2.ay;
        float f2 = bq2.aC;
        float f3 = bq2.aD;
        if (fa2.h) {
            d2 = fa2.a;
            d3 = fa2.b;
            d4 = fa2.c;
        }
        if (fa2.i) {
            f2 = fa2.e;
            f3 = fa2.f;
        }
        bq2.aY = 0.0f;
        bq2.aB = 0.0;
        bq2.aA = 0.0;
        bq2.az = 0.0;
        bq2.b(d2, d3, d4, f2, f3);
        fa2.a = bq2.aw;
        fa2.b = bq2.aG.b;
        fa2.c = bq2.ay;
        fa2.d = bq2.ax;
        this.d.a(fa2);
        if (!this.g) {
            this.e.g.at = this.e.g.aw;
            this.e.g.au = this.e.g.ax;
            this.e.g.av = this.e.g.ay;
            this.g = true;
            this.e.a((bp)null);
        }
    }

    @Override
    public void a(lq lq2) {
        this.f.a(lq2.a, lq2.b, lq2.c);
    }

    @Override
    public void a(ov ov2) {
        ha ha2 = this.f.c(ov2.a, ov2.b);
        int n2 = ov2.a * 16;
        int n3 = ov2.b * 16;
        for (int i2 = 0; i2 < ov2.f; ++i2) {
            short s2 = ov2.c[i2];
            int n4 = ov2.d[i2] & 0xFF;
            byte by2 = ov2.e[i2];
            int n5 = s2 >> 12 & 0xF;
            int n6 = s2 >> 8 & 0xF;
            int n7 = s2 & 0xFF;
            ha2.a(n5, n7, n6, n4, (int)by2);
            this.f.c(n5 + n2, n7, n6 + n3, n5 + n2, n7, n6 + n3);
            this.f.b(n5 + n2, n7, n6 + n3, n5 + n2, n7, n6 + n3);
        }
    }

    @Override
    public void a(ci ci2) {
        this.f.c(ci2.a, ci2.b, ci2.c, ci2.a + ci2.d - 1, ci2.b + ci2.e - 1, ci2.c + ci2.f - 1);
        this.f.a(ci2.a, ci2.b, ci2.c, ci2.d, ci2.e, ci2.f, ci2.g);
    }

    @Override
    public void a(mx mx2) {
        this.f.c(mx2.a, mx2.b, mx2.c, mx2.d, mx2.e);
    }

    @Override
    public void a(qi qi2) {
        this.d.a("Got kicked");
        this.c = true;
        this.e.a((Session)null);
        this.e.a(new ct("Disconnected by server", qi2.a));
    }

    @Override
    public void a(String string) {
        if (this.c) {
            return;
        }
        this.c = true;
        this.e.a((Session)null);
        this.e.a(new ct("Connection lost", string));
    }

    public void a(gk gk2) {
        if (this.c) {
            return;
        }
        this.d.a(gk2);
    }

    @Override
    public void a(bu bu2) {
        lw lw2 = this.a(bu2.a);
        Mob hf2 = (Mob)this.a(bu2.b);
        if (hf2 == null) {
            hf2 = this.e.g;
        }
        if (lw2 != null) {
            this.f.a(lw2, "random.pop", 0.2f, ((this.b.nextFloat() - this.b.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            this.e.h.a(new cm(this.e.e, lw2, hf2, -0.5f));
            this.f.c(bu2.a);
        }
    }

    @Override
    public void a(eq eq2) {
        lw lw2 = this.a(eq2.a);
        if (lw2 == null) {
            return;
        }
        Player eb2 = (Player)lw2;
        int n2 = eq2.b;
        eb2.e.storage[eb2.e.d] = n2 == 0 ? null : new InventoryItem(n2);
    }

    @Override
    public void a(jr jr2) {
        this.e.u.a(jr2.a);
    }

    @Override
    public void a(ii ii2) {
        lw lw2 = this.a(ii2.a);
        if (lw2 == null) {
            return;
        }
        if (ii2.b == 1) {
            Player eb2 = (Player)lw2;
            eb2.z();
        } else if (ii2.b == 100) {
            lw2.bu = true;
        } else if (ii2.b == 101) {
            lw2.bu = false;
        } else if (ii2.b == 102) {
            lw2.bv = true;
        } else if (ii2.b == 103) {
            lw2.bv = false;
        } else if (ii2.b == 104) {
            lw2.bw = true;
        } else if (ii2.b == 105) {
            lw2.bw = false;
        } else if (ii2.b == 2) {
            lw2.g();
        }
    }

    @Override
    public void a(mt mt2) {
        this.e.g.e.a(new InventoryItem(mt2.a, mt2.b, mt2.c));
    }

    @Override
    public void a(hw hw2) {
        if (hw2.a.equals("-")) {
            this.a((gk)new iu(this.e.i.b, "Password", 6));
        } else {
            try {
                URL uRL = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + this.e.i.b + "&sessionId=" + this.e.i.c + "&serverId=" + hw2.a);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream()));
                String string = bufferedReader.readLine();
                bufferedReader.close();
                if (string.equalsIgnoreCase("ok")) {
                    this.a((gk)new iu(this.e.i.b, "Password", 6));
                } else {
                    this.d.a("Failed to login: " + string);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
                this.d.a("Internal client error: " + exception.toString());
            }
        }
    }

    public void b() {
        this.c = true;
        this.d.a("Closed");
    }

    @Override
    public void a(fv fv2) {
        double d2 = (double)fv2.c / 32.0;
        double d3 = (double)fv2.d / 32.0;
        double d4 = (double)fv2.e / 32.0;
        float f2 = (float)(fv2.f * 360) / 256.0f;
        float f3 = (float)(fv2.g * 360) / 256.0f;
        Mob hf2 = (Mob)EntityRegistry.a(fv2.b, this.e.e);
        hf2.br = fv2.c;
        hf2.bs = fv2.d;
        hf2.bt = fv2.e;
        hf2.an = fv2.a;
        hf2.b(d2, d3, d4, f2, f3);
        hf2.G = true;
        this.f.a(fv2.a, hf2);
    }

    @Override
    public void a(ek ek2) {
        this.e.e.a(ek2.a);
    }

    @Override
    public void a(p p2) {
        bq bq2 = this.e.g;
        if (p2.a == -1) {
            bq2.e.storage = p2.b;
        }
        if (p2.a == -2) {
            bq2.e.c = p2.b;
        }
        if (p2.a == -3) {
            bq2.e.b = p2.b;
        }
    }

    @Override
    public void a(py py2) {
        if (py2.e.getInt("x") != py2.a) {
            return;
        }
        if (py2.e.getInt("y") != py2.b) {
            return;
        }
        if (py2.e.getInt("z") != py2.c) {
            return;
        }
        TileEntityRegistry ji2 = this.f.b(py2.a, py2.b, py2.c);
        if (ji2 != null) {
            try {
                ji2.a(py2.e);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.f.b(py2.a, py2.b, py2.c, py2.a, py2.b, py2.c);
        }
    }

    @Override
    public void a(kv kv2) {
        this.f.m = kv2.a;
        this.f.n = kv2.b;
        this.f.o = kv2.c;
    }

    @Override
    public void a(io io2) {
        lw lw2 = this.a(io2.a);
        lw lw3 = this.a(io2.b);
        if (io2.a == this.e.g.an) {
            lw2 = this.e.g;
        }
        if (lw2 == null) {
            return;
        }
        lw2.h(lw3);
    }

    @Override
    public void a(fs fs2) {
        lw lw2 = this.a(fs2.a);
        if (lw2 != null) {
            lw2.a(fs2.b);
        }
    }

    private lw a(int n2) {
        if (n2 == this.e.g.an) {
            return this.e.g;
        }
        return this.f.b(n2);
    }

    @Override
    public void a(cq cq2) {
        this.e.g.a_(cq2.a);
    }

    @Override
    public void a(jk jk2) {
        this.e.p();
    }

    @Override
    public void a(lc lc2) {
        ks ks2 = new ks(this.e.e, null, lc2.a, lc2.b, lc2.c, lc2.d);
        ks2.g = lc2.e;
        ks2.b();
    }
}

