# PATCHES.md — все отклонения от decomp-исходников

Каждое изменение здесь — осознанное и обосновано ниже. Цель: минимальные,
точечные, легко проверяемые правки — НЕ переписывание игровой логики.

## Полностью исключённые файлы (не копируются в web-port)

Причина исключения указана для каждого — либо мёртвый/неигровой код, либо
функциональность, сознательно отложенная на будущую сессию (см. TODO.md).

| Файл | Причина |
|---|---|
| `net/minecraft/isom/**` | Отдельный изометрический preview-инструмент, не часть игры |
| `net/minecraft/client/ah.java` | Используется только `IsomPreviewApplet` (см. выше) |
| `net/minecraft/client/CrashDialog.java` | AWT crash-диалог — заменён консольным логом |
| `net/minecraft/client/do.java` (`_do`) | Используется только `CrashDialog` (рисует лого) |
| `net/minecraft/client/i.java` | AWT-подкласс Minecraft (applet crash handling) — заменён `WebMinecraft` |
| `net/minecraft/client/j.java` | AWT Canvas-подкласс для `MinecraftApplet` — не нужен (свой canvas в DOM) |
| `net/minecraft/client/gn.java` | AWT WindowAdapter (закрытие окна) — нет аналога в браузере |
| `net/minecraft/client/ow.java` | Тривиальный AWT Canvas для UI-подсказки размера — не нужен |
| `net/minecraft/client/StandaloneClient.java` | Desktop-лаунчер с AWT Frame — не нужен |
| `net/minecraft/client/MinecraftApplet.java` (оригинал) | Заменён минимальной заглушкой (см. ниже) |
| `net/minecraft/client/od.java` | ZIP-based текстур-паки (`java.util.zip.ZipFile` + `java.io.File`) — отложено |
| `com/jcraft/jogg/**`, `com/jcraft/jorbis/**` | Ogg Vorbis декодер — часть звуковой подсистемы, отложено (Этап 4, TODO.md) |
| `paulscode/**` | Звуковой движок (потоки, OpenAL, MIDI) — отложено (Этап 4, TODO.md) |

## Полностью заменённые файлы (тот же package/class name, другая реализация)

### `net/minecraft/client/MinecraftApplet.java`
Оригинал — полноценный `java.applet.Applet` с AWT canvas embedding.
Заменён минимальной заглушкой: `Minecraft.java` хранит ссылку на неё в поле
`this.z`, но ВСЕ реальные обращения защищены `null`-проверками
(`this.z != null`, `this.z == null || ...`) кроме `this.z.isActive()`,
поэтому веб-версия всегда передаёт готовый объект-заглушку с
`isActive() { return true; }`, а не `null` — тем самым НЕ нужно патчить
Minecraft.java вообще для этого поля.

### `net/minecraft/client/qg.java` (звуковой менеджер)
Оригинал тянет `paulscode.sound.*` + `com.jcraft.jogg/jorbis` (реальные
Java-потоки, javax.sound.midi, LWJGL OpenAL — ничего из этого не
реализовано в web-порте на данном этапе, см. TODO.md Этап 4). Заменён
no-op заглушкой с ИДЕНТИЧНЫМИ публичными сигнатурами методов (сверено по
исходнику) — весь код, вызывающий методы `qg`, компилируется без изменений,
звук просто не воспроизводится. Настоящая реализация через Web Audio API —
следующая сессия.

### `net/minecraft/client/oi.java` (мышь: grab/ungrab)
Один патч: `this.c.getWidth()`/`getHeight()` (AWT `Component`, всегда `null`
в веб-версии, т.к. `this.k` в Minecraft.java — AWT canvas — не используется)
заменены на `Display.getWidth()`/`getHeight()` (наш `org.lwjgl.opengl.Display`,
всегда актуален и корректен, т.к. отражает реальный размер `<canvas>`).
Без этого патча `oi.b()` (вызывается при открытии паузы) кидал бы NPE —
это НЕ мёртвый код, реальный игровой путь. Поле `Component c` и
конструктор — убраны, т.к. становятся не нужны после патча.

## Точечные патчи (замена одной строки в существующем файле)

Все патчи одного типа: заменяют `SomeClass.class.getResource("/path")` /
`getResourceAsStream(...)` на наш `ResourceIO`/`ImageIO`-путь (см.
README.md — почему не полагаемся на `java.lang.Class.getResourceAsStream`
в TeaVM: поведение непроверяемо в этой среде разработки, поэтому явный,
контролируемый мост надёжнее).

| Файл | Было | Стало |
|---|---|---|
| `ae.java` | `ImageIO.read(Minecraft.class.getResource("/gui/items.png"))` | `ImageIO.read("/gui/items.png")` |
| `ev.java` | `ImageIO.read(ft.class.getResource("/misc/grasscolor.png"))` | `ImageIO.read("/misc/grasscolor.png")` |
| `ft.java` | `ImageIO.read(ft.class.getResource("/misc/foliagecolor.png"))` | `ImageIO.read("/misc/foliagecolor.png")` |
| `gp.java` (x2) | `ImageIO.read(Minecraft.class.getResource("/gui/items.png"))` и `"/misc/dial.png"` | `ImageIO.read("/gui/items.png")` / `ImageIO.read("/misc/dial.png")` |
| `jm.java` | `ImageIO.read(jm.class.getResource("/pack.png"))` | `ImageIO.read("/pack.png")` |
| `nk.java` | `ImageIO.read(nk.class.getResource("/terrain.png"))` | `ImageIO.read("/terrain.png")` |
| `d.java` | `return d.class.getResourceAsStream(string);` | `return net.minecraft.client.web.ResourceIO.getImageResourceAsStream(string);` |
| `ls.java` | `ImageIO.read(fu.class.getResourceAsStream(string))` | `ImageIO.read(string)` (напрямую, т.к. `string` = `/font/default.png`, всегда картиночный путь) |

## Отложенные (НЕ патчатся в этой сессии, задокументировано в TODO.md)

- `em.java` — `ImageIO.write(...)` (скриншот в файл) — нужна реализация через
  Blob+download вместо File I/O. Пока просто не будет работать (исключение
  поймается вызывающим кодом, если он оборачивает в try/catch — ПРОВЕРИТЬ
  при интеграции).
- `mq.java` — `ImageIO.read(httpURLConnection.getInputStream())` — загрузка
  скинов по сети (Mojang API) — требует CORS-совместимого прокси или
  альтернативного источника, не решается на уровне ImageIO-моста.
- `/title/splashes.txt` (`dj.java`) — текстовый ресурс, наш
  ResourcePreloader сейчас прелоадит только картинки. Патч: заменить
  реальную загрузку на небольшой хардкоженный массив строк-заглушек
  (см. TODO.md) — низкий приоритет, чисто косметическая фича (случайная
  фраза под логотипом на титульном экране).
