/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Controllers
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;

import net.minecraft.client.awtshim.Canvas;
import net.minecraft.client.awtshim.Color;
import net.minecraft.client.awtshim.Component;
import net.minecraft.client.awtshim.Graphics;
import java.io.File;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft
implements Runnable {
    public iv b;
    private boolean a = false;
    public int c;
    public int d;
    private bm O;
    private kc P = new kc(20.0f);
    public Session e;
    public f f;
    public bq g;
    public bz h;
    public ea i = null;
    public String j;
    public Canvas k;
    public boolean l = true;
    public volatile boolean m = false;
    public fu n;
    public ls o;
    public bp p = null;
    public hu q = new hu(this);
    public kb r = new kb(this);
    private ResourcesDownloader Q;
    private int R = 0;
    private int S = 0;
    private int T;
    private int U;
    public String s = null;
    public int t = 0;
    public nl u;
    public boolean v = false;
    public dc w = new dc(0.0f);
    public nx x = null;
    public gq y;
    protected MinecraftApplet z;
    public qg A = new qg();
    public oi B;
    public ff C;
    public File D;
    public static long[] E = new long[512];
    public static long[] F = new long[512];
    public static int G = 0;
    private String V;
    private int W;
    private oe X = new oe();
    private ba Y = new ba();
    static private File Z = null;
    public volatile boolean H = true;
    public String I = "";
    boolean J = false;
    long K = -1L;
    public boolean L = false;
    private int aa = 0;
    public boolean M = false;
    long N = System.currentTimeMillis();
    private int ab = 0;

    public Minecraft(Component component, Canvas canvas, MinecraftApplet minecraftApplet, int n2, int n3, boolean bl2) {
        this.T = n2;
        this.U = n3;
        this.a = bl2;
        this.z = minecraftApplet;
        new gi(this, "Timer hack thread");
        this.k = canvas;
        this.c = n2;
        this.d = n3;
        this.a = bl2;
    }

    public abstract void a(hr var1);

    public void a(String string, int n2) {
        this.V = string;
        this.W = n2;
    }

    public void a() throws LWJGLException {
        if (this.k != null) {
            Graphics graphics = this.k.getGraphics();
            if (graphics != null) {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, this.c, this.d);
                graphics.dispose();
            }
            Display.setParent((Canvas)this.k);
        } else if (this.a) {
            Display.setFullscreen((boolean)true);
            this.c = Display.getDisplayMode().getWidth();
            this.d = Display.getDisplayMode().getHeight();
            if (this.c <= 0) {
                this.c = 1;
            }
            if (this.d <= 0) {
                this.d = 1;
            }
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.c, this.d));
        }
        Display.setTitle((String)"Minecraft Minecraft Alpha v1.2.6");
        try {
            Display.create();
        }
        catch (LWJGLException lWJGLException) {
            lWJGLException.printStackTrace();
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            Display.create();
        }
        mn.a.f = new ku(this);
        this.D = Minecraft.b();
        this.y = new gq(this, this.D);
        this.C = new ff(this, this.D);
        this.n = new fu(this.C, this.y);
        this.o = new ls(this.y, "/font/default.png", this.n);
        this.q();
        Keyboard.create();
        Mouse.create();
        this.B = new oi();
        try {
            Controllers.create();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.c("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        this.c("Startup");
        this.O = new bm();
        this.A.a(this.y);
        this.n.a(this.Y);
        this.n.a(this.X);
        this.n.a(new et());
        this.n.a(new ae(this));
        this.n.a(new gp(this));
        this.n.a(new iz());
        this.n.a(new ez());
        this.n.a(new ln(0));
        this.n.a(new ln(1));
        this.f = new f(this, this.n);
        GL11.glViewport(0, 0, (int)this.c, (int)this.d);
        this.h = new bz(this.e, this.n);
        try {
            this.Q = new ResourcesDownloader(this.D, this);
            this.Q.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.c("Post startup");
        this.u = new nl(this);
        if (this.V != null) {
            this.a(new og(this, this.V, this.W));
        } else {
            this.a(new dj());
        }
    }

    private void q() throws LWJGLException {
        kl kl2 = new kl(this.c, this.d);
        int n2 = kl2.a();
        int n3 = kl2.b();
        GL11.glClear(16640);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)n2, (double)n3, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glViewport(0, 0, (int)this.c, (int)this.d);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        is is2 = is.a;
        GL11.glDisable(2896);
        GL11.glEnable(3553);
        GL11.glDisable(2912);
        GL11.glBindTexture(3553, (int)this.n.a("/title/mojang.png"));
        is2.b();
        is2.b(0xFFFFFF);
        is2.a(0.0, this.d, 0.0, 0.0, 0.0);
        is2.a(this.c, this.d, 0.0, 0.0, 0.0);
        is2.a(this.c, 0.0, 0.0, 0.0, 0.0);
        is2.a(0.0, 0.0, 0.0, 0.0, 0.0);
        is2.a();
        int n4 = 256;
        int n5 = 256;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        is2.b(0xFFFFFF);
        this.a((this.c / 2 - n4) / 2, (this.d / 2 - n5) / 2, 0, 0, n4, n5);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        Display.swapBuffers();
    }

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        float f2 = 0.00390625f;
        float f3 = 0.00390625f;
        is is2 = is.a;
        is2.b();
        is2.a(n2 + 0, n3 + n7, 0.0, (float)(n4 + 0) * f2, (float)(n5 + n7) * f3);
        is2.a(n2 + n6, n3 + n7, 0.0, (float)(n4 + n6) * f2, (float)(n5 + n7) * f3);
        is2.a(n2 + n6, n3 + 0, 0.0, (float)(n4 + n6) * f2, (float)(n5 + 0) * f3);
        is2.a(n2 + 0, n3 + 0, 0.0, (float)(n4 + 0) * f2, (float)(n5 + 0) * f3);
        is2.a();
    }

    public static File b() {
        if (Z == null) {
            Z = Minecraft.a("minecraft");
        }
        return Z;
    }

    public static File a(String string) {
        File file;
        String string2 = System.getProperty("user.home", ".");
        switch (Minecraft.r()) {
            case linux: 
            case solaris: {
                file = new File(string2, '.' + string + '/');
                break;
            }
            case windows: {
                String string3 = System.getenv("APPDATA");
                if (string3 != null) {
                    file = new File(string3, "." + string + '/');
                    break;
                }
                file = new File(string2, '.' + string + '/');
                break;
            }
            case macos: {
                file = new File(string2, "Library/Application Support/" + string);
                break;
            }
            default: {
                file = new File(string2, string + '/');
            }
        }
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + file);
        }
        return file;
    }

    private static jp r() {
        String string = System.getProperty("os.name").toLowerCase();
        if (string.contains("win")) {
            return jp.windows;
        }
        if (string.contains("mac")) {
            return jp.macos;
        }
        if (string.contains("solaris")) {
            return jp.solaris;
        }
        if (string.contains("sunos")) {
            return jp.solaris;
        }
        if (string.contains("linux")) {
            return jp.linux;
        }
        if (string.contains("unix")) {
            return jp.linux;
        }
        return jp.unknown;
    }

    public void a(bp bp2) {
        if (this.p instanceof ay) {
            return;
        }
        if (this.p != null) {
            this.p.h();
        }
        if (bp2 == null && this.e == null) {
            bp2 = new dj();
        } else if (bp2 == null && this.g.J <= 0) {
            bp2 = new GameOverScreen();
        }
        this.p = bp2;
        if (bp2 != null) {
            this.f();
            kl kl2 = new kl(this.c, this.d);
            int n2 = kl2.a();
            int n3 = kl2.b();
            bp2.a(this, n2, n3);
            this.v = false;
        } else {
            this.e();
        }
    }

    private void c(String string) {
        int n2 = GL11.glGetError();
        if (n2 != 0) {
            String string2 = GLU.gluErrorString((int)n2);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + string);
            System.out.println(n2 + ": " + string2);
            System.exit(0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c() {
        if (this.z != null) {
            this.z.c();
        }
        try {
            if (this.Q != null) {
                this.Q.b();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            System.out.println("Stopping!");
            this.a((Session)null);
            try {
                ds.a();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.A.b();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally {
            Display.destroy();
        }
        System.gc();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    // ПАТЧЕНО для web-порта: l2/n2 из оригинального run() стали полями
    // класса — должны сохраняться МЕЖДУ вызовами runOneFrame() (см. ниже),
    // а не жить только в рамках одной итерации while, как в оригинале.
    private long fpsTimerMs = -1L;
    private int fpsFrameCount = 0;

    /** Возвращает true, пока игра должна продолжать получать кадры (см. WebEntryPoint — проверяется перед каждым runOneFrame()). */
    public boolean isRunning() {
        return this.H && (this.z == null || this.z.isActive());
    }

    /** Разовая инициализация — соответствует первому try-блоку оригинального run(). Вызывать один раз перед первым runOneFrame(). */
    public void init() throws Exception {
        this.H = true;
        this.a();
    }

    /**
     * ПАТЧЕНО для web-порта (см. PATCHES.md): оригинальный run() содержал
     * блокирующий `while` с Thread.sleep/Thread.yield — несовместимо с
     * однопоточной событийной моделью браузера. Разбит на init() (выше,
     * вызывается один раз) + runOneFrame() (вызывается WebEntryPoint на
     * каждый кадр через requestAnimationFrame). Логика одной итерации
     * сохранена как в оригинале, кроме:
     *  - Thread.sleep(5L)/Thread.sleep(10L) — убраны (искусственный
     *    троттлинг/энергосбережение при потере фокуса — не критично,
     *    безопасно пропустить, вместо того чтобы полагаться на
     *    непроверенное поведение Thread.sleep в TeaVM JS-таргете).
     *  - Thread.yield() — убран (не имеет смысла в однопоточной модели).
     */
    public void runOneFrame() {
        if (!isRunning()) {
            return;
        }
        if (fpsTimerMs < 0) {
            fpsTimerMs = System.currentTimeMillis();
        }
        try {
            co.a();
            Vector3D.a();
            if (this.k == null && Display.isCloseRequested()) {
                this.d();
            }
            if (this.m && this.e != null) {
                float f2 = this.P.c;
                this.P.a();
                this.P.c = f2;
            } else {
                this.P.a();
            }
            long l3 = System.nanoTime();
            for (int i2 = 0; i2 < this.P.b; ++i2) {
                ++this.R;
                try {
                    this.i();
                    continue;
                }
                catch (np np2) {
                    this.e = null;
                    this.a((Session)null);
                    this.a(new kh());
                }
            }
            long l4 = System.nanoTime() - l3;
            this.c("Pre render");
            this.A.a(this.g, this.P.c);
            GL11.glEnable(3553);
            if (this.e != null && !this.e.z) {
                while (this.e.g()) {
                }
            }
            if (this.e != null && this.e.z) {
                this.e.g();
            }
            if (!Keyboard.isKeyDown(65)) {
                Display.update();
            }
            if (!this.v) {
                if (this.b != null) {
                    this.b.a(this.P.c);
                }
                this.r.b(this.P.c);
            }
            if (!Display.isActive()) {
                if (this.a) {
                    this.h();
                }
            }
            if (Keyboard.isKeyDown(61)) {
                this.a(l4);
            } else {
                this.K = System.nanoTime();
            }
            if (Keyboard.isKeyDown(65)) {
                Display.update();
            }
            this.s();
            if (!(this.k == null || this.a || this.k.getWidth() == this.c && this.k.getHeight() == this.d)) {
                this.c = this.k.getWidth();
                this.d = this.k.getHeight();
                if (this.c <= 0) {
                    this.c = 1;
                }
                if (this.d <= 0) {
                    this.d = 1;
                }
                this.a(this.c, this.d);
            }
            this.c("Post render");
            ++fpsFrameCount;
            boolean bl2 = this.m = !this.j() && this.p != null && this.p.b();
            while (System.currentTimeMillis() >= fpsTimerMs + 1000L) {
                this.I = fpsFrameCount + " fps, " + bw.b + " chunk updates";
                bw.b = 0;
                fpsTimerMs += 1000L;
                fpsFrameCount = 0;
            }
        }
        catch (pq pq2) {
            this.H = false;
        }
        catch (Throwable throwable) {
            this.e = null;
            throwable.printStackTrace();
            this.a(new hr("Unexpected error", throwable));
            this.H = false;
        }
    }

    /** Оставлен ради `implements Runnable` — реальный игровой цикл управляется извне через init()+runOneFrame() (см. WebEntryPoint/WebMinecraft). */
    @Override
    public void run() {
        try {
            this.init();
        } catch (Exception exception) {
            exception.printStackTrace();
            this.a(new hr("Failed to start game", exception));
        }
    }

    private void s() {
        if (Keyboard.isKeyDown(60)) {
            if (!this.J) {
                if (Keyboard.isKeyDown(59)) {
                    this.u.a(em.a(Z, this.c, this.d));
                }
                this.J = true;
            }
        } else {
            this.J = false;
        }
    }

    private void a(long l2) {
        int n2;
        long l3 = 16666666L;
        if (this.K == -1L) {
            this.K = System.nanoTime();
        }
        long l4 = System.nanoTime();
        Minecraft.F[Minecraft.G & Minecraft.E.length - 1] = l2;
        Minecraft.E[Minecraft.G++ & Minecraft.E.length - 1] = l4 - this.K;
        this.K = l4;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.c, (double)this.d, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        is is2 = is.a;
        is2.a(7);
        int n3 = (int)(l3 / 200000L);
        is2.b(0x20000000);
        is2.a(0.0, (double)(this.d - n3), 0.0);
        is2.a(0.0, (double)this.d, 0.0);
        is2.a((double)E.length, (double)this.d, 0.0);
        is2.a((double)E.length, (double)(this.d - n3), 0.0);
        is2.b(0x20200000);
        is2.a(0.0, (double)(this.d - n3 * 2), 0.0);
        is2.a(0.0, (double)(this.d - n3), 0.0);
        is2.a((double)E.length, (double)(this.d - n3), 0.0);
        is2.a((double)E.length, (double)(this.d - n3 * 2), 0.0);
        is2.a();
        long l5 = 0L;
        for (n2 = 0; n2 < E.length; ++n2) {
            l5 += E[n2];
        }
        n2 = (int)(l5 / 200000L / (long)E.length);
        is2.a(7);
        is2.b(0x20400000);
        is2.a(0.0, (double)(this.d - n2), 0.0);
        is2.a(0.0, (double)this.d, 0.0);
        is2.a((double)E.length, (double)this.d, 0.0);
        is2.a((double)E.length, (double)(this.d - n2), 0.0);
        is2.a();
        is2.a(1);
        for (int i2 = 0; i2 < E.length; ++i2) {
            int n4 = (i2 - G & E.length - 1) * 255 / E.length;
            int n5 = n4 * n4 / 255;
            n5 = n5 * n5 / 255;
            int n6 = n5 * n5 / 255;
            n6 = n6 * n6 / 255;
            if (E[i2] > l3) {
                is2.b(-16777216 + n5 * 65536);
            } else {
                is2.b(-16777216 + n5 * 256);
            }
            long l6 = E[i2] / 200000L;
            long l7 = F[i2] / 200000L;
            is2.a((double)((float)i2 + 0.5f), (double)((float)((long)this.d - l6) + 0.5f), 0.0);
            is2.a((double)((float)i2 + 0.5f), (double)((float)this.d + 0.5f), 0.0);
            is2.b(-16777216 + n5 * 65536 + n5 * 256 + n5 * 1);
            is2.a((double)((float)i2 + 0.5f), (double)((float)((long)this.d - l6) + 0.5f), 0.0);
            is2.a((double)((float)i2 + 0.5f), (double)((float)((long)this.d - (l6 - l7)) + 0.5f), 0.0);
        }
        is2.a();
        GL11.glEnable(3553);
    }

    public void d() {
        this.H = false;
    }

    public void e() {
        if (!Display.isActive()) {
            return;
        }
        if (this.L) {
            return;
        }
        this.L = true;
        this.B.a();
        this.a((bp)null);
        this.aa = this.R + 10000;
    }

    public void f() {
        if (!this.L) {
            return;
        }
        if (this.g != null) {
            this.g.m_();
        }
        this.L = false;
        this.B.b();
    }

    public void g() {
        if (this.p != null) {
            return;
        }
        this.a(new PauseMenu());
    }

    private void a(int n2, boolean bl2) {
        if (this.b.b) {
            return;
        }
        if (n2 == 0 && this.S > 0) {
            return;
        }
        if (bl2 && this.x != null && this.x.a == 0 && n2 == 0) {
            int n3 = this.x.b;
            int n4 = this.x.c;
            int n5 = this.x.d;
            this.b.c(n3, n4, n5, this.x.e);
            this.h.a(n3, n4, n5, this.x.e);
        } else {
            this.b.a();
        }
    }

    private void a(int n2) {
        InventoryItem fp2;
        if (n2 == 0 && this.S > 0) {
            return;
        }
        if (n2 == 0) {
            this.g.z();
        }
        boolean bl2 = true;
        if (this.x == null) {
            if (n2 == 0 && !(this.b instanceof jt)) {
                this.S = 10;
            }
        } else if (this.x.a == 1) {
            if (n2 == 0) {
                this.b.b(this.g, this.x.g);
            }
            if (n2 == 1) {
                this.b.a(this.g, this.x.g);
            }
        } else if (this.x.a == 0) {
            int n3 = this.x.b;
            int n4 = this.x.c;
            int n5 = this.x.d;
            int n6 = this.x.e;
            Block nq2 = Block.registry[this.e.a(n3, n4, n5)];
            if (n2 == 0) {
                this.e.i(n3, n4, n5, this.x.e);
                if (nq2 != Block.z || this.g.f >= 100) {
                    this.b.a(n3, n4, n5, this.x.e);
                }
            } else {
                int n7;
                InventoryItem fp3 = this.g.e.a();
                int n8 = n7 = fp3 != null ? fp3.count : 0;
                if (this.b.a(this.g, this.e, fp3, n3, n4, n5, n6)) {
                    bl2 = false;
                    this.g.z();
                }
                if (fp3 == null) {
                    return;
                }
                if (fp3.count == 0) {
                    this.g.e.storage[this.g.e.d] = null;
                } else if (fp3.count != n7) {
                    this.r.a.b();
                }
            }
        }
        if (bl2 && n2 == 1 && (fp2 = this.g.e.a()) != null && this.b.a(this.g, this.e, fp2)) {
            this.r.a.c();
        }
    }

    public void h() {
        try {
            this.a = !this.a;
            System.out.println("Toggle fullscreen!");
            if (this.a) {
                Display.setDisplayMode((DisplayMode)Display.getDesktopDisplayMode());
                this.c = Display.getDisplayMode().getWidth();
                this.d = Display.getDisplayMode().getHeight();
                if (this.c <= 0) {
                    this.c = 1;
                }
                if (this.d <= 0) {
                    this.d = 1;
                }
            } else {
                if (this.k != null) {
                    this.c = this.k.getWidth();
                    this.d = this.k.getHeight();
                } else {
                    this.c = this.T;
                    this.d = this.U;
                }
                if (this.c <= 0) {
                    this.c = 1;
                }
                if (this.d <= 0) {
                    this.d = 1;
                }
                Display.setDisplayMode((DisplayMode)new DisplayMode(this.T, this.U));
            }
            this.f();
            Display.setFullscreen((boolean)this.a);
            Display.update();
            Thread.sleep(1000L);
            if (this.a) {
                this.e();
            }
            if (this.p != null) {
                this.f();
                this.a(this.c, this.d);
            }
            System.out.println("Size: " + this.c + ", " + this.d);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void a(int n2, int n3) {
        if (n2 <= 0) {
            n2 = 1;
        }
        if (n3 <= 0) {
            n3 = 1;
        }
        this.c = n2;
        this.d = n3;
        if (this.p != null) {
            kl kl2 = new kl(n2, n3);
            int n4 = kl2.a();
            int n5 = kl2.b();
            this.p.a(this, n4, n5);
        }
    }

    private void t() {
        if (this.x != null) {
            int n2 = this.e.a(this.x.b, this.x.c, this.x.d);
            if (n2 == Block.u.bh) {
                n2 = Block.v.bh;
            }
            if (n2 == Block.aj.bh) {
                n2 = Block.ak.bh;
            }
            if (n2 == Block.z.bh) {
                n2 = Block.t.bh;
            }
            this.g.e.a(n2, this.b instanceof jt);
        }
    }

    public void i() {
        this.u.a();
        this.r.a(1.0f);
        if (this.g != null) {
            this.g.o();
        }
        if (!this.m && this.e != null) {
            this.b.c();
        }
        GL11.glBindTexture(3553, (int)this.n.a("/terrain.png"));
        if (!this.m) {
            this.n.a();
        }
        if (this.p == null && this.g != null && this.g.J <= 0) {
            this.a((bp)null);
        }
        if (this.p != null) {
            this.aa = this.R + 10000;
        }
        if (this.p != null) {
            this.p.d();
            if (this.p != null) {
                this.p.g();
            }
        }
        if (this.p == null || this.p.f) {
            while (Mouse.next()) {
                long l2 = System.currentTimeMillis() - this.N;
                if (l2 > 200L) continue;
                int n2 = Mouse.getEventDWheel();
                if (n2 != 0) {
                    this.g.e.a(n2);
                }
                if (this.p == null) {
                    if (!this.L && Mouse.getEventButtonState()) {
                        this.e();
                        continue;
                    }
                    if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                        this.a(0);
                        this.aa = this.R;
                    }
                    if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                        this.a(1);
                        this.aa = this.R;
                    }
                    if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) continue;
                    this.t();
                    continue;
                }
                if (this.p == null) continue;
                this.p.e();
            }
            if (this.S > 0) {
                --this.S;
            }
            while (Keyboard.next()) {
                this.g.a(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (!Keyboard.getEventKeyState()) continue;
                if (Keyboard.getEventKey() == 87) {
                    this.h();
                    continue;
                }
                if (this.p != null) {
                    this.p.f();
                } else {
                    if (Keyboard.getEventKey() == 1) {
                        this.g();
                    }
                    if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                        this.u();
                    }
                    if (Keyboard.getEventKey() == 63) {
                        boolean bl2 = this.y.y = !this.y.y;
                    }
                    if (Keyboard.getEventKey() == this.y.p.b) {
                        this.a(new ne(this.g.e, this.g.e.c));
                    }
                    if (Keyboard.getEventKey() == this.y.q.b) {
                        this.g.a(this.g.e.a(this.g.e.d, 1), false);
                    }
                    if (this.j() && Keyboard.getEventKey() == this.y.r.b) {
                        this.a(new dr());
                    }
                }
                for (int i2 = 0; i2 < 9; ++i2) {
                    if (Keyboard.getEventKey() != 2 + i2) continue;
                    this.g.e.d = i2;
                }
                if (Keyboard.getEventKey() != this.y.s.b) continue;
                this.y.b(4, Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) ? -1 : 1);
            }
            if (this.p == null) {
                if (Mouse.isButtonDown(0) && (float)(this.R - this.aa) >= this.P.a / 4.0f && this.L) {
                    this.a(0);
                    this.aa = this.R;
                }
                if (Mouse.isButtonDown(1) && (float)(this.R - this.aa) >= this.P.a / 4.0f && this.L) {
                    this.a(1);
                    this.aa = this.R;
                }
            }
            this.a(0, this.p == null && Mouse.isButtonDown(0) && this.L);
        }
        if (this.e != null) {
            if (this.g != null) {
                ++this.ab;
                if (this.ab == 30) {
                    this.ab = 0;
                    this.e.f(this.g);
                }
            }
            this.e.k = this.y.x;
            if (this.e.z) {
                this.e.k = 3;
            }
            if (!this.m) {
                this.r.a();
            }
            if (!this.m) {
                this.f.d();
            }
            if (!this.m) {
                this.e.e();
            }
            if (!this.m || this.j()) {
                this.e.i();
            }
            if (!this.m && this.e != null) {
                this.e.m(TrigLookup.b(this.g.aw), TrigLookup.b(this.g.ax), TrigLookup.b(this.g.ay));
            }
            if (!this.m) {
                this.h.a();
            }
        }
        this.N = System.currentTimeMillis();
    }

    private void u() {
        System.out.println("FORCING RELOAD!");
        this.A = new qg();
        this.A.a(this.y);
        this.Q.a();
    }

    public boolean j() {
        return this.e != null && this.e.z;
    }

    public void b(String string) {
        this.a((Session)null);
        System.gc();
        Session cy2 = new Session(new File(Minecraft.b(), "saves"), string);
        if (cy2.p) {
            this.a(cy2, "Generating level");
        } else {
            this.a(cy2, "Loading level");
        }
    }

    public void k() {
        this.g.m = this.g.m == -1 ? 0 : -1;
        this.e.d(this.g);
        this.g.aN = false;
        double d2 = this.g.aw;
        double d3 = this.g.ay;
        double d4 = 8.0;
        if (this.g.m == -1) {
            this.g.c(d2 /= d4, this.g.ax, d3 /= d4, this.g.aC, this.g.aD);
            this.e.a((lw)this.g, false);
            Session cy2 = new Session(this.e, new om());
            this.a(cy2, "Entering the Nether", this.g);
        } else {
            this.g.c(d2 *= d4, this.g.ax, d3 *= d4, this.g.aC, this.g.aD);
            this.e.a((lw)this.g, false);
            Session cy3 = new Session(this.e, new oz());
            this.a(cy3, "Leaving the Nether", this.g);
        }
        this.g.as = this.e;
        this.g.c(d2, this.g.ax, d3, this.g.aC, this.g.aD);
        this.e.a((lw)this.g, false);
        new no().a(this.e, this.g);
    }

    public void a(Session cy2) {
        this.a(cy2, "");
    }

    public void a(Session cy2, String string) {
        this.a(cy2, string, null);
    }

    public void a(Session cy2, String string, Player eb2) {
        this.q.a(string);
        this.q.d("");
        this.A.a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        if (this.e != null) {
            this.e.a(this.q);
        }
        this.e = cy2;
        System.out.println("Player is " + this.g);
        if (cy2 != null) {
            this.b.a(cy2);
            if (!this.j()) {
                if (eb2 == null) {
                    this.g = (bq)cy2.a(bq.class);
                }
            } else if (this.g != null) {
                this.g.t();
                if (cy2 != null) {
                    cy2.a((lw)this.g);
                }
            }
            if (!cy2.z) {
                this.d(string);
            }
            System.out.println("Player is now " + this.g);
            if (this.g == null) {
                this.g = (bq)this.b.b(cy2);
                this.g.t();
                this.b.a(this.g);
            }
            this.g.a = new he(this.y);
            if (this.f != null) {
                this.f.a(cy2);
            }
            if (this.h != null) {
                this.h.a(cy2);
            }
            this.b.b(this.g);
            if (eb2 != null) {
                cy2.c();
            }
            cy2.a(this.g);
            if (cy2.p) {
                cy2.a(this.q);
            }
        } else {
            this.g = null;
        }
        System.gc();
        this.N = 0L;
    }

    private void d(String string) {
        this.q.a(string);
        this.q.d("Building terrain");
        int n2 = 128;
        int n3 = 0;
        int n4 = n2 * 2 / 16 + 1;
        n4 *= n4;
        for (int i2 = -n2; i2 <= n2; i2 += 16) {
            int n5 = this.e.m;
            int n6 = this.e.o;
            if (this.g != null) {
                n5 = (int)this.g.aw;
                n6 = (int)this.g.ay;
            }
            for (int i3 = -n2; i3 <= n2; i3 += 16) {
                this.q.a(n3++ * 100 / n4);
                this.e.a(n5 + i2, 64, n6 + i3);
                while (this.e.g()) {
                }
            }
        }
        this.q.d("Simulating world for a bit");
        n4 = 2000;
        this.e.l();
    }

    public void a(String string, File file) {
        int n2 = string.indexOf("/");
        String string2 = string.substring(0, n2);
        string = string.substring(n2 + 1);
        if (string2.equalsIgnoreCase("sound")) {
            this.A.a(string, file);
        } else if (string2.equalsIgnoreCase("newsound")) {
            this.A.a(string, file);
        } else if (string2.equalsIgnoreCase("streaming")) {
            this.A.b(string, file);
        } else if (string2.equalsIgnoreCase("music")) {
            this.A.c(string, file);
        } else if (string2.equalsIgnoreCase("newmusic")) {
            this.A.c(string, file);
        }
    }

    public bm l() {
        return this.O;
    }

    public String m() {
        return this.f.b();
    }

    public String n() {
        return this.f.c();
    }

    public String o() {
        return "P: " + this.h.b() + ". T: " + this.e.f();
    }

    public void p() {
        if (!this.e.q.d()) {
            this.k();
        }
        this.e.b();
        this.e.o();
        int n2 = 0;
        if (this.g != null) {
            n2 = this.g.an;
            this.e.d(this.g);
        }
        this.g = (bq)this.b.b(this.e);
        this.g.t();
        this.b.a(this.g);
        this.e.a(this.g);
        this.g.a = new he(this.y);
        this.g.an = n2;
        this.b.b(this.g);
        this.d("Respawning");
        if (this.p instanceof GameOverScreen) {
            this.a((bp)null);
        }
    }

    /*
     * ИСКЛЮЧЕНО для web-порта (см. PATCHES.md): оригинальные методы
     * a(String,String)/a(String,String,String) — desktop AWT-лаунчер
     * (создаёт java.awt.Frame/Canvas, инстанцирует StandaloneClient в
     * отдельном потоке) — и main(String[]) — точка входа desktop JAR.
     * Ни то ни другое не применимо к веб-версии: реальная точка входа —
     * net.minecraft.client.WebEntryPoint.main(), которая создаёт игру
     * напрямую без AWT-окна. StandaloneClient и gn (WindowAdapter) не
     * скопированы в проект (см. PATCHES.md, "Полностью исключённые
     * файлы") — оставлять эти методы означало бы ссылаться на
     * несуществующие типы.
     */
}

