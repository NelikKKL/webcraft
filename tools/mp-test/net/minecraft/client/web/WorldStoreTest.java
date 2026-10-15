package net.minecraft.client.web;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.client.Session;

/** Круг «сохранили мир -> перезагрузка (новая папка, файлы из «IndexedDB») -> мир на месте» без браузера. */
public class WorldStoreTest {
  static int fails = 0;
  static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL: " + what); } else System.out.println("ok:   " + what); }

  public static void main(String[] args) throws Exception {
    File base1 = Files.createTempDirectory("wsA").toFile();
    WorldStore.testSetup(base1);
    Session w = new Session(new File(base1, "saves"), "W1", 777L);
    for (int cx = -1; cx <= 1; cx++) for (int cz = -1; cz <= 1; cz++) w.c(cx, cz);
    w.d(5, 100, 5, 4);                  // игрок «построил» блок (id 4) в воздухе
    int seedBefore = 777;
    w.a(0);
    w.a(true, null);
    ArrayList<String> removed = new ArrayList<String>();
    Map<String, byte[]> files = WorldStore.drain(removed);
    int chunks = 0; boolean level = false, lock = false, tmp = false;
    for (String k : files.keySet()) {
      if (k.endsWith("level.dat")) level = true;
      if (k.endsWith("session.lock")) lock = true;
      if (k.endsWith("tmp_chunk.dat") || k.endsWith("level.dat_new") || k.endsWith("level.dat_old")) tmp = true;
      if (k.contains("/c.") && k.endsWith(".dat")) chunks++;
    }
    check(level, "level.dat is queued for IndexedDB");
    check(chunks >= 1, "chunk files are queued (" + chunks + ")");
    check(!lock && !tmp, "session.lock and temp files are not stored");
    check(files.keySet().iterator().next().startsWith("saves/W1/"), "keys are relative to the game folder: " + files.keySet().iterator().next());

    // «перезагрузка»: пустая папка игры, файлы возвращаются из «IndexedDB»
    File base2 = Files.createTempDirectory("wsB").toFile();
    WorldStore.testSetup(base2);
    for (Map.Entry<String, byte[]> e : files.entrySet()) WorldStore.writeFile(e.getKey(), e.getValue());
    Session w2 = new Session(new File(base2, "saves"), "W1", 1L);
    check(!w2.p, "restored world is loaded as existing (not regenerated)");
    check(w2.u == 777L, "world seed survived the reload (" + w2.u + ")");
    check(w2.a(5, 100, 5) == 4, "block placed before the reload is still there");

    // удаление мира
    WorldStore.testSetup(base2);
    Session.b(base2, "W1");
    removed = new ArrayList<String>();
    WorldStore.drain(removed);
    check(removed.contains("saves/W1/"), "deleting a world removes it from IndexedDB too " + removed);

    System.out.println(fails == 0 ? "WORLDSTORE OK" : ("WORLDSTORE FAILURES: " + fails));
    System.exit(fails == 0 ? 0 : 1);
  }
}
