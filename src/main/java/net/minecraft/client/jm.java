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
}
