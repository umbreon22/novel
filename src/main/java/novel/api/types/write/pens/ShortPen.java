package novel.api.types.write.pens;

import java.util.function.Supplier;

public interface ShortPen<Pen> {

    /**
     * Writes a short.
     * @param s a short
     * @return {@link #parameterizedThis()}
     */
    Pen shorts(short s);

    /**
     * Writes shorts.
     * @param shorts a varargs array of shorts
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(short... shorts) {
        for(short s : shorts) shorts(s);
        return parameterizedThis();
    }

    /**
     * @see #shorts(short)
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(Supplier<Short> shorts) {
        return shorts(shorts.get());
    }

    /**
     * Writes {@link short[]}s.
     * @param shorts a varargs array of {@link short[]}s
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(short[]... shorts) {
        for(short[] s : shorts) shorts(s);
        return parameterizedThis();
    }

    /**
     * Writes an {@link Iterable} of shorts.
     * @param shorts a {@link Iterable<Short>}
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(Iterable<Short> shorts) {
        for(short s : shorts) shorts(s);
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
