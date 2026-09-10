package net.minecraft.client;

import net.minecraft.client.web.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Тестовая точка входа — НЕ настоящий Minecraft. Задача: прогнать через
 * реальный игровой цикл (requestAnimationFrame), GL11-шим (матрицы,
 * display lists, client-side vertex arrays, освещение, туман), Keyboard/
 * Mouse-мосты — чтобы подтвердить, что фундамент реально работает в
 * браузере, прежде чем подключать 519-файловое дерево настоящего
 * Minecraft.java (которое дополнительно требует AWT-заглушек, ImageIO->
 * fetch моста для текстур и звуковой подсистемы — следующие сессии).
 *
 * Сцена: комната с текстурированным полом (procedural checkerboard
 * texture, чтобы не тащить внешние ассеты) и несколько вращающихся кубов,
 * плюс WASD+мышь свободная камера. Использует display list для куба
 * (проверяет glNewList/glCallList), client-side vertex arrays (проверяет
 * FloatBuffer-путь glVertexPointer), fog и directional lighting.
 */
public final class WebEntryPoint {

    private static float camX = 0, camY = 2, camZ = 5;
    private static float yaw = 0, pitch = 0;
    private static int cubeDisplayList;
    private static int floorTexture;
    private static long lastFrameMs;
    private static float spinAngle = 0;

    public static void main(String[] args) throws Exception {
        Display.setDisplayMode(new DisplayMode(960, 540));
        Display.setTitle("Minecraft Alpha web port — GL bridge smoke test");
        Display.create();

        Keyboard.create();
        Mouse.create();

        setupGlState();
        floorTexture = createCheckerTexture();
        cubeDisplayList = buildCubeDisplayList();

        logStatus("GL bridge initialized: " + GL11.glGetString(7937) + " / " + GL11.glGetString(7938));

        lastFrameMs = (long) now();
        requestFrame(WebEntryPoint::frame);
    }

    private static void setupGlState() {
        GL11.glClearColor(0.53f, 0.81f, 0.92f, 1.0f); // небо
        GL11.glEnable(2929); // GL_DEPTH_TEST
        GL11.glDepthFunc(515); // GL_LEQUAL
        GL11.glEnable(2884); // GL_CULL_FACE
        GL11.glCullFace(1029); // GL_BACK

        GL11.glEnable(2912); // GL_FOG
        GL11.glFogi(2917, 2048); // GL_FOG_MODE = GL_EXP
        GL11.glFogf(2914, 0.02f); // GL_FOG_DENSITY
        GL11.glFog(2918, floatBuf(0.53f, 0.81f, 0.92f, 1.0f)); // GL_FOG_COLOR

        GL11.glEnable(2896); // GL_LIGHTING
        GL11.glLight(16384, 4611, floatBuf(0.3f, 1.0f, 0.5f, 0.0f)); // LIGHT0 position (directional)
        GL11.glLight(16384, 4609, floatBuf(1.0f, 1.0f, 0.95f, 1.0f)); // LIGHT0 diffuse
        GL11.glLightModel(2899, floatBuf(0.35f, 0.35f, 0.4f, 1.0f)); // ambient
    }

