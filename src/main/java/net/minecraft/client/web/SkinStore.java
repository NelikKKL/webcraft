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

    /** 0 = Auto (по прозрачности), 1 = Classic (4 px), 2 = Slim (3 px). */
    private static int armMode = readArmMode();
    private static String nick = readNick();

    @JSFunctor interface SetCb extends JSObject { void call(int w, int h, Uint8Array rgba); }
    @JSFunctor interface ClearCb extends JSObject { void call(); }

    /** Регистрирует колбэки для JS. Вызывать до загрузки сохранённого скина. */
    public static void install() {
        SetCb set = SkinStore::onSet;
        ClearCb clear = SkinStore::onClear;
        installNative(set, clear);
    }

    @JSBody(params = { "set", "clear" }, script =
        "window.__javaSkin = { set: set, clear: clear };")
    private static native void installNative(SetCb set, ClearCb clear);

    private static void onSet(int w, int h, Uint8Array rgba) {
        ResourceCache.Entry e = ResourceCache.makeEntry(w, h, rgba);
        ResourceCache.putDynamic(PATH, e);
        has = true;
        width = w;
        height = h;
        modern = (h == w);
        autoSlim = modern && detectSlim(e.argb, w);
        pendingApply = true;
    }

    private static void onClear() {
        ResourceCache.removeDynamic(PATH);
        has = false;
        modern = false;
        autoSlim = false;
        pendingApply = true;
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
