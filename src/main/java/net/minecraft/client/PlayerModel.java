package net.minecraft.client;

import net.minecraft.client.web.SkinStore;

/**
 * Модель игрока с поддержкой современных скинов (64x64).
 *
 * Классический режим (скин 64x32 или без скина) — ровно прежняя модель dc.
 * Современный режим перестраивает части под раскладку 64x64:
 *   - отдельные левая рука (32,48) и левая нога (16,48) вместо зеркалирования;
 *   - второй слой одежды: куртка (16,32), рукава (40,32)/(48,48),
 *     штаны (0,32)/(0,48) — чуть больше базового слоя;
 *   - тонкие "Alex"-руки (3 px) в режиме Slim.
 * Раскладка выбирается на каждом кадре по SkinStore, поэтому смена скина в
 * меню применяется сразу. Анимацию (шаг, взмах, приседание) по-прежнему
 * считает dc.a(...): оверлеи просто копируют позу своих базовых частей.
 */
public class PlayerModel extends dc {

    public ka jacket, rightSleeve, leftSleeve, rightPants, leftPants;
    private final float pad;
    private int built = 0;   // 0 = классика, 1 = 64x64 classic, 2 = 64x64 slim

    public PlayerModel(float pad) {
        super(pad);
        this.pad = pad;
    }

    private int wanted() {
        if (!SkinStore.isModern()) return 0;
        return SkinStore.isSlim() ? 2 : 1;
    }

    /** Приводит раскладку частей в соответствие текущему скину. Вызывать перед обращением к частям. */
    public void prepare() {
        int want = wanted();
        if (want != built) {
            build(want);
            built = want;
        }
    }

    private void freeAll() {
        ka[] all = { a, b, c, d, e, f, g, jacket, rightSleeve, leftSleeve, rightPants, leftPants };
        for (ka p : all) {
            if (p != null) p.free();
        }
    }

    private void build(int state) {
        freeAll();
        boolean modern = state != 0;
        boolean slim = state == 2;
        int th = modern ? 64 : 32;
        float p = this.pad;
        int aw = slim ? 3 : 4;
        float ax = slim ? -2.0f : -3.0f;       // правая рука: левый край бокса
        float ay = slim ? 2.5f : 2.0f;         // у тонких рук плечо на полпикселя ниже

        a = new ka(0, 0, th);
        a.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, p);
        a.a(0.0f, 0.0f, 0.0f);
        b = new ka(32, 0, th);
        b.a(-4.0f, -8.0f, -4.0f, 8, 8, 8, p + 0.5f);
        b.a(0.0f, 0.0f, 0.0f);
        c = new ka(16, 16, th);
        c.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, p);
        c.a(0.0f, 0.0f, 0.0f);

        d = new ka(40, 16, th);
        d.a(ax, -2.0f, -2.0f, aw, 12, 4, p);
        d.a(-5.0f, ay, 0.0f);
        if (modern) {
            e = new ka(32, 48, th);
        } else {
            e = new ka(40, 16, th);
            e.g = true;
        }
        e.a(-1.0f, -2.0f, -2.0f, aw, 12, 4, p);
        e.a(5.0f, ay, 0.0f);

        f = new ka(0, 16, th);
        f.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, p);
        f.a(-2.0f, 12.0f, 0.0f);
        if (modern) {
            g = new ka(16, 48, th);
        } else {
            g = new ka(0, 16, th);
            g.g = true;
        }
        g.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, p);
        g.a(2.0f, 12.0f, 0.0f);

        if (modern) {
            float o = p + 0.25f;
            jacket = new ka(16, 32, th);
            jacket.a(-4.0f, 0.0f, -2.0f, 8, 12, 4, o);
            jacket.a(0.0f, 0.0f, 0.0f);
            rightSleeve = new ka(40, 32, th);
            rightSleeve.a(ax, -2.0f, -2.0f, aw, 12, 4, o);
            rightSleeve.a(-5.0f, ay, 0.0f);
            leftSleeve = new ka(48, 48, th);
            leftSleeve.a(-1.0f, -2.0f, -2.0f, aw, 12, 4, o);
            leftSleeve.a(5.0f, ay, 0.0f);
            rightPants = new ka(0, 32, th);
            rightPants.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, o);
            rightPants.a(-2.0f, 12.0f, 0.0f);
            leftPants = new ka(0, 48, th);
            leftPants.a(-2.0f, 0.0f, -2.0f, 4, 12, 4, o);
            leftPants.a(2.0f, 12.0f, 0.0f);
        } else {
            jacket = rightSleeve = leftSleeve = rightPants = leftPants = null;
        }
    }

    private static void copyPose(ka from, ka to) {
        to.a = from.a;
        to.b = from.b;
        to.c = from.c;
        to.d = from.d;
        to.e = from.e;
        to.f = from.f;
    }

    @Override
    public void b(float f2, float f3, float f4, float f5, float f6, float f7) {
        prepare();
        if (built == 0) {
            super.b(f2, f3, f4, f5, f6, f7);
            return;
        }
        this.a(f2, f3, f4, f5, f6, f7);       // анимация базовых частей (dc.a)
        copyPose(a, b);                        // шапка/второй слой головы следует за головой
        copyPose(c, jacket);
        copyPose(d, rightSleeve);
        copyPose(e, leftSleeve);
        copyPose(f, rightPants);
        copyPose(g, leftPants);
        a.a(f7);
        c.a(f7);
        d.a(f7);
        e.a(f7);
        f.a(f7);
        g.a(f7);
        b.a(f7);
        jacket.a(f7);
        rightSleeve.a(f7);
        leftSleeve.a(f7);
        rightPants.a(f7);
        leftPants.a(f7);
    }

    /** Второй слой правой руки для вида от первого лица (вызывается после d.a(f)). */
    public void renderHandOverlay(float f2) {
        if (built == 0 || rightSleeve == null) return;
        copyPose(d, rightSleeve);
        rightSleeve.a(f2);
    }
}
