package net.minecraft.client.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.ArrayBuffer;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Сохранение миров между перезагрузками вкладки.
 *
 * saves/ лежит в виртуальной файловой системе TeaVM (в памяти). Здесь она зеркалится в IndexedDB
 * (web/extras.js -> window.WorldDB):
 *  - при старте restore() выкладывает файлы из IndexedDB обратно в saves/ — до создания игры;
 *  - места записи (сохранение чанка, level.dat) сообщают changed(file); flush() пишет накопленное одной
 *    транзакцией (по таймеру в WebEntryPoint и при уходе со страницы);
 *  - удаление мира — deletedTree(dir).
 * session.lock и временные файлы не сохраняются.
 */
public final class WorldStore {

    private WorldStore() {}

    @JSFunctor interface FileCb extends JSObject { void call(String path, Uint8Array data); }
    @JSFunctor interface DoneCb extends JSObject { void call(boolean ok); }
    @JSFunctor interface ExitCb extends JSObject { void call(); }

    /** Пути относительно папки игры (например "saves/World1/level.dat"). */
    private static final LinkedHashSet<String> dirty = new LinkedHashSet<String>();
    private static final ArrayList<String> removedPrefixes = new ArrayList<String>();
    private static boolean enabled = false;
    private static File baseOverride = null;   // для JVM-теста

    // ------------------------------------------------------------ пути

    private static File base() {
        return baseOverride != null ? baseOverride : Minecraft.b();
    }

    static boolean skip(String name) {
        return name.equals("session.lock") || name.equals("tmp_chunk.dat")
            || name.equals("level.dat_new") || name.equals("level.dat_old");
    }

    /** Ключ файла относительно папки игры или null, если файл не из saves/ или не сохраняется. */
    static String keyOf(File f) {
        String root = base().getAbsolutePath().replace('\\', '/');
        String p = f.getAbsolutePath().replace('\\', '/');
        if (!root.endsWith("/")) root += "/";
        if (!p.startsWith(root)) return null;
        String key = p.substring(root.length());
        if (!key.startsWith("saves/")) return null;
        if (skip(f.getName())) return null;
        return key;
    }

    // ------------------------------------------------------------ учёт изменений

    /** Файл записан (или переименован на место) — запомнить для сохранения в IndexedDB. */
    public static void changed(File f) {
        if (!enabled) return;
        try {
            String key = keyOf(f);
            if (key != null) dirty.add(key);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /** Мир (папка) удаляется целиком. */
    public static void deletedTree(File dir) {
        if (!enabled) return;
        try {
            String root = base().getAbsolutePath().replace('\\', '/');
            String p = dir.getAbsolutePath().replace('\\', '/');
            if (!root.endsWith("/")) root += "/";
            if (!p.startsWith(root)) return;
            String prefix = p.substring(root.length()) + "/";
            if (!prefix.startsWith("saves/")) return;
            for (Iterator<String> it = dirty.iterator(); it.hasNext();) {
                if (it.next().startsWith(prefix)) it.remove();
            }
            removedPrefixes.add(prefix);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static boolean hasPending() { return !dirty.isEmpty() || !removedPrefixes.isEmpty(); }

    // ------------------------------------------------------------ чтение/запись файлов

    static byte[] readAll(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream((int)Math.max(32, f.length()));
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
            return out.toByteArray();
        } finally {
            in.close();
        }
    }

    static void writeFile(String key, byte[] data) throws IOException {
        File f = new File(base(), key);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        FileOutputStream out = new FileOutputStream(f);
        try {
            out.write(data);
        } finally {
            out.close();
        }
    }

    /** Забрать накопленные изменения как {путь -> байты (null — файл удалён)} и префиксы удалённых миров. */
    static Map<String, byte[]> drain(ArrayList<String> removedOut) {
        LinkedHashMap<String, byte[]> out = new LinkedHashMap<String, byte[]>();
        removedOut.addAll(removedPrefixes);
        removedPrefixes.clear();
        ArrayList<String> keys = new ArrayList<String>(dirty);
        dirty.clear();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            File f = new File(base(), key);
            try {
                out.put(key, f.exists() ? readAll(f) : null);
            } catch (IOException e) {
                e.printStackTrace();
                dirty.add(key);   // попробуем в следующий раз
            }
        }
        return out;
    }

    // ------------------------------------------------------------ IndexedDB

    /** Записать накопленные изменения в IndexedDB (одна транзакция). */
    public static void flush() {
        if (!enabled || !hasPending()) return;
        try {
            ArrayList<String> removed = new ArrayList<String>();
            Map<String, byte[]> files = drain(removed);
            for (int i = 0; i < removed.size(); i++) nativeRemovePrefix(removed.get(i));
            for (Map.Entry<String, byte[]> e : files.entrySet()) {
                byte[] data = e.getValue();
                if (data == null) {
                    nativeRemove(e.getKey());
                } else {
                    Uint8Array arr = new Uint8Array(new ArrayBuffer(data.length));
                    arr.set(data, 0);
                    nativePut(e.getKey(), arr);
                }
            }
            nativeCommit();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /** Выложить сохранённые миры из IndexedDB в saves/ и только потом вызвать onDone. Вызывать до создания игры. */
    public static void restore(final Runnable onDone) {
        final int[] count = {0};
        FileCb onFile = (path, data) -> {
            if (path == null || !path.startsWith("saves/") || path.contains("..")) return;
            try {
                int n = data.getLength();
                byte[] bytes = new byte[n];
                for (int i = 0; i < n; i++) bytes[i] = (byte)data.get(i);
                writeFile(path, bytes);
                count[0]++;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        DoneCb done = ok -> {
            enabled = true;   // с этого момента изменения отслеживаются (restore сам ничего не помечает)
            System.out.println("WorldStore: restored " + count[0] + " files" + (ok ? "" : " (storage unavailable)"));
            onDone.run();
        };
        nativeLoad(onFile, done);
    }

    /** JS (pagehide / вкладка скрыта) просит сохранить мир прямо сейчас. */
    public static void installExitHook(final Runnable save) {
        ExitCb cb = () -> save.run();
        nativeInstallExit(cb);
    }

    @JSBody(params = { "onFile", "onDone" }, script =
        "if (window.WorldDB) { window.WorldDB.load(onFile, onDone); } else { onDone(false); }")
    private static native void nativeLoad(FileCb onFile, DoneCb onDone);

    @JSBody(params = { "path", "bytes" }, script = "if (window.WorldDB) window.WorldDB.put(path, bytes);")
    private static native void nativePut(String path, Uint8Array bytes);

    @JSBody(params = { "path" }, script = "if (window.WorldDB) window.WorldDB.remove(path);")
    private static native void nativeRemove(String path);

    @JSBody(params = { "prefix" }, script = "if (window.WorldDB) window.WorldDB.removePrefix(prefix);")
    private static native void nativeRemovePrefix(String prefix);

    @JSBody(params = {}, script = "if (window.WorldDB) window.WorldDB.commit();")
    private static native void nativeCommit();

    @JSBody(params = { "cb" }, script = "window.__javaWorldSave = cb;")
    private static native void nativeInstallExit(ExitCb cb);

    // ------------------------------------------------------------ для JVM-теста (без браузера)

    static void testSetup(File base) { baseOverride = base; enabled = true; dirty.clear(); removedPrefixes.clear(); }
}
