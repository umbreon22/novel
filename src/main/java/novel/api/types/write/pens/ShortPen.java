package novel.api.types.write.pens;

import novel.api.types.write.writers.ShortDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ShortPen {

    /**
     * Writes a short.
     * @param s a short
     * @return {@code this}
     */
    ShortPen shorts(short s);

    /**
     * Writes shorts.
     * @param shorts a varargs array of shorts
     * @return {@code this}
     */
    default ShortPen shorts(short... shorts) {
        for(short s : shorts) shorts(s);
        return this;
    }

    /**
     * @see #shorts(short)
     * @return {@code this}
     */
    default ShortPen shorts(Supplier<Short> shorts) {
        return shorts(shorts.get());
    }

    /**
     * Writes {@link short[]}s.
     * @param shorts a varargs array of {@link short[]}s
     * @return {@code this}
     */
    default ShortPen shorts(short[]... shorts) {
        for(short[] s : shorts) shorts(s);
        return this;
    }

    /**
     * Writes an {@link Iterable} as {@link Number#shortValue()}
     * @param numbers a {@link Iterable} of {@link Number}s
     * @return {@code this}
     */
    default ShortPen shorts(Iterable<? extends Number> numbers) {
        for(Number num : numbers) shorts(num.shortValue());
        return this;
    }

    /**
     * Writes an {@link Iterable} using {@code shortDataWriter}
     * @param numbers a {@link Iterable} of {@link Number}s
     * @param shortDataWriter a {@link ShortDataWriter}
     * @return {@code this}
     */
    default ShortPen shorts(Iterable<? extends Number> numbers, ShortDataWriter shortDataWriter) {
        for(Number num : numbers) shorts(num.shortValue(), shortDataWriter);
        return this;
    }

    /**
     * Writes a {@link Stream} as {@link Number#shortValue()}.
     * @param numbers a {@link Stream} of {@link Number}s.
     * @return {@code this}
     */
    default ShortPen shorts(Stream<? extends Number> numbers) {
        numbers.forEach(number -> this.shorts(number.shortValue()));
        return this;
    }
    /**
     * Writes an{@link Stream} using {@code shortDataWriter}
     * @param numbers a {@link Stream} of {@link Number}s.
     * @param shortDataWriter a {@link ShortDataWriter}
     * @return {@code this}
     */
    default ShortPen shorts(Stream<? extends Number> numbers, ShortDataWriter shortDataWriter) {
        numbers.forEach(number -> this.shorts(number.shortValue(), shortDataWriter));
        return this;
    }

    /**
     * Writes a {@link short} using the provided {@code shortWriter}
     * @param s a short.
     * @param shortWriter a {@link ShortDataWriter} instance
     * @return {@code this}
     */
    default ShortPen shorts(short s, ShortDataWriter shortWriter) {
        Objects.requireNonNull(shortWriter).write(this, s);
        return this;
    }

    /**
     * Writes a {@link short[]} using the provided {@code shortWriter}
     * @param shorts a short array
     * @param shortWriter a {@link ShortDataWriter} instance
     * @return {@code this}
     */
    default ShortPen shorts(short[] shorts, ShortDataWriter shortWriter) {
        Objects.requireNonNull(shortWriter);
        for(short s : shorts) {
            shortWriter.write(this, s);
        }
        return this;
    }

}
