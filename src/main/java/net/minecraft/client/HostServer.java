package net.minecraft.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.web.Mp;
import net.minecraft.client.web.RtcSocket;
import net.minecraft.client.web.Zlib;

/**
 * Встроенный "сервер" мультиплеера для хоста (протокол Alpha 1.2.6, версия 6).
 *
 * В клиентском jar серверной части нет, поэтому здесь реализован минимум, нужный
 * штатному клиенту (ib/hv/pg): рукопожатие и логин, потоковая отправка чанков,
 * синхронизация блоков (через слушатель мира), игроки, выпавшие предметы, мобы
 * (позиции), чат, установка/ломание блоков и атака гостей. Мир принадлежит хосту:
 * гости — это обычные сущности {@link ps} в его мире, поэтому мобы на них реагируют.
 *
 * Транспорт — WebRTC DataChannel (см. web/extras.js, Mp.java, RtcSocket.java):
 * одно сообщение = один пакет. Всё работает в игровом тике хоста, без потоков.
 *
 * Ограничения первой версии: гости неуязвимы (ps не получает урон), нет окон
 * контейнеров (сундуки/печи), нет синхронизации погоды и табличек.
 */
public final class HostServer implements jv {

    /** Всё, что серверу нужно от игры хоста (в тестах подменяется заглушкой). */
    public interface Ctx {
        Session world();
        lw player();
        String hostName();
        void chat(String line);
    }

    private static final class McCtx implements Ctx {
        private final Minecraft mc;
        McCtx(Minecraft mc) { this.mc = mc; }
        public Session world() { return mc.e; }
        public lw player() { return mc.g; }
        public String hostName() { return mc.i != null ? mc.i.b : "Host"; }
        public void chat(String line) { if (mc.u != null) mc.u.a(line); }
    }

    // ---------------------------------------------------------------- состояние
    private static HostServer instance = null;

    /** Сервер запущен (для паузы, подсказок чата и т.п.). */
    public static boolean isActive() { return instance != null; }

    public static HostServer get() { return instance; }

    /** Запускает сервер (если ещё не запущен) для текущего мира хоста. */
    public static HostServer start(Minecraft mc) {
        if (instance == null && mc.e != null && !mc.e.z) {
            instance = new HostServer(new McCtx(mc));
            mc.e.a((jv)instance);
            instance.ctx.chat("\u00a7eServer started. Use /invite to add a player, /stop-server to stop.");
        }
        return instance;
    }

    public static void stop() {
        if (instance != null) {
            instance.shutdown();
            instance = null;
        }
    }

    /** Вызывается при смене мира (выход в меню и т.д.). */
    public static void onWorldChanged() { stop(); }

    public static void tick(Minecraft mc) {
        if (instance != null) instance.tickInternal();
    }

    /** Один тик сервера (для тестов). */
    void tickForTest() { tickInternal(); }

    // -------------------------------------------------------------------- гости
    private static final int PHASE_PENDING = 0;   // приглашение выдано, ждём ответа/соединения
    private static final int PHASE_HANDSHAKE = 1; // канал открыт
    private static final int PHASE_PLAY = 2;

    /** Состояние одного приглашения для экрана хоста. */
    public static final class Invite {
        public volatile String code = null;      // код приглашения (когда готов)
        public volatile String error = null;     // текст ошибки
        public volatile boolean accepted = false; // ответ гостя принят, ждём открытия канала
        public volatile boolean connected = false;
        public volatile String playerName = null;
        Guest guest;
        public void cancel() { if (guest != null) guest.drop("Invite cancelled"); }
        RtcSocket testSocket() { return guest.sock; }
    }

    private static final class Guest {
        final RtcSocket sock;
        final Invite invite;
        int phase = PHASE_PENDING;
        String name = "Player";
        ps entity;
        int quietTicks = 0;
        boolean dead = false;
        final Set<Long> sent = new HashSet<Long>();
        final List<int[]> pending = new ArrayList<int[]>();
        int lastCx = Integer.MIN_VALUE, lastCz = Integer.MIN_VALUE;
        Guest(RtcSocket s, Invite i) { sock = s; invite = i; }
        void drop(String why) { if (!dead) { dead = true; if (sock != null) sock.close(); } }
    }

    final Ctx ctx;
    private final List<Guest> guests = new ArrayList<Guest>();
    private final Map<Integer, int[]> lastSent = new HashMap<Integer, int[]>();
    private int tickCount = 0;

