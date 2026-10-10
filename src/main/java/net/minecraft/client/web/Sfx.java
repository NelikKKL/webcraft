package net.minecraft.client.web;

import org.teavm.jso.JSBody;

/**
 * Тонкая обёртка над window.Sounds (web/extras.js, раздел "Звук (Web Audio)").
 * Сам движок — в JS: пул звуков с оригинальной раскладкой ресурсов (sound/, music/,
 * streaming/), позиционное воспроизведение через PannerNode, музыка и пластинки через
 * <audio>, процедурные заменители для звуков без файлов.
 */
public final class Sfx {

    private Sfx() {}

    /** Эффект. gain уже включает громкость настроек; positional — 3D с затуханием на maxDist блоков. */
    @JSBody(params = { "name", "x", "y", "z", "gain", "pitch", "positional", "maxDist" }, script =
        "return window.Sounds ? !!window.Sounds.play(name, x, y, z, gain, pitch, positional, maxDist) : false;")
    public static native boolean play(String name, float x, float y, float z, float gain, float pitch,
                                      boolean positional, float maxDist);

    @JSBody(params = { "x", "y", "z", "lx", "ly", "lz" }, script =
        "if (window.Sounds) { window.Sounds.setListener(x, y, z, lx, ly, lz); }")
    public static native void listener(float x, float y, float z, float lx, float ly, float lz);

    // ---- фоновая музыка
    @JSBody(script = "return window.Sounds ? !!window.Sounds.hasMusic() : false;")
    public static native boolean hasMusic();

    /** true — трек запущен (false, если музыки нет или браузер ещё не разрешил звук). */
    @JSBody(params = { "volume" }, script = "return window.Sounds ? !!window.Sounds.music(volume) : false;")
    public static native boolean startMusic(float volume);

    @JSBody(script = "return window.Sounds ? !!window.Sounds.musicPlaying() : false;")
    public static native boolean musicPlaying();

    @JSBody(params = { "volume" }, script = "if (window.Sounds) { window.Sounds.musicVolume(volume); }")
    public static native void musicVolume(float volume);

    @JSBody(script = "if (window.Sounds) { window.Sounds.stopMusic(); }")
    public static native void stopMusic();

    // ---- пластинки (streaming/*.mus)
    @JSBody(params = { "name" }, script = "return window.Sounds ? !!window.Sounds.hasStream(name) : false;")
    public static native boolean hasStream(String name);

    @JSBody(params = { "name", "volume" }, script = "return window.Sounds ? !!window.Sounds.stream(name, volume) : false;")
    public static native boolean startStream(String name, float volume);

    @JSBody(script = "return window.Sounds ? !!window.Sounds.streamPlaying() : false;")
    public static native boolean streamPlaying();

    @JSBody(params = { "volume" }, script = "if (window.Sounds) { window.Sounds.streamVolume(volume); }")
    public static native void streamVolume(float volume);

    @JSBody(script = "if (window.Sounds) { window.Sounds.stopStream(); }")
    public static native void stopStream();
}
