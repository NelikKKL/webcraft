# PATCHES.md — все отклонения от decomp-исходников

Каждое изменение здесь — осознанное и обосновано ниже. Цель: минимальные,
точечные, легко проверяемые правки — НЕ переписывание игровой логики.

## Веб-обвязка: экран загрузки/краша, упаковка ассетов (не decomp-патч)

Не изменение decomp-кода, а обвязка вокруг него — упомянуто здесь ради
единой картины изменений. См. TODO.md, раздел "UI: экран загрузки/краша"
за полным описанием. Кратко: `web/assets.zip` (все 44 текстуры одним
архивом, распаковка через JSZip с CDN в браузере) вместо 44 отдельных
файлов; `index.html` — экран загрузки на `background.png` с прогресс-баром
и экран краша (белая панель, лог, кнопка copy); `WebEntryPoint`/
`WebMinecraft` пробрасывают ошибки в `window.__showCrashScreen` вместо
старого текстового статуса.

## Пакет `net.minecraft.client.awtshim` (НЕ `java.awt`/`javax.imageio`)

Наши шимы `BufferedImage`/`Graphics`/`Color`/`WritableRaster`/`DataBuffer`/
`DataBufferInt`/`ImageIO` живут в `net.minecraft.client.awtshim`, а НЕ в
`java.awt`/`java.awt.image`/`javax.imageio`, где им было бы "естественно"
находиться. Причина: JDK 9+ Java Platform Module System запрещает
пользовательскому коду объявлять классы в пакетах, принадлежащих
системным модулям (`java.desktop` включает весь `java.awt.*`/
`javax.imageio.*`) — обычный `javac` (запускается ДО `teavm-maven-plugin`
через `maven-compiler-plugin`) выдаёт "package exists in another module"
и множество каскадных ошибок (подтверждено первым реальным прогоном CI —
см. TODO.md).

**Следствие для копирования decomp-дерева:** любой файл с
`import java.awt.Color;` / `java.awt.Graphics;` /
`java.awt.image.BufferedImage;` / `java.awt.image.DataBufferInt;` /
`javax.imageio.ImageIO;` нужно патчить — заменять на
`net.minecraft.client.awtshim.<ИмяКласса>`. Это ДОПОЛНИТЕЛЬНО к точечным
патчам ниже (те меняют сами вызовы `getResource`, эти — import-строки).

## Пакет `net.minecraft.client.netshim` (НЕ `java.net`)

Та же причина, что и у `awtshim` выше: `java.net` — тоже часть системного
модуля `java.base` (присутствует ВСЕГДА, в отличие от `java.desktop`,
который хотя бы теоретически можно исключить) — писать свои классы прямо
в пакет `java.net` нельзя (подтверждено реальной ошибкой сборки третьего
CI-прогона: "Class java.net.Socket was not found" — TeaVM classlib не
включает `Socket`/`ConnectException`).

Наши `Socket`/`ConnectException` живут в `net.minecraft.client.netshim`.
Патчены импорты в `oy.java`, `jq.java`, `ib.java` (`import java.net.Socket;`
→ `import net.minecraft.client.netshim.Socket;`, аналогично для
`ConnectException`). `Socket`-конструктор **всегда** бросает
`ConnectException` — семантически корректное поведение (браузер не может
открыть сырой TCP), не временная заглушка для обхода компиляции.

**Важно:** `SocketAddress`/`SocketException`/`UnknownHostException`/`URL`/
`MalformedURLException` НЕ патчились — не были в списке ошибок сборки
(хотя `InetAddress` изначально ошибочно предполагался поддерживаемым по
той же логике и оказался НЕ поддерживаемым — см. TODO.md, "четвёртый
прогон": отсутствие в одном батче ошибок не гарантирует поддержку,
только последующий прогон даёт полную уверенность).

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
| `net/minecraft/client/my.java` | Используется только `ah` (см. выше) — thread helper для isom-инструмента, нигде не инстанцируется |
| `net/minecraft/client/mz.java` | То же — `Session`-подкласс для isom-инструмента, нигде не инстанцируется |
| `net/minecraft/client/fj.java` | `extends paulscode.sound.codecs.CodecJOrbis` — стриминг музыки по URL, звуковая функциональность |
| `net/minecraft/client/in.java` | Используется только `fj.java` (см. выше) — XOR-деobfuscation поток для стриминга |
| `net/minecraft/client/ResourcesDownloader.java` | Скачивание доп. звуковых ресурсов по HTTP из S3 — ссылка нерабочая уже в оригинальной desktop-игре (комментарий в исходнике: "This link is broken"); использует java.net.URL+java.io.File+XML-парсинг+реальные Thread — TeaVM не поддерживает `javax.xml.parsers.DocumentBuilderFactory` (подтверждено ошибкой сборки). 4 точки использования в Minecraft.java (поле `Q`, конструктор+start, cleanup, force-reload) пропатчены на no-op. |
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
Патч: `this.c.getWidth()`/`getHeight()` (AWT `Component`, всегда `null`
в веб-версии, т.к. `this.k` в Minecraft.java — AWT canvas — не используется)
заменены на `Display.getWidth()`/`getHeight()` (наш `org.lwjgl.opengl.Display`,
всегда актуален и корректен, т.к. отражает реальный размер `<canvas>`).
Без этого патча `oi.b()` (вызывается при открытии паузы) кидал бы NPE —
это НЕ мёртвый код, реальный игровой путь. Поле `Component c` и
конструктор с параметром — убраны (конструктор стал `oi()` без параметров,
вызывающий код в Minecraft.java пропатчен: `new oi(this.k)` → `new oi()`).
Также убрано поле `Cursor d` и его инициализация (кастомная форма курсора
ОС через `org.lwjgl.input.Cursor`, которого нет в web-порте) — Pointer
Lock браузера и так скрывает курсор в grabbed-режиме.

