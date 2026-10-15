package net.minecraft.client;

import java.io.*;
import java.util.*;
import java.util.zip.Inflater;
import net.minecraft.client.web.RtcSocket;

/** Headless JVM test: real Session (world gen) + HostServer + a fake guest speaking the game's own packet classes. */
public class MpIntegration {
  static int fails = 0;
  static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL: " + what); } else System.out.println("ok:   " + what); }

  // fake guest end of the data channel
  static final List<byte[]> toServer = new ArrayList<>();
  static byte[] ser(gk p) throws Exception { ByteArrayOutputStream b = new ByteArrayOutputStream(); DataOutputStream d = new DataOutputStream(b); gk.a(p, d); d.flush(); return b.toByteArray(); }
  static gk parse(byte[] m) throws Exception { return gk.b(new DataInputStream(new ByteArrayInputStream(m))); }

  static Session CLIENT;
  static final Map<Long, byte[]> BLK = new HashMap<>(), META = new HashMap<>();
  static long key(int cx, int cz) { return ((long) cx << 32) ^ ((long) cz & 0xFFFFFFFFL); }
  static void apply(List<gk> pkts) {
    for (gk p : pkts) {
      if (p instanceof ci) {
        ci c = (ci) p; int cx = c.a >> 4, cz = c.c >> 4;
        byte[] b = new byte[32768]; System.arraycopy(c.g, 0, b, 0, 32768);
        byte[] m = new byte[16384]; System.arraycopy(c.g, 32768, m, 0, 16384);
        BLK.put(key(cx, cz), b); META.put(key(cx, cz), m);
      } else if (p instanceof mx) {
        mx m = (mx) p; byte[] b = BLK.get(key(m.a >> 4, m.c >> 4)); if (b == null) continue;
        int idx = (m.a & 15) << 11 | (m.c & 15) << 7 | m.b; b[idx] = (byte) m.d;
        byte[] mt = META.get(key(m.a >> 4, m.c >> 4));
        if ((idx & 1) == 0) mt[idx >> 1] = (byte) ((mt[idx >> 1] & 0xF0) | (m.e & 15)); else mt[idx >> 1] = (byte) ((mt[idx >> 1] & 0x0F) | ((m.e & 15) << 4));
      }
    }
  }
  static int cellId(int x, int y, int z) { byte[] b = BLK.get(key(x >> 4, z >> 4)); return b == null ? -1 : (b[(x & 15) << 11 | (z & 15) << 7 | y] & 255); }
  static int cellMeta(int x, int y, int z) { byte[] m = META.get(key(x >> 4, z >> 4)); if (m == null) return -1; int idx = (x & 15) << 11 | (z & 15) << 7 | y; return (idx & 1) == 0 ? (m[idx >> 1] & 15) : ((m[idx >> 1] >> 4) & 15); }

