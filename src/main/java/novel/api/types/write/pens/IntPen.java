package novel.api.types.write.pens;

import novel.api.types.write.writers.IntDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface IntPen<Pen> {

    /**
     * Writes an int.
     * @param i an int
     * @return {@link #parameterizedThis()}
     */
    Pen ints(int i);

    /**
     * Writes ints.
     * @param ints a varargs array of ints.
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(int... ints) {
        for(int i : ints) ints(i);
        return parameterizedThis();
    }

    /**
     * Writes int arrays.
     * @param ints an array of int arrays.
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(int[]... ints) {
        for(int[] i : ints) ints(i);
        return parameterizedThis();
    }

    /**
     * @see #ints(int)
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(Supplier<Integer> ints) {
        return ints(ints.get());
    }

    /**
     * Writes an {@link Iterable} of int.
     * @param ints a {@link Iterable<Integer>}
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(Iterable<Integer> ints) {
        for(int i : ints) ints(i);
        return parameterizedThis();
    }

    /**
     * Writes an {@link int} using the provided {@code intWriter}
     * @param i an int.
     * @param intWriter a {@link IntDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen ints(int i, IntDataWriter intWriter) {
        Objects.requireNonNull(intWriter).write(this, i);
        return parameterizedThis();
    }

    /**
     * Writes an {@link int[]} using the provided {@code intWriter}
     * @param ints an int array
     * @param intWriter a {@link IntDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen ints(int[] ints, IntDataWriter intWriter) {
        Objects.requireNonNull(intWriter);
        for(int object : ints) {
            intWriter.write(this, object);
        }
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
