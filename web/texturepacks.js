/*
 * Текстур-паки (.zip) для web-порта Minecraft Alpha 1.2.6.
 *
 * Что делает этот файл:
 *   - читает zip прямо в браузере (central directory + stored/deflate через
 *     встроенный DecompressionStream — внешних библиотек не нужно);
 *   - декодирует PNG браузером (createImageBitmap) и передаёт в Java готовые
 *     RGBA-пиксели через колбэки window.__javaTP (см. TexturePackStore.java);
 *   - хранит загруженные паки в IndexedDB — после перезагрузки страницы они
 *     подхватываются автоматически (loadSaved вызывается из ResourcePreloader
 *     до старта игры);
 *   - даёт два способа добавить пак: кнопка "Add pack..." в экране
 *     "Mods and Texture Packs" (pick()) и перетаскивание .zip на окно.
 *
 * Поддерживаются обычные паки Alpha/Beta (terrain.png, gui/*, mob/* и т.д. —
 * в корне архива или в единственной вложенной папке). Файлы, которых нет в
 * паке, берутся из встроенных ресурсов игры.
 */
(function () {
  'use strict';

  var DB_NAME = 'webcraft-texturepacks';
  var STORE = 'packs';

  // ---------------------------------------------------------------- toast
  var toastEl = null, toastTimer = 0;
  function toast(text, isError) {
    if (!toastEl) {
      toastEl = document.createElement('div');
      toastEl.style.cssText =
        'position:fixed;left:50%;bottom:24px;transform:translateX(-50%);' +
        'max-width:80vw;padding:8px 14px;border-radius:3px;z-index:300;' +
        'font:13px -apple-system,"Segoe UI",sans-serif;color:#fff;' +
        'background:rgba(0,0,0,0.8);pointer-events:none;display:none;';
      document.body.appendChild(toastEl);
    }
    toastEl.textContent = text;
    toastEl.style.background = isError ? 'rgba(160,30,30,0.9)' : 'rgba(0,0,0,0.8)';
    toastEl.style.display = 'block';
    clearTimeout(toastTimer);
    toastTimer = setTimeout(function () { toastEl.style.display = 'none'; }, 4500);
  }

  // ------------------------------------------------------------ IndexedDB
  function openDb() {
    return new Promise(function (resolve, reject) {
      if (!window.indexedDB) { reject(new Error('IndexedDB unavailable')); return; }
      var req = indexedDB.open(DB_NAME, 1);
      req.onupgradeneeded = function () { req.result.createObjectStore(STORE, { keyPath: 'name' }); };
      req.onsuccess = function () { resolve(req.result); };
      req.onerror = function () { reject(req.error); };
    });
  }
  function tx(mode, fn) {
    return openDb().then(function (db) {
      return new Promise(function (resolve, reject) {
        var t = db.transaction(STORE, mode);
        var result = fn(t.objectStore(STORE));
        t.oncomplete = function () { resolve(result && result.result !== undefined ? result.result : undefined); db.close(); };
        t.onerror = function () { reject(t.error); db.close(); };
        t.onabort = function () { reject(t.error); db.close(); };
      });
    });
  }
  function dbGetAll() { return tx('readonly', function (s) { return s.getAll(); }).then(function (r) { return r || []; }); }
  function dbPut(rec) { return tx('readwrite', function (s) { return s.put(rec); }); }
  function dbDelete(name) { return tx('readwrite', function (s) { return s.delete(name); }); }

  // ------------------------------------------------------------ zip reader
  function readZipDirectory(buf) {
    var dv = new DataView(buf);
    var eocd = -1;
    var min = Math.max(0, buf.byteLength - 22 - 65535);
    for (var i = buf.byteLength - 22; i >= min; i--) {
      if (dv.getUint32(i, true) === 0x06054b50) { eocd = i; break; }
    }
    if (eocd < 0) throw new Error('Not a zip file (no end-of-central-directory)');
    var count = dv.getUint16(eocd + 10, true);
    var pos = dv.getUint32(eocd + 16, true);
    var dec = new TextDecoder('utf-8');
    var entries = [];
    for (var n = 0; n < count; n++) {
      if (dv.getUint32(pos, true) !== 0x02014b50) throw new Error('Corrupt zip central directory');
      var method = dv.getUint16(pos + 10, true);
      var compSize = dv.getUint32(pos + 20, true);
      var nameLen = dv.getUint16(pos + 28, true);
      var extraLen = dv.getUint16(pos + 30, true);
      var commentLen = dv.getUint16(pos + 32, true);
      var localOff = dv.getUint32(pos + 42, true);
      var name = dec.decode(new Uint8Array(buf, pos + 46, nameLen));
      entries.push({ name: name, method: method, compSize: compSize, localOff: localOff });
      pos += 46 + nameLen + extraLen + commentLen;
    }
    return entries;
  }

  function extractEntry(buf, e) {
    var dv = new DataView(buf);
    if (dv.getUint32(e.localOff, true) !== 0x04034b50) throw new Error('Corrupt zip local header: ' + e.name);
    var nameLen = dv.getUint16(e.localOff + 26, true);
    var extraLen = dv.getUint16(e.localOff + 28, true);
    var start = e.localOff + 30 + nameLen + extraLen;
    var data = buf.slice(start, start + e.compSize);
    if (e.method === 0) return Promise.resolve(data);
    if (e.method === 8) {
      if (typeof DecompressionStream === 'undefined') {
        return Promise.reject(new Error('This browser has no DecompressionStream (needed for zip)'));
      }
      var stream = new Blob([data]).stream().pipeThrough(new DecompressionStream('deflate-raw'));
      return new Response(stream).arrayBuffer();
    }
    return Promise.reject(new Error('Unsupported zip compression method ' + e.method + ': ' + e.name));
  }

  // Если все нужные файлы лежат в одной вложенной папке (MyPack/terrain.png) — срезаем её.
  function detectPrefix(entries) {
    var known = /^(terrain\.png|pack\.png|gui\/|mob\/|misc\/|art\/|item\/|title\/|font\/|environment\/|particles\.png)/;
    var anyRoot = false, prefix = null;
    for (var i = 0; i < entries.length; i++) {
      var nm = entries[i].name;
      if (known.test(nm)) { anyRoot = true; break; }
      var m = /^([^\/]+\/)(.+)$/.exec(nm);
      if (m && known.test(m[2]) && prefix === null) prefix = m[1];
    }
    return anyRoot ? '' : (prefix || '');
  }

  var canvas = null, ctx = null;
  function decodePng(arrayBuffer) {
    return createImageBitmap(new Blob([arrayBuffer], { type: 'image/png' })).then(function (bmp) {
      if (!canvas) {
        canvas = document.createElement('canvas');
        ctx = canvas.getContext('2d', { willReadFrequently: true });
      }
      canvas.width = bmp.width;
      canvas.height = bmp.height;
      ctx.clearRect(0, 0, bmp.width, bmp.height);
      ctx.drawImage(bmp, 0, 0);
      var img = ctx.getImageData(0, 0, bmp.width, bmp.height);
      var w = bmp.width, h = bmp.height;
      if (bmp.close) bmp.close();
      return { w: w, h: h, rgba: new Uint8Array(img.data.buffer) };
    });
  }

  // Разбирает zip и передаёт все PNG в Java. Возвращает Promise<число файлов>.
  function registerPack(name, buf) {
    var jt = window.__javaTP;
    var entries = readZipDirectory(buf);
    var prefix = detectPrefix(entries);
    var pngs = entries.filter(function (e) {
      var nm = e.name;
      return !nm.endsWith('/') && nm.indexOf('__MACOSX') < 0 &&
             nm.toLowerCase().endsWith('.png') && nm.indexOf(prefix) === 0;
    });
    jt.begin(name);
    var i = 0;
    function next() {
      if (i >= pngs.length) { jt.end(name); return Promise.resolve(pngs.length); }
      var e = pngs[i++];
      return extractEntry(buf, e).then(decodePng).then(function (r) {
        jt.resource(name, e.name.substring(prefix.length), r.w, r.h, r.rgba);
      }).catch(function (err) {
        console.warn('Texture pack: skipped', e.name, err);
      }).then(next);
    }
    return next();
  }

  // -------------------------------------------------------------- public API
  var pending = [];

  function packName(file) { return file.name.replace(/\.zip$/i, ''); }

  function addFile(file) {
    if (!window.__javaTP) { pending.push(file); return Promise.resolve(); }
    var name = packName(file);
    return file.arrayBuffer().then(function (buf) {
      return registerPack(name, buf).then(function (count) {
        if (count === 0) throw new Error('no PNG textures found in ' + file.name);
        return dbPut({ name: name, blob: file, added: Date.now() }).catch(function (e) {
          console.warn('Could not save texture pack to IndexedDB:', e);
        }).then(function () {
          toast('Texture pack "' + name + '" added (' + count + ' files) — select it in the list');
        });
      });
    }).catch(function (err) {
      console.error('Texture pack failed:', err);
      toast('Texture pack "' + name + '" failed: ' + (err && err.message ? err.message : err), true);
    });
  }

  function addFiles(files) {
    var list = Array.prototype.filter.call(files, function (f) { return /\.zip$/i.test(f.name); });
    if (!list.length) { if (files.length) toast('Texture packs must be .zip files', true); return; }
    list.reduce(function (p, f) { return p.then(function () { return addFile(f); }); }, Promise.resolve());
  }

  window.TexturePacks = {
    pick: function () {
      var input = document.createElement('input');
      input.type = 'file';
      input.accept = '.zip,application/zip';
      input.multiple = true;
      input.style.display = 'none';
      input.addEventListener('change', function () { addFiles(input.files); input.remove(); });
      document.body.appendChild(input);
      input.click();
    },

    flushPending: function () {
      var files = pending; pending = [];
      if (files.length) addFiles(files);
    },

    // Вызывается ResourcePreloader перед стартом игры.
    loadSaved: function () {
      return dbGetAll().then(function (recs) {
        recs.sort(function (a, b) { return (a.added || 0) - (b.added || 0); });
        return recs.reduce(function (p, rec) {
          return p.then(function () {
            return rec.blob.arrayBuffer().then(function (buf) { return registerPack(rec.name, buf); })
              .catch(function (e) { console.warn('Saved texture pack failed:', rec.name, e); });
          });
        }, Promise.resolve());
      }).catch(function (e) { console.warn('Texture pack storage unavailable:', e); });
    },

    remove: function (name) {
      dbDelete(name).catch(function (e) { console.warn('Could not delete texture pack:', e); });
    }
  };

  // Drag & drop .zip на окно игры.
  window.addEventListener('dragover', function (e) {
    if (e.dataTransfer && Array.prototype.indexOf.call(e.dataTransfer.types || [], 'Files') >= 0) e.preventDefault();
  });
  window.addEventListener('drop', function (e) {
    if (e.dataTransfer && e.dataTransfer.files && e.dataTransfer.files.length) {
      e.preventDefault();
      addFiles(e.dataTransfer.files);
    }
  });
})();