    HostServer(Ctx ctx) { this.ctx = ctx; }

    private Session world() { return ctx.world(); }

    // ======================================================== приглашения (UI)

    /** Создаёт новое приглашение. UI опрашивает поля Invite. */
    public Invite newInvite() {
        RtcSocket sock = new RtcSocket(Mp.newLink(null), false);
        Mp.registerListener(sock.linkId(), sock);
        Invite inv = attach(sock);
        Mp.createInvite(sock.linkId());
        return inv;
    }

    /** Привязывает сокет к новому гостю (общая часть для реального и тестового пути). */
    private Invite attach(RtcSocket sock) {
        final Invite inv = new Invite();
        final Guest g = new Guest(sock, inv);
        inv.guest = g;
        sock.setExtra(new Mp.Listener() {
            public void onOpen() {
                g.phase = PHASE_HANDSHAKE;
                inv.connected = true;
            }
            public void onMessage(byte[] data) { }
            public void onClose() { g.drop("closed"); }
            public void onCode(String code, String error) {
                if (error != null && error.length() > 0) inv.error = error; else inv.code = code;
            }
            public void onAccepted(String error) {
                if (error != null && error.length() > 0) inv.error = error; else inv.accepted = true;
            }
        });
        guests.add(g);
        return inv;
    }

    /** Для тестов: приглашение поверх подменённого сокета. */
    Invite newInviteForTest(RtcSocket sock) { return attach(sock); }

    /** Передать хосту код-ответ гостя. */
    public void acceptAnswer(Invite inv, String code) {
        inv.error = null;
        Mp.acceptAnswer(inv.guest.sock.linkId(), code);
    }

    // ================================================================== тик
    private void tickInternal() {
        ++tickCount;
        generatedThisTick = false;
        Session w = world();
        if (w == null) return;
        for (int i = guests.size() - 1; i >= 0; --i) {
            Guest g = guests.get(i);
            if (g.dead) { removeGuest(g); guests.remove(i); continue; }
            byte[] msg;
            int budget = 100;
            boolean got = false;
            while (budget-- > 0 && (msg = g.sock.poll()) != null) {
                got = true;
                try {
                    gk pkt = gk.b(new DataInputStream(new ByteArrayInputStream(msg)));
                    if (pkt != null) handle(g, pkt);
                } catch (Exception e) {
                    e.printStackTrace();
                    g.drop("Protocol error");
                }
            }
            if (g.phase == PHASE_PENDING) continue;
            g.quietTicks = got ? 0 : g.quietTicks + 1;
            if (g.quietTicks > 1200) { g.drop("Timed out"); continue; }
            if (g.phase == PHASE_PLAY) tickGuest(g);
        }
        if (tickCount % 20 == 0) broadcast(new hl());
        if (tickCount % 200 == 0) {
            ek t = new ek();
            t.a = w.e;
            broadcast(t);
        }
        if (tickCount % 2 == 0) syncEntities();
        pickups();
    }

    private void shutdown() {
        for (Guest g : new ArrayList<Guest>(guests)) {
            if (g.phase == PHASE_PLAY) send(g, new qi("Server closed"));
            g.drop("Server closed");
            removeGuest(g);
        }
        guests.clear();
        if (world() != null) world().b((jv)this);
        ctx.chat("\u00a7eServer stopped.");
    }

