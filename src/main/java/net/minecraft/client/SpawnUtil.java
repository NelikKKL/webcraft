package net.minecraft.client;

/**
 * Поиск безопасного места появления игрока (веб-порт).
 * Y мирового спавна (Session.n) в этом порте — просто 64 и не всегда совпадает с рельефом, поэтому
 * возрождение «ровно в w.n» могло поставить игрока внутрь земли. Здесь высота ищется по реальным блокам.
 */
final class SpawnUtil {
    private SpawnUtil() {}

    /** Блок не мешает проходу (воздух, вода, лава, растения, факелы, снежный слой и т.п.). */
    static boolean passable(int id) {
        switch (id) {
            case 0: case 6: case 8: case 9: case 10: case 11: case 27: case 28: case 30: case 31: case 32:
            case 37: case 38: case 39: case 40: case 50: case 51: case 55: case 59: case 63: case 65: case 66:
            case 68: case 69: case 70: case 72: case 75: case 76: case 77: case 78: case 83:
                return true;
            default:
                return false;
        }
    }

    /** Свободная ячейка, в которой можно стоять: проходимая, но не вода/лава/огонь. */
    static boolean clear(int id) {
        return passable(id) && id != 8 && id != 9 && id != 10 && id != 11 && id != 51;
    }

    /** Твёрдый пол, на который можно встать (не кактус, не лёд-обманка не считаем). */
    static boolean goodFloor(int id) {
        return id > 0 && !passable(id) && id != 81;
    }

    static boolean isSafe(Session w, int x, int y, int z) {
        if (y < 1 || y > 124) return false;
        return clear(w.a(x, y, z)) && clear(w.a(x, y + 1, z)) && goodFloor(w.a(x, y - 1, z));
    }

    /** Самое верхнее безопасное место в колонке (x,z) или -1. */
    static int topSafeY(Session w, int x, int z) {
        for (int y = 124; y >= 1; y--) {
            if (isSafe(w, x, y, z)) return y;
        }
        return -1;
    }

    /**
     * Безопасное место появления около (bx,bz): сначала сама колонка, затем окрестность по расширяющимся кольцам.
     * Возвращает {x, y(ноги), z}. Если ничего твёрдого нет (океан) — над поверхностью воды.
     */
    static double[] surfaceSpot(Session w, int bx, int bz, int fallbackY) {
        for (int r = 0; r <= 6; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;
                    int x = bx + dx, z = bz + dz;
                    w.c(x >> 4, z >> 4);
                    int y = topSafeY(w, x, z);
                    if (y > 0) return new double[]{x + 0.5, y, z + 0.5};
                }
            }
        }
        // ни одного твёрдого пола рядом (например, океан): над самым верхним непустым блоком колонки
        w.c(bx >> 4, bz >> 4);
        for (int y = 125; y >= 1; y--) {
            if (w.a(bx, y, bz) != 0) return new double[]{bx + 0.5, y + 1, bz + 0.5};
        }
        return new double[]{bx + 0.5, Math.max(fallbackY, 64) + 1, bz + 0.5};
    }

    /**
     * Если ноги/голова игрока оказались внутри твёрдых блоков (чанк уже загружен) — поднять до свободного места.
     * Возвращает новую Y ног.
     */
    static double unstuck(Session w, double x, double feetY, double z) {
        int bx = (int)Math.floor(x), bz = (int)Math.floor(z);
        if (!w.d(bx, 64, bz)) return feetY;
        int y = (int)Math.floor(feetY);
        if (y < 0 || y > 124) return feetY;
        if (passable(w.a(bx, y, bz)) && passable(w.a(bx, y + 1, bz))) return feetY;
        for (int ny = y + 1; ny <= 124; ny++) {
            if (passable(w.a(bx, ny, bz)) && passable(w.a(bx, ny + 1, bz))) return ny;
        }
        return feetY;
    }
}
