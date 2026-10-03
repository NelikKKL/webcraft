package net.minecraft.client.web;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.awtshim.BufferedImage;
import net.minecraft.client.d;
import org.lwjgl.opengl.GL11;

/**
 * Текстур-пак, загруженный пользователем из .zip (разбор zip и декодирование
 * PNG делает JS — см. web/texturepacks.js; сюда файлы приходят уже готовыми
 * ARGB-картинками через {@link TexturePackStore}).
 *
 * Активация пака = подмена оверлея в {@link ResourceCache}: ImageIO.read(path)
 * сначала ищет путь в паке и только потом в встроенных ресурсах — как и в
 * оригинале, пак может перекрывать лишь часть файлов. Сами текстуры в GL
 * перезаливаются штатным fu.b() (его уже вызывает экран выбора паков).
 */
public final class WebTexturePack extends d {

    final Map<String, ResourceCache.Entry> files = new HashMap<>();
    private BufferedImage icon;
    private int iconTexture = -1;

    public WebTexturePack(String name) {
        this.a = name;
        this.b = "";
        this.c = "";
    }

    void addFile(String path, ResourceCache.Entry entry) {
        files.put(ResourceCache.normalizePath(path), entry);
    }

    /** Вызывается после того, как JS передал все файлы пака. */
    void finish() {
        ResourceCache.Entry terrain = files.get("terrain.png");
        if (terrain == null) {
            this.b = "Partial pack";
        } else if (terrain.width == 256) {
            this.b = "16x16 texture pack";
        } else {
            // Анимированные тайлы (вода, лава, огонь, портал) игра пишет в
            // атлас блоками 16x16, у HD-атласа они будут выглядеть неверно.
            this.b = "HD pack: animated tiles may glitch";
        }
        this.c = files.size() + " files";
        ResourceCache.Entry ic = files.get("pack.png");
        if (ic != null) {
            icon = BufferedImage.wrapArgb(ic.width, ic.height, ic.argb.clone());
        }
    }

    /** Пак выбран. */
    @Override
    public void a() {
        ResourceCache.setOverlay(files);
    }

    /** Пак снят с выбора. */
    @Override
    public void b() {
        ResourceCache.setOverlay(null);
    }

    @Override
    public void b(Minecraft minecraft) {
        if (iconTexture >= 0) {
            minecraft.n.a(iconTexture);
            iconTexture = -1;
        }
    }

    @Override
    public void c(Minecraft minecraft) {
        if (icon != null && iconTexture < 0) {
            iconTexture = minecraft.n.a(icon);
        }
        if (icon != null) {
            minecraft.n.b(iconTexture);
        } else {
            GL11.glBindTexture(3553, (int) minecraft.n.a("/gui/unknown_pack.png"));
        }
    }
}
