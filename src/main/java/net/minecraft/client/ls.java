/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import net.minecraft.client.awtshim.ImageIO;
import org.lwjgl.opengl.GL11;

public class ls {
    private int[] b = new int[256];
    public int a = 0;
    private int c;
    private IntBuffer d = ds.c(1024);

    public ls(gq gq2, String string, fu fu2) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(string);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        int n7 = bufferedImage.getWidth();
        int n8 = bufferedImage.getHeight();
        int[] nArray = new int[n7 * n8];
        bufferedImage.getRGB(0, 0, n7, n8, nArray, 0, n7);
        for (int i2 = 0; i2 < 256; ++i2) {
            n6 = i2 % 16;
            n5 = i2 / 16;
            for (n4 = 7; n4 >= 0; --n4) {
                int n9 = n6 * 8 + n4;
                boolean bl2 = true;
                for (int i3 = 0; i3 < 8 && bl2; ++i3) {
                    n3 = (n5 * 8 + i3) * n7;
                    n2 = nArray[n9 + n3] & 0xFF;
                    if (n2 <= 0) continue;
                    bl2 = false;
                }
                if (!bl2) break;
            }
            if (i2 == 32) {
                n4 = 2;
            }
            this.b[i2] = n4 + 2;
        }
        this.a = fu2.a(bufferedImage);
        this.c = ds.a(288);
        is is2 = is.a;
        for (n6 = 0; n6 < 256; ++n6) {
            GL11.glNewList((int)(this.c + n6), 4864);
            is2.b();
            n5 = n6 % 16 * 8;
            n4 = n6 / 16 * 8;
            float f2 = 7.99f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            is2.a(0.0, 0.0f + f2, 0.0, (float)n5 / 128.0f + f3, ((float)n4 + f2) / 128.0f + f4);
            is2.a(0.0f + f2, 0.0f + f2, 0.0, ((float)n5 + f2) / 128.0f + f3, ((float)n4 + f2) / 128.0f + f4);
            is2.a(0.0f + f2, 0.0, 0.0, ((float)n5 + f2) / 128.0f + f3, (float)n4 / 128.0f + f4);
            is2.a(0.0, 0.0, 0.0, (float)n5 / 128.0f + f3, (float)n4 / 128.0f + f4);
            is2.a();
            GL11.glTranslatef((float)this.b[n6], 0.0f, 0.0f);
            GL11.glEndList();
        }
        for (n6 = 0; n6 < 32; ++n6) {
            boolean bl3;
            n5 = (n6 >> 3 & 1) * 85;
            n4 = (n6 >> 2 & 1) * 170 + n5;
            int n10 = (n6 >> 1 & 1) * 170 + n5;
            int n11 = (n6 >> 0 & 1) * 170 + n5;
            if (n6 == 6) {
                n4 += 85;
            }
            boolean bl4 = bl3 = n6 >= 16;
            if (gq2.g) {
                n3 = (n4 * 30 + n10 * 59 + n11 * 11) / 100;
                n2 = (n4 * 30 + n10 * 70) / 100;
                int n12 = (n4 * 30 + n11 * 70) / 100;
                n4 = n3;
                n10 = n2;
                n11 = n12;
            }
            if (bl3) {
                n4 /= 4;
                n10 /= 4;
                n11 /= 4;
            }
            GL11.glNewList((int)(this.c + 256 + n6), 4864);
            GL11.glColor3f((float)((float)n4 / 255.0f), (float)((float)n10 / 255.0f), (float)((float)n11 / 255.0f));
            GL11.glEndList();
        }
    }

    public void a(String string, int n2, int n3, int n4) {
        this.a(string, n2 + 1, n3 + 1, n4, true);
        this.b(string, n2, n3, n4);
    }

    public void b(String string, int n2, int n3, int n4) {
        this.a(string, n2, n3, n4, false);
    }

    public void a(String string, int n2, int n3, int n4, boolean bl2) {
        if (string == null) {
            return;
        }
        if (bl2) {
            int n5 = n4 & 0xFF000000;
            n4 = (n4 & 0xFCFCFC) >> 2;
            n4 += n5;
        }

        // Web-port fix: the original implementation stores glyph geometry in
        // OpenGL display lists. Our WebGL client-array shim cannot safely keep
        // the client-side vertex pointer state inside a display list, so the
        // recorded draw can execute later with no active position/UV array.
        // Render glyph quads directly instead. This preserves the original
        // glyph atlas, spacing, color codes and matrix semantics.
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, (int)this.a);
        GL11.glColor4f(
            (float)(n4 >> 16 & 0xFF) / 255.0f,
            (float)(n4 >> 8 & 0xFF) / 255.0f,
            (float)(n4 & 0xFF) / 255.0f,
            ((float)(n4 >> 24 & 0xFF) / 255.0f) == 0.0f ? 1.0f : (float)(n4 >> 24 & 0xFF) / 255.0f
        );

        is is2 = is.a;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n2, (float)n3, 0.0f);

        for (int i2 = 0; i2 < string.length(); ++i2) {
            char ch = string.charAt(i2);

            if (ch == '\u00a7' && string.length() > i2 + 1) {
                int code = "0123456789abcdef".indexOf(Character.toLowerCase(string.charAt(i2 + 1)));
                if (code < 0 || code > 15) {
                    code = 15;
                }

                int r = (code >> 2 & 1) * 170;
                int g = (code >> 1 & 1) * 170;
                int b = (code & 1) * 170;
                int extra = (code >> 3 & 1) * 85;
                r += extra;
                g += extra;
                b += extra;
                if (code == 6) {
                    r += 85;
                }
                if (bl2) {
                    int gray = (r * 30 + g * 59 + b * 11) / 100;
                    r = (gray * 30 + g * 70) / 100;
                    b = (gray * 30 + b * 70) / 100;
                    g = gray;
                }
                if (code >= 16) {
                    r /= 4;
                    g /= 4;
                    b /= 4;
                }
                GL11.glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
                ++i2;
                continue;
            }

            int glyph = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(ch);
            if (glyph < 0) {
                continue;
            }

            int glyphId = glyph + 32;
            int tx = glyphId % 16 * 8;
            int ty = glyphId / 16 * 8;
            float f2 = 7.99f;
            float u0 = (float)tx / 128.0f;
            float v0 = (float)ty / 128.0f;
            float u1 = ((float)tx + f2) / 128.0f;
            float v1 = ((float)ty + f2) / 128.0f;

            is2.b();
            is2.a(0.0, f2, 0.0, u0, v1);
            is2.a(f2, f2, 0.0, u1, v1);
            is2.a(f2, 0.0, 0.0, u1, v0);
            is2.a(0.0, 0.0, 0.0, u0, v0);
            is2.a();
            GL11.glTranslatef((float)this.b[glyphId], 0.0f, 0.0f);
        }
        GL11.glPopMatrix();
    }

    public int a(String string) {
        if (string == null) {
            return 0;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            if (string.charAt(i2) == '\u00a7') {
                ++i2;
                continue;
            }
            int n3 = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(string.charAt(i2));
            if (n3 < 0) continue;
            n2 += this.b[n3 + 32];
        }
        return n2;
    }
}

