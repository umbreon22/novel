package novel.api.types.write.pens;

import java.util.function.Supplier;

public interface BytePen<Pen> {

    /**
     * Writes a byte.
     * @param b a byte
     * @return {@link #parameterizedThis()}
     */
    Pen bytes(byte b);

    /**
     * Writes bytes.
     * @param bytes a varargs array of bytes
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(byte... bytes) {
        for(byte b : bytes) bytes(b);
        return parameterizedThis();
    }

    /**
     * Writes bytes arrays.
     * @param bytes a varargs array of byte arrays.
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(byte[]... bytes) {
        for(byte[] b : bytes) bytes(b);
        return parameterizedThis();
    }

    /**
     * @see #bytes(byte)
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(Supplier<Byte> bytes) {
        return bytes(bytes.get());
    }

    /**
     * Writes an {@link Iterable} of bytes.
     * @param bytes a {@link Iterable<Byte>}
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(Iterable<Byte> bytes) {
        for(byte b : bytes) bytes(b);
        return parameterizedThis();
    }

    /**
     * Returns {@code this} cast as your defined type parameter, {@link Pen}
     * @return {@code this) cast as {@link Pen}
     */
    @SuppressWarnings("unchecked")
    private Pen parameterizedThis() {
        return (Pen) this;
    }
}
