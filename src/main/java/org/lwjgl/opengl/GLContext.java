package org.lwjgl.opengl;

/**
 * Шим org.lwjgl.opengl.GLContext. В decomp используется только для проверки
 * поддержки desktop-GL extensions (ARB_vertex_buffer_object, NV_fog_distance,
 * ARB_occlusion_query — см. grep по всему проекту). WebGL не имеет этих
 * extension-флагов в том же виде, а сами use-site в decomp либо мёртвый код
 * (ARB_vertex_buffer_object проверяется под статически false флагом — см.
 * is.java), либо там, где флаг реально влияет на поведение (kb.java,
 * GL_NV_fog_distance — выбор способа расчёта дистанции тумана), false
 * означает "используй стандартный портативный путь", что корректно и для
 * WebGL, и для любой не-NVIDIA видеокарты в реальном OpenGL.
 */
public final class GLContext {
    private GLContext() {}

    private static final GLContextCapabilities CAPS = new GLContextCapabilities();

    public static GLContextCapabilities getCapabilities() {
        return CAPS;
    }
}
