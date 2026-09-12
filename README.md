# Minecraft Alpha 1.2.6 — web port (TeaVM)

Портирование [decomp-исходников](https://github.com/) Minecraft Alpha 1.2.6
в браузер через [TeaVM](https://teavm.org/) (Java → JS/WASM транспилятор).

**Статус: этап 3 — decomp-дерево скопировано и пропатчено. Пять реальных
CI-прогонов пройдены итеративно** — от `javac`-ошибок (JPMS-конфликт,
API TeaVM) до всё более редких TeaVM reachability-ошибок
(`System.exit`/`Socket`/`InetAddress`/`Thread.stop` — методы, реально
существующие в JDK, но не реализованные в собственной JRE-эмуляции
TeaVM). Пятый прогон дал всего одну ошибку (`Thread.stop()`, дважды) —
похоже, список неподдерживаемых методов близок к исчерпанию. **Ничего не
подтверждено шестым прогоном** — остаются `java.util.zip`, `java.io.File`,
`org.lwjgl.BufferUtils` как непроверенные риски (см. TODO.md).

## Что уже реализовано

- **`org.lwjgl.opengl.GL11`** — эмуляция fixed-function OpenGL 1.1
  (матричный стек, display lists, client-side vertex arrays, простое
  directional-освещение, туман) поверх WebGL2. Единственный универсальный
  шейдер эмулирует всё через uniform-флаги (см. `Shaders.java`).
- **`org.lwjgl.opengl.Display`** — единственный `<canvas>` вместо
  настоящего окна ОС, fullscreen через resize к `window.innerWidth/Height`.
- **`org.lwjgl.input.Keyboard`/`Mouse`** — DOM события → LWJGL-подобная
  pull-based очередь событий, Pointer Lock API для grabbed-режима камеры.
- **`org.lwjgl.util.glu.GLU`** — только `gluPerspective`/`gluErrorString`
  (единственное реально используемое в decomp).
- **`WebEntryPoint`** — тестовая сцена (НЕ настоящая игра): текстурированный
  пол, несколько вращающихся кубов через display list, свободная камера.
  Существует, чтобы проверить весь GL-мост в сборе до интеграции реального
  Minecraft.java.
- **`java.awt.image.BufferedImage`/`Graphics`/`Color` + `javax.imageio.ImageIO`**
  — настоящая (не заглушка) реализация ровно того подмножества API, что
  использует decomp (bulk getRGB/setRGB, drawImage-блит, HSB→RGB для цвета
  неба/биомов). PNG декодируется браузером при прелоадке (см. ниже), а не
  Java-кодом — писать свой PNG-декодер не нужно.
- **`ResourcePreloader`/`ResourceCache`/`ResourceManifest`/`ResourceIO`** —
  мост для 44 картиночных ресурсов игры: асинхронный `fetch` +
  `createImageBitmap` + `canvas.getImageData()` ДО старта игрового кода,
  затем синхронный доступ через `ImageIO.read(path)` — ровно как ожидает
  decomp-код, написанный в расчёте на синхронную classpath-загрузку.
- **`qg`/`MinecraftApplet`** — no-op заглушки (звук и AWT-апплет
  соответственно) с сохранёнными публичными сигнатурами оригинала — весь
  вызывающий код компилируется без изменений. См. `PATCHES.md`.

## PATCHES.md

Полный аудит каждого отклонения от оригинального decomp-кода: какие файлы
исключены из сборки и почему, какие заменены целиком, какие патчатся
точечно (с точным "было/стало"). Обязательно к прочтению перед
копированием дерева `net/minecraft/client/*` в проект.

## Важные находки при анализе decomp (см. комментарии в коде)

1. **VBO-путь мёртв.** `Tessellator` (`is.java` в decomp) проверяет
   ARB_vertex_buffer_object через `this.x = c && GLContext...`, где `c`
   жёстко `= false`. Значит единственный реально исполняемый путь геометрии
   — client-side vertex arrays (`glVertexPointer(size, stride, FloatBuffer)`),
   уже полностью реализован.
2. **`buffer.position()` как base offset.** LWJGL `glXxxPointer(..., Buffer)`
   использует текущую позицию буфера как смещение чтения — decomp явно
   этим пользуется (один `FloatBuffer` под разные атрибуты через
   `position(N)` перед каждым вызовом). Учтено в `GLBridge`.
3. **`glDisableClientState` обязателен.** Decomp честно включает/выключает
   client-side массивы вокруг каждого `glDrawArrays` — наша реализация
   обязана реально гасить атрибут (не no-op), иначе состояние "утечёт"
   между несвязанными draw call'ами.
4. **Display list recording должен захватывать *Pointer-вызовы.** Не только
   `glDrawArrays`/`glColor*`/`glNormal*`, но и сами `glVertexPointer` и
   аналоги — иначе список с несколькими парами (Pointer, DrawArrays) внутри
   одного `glNewList`/`glEndList` при воспроизведении рисует все части
   одними и теми же последними записанными вершинными данными.
5. **decomp использует только числовые scancode**, не `Keyboard.KEY_*`
   константы — таблица перевода DOM `KeyboardEvent.code` →
   DirectInput-scancode нужна полная (см. `KeyboardBridge`).
6. **Звуковая подсистема тянется транзитивно через одно поле.**
   `Minecraft.java` содержит `public qg A = new qg();` — если бы `qg`
   остался оригинальным классом, TeaVM попытался бы скомпилировать ВЕСЬ
   `paulscode.sound.*` + `com.jcraft.jogg/jorbis` (реальные потоки,
   `javax.sound.midi`, LWJGL OpenAL) просто из-за этого одного поля, даже
   если реальный звук в этой сессии не нужен. Решение — заменить `qg` на
   no-op заглушку с идентичными публичными сигнатурами (см. `PATCHES.md`).
7. **`oi.java` (grab/ungrab мыши) кинул бы NPE в реальном игровом пути.**
   Использует `Component.getWidth()/getHeight()` на объекте, который в
   веб-версии всегда `null` (AWT canvas не используется) — вызывается при
   каждом открытии паузы (`Minecraft.f()`), не мёртвый код. Патч:
   `Display.getWidth()/getHeight()` вместо AWT `Component`.
8. **Пути ресурсов — только строковые литералы**, но не всегда прямо
   внутри `getResource(...)` — например, `/font/default.png` передаётся
   через промежуточную переменную. Полный манифест собирается grep'ом ВСЕХ
   строковых литералов вида `"/path.png"` по всему дереву, а не только
   аргументов `getResource`.
9. **Настройки (`gq.java`) используют `java.io.File`/`FileReader`/
   `FileWriter`** для персистентности — сохранение миров, вероятно, тоже
   (не проверено). Нужен мост на `localStorage`/`IndexedDB` — не начато,
   см. `TODO.md` "Этап 3.5".

## Сборка

```
mvn package
```

Собирает через `teavm-maven-plugin`, кладёт `alpha126.js` + `web/index.html`
в `target/site/`. GitHub Actions (`.github/workflows/build.yml`) делает то
же самое при каждом push и публикует `target/site` как артефакт сборки.

**Важно:** сборка НЕ проверялась локально (среда разработки без сети/JDK —
только вычитка кода вручную). Первый прогон CI может вскрыть ошибки —
особенно в местах, явно помеченных как "риск" в комментариях
(`Float32ArrayFactory`/`Uint8ArrayFactory` — выбран самый консервативный
путь через `ArrayBuffer`, но версии TeaVM API стоит перепроверить по
логам первой сборки).

## Открыть локально после сборки

```
cd target/site && python3 -m http.server 8000
```
Открыть `http://localhost:8000` — должна появиться сцена с текстурированным
полом и вращающимися кубами, управление WASD + мышь (клик для захвата).
