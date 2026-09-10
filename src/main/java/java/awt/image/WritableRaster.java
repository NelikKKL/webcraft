package java.awt.image;

/**
 * Минимальная реализация — только getDataBuffer(), единственный метод,
 * реально используемый в decomp (lf.java, конвертер старых 64x32 скинов).
 */
public class WritableRaster {
    private final DataBufferInt dataBuffer;

    WritableRaster(BufferedImage owner) {
        this.dataBuffer = new DataBufferInt(owner.pixels);
    }

    public DataBuffer getDataBuffer() {
        return dataBuffer;
    }
}