    /** Симулирует то, как decomp реально пишет вершины через FloatBuffer + glVertexPointer/glColorPointer/glDrawArrays — проверка client-side array пути. */
    private static int buildCubeDisplayList() {
        int list = GL11.glGenLists(1);
        GL11.glNewList(list, 0 /* GL_COMPILE не критичен для нашей эмуляции — записывается всегда */);

        float[] verts = cubeVertices();
        FloatBuffer vb = ByteBuffer.allocateDirect(verts.length * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vb.put(verts).flip();

        // interleaved: pos3, normal-placeholder3 -> stride 24 bytes; каждая
        // грань задаёт свой vertex pointer с новым position() перед draw
        // (см. drawCubeFaces) — проверка того самого паттерна из decomp
        // (is.java: buffer.position(N) перед каждым glXxxPointer).
        GL11.glEnableClientState(32884); // GL_VERTEX_ARRAY

        GL11.glColor4f(1f, 1f, 1f, 1f);
        drawCubeFaces(vb);

        GL11.glDisableClientState(32884);
        GL11.glEndList();
        return list;
    }

    private static void drawCubeFaces(FloatBuffer vb) {
        // 6 граней по 4 вершины (GL_QUADS - проверяет эмуляцию квадов в GLBridge)
        float[][] normals = {
            {0,0,1}, {0,0,-1}, {1,0,0}, {-1,0,0}, {0,1,0}, {0,-1,0}
        };
        for (int face = 0; face < 6; face++) {
            GL11.glNormal3f(normals[face][0], normals[face][1], normals[face][2]);
            vb.position(face * 4 * 6); // 4 вершины * 6 floats(pos3+normal3, normal здесь не читается GL - см. выше) на грань
            GL11.glVertexPointer(3, 24, vb);
            GL11.glDrawArrays(7 /* GL_QUADS */, 0, 4);
        }
    }

    private static float[] cubeVertices() {
        // 6 граней * 4 вершины * (pos3 + normal3 placeholder) — normal не
        // используется через array path в этом тесте (см. drawCubeFaces),
        // но держим единый stride 24 байта ради единообразия с decomp-форматом.
        float[] p = {
            // front
            -0.5f,-0.5f, 0.5f,0,0,0,  0.5f,-0.5f, 0.5f,0,0,0,  0.5f, 0.5f, 0.5f,0,0,0, -0.5f, 0.5f, 0.5f,0,0,0,
            // back
             0.5f,-0.5f,-0.5f,0,0,0, -0.5f,-0.5f,-0.5f,0,0,0, -0.5f, 0.5f,-0.5f,0,0,0,  0.5f, 0.5f,-0.5f,0,0,0,
            // right
             0.5f,-0.5f, 0.5f,0,0,0,  0.5f,-0.5f,-0.5f,0,0,0,  0.5f, 0.5f,-0.5f,0,0,0,  0.5f, 0.5f, 0.5f,0,0,0,
            // left
            -0.5f,-0.5f,-0.5f,0,0,0, -0.5f,-0.5f, 0.5f,0,0,0, -0.5f, 0.5f, 0.5f,0,0,0, -0.5f, 0.5f,-0.5f,0,0,0,
            // top
            -0.5f, 0.5f, 0.5f,0,0,0,  0.5f, 0.5f, 0.5f,0,0,0,  0.5f, 0.5f,-0.5f,0,0,0, -0.5f, 0.5f,-0.5f,0,0,0,
            // bottom
            -0.5f,-0.5f,-0.5f,0,0,0,  0.5f,-0.5f,-0.5f,0,0,0,  0.5f,-0.5f, 0.5f,0,0,0, -0.5f,-0.5f, 0.5f,0,0,0,
        };
        return p;
    }

    /** Procedural 16x16 черно-белая шахматная текстура — не тащим внешние ассеты для smoke-теста. */
    private static int createCheckerTexture() {
        int size = 16;
        ByteBuffer buf = ByteBuffer.allocateDirect(size * size * 4).order(ByteOrder.nativeOrder());
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                boolean light = ((x / 4) + (y / 4)) % 2 == 0;
                byte v = (byte) (light ? 0xE0 : 0x40);
                buf.put(v).put(v).put(v).put((byte) 0xFF);
            }
        }
        buf.flip();