### `net/minecraft/client/Minecraft.java` — удалён AWT desktop-лаунчер
Методы `a(String,String)`, `a(String,String,String)` (создают
`java.awt.Frame`/`Canvas`, инстанцируют `StandaloneClient` в отдельном
`Thread`) и `main(String[])` (реальная точка входа desktop JAR, звала
`a(String,String)`) — удалены целиком. Это мёртвый код для веб-версии
(реальная точка входа — `WebEntryPoint.main()`/будущий `WebMinecraft`),
и он ссылался на исключённые типы `StandaloneClient`/`gn` — оставлять
значило бы либо восстанавливать эти классы, либо ловить ошибку компиляции.
Также убраны теперь-неиспользуемые импорты `BorderLayout`/`Dimension`/
`Frame`; `Canvas`/`Component`/`Graphics` остались (используются в других,
реально достижимых местах класса) и перенесены на `awtshim`-версии.

### `net/minecraft/client/bp.java` (GuiScreen — базовый класс экранов)
Один метод, `public static String c()` (чтение системного буфера обмена
через `java.awt.Toolkit`/`datatransfer` — для Ctrl+V в текстовых полях),
заменён на `return null;`. Остальной класс (обработка мыши/клавиатуры,
рендер фона меню) не тронут. Реальный `navigator.clipboard.readText()`
браузера асинхронный — не вписывается в эту синхронную сигнатуру без
переработки вызывающего кода; отложено, см. TODO.md.

### `net/minecraft/client/em.java` (скриншоты)
Весь класс заменён заглушкой, возвращающей строку "не поддерживается" —
избегает `org.lwjgl.BufferUtils` (не реализован, используется только
здесь и в исключённом paulscode) и `ImageIO.write`/`java.io.File`-based
сохранения (в браузере нет прямого доступа к файловой системе — нужен
Blob+download, не реализовано). Сигнатура метода (`String a(File,int,int)`)
сохранена, чтобы вызывающий код не менялся.

### `net/minecraft/client/ff.java` — убрано сканирование ZIP текстур-паков
Метод `a()` (пересканирование доступных текстур-паков) заменён на простое
`this.a = this.c;` (всегда default-пак). Оригинал сканировал директорию
`texturepacks/` через `java.io.File.listFiles()` и грузил `.zip`-файлы
через `od` (исключён, см. выше). В браузере нет пользовательской файловой
системы с ZIP-паками — кастомные текстур-паки отложены (см. TODO.md).

## Новые LWJGL-шимы, понадобившиеся после интеграции дерева

Эти классы не были нужны для GL/ввод-моста сам по себе (предыдущие
сессии) — их отсутствие проявилось только после копирования реального
decomp-дерева и попытки его скомпилировать (второй прогон CI).

- **`org.lwjgl.opengl.ARBOcclusionQuery`** — используется `f.java`
  (рендерер мира) для occlusion culling чанков — GPU-оптимизация
  видимости, не влияющая на корректность. Заглушка: все запросы всегда
  сообщают "результат готов, объект был виден" — теряем оптимизацию
  (рендерим больше, чем строго нужно), но никогда не скрываем то, что
  реально должно быть видно. Настоящая реализация возможна через нативные
  WebGL2 query objects (`createQuery`/`ANY_SAMPLES_PASSED`) — отложено.
- **`org.lwjgl.Sys`** — только `openURL(String)` (кнопка "Open texture
  pack folder" в `dk.java`). `file://` пути — no-op с логом (нет доступа
  к локальной ФС из браузера); обычные URL — `window.open` в новой
  вкладке.
- **`org.lwjgl.input.Keyboard.enableRepeatEvents(boolean)`** — включает
  повторные KEY_DOWN события при удержании клавиши (зажатие Backspace в
  текстовых полях). DOM и так шлёт `keydown` с `repeat=true` при
  удержании — реализовано через JS-флаг, переключающий, фильтровать эти
  события или нет (раньше фильтровались безусловно).

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

Отдельная группа патчей — TeaVM-специфичные "метод не найден" (не
getResource-связанные), все обнаружены третьим CI-прогоном:

| Файл | Было | Стало | Причина |
|---|---|---|---|
| `Minecraft.java` (`c(String)`) | `System.exit(0);` | `this.H = false;` | `System.exit(int)` не поддерживается TeaVM |
| `nl.java` (F3-экран) | Блок из 4 строк с `Runtime.getRuntime().maxMemory/totalMemory/freeMemory()` | Убран целиком | Не поддерживается TeaVM |
| `ha.java` | `Thread.dumpStack();` | Убран (оставлен соседний `System.out.println`) | Не поддерживается TeaVM |
| `pe.java` (x2) | `jq.e(this.a).stop();` / `jq.f(this.a).stop();` (внутри try/catch(Throwable)) | Убраны целиком | `Thread.stop()` не поддерживается TeaVM |

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
