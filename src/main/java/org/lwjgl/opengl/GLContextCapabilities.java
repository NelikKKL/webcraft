package org.lwjgl.opengl;

/**
 * Все extension-флаги — реальные public поля (не методы!), как в настоящем
 * LWJGL GLContextCapabilities — decomp обращается к ним как к полям
 * (GLContext.getCapabilities().GL_ARB_vertex_buffer_object). Значения — все
 * false: см. javadoc GLContext.java для обоснования по каждому use-site.
 */
public final class GLContextCapabilities {
    public final boolean GL_ARB_vertex_buffer_object = false;
    public final boolean GL_ARB_occlusion_query = false;
    public final boolean GL_NV_fog_distance = false;
}
