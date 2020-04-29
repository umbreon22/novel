package novel.api.types.write.pens;

import java.util.function.Supplier;

public interface BoolPen<Pen> {

    /**
     * Writes a boolean.
     * @param b a boolean
     * @return {@link #parameterizedThis()}
     */
    Pen bools(boolean b);

    /**
     * Writes booleans.
     * @param booleans a varargs array of booleans
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(boolean... booleans) {
        for(boolean b : booleans) bools(b);
        return parameterizedThis();
    }

    /**
     * Writes {@link boolean[]}s.
     * @param bools a varargs array of {@link boolean[]}s
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(boolean[]... bools) {
        for(boolean[] b : bools) bools(b);
        return parameterizedThis();
    }

    /**
     * @see #bools(boolean)
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(Supplier<Boolean> booleans) {
        return bools(booleans.get());
    }

    /**
     * Writes an {@link Iterable} of booleans.
     * @param booleans a {@link Iterable<Boolean>}
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(Iterable<Boolean> booleans) {
        for(boolean b : booleans) bools(b);
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
