package org.lwjgl.opengl;

import java.nio.IntBuffer;

/**
 * Шим org.lwjgl.opengl.ARBOcclusionQuery. Используется в f.java (рендерер
 * мира) для occlusion culling — оптимизации, пропускающей рендер полностью
 * скрытых чанков через GPU-запрос "было ли что-то видно за предыдущий
 * кадр". Это ЧИСТО оптимизация, не влияющая на корректность — WebGL2
 * реально поддерживает похожий механизм (createQuery/ANY_SAMPLES_PASSED),
 * но реализация отложена (см. TODO.md); пока что все запросы всегда
 * сообщают "результат готов, объект был виден" — безопасная деградация:
 * теряем производительность (рендерим больше, чем строго нужно), но
 * НИКОГДА не скрываем то, что реально должно быть видно.
 */
public final class ARBOcclusionQuery {
    private ARBOcclusionQuery() {}

    public static final int GL_QUERY_RESULT_ARB = 34918;
    public static final int GL_QUERY_RESULT_AVAILABLE_ARB = 34919;
    public static final int GL_SAMPLES_PASSED_ARB = 35092;

    private static int nextId = 1;

    public static void glGenQueriesARB(IntBuffer buffer) {
        int pos = buffer.position();
        int limit = buffer.limit();
        for (int i = pos; i < limit; i++) {
            buffer.put(i, nextId++); // ненулевые уникальные ID (0 обычно означает "запроса нет")
        }
    }

    public static void glBeginQueryARB(int target, int id) {
        // no-op: см. javadoc класса — реальный GPU-запрос не выполняется.
    }

    public static void glEndQueryARB(int target) {
        // no-op: см. javadoc класса.
    }

    public static void glGetQueryObjectuARB(int id, int pname, IntBuffer params) {
        // Всегда "готово" (GL_QUERY_RESULT_AVAILABLE_ARB) и "видимо, все
        // сэмплы прошли" (GL_QUERY_RESULT_ARB) — см. javadoc класса.
        params.put(params.position(), 1);
    }
}
