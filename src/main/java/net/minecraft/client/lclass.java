/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;

public class lclass {
    static private FloatBuffer a = ds.d(16);

    public static void a() {
        GL11.glDisable(2896);
        GL11.glDisable(16384);
        GL11.glDisable(16385);
        GL11.glDisable(2903);
    }

    public static void b() {
        GL11.glEnable(2896);
        GL11.glEnable(16384);
        GL11.glEnable(16385);
        GL11.glEnable(2903);
        GL11.glColorMaterial(1032, 5634);
        float f2 = 0.4f;
        float f3 = 0.6f;
        float f4 = 0.0f;
        Vector3D ao2 = Vector3D.b(0.2f, 1.0, -0.7f).b();
        GL11.glLight(16384, 4611, (FloatBuffer)lclass.a(ao2.x, ao2.y, ao2.z, 0.0));
        GL11.glLight(16384, 4609, (FloatBuffer)lclass.a(f3, f3, f3, 1.0f));
        GL11.glLight(16384, 4608, (FloatBuffer)lclass.a(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, (FloatBuffer)lclass.a(f4, f4, f4, 1.0f));
        ao2 = ao2.b(-0.2f, 1.0, 0.7f).b();
        GL11.glLight(16385, 4611, (FloatBuffer)lclass.a(ao2.x, ao2.y, ao2.z, 0.0));
        GL11.glLight(16385, 4609, (FloatBuffer)lclass.a(f3, f3, f3, 1.0f));
        GL11.glLight(16385, 4608, (FloatBuffer)lclass.a(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, (FloatBuffer)lclass.a(f4, f4, f4, 1.0f));
        GL11.glShadeModel(7424);
        GL11.glLightModel(2899, (FloatBuffer)lclass.a(f2, f2, f2, 1.0f));
    }

    private static FloatBuffer a(double d2, double d3, double d4, double d5) {
        return lclass.a((float)d2, (float)d3, (float)d4, (float)d5);
    }

    private static FloatBuffer a(float f2, float f3, float f4, float f5) {
        a.clear();
        a.put(f2).put(f3).put(f4).put(f5);
        a.flip();
        return a;
    }
}

