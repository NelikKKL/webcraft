/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class ff {
    private List b = new ArrayList();
    private d c = new jm();
    public d a;
    private Map d = new HashMap();
    private Minecraft e;
    private File f;
    private String g;

    public ff(Minecraft minecraft, File file) {
        this.e = minecraft;
        this.f = new File(file, "texturepacks");
        if (!this.f.exists()) {
            this.f.mkdirs();
        }
        this.g = minecraft.y.j;
        this.a();
        this.a.a();
    }

    public boolean a(d d2) {
        if (d2 == this.a) {
            return false;
        }
        this.a.b();
        this.g = d2.a;
        this.a = d2;
        this.e.y.j = this.g;
        this.e.y.b();
        this.a.a();
        return true;
    }

    /**
     * ПАТЧЕНО для web-порта (см. PATCHES.md): оригинал сканирует директорию
     * texturepacks/ на .zip файлы и грузит каждый через `od` (ZIP-based
     * текстур-пак — исключён из сборки, см. PATCHES.md, "od.java"). В
     * браузере нет файловой системы с пользовательскими ZIP-файлами —
     * весь блок сканирования убран, всегда используется встроенный
     * default-пак (`this.c`, класс `jm` — работает через наш
     * ResourceCache, не тронут). Кастомные текстур-паки — отложенная
     * фича, см. TODO.md.
     */
    public void a() {
        this.a = this.c;
    }

    public List b() {
        return new ArrayList(this.b);
    }
}

