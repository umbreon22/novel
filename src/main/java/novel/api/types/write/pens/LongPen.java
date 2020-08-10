package novel.api.types.write.pens;

import novel.api.types.write.writers.LongDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface LongPen<Pen> {

    /**
     * Writes a long.
     * @param l a long
     * @return {@link #parameterizedThis()}
     */
    Pen longs(long l);

    /**
     * Writes longs.
     * @param longs a varargs array of longs
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(long... longs) {
        for(long l : longs) longs(l);
        return parameterizedThis();
    }

    /**
     * Writes {@link long[]}s.
     * @param longs a varargs array of {@link long[]}s
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(long[]... longs) {
        for(long[] l : longs) longs(l);
        return parameterizedThis();
    }

    /**
     * @see #longs(long)
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(Supplier<Long> longs) {
        return longs(longs.get());
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param longs a {@link Iterable<Character>}
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(Iterable<Long> longs) {
        for(long l : longs) longs(l);
        return parameterizedThis();
    }

    /**
     * Writes a {@link long} using the provided {@code longWriter}
     * @param l a long.
     * @param longWriter a {@link LongDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen longs(long l, LongDataWriter longWriter) {
        Objects.requireNonNull(longWriter).write(this, l);
        return parameterizedThis();
    }

    /**
     * Writes a {@link long[]} using the provided {@code longWriter}
     * @param longs a long array
     * @param longWriter a {@link LongDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen longs(long[] longs, LongDataWriter longWriter) {
        Objects.requireNonNull(longWriter);
        for(long l : longs) {
            longWriter.write(this, l);
        }
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
