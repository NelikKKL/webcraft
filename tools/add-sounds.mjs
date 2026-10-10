#!/usr/bin/env node
// Добавляет оригинальные звуки в web/assets.akrile.
//
//   node tools/add-sounds.mjs <папка resources>
//
// <папка resources> — папка из старого лаунчера Alpha (.minecraft/resources) или любая другая с
// подпапками sound/, newsound/, music/, newmusic/, streaming/ (файлы .ogg/.wav/.mus). Звуки в jar
// игры не входят — оригинал докачивал их отдельно, поэтому берём их из такой папки.
// Альтернатива без пересборки архива: заархивировать эту папку в .zip и перетащить на окно игры
// (см. web/extras.js, раздел "Звук").
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const archivePath = path.join(root, 'web', 'assets.akrile');
const src = process.argv[2];
if (!src || !fs.existsSync(src)) { console.error('usage: node tools/add-sounds.mjs <resources folder>'); process.exit(1); }

const { default: Akrile } = await import(path.join(root, 'web', 'akrile.js'));
await Akrile.ready;

const GROUPS = ['sound', 'newsound', 'music', 'newmusic', 'streaming'];
const AUDIO = /\.(ogg|wav|mus|mp3|m4a|flac)$/i;
function* walk(dir) {
  for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
    const p = path.join(dir, e.name);
    if (e.isDirectory()) yield* walk(p); else yield p;
  }
}

const zip = await Akrile.loadAsync(fs.readFileSync(archivePath));
let added = 0, bytes = 0;
for (const g of GROUPS) {
  const dir = path.join(src, g);
  if (!fs.existsSync(dir)) continue;
  for (const file of walk(dir)) {
    if (!AUDIO.test(file)) continue;
    const rel = path.relative(src, file).split(path.sep).join('/');
    const data = fs.readFileSync(file);
    zip.file(rel, data); added++; bytes += data.length;
  }
}
if (!added) { console.error('No audio files found under', GROUPS.join(', '), 'in', src); process.exit(2); }
fs.copyFileSync(archivePath, archivePath + '.bak');
fs.writeFileSync(archivePath, await zip.generateAsync({ type: 'uint8array' }));
console.log(`Added ${added} files (${(bytes / 1048576).toFixed(1)} MB) to ${path.relative(process.cwd(), archivePath)}; backup: assets.akrile.bak`);
