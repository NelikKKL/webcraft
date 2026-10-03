/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.client;
import net.minecraft.client.awtshim.BufferedImage;
import java.io.IOException;
import net.minecraft.client.awtshim.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class jm
extends d {
    private int e = -1;
    private BufferedImage f;

    public jm() {
        this.a = "Default";
        this.b = "The default look of Minecraft";
        try {
            this.f = ImageIO.read("/pack.png");
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    @Override
    public void b(Minecraft minecraft) {
        if (this.f != null) {
            minecraft.n.a(this.e);
        }
    }

    // ИЗМЕНЕНО (WASM-GC сборка): TeaVM 0.11.0 падал на этом методе с
    // "Failed generating method body ... NullPointerException" (WasmGCMethodGenerator,
    // at net.minecraft.client.jm.c), хотя JS-таргет его компилирует без проблем.
    // Логика прежняя, но метод разбит на простые линейные шаги без составного
    // условия `&&` и без каста `(int)` — на случай, если падение связано с формой
    // байткода (после инлайнинга fu.a/fu.b в этот метод).
    @Override
    public void c(Minecraft minecraft) {
        if (this.f == null) {
            this.bindUnknownIcon(minecraft);
            return;
        }
        this.bindPackIcon(minecraft);
    }

    private void bindPackIcon(Minecraft minecraft) {
        if (this.e < 0) {
            this.e = minecraft.n.a(this.f);
        }
        minecraft.n.b(this.e);
    }

    private void bindUnknownIcon(Minecraft minecraft) {
        int id = minecraft.n.a("/gui/unknown_pack.png");
        GL11.glBindTexture(3553, id);
    }
}
