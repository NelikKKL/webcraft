/*
 * ПАТЧЕНО для web-порта — см. PATCHES.md, "em.java" (скриншоты).
 *
 * Оригинал сохраняет PNG на диск через java.io.File + ImageIO.write +
 * org.lwjgl.BufferUtils (не реализован — используется только здесь и в
 * уже исключённом paulscode, см. PATCHES.md). Реальная реализация для
 * веба должна триггерить браузерный download (Blob + <a download>) —
 * отложено, см. TODO.md. Метод сохраняет исходную сигнатуру (String
 * return, те же параметры), чтобы вызывающий код не менялся.
 */
package net.minecraft.client;
import java.io.File;

public class em {
    public static String a(File file, int n2, int n3) {
        return "Screenshots not yet supported in the web port";
    }
}
