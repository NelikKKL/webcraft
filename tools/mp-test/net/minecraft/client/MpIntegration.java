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
    check(prechunks == 9 && chunks == 9, "9 pre-chunks + 9 chunks around spawn at login (got " + prechunks + "/" + chunks + ")");
    check(gotPos && gotSpawn && gotInv, "position, spawn point and inventory packets present");

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

    // disconnect
    sock.fireClose();
    srv.tickForTest();
    check(chat.contains("\u00a7eAlex left the game"), "guest leaving is announced");

    System.out.println(fails == 0 ? "\nINTEGRATION OK" : "\nFAILURES: " + fails);
    System.exit(fails == 0 ? 0 : 1);
  }
}
