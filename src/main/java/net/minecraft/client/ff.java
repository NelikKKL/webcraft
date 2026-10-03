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
import net.minecraft.client.web.TexturePackStore;
import net.minecraft.client.web.WebTexturePack;

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
        // Выбранный пак хранится в localStorage (options.txt в вебе не надёжен).
        String saved = TexturePackStore.getSelected();
        if (saved != null) {
            this.g = saved;
        }
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
        TexturePackStore.setSelected(this.g);
        this.a.a();
        return true;
    }

    /**
     * ПАТЧЕНО для web-порта (см. PATCHES.md): оригинал сканирует texturepacks/
     * на .zip. В браузере список паков ведёт TexturePackStore (zip выбирает
     * пользователь через файловый диалог / drag&drop, см. web/texturepacks.js,
     * и они сохраняются в IndexedDB). Метод вызывается экраном выбора паков
     * раз в секунду — пересобирает список и, если выбранный пак пропал или
     * был заменён, переключается на подходящий и перезагружает текстуры.
     */
    public void a() {
        this.b.clear();
        this.b.add(this.c);
        d selected = this.c;
        for (WebTexturePack p : TexturePackStore.list()) {
            this.b.add(p);
            if (p.a.equals(this.g)) {
                selected = p;
            }
        }
        if (this.a == null) {
            this.a = selected;          // первый вызов из конструктора: активацию делает он
        } else if (this.a != selected) {
            this.a.b();
            this.a = selected;
            this.g = selected.a;
            TexturePackStore.setSelected(this.g);
            this.a.a();
            if (this.e != null && this.e.n != null) {
                this.e.n.b();
            }
        }
    }

    public List b() {
        return new ArrayList(this.b);
    }
}

