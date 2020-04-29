package novel.api.types.write.pens;

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
     * Returns {@code this} cast as your defined type parameter, {@link Pen}
     * @return {@code this) cast as {@link Pen}
     */
    @SuppressWarnings("unchecked")
    private Pen parameterizedThis() {
        return (Pen) this;
    }
}
