package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

/**
 * Persistent browser storage for Minecraft world's NBT files.
 *
 * TeaVM's default java.io filesystem is volatile in a browser. World chunks and
 * level.dat are therefore stored as compressed-NBT Base64 strings in IndexedDB.
 * Java can synchronously read/write the in-memory cache, while IndexedDB commits
 * are batched asynchronously. IndexedDB is hydrated before the game starts.
 */
public final class WorldStore {
    private static boolean enabled;

    private WorldStore() { }

    @JSFunctor
    private interface LoadCallback extends JSObject {
        void run();
    }

    /** Must complete before Minecraft constructs the world-select screen. */
    public static void loadAll(Runnable onComplete) {
        LoadCallback callback = () -> {
            enabled = true;
            if (onComplete != null) onComplete.run();
        };
        loadAllNative(callback);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static String levelKey(String worldName) {
        return "world/" + cleanPath(worldName) + "/level.dat";
    }

    public static String chunkKey(String worldDirectory, int x, int z) {
        return "world/" + cleanPath(worldDirectory) + "/chunks/" + x + "." + z + ".dat";
    }

    private static String cleanPath(String path) {
        if (path == null) return "unknown";
        String normalized = path.replace('\\', '/');
        while (normalized.startsWith("/")) normalized = normalized.substring(1);
        while (normalized.contains("../")) normalized = normalized.replace("../", "");
        while (normalized.startsWith("./")) normalized = normalized.substring(2);
        return normalized;
    }

    public static String get(String key) {
        if (!enabled || key == null) return null;
        return getNative(key);
    }

    public static void put(String key, String value) {
        if (!enabled || key == null || value == null) return;
        putNative(key, value);
    }

    public static void removePrefix(String prefix) {
        if (!enabled || prefix == null) return;
        removePrefixNative(prefix);
    }

    /** Commit any queued file updates promptly, especially on pagehide. */
    public static void flush() {
        if (enabled) flushNative();
    }

    /** Register a last-chance game save on tab close/navigation. */
    public static void installPageLifecycleSave(Runnable onPageExit) {
        if (!enabled || onPageExit == null) return;
        LoadCallback callback = onPageExit::run;
        installPageLifecycleSaveNative(callback);
    }

    /** Encode bytes without relying on java.util.Base64 support in a TeaVM target. */
    public static String encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        StringBuilder out = new StringBuilder((bytes.length + 2) / 3 * 4);
        for (int i = 0; i < bytes.length; i += 3) {
            int a = bytes[i] & 0xff;
            int hasB = i + 1 < bytes.length ? 1 : 0;
            int hasC = i + 2 < bytes.length ? 1 : 0;
            int b = hasB != 0 ? bytes[i + 1] & 0xff : 0;
            int c = hasC != 0 ? bytes[i + 2] & 0xff : 0;
            out.append(alphabet[a >>> 2]);
            out.append(alphabet[((a & 3) << 4) | (b >>> 4)]);
            out.append(hasB != 0 ? alphabet[((b & 15) << 2) | (c >>> 6)] : '=');
            out.append(hasC != 0 ? alphabet[c & 63] : '=');
        }
        return out.toString();
    }

    public static byte[] decode(String value) {
        if (value == null || value.isEmpty()) return new byte[0];
        int length = value.length();
        if ((length & 3) != 0) throw new IllegalArgumentException("Invalid Base64 length");
        int padding = value.endsWith("==") ? 2 : (value.endsWith("=") ? 1 : 0);
        byte[] out = new byte[length / 4 * 3 - padding];
        int outPos = 0;
        for (int i = 0; i < length; i += 4) {
            int a = base64Value(value.charAt(i));
            int b = base64Value(value.charAt(i + 1));
            int c = value.charAt(i + 2) == '=' ? 0 : base64Value(value.charAt(i + 2));
            int d = value.charAt(i + 3) == '=' ? 0 : base64Value(value.charAt(i + 3));
            if (a < 0 || b < 0 || c < 0 || d < 0) throw new IllegalArgumentException("Invalid Base64 data");
            int bits = (a << 18) | (b << 12) | (c << 6) | d;
            if (outPos < out.length) out[outPos++] = (byte)(bits >>> 16);
            if (outPos < out.length) out[outPos++] = (byte)(bits >>> 8);
            if (outPos < out.length) out[outPos++] = (byte)bits;
        }
        return out;
    }

    private static int base64Value(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        if (c >= 'a' && c <= 'z') return c - 'a' + 26;
        if (c >= '0' && c <= '9') return c - '0' + 52;
        if (c == '+') return 62;
        if (c == '/') return 63;
        return -1;
    }

