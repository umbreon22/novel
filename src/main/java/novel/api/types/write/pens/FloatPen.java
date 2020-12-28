package novel.api.types.write.pens;

import novel.api.types.write.writers.FloatDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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
     * @param numbers a {@link Iterable<Character>}
     * @return {@code this}
     */
    default FloatPen floats(Iterable<? extends Number> numbers) {
        for(Number num : numbers) floats(num.floatValue());
        return this;
    }
    /**
     * Writes an {@link Iterable} of chars.
     * @param numbers a {@link Iterable<Character>}
     * @return {@code this}
     */
    default FloatPen floats(Iterable<? extends Number> numbers, FloatDataWriter floatDataWriter) {
        for(Number num : numbers) floats(num.floatValue(), floatDataWriter);
        return this;
    }

    /**
     * Writes a {@link Stream} using {@link Number#floatValue()}.
     * @param numbers a {@link Stream} of {@link Number}s.
     * @return {@code this}
     */
    default FloatPen floats(Stream<? extends Number> numbers) {
        numbers.forEach(number -> this.floats(number.floatValue()));
        return this;
    }

    /**
     * Writes a {@link Stream} using {@code floatDataWriter}.
     * @param numbers a {@link Stream} of {@link Number}s.
     * @param floatDataWriter a {@link FloatDataWriter}
     * @return {@code this}
     */
    default FloatPen floats(Stream<? extends Number> numbers, FloatDataWriter floatDataWriter) {
        numbers.forEach(number -> this.floats(number.floatValue(), floatDataWriter));
        return this;
    }


    /**
     * Writes a {@link DoubleStream} as floats.
     * @param doubles a {@link DoubleStream}
     * @return {@code this}
     */
    default FloatPen floats(DoubleStream doubles) {
        doubles.forEach(d -> this.floats((float) d));
        return this;
    }

    /**
     * Writes a {@link DoubleStream} using {@code floatDataWriter}.
     * @param doubles a {@link DoubleStream}
     * @param floatDataWriter a {@link FloatDataWriter}
     * @return {@code this}
     */
    default FloatPen floats(DoubleStream doubles, FloatDataWriter floatDataWriter) {
        doubles.forEach(number -> this.floats((float) number, floatDataWriter));
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
