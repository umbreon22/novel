package novel.api.types.write.pens;

import novel.api.types.write.writers.LongDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface LongPen {

    /**
     * Writes a long.
     * @param l a long
     * @return {@code this}
     */
    LongPen longs(long l);

    /**
     * Writes longs.
     * @param longs a varargs array of longs
     * @return {@code this}
     */
    default LongPen longs(long... longs) {
        for(long l : longs) longs(l);
        return this;
    }

    /**
     * Writes {@link long[]}s.
     * @param longs a varargs array of {@link long[]}s
     * @return {@code this}
     */
    default LongPen longs(long[]... longs) {
        for(long[] l : longs) longs(l);
        return this;
    }

    /**
     * @see #longs(long)
     * @return {@code this}
     */
    default LongPen longs(Supplier<Long> longs) {
        return longs(longs.get());
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param numbers a {@link Iterable} of {@link Number}s
     * @return {@code this}
     */
    default LongPen longs(Iterable<? extends Number> numbers) {
        for(Number num : numbers) longs(num.longValue());
        return this;
    }

    /**
     * Writes an {@link Iterable} using {@code longDataWriter}
     * @param numbers a {@link Iterable} of {@link Number}s
     * @param longDataWriter a {@link LongDataWriter}
     * @return {@code this}
     */
    default LongPen longs(Iterable<? extends Number> numbers, LongDataWriter longDataWriter) {
        numbers.forEach(num -> this.longs(num.longValue(), longDataWriter));
        return this;
    }

    /**
     * Writes a {@link Stream} using {@link Number#longValue()}.
     * @param numbers a {@link Stream} of {@link Number}s.
     * @return {@code this}
     */
    default LongPen longs(Stream<? extends Number> numbers) {
        numbers.forEach(number -> this.longs(number.longValue()));
        return this;
    }

    /**
     * Writes a {@link Stream} using {@code longDataWriter}
     * @param numbers a {@link Stream} of {@link Number}s
     * @param longDataWriter a {@link LongDataWriter}
     * @return {@code this}
     */
    default LongPen longs(Stream<? extends Number> numbers, LongDataWriter longDataWriter) {
        numbers.forEach(num -> this.longs(num.longValue(), longDataWriter));
        return this;
    }

    /**
     * Writes a {@link LongStream}.
     * @param longs a {@link LongStream}
     * @return {@code this}
     */
    default LongPen longs(LongStream longs) {
        longs.forEach(this::longs);
        return this;
    }

    /**
     * Writes a {@link LongStream} using {@code longDataWriter}
     * @param longs a {@link LongStream}
     * @param longDataWriter a {@link LongDataWriter}
     * @return {@code this}
     */
    default LongPen longs(LongStream longs, LongDataWriter longDataWriter) {
        longs.forEach(l -> this.longs(l, longDataWriter));
        return this;
    }

    /**
     * Writes a {@link long} using the provided {@code longWriter}
     * @param l a long.
     * @param longWriter a {@link LongDataWriter} instance
     * @return {@code this}
     */
    default LongPen longs(long l, LongDataWriter longWriter) {
        Objects.requireNonNull(longWriter).write(this, l);
        return this;
    }

    /**
     * Writes a {@link long[]} using the provided {@code longWriter}
     * @param longs a long array
     * @param longWriter a {@link LongDataWriter} instance
     * @return {@code this}
     */
    default LongPen longs(long[] longs, LongDataWriter longWriter) {
        Objects.requireNonNull(longWriter);
        for(long l : longs) {
            longWriter.write(this, l);
        }
        return this;
    }

}