    @JSBody(params = { "done" }, script =
        "var existing = window.__webcraftWorldStore;" +
        "if (existing && existing.ready) { done(); return; }" +
        "if (existing && existing.loading) { existing.waiters.push(done); return; }" +
        "var s = existing || {};" +
        "s.cache = s.cache || Object.create(null);" +
        "s.dirty = s.dirty || Object.create(null);" +
        "s.localOnly = Object.create(null);" +
        "s.db = null; s.ready = false; s.loading = true; s.waiters = [done];" +
        "s.flushTimer = null; s.flush = null;" +
        "window.__webcraftWorldStore = s;" +
        "var localPrefix = 'webcraft.worlddata.';" +
        "function loadLocal() { try { for (var i=0; i<localStorage.length; i++) { var k=localStorage.key(i); if (k && k.indexOf(localPrefix)===0) { var shortKey=k.substring(localPrefix.length); var value=localStorage.getItem(k); if (value!==null) { s.cache[shortKey]=value; s.localOnly[shortKey]=value; } } } } catch(e) {} }" +
        "function complete() { s.loading=false; s.ready=true; var waiters=s.waiters.slice(); s.waiters.length=0; waiters.forEach(function(cb){ try { cb(); } catch(e) { console.error(e); } }); }" +
        "function fallbackWrite(key,value) { try { localStorage.setItem(localPrefix+key,value); } catch(e) { console.warn('World save fallback storage is full or unavailable:',e); } }" +
        "function flush() { s.flushTimer=null; var batch=s.dirty; s.dirty=Object.create(null); var keys=Object.keys(batch); if (!keys.length) return; if (!s.db) { keys.forEach(function(k){ fallbackWrite(k,batch[k]); }); return; } try { var tx=s.db.transaction('files','readwrite'); var store=tx.objectStore('files'); keys.forEach(function(k){ store.put({key:k,data:batch[k]}); }); tx.onerror=function(){ console.warn('World save transaction failed:',tx.error); keys.forEach(function(k){ if (!(k in s.dirty)) s.dirty[k]=batch[k]; }); }; } catch(e) { console.warn('Could not write world save:',e); keys.forEach(function(k){ if (!(k in s.dirty)) s.dirty[k]=batch[k]; }); keys.forEach(function(k){ fallbackWrite(k,batch[k]); }); } }" +
        "function scheduleFlush() { if (s.flushTimer!==null) return; s.flushTimer=setTimeout(flush,150); }" +
        "s.flush=flush; s.scheduleFlush=scheduleFlush;" +
        "loadLocal();" +
        "if (!window.indexedDB) { complete(); return; }" +
        "var req; try { req=indexedDB.open('webcraft-world-saves',1); } catch(e) { console.warn('IndexedDB unavailable for world saves:',e); complete(); return; }" +
        "req.onupgradeneeded=function(){ var db=req.result; if (!db.objectStoreNames.contains('files')) db.createObjectStore('files',{keyPath:'key'}); };" +
        "req.onerror=function(){ console.warn('Could not open world save database:',req.error); complete(); };" +
        "req.onblocked=function(){ console.warn('World save database open was blocked'); };" +
        "req.onsuccess=function(){ s.db=req.result; s.db.onversionchange=function(){ try { s.db.close(); } catch(e) {} s.db=null; }; var tx; try { tx=s.db.transaction('files','readonly'); } catch(e) { complete(); return; } var getAll=tx.objectStore('files').getAll(); getAll.onerror=function(){ console.warn('Could not load world saves:',getAll.error); complete(); }; getAll.onsuccess=function(){ var seen=Object.create(null); (getAll.result||[]).forEach(function(row){ if(row && typeof row.key==='string' && typeof row.data==='string'){ s.cache[row.key]=row.data; seen[row.key]=true; } }); Object.keys(s.localOnly).forEach(function(k){ if(!seen[k]) s.dirty[k]=s.localOnly[k]; }); s.localOnly=Object.create(null); if(Object.keys(s.dirty).length) scheduleFlush(); complete(); }; };" )
    private static native void loadAllNative(LoadCallback done);

    @JSBody(params = { "key" }, script =
        "var s=window.__webcraftWorldStore; return s && s.cache && Object.prototype.hasOwnProperty.call(s.cache,key) ? s.cache[key] : null;")
    private static native String getNative(String key);

    @JSBody(params = { "key", "value" }, script =
        "var s=window.__webcraftWorldStore; if (!s || !s.cache) return; s.cache[key]=value; s.dirty[key]=value; if (!s.db) { try { localStorage.setItem('webcraft.worlddata.'+key,value); } catch(e) {} } if (s.scheduleFlush) s.scheduleFlush();")
    private static native void putNative(String key, String value);

    @JSBody(params = { "prefix" }, script =
        "var s=window.__webcraftWorldStore; if (!s || !s.cache) return; Object.keys(s.cache).forEach(function(k){ if(k.indexOf(prefix)===0) delete s.cache[k]; }); Object.keys(s.dirty).forEach(function(k){ if(k.indexOf(prefix)===0) delete s.dirty[k]; });" +
        "try { for(var i=localStorage.length-1;i>=0;i--){var k=localStorage.key(i); if(k && k.indexOf('webcraft.worlddata.'+prefix)===0) localStorage.removeItem(k);} } catch(e) {}" +
        "if (s.db) { try { var tx=s.db.transaction('files','readwrite'); var req=tx.objectStore('files').openCursor(); req.onsuccess=function(){ var cur=req.result; if(!cur) return; if(typeof cur.key==='string' && cur.key.indexOf(prefix)===0) cur.delete(); cur.continue(); }; } catch(e) { console.warn('Could not delete saved world:',e); } }")
    private static native void removePrefixNative(String prefix);

    @JSBody(script = "var s=window.__webcraftWorldStore; if(s && s.flush) s.flush();")
    private static native void flushNative();

    @JSBody(params = { "callback" }, script =
        "var s=window.__webcraftWorldStore; if(!s || s.lifecycleInstalled) return; s.lifecycleInstalled=true; var saving=false;" +
        "function saveBeforeExit(){ if(saving) return; saving=true; try { callback(); } catch(e) { console.error('Final world save failed:',e); } finally { try { if(s.flush) s.flush(); } catch(e) { console.error(e); } } }" +
        "window.addEventListener('pagehide',saveBeforeExit); window.addEventListener('beforeunload',saveBeforeExit);")
    private static native void installPageLifecycleSaveNative(LoadCallback callback);
}
