package novel.api.types.write.pens;

import novel.api.types.write.writers.DoubleDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public interface DoublePen {

    /**
     * Writes a double.
     * @param d a double
     * @return {@code this}
     */
    DoublePen doubles(double d);

    /**
     * Writes doubles.
     * @param doubles a varargs array of doubles
     * @return {@code this}
     */
    default DoublePen doubles(double... doubles) {
        for(double d : doubles) doubles(d);
        return this;
    }

    /**
     * Writes {@link double[]}s.
     * @param doubles a varargs array of {@link double[]}s
     * @return {@code this}
     */
    default DoublePen doubles(double[]... doubles) {
        for(double[] d : doubles) doubles(d);
        return this;
    }

    /**
     * @see #doubles(double)
     * @return {@code this}
     */
    default DoublePen doubles(Supplier<Double> doubles) {
        return doubles(doubles.get());
    }

    /**
     * Writes an {@link Iterable} of {@link Number}s as {@link Number#doubleValue()}.
     * @param numbers an {@link Iterable}
     * @return {@code this}
     */
    default DoublePen doubles(Iterable<? extends Number> numbers) {
        for(Number num : numbers) doubles(num.doubleValue());
        return this;
    }

    /**
     * Writes an {@link Iterable} of {@link Number}s using {@link DoubleDataWriter}
     * @param numbers an {@link Iterable}
     * @param doubleDataWriter a {@link DoubleDataWriter}
     * @return {@code this}
     */
    default DoublePen doubles(Iterable<? extends Number> numbers, DoubleDataWriter doubleDataWriter) {
        for(Number nums : numbers) doubles(nums.doubleValue(), doubleDataWriter);
        return this;
    }

    /**
     * Writes a {@link Stream} using {@link Number#doubleValue()}.
     * @param numbers a {@link Stream} of {@link Number}s
     * @return {@code this}
     */
    default DoublePen doubles(Stream<? extends Number> numbers) {
        numbers.forEach(number->this.doubles(number.doubleValue()));
        return this;
    }

    /**
     * Writes a {@link Stream} of {@link Number}s using {@link DoubleDataWriter}
     * @param numbers an {@link Stream} of {@link Number}s
     * @param doubleDataWriter a {@link DoubleDataWriter}
     * @return {@code this}
     */
    default DoublePen doubles(Stream<? extends Number> numbers, DoubleDataWriter doubleDataWriter) {
        numbers.forEach(num -> this.doubles(num.doubleValue(), doubleDataWriter));
        return this;
    }

    /**
     * Writes a {@link DoubleStream} as doubles.
     * @param doubles a {@link DoubleStream}
     * @return {@code this}
     */
    default DoublePen doubles(DoubleStream doubles) {
        doubles.forEach(this::doubles);
        return this;
    }

    /**
     * Writes a {@link DoubleStream} using {@code doubleDataWriter}
     * @param doubles a {@link DoubleStream}
     * @param doubleDataWriter a {@link DoubleDataWriter}
     * @return {@code this}
     */
    default DoublePen doubles(DoubleStream doubles, DoubleDataWriter doubleDataWriter) {
        doubles.forEach(d -> this.doubles(d, doubleDataWriter));
        return this;
    }

    /**
     * Writes a {@link double} using the provided {@code doubleWriter}
     * @param d a double.
     * @param doubleWriter a {@link DoubleDataWriter} instance
     * @return {@code this}
     */
    default DoublePen doubles(double d, DoubleDataWriter doubleWriter) {
        Objects.requireNonNull(doubleWriter).write(this, d);
        return this;
    }

    /**
     * Writes a {@link double[]} using the provided {@code doubleWriter}
     * @param doubles a double array
     * @param doubleWriter a {@link DoubleDataWriter} instance
     * @return {@code this}
     */
    default DoublePen doubles(double[] doubles, DoubleDataWriter doubleWriter) {
        Objects.requireNonNull(doubleWriter);
        for(double d : doubles) {
            doubleWriter.write(this, d);
        }
        return this;
    }
    
}
