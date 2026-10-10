package net.minecraft.client;

import java.io.File;
import java.util.Random;

import net.minecraft.client.web.Sfx;

/**
 * Звуковой менеджер (замена оригинального qg.java, который использовал paulscode/OpenAL).
 * Сигнатуры методов сохранены 1:1 с оригиналом; логика (громкости, дальность 16 блоков,
 * множитель 0.25 у "UI-звуков", таймер фоновой музыки 12000..24000 тиков, остановка музыки при
 * пластинке) повторяет байткод оригинала. Воспроизведение — через Web Audio (web/Sfx.java,
 * web/extras.js). Файлы звуков регистрируются на стороне JS (assets.akrile / zip-паки), поэтому
 * a/b/c(String, File) — заглушки.
 */
public class qg {

    private gq f;                                       // настройки: f.a — громкость музыки, f.b — звука
    private final Random h = new Random();
    private int i = this.h.nextInt(12000);              // тиков до следующего трека

    /** Инициализация / смена настроек (qg.a(gq)). */
    public void a(gq gameSettings) {
        this.f = gameSettings;
        this.a();
    }

    /** Каждый тик: громкость музыки; нулевая громкость выключает музыку (qg.a()). */
    public void a() {
        if (this.f == null) {
            return;
        }
        if (this.f.a == 0.0f) {
            Sfx.stopMusic();
        } else {
            Sfx.musicVolume(this.f.a);
        }
    }

    /** Освобождение (qg.b()). */
    public void b() {
        Sfx.stopMusic();
        Sfx.stopStream();
    }

    /** Попытка запустить фоновую музыку (qg.c()) — вызывается каждый тик из контроллера. */
    public void c() {
        if (this.f == null || this.f.a == 0.0f) {
            return;
        }
        if (!Sfx.musicPlaying() && !Sfx.streamPlaying()) {
            if (this.i > 0) {
                --this.i;
                return;
            }
            // Таймер перезапускается, только если трек реально стартовал (браузер мог ещё не разрешить звук).
            if (Sfx.hasMusic() && Sfx.startMusic(this.f.a)) {
                this.i = this.h.nextInt(12000) + 12000;
            }
        }
    }

    /** Регистрация файлов делается на стороне JS (assets.akrile и zip-паки) — см. web/extras.js. */
    public void a(String name, File file) { }
    public void b(String name, File file) { }
    public void c(String name, File file) { }

    /** Положение и ориентация слушателя (камеры) — qg.a(Mob, float). */
    public void a(Mob listener, float partialTick) {
        if (this.f == null || this.f.b == 0.0f || listener == null) {
            return;
        }
        float yaw = listener.aE + (listener.aC - listener.aE) * partialTick;
        double x = listener.at + (listener.aw - listener.at) * (double)partialTick;
        double y = listener.au + (listener.ax - listener.au) * (double)partialTick;
        double z = listener.av + (listener.ay - listener.av) * (double)partialTick;
        float theta = -yaw * 0.017453292f - 3.1415927f;
        float cos = (float)Math.cos(theta), sin = (float)Math.sin(theta);
        Sfx.listener((float)x, (float)y, (float)z, -sin, 0.0f, -cos);
    }

    /** Пластинка (потоковый звук) в позиции — qg.a(String,...). Имя null только останавливает. */
    public void a(String name, float x, float y, float z, float volume, float pitch) {
        if (this.f == null || this.f.b == 0.0f) {
            return;
        }
        if (Sfx.streamPlaying()) {
            Sfx.stopStream();
        }
        if (name == null) {
            return;
        }
        if (Sfx.hasStream(name) && volume > 0.0f) {
            if (Sfx.musicPlaying()) {
                Sfx.stopMusic();
            }
            Sfx.startStream(name, 0.5f * this.f.b);
        }
    }

    /** Позиционный эффект — qg.b(String,...): блоки, мобы и т.д. */
    public void b(String name, float x, float y, float z, float volume, float pitch) {
        if (this.f == null || this.f.b == 0.0f || name == null || volume <= 0.0f) {
            return;
        }
        float dist = 16.0f;
        if (volume > 1.0f) {
            dist *= volume;
        }
        float gain = (volume > 1.0f ? 1.0f : volume) * this.f.b;
        Sfx.play(name, x, y, z, gain, pitch, true, dist);
    }

    /** Непозиционный эффект (UI) — qg.a(String, float, float): громкость x0.25. */
    public void a(String name, float volume, float pitch) {
        if (this.f == null || this.f.b == 0.0f || name == null) {
            return;
        }
        float v = volume > 1.0f ? 1.0f : volume;
        Sfx.play(name, 0.0f, 0.0f, 0.0f, v * 0.25f * this.f.b, pitch, false, 0.0f);
    }
}
