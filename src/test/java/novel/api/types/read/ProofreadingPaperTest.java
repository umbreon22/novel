package novel.api.types.read;

import novel.api.types.adapt.Novelable;
import novel.api.types.read.validators.*;
import novel.api.types.write.AutoWriteable;
import novel.api.types.write.pens.DataPen;
import novel.internal.testutil.DataPenQueue;
import novel.internal.testutil.ProofreadingPaperQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class ProofreadingPaperTest {

    ProofreadingPaperQueue newPaper(Function<DataPenQueue, DataPenQueue> pen) {
        return new ProofreadingPaperQueue(pen.apply(new DataPenQueue()).queue());
    }

    private final IntValidator positiveIntValidator = i -> i > 0;
    private final LongValidator positiveLongValidator = i -> i > 0;
    private final DoubleValidator positiveDoubleValidator = i -> i > 0;

    @Test
    void intsTest() {
        int[] data = {1, 2, 3};
        var paper = newPaper(pen->pen.ints(data));
        Assertions.assertArrayEquals(paper.ints(data.length, positiveIntValidator), data);
        Assertions.assertEquals(1, newPaper(pen->pen.ints(1)).ints(positiveIntValidator));
    }

    @Test
    void shortsTest() {
        short[] data = {1, 2, 3};
        var paper = newPaper(pen->pen.shorts(data));
        ShortValidator positiveShortValidator = positiveIntValidator::test;
        Assertions.assertArrayEquals(paper.shorts(data.length, positiveShortValidator), data);
        Assertions.assertEquals(1, newPaper(pen->pen.shorts((short)1)).shorts(positiveShortValidator));
    }

    @Test
    void longsTest() {
        long[] data = {1, 2, 3};
        var paper = newPaper(pen->pen.longs(data));
        Assertions.assertArrayEquals(paper.longs(data.length, positiveLongValidator), data);
        Assertions.assertEquals(1, newPaper(pen->pen.longs(1)).longs(positiveLongValidator));
    }

    @Test
    void charsTest() {
        char[] data = {'a', 'b', 'c'};
        var paper = newPaper(pen->pen.chars(data));
        CharValidator positiveValidator = positiveIntValidator::test;
        Assertions.assertArrayEquals(paper.chars(data.length, positiveValidator), data);
        Assertions.assertEquals('a', newPaper(pen->pen.chars('a')).chars(positiveValidator));
    }

    @Test
    void bytesTest() {
        byte[] data = {1, 2, 3};
        var paper = newPaper(pen->pen.bytes(data));
        ByteValidator positiveValidator = positiveIntValidator::test;
        Assertions.assertArrayEquals(paper.bytes(data.length,positiveValidator), data);
        Assertions.assertEquals(1, newPaper(pen->pen.bytes((byte)1)).bytes(positiveValidator));
    }

    @Test
    void doublesTest() {
        double[] data = {1d, 2d, 3d};
        var paper = newPaper(pen->pen.doubles(data));
        Assertions.assertArrayEquals(paper.doubles(data.length, positiveDoubleValidator), data);
        Assertions.assertEquals(1d , newPaper(pen->pen.doubles(1d)).doubles(positiveDoubleValidator));
    }

    @Test
    void floatsTest() {
        float[] data = {1f, 2f, 3f};
        var paper = newPaper(pen->pen.floats(data));
        FloatValidator positiveValidator = positiveDoubleValidator::test;
        Assertions.assertArrayEquals(paper.floats(data.length, positiveValidator), data);
        Assertions.assertEquals(1f , newPaper(pen->pen.floats(1f)).floats(positiveValidator));
    }

    @Test
    void boolsTest() {
        boolean[] data = {true, false};
        var paper = newPaper(pen->pen.bools(data));
        Assertions.assertArrayEquals(paper.bools(data.length, Objects::nonNull), data);
        Assertions.assertTrue(newPaper(pen -> pen.bools(true)).bools(Objects::nonNull));
    }

    @Test
    void stringsTest() {
        String[] data = {"1", "2", "3"};
        var paper = newPaper(pen->pen.strings(data));
        Assertions.assertArrayEquals(paper.strings(data.length, Objects::nonNull), data);
    }
    @Test
    void typedObjectsTest() {
        stringObjectsTest("hello", "goodbye");
    }

    @SafeVarargs
    final <T> void stringObjectsTest(T... data) {
        AutoWriteable write = pen->pen.strings((Object[]) data);
        var paper = newPaper(pen->pen.objects(write));
        TypeValidator<String> validator = Objects::nonNull;
        Assertions.assertArrayEquals(paper.objects(DataPaper::strings, data.length, String.class, validator), data);
        Assertions.assertEquals(Arrays.toString(data), newPaper(pen -> pen.strings(Arrays.toString(data))).strings(validator));
    }

    @Test
    void variedObjectsTest() {
        Object[] data = new Object[]{1, '2', 3L};
        AutoWriteable[] writers = {pen->pen.ints((int)data[0]), pen->pen.chars((char)data[1]), pen->pen.longs((long) data[2])};
        ObjectDataReader<?>[] readers = {DataPaper::ints, DataPaper::chars, DataPaper::longs};
        var paper = newPaper(pen->pen.objects(writers));
        Object[] read = new Object[readers.length];
        for(int i = 0; i < read.length; i++) {
            read[i] = paper.objects(readers[i], Objects::nonNull);
        }
        Assertions.assertArrayEquals(read, data);
    }

    static class TestObject implements Novelable, AutoWriteable {
        String str;
        TestObject(String str) {
            this.str = str;
        }

        @Override
        public void write(DataPen<?> output) {
            output.strings(str);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return Objects.equals(str, that.str);
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @Test
    void zygonsTest() {
        ObjectDataReader<TestObject> reader = paper -> new TestObject(paper.strings());
        TestObject[] zygonIsCool = Stream.of("zygon", "is", "cool").map(TestObject::new).toArray(TestObject[]::new);
        ProofreadingPaper paper = newPaper(pen->pen.objects(zygonIsCool));
        TestObject[] data = paper.objects(reader, zygonIsCool.length, TestObject.class, Objects::nonNull);
        Assertions.assertArrayEquals(zygonIsCool, data);
        paper = newPaper(pen->pen.strings((Object[])zygonIsCool));
        TestObject[] data2 = paper.objects(reader, zygonIsCool.length, TestObject[]::new, Objects::nonNull);
        Assertions.assertArrayEquals(zygonIsCool, data2);
    }

}
