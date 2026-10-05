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
    var re = /a=candidate:\S+ 1 udp \d+ (\S+) (\d+) typ (host|srflx|relay)/g, m;
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
    createInvite: function (id) {
      links[id].createInvite().then(function (c) { window.__javaMp.onCode(id, c, ''); },
        function (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); });
    },
    acceptAnswer: function (id, code) {
      links[id].acceptAnswer(code).then(function () { window.__javaMp.onAccepted(id, ''); },
        function (e) { window.__javaMp.onAccepted(id, String(e && e.message || e)); });
    },
    acceptInvite: function (id, code) {
      links[id].acceptInvite(code).then(function (c) { window.__javaMp.onCode(id, c, ''); },
        function (e) { window.__javaMp.onCode(id, '', String(e && e.message || e)); });
    },
    send: function (id, u8) { links[id].send(u8); },
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
