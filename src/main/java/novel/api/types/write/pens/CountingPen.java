package novel.api.types.write.pens;

/**
 * This {@link DataPen} keeps a running count on how much data you'd output without doing any actual writing.
 */
public class CountingPen implements DataPen {

    private int byteCount = 0;
    public int count() {
        return byteCount;
    }

    @Override
    public CountingPen bools(boolean b) {
        byteCount += Byte.BYTES;
        return this;
    }

    @Override
    public CountingPen ints(int i) {
        byteCount += Integer.BYTES;
        return this;
    }

    @Override
    public CountingPen longs(long l) {
        byteCount += Long.BYTES;
        return this;
    }

    @Override
    public CountingPen shorts(short s) {
        byteCount += Short.BYTES;
        return this;
    }

    @Override
    public CountingPen floats(float f) {
        byteCount += Float.BYTES;
        return this;
    }

    @Override
    public CountingPen doubles(double d) {
        byteCount += Double.BYTES;
        return this;
    }

    @Override
    public CountingPen strings(CharSequence str) {
        if(str != null) {
            byteCount += Short.BYTES;
            byteCount += str.length() * Byte.BYTES;
        }
        return this;
    }

    @Override
    public CountingPen chars(char c) {
        byteCount += Character.BYTES;
        return this;
    }

    @Override
    public CountingPen bytes(byte b) {
        byteCount += Byte.BYTES;
        return this;
    }

}
