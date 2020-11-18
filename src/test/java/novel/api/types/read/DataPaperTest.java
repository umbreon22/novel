package novel.api.types.read;

import novel.api.types.adapt.Novelable;
import novel.api.types.write.pens.DataPen;
import novel.internal.testutil.DataPaperQueue;
import novel.internal.testutil.DataPenQueue;
import novel.api.types.write.AutoWriteable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class DataPaperTest {

    DataPaperQueue newPaper(Function<DataPenQueue, DataPenQueue> pen) {
        return pen.apply(new DataPenQueue()).paper();
    }

    @Test
    void intsTest() {
        int[] data = {1, 2, 3};
        DataPaper paper = newPaper(pen->pen.ints(data));
        Assertions.assertArrayEquals(paper.ints(data.length), data);
    }

    @Test
    void shortsTest() {
        short[] data = {1, 2, 3};
        DataPaper paper = newPaper(pen->pen.shorts(data));
        Assertions.assertArrayEquals(paper.shorts(data.length), data);
    }

    @Test
    void longsTest() {
        long[] data = {1, 2, 3};
        DataPaper paper = newPaper(pen->pen.longs(data));
        Assertions.assertArrayEquals(paper.longs(data.length), data);
    }

    @Test
    void charsTest() {
        char[] data = {'a', 'b', 'c'};
        DataPaper paper = newPaper(pen->pen.chars(data));
        Assertions.assertArrayEquals(paper.chars(data.length), data);
    }

    @Test
    void bytesTest() {
        byte[] data = {1, 2, 3};
        DataPaper paper = newPaper(pen->pen.bytes(data));
        Assertions.assertArrayEquals(paper.bytes(data.length), data);
    }

    @Test
    void doublesTest() {
        double[] data = {1d, 2d, 3d};
        DataPaper paper = newPaper(pen->pen.doubles(data));
        Assertions.assertArrayEquals(paper.doubles(data.length), data);
    }

    @Test
    void floatsTest() {
        float[] data = {1f, 2f, 3f};
        DataPaper paper = newPaper(pen->pen.floats(data));
        Assertions.assertArrayEquals(paper.floats(data.length), data);
    }

    @Test
    void boolsTest() {
        boolean[] data = {true, false};
        DataPaper paper = newPaper(pen->pen.bools(data));
        Assertions.assertArrayEquals(paper.bools(data.length), data);
    }

    @Test
    void stringsTest() {
        String[] data = {"1", "2", "3"};
        DataPaper paper = newPaper(pen->pen.strings(data));
        Assertions.assertArrayEquals(paper.strings(data.length), data);
    }
    @Test
    void typedObjectsTest() {
        stringObjectsTest("hello", "goodbye");
    }

    @SafeVarargs
    final <T> void stringObjectsTest(T... data) {
        AutoWriteable write = pen->pen.strings((Object[])data);
        DataPaper paper = newPaper(pen->pen.objects(write));
        String[] read = paper.objects(DataPaper::strings, data.length, String[]::new);
        Assertions.assertArrayEquals(read, data);
    }

    @Test
    void variedObjectsTest() {
        Object[] data = new Object[]{1, '2', 3L};
        AutoWriteable[] writers = {pen->pen.ints((int)data[0]), pen->pen.chars((char)data[1]), pen->pen.longs((long) data[2])};
        ObjectDataReader<?>[] readers = {DataPaper::ints, DataPaper::chars, DataPaper::longs};
        DataPaper paper = newPaper(pen->pen.objects(writers));
        Object[] read = new Object[readers.length];
        for(int i = 0; i < read.length; i++) {
            read[i] = paper.objects(readers[i]);
        }
        Assertions.assertArrayEquals(read, data);
    }

    static class TestObject implements Novelable, AutoWriteable {
        String str;
        TestObject(String str) {
            this.str = str;
        }

        @Override
        public void write(DataPen output) {
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
        DataPaper paper = newPaper(pen->pen.objects(zygonIsCool));
        TestObject[] data = paper.objects(reader, zygonIsCool.length, TestObject[]::new);
        Assertions.assertArrayEquals(zygonIsCool, data);

        paper = newPaper(pen->pen.strings((Object[])zygonIsCool));
        TestObject[] data2 = paper.objects(reader, zygonIsCool.length, TestObject.class);
        Assertions.assertArrayEquals(zygonIsCool, data2);
    }

}
