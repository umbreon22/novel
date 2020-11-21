package novel.api.types.write.pens;

import novel.api.types.write.writers.CharDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface CharPen {

    /**
     * Writes a char.
     * @param c a char
     * @return {@code this}
     */
    CharPen chars(char c);

    /**
     * Writes chars.
     * @param chars a varargs array of chars
     * @return {@code this}
     */
    default CharPen chars(char... chars) {
        for(char c : chars) chars(c);
        return this;
    }

    /**
     * @see #chars
     * @return {@code this}
     */
    default CharPen chars(Supplier<Character> chars) {
        return chars(chars.get());
    }

    /**
     * Writes char arrays.
     * @param chars a varargs array of char arrays.
     * @return {@code this}
     */
    default CharPen chars(char[]... chars) {
        for(char[] c : chars) chars(c);
        return this;
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param chars a {@link Iterable<Character>}
     * @return {@code this}
     */
    default CharPen chars(Iterable<Character> chars) {
        for(char c : chars) chars(c);
        return this;
    }

    /**
     * Writes a {@link Stream<Character>}.
     * @param chars a {@link Stream<Character>}
     * @return {@code this}
     */
    default CharPen chars(Stream<Character> chars) {
        chars.forEach(this::chars);
        return this;
    }

    /**
     * Writes a {@link char} using the provided {@code charWriter}
     * @param c a char.
     * @param charWriter a {@link CharDataWriter} instance
     * @return {@code this}
     */
    default CharPen chars(char c, CharDataWriter charWriter) {
        Objects.requireNonNull(charWriter).write(this, c);
        return this;
    }

    /**
     * Writes a {@link char[]} using the provided {@code charWriter}
     * @param chars a char array
     * @param charWriter a {@link CharDataWriter} instance
     * @return {@code this}
     */
    default CharPen chars(char[] chars, CharDataWriter charWriter) {
        Objects.requireNonNull(charWriter);
        for(char c : chars) {
            charWriter.write(this, c);
        }
        return this;
    }

}
