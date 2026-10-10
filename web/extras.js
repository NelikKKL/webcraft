/*
 * Дополнения web-порта Minecraft Alpha 1.2.6: текстур-паки (.zip), скин игрока и
 * мультиплеер (WebRTC, коды приглашения).
 * (Один файл вместо двух — чтобы в билде было меньше файлов.)
 *
 * ===== Текстур-паки =====
 * Что делает эта часть:
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
    var known = /^(terrain\.png|pack\.png|gui\/|mob\/|misc\/|art\/|item\/|title\/|font\/|environment\/|particles\.png|sound\/|newsound\/|music\/|newmusic\/|streaming\/)/;
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

  // Разбирает zip: PNG уходят в Java (текстур-пак), аудио (sound/, music/, streaming/ в любой
  // вложенности, например resources/sound/...) — в звуковой движок. Возвращает Promise<{png, audio}>.
  function registerPack(name, buf) {
    var jt = window.__javaTP;
    var entries = readZipDirectory(buf);
    var prefix = detectPrefix(entries);
    var skipEntry = function (nm) { return nm.endsWith('/') || nm.indexOf('__MACOSX') >= 0; };
    var pngs = entries.filter(function (e) {
      return !skipEntry(e.name) && e.name.toLowerCase().endsWith('.png') && e.name.indexOf(prefix) === 0;
    });
    var audio = entries.filter(function (e) {
      return !skipEntry(e.name) && window.Sounds && window.Sounds.isAudioFile(e.name);
    });

    function doPngs() {
      if (!pngs.length) return Promise.resolve(0);
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
    function doAudio() {
      var i = 0, added = 0;
      function next() {
        if (i >= audio.length) return Promise.resolve(added);
        var e = audio[i++];
        return extractEntry(buf, e).then(function (ab) {
          if (window.Sounds.add(e.name, new Uint8Array(ab))) added++;
        }).catch(function (err) {
          console.warn('Sound pack: skipped', e.name, err);
        }).then(next);
      }
      return next();
    }
    return doPngs().then(function (png) {
      return doAudio().then(function (snd) { return { png: png, audio: snd }; });
    });
  }

  // -------------------------------------------------------------- public API
  var pending = [];

  function packName(file) { return file.name.replace(/\.zip$/i, ''); }

  function addFile(file) {
    if (!window.__javaTP) { pending.push(file); return Promise.resolve(); }
    var name = packName(file);
    return file.arrayBuffer().then(function (buf) {
      return registerPack(name, buf).then(function (r) {
        if (r.png === 0 && r.audio === 0) throw new Error('no PNG textures or sounds found in ' + file.name);
        return dbPut({ name: name, blob: file, added: Date.now() }).catch(function (e) {
          console.warn('Could not save pack to IndexedDB:', e);
        }).then(function () {
          var parts = [];
          if (r.png) parts.push('texture pack "' + name + '" (' + r.png + ' files) — select it in the list');
          if (r.audio) parts.push(r.audio + ' sounds loaded');
          toast('Added: ' + parts.join('; '));
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

  function loadSavedPacks() {
    return dbGetAll().then(function (recs) {
      recs.sort(function (a, b) { return (a.added || 0) - (b.added || 0); });
      return recs.reduce(function (p, rec) {
        return p.then(function () {
          return rec.blob.arrayBuffer().then(function (buf) { return registerPack(rec.name, buf); })
            .catch(function (e) { console.warn('Saved texture pack failed:', rec.name, e); });
        });
      }, Promise.resolve());
    }).catch(function (e) { console.warn('Texture pack storage unavailable:', e); });
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
      return loadSavedPacks().then(function () {
        return window.Skins ? window.Skins.loadSaved() : undefined;
      });
    },

    remove: function (name) {
      dbDelete(name).catch(function (e) { console.warn('Could not delete texture pack:', e); });
    }
  };


  // ======================================================================
  // ===== Скин игрока (PNG) ==============================================
  // ======================================================================
  // Меню Options -> Skin & Name... вызывает Skins.pick(). Файл декодируется
  // браузером и уходит в Java (window.__javaSkin.set), копия сохраняется в
  // localStorage и подхватывается при следующем запуске (loadSaved).
  // Поддержка: 64x32 (классика), 64x64 (современные, в т.ч. slim "Alex"),
  // HD — кратно 64 с соотношением 1:1 или 2:1.
  var SKIN_KEY = 'webcraft.skin';

  function skinSizeOk(w, h) {
    if (w < 64 || w % 64 !== 0) return false;
    return h === w || h * 2 === w;
  }

  function bitmapToRgba(bmp) {
    var c = document.createElement('canvas');
    c.width = bmp.width; c.height = bmp.height;
    var g = c.getContext('2d', { willReadFrequently: true });
    g.drawImage(bmp, 0, 0);
    var img = g.getImageData(0, 0, bmp.width, bmp.height);
    return { canvas: c, w: bmp.width, h: bmp.height, rgba: new Uint8Array(img.data.buffer) };
  }

  function decodeSkin(blob) {
    return createImageBitmap(blob).then(function (bmp) {
      var r = bitmapToRgba(bmp);
      if (bmp.close) bmp.close();
      return r;
    });
  }

  function useSkin(blob, persist) {
    return decodeSkin(blob).then(function (r) {
      if (!skinSizeOk(r.w, r.h)) {
        throw new Error('unsupported skin size ' + r.w + 'x' + r.h + ' (need 64x32, 64x64 or HD multiples)');
      }
      window.__javaSkin.set(r.w, r.h, r.rgba);
      if (persist) {
        try { localStorage.setItem(SKIN_KEY, r.canvas.toDataURL('image/png')); }
        catch (e) { console.warn('Could not save skin:', e); }
      }
      return r;
    });
  }

  function addSkinFile(file) {
    if (!window.__javaSkin) return Promise.resolve();
    return useSkin(file, true).then(function (r) {
      toast('Skin applied (' + r.w + 'x' + r.h + ')');
    }).catch(function (err) {
      console.error('Skin failed:', err);
      toast('Skin failed: ' + (err && err.message ? err.message : err), true);
    });
  }

  window.Skins = {
    pick: function () {
      var input = document.createElement('input');
      input.type = 'file';
      input.accept = '.png,image/png';
      input.style.display = 'none';
      input.addEventListener('change', function () {
        if (input.files && input.files[0]) addSkinFile(input.files[0]);
        input.remove();
      });
      document.body.appendChild(input);
      input.click();
    },

    // Вызывается перед стартом игры (из TexturePacks.loadSaved).
    loadSaved: function () {
      var url = null;
      try { url = localStorage.getItem(SKIN_KEY); } catch (e) {}
      if (!url || !window.__javaSkin) return Promise.resolve();
      return fetch(url).then(function (r) { return r.blob(); })
        .then(function (b) { return useSkin(b, false); })
        .catch(function (e) { console.warn('Saved skin failed to load:', e); });
    },

    forget: function () {
      try { localStorage.removeItem(SKIN_KEY); } catch (e) {}
    }
  };

  // ======================================================================
  // ===== Мультиплеер: WebRTC DataChannel + ручной обмен короткими кодами ===
  // ======================================================================
  // Серверов нет: хост создаёт "код приглашения", гость вставляет его и получает
  // "код-ответ", который нужно вернуть хосту. Дальше игроки соединены напрямую.
  // Для обхода NAT используются бесплатные публичные STUN-серверы (только
  // определение внешнего адреса, трафик игры через них не идёт).
  //
  // Код — это не полный SDP (~550 символов), а только то, что нужно для соединения:
  // ICE ufrag/pwd, отпечаток DTLS-сертификата, роль и список UDP-кандидатов в
  // бинарной упаковке + контрольная сумма, затем base64url. Остальной SDP
  // восстанавливается из шаблона на другой стороне. Обычно получается 90-200 символов.
  var MP_PREFIX = 'MC1-';
  var MP_STUN = [{ urls: ['stun:stun.l.google.com:19302', 'stun:stun1.l.google.com:19302', 'stun:stun.cloudflare.com:3478'] }];

  function u8cat(arr) {
    var n = 0, i; for (i = 0; i < arr.length; i++) n += arr[i].length;
    var out = new Uint8Array(n), o = 0;
    for (i = 0; i < arr.length; i++) { out.set(arr[i], o); o += arr[i].length; }
    return out;
  }
  function b64uEnc(u8) {
    var s = ''; for (var i = 0; i < u8.length; i++) s += String.fromCharCode(u8[i]);
    return btoa(s).replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
  }
  function b64uDec(str) {
    str = str.replace(/-/g, '+').replace(/_/g, '/');
    while (str.length % 4) str += '=';
    var bin = atob(str), u8 = new Uint8Array(bin.length);
    for (var i = 0; i < bin.length; i++) u8[i] = bin.charCodeAt(i);
    return u8;
  }
  function crc16(u8) {
    var c = 0xFFFF;
    for (var i = 0; i < u8.length; i++) {
      c ^= u8[i] << 8;
      for (var k = 0; k < 8; k++) c = (c & 0x8000) ? ((c << 1) ^ 0x1021) & 0xFFFF : (c << 1) & 0xFFFF;
    }
    return c;
  }
  function asciiBytes(s) { var u = new Uint8Array(s.length); for (var i = 0; i < s.length; i++) u[i] = s.charCodeAt(i) & 0x7F; return u; }

  // sdp -> строка-код
  function packSdp(sdp, isAnswer) {
    function g(re) { var m = re.exec(sdp); return m ? m[1] : ''; }
    var ufrag = g(/a=ice-ufrag:(\S+)/), pwd = g(/a=ice-pwd:(\S+)/);
    var fp = g(/a=fingerprint:sha-256 (\S+)/).split(':').map(function (h) { return parseInt(h, 16); });
    if (!ufrag || !pwd || fp.length !== 32) throw new Error('SDP has no ICE/DTLS data');
    var cands = [], seen = {};
    var re = /a=candidate:\S+ 1 udp \d+ (\S+) (\d+) typ (host|srflx|relay)/gi, m;
    while ((m = re.exec(sdp))) {
      var key = m[1] + ':' + m[2];
      if (seen[key]) continue; seen[key] = 1;
      if (/^fe80:/i.test(m[1])) continue;                    // link-local IPv6 бесполезен
      var type = m[3] === 'host' ? 0 : (m[3] === 'srflx' ? 1 : 2), port = parseInt(m[2], 10), body;
      if (/^\d+\.\d+\.\d+\.\d+$/.test(m[1])) {
        body = [type, 0].concat(m[1].split('.').map(Number));
      } else if (m[1].indexOf(':') >= 0) {                   // IPv6: разворачиваем в 16 байт
        var halves = m[1].split('::'), head = halves[0] ? halves[0].split(':') : [], tail = (halves.length > 1 && halves[1]) ? halves[1].split(':') : [];
        var fill = 8 - head.length - tail.length, words = head.slice();
        for (var z = 0; z < fill; z++) words.push('0');
        words = words.concat(tail);
        body = [type, 1];
        for (var w = 0; w < 8; w++) { var v = parseInt(words[w] || '0', 16); body.push(v >> 8, v & 255); }
      } else {                                              // mDNS (xxxx.local)
        var nm = asciiBytes(m[1]); body = [type, 2, nm.length].concat(Array.prototype.slice.call(nm));
      }
      body.push(port >> 8, port & 255);
      cands.push(new Uint8Array(body));
      if (cands.length >= 6) break;
    }
    var parts = [new Uint8Array([isAnswer ? 1 : 0, ufrag.length, pwd.length]), asciiBytes(ufrag), asciiBytes(pwd), new Uint8Array(fp), new Uint8Array([cands.length])];
    parts = parts.concat(cands);
    var payload = u8cat(parts), c = crc16(payload);
    return MP_PREFIX + b64uEnc(u8cat([payload, new Uint8Array([c >> 8, c & 255])]));
  }

  // строка-код -> {isAnswer, ufrag, pwd, fp, cands[]}
  function unpackCode(code) {
    code = String(code || '').replace(/\s+/g, '');
    if (code.indexOf(MP_PREFIX) !== 0) throw new Error('This is not a multiplayer code');
    var u8 = b64uDec(code.substring(MP_PREFIX.length));
    if (u8.length < 40) throw new Error('Code is too short (copied incompletely?)');
    var payload = u8.subarray(0, u8.length - 2), c = crc16(payload);
    if (((c >> 8) & 255) !== u8[u8.length - 2] || (c & 255) !== u8[u8.length - 1]) throw new Error('Code is damaged (copied incompletely?)');
    var p = 0, isAnswer = payload[p++] === 1, ul = payload[p++], pl = payload[p++];
    function str(n) { var s = ''; for (var i = 0; i < n; i++) s += String.fromCharCode(payload[p++]); return s; }
    var ufrag = str(ul), pwd = str(pl), fp = [];
    for (var i = 0; i < 32; i++) fp.push(('0' + payload[p++].toString(16)).slice(-2).toUpperCase());
    var n = payload[p++], cands = [];
    for (i = 0; i < n; i++) {
      var type = payload[p++], kind = payload[p++], host;
      if (kind === 0) { host = payload[p] + '.' + payload[p + 1] + '.' + payload[p + 2] + '.' + payload[p + 3]; p += 4; }
      else if (kind === 1) { var w = []; for (var k = 0; k < 8; k++) { w.push(((payload[p] << 8) | payload[p + 1]).toString(16)); p += 2; } host = w.join(':'); }
      else { var len = payload[p++]; host = ''; for (k = 0; k < len; k++) host += String.fromCharCode(payload[p++]); }
      var port = (payload[p] << 8) | payload[p + 1]; p += 2;
      cands.push({ type: ['host', 'srflx', 'relay'][type] || 'host', host: host, port: port });
    }
    return { isAnswer: isAnswer, ufrag: ufrag, pwd: pwd, fp: fp.join(':'), cands: cands };
  }

  // распакованный код -> SDP (offer или answer) для setRemoteDescription
  function buildSdp(info, asOffer) {
    var L = ['v=0', 'o=- 4611731400430051336 2 IN IP4 127.0.0.1', 's=-', 't=0 0', 'a=group:BUNDLE 0', 'a=msid-semantic: WMS',
      'm=application 9 UDP/DTLS/SCTP webrtc-datachannel', 'c=IN IP4 0.0.0.0',
      'a=ice-ufrag:' + info.ufrag, 'a=ice-pwd:' + info.pwd, 'a=ice-options:trickle',
      'a=fingerprint:sha-256 ' + info.fp, 'a=setup:' + (asOffer ? 'actpass' : 'active'), 'a=mid:0', 'a=sctp-port:5000', 'a=max-message-size:262144'];
    var prio = { host: 2130706431, srflx: 1677729535, relay: 16777215 };
    info.cands.forEach(function (c, i) {
      L.push('a=candidate:' + (i + 1) + ' 1 udp ' + (prio[c.type] - i) + ' ' + c.host + ' ' + c.port + ' typ ' + c.type +
             (c.type === 'host' ? '' : ' raddr 0.0.0.0 rport 0'));
    });
    return L.join('\r\n') + '\r\n';
  }

  function waitIce(pc) {
    return new Promise(function (resolve) {
      if (pc.iceGatheringState === 'complete') return resolve();
      var done = function () { resolve(); };
      pc.addEventListener('icegatheringstatechange', function () { if (pc.iceGatheringState === 'complete') done(); });
      setTimeout(done, 4000);       // не ждём бесконечно: хватит и того, что собралось
    });
  }

  // Одно соединение (один гость). Колбэки: onOpen(), onMessage(Uint8Array), onClose().
  function Link(cb) {
    this.cb = cb; this.pc = null; this.dc = null; this.open = false; this.closed = false;
  }
  Link.prototype._wire = function (dc) {
    var self = this;
    this.dc = dc; dc.binaryType = 'arraybuffer';
    dc.onopen = function () { self.open = true; if (self.cb.onOpen) self.cb.onOpen(); };
    dc.onmessage = function (e) { if (self.cb.onMessage) self.cb.onMessage(new Uint8Array(e.data)); };
    dc.onclose = function () { self._closed(); };
  };
  Link.prototype._closed = function () {
    if (this.closed) return; this.closed = true; this.open = false;
    if (this.cb.onClose) this.cb.onClose();
  };
  Link.prototype._pc = function () {
    var self = this;
    this.pc = new RTCPeerConnection({ iceServers: MP_STUN });
    this.pc.onconnectionstatechange = function () {
      var s = self.pc.connectionState;
      if (s === 'failed' || s === 'closed' || s === 'disconnected') self._closed();
    };
    return this.pc;
  };
  // Хост: создать приглашение -> Promise<код>
  Link.prototype.createInvite = function () {
    var self = this, pc = this._pc();
    this._wire(pc.createDataChannel('mc', { ordered: true }));
    return pc.createOffer().then(function (o) { return pc.setLocalDescription(o); })
      .then(function () { return waitIce(pc); })
      .then(function () { return packSdp(pc.localDescription.sdp, false); });
  };
  // Хост: принять ответ гостя
  Link.prototype.acceptAnswer = function (code) {
    var info = unpackCode(code);
    if (!info.isAnswer) return Promise.reject(new Error('This is an invite code, but the guest\'s reply code is needed'));
    return this.pc.setRemoteDescription({ type: 'answer', sdp: buildSdp(info, false) });
  };
  // Гость: принять приглашение -> Promise<код-ответ>
  Link.prototype.acceptInvite = function (code) {
    var self = this, info = unpackCode(code);
    if (info.isAnswer) return Promise.reject(new Error('This is a reply code, but the host\'s invite code is needed'));
    var pc = this._pc();
    pc.ondatachannel = function (e) { self._wire(e.channel); };
    return pc.setRemoteDescription({ type: 'offer', sdp: buildSdp(info, true) })
      .then(function () { return pc.createAnswer(); })
      .then(function (a) { return pc.setLocalDescription(a); })
      .then(function () { return waitIce(pc); })
      .then(function () { return packSdp(pc.localDescription.sdp, true); });
  };
  Link.prototype.send = function (u8) {
    if (this.dc && this.dc.readyState === 'open') this.dc.send(u8);
  };
  Link.prototype.close = function () {
    this.closed = true;
    try { if (this.dc) this.dc.close(); } catch (e) {}
    try { if (this.pc) this.pc.close(); } catch (e) {}
  };

  // ---- Мост для Java: ссылки лежат в массиве, Java оперирует числами-id ----
  var links = [];
  window.Mp = {
    // Возвращает id ссылки. Колбэки Java: onOpen(id), onMessage(id, Uint8Array), onClose(id)
    newLink: function () {
      var id = links.length, J = window.__javaMp || {};
      links.push(new Link({
        onOpen: function () { if (window.__javaMp) window.__javaMp.onOpen(id); },
        onMessage: function (u8) { if (window.__javaMp) window.__javaMp.onMessage(id, u8); },
        onClose: function () { if (window.__javaMp) window.__javaMp.onClose(id); }
      }));
      return id;
    },
    // Все точки входа из Java обёрнуты в try/catch: синхронное исключение (например, мусор вместо кода
    // в буфере обмена) раньше улетало прямо в Java и ронял игру; теперь это обычное сообщение об ошибке.
    createInvite: function (id) {
      try {
        links[id].createInvite().then(function (c) { window.__javaMp.onCode(id, c, ''); },
          function (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); });
      } catch (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); }
    },
    acceptAnswer: function (id, code) {
      try {
        links[id].acceptAnswer(code).then(function () { window.__javaMp.onAccepted(id, ''); },
          function (e) { window.__javaMp.onAccepted(id, String(e && e.message || e)); });
      } catch (e) { window.__javaMp.onAccepted(id, String(e && e.message || e)); }
    },
    acceptInvite: function (id, code) {
      try {
        links[id].acceptInvite(code).then(function (c) { window.__javaMp.onCode(id, c, ''); },
          function (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); });
      } catch (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); }
    },
    send: function (id, u8) { try { links[id].send(u8); } catch (e) {} },
    close: function (id) { if (links[id]) links[id].close(); },
    isOpen: function (id) { return !!(links[id] && links[id].open); },

    // Буфер обмена (копирование — по клику; чтение — через разрешение браузера, иначе prompt)
    copy: function (text) {
      function fallback() {
        var ta = document.createElement('textarea');
        ta.value = text; ta.style.cssText = 'position:fixed;left:-1000px;top:0;opacity:0';
        document.body.appendChild(ta); ta.focus(); ta.select();
        try { document.execCommand('copy'); } catch (e) {}
        ta.remove();
      }
      if (navigator.clipboard && navigator.clipboard.writeText) navigator.clipboard.writeText(text).catch(fallback);
      else fallback();
    },
    paste: function () {
      function viaPrompt() {
        var t = window.prompt('Paste the code here (Ctrl+V):', '');
        window.__javaMp.onPaste(t === null ? '' : t);
      }
      if (navigator.clipboard && navigator.clipboard.readText) {
        navigator.clipboard.readText().then(function (t) { window.__javaMp.onPaste(t || ''); }, viaPrompt);
      } else viaPrompt();
    },
    _test: { packSdp: packSdp, unpackCode: unpackCode, buildSdp: buildSdp }
  };

  // ======================================================================
  // ===== Звук (Web Audio) ===============================================
  // ======================================================================
  // Реализация звуковой системы поверх Web Audio API вместо paulscode/OpenAL оригинала.
  // Раскладка ресурсов — как у оригинальной игры (папка resources/ лаунчера Alpha):
  //   sound/<группа>/<имя>[N].ogg  и  newsound/...  — звуковые эффекты ("step/grass1.ogg" -> "step.grass",
  //                                                   варианты 1..N выбираются случайно, как в es.java);
  //   music/*.ogg, newmusic/*.ogg  — фоновая музыка (случайный трек раз в 10-20 минут);
  //   streaming/*.mus (.ogg)       — пластинки (имя без расширения: "13", "cat").
  // Файлы берутся из assets.akrile (пути sound/..., music/...) и из zip-паков, которые игрок
  // перетаскивает на окно/выбирает в меню (см. раздел "Текстур-паки"). Если файла для звука нет,
  // он синтезируется процедурно (шаги, клики, мобы и т.д.) — игра не молчит.
  var Sounds = (function () {
    var AUDIO_EXT = /\.(ogg|wav|mus|mp3|m4a|flac)$/i;
    var ctx = null, master = null, unlocked = false;
    var pool = { sound: {}, music: [], stream: {} };     // sound: name -> [entry], music: [entry], stream: name -> [entry]
    var activeVoices = 0, MAX_VOICES = 40;
    var music = null, musicVol = 1, stream = null, streamVol = 1;
    var synthCache = {};

    function mimeFor(path) {
      if (/\.wav$/i.test(path)) return 'audio/wav';
      if (/\.mp3$/i.test(path)) return 'audio/mpeg';
      if (/\.m4a$/i.test(path)) return 'audio/mp4';
      if (/\.flac$/i.test(path)) return 'audio/flac';
      return 'audio/ogg';        // .ogg и .mus (оригинальные .mus — это Ogg Vorbis)
    }

    function ensureCtx() {
      if (!ctx) {
        try {
          var AC = window.AudioContext || window.webkitAudioContext;
          if (!AC) return null;
          ctx = new AC();
          master = ctx.createGain();
          master.gain.value = 1;
          master.connect(ctx.destination);
        } catch (e) { return null; }
      }
      if (ctx.state === 'suspended') { try { ctx.resume(); } catch (e) {} }
      return ctx;
    }

    // Браузер не даёт играть звук до первого жеста пользователя — ловим его.
    function unlock() {
      unlocked = true;
      ensureCtx();
      ['pointerdown', 'keydown', 'touchstart', 'mousedown'].forEach(function (ev) { window.removeEventListener(ev, unlock, true); });
      setTimeout(function () { try { warmup(); } catch (e) {} }, 400);
    }
    ['pointerdown', 'keydown', 'touchstart', 'mousedown'].forEach(function (ev) { window.addEventListener(ev, unlock, true); });

    // Нормализация пути ресурса в (группа, имя). Правило имени — как в es.a(String, File).
    function classify(path) {
      var p = String(path).replace(/\\/g, '/').replace(/^\/+/, '');
      var low = p.toLowerCase();
      if (!AUDIO_EXT.test(low)) return null;
      var m = /^(?:.*?\/)?(sound|newsound|music|newmusic|streaming)\/(.+)$/.exec(low);
      if (!m) return null;
      var group = m[1], rest = m[2];
      var name = rest.substring(0, rest.indexOf('.')).replace(/\//g, '.');
      if (group === 'sound' || group === 'newsound') {
        name = name.replace(/\d+$/, '');
        return { kind: 'sound', name: name, key: group + '/' + rest };
      }
      if (group === 'music' || group === 'newmusic') return { kind: 'music', name: name, key: group + '/' + rest };
      return { kind: 'stream', name: name, key: group + '/' + rest };
    }

    function upsert(list, entry) {
      for (var i = 0; i < list.length; i++) if (list[i].key === entry.key) { list[i] = entry; return; }
      list.push(entry);
    }

    // Регистрация файла (байты). Повторная регистрация того же пути заменяет файл (паки перекрывают встроенные).
    function add(path, bytes) {
      var c = classify(path);
      if (!c) return false;
      var entry = { key: c.key, path: path, bytes: bytes, buf: null, loading: null, url: null };
      if (c.kind === 'sound') { (pool.sound[c.name] = pool.sound[c.name] || []); upsert(pool.sound[c.name], entry); }
      else if (c.kind === 'music') upsert(pool.music, entry);
      else { (pool.stream[c.name] = pool.stream[c.name] || []); upsert(pool.stream[c.name], entry); }
      return true;
    }

    function decode(entry) {
      if (entry.buf) return Promise.resolve(entry.buf);
      if (entry.loading) return entry.loading;
      var c = ensureCtx();
      if (!c) return Promise.reject(new Error('no AudioContext'));
      var copy = entry.bytes.buffer.slice(entry.bytes.byteOffset, entry.bytes.byteOffset + entry.bytes.byteLength);
      entry.loading = new Promise(function (resolve, reject) {
        var p = c.decodeAudioData(copy, resolve, reject);
        if (p && p.then) p.then(resolve, reject);
      }).then(function (b) { entry.buf = b; entry.loading = null; return b; },
              function (e) { entry.loading = null; entry.bad = true; throw e; });
      return entry.loading;
    }

    function pick(list) {
      var ok = list.filter(function (e) { return !e.bad; });
      return ok.length ? ok[(Math.random() * ok.length) | 0] : null;
    }

    // ------------------------------------------------------------ синтезатор
    function noise(n, lp, rnd) {          // белый шум с односполюсным ФНЧ (lp 0..1: больше = ярче)
      var out = new Float32Array(n), y = 0;
      for (var i = 0; i < n; i++) { y += lp * ((rnd() * 2 - 1) - y); out[i] = y; }
      return out;
    }
    function seeded(seed) { var s = seed >>> 0 || 1; return function () { s = (s * 1664525 + 1013904223) >>> 0; return s / 4294967296; }; }
    function env(i, n, a, d) { var t = i / n; return (t < a ? t / a : Math.pow(1 - (t - a) / (1 - a), d)); }

    function synth(name, variant) {
      var c = ensureCtx(); if (!c) return null;
      var key = name + '#' + variant;
      if (synthCache[key]) return synthCache[key];
      var sr = c.sampleRate, rnd = seeded(variant * 7919 + name.length * 131 + name.charCodeAt(name.length - 1));
      var parts = name.split('.'), grp = parts[0], sub = parts.slice(1).join('.');
      var dur = 0.18, data;
      function make(d, fn) { dur = d; var n = Math.max(1, (d * sr) | 0), a = new Float32Array(n); fn(a, n); return a; }
      var soft = { grass: 0.28, sand: 0.22, cloth: 0.12, gravel: 0.55, wood: 0.18, stone: 0.7, glass: 0.9 };
      if (grp === 'step' || grp === 'dig') {
        var mat = soft[sub] !== undefined ? sub : 'stone', lp = soft[mat], dig = grp === 'dig';
        data = make(dig ? 0.24 : 0.13, function (a, n) {
          var nz = noise(n, lp, rnd), f = mat === 'wood' ? 150 + rnd() * 30 : 0;
          for (var i = 0; i < n; i++) {
            var e = env(i, n, 0.02, mat === 'gravel' ? 3 : 4);
            if (mat === 'gravel' && (i % ((sr * 0.012) | 0)) < 40) e *= 1.6;
            var v = nz[i] * e * (dig ? 1.1 : 0.7);
            if (f) v += Math.sin(2 * Math.PI * f * i / sr) * e * 0.5;
            if (mat === 'stone' || mat === 'glass') v += Math.sin(2 * Math.PI * (mat === 'glass' ? 2400 : 900) * i / sr) * e * e * 0.25;
            a[i] = v;
          }
        });
      } else if (grp === 'random') {
        if (sub === 'click') data = make(0.05, function (a, n) { for (var i = 0; i < n; i++) a[i] = Math.sin(2 * Math.PI * 1500 * i / sr) * env(i, n, 0.01, 6) * 0.5 + (i < 8 ? 0.4 : 0); });
        else if (sub === 'pop') data = make(0.09, function (a, n) { for (var i = 0; i < n; i++) { var t = i / n; a[i] = Math.sin(2 * Math.PI * (350 + 500 * t) * i / sr) * env(i, n, 0.05, 2) * 0.55; } });
        else if (sub === 'bow') data = make(0.3, function (a, n) { var nz = noise(n, 0.4, rnd); for (var i = 0; i < n; i++) { var t = i / n; a[i] = (Math.sin(2 * Math.PI * (260 - 120 * t) * i / sr) * 0.5 + nz[i] * 0.3) * env(i, n, 0.01, 3); } });
        else if (sub === 'explode') data = make(1.1, function (a, n) { var nz = noise(n, 0.07, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * 3.2 * env(i, n, 0.005, 2.2) + Math.sin(2 * Math.PI * 45 * i / sr) * env(i, n, 0.01, 3) * 0.5; });
        else if (sub === 'splash') data = make(0.4, function (a, n) { var nz = noise(n, 0.35, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * 1.6 * env(i, n, 0.04, 2.5); });
        else if (sub === 'fizz' || sub === 'fuse') data = make(sub === 'fuse' ? 0.6 : 0.35, function (a, n) { var nz = noise(n, 0.95, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * 0.45 * env(i, n, 0.1, 1.5); });
        else if (sub === 'door_open' || sub === 'door_close') data = make(0.3, function (a, n) { var nz = noise(n, 0.12, rnd), up = sub === 'door_open'; for (var i = 0; i < n; i++) { var t = i / n; a[i] = (nz[i] * 1.2 + Math.sin(2 * Math.PI * (up ? 120 + 180 * t : 300 - 180 * t) * i / sr) * 0.35) * env(i, n, 0.08, 2); } });
        else if (sub === 'glass') data = make(0.3, function (a, n) { var nz = noise(n, 0.97, rnd); for (var i = 0; i < n; i++) a[i] = (nz[i] * 0.5 + Math.sin(2 * Math.PI * 3100 * i / sr) * 0.2) * env(i, n, 0.005, 5); });
        else if (sub === 'hurt') data = make(0.3, function (a, n) { for (var i = 0; i < n; i++) { var t = i / n, ph = 2 * Math.PI * (210 - 90 * t) * i / sr; a[i] = (Math.sin(ph) + 0.4 * Math.sin(2 * ph)) * env(i, n, 0.03, 2) * 0.45; } });
        else if (sub === 'drr') data = make(0.25, function (a, n) { for (var i = 0; i < n; i++) a[i] = Math.sign(Math.sin(2 * Math.PI * 95 * i / sr)) * env(i, n, 0.03, 2) * 0.25; });
        else data = make(0.12, function (a, n) { var nz = noise(n, 0.5, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * env(i, n, 0.02, 4); });
      } else if (grp === 'mob' || grp === 'damage') {
        var base = 130, len = 0.45, kind = 'voc';
        if (/pig/.test(sub)) { base = 190; len = /death/.test(sub) ? 0.6 : 0.3; } else if (/cow/.test(sub)) { base = 105; len = 0.7; }
        else if (/sheep/.test(sub)) { base = 330; len = 0.55; } else if (/chicken/.test(sub)) { base = 700; len = 0.15; }
        else if (/zombiepig/.test(sub)) { base = 150; len = 0.5; } else if (/zombie/.test(sub)) { base = 85; len = 0.7; }
        else if (/skeleton/.test(sub)) { kind = 'rattle'; len = 0.3; } else if (/creeper/.test(sub)) { kind = 'hiss'; len = 0.5; }
        else if (/spider/.test(sub)) { kind = 'hiss'; len = 0.3; } else if (/slime/.test(sub)) { kind = 'squish'; len = 0.25; }
        else if (/ghast/.test(sub)) { base = 520; len = 0.9; }
        data = make(len, function (a, n) {
          var nz = noise(n, 0.5, rnd), jit = 1 + (rnd() - 0.5) * 0.15;
          for (var i = 0; i < n; i++) {
            var t = i / n, e = env(i, n, 0.08, 1.7);
            if (kind === 'hiss') a[i] = nz[i] * 0.7 * e;
            else if (kind === 'rattle') a[i] = (((i % ((sr * 0.035) | 0)) < 90) ? (rnd() * 2 - 1) : 0) * e * 0.8;
            else if (kind === 'squish') a[i] = Math.sin(2 * Math.PI * (160 + 420 * t) * i / sr) * e * 0.5;
            else { var f = base * jit * (1 + 0.05 * Math.sin(2 * Math.PI * 6 * t)) * (1 - 0.15 * t), ph = 2 * Math.PI * f * i / sr; a[i] = ((ph % (2 * Math.PI)) / Math.PI - 1) * 0.35 * e + nz[i] * 0.12 * e; }
          }
        });
      } else if (grp === 'liquid') {
        data = make(0.5, function (a, n) { var nz = noise(n, 0.2, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * 0.9 * env(i, n, 0.3, 1.2) * (0.6 + 0.4 * Math.sin(2 * Math.PI * 9 * i / sr)); });
      } else if (grp === 'fire') {
        data = make(sub === 'ignite' ? 0.4 : 0.35, function (a, n) { var nz = noise(n, sub === 'ignite' ? 0.5 : 0.9, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * (sub === 'fire' ? (rnd() < 0.02 ? 1.4 : 0.15) : 0.8) * env(i, n, 0.1, 1.6); });
      } else if (grp === 'portal') {
        data = make(1.2, function (a, n) { for (var i = 0; i < n; i++) { var t = i / n; a[i] = Math.sin(2 * Math.PI * (110 + 90 * Math.sin(t * 6)) * i / sr) * env(i, n, 0.3, 1.4) * 0.4; } });
      } else if (grp === 'ambient') {
        data = make(2.5, function (a, n) { var nz = noise(n, 0.03, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * 5 * env(i, n, 0.4, 1.3); });
      } else {
        data = make(0.1, function (a, n) { var nz = noise(n, 0.5, rnd); for (var i = 0; i < n; i++) a[i] = nz[i] * env(i, n, 0.02, 4); });
      }
      var buf = c.createBuffer(1, data.length, sr); buf.getChannelData(0).set(data);
      synthCache[key] = buf; return buf;
    }

    // ------------------------------------------------------------ воспроизведение эффектов
    function startBuffer(buf, x, y, z, gain, pitch, positional, maxDist) {
      var c = ensureCtx(); if (!c || !unlocked || activeVoices >= MAX_VOICES) return false;
      var src = c.createBufferSource(); src.buffer = buf;
      src.playbackRate.value = Math.max(0.25, Math.min(4, pitch || 1));
      var g = c.createGain(); g.gain.value = Math.max(0, Math.min(1, gain));
      var out = g;
      src.connect(g);
      if (positional) {
        var p = c.createPanner();
        p.panningModel = 'equalpower'; p.distanceModel = 'linear';
        p.refDistance = 1; p.maxDistance = Math.max(2, maxDist || 16); p.rolloffFactor = 1;
        if (p.positionX) { p.positionX.value = x; p.positionY.value = y; p.positionZ.value = z; } else p.setPosition(x, y, z);
        g.connect(p); out = p;
      }
      out.connect(master);
      activeVoices++;
      src.onended = function () { activeVoices--; try { src.disconnect(); out.disconnect(); } catch (e) {} };
      src.start();
      return true;
    }

    // name: "step.grass"; gain уже включает громкость настроек; positional — 3D; maxDist — радиус затухания
    function play(name, x, y, z, gain, pitch, positional, maxDist) {
      if (!unlocked || gain <= 0) return false;
      var c = ensureCtx(); if (!c) return false;
      var list = pool.sound[name];
      var entry = list && list.length ? pick(list) : null;
      if (entry) {
        if (entry.buf) return startBuffer(entry.buf, x, y, z, gain, pitch, positional, maxDist);
        decode(entry).then(function (b) { startBuffer(b, x, y, z, gain, pitch, positional, maxDist); },
                           function () { var s = synth(name, 0); if (s) startBuffer(s, x, y, z, gain, pitch, positional, maxDist); });
        return true;
      }
      var buf = synth(name, (Math.random() * 3) | 0);
      return buf ? startBuffer(buf, x, y, z, gain, pitch, positional, maxDist) : false;
    }

    function setListener(x, y, z, lx, ly, lz) {
      var c = ctx; if (!c) return;
      var L = c.listener;
      if (L.positionX) {
        L.positionX.value = x; L.positionY.value = y; L.positionZ.value = z;
        L.forwardX.value = lx; L.forwardY.value = ly; L.forwardZ.value = lz;
        L.upX.value = 0; L.upY.value = 1; L.upZ.value = 0;
      } else { L.setPosition(x, y, z); L.setOrientation(lx, ly, lz, 0, 1, 0); }
    }

    // ------------------------------------------------------------ музыка и пластинки (потоково, через <audio>)
    function makeAudio(entry) {
      if (!entry.url) entry.url = URL.createObjectURL(new Blob([entry.bytes], { type: mimeFor(entry.path) }));
      var a = new Audio(entry.url); a.preload = 'auto'; return a;
    }
    function stopEl(el) { if (el) { try { el.pause(); } catch (e) {} el.removeAttribute && el.removeAttribute('src'); } }

    function startMusic(vol) {
      if (!unlocked) return false;
      var e = pick(pool.music); if (!e) return false;
      stopEl(music); musicVol = vol;
      var a = makeAudio(e); music = a; a.volume = Math.max(0, Math.min(1, vol));
      a.onended = function () { if (music === a) music = null; };
      a.onerror = function () { e.bad = true; if (music === a) music = null; };
      var p = a.play(); if (p && p.catch) p.catch(function () { if (music === a) music = null; });
      return true;
    }
    function startStream(name, vol) {
      if (!unlocked) return false;
      var e = pick(pool.stream[name] || []); if (!e) return false;
      stopEl(stream); streamVol = vol;
      var a = makeAudio(e); stream = a; a.volume = Math.max(0, Math.min(1, vol));
      a.onended = function () { if (stream === a) stream = null; };
      a.onerror = function () { e.bad = true; if (stream === a) stream = null; };
      var p = a.play(); if (p && p.catch) p.catch(function () { if (stream === a) stream = null; });
      return true;
    }

    // Фоновая декодировка небольших эффектов, чтобы первый звук не запаздывал
    function warmup() {
      var names = Object.keys(pool.sound), i = 0;
      (function next() {
        if (!ctx || i >= names.length) return;
        var list = pool.sound[names[i++]], k = 0;
        (function one() { if (k >= list.length) return setTimeout(next, 0); var e = list[k++]; if (e.bytes.length > 400000) return one(); decode(e).then(one, one); })();
      })();
    }

    return {
      add: add, play: play, setListener: setListener, warmup: warmup,
      music: startMusic, hasMusic: function () { return pool.music.some(function (e) { return !e.bad; }); },
      musicPlaying: function () { return !!music && !music.paused && !music.ended; },
      musicVolume: function (v) { musicVol = v; if (music) music.volume = Math.max(0, Math.min(1, v)); },
      stopMusic: function () { stopEl(music); music = null; },
      stream: startStream, hasStream: function (n) { return !!(pool.stream[n] && pool.stream[n].length); },
      streamPlaying: function () { return !!stream && !stream.paused && !stream.ended; },
      streamVolume: function (v) { streamVol = v; if (stream) stream.volume = Math.max(0, Math.min(1, v)); },
      stopStream: function () { stopEl(stream); stream = null; },
      isUnlocked: function () { return unlocked; },
      isAudioFile: function (n) { return AUDIO_EXT.test(n) && !!classify(n); },
      stats: function () { var s = 0, f = 0; Object.keys(pool.sound).forEach(function (k) { s++; f += pool.sound[k].length; }); return { sounds: s, soundFiles: f, music: pool.music.length, streams: Object.keys(pool.stream).length, voices: activeVoices, ctx: ctx ? ctx.state : 'none' }; },
      _test: { classify: classify, synth: synth, ensureCtx: ensureCtx, forceUnlock: unlock, decode: decode, pool: pool }
    };
  })();
  window.Sounds = Sounds;

  // Drag & drop на окно игры: .zip — текстур-пак, .png — скин.
  window.addEventListener('dragover', function (e) {
    if (e.dataTransfer && Array.prototype.indexOf.call(e.dataTransfer.types || [], 'Files') >= 0) e.preventDefault();
  });
  window.addEventListener('drop', function (e) {
    var files = e.dataTransfer && e.dataTransfer.files;
    if (!files || !files.length) return;
    e.preventDefault();
    var zips = [], png = null;
    for (var i = 0; i < files.length; i++) {
      if (/\.zip$/i.test(files[i].name)) zips.push(files[i]);
      else if (/\.png$/i.test(files[i].name) && !png) png = files[i];
    }
    if (png) addSkinFile(png);
    if (zips.length) addFiles(zips);
    else if (!png) toast('Drop a .zip (texture pack) or a .png (skin)', true);
  });
})();
