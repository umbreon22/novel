package novel.api;

import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.Novelable;
import novel.api.types.adapt.NovelableWrapper;
import novel.api.types.token.TypeToken;
import novel.api.types.write.Writeable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import novel.api.types.read.Readable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NovelTest implements Novelable {

    /*private record TestRecord (
        @Folio(3) int a,
        @Folio(2) String b
    ) implements Novelable {}

    @Test
    public void testRecordStreamed() throws IOException {
        Novel novel = Novel.newDefaultInstance();
        var baos = new ByteArrayOutputStream();
        var exampleRecord = new TestRecord(0, "");
        try(var writer = new NovelPenStream(baos)) {
            novel.write(writer, exampleRecord);
        }
        try(var reader = new NovelPaperStream(baos.toByteArray())) {
            var readRecord = novel.read(reader, TestRecord.class);
            Assertions.assertEquals(exampleRecord, readRecord);
        }
    }

    @Test
    void testRecord() {
        Novel novel = Novel.newBuilder().build();
        record DummyRecord(String stringA, String stringB) implements Novelable {}
        var writeMe = new DummyRecord("hello", "goodbye");
        DataPenQueue pen = new DataPenQueue();
        novel.write(pen, writeMe);
        DataPaper paper = pen.paper();
        var readMe = novel.read(paper, DummyRecord.class);
        Assertions.assertEquals(writeMe, readMe);
    }

    @Test
    void testRecordQueued() {
        Novel novel = Novel.newDefaultInstance();
        DataPenQueue pen = new DataPenQueue();
        var record = new TestRecord(0, "");
        novel.write(pen, record);
    }*/

    @Test
    public void testIntClass() throws IOException {
        Novel novel = Novel.newBuilder().build();
        var baos = new ByteArrayOutputStream();
        try(NovelPenStream writer = new NovelPenStream(baos)) {
            novel.write(writer, NovelableWrapper.ints(4));
        }
        int headerLength = 4 + 2;
        Assertions.assertArrayEquals(
            new byte[]{0x0, 0x0, 0x0, 0x4},
            skipArray(baos.toByteArray(), headerLength)
        );
        try(NovelPaperStream reader = new NovelPaperStream(new ByteArrayInputStream(baos.toByteArray()))) {
            NovelableWrapper<Integer> four = novel.read(reader, TypeToken.getParameterizedType(NovelableWrapper.class, Integer.class));//new TypeToken<NovelableWrapper<Integer>>(){}.getType()
            Assertions.assertEquals(4, four.unwrap());
        }
    }

    @Test
    public void testUnknownClass() throws IOException {
        Novel novel = Novel.newBuilder().build();
        var baos = new ByteArrayOutputStream();
        String someString = "unknown";
        try(NovelPenStream writer = new NovelPenStream(baos)) {
            novel.write(writer, NovelableWrapper.strings(someString));
        }
        try(NovelPaperStream reader = new NovelPaperStream(new ByteArrayInputStream(baos.toByteArray()))) {
            NovelableWrapper<String> wrappedString = novel.read(reader, NovelableWrapper.wrappedToken(String.class));
            Assertions.assertEquals(someString, wrappedString.unwrap());
        }
    }

    private static byte[] skipArray(byte[] array, int bytesToSkip) {
        int len = array.length - bytesToSkip;
        if(len < 0 || len > array.length) {
            throw new IllegalArgumentException("Cannot skip " + bytesToSkip + " in array of length " + array.length);
        }
        byte[] ret = new byte[len];
        System.arraycopy(array, bytesToSkip, ret, 0, len);
        return ret;
    }

    @Test
    void testCanWrite() {
        Novel novel = Novel.newDefaultInstance();
        class NotAWriteable {}
        Assertions.assertFalse(novel.canWrite(NotAWriteable.class));
        class IsAWriteable implements Writeable {}
        Assertions.assertTrue(novel.canWrite(IsAWriteable.class));

        novel = Novel.newBuilder().withWriter((a,b)->{}, NotAWriteable.class).build();
        Assertions.assertTrue(novel.canWrite(NotAWriteable.class));
    }

    @Test
    void testCanRead() {
        Novel novel = Novel.newDefaultInstance();
        class NotAReadable {}
        Assertions.assertFalse(novel.canWrite(NotAReadable.class));
        class IsAReadable implements Readable {}
        Assertions.assertTrue(novel.canRead(IsAReadable.class));

        novel = Novel.newBuilder().withReader(a->null, NotAReadable.class).build();
        Assertions.assertTrue(novel.canRead(NotAReadable.class));
    }

}