    // ============================================================== пакеты
    private void send(Guest g, gk pkt) {
        if (g.dead || !g.sock.isOpen()) return;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(64);
            DataOutputStream dos = new DataOutputStream(bos);
            gk.a(pkt, dos);
            dos.flush();
            byte[] b = bos.toByteArray();
            g.sock.sendPacket(b, b.length);
        } catch (Exception e) {
            e.printStackTrace();
            g.drop("Send error");
        }
    }

    private void broadcast(gk pkt) {
        for (Guest g : guests) if (g.phase == PHASE_PLAY) send(g, pkt);
    }

    private void broadcastExcept(Guest except, gk pkt) {
        for (Guest g : guests) if (g != except && g.phase == PHASE_PLAY) send(g, pkt);
    }

    private void handle(Guest g, gk pkt) {
        if (g.phase == PHASE_HANDSHAKE) {
            if (pkt instanceof hw) {
                send(g, new hw("-"));        // оффлайн-режим
            } else if (pkt instanceof iu) {
                login(g, (iu)pkt);
            } else if (pkt instanceof hl) {
                // keepalive
            } else {
                g.drop("Unexpected packet before login");
            }
            return;
        }
        if (pkt instanceof fa) {
            movement(g, (fa)pkt);
        } else if (pkt instanceof jr) {
            chat(g, ((jr)pkt).a);
        } else if (pkt instanceof gc) {
            dig(g, (gc)pkt);
        } else if (pkt instanceof ed) {
            place(g, (ed)pkt);
        } else if (pkt instanceof p) {
            inventory(g, (p)pkt);
        } else if (pkt instanceof eq) {
            int slot = ((eq)pkt).b;
            if (slot >= 0 && slot < 9) g.entity.e.d = slot;
        } else if (pkt instanceof ii) {
            ii a = (ii)pkt;
            ii out = new ii();
            out.a = g.entity.an;
            out.b = a.b;
            broadcastExcept(g, out);
        } else if (pkt instanceof id) {
            dropItem(g, (id)pkt);
        } else if (pkt instanceof a) {
            attack(g, (a)pkt);
        } else if (pkt instanceof qi) {
            g.drop("Quit");
        }
        // hl (keepalive), jk (respawn: гости неуязвимы), cq и прочее игнорируем
    }

    // ------------------------------------------------------------------ вход
    private static String cleanName(String raw) {
        StringBuilder sb = new StringBuilder();
        if (raw != null) {
            for (int i = 0; i < raw.length() && sb.length() < 16; i++) {
                char c = raw.charAt(i);
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') sb.append(c);
            }
        }
        return sb.length() == 0 ? "Player" : sb.toString();
    }

    private boolean nameTaken(String n) {
        if (n.equalsIgnoreCase(ctx.hostName())) return true;
        for (Guest o : guests) if (o.phase == PHASE_PLAY && o.name.equalsIgnoreCase(n)) return true;
        return false;
    }

    private void login(Guest g, iu in) {
        if (in.a != 6) {
            send(g, new qi(in.a < 6 ? "Outdated client!" : "Outdated server!"));
            g.drop("Wrong protocol version");
            return;
        }
        String base = cleanName(in.b), name = base;
        for (int n = 2; nameTaken(name); n++) name = (base.length() > 13 ? base.substring(0, 13) : base) + n;
        g.name = name;
        Session w = world();

        // Сущность гостя в мире хоста (на позиции хоста)
        lw host = ctx.player();
        ps e = new ps(w, name);
        e.bl = null;   // не пытаться качать скин по HTTP
        double fx = host.aw, fy = host.aG.b, fz = host.ay;
        e.b(fx, fy, fz, host.aC, host.aD);
        w.a((lw)e);
        g.entity = e;
        g.invite.playerName = name;

        g.phase = PHASE_PLAY;   // с этого момента изменения блоков в отправленных чанках уходят гостю

        // Логин-ответ
        iu out = new iu();
        out.a = e.an;
        out.b = "";
        out.c = "";
        out.d = w.u;
        out.e = (byte)0;
        send(g, out);

        kv spawn = new kv();
        spawn.a = w.m; spawn.b = w.n; spawn.c = w.o;
        send(g, spawn);
        ek time = new ek();
        time.a = w.e;
        send(g, time);
        send(g, new p(-1, starterInventory(e)));
        send(g, new p(-2, new InventoryItem[4]));
        send(g, new p(-3, new InventoryItem[4]));

        // Ближние чанки сразу — чтобы гость не провалился; остальные потоком
        int cx = (int)Math.floor(fx / 16.0), cz = (int)Math.floor(fz / 16.0);
        for (int dx = -2; dx <= 2; dx++) for (int dz = -2; dz <= 2; dz++) w.c(cx + dx, cz + dz);   // окружение 3x3 каждого из 9 чанков
        for (int dx = -1; dx <= 1; dx++) for (int dz = -1; dz <= 1; dz++) sendChunk(g, cx + dx, cz + dz);
        g.lastCx = Integer.MIN_VALUE; g.lastCz = Integer.MIN_VALUE;   // потоковая отправка остального начнётся в тике

        // Позиция гостя (клиент закрывает экран загрузки по первому такому пакету)
        send(g, new cr(fx, fy + 1.62, fy, fz, host.aC, host.aD, true));

        // Показать гостю хоста и остальных игроков, а гостя — остальным
        send(g, spawnPlayerPacket(host.an, ctx.hostName(), host.aw, host.aG.b, host.ay, host.aC, host.aD));
        for (Guest o : guests) {
            if (o != g && o.phase == PHASE_PLAY && o.entity != null) {
                lw oe = o.entity;
                send(g, spawnPlayerPacket(oe.an, o.name, oe.aw, oe.ax, oe.ay, oe.aC, oe.aD));
            }
        }
        broadcastExcept(g, spawnPlayerPacket(e.an, name, fx, fy, fz, host.aC, host.aD));
        sendExistingEntities(g);

        String msg = "\u00a7e" + name + " joined the game";
        ctx.chat(msg);
        broadcast(new jr(msg));
    }

    private static InventoryItem[] starterInventory(Player p) {
        InventoryItem[] inv = new InventoryItem[37];
        int[][] kit = {
            {257, 1}, {258, 1}, {256, 1}, {267, 1}, {50, 64}, {1, 64}, {3, 64}, {5, 64}, {4, 64},
            {17, 64}, {20, 64}, {12, 64}, {13, 64}, {45, 64}, {58, 1}, {61, 1}, {85, 64}, {65, 64},
            {35, 64}, {18, 64}, {320, 32}, {263, 64}
        };
        for (int i = 0; i < kit.length; i++) inv[i] = new InventoryItem(kit[i][0], kit[i][1], 0);
        return inv;
    }

    private hs spawnPlayerPacket(int id, String name, double x, double feetY, double z, float yaw, float pitch) {
        hs s = new hs();
        s.a = id;
        s.b = name;
        s.c = (int)Math.floor(x * 32.0);
        s.d = (int)Math.floor(feetY * 32.0);
        s.e = (int)Math.floor(z * 32.0);
        s.f = (byte)(int)(yaw * 256.0f / 360.0f);
        s.g = (byte)(int)(pitch * 256.0f / 360.0f);
        s.h = 0;
        return s;
    }

    // ----------------------------------------------------------------- чанки
    private void sendChunk(Guest g, int cx, int cz) {
        long key = ((long)cx << 32) ^ ((long)cz & 0xFFFFFFFFL);
        if (g.sent.contains(key)) return;
        // Сначала загружаем/генерируем чанк (это может достроить соседей — деревья и т.п.),
        // и только потом помечаем отправленным: любые изменения ПОСЛЕ этого момента уйдут
        // гостю отдельными пакетами блоков через слушатель мира.
        ha chunk = world().c(cx, cz);
        g.sent.add(key);
        lq pre = new lq();
        pre.a = cx; pre.b = cz; pre.c = true;
        send(g, pre);
        byte[] raw = new byte[81920];
        System.arraycopy(chunk.b, 0, raw, 0, 32768);
        System.arraycopy(chunk.e.a, 0, raw, 32768, 16384);   // метаданные
        System.arraycopy(chunk.g.a, 0, raw, 49152, 16384);   // свет блоков
        System.arraycopy(chunk.f.a, 0, raw, 65536, 16384);   // свет неба
        byte[] z = Zlib.compress(raw, raw.length);
        ci pkt = new ci();
        pkt.a = cx * 16; pkt.b = 0; pkt.c = cz * 16;
        pkt.d = 16; pkt.e = 128; pkt.f = 16;
        pkt.setCompressed(z);
        send(g, pkt);
    }

    private static final int VIEW = 6;

    /**
     * Чанк C меняется при "достройке" (деревья, руды, озёра) самого C и его соседей
     * C-x, C-z, C-x-z — а они происходят, когда загружены все 8 соседей. Генераторы
     * ставят блоки "тихо" (без оповещения слушателей), поэтому отправлять чанк гостю
     * можно только когда его 3x3-окружение уже загружено, иначе гость увидит чанк без
     * деревьев/руд. Возвращает число ещё не загруженных чанков окружения.
     */
    private int missingNeighbours(int cx, int cz, int[] firstMissing) {
        int missing = 0;
        Session w = world();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (!w.isChunkLoaded(cx + dx, cz + dz)) {
                    if (missing == 0) { firstMissing[0] = cx + dx; firstMissing[1] = cz + dz; }
                    missing++;
                }
            }
        }
        return missing;
    }

    /** Генерируем не больше одного нового чанка за тик на весь сервер — чтобы не было фризов. */
    private boolean generatedThisTick = false;

    private void tickGuest(Guest g) {
        lw e = g.entity;
        int cx = (int)Math.floor(e.aw / 16.0), cz = (int)Math.floor(e.ay / 16.0);
        if (cx != g.lastCx || cz != g.lastCz || tickCount % 40 == 0) {
            g.lastCx = cx; g.lastCz = cz;
            g.pending.clear();
            for (int dx = -VIEW; dx <= VIEW; dx++) {
                for (int dz = -VIEW; dz <= VIEW; dz++) {
                    long key = ((long)(cx + dx) << 32) ^ ((long)(cz + dz) & 0xFFFFFFFFL);
                    if (!g.sent.contains(key)) g.pending.add(new int[]{cx + dx, cz + dz, dx * dx + dz * dz});
                }
            }
            java.util.Collections.sort(g.pending, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) { return a[2] - b[2]; }
            });
            // выгрузка далёких чанков у гостя
            List<Long> drop = new ArrayList<Long>();
            for (Long key : g.sent) {
                int kx = (int)(key.longValue() >> 32), kz = (int)key.longValue();
                if (Math.abs(kx - cx) > VIEW + 2 || Math.abs(kz - cz) > VIEW + 2) drop.add(key);
            }
            for (Long key : drop) {
                g.sent.remove(key);
                lq un = new lq();
                un.a = (int)(key.longValue() >> 32); un.b = (int)key.longValue(); un.c = false;
                send(g, un);
            }
            if (tickCount % 30 == 0) world().f((lw)e);   // держим чанки рядом с гостем загруженными
        }
        int sends = 2;
        int[] miss = new int[2];
        for (int i = 0; i < g.pending.size() && sends > 0; ) {
            int[] c = g.pending.get(i);
            int missing = missingNeighbours(c[0], c[1], miss);
            if (missing == 0) {
                g.pending.remove(i);
                sendChunk(g, c[0], c[1]);
                sends--;
            } else if (!generatedThisTick) {
                generatedThisTick = true;
                world().c(miss[0], miss[1]);      // догружаем одного соседа, остальные — в следующие тики
                i++;
            } else {
                i++;
                if (i > 24) break;                // не перебираем весь список ради пары чанков
            }
        }
    }

    // ------------------------------------------------------------ действия гостя
    private void movement(Guest g, fa f) {
        ps e = g.entity;
        if (f.h) {
            double x = f.a, y = f.b, z = f.c;
            if (Math.abs(x) < 3.2e7 && Math.abs(z) < 3.2e7 && y > -64 && y < 400) {
                e.a(x, y, z, f.i ? f.e : e.aC, f.i ? f.f : e.aD, 3);
            }
        } else if (f.i) {
            e.a(e.aw, e.ax, e.ay, f.e, f.f, 3);
        }
        e.aH = f.g;
    }

    private void chat(Guest g, String text) {
        if (text == null) return;
        text = text.trim();
        if (text.length() == 0 || text.length() > 100) return;
        if (text.startsWith("/")) {
            send(g, new jr("\u00a7cCommands are available only to the host."));
            return;
        }
        String line = "<" + g.name + "> " + text;
        ctx.chat(line);
        broadcast(new jr(line));
    }

    /** Сообщение хоста (из окна чата). */
    public void hostChat(String text) {
        String line = "<" + ctx.hostName() + "> " + text;
        ctx.chat(line);
        broadcast(new jr(line));
    }

    private boolean near(Guest g, int x, int y, int z, double max) {
        double dx = g.entity.aw - (x + 0.5), dy = g.entity.ax - (y + 0.5), dz = g.entity.ay - (z + 0.5);
        return dx * dx + dy * dy + dz * dz <= max * max;
    }

    private void dig(Guest g, gc d) {
        if (d.e != 3) return;                       // 3 = блок сломан (остальное — анимация)
        int x = d.a, y = d.b, z = d.c;
        if (y < 0 || y >= 128 || !near(g, x, y, z, 10.0)) { resync(g, x, y, z); return; }
        Session w = world();
        int id = w.a(x, y, z);
        if (id <= 0 || id == 7 || Block.registry[id] == null) { resync(g, x, y, z); return; }   // 7 = бедрок
        int meta = w.e(x, y, z);
        Block b = Block.registry[id];
        boolean ok = w.d(x, y, z, 0);
        if (ok) b.b(w, x, y, z, meta);
    }

    private void place(Guest g, ed p) {
        Session w = world();
        ps e = g.entity;
        if (p.c == -1 && p.b == -1) return;         // использование предмета в воздухе — не поддержано
        int x = p.b, y = p.c, z = p.d, face = p.e;
        if (y < 0 || y >= 128 || !near(g, x, y, z, 10.0)) { resync(g, x, y, z); return; }
        InventoryItem held = e.e.storage[e.e.d];
        if (held == null || held.id != p.a) held = p.a > 0 ? new InventoryItem(p.a, 1, 0) : null;
        int bid = w.a(x, y, z);
        boolean used = false;
        if (bid > 0 && Block.registry[bid] != null && Block.registry[bid].a(w, x, y, z, (Player)e)) used = true;
        if (!used && held != null) held.a((Player)e, w, x, y, z, face);
        // Результат придёт гостю через слушатель блоков; на всякий случай досылаем соседний блок
        resync(g, x, y, z);
        int ox = x + (face == 4 ? -1 : face == 5 ? 1 : 0), oy = y + (face == 0 ? -1 : face == 1 ? 1 : 0), oz = z + (face == 2 ? -1 : face == 3 ? 1 : 0);
        resync(g, ox, oy, oz);
    }

    private void resync(Guest g, int x, int y, int z) {
        if (y < 0 || y >= 128) return;
        Session w = world();
        mx m = new mx();
        m.a = x; m.b = y; m.c = z;
        m.d = w.a(x, y, z);
        m.e = w.e(x, y, z);
        send(g, m);
    }

    private void inventory(Guest g, p pkt) {
        ps e = g.entity;
        if (pkt.a == -1 && pkt.b != null) {
            for (int i = 0; i < e.e.storage.length && i < pkt.b.length; i++) e.e.storage[i] = pkt.b[i];
        } else if (pkt.a == -2 && pkt.b != null) {
            for (int i = 0; i < e.e.c.length && i < pkt.b.length; i++) e.e.c[i] = pkt.b[i];
        } else if (pkt.a == -3 && pkt.b != null) {
            for (int i = 0; i < e.e.b.length && i < pkt.b.length; i++) e.e.b[i] = pkt.b[i];
        }
    }

    private void dropItem(Guest g, id d) {
        if (d.h <= 0 || d.i <= 0 || d.i > 64) return;
        DroppedItem it = new DroppedItem(world(), d.b / 32.0, d.c / 32.0, d.d / 32.0, new InventoryItem(d.h, d.i));
        it.az = d.e / 128.0; it.aA = d.f / 128.0; it.aB = d.g / 128.0;
        it.c = 40;
        world().a((lw)it);
    }

    private void attack(Guest g, a u) {
        if (u.c != 1) return;
        lw target = null;
        List all = world().b;
        for (int i = 0; i < all.size(); i++) {
            lw o = (lw)all.get(i);
            if (o.an == u.b) { target = o; break; }
        }
        if (target == null || target == g.entity || target.aN) return;
        double dx = g.entity.aw - target.aw, dy = g.entity.ax - target.ax, dz = g.entity.ay - target.ay;
        if (dx * dx + dy * dy + dz * dz > 36.0) return;
        g.entity.a_(target);
    }

    // ============================================ синхронизация сущностей мира
    private static boolean syncable(lw e) {
        return e instanceof DroppedItem || (e instanceof Mob && !(e instanceof Player));
    }

    private void sendExistingEntities(Guest g) {
        List all = world().b;
        for (int i = 0; i < all.size(); i++) {
            lw e = (lw)all.get(i);
            if (!e.aN && syncable(e)) sendSpawn(g, e);
        }
    }

    private void sendSpawn(Guest g, lw e) {
        if (e instanceof DroppedItem) {
            if (((DroppedItem)e).a != null) send(g, new id((DroppedItem)e));
        } else if (e instanceof Mob) {
            try {
                send(g, new fv((Mob)e));
            } catch (RuntimeException ex) {
                // класс моба не зарегистрирован в EntityRegistry — клиент его всё равно не создаст
            }
        }
    }

    /** Позиции игроков и мобов (раз в 2 тика, только изменившиеся). */
    private void syncEntities() {
        if (guests.isEmpty()) return;
        lw host = ctx.player();
        if (host != null) sendMove(null, host, host.aG.b);
        for (Guest g : guests) {
            if (g.phase == PHASE_PLAY && g.entity != null) sendMove(g, g.entity, g.entity.ax);
        }
        if (tickCount % 6 == 0) {
            List all = world().b;
            for (int i = 0; i < all.size(); i++) {
                lw e = (lw)all.get(i);
                if (e instanceof Mob && !(e instanceof Player) && !e.aN) sendMove(null, e, e.ax);
            }
        }
    }

    private void sendMove(Guest owner, lw e, double feetY) {
        int[] now = {
            (int)Math.floor(e.aw * 32.0), (int)Math.floor(feetY * 32.0), (int)Math.floor(e.ay * 32.0),
            (int)(e.aC * 256.0f / 360.0f) & 255, (int)(e.aD * 256.0f / 360.0f) & 255
        };
        int[] old = lastSent.get(e.an);
        if (old != null && old[0] == now[0] && old[1] == now[1] && old[2] == now[2] && old[3] == now[3] && old[4] == now[4]) return;
        lastSent.put(e.an, now);
        ky t = new ky();
        t.a = e.an; t.b = now[0]; t.c = now[1]; t.d = now[2];
        t.e = (byte)now[3]; t.f = (byte)now[4];
        if (owner == null) broadcast(t); else broadcastExcept(owner, t);
    }

    /** Подбор выпавших предметов гостями (хост подбирает своим игроком как обычно). */
    private void pickups() {
        if (guests.isEmpty()) return;
        List all = world().b;
        for (int i = 0; i < all.size(); i++) {
            lw e = (lw)all.get(i);
            if (!(e instanceof DroppedItem) || e.aN) continue;
            DroppedItem it = (DroppedItem)e;
            if (it.c > 0 || it.a == null) continue;
            for (Guest g : guests) {
                if (g.phase != PHASE_PLAY || g.entity == null) continue;
                double dx = g.entity.aw - e.aw, dy = g.entity.aG.b - e.ax, dz = g.entity.ay - e.ay;
                if (Math.abs(dx) < 1.2 && Math.abs(dz) < 1.2 && dy > -1.5 && dy < 2.0) {
                    mt give = new mt();
                    give.a = it.a.id; give.b = it.a.count; give.c = it.a.damage;
                    send(g, give);
                    bu col = new bu();
                    col.a = e.an; col.b = g.entity.an;
                    broadcast(col);
                    it.J();
                    break;
                }
            }
        }
    }

    private void removeGuest(Guest g) {
        if (g.entity != null) {
            if (!g.entity.aN) g.entity.J();
            lastSent.remove(g.entity.an);
            if (g.phase == PHASE_PLAY) {
                String msg = "\u00a7e" + g.name + " left the game";
                ctx.chat(msg);
                broadcastExcept(g, new jr(msg));
            }
            g.entity = null;
        }
        g.phase = PHASE_PENDING;
        g.invite.connected = false;
    }

    // ==================================================== слушатель мира (jv)
    @Override
    public void a(int x, int y, int z) {
        if (guests.isEmpty() || y < 0 || y >= 128) return;
        Session w = world();
        long key = ((long)(x >> 4) << 32) ^ ((long)(z >> 4) & 0xFFFFFFFFL);
        mx m = null;
        for (Guest g : guests) {
            if (g.phase != PHASE_PLAY || !g.sent.contains(key)) continue;
            if (m == null) {
                m = new mx();
                m.a = x; m.b = y; m.c = z;
                m.d = w.a(x, y, z);
                m.e = w.e(x, y, z);
            }
            send(g, m);
        }
    }

    @Override public void b(int a, int b, int c, int d, int e, int f) { }
    @Override public void a(String s, double x, double y, double z, float v, float p) { }
    @Override public void a(String s, double x, double y, double z, double a, double b, double c) { }

    @Override
    public void a(lw e) {
        if (guests.isEmpty() || !syncable(e)) return;
        for (Guest g : guests) if (g.phase == PHASE_PLAY) sendSpawn(g, e);
    }

    @Override
    public void b(lw e) {
        if (guests.isEmpty() || e instanceof Player) {
            return;
        }
        if (syncable(e)) {
            li d = new li();
            d.a = e.an;
            broadcast(d);
            lastSent.remove(e.an);
        }
    }

    @Override public void e() { }
    @Override public void a(String s, int x, int y, int z) { }
    @Override public void a(int x, int y, int z, TileEntityRegistry t) { }
}
