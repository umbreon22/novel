package novel.api.types.write.pens;

import novel.api.types.write.writers.DoubleDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

public interface DoublePen<Pen> {

    /**
     * Writes a double.
     * @param d a double
     * @return {@link #parameterizedThis()}
     */
    Pen doubles(double d);

    /**
     * Writes doubles.
     * @param doubles a varargs array of doubles
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(double... doubles) {
        for(double d : doubles) doubles(d);
        return parameterizedThis();
    }

    /**
     * Writes {@link double[]}s.
     * @param doubles a varargs array of {@link double[]}s
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(double[]... doubles) {
        for(double[] d : doubles) doubles(d);
        return parameterizedThis();
    }

    /**
     * @see #doubles(double)
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(Supplier<Double> doubles) {
        return doubles(doubles.get());
    }
    /**
     * Writes an {@link Iterable} of doubles.
     * @param doubles a {@link Iterable<Double>}
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(Iterable<Double> doubles) {
        for(double d : doubles) doubles(d);
        return parameterizedThis();
    }

    /**
     * Writes a {@link double} using the provided {@code doubleWriter}
     * @param d a double.
     * @param doubleWriter a {@link DoubleDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen doubles(double d, DoubleDataWriter doubleWriter) {
        Objects.requireNonNull(doubleWriter).write(this, d);
        return parameterizedThis();
    }

    /**
     * Writes a {@link double[]} using the provided {@code doubleWriter}
     * @param doubles a double array
     * @param doubleWriter a {@link DoubleDataWriter} instance
     * @return {@code parameterizedThis()}
     */
    default Pen doubles(double[] doubles, DoubleDataWriter doubleWriter) {
        Objects.requireNonNull(doubleWriter);
        for(double d : doubles) {
            doubleWriter.write(this, d);
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
