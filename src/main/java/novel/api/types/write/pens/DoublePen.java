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
     * Writes an {@link Iterable} of doubles.
     * @param doubles a {@link Iterable<Double>}
     * @return {@code this}
     */
    default DoublePen doubles(Iterable<Double> doubles) {
        for(double d : doubles) doubles(d);
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
     * Writes a {@link DoubleStream} as doubles.
     * @param doubles a {@link DoubleStream}
     * @return {@code this}
     */
    default DoublePen doubles(DoubleStream doubles) {
        doubles.forEach(this::doubles);
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
