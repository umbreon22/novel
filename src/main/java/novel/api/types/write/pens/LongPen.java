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
     * @param longs a {@link Iterable<Character>}
     * @return {@code this}
     */
    default LongPen longs(Iterable<Long> longs) {
        for(long l : longs) longs(l);
        return this;
    }

    /**
     * Writes a {@link Stream <Long>} of longs.
     * @param longs a {@link Stream<Long>}
     * @return {@code this}
     */
    default LongPen longs(Stream<Long> longs) {
        longs.forEach(this::longs);
        return this;
    }

    /**
     * Writes an {@link LongStream}.
     * @param longs a {@link LongStream}
     * @return {@code this}
     */
    default LongPen longs(LongStream longs) {
        longs.forEach(this::longs);
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
