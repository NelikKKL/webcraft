package net.minecraft.client.web;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

/**
 * FXAA (Fast Approximate Anti-Aliasing) для web-порта.
 *
 * Как это работает:
 *  1. Кадр рисуется не прямо в canvas, а в оффскрин-FBO (текстура RGBA8 +
 *     renderbuffer глубины DEPTH_COMPONENT24) того же размера, что и canvas.
 *  2. Когда 3D-сцена готова (kb.b() вызывает {@link #resolveScene()} прямо
 *     перед отрисовкой GUI), полноэкранный проход с шейдером FXAA 3.11
 *     (luma-детектор краёв + поиск конца края + субпиксельное смешивание)
 *     выводит сцену в canvas, после чего интерфейс (HUD, меню, текст) рисуется
 *     поверх уже сглаженной картинки — пиксельный шрифт и GUI остаются чёткими.
 *  3. Кадры без 3D-сцены (главное меню, экран загрузки) в конце кадра просто
 *     копируются из FBO в canvas без сглаживания ({@link #endFrame()}).
 *
 * Если FBO/шейдер создать не удалось или FXAA выключен, всё работает как
 * раньше (рендер напрямую в canvas). Отключить вручную: в консоли браузера
 * localStorage.setItem('webcraft.fxaa', '0') и перезагрузить страницу
 * (вернуть: removeItem). Состояние GL-шима (кэши в GLState) после прохода
 * восстанавливается, поэтому остальной рендер ничего не замечает.
 */
public final class Fxaa {

    private Fxaa() {}

    // ------------------------------------------------------------------
    // Шейдеры (WebGL2 = GLSL ES 3.00). Полноэкранный треугольник из gl_VertexID,
    // вершинных буферов нет.
    // ------------------------------------------------------------------
    private static final String VERTEX_SRC =
        "#version 300 es\n" +
        "// Полноэкранный треугольник без вершинных буферов (позиция из gl_VertexID).\n" +
        "out vec2 vUv;\n" +
        "void main() {\n" +
        "  vec2 p = vec2(float((gl_VertexID << 1) & 2), float(gl_VertexID & 2));\n" +
        "  vUv = p;\n" +
        "  gl_Position = vec4(p * 2.0 - 1.0, 0.0, 1.0);\n" +
        "}\n";

