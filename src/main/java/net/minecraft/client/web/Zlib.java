package net.minecraft.client.web;

/**
 * Минимальный zlib-компрессор для пакетов с чанками (хост-сервер мультиплеера).
 *
 * Зачем свой: в TeaVM нет гарантии наличия java.util.zip.Deflater, а клиентский
 * пакет чанка (ci.java) читает данные обычным Inflater'ом. Здесь — валидный
 * zlib-поток с фиксированными кодами Хаффмана и LZ77, использующим только
 * расстояние 1 (повтор предыдущего байта). Чанки Minecraft почти целиком состоят
 * из длинных одинаковых серий (воздух, камень, свет 0xFF, метаданные 0), поэтому
 * такого кодирования достаточно для степени сжатия порядка 5-10x при минимуме кода.
 */
public final class Zlib {

    private Zlib() {}

    private static final int[] LEN_BASE = {3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31,
            35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258};
    private static final int[] LEN_EXTRA = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,
            3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0};

    private static final class Bits {
        byte[] buf;
        int pos = 0;
        int acc = 0;
        int nacc = 0;

        Bits(int cap) { buf = new byte[cap]; }

        private void ensure(int extra) {
            if (pos + extra > buf.length) {
                byte[] nb = new byte[Math.max(buf.length * 2, pos + extra)];
                System.arraycopy(buf, 0, nb, 0, pos);
                buf = nb;
            }
        }

        /** Запись n бит, младший бит первым (порядок упаковки DEFLATE). */
        void put(int value, int n) {
            acc |= value << nacc;
            nacc += n;
            while (nacc >= 8) {
                ensure(1);
                buf[pos++] = (byte) acc;
                acc >>>= 8;
                nacc -= 8;
            }
        }

        /** Код Хаффмана записывается старшим битом первым. */
        void putCode(int code, int len) {
            int rev = 0;
            for (int i = 0; i < len; i++) {
                rev = (rev << 1) | ((code >> i) & 1);
            }
            put(rev, len);
        }

        void flushByte() {
            if (nacc > 0) {
                ensure(1);
                buf[pos++] = (byte) acc;
                acc = 0;
                nacc = 0;
            }
        }
    }

    private static void literal(Bits b, int v) {
        if (v < 144) b.putCode(0x30 + v, 8);
        else b.putCode(0x190 + (v - 144), 9);
    }

    private static void match(Bits b, int len) {
        int idx = 28;
        while (LEN_BASE[idx] > len) idx--;
        int sym = 257 + idx;
        if (sym < 280) b.putCode(sym - 256, 7);
        else b.putCode(0xC0 + (sym - 280), 8);
        if (LEN_EXTRA[idx] > 0) b.put(len - LEN_BASE[idx], LEN_EXTRA[idx]);
        b.putCode(0, 5);   // расстояние 1 = код 0, без дополнительных бит
    }

    public static byte[] compress(byte[] data, int len) {
        Bits b = new Bits(len / 4 + 64);
        b.ensure(2);
        b.buf[b.pos++] = 0x78;
        b.buf[b.pos++] = 0x01;
        b.put(1, 1);   // BFINAL
        b.put(1, 2);   // BTYPE = 01 (фиксированные коды)
        int i = 0;
        while (i < len) {
            int run = 0;
            if (i > 0) {
                byte prev = data[i - 1];
                while (i + run < len && run < 258 && data[i + run] == prev) run++;
            }
            if (run >= 3) {
                match(b, run);
                i += run;
            } else {
                literal(b, data[i] & 0xFF);
                i++;
            }
        }
        b.putCode(0, 7);   // конец блока (символ 256)
        b.flushByte();
        // Adler-32 (big endian)
        int s1 = 1, s2 = 0;
        for (int k = 0; k < len; k++) {
            s1 = (s1 + (data[k] & 0xFF)) % 65521;
            s2 = (s2 + s1) % 65521;
        }
        int adler = (s2 << 16) | s1;
        b.ensure(4);
        b.buf[b.pos++] = (byte) (adler >>> 24);
        b.buf[b.pos++] = (byte) (adler >>> 16);
        b.buf[b.pos++] = (byte) (adler >>> 8);
        b.buf[b.pos++] = (byte) adler;
        byte[] out = new byte[b.pos];
        System.arraycopy(b.buf, 0, out, 0, b.pos);
        return out;
    }
}
