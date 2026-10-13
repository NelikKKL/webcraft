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
        /** Для окна приглашения: сокет этого приглашения (нужен номер ссылки WebRTC). */
        public Guest guest() { return guest; }
    }

    public static final class Guest {
        public final RtcSocket sock;
        final Invite invite;
        int phase = PHASE_PENDING;
        String name = "Player";
        ps entity;
        int quietTicks = 0;
        boolean dead = false;
        final Set<Long> sent = new HashSet<Long>();
        final List<int[]> pending = new ArrayList<int[]>();
        int lastCx = Integer.MIN_VALUE, lastCz = Integer.MIN_VALUE;
        // здоровье / падение / смерть
        int lastHealth = -1;
        double lastY = 0;
        float fall = 0f;
        boolean deathAnnounced = false;
        int lastHurt = 0;
        byte[] skinPng = null;      // скин гостя (пакет 200) и флаг тонких рук
        boolean skinSlim = false;
        // открытый контейнер (сундук, печь, раздатчик, табличка): позиция и последнее отправленное состояние
        boolean hasOpen = false;
        int openX, openY, openZ;
        byte[] openNbt = null;
        Guest(RtcSocket s, Invite i) { sock = s; invite = i; }
        void drop(String why) { if (!dead) { dead = true; if (sock != null) sock.close(); } }
    }

    /** Состояние ушедшего игрока (по нику) — чтобы при возвращении не потерять инвентарь и здоровье. */
    private static final class Saved {
        InventoryItem[] storage, armor, crafting;
        int health;
        double x, y, z;
    }
    private final Map<String, Saved> saved = new HashMap<String, Saved>();

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

    /** Ники игроков онлайн (для /list). */
    public List<String> playerNames() {
        List<String> out = new ArrayList<String>();
        out.add(ctx.hostName());
        for (Guest g : guests) if (g.phase == PHASE_PLAY) out.add(g.name);
        return out;
    }

    /** /kick: отключает игрока по нику. Возвращает false, если такого нет. */
    public boolean kick(String name) {
        for (Guest g : guests) {
            if (g.phase == PHASE_PLAY && g.name.equalsIgnoreCase(name)) {
                send(g, new qi("Kicked by the host"));
                g.drop("Kicked");
                return true;
            }
        }
        return false;
    }

    /** /kit: стартовый набор блоков и инструментов всем гостям (по умолчанию гости начинают с пустым инвентарём). */
    public int giveKit() {
        int n = 0;
        for (Guest g : guests) {
            if (g.phase != PHASE_PLAY || g.entity == null) continue;
            InventoryItem[] kit = starterInventory(g.entity);
            g.entity.e.storage = kit;
            send(g, new p(-1, kit));
            n++;
        }
        return n;
    }

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
        if (tickCount % 2 == 0) { syncEntities(); syncPlayerLooks(); }
        int sv = net.minecraft.client.web.SkinStore.localVersion();
        if (sv != hostSkinVersion) {              // хост сменил скин — разослать (первый раз — просто запомнить)
            if (hostSkinVersion >= 0) sendHostSkin(null);
            hostSkinVersion = sv;
        }
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
            // показать взмах руки / приседание гостя на экране хоста (его сущность ps — в мире хоста)
            if (a.b == 1) g.entity.z();
            else if (a.b == 104) g.entity.bw = true;
            else if (a.b == 105) g.entity.bw = false;
            ii out = new ii();
            out.a = g.entity.an;
            out.b = a.b;
            broadcastExcept(g, out);
        } else if (pkt instanceof id) {
            dropItem(g, (id)pkt);
        } else if (pkt instanceof a) {
            attack(g, (a)pkt);
        } else if (pkt instanceof SkinPacket) {
            skinFromGuest(g, (SkinPacket)pkt);
        } else if (pkt instanceof jk) {
            respawn(g);
        } else if (pkt instanceof py) {
            tileFromGuest(g, (py)pkt);
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
        e.takesDamage = true;
        double[] spot = findSpawnSpot(w, host);
        Saved back = saved.get(name.toLowerCase());
        if (back != null && back.y > 1 && back.y < 126) spot = new double[]{back.x, back.y, back.z};
        double fx = spot[0], fy = spot[1], fz = spot[2];
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
        // Инвентарь: как в обычном выживании — пустой; вернувшийся игрок получает свои вещи и здоровье.
        Saved sv = saved.get(name.toLowerCase());
        if (sv != null) {
            e.e.storage = sv.storage; e.e.b = sv.armor; e.e.c = sv.crafting;
            if (sv.health > 0) e.J = sv.health;
        }
        send(g, new p(-1, e.e.storage));
        send(g, new p(-2, e.e.c));
        send(g, new p(-3, e.e.b));

        // Ближние чанки сразу — чтобы гость не провалился; остальные потоком
        int cx = (int)Math.floor(fx / 16.0), cz = (int)Math.floor(fz / 16.0);
        for (int dx = -2; dx <= 2; dx++) for (int dz = -2; dz <= 2; dz++) w.c(cx + dx, cz + dz);   // окружение 3x3 каждого из 9 чанков
        for (int dx = -1; dx <= 1; dx++) for (int dz = -1; dz <= 1; dz++) sendChunk(g, cx + dx, cz + dz);
        g.lastCx = Integer.MIN_VALUE; g.lastCz = Integer.MIN_VALUE;   // потоковая отправка остального начнётся в тике

        // Позиция гостя (клиент закрывает экран загрузки по первому такому пакету)
        send(g, new cr(fx, fy + 1.62, fy, fz, host.aC, host.aD, true));

        // Показать гостю хоста и остальных игроков, а гостя — остальным
        send(g, spawnPlayerPacket(host.an, ctx.hostName(), host.aw, host.aG.b, host.ay, host.aC, host.aD, heldId(host)));
        hostHeld = heldId(host);
        for (Guest o : guests) {
            if (o != g && o.phase == PHASE_PLAY && o.entity != null) {
                lw oe = o.entity;
                send(g, spawnPlayerPacket(oe.an, o.name, oe.aw, oe.ax, oe.ay, oe.aC, oe.aD, heldId(oe)));
            }
        }
        broadcastExcept(g, spawnPlayerPacket(e.an, name, fx, fy, fz, host.aC, host.aD, heldId(e)));
        sendExistingEntities(g);
        sendKnownSkins(g);

        String msg = "\u00a7e" + name + " joined the game";
        ctx.chat(msg);
        broadcast(new jr(msg));
    }

    /**
     * Куда поставить нового гостя: рядом с хостом, на свободном месте с твёрдым полом.
     * Раньше гость появлялся ровно в точке хоста — камера оказывалась внутри модели хоста
     * (тёмный экран), да и тесно. Если подходящего места рядом нет — над хостом.
     */
    private static double[] findSpawnSpot(Session w, lw host) {
        int hx = (int)Math.floor(host.aw), hy = (int)Math.floor(host.aG.b), hz = (int)Math.floor(host.ay);
        int[][] offs = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}, {3, 0}, {-3, 0}, {0, 3}, {0, -3}};
        for (int[] o : offs) {
            int x = hx + o[0], z = hz + o[1];
            for (int dy = 2; dy >= -2; dy--) {
                int y = hy + dy;
                if (y < 1 || y > 120) continue;
                if (w.a(x, y, z) == 0 && w.a(x, y + 1, z) == 0 && w.a(x, y - 1, z) > 0) {
                    return new double[]{x + 0.5, y, z + 0.5};
                }
            }
        }
        return new double[]{host.aw, host.aG.b + 1.0, host.ay};
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

    private static int heldId(lw e) {
        if (e instanceof Player) {
            InventoryItem it = ((Player)e).e.a();
            return it == null ? 0 : it.id;
        }
        return 0;
    }

    private hs spawnPlayerPacket(int id, String name, double x, double feetY, double z, float yaw, float pitch) {
        return spawnPlayerPacket(id, name, x, feetY, z, yaw, pitch, 0);
    }

    private hs spawnPlayerPacket(int id, String name, double x, double feetY, double z, float yaw, float pitch, int held) {
        hs s = new hs();
        s.a = id;
        s.b = name;
        s.c = (int)Math.floor(x * 32.0);
        s.d = (int)Math.floor(feetY * 32.0);
        s.e = (int)Math.floor(z * 32.0);
        s.f = (byte)(int)(yaw * 256.0f / 360.0f);
        s.g = (byte)(int)(pitch * 256.0f / 360.0f);
        s.h = held;
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
        sendChunkTiles(g, chunk);
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
        ps ent = g.entity;
        if (ent == null) return;
        guestVitals(g, ent);
        if (tickCount % 5 == 0) watchOpenTile(g);
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

    // ------------------------------------------------------------ здоровье, смерть, возрождение гостя
    /** Здоровье -> клиенту (пакет 8), урон -> отдача и анимация, смерть -> сообщение в чат. */
    private void guestVitals(Guest g, ps ent) {
        int hp = ent.J;
        if (hp != g.lastHealth) {
            cq c = new cq();
            c.a = Math.max(hp, 0);
            send(g, c);
            if (g.lastHealth > hp && hp > 0) {
                // отдача от удара — клиент сам двигает своего игрока
                send(g, new dv(ent));
                ent.az = 0.0; ent.aA = 0.0; ent.aB = 0.0;
            }
            g.lastHealth = hp;
        }
        if (ent.M > g.lastHurt && hp > 0) {                       // остальные гости видят, что игрока ударили
            fs f = new fs(); f.a = ent.an; f.b = (byte)2; broadcastExcept(g, f);
        }
        g.lastHurt = ent.M;
        if (hp <= 0 && !g.deathAnnounced) {
            g.deathAnnounced = true;
            String msg = "\u00a7e" + g.name + " died";
            ctx.chat(msg);
            broadcast(new jr(msg));
            fs f = new fs(); f.a = ent.an; f.b = (byte)3; broadcastExcept(g, f);
        }
    }

    // ------------------------------------------------------------ скины (пакет 200)
    private int hostSkinVersion = -1;

    /** Гость прислал свой скин: применить к его сущности на хосте и разослать остальным. */
    private void skinFromGuest(Guest g, SkinPacket p) {
        if (g.entity == null || p.c == null || p.c.length == 0 || p.c.length > SkinPacket.MAX_BYTES) return;
        g.skinPng = p.c;
        g.skinSlim = p.b == 1;
        net.minecraft.client.web.SkinStore.queueRemote(g.entity.an, g.skinSlim, g.skinPng);   // хост тоже видит скин
        broadcastExcept(g, new SkinPacket(g.entity.an, g.skinSlim, g.skinPng));
    }

    /** Свой скин хоста -> всем гостям (при входе гостя и при смене скина). */
    private void sendHostSkin(Guest only) {
        byte[] png = net.minecraft.client.web.SkinStore.localPng();
        lw host = ctx.player();
        if (png == null || host == null) return;
        SkinPacket sp = new SkinPacket(host.an, net.minecraft.client.web.SkinStore.isSlim(), png);
        if (only != null) send(only, sp); else broadcast(sp);
    }

    /** Гость при входе получает скины всех, кто уже в игре (и хоста). */
    private void sendKnownSkins(Guest to) {
        sendHostSkin(to);
        for (Guest o : guests) {
            if (o != to && o.phase == PHASE_PLAY && o.skinPng != null && o.entity != null) {
                send(to, new SkinPacket(o.entity.an, o.skinSlim, o.skinPng));
            }
        }
    }

    /** Гость нажал "Respawn": новая сущность на месте старой, клиенту — пакет возрождения (клиент сам создаёт игрока). */
    private void respawn(Guest g) {
        Session w = world();
        ps old = g.entity;
        if (old == null) return;
        int id = old.an;
        if (!old.aN) old.J();
        ps e = new ps(w, g.name);
        e.bl = null;
        e.takesDamage = true;
        e.an = id;
        double x = w.m + 0.5, z = w.o + 0.5;
        double y = w.n;
        e.b(x, y, z, 0f, 0f);
        w.a((lw)e);
        g.entity = e;
        g.lastHealth = -1;
        g.lastHurt = 0;
        g.fall = 0f;
        g.lastY = y;
        g.deathAnnounced = false;
        g.hasOpen = false;
        lastSent.remove(id);
        heldSent.remove(id);
        li gone = new li(); gone.a = id; broadcastExcept(g, gone);                         // другие гости: убрать труп
        broadcastExcept(g, spawnPlayerPacket(id, g.name, x, y, z, 0f, 0f));              // и показать возродившегося
        send(g, new jk());
        send(g, new p(-1, new InventoryItem[37]));
        send(g, new cr(x, y + 1.62, y, z, 0f, 0f, true));
        g.lastCx = Integer.MIN_VALUE; g.lastCz = Integer.MIN_VALUE;   // заново посчитать чанки вокруг
    }

    // ------------------------------------------------------------ действия гостя
    private void movement(Guest g, fa f) {
        ps e = g.entity;
        if (e == null || e.aN) return;
        trackFall(g, f);
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

    /** Урон от падения: считаем пройденное вниз без опоры, на земле — ceil(высота - 3), как в оригинале. */
    private void trackFall(Guest g, fa f) {
        if (!f.h) return;
        double y = f.b;
        Session w = world();
        int bx = (int)Math.floor(f.a), bz = (int)Math.floor(f.c), by = (int)Math.floor(y);
        int inBlock = (by >= 0 && by < 128) ? w.a(bx, by, bz) : 0;
        boolean soft = inBlock == 8 || inBlock == 9 || inBlock == 65;   // вода и лестница гасят падение
        if (soft) {
            g.fall = 0f;
        } else if (!f.g) {
            if (y < g.lastY) g.fall += (float)(g.lastY - y);
        } else {
            if (g.fall > 3.0f && g.entity.J > 0) {
                int dmg = (int)Math.ceil(g.fall - 3.0f);
                if (dmg > 0) g.entity.a((lw)null, dmg);
            }
            g.fall = 0f;
        }
        g.lastY = y;
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
        // Клиент шлёт использование предмета "в воздух" как (x=-1, y=-1, z=-1, сторона=255); y читается как
        // беззнаковый байт (255), поэтому проверяем сторону 255, а не y == -1.
        if (p.e == 255 || (p.b == -1 && p.d == -1)) { useInAir(g, p); return; }
        int x = p.b, y = p.c, z = p.d, face = p.e;
        if (y < 0 || y >= 128 || !near(g, x, y, z, 10.0)) { resync(g, x, y, z); return; }
        InventoryItem held = e.e.storage[e.e.d];
        if (held == null || held.id != p.a) held = p.a > 0 ? new InventoryItem(p.a, 1, 0) : null;
        int bid = w.a(x, y, z);
        boolean used = false;
        if (bid > 0 && Block.registry[bid] != null && Block.registry[bid].a(w, x, y, z, (Player)e)) used = true;
        openTileEntity(g, x, y, z);   // сундук/печь/раздатчик/табличка: отправить содержимое и следить за изменениями
        if (!used && held != null) held.a((Player)e, w, x, y, z, face);
        // Результат придёт гостю через слушатель блоков; на всякий случай досылаем соседний блок
        resync(g, x, y, z);
        int ox = x + (face == 4 ? -1 : face == 5 ? 1 : 0), oy = y + (face == 0 ? -1 : face == 1 ? 1 : 0), oz = z + (face == 2 ? -1 : face == 3 ? 1 : 0);
        resync(g, ox, oy, oz);
    }


    // ------------------------------------------------ предметы "в воздух" (лук, снежок, еда, лодка...)
    private void useInAir(Guest g, ed p) {
        ps e = g.entity;
        if (e == null || e.aN || e.J <= 0) return;
        InventoryItem held = e.e.storage[e.e.d];
        if (held == null || held.id != p.a) return;
        int count = held.count;
        InventoryItem res = held.a(world(), (Player)e);   // как iv.a(Player, Session, InventoryItem) в одиночной игре
        if (res != held || (res != null && res.count != count)) {
            e.e.storage[e.e.d] = res;
            if (res != null && res.count == 0) e.e.storage[e.e.d] = null;
        }
    }

    // ------------------------------------------------ tile entities: сундуки, печи, раздатчики, таблички (пакет 59)
    private void sendTile(Guest g, TileEntityRegistry t) {
        try {
            send(g, new py(t.f, t.g, t.h, t));
        } catch (RuntimeException ex) {
            // нестандартный tile entity без NBT — клиенту он не нужен
        }
    }

    private void sendChunkTiles(Guest g, ha chunk) {
        for (Object o : new ArrayList<Object>(chunk.l.values())) sendTile(g, (TileEntityRegistry)o);
    }

    /** Гость кликнул по блоку: если там (или рядом — двойной сундук) tile entity, отправить его и следить. */
    private void openTileEntity(Guest g, int x, int y, int z) {
        Session w = world();
        TileEntityRegistry t = w.b(x, y, z);
        if (t == null) return;
        g.hasOpen = true;
        g.openX = x; g.openY = y; g.openZ = z;
        g.openNbt = null;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                TileEntityRegistry n = w.b(x + dx, y, z + dz);
                if (n != null) sendTile(g, n);
            }
        }
        g.openNbt = serializeTile(t);
    }

    private byte[] serializeTile(TileEntityRegistry t) {
        try {
            return new py(t.f, t.g, t.h, t).d;
        } catch (RuntimeException ex) {
            return null;
        }
    }

    /** Раз в 5 тиков: содержимое открытого контейнера изменилось (хост, другой гость, печь горит) — переслать. */
    private void watchOpenTile(Guest g) {
        if (!g.hasOpen) return;
        lw e = g.entity;
        double dx = e.aw - (g.openX + 0.5), dz = e.ay - (g.openZ + 0.5);
        if (dx * dx + dz * dz > 100.0) { g.hasOpen = false; return; }
        TileEntityRegistry t = world().b(g.openX, g.openY, g.openZ);
        if (t == null) { g.hasOpen = false; return; }
        byte[] now = serializeTile(t);
        if (now != null && (g.openNbt == null || !java.util.Arrays.equals(now, g.openNbt))) {
            g.openNbt = now;
            sendTile(g, t);
        }
    }

    /** Гость изменил содержимое контейнера / текст таблички — применяем к настоящему tile entity хоста. */
    private void tileFromGuest(Guest g, py p) {
        if (g.entity == null || p.e == null) return;
        if (!near(g, p.a, p.b, p.c, 10.0)) return;
        TileEntityRegistry t = world().b(p.a, p.b, p.c);
        if (t == null) return;
        try {
            t.a(p.e);
            t.b();                                  // пометить чанк изменённым
        } catch (RuntimeException ex) {
            return;
        }
        if (g.hasOpen && g.openX == p.a && g.openY == p.b && g.openZ == p.c) g.openNbt = serializeTile(t);
        broadcastExcept(g, p);                      // остальные гости видят новое содержимое / текст таблички
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

    /**
     * Пакет 7 (UseEntity): флаг 1 — удар (левая кнопка, на клиенте это eb2.c(lw)), флаг 0 — "использовать"
     * (правая кнопка: сесть на свинью/в лодку, стричь и т.п., eb2.a_(lw)). Раньше удары обрабатывались как
     * "использование", поэтому по мобам и по хосту ударить было нельзя.
     */
    private void attack(Guest g, a u) {
        ps self = g.entity;
        if (self == null || self.aN || self.J <= 0) return;
        lw target = null;
        List all = world().b;
        for (int i = 0; i < all.size(); i++) {
            lw o = (lw)all.get(i);
            if (o.an == u.b) { target = o; break; }
        }
        if (target == null || target == self || target.aN) return;
        double dx = self.aw - target.aw, dy = (self.ax + 1.62) - (target.ax + (target instanceof ps ? 1.62 : 0.0)), dz = self.ay - target.ay;
        if (dx * dx + dy * dy + dz * dz > 49.0) return;     // дальше ~7 блоков — не бьём
        if (u.c == 1) {
            self.c(target);
            fs hurt = new fs();                              // хостовый моб/игрок дёрнулся — покажем всем гостям
            hurt.a = target.an; hurt.b = (byte)2;
        } else {
            self.a_(target);
        }
    }

    // ============================================ синхронизация сущностей мира
    /** Тип в пакете 23 (VehicleSpawn) или -1, если этот объект гостям не отправляется. */
    private static int vehicleType(lw e) {
        if (e instanceof Minecart) return 10 + ((Minecart)e).d;
        if (e instanceof Arrow) return 60;
        if (e instanceof Snowball) return 61;
        if (e instanceof Boat) return 1;
        if (e instanceof PrimedTNT) return 50;
        return -1;
    }

    private static boolean syncable(lw e) {
        return e instanceof DroppedItem || (e instanceof Mob && !(e instanceof Player)) || vehicleType(e) >= 0;
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
        } else if (vehicleType(e) >= 0) {
            lz v = new lz();
            v.a = e.an;
            v.b = (int)Math.floor(e.aw * 32.0);
            v.c = (int)Math.floor(e.ax * 32.0);
            v.d = (int)Math.floor(e.ay * 32.0);
            v.e = vehicleType(e);
            send(g, v);
            if (e instanceof Arrow || e instanceof Snowball) send(g, new dv(e));   // скорость выстрела
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
        List all = world().b;
        for (int i = 0; i < all.size(); i++) {
            lw e = (lw)all.get(i);
            if (e.aN) continue;
            if (e instanceof Mob && !(e instanceof Player)) {
                if (tickCount % 6 == 0) sendMove(null, e, e.ax);
                mobStatus((Mob)e);
            } else if (vehicleType(e) >= 0) {
                sendMove(null, e, e.ax);           // снаряды и транспорт — каждые 2 тика
            }
        }
    }

    private int hostHeld = 0;
    private boolean hostSwing = false, hostSneak = false;
    private final Map<Integer, Integer> heldSent = new HashMap<Integer, Integer>();

    /**
     * Что другие видят у игрока в руках и как он действует. В протоколе Alpha предмет в руке передаётся только
     * при появлении игрока (пакет 20), поэтому при смене предмета модель игрока у остальных пересоздаётся
     * (удаление + появление) — для этого достаточно доли секунды. Взмах руки и приседание хоста шлём пакетом 18.
     */
    private void syncPlayerLooks() {
        lw host = ctx.player();
        if (host instanceof Player) {
            Player hp = (Player)host;
            if (hp.j && !hostSwing) { ii sw = new ii(); sw.a = host.an; sw.b = 1; broadcast(sw); }
            hostSwing = hp.j;
            if (host.bw != hostSneak) {
                hostSneak = host.bw;
                ii sn = new ii(); sn.a = host.an; sn.b = hostSneak ? 104 : 105; broadcast(sn);
            }
            int now = heldId(host);
            if (now != hostHeld) {
                hostHeld = now;
                respawnLook(null, host, ctx.hostName(), host.aG.b, now);
            }
        }
        for (Guest g : guests) {
            if (g.phase != PHASE_PLAY || g.entity == null || g.entity.aN) continue;
            int now = heldId(g.entity);
            Integer was = heldSent.get(g.entity.an);
            if (was == null) { heldSent.put(g.entity.an, now); continue; }
            if (was.intValue() != now) {
                heldSent.put(g.entity.an, now);
                respawnLook(g, g.entity, g.name, g.entity.ax, now);
            }
        }
    }

    /** Пересоздать модель игрока у остальных гостей (кроме владельца) — с новым предметом в руке. */
    private void respawnLook(Guest owner, lw e, String name, double feetY, int held) {
        li gone = new li(); gone.a = e.an;
        hs s = spawnPlayerPacket(e.an, name, e.aw, feetY, e.ay, e.aC, e.aD, held);
        for (Guest g : guests) {
            if (g == owner || g.phase != PHASE_PLAY) continue;
            send(g, gone);
            send(g, s);
        }
        lastSent.remove(e.an);   // следующий пакет позиции уйдёт сразу
    }

    private final Map<Integer, int[]> mobState = new HashMap<Integer, int[]>();   // id -> {последний hurtTime, смерть отправлена}

    /** Анимации "ударили" (статус 2) и "умер" (статус 3) для мобов — чтобы гости видели, как моб получает урон. */
    private void mobStatus(Mob m) {
        int[] st = mobState.get(m.an);
        if (st == null) { st = new int[]{0, 0}; mobState.put(m.an, st); }
        if (m.M > st[0] && m.J > 0) {
            fs f = new fs(); f.a = m.an; f.b = (byte)2; broadcast(f);
        }
        st[0] = m.M;
        if (m.J <= 0 && st[1] == 0) {
            st[1] = 1;
            fs f = new fs(); f.a = m.an; f.b = (byte)3; broadcast(f);
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
                    g.entity.e.a(new InventoryItem(it.a.id, it.a.count, it.a.damage));   // зеркало инвентаря гостя на сервере
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

    private static InventoryItem[] copyItems(InventoryItem[] src) {
        InventoryItem[] out = new InventoryItem[src.length];
        for (int i = 0; i < src.length; i++) out[i] = src[i] == null ? null : new InventoryItem(src[i].id, src[i].count, src[i].damage);
        return out;
    }

    private void removeGuest(Guest g) {
        if (g.entity != null && g.phase == PHASE_PLAY && !g.entity.aN && g.entity.J > 0) {
            // запомнить вещи, здоровье и место — игрок может вернуться (тот же ник) в эту же сессию хоста
            Saved sv = new Saved();
            sv.storage = copyItems(g.entity.e.storage);
            sv.armor = copyItems(g.entity.e.b);
            sv.crafting = copyItems(g.entity.e.c);
            sv.health = g.entity.J;
            sv.x = g.entity.aw; sv.y = g.entity.aG.b; sv.z = g.entity.ay;
            saved.put(g.name.toLowerCase(), sv);
        }
        if (g.entity != null) {
            if (!g.entity.aN) g.entity.J();
            lastSent.remove(g.entity.an);
            if (g.phase == PHASE_PLAY) {
                li gone = new li(); gone.a = g.entity != null ? g.entity.an : -1;
                if (gone.a >= 0) broadcastExcept(g, gone);                                 // убрать модель игрока у остальных
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