    private static final String FRAGMENT_SRC =
        "#version 300 es\n" +
        "precision highp float;\n" +
        "precision highp sampler2D;\n" +
        "\n" +
        "uniform sampler2D uScene;\n" +
        "uniform vec2 uInvRes;   // 1 / размер сцены в пикселях\n" +
        "uniform int uMode;      // 0 = просто копия, 1 = FXAA\n" +
        "\n" +
        "in vec2 vUv;\n" +
        "out vec4 outColor;\n" +
        "\n" +
        "// Настройки качества\n" +
        "const float EDGE_THRESHOLD_MIN = 0.0312;  // абсолютный порог контраста (тёмные области)\n" +
        "const float EDGE_THRESHOLD_MAX = 0.125;   // относительный порог контраста\n" +
        "const float SUBPIXEL_QUALITY   = 0.75;    // сглаживание одиночных пикселей/тонких линий\n" +
        "const int   EDGE_STEPS = 12;\n" +
        "\n" +
        "float luma(vec3 c) {\n" +
        "  return dot(c, vec3(0.299, 0.587, 0.114));\n" +
        "}\n" +
        "\n" +
        "// шаг поиска края: первые 4 — по одному пикселю, дальше ускоряемся\n" +
        "float stepSize(int i) {\n" +
        "  if (i < 4) return 1.0;\n" +
        "  if (i < 5) return 1.5;\n" +
        "  if (i < 9) return 2.0;\n" +
        "  if (i < 10) return 4.0;\n" +
        "  return 8.0;\n" +
        "}\n" +
        "\n" +
        "void main() {\n" +
        "  vec2 uv = vUv;\n" +
        "  vec3 colM = texture(uScene, uv).rgb;\n" +
        "  if (uMode == 0) {\n" +
        "    outColor = vec4(colM, 1.0);\n" +
        "    return;\n" +
        "  }\n" +
        "\n" +
        "  float lM = luma(colM);\n" +
        "  float lS = luma(textureOffset(uScene, uv, ivec2( 0, -1)).rgb);\n" +
        "  float lN = luma(textureOffset(uScene, uv, ivec2( 0,  1)).rgb);\n" +
        "  float lW = luma(textureOffset(uScene, uv, ivec2(-1,  0)).rgb);\n" +
        "  float lE = luma(textureOffset(uScene, uv, ivec2( 1,  0)).rgb);\n" +
        "\n" +
        "  float lMax = max(lM, max(max(lN, lS), max(lE, lW)));\n" +
        "  float lMin = min(lM, min(min(lN, lS), min(lE, lW)));\n" +
        "  float range = lMax - lMin;\n" +
        "\n" +
        "  // Не край — выходим сразу (большинство пикселей кадра)\n" +
        "  if (range < max(EDGE_THRESHOLD_MIN, lMax * EDGE_THRESHOLD_MAX)) {\n" +
        "    outColor = vec4(colM, 1.0);\n" +
        "    return;\n" +
        "  }\n" +
        "\n" +
        "  float lNW = luma(textureOffset(uScene, uv, ivec2(-1,  1)).rgb);\n" +
        "  float lNE = luma(textureOffset(uScene, uv, ivec2( 1,  1)).rgb);\n" +
        "  float lSW = luma(textureOffset(uScene, uv, ivec2(-1, -1)).rgb);\n" +
        "  float lSE = luma(textureOffset(uScene, uv, ivec2( 1, -1)).rgb);\n" +
        "\n" +
        "  // Субпиксельное смешивание\n" +
        "  float lAvg = (2.0 * (lN + lS + lW + lE) + lNW + lNE + lSW + lSE) / 12.0;\n" +
        "  float subpix = clamp(abs(lAvg - lM) / range, 0.0, 1.0);\n" +
        "  subpix = smoothstep(0.0, 1.0, subpix);\n" +
        "  float blendSub = subpix * subpix * SUBPIXEL_QUALITY;\n" +
        "\n" +
        "  // Ориентация края\n" +
        "  float edgeVert = abs(lN + lS - 2.0 * lM) * 2.0 + abs(lNE + lSE - 2.0 * lE) + abs(lNW + lSW - 2.0 * lW);\n" +
        "  float edgeHorz = abs(lE + lW - 2.0 * lM) * 2.0 + abs(lNE + lNW - 2.0 * lN) + abs(lSE + lSW - 2.0 * lS);\n" +
        "  bool isHorz = edgeHorz >= edgeVert;\n" +
        "\n" +
        "  // С какой стороны градиент круче\n" +
        "  float l1 = isHorz ? lS : lW;\n" +
        "  float l2 = isHorz ? lN : lE;\n" +
        "  float g1 = abs(l1 - lM);\n" +
        "  float g2 = abs(l2 - lM);\n" +
        "  bool firstSteeper = g1 >= g2;\n" +
        "\n" +
        "  float stepLen = isHorz ? uInvRes.y : uInvRes.x;\n" +
        "  float lOpp, gradient;\n" +
        "  if (firstSteeper) {\n" +
        "    stepLen = -stepLen;\n" +
        "    lOpp = l1;\n" +
        "    gradient = g1;\n" +
        "  } else {\n" +
        "    lOpp = l2;\n" +
        "    gradient = g2;\n" +
        "  }\n" +
        "\n" +
        "  vec2 edgeUv = uv;\n" +
        "  if (isHorz) edgeUv.y += stepLen * 0.5; else edgeUv.x += stepLen * 0.5;\n" +
        "\n" +
        "  vec2 along = isHorz ? vec2(uInvRes.x, 0.0) : vec2(0.0, uInvRes.y);\n" +
        "  float lEdge = 0.5 * (lM + lOpp);\n" +
        "  float gradThreshold = 0.25 * gradient;\n" +
        "\n" +
        "  // Идём по краю в обе стороны до его конца\n" +
        "  vec2 uvP = edgeUv + along;\n" +
        "  float dP = luma(texture(uScene, uvP).rgb) - lEdge;\n" +
        "  bool endP = abs(dP) >= gradThreshold;\n" +
        "  vec2 uvN = edgeUv - along;\n" +
        "  float dN = luma(texture(uScene, uvN).rgb) - lEdge;\n" +
        "  bool endN = abs(dN) >= gradThreshold;\n" +
        "\n" +
        "  for (int i = 1; i < EDGE_STEPS; i++) {\n" +
        "    if (endP && endN) break;\n" +
        "    float s = stepSize(i);\n" +
        "    if (!endP) {\n" +
        "      uvP += along * s;\n" +
        "      dP = luma(texture(uScene, uvP).rgb) - lEdge;\n" +
        "      endP = abs(dP) >= gradThreshold;\n" +
        "    }\n" +
        "    if (!endN) {\n" +
        "      uvN -= along * s;\n" +
        "      dN = luma(texture(uScene, uvN).rgb) - lEdge;\n" +
        "      endN = abs(dN) >= gradThreshold;\n" +
        "    }\n" +
        "  }\n" +
        "\n" +
        "  float distP = isHorz ? (uvP.x - uv.x) : (uvP.y - uv.y);\n" +
        "  float distN = isHorz ? (uv.x - uvN.x) : (uv.y - uvN.y);\n" +
        "  bool closerP = distP < distN;\n" +
        "  float dist = closerP ? distP : distN;\n" +
        "  float delta = closerP ? dP : dN;\n" +
        "  float thickness = distP + distN;\n" +
        "\n" +
        "  // Если конец края \"не в ту сторону\" — смешивать нечего\n" +
        "  float blendEdge = ((delta < 0.0) == (lM - lEdge < 0.0)) ? 0.0 : (0.5 - dist / thickness);\n" +
        "\n" +
        "  float blend = max(blendSub, blendEdge);\n" +
        "  vec2 finalUv = uv;\n" +
        "  if (isHorz) finalUv.y += stepLen * blend; else finalUv.x += stepLen * blend;\n" +
        "  outColor = vec4(texture(uScene, finalUv).rgb, 1.0);\n" +
        "}\n";

