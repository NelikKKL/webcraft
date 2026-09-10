package net.minecraft.client;

/**
 * ЗАМЕНА оригинального net/minecraft/client/MinecraftApplet.java —
 * см. PATCHES.md. Оригинал — полноценный java.applet.Applet с AWT
 * embedding; в веб-версии игра запускается напрямую в WebEntryPoint без
 * апплета. Minecraft.java хранит объект этого типа в поле this.z и
 * обращается к нему только за this.z.isActive() (единственное реальное
 * использование методов — все остальные обращения защищены null-check,
 * см. PATCHES.md) — эта заглушка всегда "активна".
 */
public class MinecraftApplet {
    public boolean isActive() {
        return true;
    }

    /** Соответствует MinecraftApplet.c() из оригинала (очистка при выходе) — no-op, нет AWT-контейнера для очистки. */
    public void c() {
    }
}
