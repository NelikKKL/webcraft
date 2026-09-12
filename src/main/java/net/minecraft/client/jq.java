/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class jq {
    public static final Object a = new Object();
    public static int b;
    public static int c;
    private Object d = new Object();
    private Socket e;
    private final SocketAddress f;
    private DataInputStream g;
    private DataOutputStream h;
    private boolean i = true;
    private List j = Collections.synchronizedList(new ArrayList());
    private List k = Collections.synchronizedList(new ArrayList());
    private List l = Collections.synchronizedList(new ArrayList());
    private mo m;
    private boolean n = false;
    private Thread o;
    private Thread p;
    private boolean q = false;
    private String r = "";
    private int s = 0;
    private int t = 0;
    private int u = 0;

    public jq(Socket socket, String string, mo mo2) throws SocketException, IOException {
        this.e = socket;
        this.f = socket.getRemoteSocketAddress();
        this.m = mo2;
        socket.setTrafficClass(24);
        this.g = new DataInputStream(socket.getInputStream());
        this.h = new DataOutputStream(socket.getOutputStream());
        this.p = new pf(this, string + " read thread");
        this.o = new ph(this, string + " write thread");
        this.p.start();
        this.o.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(gk gk2) {
        if (this.n) {
            return;
        }
        Object object = this.d;
        synchronized (object) {
            this.t += gk2.a() + 1;
            if (gk2.j) {
                this.l.add(gk2);
            } else {
                this.k.add(gk2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b() {
        block12: {
            try {
                gk gk2;
                Object object;
                boolean bl2 = true;
                if (!this.k.isEmpty()) {
                    bl2 = false;
                    object = this.d;
                    synchronized (object) {
                        gk2 = (gk)this.k.remove(0);
                        this.t -= gk2.a() + 1;
                    }
                    gk.a(gk2, this.h);
                }
                if ((bl2 || this.u-- <= 0) && !this.l.isEmpty()) {
                    bl2 = false;
                    object = this.d;
                    synchronized (object) {
                        gk2 = (gk)this.l.remove(0);
                        this.t -= gk2.a() + 1;
                    }
                    gk.a(gk2, this.h);
                    this.u = 50;
                }
                if (bl2) {
                    Thread.sleep(10L);
                }
            }
            catch (InterruptedException interruptedException) {
            }
            catch (Exception exception) {
                if (this.q) break block12;
                this.a(exception);
            }
        }
    }

    private void c() {
        block4: {
            try {
                gk gk2 = gk.b(this.g);
                if (gk2 != null) {
                    this.j.add(gk2);
                } else {
                    this.a("End of stream");
                }
            }
            catch (Exception exception) {
                if (this.q) break block4;
                this.a(exception);
            }
        }
    }

    private void a(Exception exception) {
        exception.printStackTrace();
        this.a("Internal exception: " + exception.toString());
    }

    public void a(String string) {
        if (!this.i) {
            return;
        }
        this.q = true;
        this.r = string;
        new pe(this).start();
        this.i = false;
        try {
            this.g.close();
            this.g = null;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            this.h.close();
            this.h = null;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            this.e.close();
            this.e = null;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public void a() {
        if (this.t > 0x100000) {
            this.a("Send buffer overflow");
        }
        if (this.j.isEmpty()) {
            if (this.s++ == 1200) {
                this.a("Timed out");
            }
        } else {
            this.s = 0;
        }
        int n2 = 100;
        while (!this.j.isEmpty() && n2-- >= 0) {
            gk gk2 = (gk)this.j.remove(0);
            gk2.a(this.m);
        }
        if (this.q && this.j.isEmpty()) {
            this.m.a(this.r);
        }
    }

    static boolean a(jq jq2) {
        return jq2.i;
    }

    static boolean b(jq jq2) {
        return jq2.n;
    }

    static void c(jq jq2) {
        jq2.c();
    }

    static void d(jq jq2) {
        jq2.b();
    }

    static Thread e(jq jq2) {
        return jq2.p;
    }

    static Thread f(jq jq2) {
        return jq2.o;
    }
}

