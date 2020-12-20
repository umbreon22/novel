package novel.api.types.write.pens;

import novel.api.types.write.writers.ByteDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BytePen {

    /**
     * Writes a byte.
     * @param b a byte
     * @return {@code this}
     */
    BytePen bytes(byte b);

    /**
     * Writes bytes.
     * @param bytes a varargs array of bytes
     * @return {@code this}
     */
    default BytePen bytes(byte... bytes) {
        for(byte b : bytes) bytes(b);
        return this;
    }

    /**
     * Writes bytes arrays.
     * @param bytes a varargs array of byte arrays.
     * @return {@code this}
     */
    default BytePen bytes(byte[]... bytes) {
        for(byte[] b : bytes) bytes(b);
        return this;
    }

    /**
     * @see #bytes(byte)
     * @return {@code this}
     */
    default BytePen bytes(Supplier<Byte> bytes) {
        return bytes(bytes.get());
    }

    /**
     * Writes an {@link Iterable} of numbers as bytes.
     * @param numbers a {@link Iterable} of numbers.
     * @return {@code this}
     */
    default BytePen bytes(Iterable<? extends Number> numbers) {
        for(Number b : numbers) bytes(b.byteValue());
        return this;
    }
	/**
	 * Writes an {@link Iterable} of numbers using {@code byteDataWriter}
	 * @param numbers a {@link Iterable<Number>}
	 * @param byteDataWriter a {@link ByteDataWriter} instance
	 * @return {@code this}
	 */
	default BytePen bytes(Iterable<? extends Number> numbers, ByteDataWriter byteDataWriter) {
		for(Number b : numbers) bytes(b.byteValue(), byteDataWriter);
		return this;
	}

    /**
     * Writes a {@link byte} using the provided {@code byteWriter}
     * @param b a byte.
     * @param byteWriter a {@link ByteDataWriter} instance
     * @return {@code this}
     */
    default BytePen bytes(byte b, ByteDataWriter byteWriter) {
        Objects.requireNonNull(byteWriter).write(this, b);
        return this;
    }

    /**
     * Writes a {@link byte[]} using the provided {@code byteWriter}
     * @param bytes a byte array
     * @param byteWriter a {@link ByteDataWriter} instance
     * @return {@code this}
     */
    default BytePen bytes(byte[] bytes, ByteDataWriter byteWriter) {
        Objects.requireNonNull(byteWriter);
        for(byte b : bytes) {
            byteWriter.write(this, b);
        }
        return this;
    }

    /**
     * Writes a {@link Stream} using {@link Number#byteValue()}
     * @param numbers a {@link Stream}
     * @return {@code this}
     */
    default BytePen bytes(Stream<? extends Number> numbers) {
        numbers.forEach(number->this.bytes(number.byteValue()));
        return this;
    }

    /**
     * Writes a {@link Stream} using {@link Number#byteValue()}
     * @param numbers a {@link Stream}
     * @return {@code this}
     */
    default BytePen bytes(Stream<? extends Number> numbers, ByteDataWriter byteWriter) {
        numbers.forEach(number -> byteWriter.write(this, number.byteValue()));
        return this;
    }

}