    // ------------------------------------------------------------------
    // Состояние
    // ------------------------------------------------------------------
    private static JSObject st;          // JS-объект: program, vao, fbo, texture, renderbuffer, uniform'ы
    private static boolean ready = false;
    private static boolean resolved = false;   // сцена уже выведена в canvas в этом кадре
    private static int width, height;

    public static boolean isActive() {
        return ready;
    }

    /** Создаёт FBO и шейдер. Вызывается один раз из Display.create() после создания GLState. */
    public static void init(GLState s) {
        if (!enabledByUser()) {
            System.out.println("FXAA: disabled (localStorage webcraft.fxaa = 0)");
            return;
        }
        WebGL2 gl = s.gl;
        width = Math.max(1, drawingWidth(gl));
        height = Math.max(1, drawingHeight(gl));
        st = create(gl, VERTEX_SRC, FRAGMENT_SRC, width, height);
        ready = st != null;
        if (ready) {
            bindScene(gl, st);
            System.out.println("FXAA: enabled (" + width + "x" + height + ")");
        } else {
            System.out.println("FXAA: unavailable, rendering directly to canvas");
        }
    }

    /**
     * Сцена готова: прогоняем FXAA в canvas и дальше рисуем GUI прямо в него.
     * Вызывается из kb.b() перед отрисовкой интерфейса.
     */
    public static void resolveScene() {
        if (!ready || resolved) return;
        pass(GLState.INSTANCE, 1, true);
        resolved = true;
    }

    /**
     * Конец кадра (Display.update / Display.swapBuffers): если сцену не
     * выводили через resolveScene() (меню, экран загрузки), копируем FBO в
     * canvas как есть. Затем готовимся к следующему кадру.
     */
    public static void endFrame() {
        if (!ready) return;
        GLState s = GLState.INSTANCE;
        if (!resolved && s.frameDirty) {
            pass(s, 0, false);
        }
        resolved = false;
        s.frameDirty = false;
        syncSize();
    }

