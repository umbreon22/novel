package novel.api.types.write.pens;

import java.util.function.Supplier;

public interface CharPen<Pen> {

    /**
     * Writes a char.
     * @param c a char
     * @return {@link #parameterizedThis()}
     */
    Pen chars(char c);

    /**
     * Writes chars.
     * @param chars a varargs array of chars
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(char... chars) {
        for(char c : chars) chars(c);
        return parameterizedThis();
    }

    /**
     * @see #chars
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(Supplier<Character> chars) {
        return chars(chars.get());
    }

    /**
     * Writes char arrays.
     * @param chars a varargs array of char arrays.
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(char[]... chars) {
        for(char[] c : chars) chars(c);
        return parameterizedThis();
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param chars a {@link Iterable<Character>}
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(Iterable<Character> chars) {
        for(char c : chars) chars(c);
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
