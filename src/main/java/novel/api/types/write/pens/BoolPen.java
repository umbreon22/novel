package novel.api.types.write.pens;

import novel.api.types.write.writers.BoolDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BoolPen {

    /**
     * Writes a boolean.
     * @param b a boolean
     * @return {@code this}
     */
    BoolPen bools(boolean b);

    /**
     * Writes booleans.
     * @param booleans a varargs array of booleans
     * @return {@code this}
     */
    default BoolPen bools(boolean... booleans) {
        for(boolean b : booleans) bools(b);
        return this;
    }

    /**
     * Writes {@link boolean[]}s.
     * @param bools a varargs array of {@link boolean[]}s
     * @return {@code this}
     */
    default BoolPen bools(boolean[]... bools) {
        for(boolean[] b : bools) bools(b);
        return this;
    }

    /**
     * @see #bools(boolean)
     * @return {@code this}
     */
    default BoolPen bools(Supplier<Boolean> booleans) {
        return bools(booleans.get());
    }

    /**
     * Writes an {@link Iterable} of booleans.
     * @param booleans a {@link Iterable<Boolean>}
     * @return {@code this}
     */
    default BoolPen bools(Iterable<Boolean> booleans) {
        for(boolean b : booleans) bools(b);
        return this;
    }

    /**
     * Writes a {@link boolean} using the provided {@code booleanWriter}
     * @param b a boolean.
     * @param boolWriter a {@link BoolDataWriter} instance
     * @return {@code this}
     */
    default BoolPen bools(boolean b, BoolDataWriter boolWriter) {
        Objects.requireNonNull(boolWriter).write(this, b);
        return this;
    }

    /**
     * Writes a {@link boolean[]} using the provided {@code booleanWriter}
     * @param booleans a boolean array
     * @param boolWriter a {@link BoolDataWriter} instance
     * @return {@code this}
     */
    default BoolPen bools(boolean[] booleans, BoolDataWriter boolWriter) {
        Objects.requireNonNull(boolWriter);
        for(boolean b : booleans) {
            boolWriter.write(this, b);
        }
        return this;
    }

    /**
     * Writes a {@link Stream <Boolean>} as booleans.
     * @param bools a {@link Stream<Boolean>}
     * @return {@code this}
     */
    default BoolPen bools(Stream<Boolean> bools) {
        bools.forEach(this::bools);
        return this;
    }

    /**
     * Writes a {@link Stream <Boolean>} using the provided {@code booleanWriter}
     * @param bools a {@link Stream<Boolean>}
     * @param boolWriter a {@link BoolDataWriter} instance
     * @return {@code this}
     */
    default BoolPen bools(Stream<Boolean> bools, BoolDataWriter boolWriter) {
        bools.forEach(b -> boolWriter.write(this, b));
        return this;
    }

}
