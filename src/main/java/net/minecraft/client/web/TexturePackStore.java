package net.minecraft.client.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;

/**
 * Список пользовательских текстур-паков (zip). JS-часть (web/texturepacks.js)
 * разбирает архивы, хранит их в IndexedDB между запусками и вызывает
 * колбэки ниже: begin(name) -> resource(name, path, w, h, rgba)* -> end(name).
 * Java-часть (ff.java) берёт список через {@link #list()}.
 *
 * Выбранный пак запоминается в localStorage (ключ webcraft.texturepack).
 */
public final class TexturePackStore {

    private TexturePackStore() {}

    private static final List<WebTexturePack> packs = new ArrayList<>();
    private static final Map<String, WebTexturePack> building = new HashMap<>();

    @JSFunctor interface BeginCb extends JSObject { void call(String name); }
    @JSFunctor interface ResourceCb extends JSObject { void call(String name, String path, int w, int h, Uint8Array rgba); }
    @JSFunctor interface EndCb extends JSObject { void call(String name); }

    /** Регистрирует колбэки для JS. Вызывать до загрузки сохранённых паков. */
    public static void install() {
        BeginCb begin = TexturePackStore::onBegin;
        ResourceCb resource = TexturePackStore::onResource;
        EndCb end = TexturePackStore::onEnd;
        installNative(begin, resource, end);
    }

    @JSBody(params = { "begin", "resource", "end" }, script =
        "window.__javaTP = { begin: begin, resource: resource, end: end };" +
        "if (window.TexturePacks && window.TexturePacks.flushPending) { window.TexturePacks.flushPending(); }")
    private static native void installNative(BeginCb begin, ResourceCb resource, EndCb end);

    private static void onBegin(String name) {
        building.put(name, new WebTexturePack(name));
    }

    private static void onResource(String name, String path, int w, int h, Uint8Array rgba) {
        WebTexturePack p = building.get(name);
        if (p == null) return;
        p.addFile(path, ResourceCache.makeEntry(w, h, rgba));
    }

    private static void onEnd(String name) {
        WebTexturePack p = building.remove(name);
        if (p == null) return;
        p.finish();
        for (int i = 0; i < packs.size(); i++) {
            if (packs.get(i).a.equals(name)) {
                packs.set(i, p);   // тот же файл загружен повторно — заменяем
                return;
            }
        }
        packs.add(p);
    }

    public static List<WebTexturePack> list() {
        return packs;
    }

    /** Удаляет пак из списка и из постоянного хранилища браузера. */
    public static void remove(WebTexturePack p) {
        packs.remove(p);
        removeNative(p.a);
    }

    @JSBody(params = { "name" }, script =
        "if (window.TexturePacks) { window.TexturePacks.remove(name); }")
    private static native void removeNative(String name);

    /** Открывает системный диалог выбора zip-файлов. */
    @JSBody(script = "if (window.TexturePacks) { window.TexturePacks.pick(); }")
    public static native void openFilePicker();

    @JSBody(script =
        "try { return localStorage.getItem('webcraft.texturepack'); } catch (e) { return null; }")
    public static native String getSelected();

    @JSBody(params = { "name" }, script =
        "try { localStorage.setItem('webcraft.texturepack', name); } catch (e) {}")
    public static native void setSelected(String name);
}