    /** Подгоняет FBO под текущий размер canvas (fullscreen/resize) и привязывает его. */
    public static void syncSize() {
        if (!ready) return;
        GLState s = GLState.INSTANCE;
        WebGL2 gl = s.gl;
        int w = Math.max(1, drawingWidth(gl));
        int h = Math.max(1, drawingHeight(gl));
        if (w != width || h != height) {
            if (!resize(gl, st, w, h, s.glTexValid ? s.glTex : null)) {
                // Не вышло — отключаемся, дальше рендерим напрямую в canvas.
                System.out.println("FXAA: resize failed, disabling");
                unbindScene(gl);
                ready = false;
                return;
            }
            width = w;
            height = h;
        }
        bindScene(gl, st);
    }

    // ------------------------------------------------------------------
    // Проход: FBO -> canvas. mode 1 = FXAA, 0 = копия.
    // ------------------------------------------------------------------
    private static void pass(GLState s, int mode, boolean clearDepth) {
        WebGL2 gl = s.gl;
        run(gl, st, mode, clearDepth,
            s.stDepthTest, s.stBlend, s.stCull, s.stDepthMask,
            s.cmR, s.cmG, s.cmB, s.cmA,
            s.glTexValid ? s.glTex : null, s.currentVao);
        // Программа изменилась — шиму надо заново сделать useProgram.
        // Остальное (текстура, VAO, depth/blend/cull, маски) восстановлено в run().
        s.programBound = false;
    }

    private static boolean enabledByUser() {
        return !"0".equals(readFlag());
    }

    @JSBody(script = "try { return localStorage.getItem('webcraft.fxaa'); } catch (e) { return null; }")
    private static native String readFlag();

    @JSBody(params = { "gl" }, script = "return gl.drawingBufferWidth;")
    private static native int drawingWidth(WebGL2 gl);

    @JSBody(params = { "gl" }, script = "return gl.drawingBufferHeight;")
    private static native int drawingHeight(WebGL2 gl);

    @JSBody(params = { "gl", "st" }, script = "gl.bindFramebuffer(36160, st.fbo);")
    private static native void bindScene(WebGL2 gl, JSObject st);

    @JSBody(params = { "gl" }, script = "gl.bindFramebuffer(36160, null);")
    private static native void unbindScene(WebGL2 gl);

    /** Возвращает JS-объект состояния либо null (ошибка компиляции/неполный FBO). */
    @JSBody(params = { "gl", "vs", "fs", "w", "h" }, script =
        "function sh(t, s) {" +
        "  var x = gl.createShader(t); gl.shaderSource(x, s); gl.compileShader(x);" +
        "  if (!gl.getShaderParameter(x, 35713)) { console.error('FXAA shader: ' + gl.getShaderInfoLog(x)); return null; }" +
        "  return x;" +
        "}" +
        "var v = sh(35633, vs), f = sh(35632, fs);" +
        "if (!v || !f) return null;" +
        "var p = gl.createProgram(); gl.attachShader(p, v); gl.attachShader(p, f); gl.linkProgram(p);" +
        "if (!gl.getProgramParameter(p, 35714)) { console.error('FXAA link: ' + gl.getProgramInfoLog(p)); return null; }" +
        "gl.deleteShader(v); gl.deleteShader(f);" +
        "var st = {" +
        "  prog: p, vao: gl.createVertexArray(), fbo: gl.createFramebuffer()," +
        "  tex: gl.createTexture(), rb: gl.createRenderbuffer()," +
        "  uScene: gl.getUniformLocation(p, 'uScene'), uInv: gl.getUniformLocation(p, 'uInvRes')," +
        "  uMode: gl.getUniformLocation(p, 'uMode'), w: 0, h: 0" +
        "};" +
        "gl.bindFramebuffer(36160, st.fbo);" +
        "gl.bindTexture(3553, st.tex);" +
        "gl.texParameteri(3553, 10241, 9729); gl.texParameteri(3553, 10240, 9729);" +   // LINEAR (FXAA нужна билинейная выборка)
        "gl.texParameteri(3553, 10242, 33071); gl.texParameteri(3553, 10243, 33071);" + // CLAMP_TO_EDGE
        "gl.texImage2D(3553, 0, 32856, w, h, 0, 6408, 5121, null);" +
        "gl.framebufferTexture2D(36160, 36064, 3553, st.tex, 0);" +
        "gl.bindRenderbuffer(36161, st.rb);" +
        "gl.renderbufferStorage(36161, 33190, w, h);" +                                  // DEPTH_COMPONENT24
        "gl.framebufferRenderbuffer(36160, 36096, 36161, st.rb);" +
        "var ok = gl.checkFramebufferStatus(36160) === 36053;" +
        "gl.bindTexture(3553, null);" +
        "if (!ok) { console.error('FXAA: framebuffer incomplete'); gl.bindFramebuffer(36160, null); return null; }" +
        "st.w = w; st.h = h;" +
        "return st;")
    private static native JSObject create(WebGL2 gl, String vs, String fs, int w, int h);

