package novel.testutil;

import novel.api.streams.NovelPenStream;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Consumer;

@Disabled()
public abstract class SimpleGoodBadTest<T> extends TestWithStreams<T> {

    private final ObjectDataAdapter<T> adapter;
    private final T goodWriteMe;
    private final Consumer<NovelPenStream> badWrite;

    public SimpleGoodBadTest(ObjectDataAdapter<T> adapter, T goodWrite, Consumer<NovelPenStream> badWrite) {
        this.adapter = adapter;
        this.goodWriteMe = goodWrite;
        this.badWrite = badWrite;
    }

    @Test()
    void testGood() throws IOException {
        testGoodWriteRead(goodWriteMe, adapter);
    }

    @Test()
    void testBad() throws IOException {
        Assertions.assertThrows(DataPaperReadException.class, ()->withInputStream(withOutputStream(badWrite), adapter::read));
    }
}
