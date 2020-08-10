package novel.api.types.write.pens;

import novel.api.types.write.writers.FloatDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface FloatPen<Pen> {

    /**
     * Writes a float.
     * @param f a float
     * @return {@link #parameterizedThis()}
     */
    Pen floats(float f);

    /**
     * Writes floats.
     * @param floats a varargs array of floats
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(float... floats) {
        for(float f : floats) floats(f);
        return parameterizedThis();
    }

    /**
     * Writes {@link float[]}s.
     * @param floats a varargs array of floats
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(float[]... floats) {
        for(float[] f : floats) floats(f);
        return parameterizedThis();
    }

    /**
     * @see #floats(float)
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(Supplier<Float> floats) {
        return floats(floats.get());
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param floats a {@link Iterable<Character>}
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(Iterable<Float> floats) {
        for(float f : floats) floats(f);
        return parameterizedThis();
    }

    /**
     * Writes a {@link float} using the provided {@code floatWriter}
     * @param f a float.
     * @param floatWriter a {@link FloatDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen floats(float f, FloatDataWriter floatWriter) {
        Objects.requireNonNull(floatWriter).write(this, f);
        return parameterizedThis();
    }

    /**
     * Writes a {@link float[]} using the provided {@code floatWriter}
     * @param floats a float array
     * @param floatWriter a {@link FloatDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen floats(float[] floats, FloatDataWriter floatWriter) {
        Objects.requireNonNull(floatWriter);
        for(float f : floats) {
            floatWriter.write(this, f);
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
