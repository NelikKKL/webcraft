package java.awt.image;

/**
 * getData() возвращает ТОТ ЖЕ int[] массив, что хранится внутри
 * BufferedImage (не копию) — ровно как ведёт себя настоящий
 * java.awt.image.DataBufferInt: изменения через getData() напрямую видны
 * в изображении. decomp-код (lf.java) полагается ровно на эту семантику
 * для прямой записи пикселей в обход setRGB.
 */
public final class DataBufferInt extends DataBuffer {
    private final int[] data;

    DataBufferInt(int[] data) {
        this.data = data;
    }

    public int[] getData() {
        return data;
    }
}