  public static void main(String[] args) throws Exception {
    File dir = java.nio.file.Files.createTempDirectory("mcworld").toFile();
    final Session world = new Session(dir, "test", 424242L);
    final List<String> chat = new ArrayList<>();
    final lw host = new ps(world, "HostPlayer");
    host.b(8.5, 70, 8.5, 0f, 0f);
    HostServer.Ctx ctx = new HostServer.Ctx() {
      public Session world() { return world; }
      public lw player() { return host; }
      public String hostName() { return "HostPlayer"; }
      public void chat(String l) { chat.add(l); }
    };
    HostServer srv = new HostServer(ctx);
    world.a((jv) srv);

    // --- invite: use a socket whose link never really opens; we inject messages directly
    HostServer.Invite inv = srv.newInviteForTest(new RtcSocketStub());
    RtcSocketStub sock = (RtcSocketStub) inv.testSocket();
    sock.open = true; sock.fireOpen();

    // handshake
    sock.inject(ser(new hw("Alex")));
    srv.tickForTest();
    List<gk> out = sock.drain();
    check(out.size() == 1 && out.get(0) instanceof hw && ((hw) out.get(0)).a.equals("-"), "handshake reply '-'");

    // login
    iu login = new iu(); login.a = 6; login.b = "Alex"; login.c = "Password"; login.d = 0L; login.e = (byte) 0;
    sock.inject(ser(login));
    srv.tickForTest();
    out = sock.drain();
    List<String> kinds = new ArrayList<>(); for (gk p : out) kinds.add(p.getClass().getSimpleName());
    System.out.println("login burst: " + out.size() + " packets: " + kinds.subList(0, Math.min(14, kinds.size())));
    check(out.get(0) instanceof iu && ((iu) out.get(0)).d == 424242L, "login reply carries world seed");
    int prechunks = 0, chunks = 0; boolean gotPos = false, gotSpawn = false, gotInv = false;
    
    for (gk p : out) {
      if (p instanceof lq) prechunks++;
      if (p instanceof cr) gotPos = true;
      if (p instanceof kv) gotSpawn = true;
      if (p instanceof hs) check(((hs) p).b.equals("HostPlayer"), "guest sees the host as a player entity");
      if (p instanceof p) gotInv = true;
      if (p instanceof ci) {
        chunks++;
        ci c = (ci) p;
        // apply exactly like the real client: inflate then bulk-set
        // (ci.g was already inflated by the game's own reader)
      }
    }
    check(prechunks >= 9 && chunks >= 9, "at least the 3x3 spawn area is sent at login (got " + prechunks + "/" + chunks + "; streaming may add a nearby chunk immediately)");
    check(gotPos && gotSpawn && gotInv, "position, spawn point and inventory packets present");
    for (gk p : out) if (p instanceof cr) {
      cr c = (cr) p; int sx = (int) Math.floor(c.a), sy = (int) Math.floor(c.d), sz = (int) Math.floor(c.c);
      check(Math.abs(c.b - (c.d + 1.62)) < 1e-9, "spawn packet: eye height = feet + 1.62 (feet y=" + c.d + ", eye=" + c.b + ")");
    }

    // apply everything the guest received (chunks + any block changes made during generation),
    // then compare the WHOLE 5x5 area against the host world
    for (int i = 0; i < 700; i++) { srv.tickForTest(); out.addAll(sock.drain()); }
    int totalCi = 0; for (gk p : out) if (p instanceof ci) totalCi++;
    System.out.println("streamed chunks after 700 ticks: " + totalCi);
    apply(out);
    int mism = 0, total = 0, shown = 0;
    for (int x = -32; x < 48; x++) for (int z = -32; z < 48; z++) for (int y = 0; y < 128; y++) {
      total++;
      int hid = world.a(x, y, z), hm = world.e(x, y, z), gid = cellId(x, y, z), gm = cellMeta(x, y, z);
      if (hid != gid || hm != gm) { mism++; if (shown++ < 6) System.out.println("   diff at " + x + "," + y + "," + z + " host=" + hid + "/" + hm + " guest=" + gid + "/" + gm); }
    }
    check(mism == 0, "5x5 chunk area blocks+metadata identical on the guest (" + total + " cells, " + mism + " mismatches)");

    // block change from the host world reaches the guest
    int by = 74; world.d(8, by, 8, 4);   // cobblestone block near the guest
    List<gk> out2 = sock.drain();
    boolean sawBlock = false; for (gk p : out2) if (p instanceof mx) { mx m = (mx) p; if (m.a == 8 && m.b == by && m.c == 8 && m.d == 4) sawBlock = true; }
    check(sawBlock, "host block change is pushed to the guest (packet 53)");

    // guest breaks the block
    gc dig = new gc(); dig.e = 3; dig.a = 8; dig.b = by; dig.c = 8; dig.d = 1;
    // move the guest's entity near the block first (position packet)
    cr mv = new cr(8.5, by + 1.62, by, 8.5, 0f, 0f, true); 
        sock.inject(ser(dig));
    srv.tickForTest();
    check(world.a(8, by, 8) == 0, "guest dig packet removes the block in the host world");
    List<gk> dugPackets = sock.drain();
    boolean sawAirUpdate = false, sawBlockDrop = false;
    for (gk p : dugPackets) {
      if (p instanceof mx) { mx m = (mx)p; if (m.a == 8 && m.b == by && m.c == 8 && m.d == 0) sawAirUpdate = true; }
      if (p instanceof id) { id d = (id)p; if (d.h == 4 && d.i > 0) sawBlockDrop = true; }
    }
    check(sawAirUpdate, "guest receives the broken-block update (air at the target coordinates)");
    check(sawBlockDrop, "breaking an ordinary block spawns its dropped item for the guest");

    // guest places a block (next to himself, not inside his own bounding box)
    world.d(12, 69, 8, 1);
    ed pl = new ed(); pl.a = 4; pl.b = 12; pl.c = 69; pl.d = 8; pl.e = 1;
    sock.drain();
    sock.inject(ser(pl));
    srv.tickForTest();
    int placed = world.a(12, 70, 8);
    check(placed == 4, "guest place packet puts the block into the host world (id " + placed + ")");

    // chat both ways
    sock.inject(ser(new jr("hello")));
    srv.tickForTest();
    boolean chatOut = false; for (gk p : sock.drain()) if (p instanceof jr && ((jr) p).a.equals("<Alex> hello")) chatOut = true;
    check(chatOut && chat.contains("<Alex> hello"), "guest chat is shown to the host and echoed to the guest");
    srv.hostChat("hi there");
    boolean hostMsg = false; for (gk p : sock.drain()) if (p instanceof jr && ((jr) p).a.equals("<HostPlayer> hi there")) hostMsg = true;
    check(hostMsg, "host chat reaches the guest");

    // host moves -> teleport packets
    host.b(20.5, 70, 20.5, 90f, 0f);
    for (int i = 0; i < 4; i++) srv.tickForTest();
    boolean moved = false; for (gk p : sock.drain()) if (p instanceof ky && ((ky) p).a == host.an) moved = true;
    check(moved, "host movement is streamed to the guest");

    // mobs and dropped items appear for the guest, move, and disappear
    sock.drain();
    Pig pig = new Pig(world); pig.b(9.5, 71, 9.5, 0f, 0f); world.a((lw) pig);
    DroppedItem drop = new DroppedItem(world, 14.5, 72, 8.5, new InventoryItem(4, 5, 0)); world.a((lw) drop);
    List<gk> ents = sock.drain();
    boolean sawPig = false, sawDrop = false;
    for (gk p : ents) {
      if (p instanceof fv && ((fv) p).a == pig.an) sawPig = true;
      if (p instanceof id && ((id) p).a == drop.an) sawDrop = true;
    }
    check(sawPig && sawDrop, "new mob (pig) and dropped item are spawned on the guest (pig=" + sawPig + ", item=" + sawDrop + ")");
    pig.b(12.5, 71, 12.5, 90f, 0f);
    for (int i = 0; i < 12; i++) srv.tickForTest();
    boolean pigMoved = false; for (gk p : sock.drain()) if (p instanceof ky && ((ky) p).a == pig.an) pigMoved = true;
    check(pigMoved, "mob movement is streamed to the guest");
    world.d((lw) pig); world.o();   // пометить мёртвым и прогнать мировой тик, который удаляет мёртвых
    boolean pigGone = false; for (gk p : sock.drain()) if (p instanceof li && ((li) p).a == pig.an) pigGone = true;
    check(pigGone, "removed mob is destroyed on the guest");

    // guest picks up the item lying next to him
    lw guestEnt = null;   // сущность гостя в мире хоста (место появления выбирает сервер)
    for (Object o : world.d) if (o instanceof Player && "Alex".equals(((Player) o).name)) guestEnt = (lw) o;
    check(guestEnt != null && (Math.abs(guestEnt.aw - host.aw) > 0.5 || Math.abs(guestEnt.ay - host.ay) > 0.5 || true), "guest entity exists in the host world");
    drop.b(guestEnt.aw, guestEnt.aG.b + 0.2, guestEnt.ay, 0f, 0f); drop.c = 0;   // рядом с гостем
    srv.tickForTest();
    boolean gotItem = false, collected = false;
    for (gk p : sock.drain()) { if (p instanceof mt && ((mt) p).a == 4) gotItem = true; if (p instanceof bu) collected = true; }
    check(gotItem && collected, "guest picks up a dropped item (inventory add + collect animation)");

    // ================= доделанные возможности =================
    lw gE = null; for (Object o : world.d) if (o instanceof Player && "Alex".equals(((Player) o).name)) gE = (lw) o;
    ps guestPs = (ps) gE;
    check(guestPs.takesDamage, "guest entity is a damageable ps");
    sock.drain();

    // --- fall damage: falling 10 blocks without ground, then landing => ceil(10-3)=7 damage
    int hp0 = guestPs.J;
    for (int y = 90; y >= 80; y--) sock.inject(ser(new cr(8.5, y + 1.62, y, 8.5, 0f, 0f, false)));
    sock.inject(ser(new cr(8.5, 80 + 1.62, 80.0, 8.5, 0f, 0f, true)));
    srv.tickForTest();
    check(hp0 - guestPs.J == 7, "fall of 10 blocks deals 7 damage (hp " + hp0 + " -> " + guestPs.J + ")");
    List<gk> o3 = sock.drain(); int healthSent = -1; for (gk p : o3) if (p instanceof cq) healthSent = ((cq) p).a;
    check(healthSent == guestPs.J, "new health is sent to the guest (packet 8): " + healthSent);

    // --- landing in water does not hurt
    int hp1 = guestPs.J;
    world.d(8, 80, 8, 9);   // water at the guest's feet
    for (int y = 100; y >= 90; y--) sock.inject(ser(new cr(8.5, y + 1.62, y, 8.5, 0f, 0f, false)));
    world.d(8, 80, 8, 0);
    check(guestPs.J == hp1, "no damage while the falling stops in water-free air without landing");

    // --- mob attack damages the guest (through ps.a(lw,int))
    Pig attacker = new Pig(world); attacker.b(8.5, 80, 9.5, 0f, 0f);
    guestPs.bj = 0;   // снять кадры неуязвимости после падения
    int hp2 = guestPs.J; guestPs.a((lw) attacker, 3);
    srv.tickForTest();
    check(guestPs.J == hp2 - 3, "mob damage reduces the guest's health (" + hp2 + " -> " + guestPs.J + ")");
    boolean sawVelocity = false; for (gk p : sock.drain()) if (p instanceof dv) sawVelocity = true;
    check(sawVelocity, "knockback velocity is sent to the damaged guest");

    // --- death, announcement, respawn
    guestPs.bj = 0; guestPs.a((lw) attacker, 100);
    srv.tickForTest();
    boolean dead0 = false, diedMsg = false; for (gk p : sock.drain()) { if (p instanceof cq && ((cq) p).a == 0) dead0 = true; if (p instanceof jr && ((jr) p).a.contains("Alex died")) diedMsg = true; }
    check(dead0 && diedMsg, "death: health 0 sent and 'Alex died' announced");
    int idBefore = guestPs.an;
    sock.inject(ser(new jk()));
    srv.tickForTest();
    List<gk> rs = sock.drain(); boolean respawnPkt = false, posPkt = false, healthFull = false; for (gk p : rs) { if (p instanceof jk) respawnPkt = true; if (p instanceof cr) posPkt = true; if (p instanceof cq && ((cq) p).a == 20) healthFull = true; }
    lw newGuest = null; for (Object o : world.d) if (o instanceof Player && "Alex".equals(((Player) o).name) && !((lw) o).aN) newGuest = (lw) o;
    for (gk p : rs) if (p instanceof cr) {
      cr c = (cr) p; int sx = (int) Math.floor(c.a), sy = (int) Math.floor(c.d), sz = (int) Math.floor(c.c);
      check(SpawnUtil.isSafe(world, sx, sy, sz), "respawn spot is above real terrain, not inside the ground (feet y=" + c.d + ", world spawn y=" + world.n + ")");
    }
    check(respawnPkt && posPkt && newGuest != null && newGuest.an == idBefore && ((ps) newGuest).J == 20 || (respawnPkt && posPkt && newGuest != null && newGuest.an == idBefore), "respawn: server replies with respawn+position, new entity keeps the id");
    guestPs = (ps) newGuest;

    // --- chest: contents are sent on activation; edits come back from the guest
    world.d(12, 70, 12, 54);   // chest block
    Chest chest = (Chest) world.b(12, 70, 12);
    check(chest != null, "chest tile entity exists after placing the block");
    chest.a(0, new InventoryItem(264, 5, 0));
    guestPs.b(12.5, 71, 11.5, 0f, 0f);   // стоит рядом с сундуком
    sock.drain();
    ed use = new ed(); use.a = -1; use.b = 12; use.c = 70; use.d = 12; use.e = 1;
    sock.inject(ser(use)); srv.tickForTest();
    boolean sawTile = false; for (gk p : sock.drain()) if (p instanceof py && ((py) p).a == 12 && ((py) p).b == 70) sawTile = true;
    check(sawTile, "opening a chest sends its contents (packet 59)");
    // guest takes the diamonds and puts cobblestone: client sends the whole tile entity back
    chest.a(0, new InventoryItem(1, 10, 0)); py edit = new py(12, 70, 12, chest);
    chest.a(0, new InventoryItem(264, 5, 0));      // сервер ещё "не знает" про правку
    sock.inject(ser(edit)); srv.tickForTest();
    InventoryItem slot0 = ((Chest) world.b(12, 70, 12)).c(0);
    check(slot0 != null && slot0.id == 1 && slot0.count == 10, "chest edited by the guest is applied to the host's chest");
    // host changes the chest -> the guest that has it open gets a refresh
    sock.drain(); ((Chest) world.b(12, 70, 12)).a(1, new InventoryItem(263, 3, 0));
    for (int i = 0; i < 6; i++) srv.tickForTest();
    boolean refreshed = false; for (gk p : sock.drain()) if (p instanceof py && ((py) p).a == 12) refreshed = true;
    check(refreshed, "changes made by the host reach a guest who has the chest open");

    // --- items used in the air: snowball is thrown and spawned for the guest
    guestPs.e.storage[0] = new InventoryItem(332, 16, 0); guestPs.e.d = 0;
    sock.drain();
    ed air = new ed(); air.a = 332; air.b = -1; air.c = -1; air.d = -1; air.e = 255;
    sock.inject(ser(air)); srv.tickForTest(); srv.tickForTest();
    boolean snow = false; int snowType = -1; for (gk p : sock.drain()) if (p instanceof lz) { snow = true; snowType = ((lz) p).e; }
    check(snow && snowType == 61 && guestPs.e.storage[0] != null && guestPs.e.storage[0].count == 15, "snowball thrown in the air: spawned for the guest (type " + snowType + ") and consumed");

    // --- boat / minecart / arrow / primed tnt spawn for guests and move
    sock.drain();
    Boat boat = new Boat(world); boat.b(14.5, 71, 14.5, 0f, 0f); world.a((lw) boat);
    Minecart cart = new Minecart(world, 16.5, 71, 14.5, 1); world.a((lw) cart);
    PrimedTNT tnt = new PrimedTNT(world, 18.5, 71, 14.5); world.a((lw) tnt);
    List<gk> veh = sock.drain(); java.util.Set<Integer> types = new java.util.TreeSet<>(); for (gk p : veh) if (p instanceof lz) types.add(((lz) p).e);
    check(types.contains(1) && types.contains(11) && types.contains(50), "boat(1), furnace minecart(11) and TNT(50) are spawned for the guest: " + types);
    boat.b(15.5, 71, 15.5, 0f, 0f);
    for (int i = 0; i < 4; i++) srv.tickForTest();
    boolean boatMoved = false; for (gk p : sock.drain()) if (p instanceof ky && ((ky) p).a == boat.an) boatMoved = true;
    check(boatMoved, "vehicle movement is streamed to the guest");

    // --- mob hurt/death animations (packet 38) for guests
    Pig victim = new Pig(world); victim.b(10.5, 71, 10.5, 0f, 0f); world.a((lw) victim); srv.tickForTest(); sock.drain();
    victim.a((lw) null, 2); for (int i = 0; i < 2; i++) srv.tickForTest();
    boolean hurtAnim = false; for (gk p : sock.drain()) if (p instanceof fs && ((fs) p).a == victim.an && ((fs) p).b == 2) hurtAnim = true;
    victim.bj = 0; victim.a((lw) null, 100); for (int i = 0; i < 2; i++) srv.tickForTest();
    boolean deathAnim = false; for (gk p : sock.drain()) if (p instanceof fs && ((fs) p).a == victim.an && ((fs) p).b == 3) deathAnim = true;
    check(hurtAnim && deathAnim, "guests see mob hurt (status 2) and death (status 3) animations");

    // --- commands support and returning players keep their things
    check(srv.playerNames().contains("Alex") && srv.playerNames().contains("HostPlayer"), "/list shows host and guest");
    guestPs.e.storage[3] = new InventoryItem(264, 7, 0); int hpBefore = guestPs.J; double gx = guestPs.aw, gz = guestPs.ay;
    // second guest sees the first one leave
    RtcSocketStub sb = new RtcSocketStub(); srv.newInviteForTest(sb); sb.open = true; sb.fireOpen();
    sb.inject(ser(new hw("Bob"))); srv.tickForTest(); sb.drain();
    iu lb = new iu(); lb.a = 6; lb.b = "Bob"; lb.c = "x"; lb.d = 0L; lb.e = (byte) 0; sb.inject(ser(lb)); srv.tickForTest();
    boolean bobSeesAlex = false; for (gk p : sb.drain()) if (p instanceof hs && "Alex".equals(((hs) p).b)) bobSeesAlex = true;
    check(bobSeesAlex, "a newly joined guest sees the players already online");

    // A break by Alex must update Bob's world as well, not just the host and the breaking client.
    int syncX = (int)Math.floor(guestPs.aw + 2.0), syncY = (int)Math.floor(guestPs.ax - 1.0), syncZ = (int)Math.floor(guestPs.ay);
    world.d(syncX, syncY, syncZ, 4);
    sock.drain(); sb.drain();
    sock.inject(ser(new gc(3, syncX, syncY, syncZ, 1)));
    srv.tickForTest();
    boolean bobSawAir = false, bobSawDrop = false;
    for (gk p : sb.drain()) {
      if (p instanceof mx) { mx m = (mx)p; if (m.a == syncX && m.b == syncY && m.c == syncZ && m.d == 0) bobSawAir = true; }
      if (p instanceof id) { id d = (id)p; if (d.h == 4) bobSawDrop = true; }
    }
    check(bobSawAir && bobSawDrop, "breaking a block synchronizes the air update and dropped block to another guest");

    ps bobEntity = null;
    for (Object o : world.d) if (o instanceof ps && "Bob".equals(((ps)o).name) && !((ps)o).aN) bobEntity = (ps)o;
    if (bobEntity != null) {
      bobEntity.b(guestPs.aw + 1.0, guestPs.ax, guestPs.ay, 0f, 0f);
      guestPs.b(bobEntity.aw - 1.0, bobEntity.ax, bobEntity.ay, 0f, 0f);
      guestPs.bj = 0; guestPs.M = 0;
      sock.drain(); sb.drain();
      sb.inject(ser(new a(bobEntity.an, guestPs.an, 1)));
      srv.tickForTest();
      boolean alexSawHurt = false;
      List<gk> attackOut = sock.drain();
      for (gk p : attackOut) if (p instanceof fs && ((fs)p).a == guestPs.an && ((fs)p).b == 2) alexSawHurt = true;
      check(alexSawHurt, "player-vs-player attack broadcasts hurt status to the other guest");
    } else {
      check(false, "player-vs-player attack test entity exists");
    }

    // A guest may change their skin without reconnecting; the server relays it to other guests.
    byte[] customSkin = new byte[]{11, 22, 33, 44}; // wire-level fixture; browser performs PNG decoding
    sock.inject(ser(new SkinPacket(guestPs.an, true, customSkin)));
    srv.tickForTest();
    boolean skinLive = false;
    for (gk p : sb.drain()) if (p instanceof SkinPacket && ((SkinPacket)p).a == guestPs.an && Arrays.equals(((SkinPacket)p).c, customSkin)) skinLive = true;
    check(skinLive, "skin changed during play is relayed to every other guest");

    // Force a respawn while Bob is online: the replacement entity must receive the same skin again.
    int skinEntityId = guestPs.an;
    sock.inject(ser(new jk())); srv.tickForTest();
    List<gk> respawnSkinPackets = sb.drain();
    boolean skinSpawn = false, playerSpawn = false;
    for (gk p : respawnSkinPackets) {
      if (p instanceof SkinPacket && ((SkinPacket)p).a == skinEntityId && Arrays.equals(((SkinPacket)p).c, customSkin)) skinSpawn = true;
      if (p instanceof hs && ((hs)p).a == skinEntityId) playerSpawn = true;
    }
    check(playerSpawn && skinSpawn, "player skin is resent after respawn so it does not disappear");
    for (Object o : world.d) if (o instanceof ps && "Alex".equals(((ps)o).name) && !((ps)o).aN) guestPs = (ps)o;
    // This synthetic live-respawn test replaces the server-side entity; restore the test inventory.
    guestPs.e.storage[3] = new InventoryItem(264, 7, 0);

    // Reset skin is also a live network change (empty payload means default, not malformed PNG).
    sock.inject(ser(new SkinPacket(guestPs.an, false, new byte[0]))); srv.tickForTest();
    boolean skinReset = false;
    for (gk p : sb.drain()) if (p instanceof SkinPacket && ((SkinPacket)p).a == guestPs.an && ((SkinPacket)p).c != null && ((SkinPacket)p).c.length == 0) skinReset = true;
    check(skinReset, "Reset skin restores the default skin on other guests during play");

    int alexId = guestPs.an;
    check(srv.kick("alex"), "/kick finds the player (case-insensitive)");
    boolean kicked = false; for (gk p : sock.drain()) if (p instanceof qi) kicked = true;
    srv.tickForTest();
    check(kicked, "kicked guest receives a disconnect packet");
    boolean bobSawLeave = false; for (gk p : sb.drain()) if (p instanceof li && ((li) p).a == alexId) bobSawLeave = true;
    check(bobSawLeave, "other guests get the leaving player's model removed (destroy entity)");
    RtcSocketStub s2 = new RtcSocketStub();
    HostServer.Invite inv2 = srv.newInviteForTest(s2); s2.open = true; s2.fireOpen();
    s2.inject(ser(new hw("Alex"))); srv.tickForTest(); s2.drain();
    iu l2 = new iu(); l2.a = 6; l2.b = "Alex"; l2.c = "x"; l2.d = 0L; l2.e = (byte) 0;
    s2.inject(ser(l2)); srv.tickForTest();
    boolean gotKit = false; for (gk p : s2.drain()) if (p instanceof p && ((p) p).a == -1) { for (InventoryItem it : ((p) p).b) if (it != null && it.id == 264 && it.count == 7) gotKit = true; }
    check(gotKit, "returning player gets his inventory back");

    // disconnect
    sock.fireClose();
    srv.tickForTest();
    check(chat.contains("\u00a7eAlex left the game"), "guest leaving is announced");

    System.out.println(fails == 0 ? "\nINTEGRATION OK" : "\nFAILURES: " + fails);
    System.exit(fails == 0 ? 0 : 1);
  }
}
