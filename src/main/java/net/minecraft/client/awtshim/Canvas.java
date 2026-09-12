package net.minecraft.client.awtshim;

/**
 * Минимальная замена java.awt.Canvas (см. PATCHES.md — почему не в пакете
 * java.awt). В веб-версии реальный AWT-canvas никогда не создаётся —
 * Minecraft.java всегда получает null для этого параметра (единственный
 * caller — наш будущий WebMinecraft/WebEntryPoint), поэтому вся ветка
 * `this.k != null` в decomp-коде (Minecraft.java) никогда не исполняется.
 * getGraphics() существует только ради компиляции этой мёртвой ветки.
 */
public class Canvas extends Component {
    public Graphics getGraphics() {
        return null;
    }

    /** Мёртвый код в web-порте (this.k всегда null) — возвращаемое значение не имеет значения, важна только компилируемость сигнатуры. */
    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
