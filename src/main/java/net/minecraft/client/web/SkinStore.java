package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Скин и ник игрока.
 *
 * Файл скина выбирает пользователь в меню Options -> Skin & Name; PNG
 * декодирует браузер (web/extras.js), сюда он приходит готовыми RGBA-пикселями
 * и кладётся в {@link ResourceCache} под путём {@link #PATH}. Игрок рисуется с
 * этой текстурой вместо /mob/char.png. Скин, ник и режим рук сохраняются в
 * браузере (localStorage) и подхватываются при следующем запуске.
 *
 * Поддерживаются классические скины 64x32 и современные 64x64 (второй слой
 * одежды, отдельные левые руки/ноги, тонкие "Alex"-руки), а также HD-версии.
 */
public final class SkinStore {

    private SkinStore() {}

    public static final String PATH = "/skin/player.png";
    public static final String DEFAULT_PATH = "/mob/char.png";

    private static boolean has = false;
    private static boolean modern = false;
    private static boolean autoSlim = false;
    private static int width = 0, height = 0;
    private static boolean pendingApply = false;
    /** Растёт при каждой смене СВОЕГО скина — сетевой код по нему понимает, что скин нужно отправить заново. */
    private static int localVersion = 0;

    /** 0 = Auto (по прозрачности), 1 = Classic (4 px), 2 = Slim (3 px). */
    private static int armMode = readArmMode();
    private static String nick = readNick();

    @JSFunctor interface SetCb extends JSObject { void call(int w, int h, Uint8Array rgba); }
    @JSFunctor interface ClearCb extends JSObject { void call(); }
    @JSFunctor interface SetForCb extends JSObject { void call(int entityId, int w, int h, boolean slim, Uint8Array rgba); }

    /** Регистрирует колбэки для JS. Вызывать до загрузки сохранённого скина. */
    public static void install() {
        SetCb set = SkinStore::onSet;
        ClearCb clear = SkinStore::onClear;
        SetForCb setFor = SkinStore::onSetFor;
        installNative(set, clear, setFor);
    }

    @JSBody(params = { "set", "clear", "setFor" }, script =
        "window.__javaSkin = { set: set, clear: clear, setFor: setFor };")
    private static native void installNative(SetCb set, ClearCb clear, SetForCb setFor);

    private static void onSet(int w, int h, Uint8Array rgba) {
        ResourceCache.Entry e = ResourceCache.makeEntry(w, h, rgba);
        ResourceCache.putDynamic(PATH, e);
        has = true;
        width = w;
        height = h;
        modern = (h == w);
        autoSlim = modern && detectSlim(e.argb, w);
        pendingApply = true;
        localVersion++;
    }

    private static void onClear() {
        ResourceCache.removeDynamic(PATH);
        has = false;
        modern = false;
        autoSlim = false;
        pendingApply = true;
        localVersion++;
    }

    /**
     * Тонкие ("Alex") скины оставляют прозрачной крайнюю колонку правой руки:
     * в 64x64 это пиксели x=54..55, y=20..31 — как и в обычных лаунчерах.
     */
    private static boolean detectSlim(int[] argb, int w) {
        int s = w / 64;
        if (s < 1) return false;
        for (int y = 20 * s; y < 32 * s; y++) {
            for (int x = 54 * s; x < 56 * s; x++) {
                if ((argb[y * w + x] >>> 24) != 0) return false;
            }
        }
        return true;
    }

    // ------------------------------------------------- скины других игроков (пакет 200)

    /** Готовый (декодированный) скин чужой сущности, ждёт применения в игровом цикле. */
    public static final class Remote {
        public final int entityId;
        public final String path;
        public final boolean modern, slim;
        public int tries = 0;
        Remote(int id, String path, boolean modern, boolean slim) { this.entityId = id; this.path = path; this.modern = modern; this.slim = slim; }
    }

    private static final java.util.ArrayList<Remote> ready = new java.util.ArrayList<>();
    /** Последний известный скин каждой чужой сущности: нужен, когда хост пересоздаёт игрока (смена предмета в руке). */
    private static final java.util.HashMap<Integer, Remote> known = new java.util.HashMap<>();

    public static Remote known(int entityId) { return known.get(entityId); }

    public static void clearKnown() { known.clear(); }

    public static String remotePath(int entityId) { return "/skin/remote_" + entityId + ".png"; }

    public static int localVersion() { return localVersion; }

    /** Отдать PNG чужого скина на декодирование (браузер); результат появится в takeReady(). */
    public static void queueRemote(int entityId, boolean slim, byte[] png) {
        if (png == null || png.length == 0 || png.length > 200000) return;
        Uint8Array arr = new Uint8Array(new org.teavm.jso.typedarrays.ArrayBuffer(png.length));
        arr.set(png, 0);
        decodeForNative(entityId, slim, arr);
    }

    /** Удалить пользовательскую текстуру чужого игрока и назначить стандартный скин. */
    public static void queueRemoteDefault(int entityId) {
        ResourceCache.removeDynamic(remotePath(entityId));
        known.remove(entityId);
        ready.add(new Remote(entityId, DEFAULT_PATH, false, false));
    }

    @JSBody(params = { "id", "slim", "bytes" }, script = "if (window.Skins) { window.Skins.decodeFor(id, slim, bytes); }")
    private static native void decodeForNative(int id, boolean slim, Uint8Array bytes);

    private static void onSetFor(int entityId, int w, int h, boolean slim, Uint8Array rgba) {
        ResourceCache.Entry e = ResourceCache.makeEntry(w, h, rgba);
        String path = remotePath(entityId);
        ResourceCache.putDynamic(path, e);
        boolean mod = (h == w);
        Remote r = new Remote(entityId, path, mod, mod && slim);
        known.put(entityId, r);
        ready.add(r);
    }

    public static java.util.List<Remote> takeReady() {
        if (ready.isEmpty()) return java.util.Collections.emptyList();
        java.util.ArrayList<Remote> out = new java.util.ArrayList<>(ready);
        ready.clear();
        return out;
    }

    /** Сущность ещё не появилась — вернуть в очередь (не навсегда). */
    public static void requeue(Remote r) { ready.add(r); }

    /** PNG своего скина (как загружен) или null; slim — тонкие руки с учётом настройки Arms. */
    public static byte[] localPng() {
        if (!has) return null;
        Uint8Array a = localPngNative();
        if (a == null) return null;
        int n = a.getLength();
        if (n <= 0 || n > 200000) return null;
        byte[] out = new byte[n];
        for (int i = 0; i < n; i++) out[i] = (byte)a.get(i);
        return out;
    }

    @JSBody(script = "return window.__localSkinPng || null;")
    private static native Uint8Array localPngNative();

    // ------------------------------------------------------------ состояние

    public static boolean hasSkin() { return has; }

    public static String texturePath() { return has ? PATH : DEFAULT_PATH; }

    /** 64x64 (и HD-аналоги) — рисуем моделью с двумя слоями и отдельными левыми конечностями. */
    public static boolean isModern() { return has && modern; }

    public static boolean isSlim() {
        return armMode == 2 || (armMode == 0 && autoSlim);
    }

    public static String describe() {
        if (!has) return "Default skin";
        return (modern ? "64x64 skin" : "Classic skin") + " (" + width + "x" + height + ")";
    }

    public static String armModeName() {
        return armMode == 0 ? "Auto" : (armMode == 1 ? "Classic" : "Slim");
    }

    public static void cycleArmMode() {
        armMode = (armMode + 1) % 3;
        writeItem("webcraft.skinarms", String.valueOf(armMode));
    }

    /** true один раз после смены скина — игровой цикл должен перезалить текстуру. */
    public static boolean consumePending() {
        boolean p = pendingApply;
        pendingApply = false;
        return p;
    }

    public static String nick() { return nick; }

    public static void setNick(String value) {
        nick = (value == null || value.isEmpty()) ? "Player" : value;
        writeItem("webcraft.nick", nick);
    }

    // ----------------------------------------------------------------- JS

    /** Открывает системный диалог выбора файла со скином (PNG). */
    @JSBody(script = "if (window.Skins) { window.Skins.pick(); }")
    public static native void chooseFile();

    /** Возвращает стандартный скин и забывает сохранённый. */
    public static void reset() {
        onClear();
        forgetNative();
    }

    @JSBody(script = "if (window.Skins) { window.Skins.forget(); }")
    private static native void forgetNative();

    @JSBody(params = { "key" }, script =
        "try { return localStorage.getItem(key); } catch (e) { return null; }")
    private static native String readItem(String key);

    @JSBody(params = { "key", "value" }, script =
        "try { localStorage.setItem(key, value); } catch (e) {}")
    private static native void writeItem(String key, String value);

    private static int readArmMode() {
        String v = readItem("webcraft.skinarms");
        if ("1".equals(v)) return 1;
        if ("2".equals(v)) return 2;
        return 0;
    }

    private static String readNick() {
        String v = readItem("webcraft.nick");
        return (v == null || v.isEmpty()) ? "Player" : v;
    }
}