    /** Пересоздаёт хранилище текстуры/глубины под новый размер. prevTex — что восстановить в TEXTURE_2D. */
    @JSBody(params = { "gl", "st", "w", "h", "prevTex" }, script =
        "gl.bindFramebuffer(36160, st.fbo);" +
        "gl.bindTexture(3553, st.tex);" +
        "gl.texImage2D(3553, 0, 32856, w, h, 0, 6408, 5121, null);" +
        "gl.bindRenderbuffer(36161, st.rb);" +
        "gl.renderbufferStorage(36161, 33190, w, h);" +
        "var ok = gl.checkFramebufferStatus(36160) === 36053;" +
        "gl.bindTexture(3553, prevTex);" +
        "if (ok) { st.w = w; st.h = h; }" +
        "return ok;")
    private static native boolean resize(WebGL2 gl, JSObject st, int w, int h, WebGLTexture prevTex);

    /**
     * Сам проход. Рисует полноэкранный треугольник в canvas (дефолтный FB),
     * при clearDepth очищает глубину canvas (GUI рисуется поверх с чистым
     * z-буфером) и возвращает состояние GL к тому, что ожидает шим.
     * dt/bl/cu/dm — значения кэшей GLState (0 = неизвестно/выключено по умолчанию,
     * 1 = включено, 2 = выключено; для depthMask 2 = false).
     */
    @JSBody(params = { "gl", "st", "mode", "clearDepth", "dt", "bl", "cu", "dm",
                       "cmr", "cmg", "cmb", "cma", "prevTex", "prevVao" }, script =
        "gl.bindFramebuffer(36160, null);" +
        "var cw = gl.drawingBufferWidth, ch = gl.drawingBufferHeight;" +
        "gl.viewport(0, 0, cw, ch);" +
        "gl.disable(2929); gl.disable(3042); gl.disable(2884);" +
        "gl.colorMask(true, true, true, true);" +
        "gl.useProgram(st.prog);" +
        "gl.bindVertexArray(st.vao);" +
        "gl.activeTexture(33984);" +
        "gl.bindTexture(3553, st.tex);" +
        "gl.uniform1i(st.uScene, 0);" +
        "gl.uniform2f(st.uInv, 1.0 / st.w, 1.0 / st.h);" +
        "gl.uniform1i(st.uMode, mode);" +
        "gl.drawArrays(4, 0, 3);" +
        "if (clearDepth) { gl.depthMask(true); gl.clear(256); }" +
        // восстановление состояния
        "gl.bindTexture(3553, prevTex);" +
        "gl.bindVertexArray(prevVao);" +
        "if (dt === 1) gl.enable(2929);" +
        "if (bl === 1) gl.enable(3042);" +
        "if (cu === 1) gl.enable(2884);" +
        "gl.depthMask(dm !== 2);" +
        "gl.colorMask(cmr, cmg, cmb, cma);")
    private static native void run(WebGL2 gl, JSObject st, int mode, boolean clearDepth,
                                   int dt, int bl, int cu, int dm,
                                   boolean cmr, boolean cmg, boolean cmb, boolean cma,
                                   WebGLTexture prevTex, WebGLVertexArrayObject prevVao);
}
