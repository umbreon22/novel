package novel.api.types.write.pens;

import novel.api.types.write.AutoWriteable;
import novel.api.types.write.writers.BoolDataWriter;
import novel.api.types.write.writers.ByteDataWriter;
import novel.api.types.write.writers.CharDataWriter;
import novel.api.types.write.writers.DoubleDataWriter;
import novel.api.types.write.writers.FloatDataWriter;
import novel.api.types.write.writers.IntDataWriter;
import novel.api.types.write.writers.LongDataWriter;
import novel.api.types.write.writers.ObjectDataWriter;
import novel.api.types.write.writers.ShortDataWriter;
import novel.api.types.write.writers.StringDataWriter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Novel's data output interface. You write on paper with a pen!
 */
public interface DataPen extends
    BytePen,
    BoolPen,
    CharPen,
    DoublePen,
    FloatPen,
    IntPen,
    LongPen,
    ShortPen,
    StringPen {

    /**
     * Writes an object via it's self defined {@link AutoWriteable#write(DataPen)} method.
     * @param object a {@link AutoWriteable} object
     * @return {@code this}
     */
    default DataPen objects(AutoWriteable object) {
        object.write(this);
        return this;
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@code this}
     */
    default DataPen objects(AutoWriteable... objects) {
        for(AutoWriteable w : objects) objects(w);
        return this;
    }

    /**
     * Writes object arrays.
     * @param objects a varargs array of object arrays.
     * @return {@code this}
     */
    default DataPen objects(AutoWriteable[]... objects) {
        for(AutoWriteable[] o : objects) objects(o);
        return this;
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@code this}
     */
    default DataPen objects(Supplier<AutoWriteable> objects) {
        return objects(objects.get());
    }

    /**
     * Writes an {@link Iterable} of {@link AutoWriteable}s.
     * @param objects a {@link Iterable}
     * @return {@code this}
     */
    default DataPen objects(Iterable<? extends AutoWriteable> objects) {
        for(AutoWriteable o : objects) objects(o);
        return this;
    }

    /**
     * Writes an {@link Stream} of {@link AutoWriteable}s.
     * @param objects a {@link Iterable}
     * @return {@code this}
     */
    default DataPen objects(Stream<? extends AutoWriteable> objects) {
        objects.forEach(this::objects);
        return this;
    }

    /**
     * Writes an object of type {@code T} using the provided {@code writer}
     * @param object an object of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code this}
     */
    default <T> DataPen objects(T object, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer).write(this, object);
        return this;
    }

    /**
     * Writes an object array of type {@code T} using the provided {@code writer}
     * @param objects an array of objects of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code this}
     */
    default <T> DataPen objects(T[] objects, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer);
        for(T object : objects) {
            writer.write(this, object);
        }
        return this;
    }

    /**
     * Writes an {@link Iterable<T>} using the provided {@code writer}
     * @param objects an iterable of objects of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code this}
     */
    default <T> DataPen objects(Iterable<T> objects, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer);
        for(T object : objects) {
            writer.write(this, object);
        }
        return this;
    }

    /**
     * Writes a {@link Stream<T>} using the provided {@code writer}
     * @param objects an iterable of objects of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code this}
     */
    default <T> DataPen objects(Stream<T> objects, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer);
        objects.forEach(object -> writer.write(this, object));
        return this;
    }

    @Override
    DataPen bools(boolean b);

    @Override
    default DataPen bools(boolean... booleans) {
        BoolPen.super.bools(booleans);
        return this;
    }

    @Override
    default DataPen bools(boolean[]... bools) {
        BoolPen.super.bools(bools);
        return this;
    }

    @Override
    default DataPen bools(Supplier<Boolean> booleans) {
        BoolPen.super.bools(booleans);
        return this;
    }

    @Override
    default DataPen bools(Iterable<Boolean> booleans) {
        BoolPen.super.bools(booleans);
        return this;
    }

    @Override
    default DataPen bools(Stream<Boolean> booleans) {
        BoolPen.super.bools(booleans);
        return this;
    }

    @Override
    default DataPen bools(Stream<Boolean> booleans, BoolDataWriter boolWriter) {
        BoolPen.super.bools(booleans, boolWriter);
        return this;
    }

    @Override
    default DataPen bools(boolean b, BoolDataWriter boolWriter) {
        BoolPen.super.bools(b, boolWriter);
        return this;
    }

    @Override
    default DataPen bools(boolean[] booleans, BoolDataWriter boolWriter) {
        BoolPen.super.bools(booleans, boolWriter);
        return this;
    }

    @Override
    DataPen bytes(byte b);

    @Override
    default DataPen bytes(byte... bytes) {
        BytePen.super.bytes(bytes);
        return this;
    }

    @Override
    default DataPen bytes(byte[]... bytes) {
        BytePen.super.bytes(bytes);
        return this;
    }

    @Override
    default DataPen bytes(Supplier<Byte> bytes) {
        BytePen.super.bytes(bytes);
        return this;
    }

    @Override
    default DataPen bytes(Iterable<? extends Number> numbers) {
        BytePen.super.bytes(numbers);
        return this;
    }

    @Override
    default DataPen bytes(Iterable<? extends Number> numbers, ByteDataWriter byteDataWriter) {
        BytePen.super.bytes(numbers, byteDataWriter);
        return this;
    }

    @Override
    default DataPen bytes(Stream<? extends Number> numbers) {
        BytePen.super.bytes(numbers);
        return this;
    }

    @Override
    default DataPen bytes(Stream<? extends Number> numbers, ByteDataWriter byteWriter) {
        BytePen.super.bytes(numbers, byteWriter);
        return this;
    }

    @Override
    default DataPen bytes(byte b, ByteDataWriter byteWriter) {
        BytePen.super.bytes(b, byteWriter);
        return this;
    }

    @Override
    default DataPen bytes(byte[] bytes, ByteDataWriter byteWriter) {
        BytePen.super.bytes(bytes, byteWriter);
        return this;
    }

    @Override
    DataPen chars(char c);

    @Override
    default DataPen chars(char... chars) {
        CharPen.super.chars(chars);
        return this;
    }

    @Override
    default DataPen chars(Supplier<Character> chars) {
        CharPen.super.chars(chars);
        return this;
    }

    @Override
    default DataPen chars(char[]... chars) {
        CharPen.super.chars(chars);
        return this;
    }

    @Override
    default DataPen chars(Iterable<Character> chars, CharDataWriter charWriter) {
        CharPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    default DataPen chars(Stream<Character> chars, CharDataWriter charWriter) {
        CharPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    default DataPen chars(Iterable<Character> chars) {
        CharPen.super.chars(chars);
        return this;
    }

    @Override
    default DataPen chars(Stream<Character> chars) {
        CharPen.super.chars(chars);
        return this;
    }

    @Override
    default DataPen chars(char c, CharDataWriter charWriter) {
        CharPen.super.chars(c, charWriter);
        return this;
    }

    @Override
    default DataPen chars(char[] chars, CharDataWriter charWriter) {
        CharPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    DataPen doubles(double d);

    @Override
    default DataPen doubles(double... doubles) {
        DoublePen.super.doubles(doubles);
        return this;
    }

    @Override
    default DataPen doubles(double[]... doubles) {
        DoublePen.super.doubles(doubles);
        return this;
    }

    @Override
    default DataPen doubles(Supplier<Double> doubles) {
        DoublePen.super.doubles(doubles);
        return this;
    }

    @Override
    default DataPen doubles(Iterable<? extends Number> numbers) {
        DoublePen.super.doubles(numbers);
        return this;
    }

    @Override
    default DataPen doubles(Iterable<? extends Number> numbers, DoubleDataWriter doubleDataWriter) {
        DoublePen.super.doubles(numbers, doubleDataWriter);
        return this;
    }

    @Override
    default DataPen doubles(Stream<? extends Number> numbers) {
        DoublePen.super.doubles(numbers);
        return this;
    }

    @Override
    default DataPen doubles(Stream<? extends Number> numbers, DoubleDataWriter doubleDataWriter) {
        DoublePen.super.doubles(numbers, doubleDataWriter);
        return this;
    }

    @Override
    default DataPen doubles(DoubleStream doubles) {
        DoublePen.super.doubles(doubles);
        return this;
    }

    @Override
    default DataPen doubles(DoubleStream doubles, DoubleDataWriter doubleDataWriter) {
        DoublePen.super.doubles(doubles, doubleDataWriter);
        return this;
    }

    @Override
    default DataPen doubles(double d, DoubleDataWriter doubleWriter) {
        DoublePen.super.doubles(d, doubleWriter);
        return this;
    }

    @Override
    default DoublePen doubles(double[] doubles, DoubleDataWriter doubleWriter) {
        DoublePen.super.doubles(doubles, doubleWriter);
        return this;
    }

    @Override
    DataPen floats(float f);

    @Override
    default DataPen floats(float... floats) {
        FloatPen.super.floats(floats);
        return this;
    }

    @Override
    default DataPen floats(float[]... floats) {
        FloatPen.super.floats(floats);
        return this;
    }

    @Override
    default DataPen floats(Supplier<Float> floats) {
        FloatPen.super.floats(floats);
        return this;
    }

    @Override
    default DataPen floats(Iterable<? extends Number> numbers) {
        FloatPen.super.floats(numbers);
        return this;
    }

    @Override
    default DataPen floats(Iterable<? extends Number> numbers, FloatDataWriter floatDataWriter) {
        FloatPen.super.floats(numbers, floatDataWriter);
        return this;
    }

    @Override
    default DataPen floats(Stream<? extends Number> numbers) {
        FloatPen.super.floats(numbers);
        return this;
    }

    @Override
    default DataPen floats(Stream<? extends Number> numbers, FloatDataWriter floatDataWriter) {
        FloatPen.super.floats(numbers, floatDataWriter);
        return this;
    }

    @Override
    default DataPen floats(DoubleStream doubles, FloatDataWriter floatDataWriter) {
        FloatPen.super.floats(doubles, floatDataWriter);
        return this;
    }

    @Override
    default DataPen floats(DoubleStream doubles) {
        FloatPen.super.floats(doubles);
        return this;
    }

    @Override
    default DataPen floats(float f, FloatDataWriter floatWriter) {
        FloatPen.super.floats(f, floatWriter);
        return this;
    }

    @Override
    default FloatPen floats(float[] floats, FloatDataWriter floatWriter) {
        FloatPen.super.floats(floats, floatWriter);
        return this;
    }

    @Override
    DataPen ints(int i);

    @Override
    default DataPen ints(int... ints) {
        IntPen.super.ints(ints);
        return this;
    }

    @Override
    default DataPen ints(int[]... ints) {
        IntPen.super.ints(ints);
        return this;
    }

    @Override
    default DataPen ints(Supplier<Integer> ints) {
        IntPen.super.ints(ints);
        return this;
    }

    @Override
    default DataPen ints(Iterable<? extends Number> numbers) {
        IntPen.super.ints(numbers);
        return this;
    }

    @Override
    default IntPen ints(Iterable<? extends Number> numbers, IntDataWriter intDataWriter) {
        IntPen.super.ints(numbers, intDataWriter);
        return this;
    }

    @Override
    default DataPen ints(Stream<? extends Number> numbers) {
        IntPen.super.ints(numbers);
        return this;
    }

    @Override
    default IntPen ints(Stream<? extends Number> numbers, IntDataWriter intDataWriter) {
        IntPen.super.ints(numbers, intDataWriter);
        return this;
    }

    @Override
    default DataPen ints(IntStream ints) {
        IntPen.super.ints(ints);
        return this;
    }

    @Override
    default DataPen ints(IntStream ints, IntDataWriter intDataWriter) {
        IntPen.super.ints(ints, intDataWriter);
        return this;
    }

    @Override
    default DataPen ints(int i, IntDataWriter intWriter) {
        IntPen.super.ints(i, intWriter);
        return this;
    }

    @Override
    default IntPen ints(int[] ints, IntDataWriter intWriter) {
        IntPen.super.ints(ints, intWriter);
        return this;
    }

    @Override
    DataPen longs(long l);

    @Override
    default DataPen longs(long... longs) {
        LongPen.super.longs(longs);
        return this;
    }

    @Override
    default DataPen longs(long[]... longs) {
        LongPen.super.longs(longs);
        return this;
    }

    @Override
    default DataPen longs(Supplier<Long> longs) {
        LongPen.super.longs(longs);
        return this;
    }

    @Override
    default DataPen longs(Iterable<Long> longs) {
        LongPen.super.longs(longs);
        return this;
    }

    @Override
    default DataPen longs(Stream<? extends Number> numbers) {
        LongPen.super.longs(numbers);
        return this;
    }
    
    @Override
    default DataPen longs(LongStream longs) {
        LongPen.super.longs(longs);
        return this;
    }

    @Override
    default LongPen longs(long l, LongDataWriter longWriter) {
        LongPen.super.longs(l, longWriter);
        return this;
    }

    @Override
    default LongPen longs(long[] longs, LongDataWriter longWriter) {
        LongPen.super.longs(longs, longWriter);
        return this;
    }

    @Override
    DataPen shorts(short s);

    @Override
    default DataPen shorts(short... shorts) {
        ShortPen.super.shorts(shorts);
        return this;
    }

    @Override
    default DataPen shorts(Supplier<Short> shorts) {
        ShortPen.super.shorts(shorts);
        return this;
    }

    @Override
    default DataPen shorts(short[]... shorts) {
        ShortPen.super.shorts(shorts);
        return this;
    }

    @Override
    default DataPen shorts(Iterable<Short> shorts) {
        ShortPen.super.shorts(shorts);
        return this;
    }

    @Override
    default DataPen shorts(Stream<? extends Number> shorts) {
        ShortPen.super.shorts(shorts);
        return this;
    }

    @Override
    default DataPen shorts(short s, ShortDataWriter shortWriter) {
        ShortPen.super.shorts(s, shortWriter);
        return this;
    }

    @Override
    default DataPen shorts(short[] shorts, ShortDataWriter shortWriter) {
        ShortPen.super.shorts(shorts, shortWriter);
        return this;
    }

    @Override
    DataPen strings(CharSequence str);

    @Override
    default DataPen strings(CharSequence... strings) {
        StringPen.super.strings(strings);
        return this;
    }

    @Override
    default DataPen strings(CharSequence[]... strings) {
        StringPen.super.strings(strings);
        return this;
    }

    @Override
    default DataPen strings(Supplier<CharSequence> string) {
        StringPen.super.strings(string);
        return this;
    }

    @Override
    default DataPen strings(Object toStringMe) {
        StringPen.super.strings(toStringMe);
        return this;
    }

    @Override
    default DataPen strings(Object... toStringMe) {
        StringPen.super.strings(toStringMe);
        return this;
    }

    @Override
    default DataPen strings(Object[]... toStringMe) {
        StringPen.super.strings(toStringMe);
        return this;
    }

    @Override
    default DataPen strings(Iterable<? extends CharSequence> strings) {
        StringPen.super.strings(strings);
        return this;
    }

    @Override
    default DataPen strings(Stream<? extends CharSequence> strings) {
        StringPen.super.strings(strings);
        return this;
    }

    @Override
    default DataPen strings(CharSequence s, StringDataWriter stringWriter) {
        StringPen.super.strings(s, stringWriter);
        return this;
    }

    @Override
    default DataPen strings(CharSequence[] strings, StringDataWriter stringWriter) {
        StringPen.super.strings(strings, stringWriter);
        return this;
    }
}
