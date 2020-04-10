package novel.testutil;

import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.ObjectDataAdapter;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class TestWithStreams<T> {

    protected void testGoodWriteRead(T writeMe, ObjectDataAdapter<T> adapter) throws IOException {
        byte[] writtenBytes = withOutputStream(stream->adapter.write(stream, writeMe));
        T readMe = withInputStream(writtenBytes, adapter::read);
        assertGood(writeMe, readMe);
    }

    protected void assertGood(T writeMe, T readMe) {
        Assertions.assertEquals(writeMe, readMe);
    }

    protected byte[] withOutputStream(Consumer<NovelPenStream> onStream) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream(); NovelPenStream oos = new NovelPenStream(baos)) {
            onStream.accept(oos);
            oos.flush();
            return baos.toByteArray();
        }
    }


    protected <OT> OT withInputStreamAlt(byte[] bytes, Function<NovelPaperStream, OT> onStream) throws IOException {
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(bytes))) {
            return onStream.apply(stream);
        }
    }

    protected T withInputStream(byte[] bytes, Function<NovelPaperStream, T> onStream) throws IOException {
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(bytes))) {
            return onStream.apply(stream);
        }
    }
}
