package novel.api.types.write.pens;

import java.util.Objects;
import java.util.function.Supplier;

public interface StringPen<Pen> {

    /**
     * Writes a {@link CharSequence}.
     * @param str a {@link CharSequence}
     * @return {@link #parameterizedThis()}
     */
    Pen strings(CharSequence str);

    /**
     * Writes {@link CharSequence}s.
     * @param strings a varargs array of {@link CharSequence}s
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(CharSequence... strings) {
        for(CharSequence str : strings) strings(str);
        return parameterizedThis();
    }

    /**
     * Writes {@link CharSequence}s arrays.
     * @param strings a varargs array of {@link CharSequence[]}s
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(CharSequence[]... strings) {
        for(CharSequence[] str : strings) strings(str);
        return parameterizedThis();
    }

    /**
     * @see #strings
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Supplier<CharSequence> string) {
        return strings(string.get());
    }

    /**
     * Writes an object as a String.
     * Default implementation uses {@link Objects#toString(Object)} and should be null safe.
     * @see #strings(CharSequence)
     * @param toStringMe an object
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Object toStringMe) {
        strings(Objects.toString(toStringMe));
        return parameterizedThis();
    }

    /**
     * Writes an varags object array as {@link String}s.
     * @see #strings(Object)
     * @param toStringMe an varargs array of objects to write as string
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Object... toStringMe) {
        for(Object object : toStringMe) {
            strings(object);
        }
        return parameterizedThis();
    }

    /**
     * Writes {@link String} arrays.
     * @param toStringMe a varargs array of Object arrays.
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Object[]... toStringMe) {
        for(Object[] objects : toStringMe) strings(objects);
        return parameterizedThis();
    }

    /**
     * Writes an {@link Iterable} of strings.
     * @param strings a {@link Iterable<CharSequence>}
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Iterable<? extends CharSequence> strings) {
        for(CharSequence s : strings) strings(s);
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
