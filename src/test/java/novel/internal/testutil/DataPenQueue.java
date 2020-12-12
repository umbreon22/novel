package novel.internal.testutil;

import novel.api.types.write.AutoWriteable;
import novel.api.types.write.pens.DataPen;
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

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class DataPenQueue implements DataPen {

    private final Queue<Object> queue = new LinkedList<>();

    public Queue<Object> queue() {
        return queue;
    }

    public DataPaperQueue paper() {
        return new DataPaperQueue(queue);
    }

    @Override
    public DataPenQueue objects(AutoWriteable object) {
        DataPen.super.objects(object);
        return this;
    }

    @Override
    public DataPenQueue objects(AutoWriteable... objects) {
        DataPen.super.objects(objects);
        return this;
    }

    @Override
    public DataPenQueue objects(AutoWriteable[]... objects) {
        DataPen.super.objects(objects);
        return this;
    }

    @Override
    public DataPenQueue objects(Supplier<AutoWriteable> objects) {
        DataPen.super.objects(objects);
        return this;
    }

    @Override
    public DataPenQueue objects(Iterable<? extends AutoWriteable> objects) {
        DataPen.super.objects(objects);
        return this;
    }

    @Override
    public DataPenQueue objects(Stream<? extends AutoWriteable> objects) {
        DataPen.super.objects(objects);
        return this;
    }

    @Override
    public <T> DataPenQueue objects(T object, ObjectDataWriter<T> writer) {
        DataPen.super.objects(object, writer);
        return this;
    }

    @Override
    public <T> DataPenQueue objects(T[] objects, ObjectDataWriter<T> writer) {
        DataPen.super.objects(objects, writer);
        return this;
    }

    @Override
    public <T> DataPenQueue objects(Iterable<T> objects, ObjectDataWriter<T> writer) {
        DataPen.super.objects(objects, writer);
        return this;
    }

    @Override
    public <T> DataPenQueue objects(Stream<T> objects, ObjectDataWriter<T> writer) {
        DataPen.super.objects(objects, writer);
        return this;
    }

    @Override
    public DataPenQueue bools(boolean b) {
        queue.add(b);
        return this;
    }

    @Override
    public DataPenQueue bools(boolean... booleans) {
        DataPen.super.bools(booleans);
        return this;
    }

    @Override
    public DataPenQueue bools(boolean[]... bools) {
        DataPen.super.bools(bools);
        return this;
    }

    @Override
    public DataPenQueue bools(Supplier<Boolean> booleans) {
        DataPen.super.bools(booleans);
        return this;
    }

    @Override
    public DataPenQueue bools(Iterable<Boolean> booleans) {
        DataPen.super.bools(booleans);
        return this;
    }

    @Override
    public DataPenQueue bools(Stream<Boolean> booleans) {
        DataPen.super.bools(booleans);
        return this;
    }

    @Override
    public DataPenQueue bools(Stream<Boolean> booleans, BoolDataWriter boolWriter) {
        DataPen.super.bools(booleans, boolWriter);
        return this;
    }

    @Override
    public DataPenQueue bools(boolean b, BoolDataWriter boolWriter) {
        DataPen.super.bools(b, boolWriter);
        return this;
    }

    @Override
    public DataPenQueue bools(boolean[] booleans, BoolDataWriter boolWriter) {
        DataPen.super.bools(booleans, boolWriter);
        return this;
    }

    @Override
    public DataPenQueue ints(int i) {
        queue.add(i);
        return this;
    }

    @Override
    public DataPenQueue ints(int... ints) {
        DataPen.super.ints(ints);
        return this;
    }

    @Override
    public DataPenQueue ints(int[]... ints) {
        DataPen.super.ints(ints);
        return this;
    }

    @Override
    public DataPenQueue ints(Supplier<Integer> ints) {
        DataPen.super.ints(ints);
        return this;
    }

    @Override
    public DataPenQueue ints(Iterable<Integer> ints) {
        DataPen.super.ints(ints);
        return this;
    }

    @Override
    public DataPenQueue ints(Stream<? extends Number> numbers) {
        DataPen.super.ints(numbers);
        return this;
    }

    @Override
    public DataPenQueue ints(IntStream ints) {
        DataPen.super.ints(ints);
        return this;
    }

    @Override
    public DataPenQueue ints(int i, IntDataWriter intWriter) {
        DataPen.super.ints(i, intWriter);
        return this;
    }

    @Override
    public DataPenQueue ints(int[] ints, IntDataWriter intWriter) {
        DataPen.super.ints(ints, intWriter);
        return this;
    }

    @Override
    public DataPenQueue longs(long l) {
        queue.add(l);
        return this;
    }

    @Override
    public DataPenQueue longs(long... longs) {
        DataPen.super.longs(longs);
        return this;
    }

    @Override
    public DataPenQueue longs(long[]... longs) {
        DataPen.super.longs(longs);
        return this;
    }

    @Override
    public DataPenQueue longs(Supplier<Long> longs) {
        DataPen.super.longs(longs);
        return this;
    }

    @Override
    public DataPenQueue longs(Iterable<Long> longs) {
        DataPen.super.longs(longs);
        return this;
    }

    @Override
    public DataPenQueue longs(Stream<? extends Number> nums) {
        DataPen.super.longs(nums);
        return this;
    }

    @Override
    public DataPenQueue longs(LongStream longs) {
        DataPen.super.longs(longs);
        return this;
    }

    @Override
    public DataPenQueue longs(long l, LongDataWriter longWriter) {
        DataPen.super.longs(l, longWriter);
        return this;
    }

    @Override
    public DataPenQueue longs(long[] longs, LongDataWriter longWriter) {
        DataPen.super.longs(longs, longWriter);
        return this;
    }

    @Override
    public DataPenQueue shorts(short s) {
        queue.add(s);
        return this;
    }

    @Override
    public DataPenQueue shorts(short... shorts) {
        DataPen.super.shorts(shorts);
        return this;
    }

    @Override
    public DataPenQueue shorts(Supplier<Short> shorts) {
        DataPen.super.shorts(shorts);
        return this;
    }

    @Override
    public DataPenQueue shorts(short[]... shorts) {
        DataPen.super.shorts(shorts);
        return this;
    }

    @Override
    public DataPenQueue shorts(Iterable<Short> shorts) {
        DataPen.super.shorts(shorts);
        return this;
    }

    @Override
    public DataPenQueue shorts(Stream<? extends Number> numbers) {
        DataPen.super.shorts(numbers);
        return this;
    }

    @Override
    public DataPenQueue shorts(short s, ShortDataWriter shortWriter) {
        DataPen.super.shorts(s, shortWriter);
        return this;
    }

    @Override
    public DataPenQueue shorts(short[] shorts, ShortDataWriter shortWriter) {
        DataPen.super.shorts(shorts, shortWriter);
        return this;
    }

    @Override
    public DataPenQueue floats(float f) {
        queue.add(f);
        return this;
    }

    @Override
    public DataPenQueue floats(float... floats) {
        DataPen.super.floats(floats);
        return this;
    }

    @Override
    public DataPenQueue floats(float[]... floats) {
        DataPen.super.floats(floats);
        return this;
    }

    @Override
    public DataPenQueue floats(Supplier<Float> floats) {
        DataPen.super.floats(floats);
        return this;
    }

    @Override
    public DataPenQueue floats(Iterable<Float> floats) {
        DataPen.super.floats(floats);
        return this;
    }

    @Override
    public DataPenQueue floats(Stream<? extends Number> nums) {
        DataPen.super.floats(nums);
        return this;
    }

    @Override
    public DataPenQueue floats(DoubleStream doubles) {
        DataPen.super.floats(doubles);
        return this;
    }

    @Override
    public DataPenQueue floats(float f, FloatDataWriter floatWriter) {
        DataPen.super.floats(f, floatWriter);
        return this;
    }

    @Override
    public DataPenQueue floats(float[] floats, FloatDataWriter floatWriter) {
        DataPen.super.floats(floats, floatWriter);
        return this;
    }

    @Override
    public DataPenQueue doubles(double d) {
        queue.add(d);
        return this;
    }

    @Override
    public DataPenQueue doubles(double... doubles) {
        DataPen.super.doubles(doubles);
        return this;
    }

    @Override
    public DataPenQueue doubles(double[]... doubles) {
        DataPen.super.doubles(doubles);
        return this;
    }

    @Override
    public DataPenQueue doubles(Supplier<Double> doubles) {
        DataPen.super.doubles(doubles);
        return this;
    }

    @Override
    public DataPenQueue doubles(Iterable<Double> doubles) {
        DataPen.super.doubles(doubles);
        return this;
    }

    @Override
    public DataPenQueue doubles(Stream<? extends Number> nums) {
        DataPen.super.doubles(nums);
        return this;
    }

    @Override
    public DataPenQueue doubles(DoubleStream doubles) {
        DataPen.super.doubles(doubles);
        return this;
    }

    @Override
    public DataPenQueue doubles(double d, DoubleDataWriter doubleWriter) {
        DataPen.super.doubles(d, doubleWriter);
        return this;
    }

    @Override
    public DataPenQueue doubles(double[] doubles, DoubleDataWriter doubleWriter) {
        DataPen.super.doubles(doubles, doubleWriter);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence str) {
        queue.add(str);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence... strings) {
        DataPen.super.strings(strings);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence[]... strings) {
        DataPen.super.strings(strings);
        return this;
    }

    @Override
    public DataPenQueue strings(Supplier<CharSequence> string) {
        DataPen.super.strings(string);
        return this;
    }

    @Override
    public DataPenQueue strings(Object toStringMe) {
        DataPen.super.strings(toStringMe);
        return this;
    }

    @Override
    public DataPenQueue strings(Object... toStringMe) {
        DataPen.super.strings(toStringMe);
        return this;
    }

    @Override
    public DataPenQueue strings(Object[]... toStringMe) {
        DataPen.super.strings(toStringMe);
        return this;
    }

    @Override
    public DataPenQueue strings(Iterable<? extends CharSequence> strings) {
        DataPen.super.strings(strings);
        return this;
    }

    @Override
    public DataPenQueue strings(Stream<? extends CharSequence> strings) {
        DataPen.super.strings(strings);
        return this;
    }

    @Override
    public DataPenQueue chars(Iterable<Character> chars, CharDataWriter charWriter) {
        DataPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    public DataPenQueue chars(Stream<Character> chars, CharDataWriter charWriter) {
        DataPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence s, StringDataWriter stringWriter) {
        DataPen.super.strings(s, stringWriter);
        return this;
    }

    @Override
    public DataPenQueue strings(CharSequence[] strings, StringDataWriter stringWriter) {
        DataPen.super.strings(strings, stringWriter);
        return this;
    }

    @Override
    public DataPenQueue chars(char c) {
        queue.add(c);
        return this;
    }

    @Override
    public DataPenQueue chars(char... chars) {
        DataPen.super.chars(chars);
        return this;
    }

    @Override
    public DataPenQueue chars(Supplier<Character> chars) {
        DataPen.super.chars(chars);
        return this;
    }

    @Override
    public DataPenQueue chars(char[]... chars) {
        DataPen.super.chars(chars);
        return this;
    }

    @Override
    public DataPenQueue chars(Iterable<Character> chars) {
        DataPen.super.chars(chars);
        return this;
    }

    @Override
    public DataPenQueue chars(Stream<Character> chars) {
        DataPen.super.chars(chars);
        return this;
    }

    @Override
    public DataPenQueue chars(char c, CharDataWriter charWriter) {
        DataPen.super.chars(c, charWriter);
        return this;
    }

    @Override
    public DataPenQueue chars(char[] chars, CharDataWriter charWriter) {
        DataPen.super.chars(chars, charWriter);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte b) {
        queue.add(b);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte... bytes) {
        DataPen.super.bytes(bytes);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte[]... bytes) {
        DataPen.super.bytes(bytes);
        return this;
    }

    @Override
    public DataPenQueue bytes(Supplier<Byte> bytes) {
        DataPen.super.bytes(bytes);
        return this;
    }

    @Override
    public DataPenQueue bytes(Iterable<Byte> bytes) {
        DataPen.super.bytes(bytes);
        return this;
    }

    @Override
    public DataPenQueue bytes(Iterable<Byte> bytes, ByteDataWriter byteDataWriter) {
        DataPen.super.bytes(bytes, byteDataWriter);
        return this;
    }

    @Override
    public DataPenQueue bytes(Stream<? extends Number> numbers) {
        DataPen.super.bytes(numbers);
        return this;
    }

    @Override
    public DataPenQueue bytes(Stream<? extends Number> numbers, ByteDataWriter byteWriter) {
        DataPen.super.bytes(numbers, byteWriter);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte b, ByteDataWriter byteWriter) {
        DataPen.super.bytes(b, byteWriter);
        return this;
    }

    @Override
    public DataPenQueue bytes(byte[] bytes, ByteDataWriter byteWriter) {
        DataPen.super.bytes(bytes, byteWriter);
        return this;
    }

}