        int tex = GL11.glGenTextures();
        GL11.glBindTexture(3553, tex); // GL_TEXTURE_2D
        GL11.glTexParameteri(3553, 10241, 9728); // GL_TEXTURE_MIN_FILTER = GL_NEAREST
        GL11.glTexParameteri(3553, 10240, 9728); // GL_TEXTURE_MAG_FILTER = GL_NEAREST
        GL11.glTexImage2D(3553, 0, 6408, size, size, 0, 6408, 5121, buf);
        return tex;
    }

    private static void frame(double timestampMs) {
        long nowMs = (long) timestampMs;
        float dt = Math.min(0.05f, (nowMs - lastFrameMs) / 1000f);
        lastFrameMs = nowMs;

        pollInput(dt);
        render();

        requestFrame(WebEntryPoint::frame);
    }

    private static void pollInput(float dt) {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == 0x01) { // ESCAPE
                Mouse.setGrabbed(false);
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
                Mouse.setGrabbed(true);
            }
        }

        int dx = Mouse.getDX();
        int dy = Mouse.getDY();
        yaw += dx * 0.15f;
        pitch = clamp(pitch + dy * 0.15f, -89f, 89f);

        float speed = 4.0f * dt;
        float radYaw = (float) Math.toRadians(yaw);
        float fx = (float) Math.sin(radYaw);
        float fz = -(float) Math.cos(radYaw);
        float rx = (float) Math.sin(radYaw + Math.PI / 2);
        float rz = -(float) Math.cos(radYaw + Math.PI / 2);

        if (Keyboard.isKeyDown(0x11)) { camX += fx * speed; camZ += fz * speed; } // W
        if (Keyboard.isKeyDown(0x1F)) { camX -= fx * speed; camZ -= fz * speed; } // S
        if (Keyboard.isKeyDown(0x1E)) { camX -= rx * speed; camZ -= rz * speed; } // A
        if (Keyboard.isKeyDown(0x20)) { camX += rx * speed; camZ += rz * speed; } // D
        if (Keyboard.isKeyDown(0x39)) { camY += speed; } // SPACE
        if (Keyboard.isKeyDown(0x2A)) { camY -= speed; } // LSHIFT

        spinAngle += 45f * dt;
    }

    private static float clamp(float v, float lo, float hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private static void render() {
        int w = Display.getWidth();
        int h = Display.getHeight();
        GL11.glViewport(0, 0, w, h);

        GL11.glClear(16384 | 256); // GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT

        GL11.glMatrixMode(5889); // GL_PROJECTION
        GL11.glLoadIdentity();
        GLU.gluPerspective(70f, (float) w / h, 0.05f, 100f);

        GL11.glMatrixMode(5888); // GL_MODELVIEW
        GL11.glLoadIdentity();
        GL11.glRotatef(pitch, 1, 0, 0);
        GL11.glRotatef(yaw, 0, 1, 0);
        GL11.glTranslatef(-camX, -camY, -camZ);

        drawFloor();
        drawCubes();
    }

    private static void drawFloor() {
        GL11.glEnable(3553); // GL_TEXTURE_2D
        GL11.glBindTexture(3553, floorTexture);
        GL11.glDisable(2896); // без освещения для пола, чтобы четко видеть текстуру

        float[] floorData = {
            // x, y, z,  u, v
            -20, 0, -20,  0, 0,
             20, 0, -20,  20, 0,
             20, 0,  20,  20, 20,
            -20, 0,  20,  0, 20,
        };
        FloatBuffer fb = ByteBuffer.allocateDirect(floorData.length * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(floorData).flip();

        GL11.glEnableClientState(32884); // GL_VERTEX_ARRAY
        GL11.glEnableClientState(32888); // GL_TEXTURE_COORD_ARRAY
        fb.position(0);
        GL11.glVertexPointer(3, 20, fb);
        fb.position(3);
        GL11.glTexCoordPointer(2, 20, fb);
        GL11.glDrawArrays(7, 0, 4); // GL_QUADS
        GL11.glDisableClientState(32888);
        GL11.glDisableClientState(32884);

        GL11.glEnable(2896);
        GL11.glDisable(3553);
    }

    private static void drawCubes() {
        int[][] positions = { {0,1,0}, {3,1,-2}, {-3,1,2}, {5,1,4} };
        for (int[] p : positions) {
            GL11.glPushMatrix();
            GL11.glTranslatef(p[0], p[1], p[2]);
            GL11.glRotatef(spinAngle, 0.4f, 1f, 0.2f);
            GL11.glColor4f(0.8f, 0.3f, 0.3f, 1f);
            GL11.glCallList(cubeDisplayList);
            GL11.glPopMatrix();
        }
    }

    private static FloatBuffer floatBuf(float... values) {
        FloatBuffer fb = ByteBuffer.allocateDirect(values.length * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(values).flip();
        return fb;
    }

    // --- JS interop: requestAnimationFrame loop + console logging ---

    @JSFunctor
    interface FrameCallback extends JSObject {
        void run(double timestampMs);
    }

    @JSBody(params = { "cb" }, script = "window.requestAnimationFrame(function(t) { cb.run(t); });")
    private static native void requestFrame(FrameCallback cb);

    @JSBody(params = {}, script = "return performance.now();")
    private static native double now();

    @JSBody(params = { "msg" }, script = "console.log(msg); var el = document.getElementById('status'); if (el) el.textContent = msg;")
    private static native void logStatus(String msg);
}
