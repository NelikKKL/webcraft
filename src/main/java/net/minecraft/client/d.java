/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.awtshim.BufferedImage;
import org.lwjgl.opengl.GL11;

public abstract class d {
    public String a;
    public String b;
    public String c;
    public String d;
    // ПЕРЕНЕСЕНО из jm (WASM-GC сборка): иконка пака и её GL-текстура.
    public int e = -1;
    public BufferedImage f;

    public void a() {
    }

    public void b() {
    }

    public void a(Minecraft minecraft) {
    }

    public void b(Minecraft minecraft) {
    }

    // ИЗМЕНЕНО (WASM-GC сборка): раньше d.c() был пустым, а настоящую реализацию
    // давал jm.c() через переопределение. TeaVM 0.11.0 на WEBASSEMBLY_GC стабильно
    // падал именно на jm.c ("Failed generating method body ... NullPointerException",
    // WasmGCMethodGenerator.generateRegularMethodBody), даже после упрощения тела.
    // Единственный наследник d — jm, поэтому реализация живёт здесь, переопределения
    // больше нет. Поведение то же: привязать иконку пака или unknown_pack.png.
    public void c(Minecraft minecraft) {
        if (this.f == null) {
            int id = minecraft.n.a("/gui/unknown_pack.png");
            GL11.glBindTexture(3553, id);
            return;
        }
        if (this.e < 0) {
            this.e = minecraft.n.a(this.f);
        }
        minecraft.n.b(this.e);
    }

    public InputStream a(String string) {
        return net.minecraft.client.web.ResourceIO.getImageResourceAsStream(string);
    }
}

