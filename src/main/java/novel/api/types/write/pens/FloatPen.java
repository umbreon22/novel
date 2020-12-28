package novel.api.types.write.pens;

import novel.api.types.write.writers.FloatDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface FloatPen {

    /**
     * Writes a float.
     * @param f a float
     * @return {@code this}
     */
    FloatPen floats(float f);

    /**
     * Writes floats.
     * @param floats a varargs array of floats
     * @return {@code this}
     */
    default FloatPen floats(float... floats) {
        for(float f : floats) floats(f);
        return this;
    }

    /**
     * Writes {@link float[]}s.
     * @param floats a varargs array of floats
     * @return {@code this}
     */
    default FloatPen floats(float[]... floats) {
        for(float[] f : floats) floats(f);
        return this;
    }

    /**
     * @see #floats(float)
     * @return {@code this}
     */
    default FloatPen floats(Supplier<Float> floats) {
        return floats(floats.get());
    }

    /**
     * Writes an {@link Iterable} of chars.
     * @param floats a {@link Iterable<Character>}
     * @return {@code this}
     */
    default FloatPen floats(Iterable<Float> floats) {
        for(float f : floats) floats(f);
        return this;
    }

    /**
     * Writes a {@link float} using the provided {@code floatWriter}
     * @param f a float.
     * @param floatWriter a {@link FloatDataWriter} instance
     * @return {@code this}
     */
    default FloatPen floats(float f, FloatDataWriter floatWriter) {
        Objects.requireNonNull(floatWriter).write(this, f);
        return this;
    }

    /**
     * Writes a {@link float[]} using the provided {@code floatWriter}
     * @param floats a float array
     * @param floatWriter a {@link FloatDataWriter} instance
     * @return {@code this}
     */
    default FloatPen floats(float[] floats, FloatDataWriter floatWriter) {
        Objects.requireNonNull(floatWriter);
        for(float f : floats) {
            floatWriter.write(this, f);
        }
        return this;
    }

}
