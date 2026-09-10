package net.minecraft.client.web;

/**
 * Полный список ресурсов Minecraft Alpha 1.2.6, извлечённый построчным
 * grep всех строковых литералов вида "/path/to/file.ext" по всему
 * decomp-дереву (см. TODO.md/README.md — методология поиска). Файлы
 * реально присутствуют в архиве decomp-проекта и скопированы в
 * web/assets/ с сохранением относительной структуры путей.
 *
 * IMAGE_PATHS прелоадятся через ResourcePreloader (PNG, декодируются
 * браузером в ARGB и кладутся в ResourceCache). TEXT_PATHS (пока только
 * /title/splashes.txt) НЕ прелоадятся этим механизмом — см. TODO.md,
 * пункт про текстовые ресурсы (сейчас dj.java патчится на fallback-массив
 * вместо реальной загрузки этого файла — низкий приоритет, чисто
 * косметическая фича).
 */
public final class ResourceManifest {
    private ResourceManifest() {}

    public static final String[] IMAGE_PATHS = {
        "/art/kz.png",
        "/environment/clouds.png",
        "/font/default.png",
        "/gui/background.png",
        "/gui/container.png",
        "/gui/crafting.png",
        "/gui/furnace.png",
        "/gui/gui.png",
        "/gui/icons.png",
        "/gui/inventory.png",
        "/gui/items.png",
        "/gui/logo.png",
        "/gui/unknown_pack.png",
        "/item/arrows.png",
        "/item/boat.png",
        "/item/cart.png",
        "/item/sign.png",
        "/misc/dial.png",
        "/misc/foliagecolor.png",
        "/misc/grasscolor.png",
        "/misc/water.png",
        "/mob/char.png",
        "/mob/chicken.png",
        "/mob/cow.png",
        "/mob/creeper.png",
        "/mob/ghast.png",
        "/mob/ghast_fire.png",
        "/mob/pig.png",
        "/mob/pigzombie.png",
        "/mob/saddle.png",
        "/mob/sheep.png",
        "/mob/sheep_fur.png",
        "/mob/skeleton.png",
        "/mob/slime.png",
        "/mob/spider.png",
        "/mob/spider_eyes.png",
        "/mob/zombie.png",
        "/pack.png",
        "/particles.png",
        "/terrain.png",
        "/terrain/moon.png",
        "/terrain/sun.png",
        "/title/black.png",
        "/title/mojang.png",
    };
}
