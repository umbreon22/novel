package novel.api.types.read;

import novel.testutil.DataPaperQueue;
import novel.testutil.DataPenQueue;
import novel.api.types.write.AutoWriteable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

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

    <T> void stringObjectsTest(T... data) {
        AutoWriteable write = pen->pen.strings(data);
        DataPaper paper = newPaper(pen->pen.objects(write));
        Assertions.assertArrayEquals(paper.objects(DataPaper::strings, data.length), data);
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

}
