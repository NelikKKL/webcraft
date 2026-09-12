package net.minecraft.client.web;

/**
 * Один универсальный шейдер, покрывающий все комбинации состояния, которые
 * встречаются в decomp-рендере Alpha 1.2.6: позиция + (опционально) UV +
 * (опционально) per-vertex цвет + (опционально) нормаль для освещения +
 * туман. Вместо перекомпиляции под каждую комбинацию все ветки решаются
 * uniform-флагами (uUseTexture, uUseLighting, uUseFog) — дешевле по
 * реализации, чем shader permutations, и достаточно для fixed-function
 * уровня сложности этой версии игры.
 */
public final class Shaders {

    // WebGL2 = GLSL ES 3.00
    private static final String VERTEX_SRC =
        "#version 300 es\n" +
        "layout(location=0) in vec3 aPosition;\n" +
        "layout(location=1) in vec2 aTexCoord;\n" +
        "layout(location=2) in vec4 aColor;\n" +
        "layout(location=3) in vec3 aNormal;\n" +
        "uniform mat4 uProjection;\n" +
        "uniform mat4 uModelview;\n" +
        "out vec2 vTexCoord;\n" +
        "out vec4 vColor;\n" +
        "out vec3 vNormalWorld;\n" +
        "out float vFogDist;\n" +
        "void main() {\n" +
        "  vec4 eyePos = uModelview * vec4(aPosition, 1.0);\n" +
        "  gl_Position = uProjection * eyePos;\n" +
        "  vTexCoord = aTexCoord;\n" +
        "  vColor = aColor;\n" +
        "  vNormalWorld = mat3(uModelview) * aNormal;\n" +
        "  vFogDist = -eyePos.z;\n" +
        "}\n";

    private static final String FRAGMENT_SRC =
        "#version 300 es\n" +
        "precision mediump float;\n" +
        "in vec2 vTexCoord;\n" +
        "in vec4 vColor;\n" +
        "in vec3 vNormalWorld;\n" +
        "in float vFogDist;\n" +
        "uniform sampler2D uTexture;\n" +
        "uniform bool uUseTexture;\n" +
        "uniform bool uUseLighting;\n" +
        "uniform vec3 uLightDir0;\n" +
        "uniform vec3 uLightDir1;\n" +
        "uniform vec3 uLightColor0;\n" +
        "uniform vec3 uLightColor1;\n" +
        "uniform vec3 uAmbient;\n" +
        "uniform bool uUseFog;\n" +
        "uniform int uFogMode;\n" + // 0=EXP, 1=EXP2, 2=LINEAR
        "uniform float uFogDensity;\n" +
        "uniform float uFogStart;\n" +
        "uniform float uFogEnd;\n" +
        "uniform vec4 uFogColor;\n" +
        "uniform vec4 uColorMult;\n" + // текущий glColor4f (используется вместе с per-vertex цветом или вместо)
        "out vec4 outColor;\n" +
        "void main() {\n" +
        "  vec4 base = uUseTexture ? texture(uTexture, vTexCoord) : vec4(1.0);\n" +
        "  vec4 color = base * vColor * uColorMult;\n" +
        "  if (uUseLighting) {\n" +
        "    vec3 n = normalize(vNormalWorld);\n" +
        "    float d0 = max(dot(n, normalize(uLightDir0)), 0.0);\n" +
        "    float d1 = max(dot(n, normalize(uLightDir1)), 0.0);\n" +
        "    vec3 lit = uAmbient + d0 * uLightColor0 + d1 * uLightColor1;\n" +
        "    color.rgb *= lit;\n" +
        "  }\n" +
        "  if (uUseFog) {\n" +
        "    float f;\n" +
        "    if (uFogMode == 2) {\n" +
        "      f = clamp((uFogEnd - vFogDist) / (uFogEnd - uFogStart), 0.0, 1.0);\n" +
        "    } else if (uFogMode == 1) {\n" +
        "      float x = uFogDensity * vFogDist;\n" +
        "      f = clamp(exp(-x * x), 0.0, 1.0);\n" +
        "    } else {\n" +
        "      f = clamp(exp(-uFogDensity * vFogDist), 0.0, 1.0);\n" +
        "    }\n" +
        "    color.rgb = mix(uFogColor.rgb, color.rgb, f);\n" +
        "  }\n" +
        "  if (color.a < 0.004) discard;\n" +
        "  outColor = color;\n" +
        "}\n";

    public final WebGLProgram program;

    // GLSL uniform location cache
    public final WebGLUniformLocation uProjection;
    public final WebGLUniformLocation uModelview;
    public final WebGLUniformLocation uTexture;
    public final WebGLUniformLocation uUseTexture;
    public final WebGLUniformLocation uUseLighting;
    public final WebGLUniformLocation uLightDir0, uLightDir1, uLightColor0, uLightColor1, uAmbient;
    public final WebGLUniformLocation uUseFog, uFogMode, uFogDensity, uFogStart, uFogEnd, uFogColor;
    public final WebGLUniformLocation uColorMult;

    Shaders(WebGL2 gl) {
        WebGLShader vs = compile(gl, 35633 /*VERTEX_SHADER*/, VERTEX_SRC);
        WebGLShader fs = compile(gl, 35632 /*FRAGMENT_SHADER*/, FRAGMENT_SRC);
        program = gl.createProgram();
        gl.attachShader(program, vs);
        gl.attachShader(program, fs);
        gl.linkProgram(program);
        if (!GLRaw.getProgramLinkStatus(gl, program)) {
            throw new RuntimeException("Shader link error: " + GLRaw.getProgramInfoLog(gl, program));
        }

        uProjection = gl.getUniformLocation(program, "uProjection");
        uModelview = gl.getUniformLocation(program, "uModelview");
        uTexture = gl.getUniformLocation(program, "uTexture");
        uUseTexture = gl.getUniformLocation(program, "uUseTexture");
        uUseLighting = gl.getUniformLocation(program, "uUseLighting");
        uLightDir0 = gl.getUniformLocation(program, "uLightDir0");
        uLightDir1 = gl.getUniformLocation(program, "uLightDir1");
        uLightColor0 = gl.getUniformLocation(program, "uLightColor0");
        uLightColor1 = gl.getUniformLocation(program, "uLightColor1");
        uAmbient = gl.getUniformLocation(program, "uAmbient");
        uUseFog = gl.getUniformLocation(program, "uUseFog");
        uFogMode = gl.getUniformLocation(program, "uFogMode");
        uFogDensity = gl.getUniformLocation(program, "uFogDensity");
        uFogStart = gl.getUniformLocation(program, "uFogStart");
        uFogEnd = gl.getUniformLocation(program, "uFogEnd");
        uFogColor = gl.getUniformLocation(program, "uFogColor");
        uColorMult = gl.getUniformLocation(program, "uColorMult");
    }

    private static WebGLShader compile(WebGL2 gl, int type, String src) {
        WebGLShader shader = gl.createShader(type);
        gl.shaderSource(shader, src);
        gl.compileShader(shader);
        if (!GLRaw.getShaderCompileStatus(gl, shader)) {
            throw new RuntimeException("Shader compile error: " + GLRaw.getShaderInfoLog(gl, shader));
        }
        return shader;
    }
}
