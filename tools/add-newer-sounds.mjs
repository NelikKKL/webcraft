#!/usr/bin/env node
// Добавляет в web/assets.akrile звуки, нужные Alpha 1.2.6, из набора НОВЕЕ (раскладка вида
// step/grass1.mp3, mob/cow/say1.mp3, random/click.mp3 ... — без папки sound/, имена мобов по папкам).
//
//   node tools/add-newer-sounds.mjs <папка с набором> [--dry]
//
// Новые версии игры переименовали файлы (mob/zombie1.ogg -> mob/zombie/say1.mp3, mob/slime -> small/big),
// поэтому таблица ниже сопоставляет КАЖДОЕ имя звука, которое запрашивает код игры (step.grass,
// mob.cowhurt, ...), с файлами набора и кладёт их в архив под оригинальными путями
// sound/<имя>N.mp3 (движок строит имя по правилу оригинала: sound/mob/cowhurt2.mp3 -> "mob.cowhurt").
// Лишние файлы набора (волки, эндермены, дракон ...) в Alpha 1.2.6 не нужны и не добавляются.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const archivePath = path.join(root, 'web', 'assets.akrile');
const src = process.argv[2], dry = process.argv.includes('--dry');
if (!src || !fs.existsSync(src)) { console.error('usage: node tools/add-newer-sounds.mjs <folder> [--dry]'); process.exit(1); }

// [путь назначения без номера, папка в наборе, регулярка имён файлов]
const RULES = [];
const r = (dest, dir, re) => RULES.push([dest, dir, re]);
for (const m of ['cloth', 'grass', 'gravel', 'stone', 'wood']) r(`step/${m}`, 'step', new RegExp(`^${m}\\d+\\.mp3$`));
for (const n of ['bow', 'click', 'door_open', 'door_close', 'drr', 'explode', 'fizz', 'fuse', 'glass', 'hurt', 'pop', 'splash'])
  r(`random/${n}`, 'random', new RegExp(`^${n}\\d*\\.mp3$`));
r('liquid/water', 'liquid', /^water\d*\.mp3$/);
r('fire/fire', 'fire', /^fire\d*\.mp3$/); r('fire/ignite', 'fire', /^ignite\d*\.mp3$/);
for (const n of ['portal', 'travel', 'trigger']) r(`portal/${n}`, 'portal', new RegExp(`^${n}\\d*\\.mp3$`));
r('ambient/cave/cave', 'ambient/cave', /^cave\d+\.mp3$/);
// мобы: [имя в Alpha, папка, звук "говорит", звук "урон", прочее]
r('mob/chicken', 'mob/chicken', /^say\d+\.mp3$/); r('mob/chickenhurt', 'mob/chicken', /^hurt\d+\.mp3$/); r('mob/chickenplop', 'mob/chicken', /^plop\d*\.mp3$/);
r('mob/cow', 'mob/cow', /^say\d+\.mp3$/); r('mob/cowhurt', 'mob/cow', /^hurt\d+\.mp3$/);
r('mob/creeper', 'mob/creeper', /^say\d+\.mp3$/); r('mob/creeperdeath', 'mob/creeper', /^death\d*\.mp3$/);
r('mob/pig', 'mob/pig', /^say\d+\.mp3$/); r('mob/pigdeath', 'mob/pig', /^death\d*\.mp3$/);
r('mob/sheep', 'mob/sheep', /^say\d+\.mp3$/);
r('mob/skeleton', 'mob/skeleton', /^say\d+\.mp3$/); r('mob/skeletonhurt', 'mob/skeleton', /^hurt\d+\.mp3$/);
r('mob/slime', 'mob/slime', /^(small|big)\d+\.mp3$/); r('mob/slimeattack', 'mob/slime', /^attack\d+\.mp3$/);
r('mob/spider', 'mob/spider', /^say\d+\.mp3$/); r('mob/spiderdeath', 'mob/spider', /^death\d*\.mp3$/);
r('mob/zombie', 'mob/zombie', /^say\d+\.mp3$/); r('mob/zombiehurt', 'mob/zombie', /^hurt\d+\.mp3$/); r('mob/zombiedeath', 'mob/zombie', /^death\d*\.mp3$/);
for (const n of ['charge', 'death', 'fireball', 'moan', 'scream']) r(`mob/ghast/${n}`, 'mob/ghast', new RegExp(`^${n}\\d*\\.mp3$`));
for (const n of ['zpig', 'zpigangry', 'zpigdeath', 'zpighurt']) r(`mob/zombiepig/${n}`, 'mob/zombiepig', new RegExp(`^${n}\\d*\\.mp3$`));

const plan = [], missing = [];
for (const [dest, dir, re] of RULES) {
  const d = path.join(src, dir);
  const files = fs.existsSync(d) ? fs.readdirSync(d).filter(f => re.test(f)).sort((a, b) => a.localeCompare(b, 'en', { numeric: true })) : [];
  if (!files.length) { missing.push(dest.replace(/\//g, '.')); continue; }
  files.forEach((f, i) => plan.push({ from: path.join(d, f), to: `sound/${dest}${i + 1}.mp3`, name: dest.replace(/\//g, '.') }));
}
const names = new Set(plan.map(p => p.name));
console.log(`Sounds found: ${names.size} names, ${plan.length} files. Missing: ${missing.length ? missing.join(', ') : 'none'}`);
if (dry) process.exit(0);

const { default: Akrile } = await import(path.join(root, 'web', 'akrile.js'));
await Akrile.ready;
const zip = await Akrile.loadAsync(fs.readFileSync(archivePath));
for (const k of Object.keys(zip.files)) if (/^sound\//.test(k)) zip.remove ? zip.remove(k) : null;   // повторный запуск не плодит дубли
let bytes = 0;
for (const p of plan) { const data = fs.readFileSync(p.from); zip.file(p.to, data); bytes += data.length; }
fs.copyFileSync(archivePath, archivePath + '.bak');
fs.writeFileSync(archivePath, await zip.generateAsync({ type: 'uint8array' }));
console.log(`Added ${plan.length} files (${(bytes / 1024).toFixed(0)} KB); backup: assets.akrile.bak`);
