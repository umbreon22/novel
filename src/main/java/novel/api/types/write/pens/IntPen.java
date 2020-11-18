package novel.api.types.write.pens;

import novel.api.types.write.writers.IntDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface IntPen {

    /**
     * Writes an int.
     * @param i an int
     * @return {@code this}
     */
    IntPen ints(int i);

    /**
     * Writes ints.
     * @param ints a varargs array of ints.
     * @return {@code this}
     */
    default IntPen ints(int... ints) {
        for(int i : ints) ints(i);
        return this;
    }

    /**
     * Writes int arrays.
     * @param ints an array of int arrays.
     * @return {@code this}
     */
    default IntPen ints(int[]... ints) {
        for(int[] i : ints) ints(i);
        return this;
    }

    /**
     * @see #ints(int)
     * @return {@code this}
     */
    default IntPen ints(Supplier<Integer> ints) {
        return ints(ints.get());
    }

    /**
     * Writes an {@link Iterable} of int.
     * @param ints a {@link Iterable<Integer>}
     * @return {@code this}
     */
    default IntPen ints(Iterable<Integer> ints) {
        for(int i : ints) ints(i);
        return this;
    }

    /**
     * Writes an {@link int} using the provided {@code intWriter}
     * @param i an int.
     * @param intWriter a {@link IntDataWriter} instance
     * @return {@code this}
     */
    default IntPen ints(int i, IntDataWriter intWriter) {
        Objects.requireNonNull(intWriter).write(this, i);
        return this;
    }

    /**
     * Writes an {@link int[]} using the provided {@code intWriter}
     * @param ints an int array
     * @param intWriter a {@link IntDataWriter} instance
     * @return {@code this}
     */
    default IntPen ints(int[] ints, IntDataWriter intWriter) {
        Objects.requireNonNull(intWriter);
        for(int object : ints) {
            intWriter.write(this, object);
        }
        return this;
    }

}
