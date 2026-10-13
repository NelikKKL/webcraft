#!/usr/bin/env node
// Добавляет фоновую музыку и пластинки в web/assets.akrile (с пережатием в меньший размер).
//
//   node tools/add-music.mjs <папка с подпапками music/ и records/> [--kbps 64] [--record-kbps 40] [--no-recode]
//
// * music/*.ogg  -> music/<имя>.ogg        (Ogg Vorbis, стерео, по умолчанию 64 кбит/с)
// * records/13.ogg, records/cat.ogg -> streaming/13.ogg, streaming/cat.ogg (моно, 40 кбит/с).
//   Только эти две пластинки знает Alpha 1.2.6 (предметы 2000 и 2001); остальные появились позже.
// Пережатие делает ffmpeg (должен быть в PATH). Нет ffmpeg или указан --no-recode — файлы кладутся как есть
// (около 20 МБ вместо ~12 МБ). Повторный запуск заменяет прежние music/ и streaming/ в архиве.
// Формат остаётся Ogg Vorbis — как у оригинала, поэтому поддержка браузерами та же, что и раньше.
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const args = process.argv.slice(2);
const src = args.find(a => !a.startsWith('--'));
const opt = (name, def) => { const i = args.indexOf(name); return i >= 0 ? args[i + 1] : def; };
const kbps = opt('--kbps', '64'), recKbps = opt('--record-kbps', '40');
let recode = !args.includes('--no-recode');
if (!src || !fs.existsSync(src)) { console.error('usage: node tools/add-music.mjs <folder with music/ and records/> [--kbps 64] [--no-recode]'); process.exit(1); }
if (recode && spawnSync('ffmpeg', ['-version']).status !== 0) { console.warn('ffmpeg not found: adding files as they are'); recode = false; }

const tmp = fs.mkdtempSync(path.join(os.tmpdir(), 'mc-music-'));
function prepare(file, mono, rate) {
  const orig = fs.readFileSync(file);
  if (!recode) return orig;
  const out = path.join(tmp, path.basename(file));
  const a = ['-v', 'error', '-y', '-i', file, '-vn', '-map_metadata', '-1', '-c:a', 'libvorbis', '-b:a', `${rate}k`];
  if (mono) a.push('-ac', '1');
  const r = spawnSync('ffmpeg', [...a, out]);
  if (r.status !== 0) throw new Error('ffmpeg failed for ' + file + ': ' + r.stderr);
  const recoded = fs.readFileSync(out);
  // Если пережатие не дало выигрыша (файл и так лёгкий, например nuance1/2) — оставляем оригинал: лишняя потеря качества не нужна.
  return recoded.length < orig.length ? recoded : orig;
}

const { default: Akrile } = await import(path.join(root, 'web', 'akrile.js'));
await Akrile.ready;
const archivePath = path.join(root, 'web', 'assets.akrile');
const zip = await Akrile.loadAsync(fs.readFileSync(archivePath));
for (const k of Object.keys(zip.files)) if (/^(music|newmusic|streaming)\//.test(k)) zip.remove ? zip.remove(k) : null;

let n = 0, before = 0, after = 0;
const musicDir = path.join(src, 'music');
if (fs.existsSync(musicDir)) {
  for (const f of fs.readdirSync(musicDir).filter(f => /\.ogg$/i.test(f)).sort()) {
    const p = path.join(musicDir, f); before += fs.statSync(p).size;
    const data = prepare(p, false, kbps); after += data.length; zip.file(`music/${f}`, data); n++;
  }
}
for (const rec of ['13', 'cat']) {
  const p = path.join(src, 'records', `${rec}.ogg`);
  if (!fs.existsSync(p)) { console.warn(`record ${rec}.ogg not found`); continue; }
  before += fs.statSync(p).size;
  const data = prepare(p, true, recKbps); after += data.length; zip.file(`streaming/${rec}.ogg`, data); n++;
}
if (!n) { console.error('Nothing to add'); process.exit(2); }
fs.copyFileSync(archivePath, archivePath + '.bak');
fs.writeFileSync(archivePath, await zip.generateAsync({ type: 'uint8array' }));
console.log(`${n} files: ${(before / 1048576).toFixed(1)} MB -> ${(after / 1048576).toFixed(1)} MB; assets.akrile is now ${(fs.statSync(archivePath).size / 1048576).toFixed(1)} MB (backup: assets.akrile.bak)`);
