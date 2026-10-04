package net.minecraft.client;

import net.minecraft.client.web.SkinStore;
import org.lwjgl.opengl.GL11;

/**
 * Экран "Skin & Name" (Options -> Skin & Name...): ник, файл скина, режим рук
 * и живой предпросмотр модели. Оформлен штатными виджетами игры (кнопки gh/r,
 * шрифт ls, фон меню), поле ввода нарисовано в том же стиле.
 */
public class SkinScreen extends bp {

    private final bp parent;
    private final PlayerModel model = new PlayerModel(0.0f);
    private String nick;
    private boolean focused = true;
    private int tick = 0;

    public SkinScreen(bp parent) {
        this.parent = parent;
        this.nick = SkinStore.nick();
    }

    private int fieldX() { return this.c / 2 - 100; }
    private int fieldY() { return 56; }

    @Override
    public void a() {
        int x = this.c / 2 - 100;
        this.e.add(new gh(1, x, 86, 200, 20, "Choose skin file..."));
        this.e.add(new gh(2, x, 110, 98, 20, "Reset skin"));
        this.e.add(new gh(3, x + 102, 110, 98, 20, "Arms: " + SkinStore.armModeName()));
        this.e.add(new gh(200, x, this.d / 6 + 168, 200, 20, "Done"));
    }

    private void close() {
        this.b.applyNick(this.nick);
        this.b.a(this.parent);
    }

    @Override
    protected void a(gh gh2) {
        if (!gh2.g) {
            return;
        }
        if (gh2.f == 1) {
            SkinStore.chooseFile();
        }
        if (gh2.f == 2) {
            SkinStore.reset();
        }
        if (gh2.f == 3) {
            SkinStore.cycleArmMode();
            gh2.e = "Arms: " + SkinStore.armModeName();
        }
        if (gh2.f == 200) {
            this.close();
        }
    }

    @Override
    protected void a(int n2, int n3, int n4) {
        if (n4 == 0) {
            this.focused = n2 >= fieldX() && n2 < fieldX() + 200 && n3 >= fieldY() && n3 < fieldY() + 20;
        }
        super.a(n2, n3, n4);
    }

    @Override
    protected void a(char c2, int n2) {
        if (n2 == 1 || n2 == 28 || n2 == 156) {      // Esc / Enter
            this.close();
            return;
        }
        if (!this.focused) {
            return;
        }
        if (n2 == 14) {                                // Backspace
            if (this.nick.length() > 0) {
                this.nick = this.nick.substring(0, this.nick.length() - 1);
            }
            return;
        }
        boolean ok = (c2 >= 'a' && c2 <= 'z') || (c2 >= 'A' && c2 <= 'Z') || (c2 >= '0' && c2 <= '9') || c2 == '_';
        if (ok && this.nick.length() < 16) {
            this.nick = this.nick + c2;
        }
    }

    @Override
    public void g() {
        super.g();
        ++this.tick;
    }

    @Override
    public void a(int n2, int n3, float f2) {
        this.i();
        this.a(this.g, "Skin & Name", this.c / 2, 20, 0xFFFFFF);

        // Поле ника
        int fx = fieldX();
        int fy = fieldY();
        this.b(this.g, "Nickname", fx, fy - 12, 0xA0A0A0);
        this.a(fx - 1, fy - 1, fx + 201, fy + 21, this.focused ? 0xFFFFFFFF : 0xFFA0A0A0);
        this.a(fx, fy, fx + 200, fy + 20, 0xFF000000);
        String shown = this.nick + (this.focused && (this.tick / 6) % 2 == 0 ? "_" : "");
        this.b(this.g, shown, fx + 4, fy + 6, 0xE0E0E0);

        // Информация о скине
        this.a(this.g, SkinStore.describe(), this.c / 2, 136, 0xFFFFFF);
        this.a(this.g, "PNG 64x32, 64x64 or HD. Arms: Auto detects Alex (slim).", this.c / 2, 148, 0x808080);

        super.a(n2, n3, f2);
        this.drawPreview(f2);
    }

    /** Предпросмотр: та же цепочка трансформаций, что у игрока в инвентаре и рендерера мобов. */
    private void drawPreview(float f2) {
        if (this.c < 330) {
            return;
        }
        int tex = this.b.n.a(SkinStore.texturePath());
        float px = this.c / 2 + 160;
        float py = 130;
        float s = 38.0f;
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glClear(256);
        GL11.glPushMatrix();
        GL11.glTranslatef(px, py, 100.0f);
        GL11.glScalef(-s, s, s);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        lclass.b();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f + ((float)this.tick + f2) * 2.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glTranslatef(0.0f, -24.0f * 0.0625f - 0.0078125f, 0.0f);
        GL11.glBindTexture(3553, tex);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.model.k = 0.0f;
        this.model.b(0.0f, 0.0f, (float)this.tick + f2, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
        lclass.a();
        GL11.glDisable(32826);
    }
}
