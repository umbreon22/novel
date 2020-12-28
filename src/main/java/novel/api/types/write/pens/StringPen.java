package novel.api.types.write.pens;

import novel.api.types.write.writers.StringDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface StringPen {

    /**
     * Writes a {@link CharSequence}.
     * @param str a {@link CharSequence}
     * @return {@code this}
     */
    StringPen strings(CharSequence str);

    /**
     * Writes {@link CharSequence}s.
     * @param strings a varargs array of {@link CharSequence}s
     * @return {@code this}
     */
    default StringPen strings(CharSequence... strings) {
        for(CharSequence str : strings) strings(str);
        return this;
    }

    /**
     * Writes {@link CharSequence}s arrays.
     * @param strings a varargs array of {@link CharSequence[]}s
     * @return {@code this}
     */
    default StringPen strings(CharSequence[]... strings) {
        for(CharSequence[] str : strings) strings(str);
        return this;
    }

    /**
     * @see #strings
     * @return {@code this}
     */
    default StringPen strings(Supplier<CharSequence> string) {
        return strings(string.get());
    }

    /**
     * Writes an object as a String.
     * Default implementation uses {@link Objects#toString(Object)} and should be null safe.
     * @see #strings(CharSequence)
     * @param toStringMe an object
     * @return {@code this}
     */
    default StringPen strings(Object toStringMe) {
        strings(Objects.toString(toStringMe));
        return this;
    }

    /**
     * Writes an varags object array as {@link String}s.
     * @see #strings(Object)
     * @param toStringMe an varargs array of objects to write as string
     * @return {@code this}
     */
    default StringPen strings(Object... toStringMe) {
        for(Object object : toStringMe) {
            strings(object);
        }
        return this;
    }

    /**
     * Writes {@link String} arrays.
     * @param toStringMe a varargs array of Object arrays.
     * @return {@code this}
     */
    default StringPen strings(Object[]... toStringMe) {
        for(Object[] objects : toStringMe) strings(objects);
        return this;
    }

    /**
     * Writes an {@link Iterable} of strings.
     * @param strings a {@link Iterable<CharSequence>}
     * @return {@code this}
     */
    default StringPen strings(Iterable<? extends CharSequence> strings) {
        for(CharSequence s : strings) strings(s);
        return this;
    }

    /**
     * Writes a {@link CharSequence} using the provided {@code stringWriter}
     * @param s a {@link CharSequence}.
     * @param stringWriter a {@link StringDataWriter} instance
     * @return {@code this}
     */
    default StringPen strings(CharSequence s, StringDataWriter stringWriter) {
        Objects.requireNonNull(stringWriter).write(this, Objects.toString(s));
        return this;
    }

    /**
     * Writes a {@link CharSequence[]} using the provided {@code stringWriter}
     * @param strings a CharSequence array
     * @param stringWriter a {@link StringDataWriter} instance
     * @return {@code this}
     */
    default StringPen strings(CharSequence[] strings, StringDataWriter stringWriter) {
        Objects.requireNonNull(stringWriter);
        for(CharSequence s : strings) {
            stringWriter.write(this, Objects.toString(s));
        }
        return this;
    }

}
