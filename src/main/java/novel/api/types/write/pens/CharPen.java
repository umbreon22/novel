package novel.api.types.write.pens;

import novel.api.types.write.writers.CharDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
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
     * Writes an {@link Iterable} of chars with {@link CharDataWriter}
     * @param chars a {@link Iterable<Character>}
     * @param charWriter a {@link CharDataWriter}
     * @return {@code this}
     */
    default CharPen chars(Iterable<Character> chars, CharDataWriter charWriter) {
        for(char c : chars) chars(c, charWriter);
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
     * Writes a {@link Stream<Character>}.
     * @param chars a {@link Stream<Character>}
     * @return {@code this}
     */
    default CharPen chars(Stream<Character> chars, CharDataWriter charWriter) {
        chars.forEach(c -> charWriter.write(this, c));
        return this;
    }

    /**
     * Writes a {@code char} using the provided {@code charWriter}
     * @param c a char
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

    /**
     * Writes an {@link IntStream} as chars.
     * @param ints a {@link IntStream}
     * @return {@code this}
     */
    default CharPen chars(IntStream ints) {
        ints.forEach(i->this.chars((char)i));
        return this;
    }

    /**
     * Writes an {@link IntStream} using a {@link CharDataWriter}
     * @param ints a {@link IntStream}
     * @param charWriter a {@link CharDataWriter} instance
     * @return {@code this}
     */
    default CharPen chars(IntStream ints, CharDataWriter charWriter) {
        ints.forEach(i -> charWriter.write(this, (char) i));
        return this;
    }

    /**
     * Writes an {@link LongStream} as chars
     * @param longs a {@link LongStream}
     * @return {@code this}
     */
    default CharPen chars(LongStream longs) {
        longs.forEach(l -> this.chars((char)l));
        return this;
    }

    /**
     * Writes a {@link LongStream} using a {@link CharDataWriter}
     * @param longs a {@link LongStream}
     * @param charWriter a {@link CharDataWriter} instance
     * @return {@code this}
     */
    default CharPen chars(LongStream longs, CharDataWriter charWriter) {
        longs.forEach(l -> charWriter.write(this, (char) l));
        return this;
    }

    /**
     * Writes a {@link DoubleStream} as chars
     * @param doubles a {@link DoubleStream}
     * @return {@code this}
     */
    default CharPen chars(DoubleStream doubles) {
        doubles.forEach(d -> this.chars((char)d));
        return this;
    }

    /**
     * Writes a {@link DoubleStream} using a {@link CharDataWriter}
     * @param doubles a {@link DoubleStream}
     * @param charWriter a {@link CharDataWriter} instance
     * @return {@code this}
     */
    default CharPen chars(DoubleStream doubles, CharDataWriter charWriter) {
        doubles.forEach(d -> charWriter.write(this, (char)d));
        return this;
    }

}
