package novel.api.streams;

import novel.api.types.adapt.Novelable;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.Readable;
import novel.api.types.write.AutoWriteable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class DataStreamTest {

    @Test
    public void test() throws IOException {
        AutoWriteable seededHentai = new TestNovel();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (NovelPenStream stream = new NovelPenStream(out)) {
            stream.objects(seededHentai);
        }
        try(NovelPaperStream stream = new NovelPaperStream(new ByteArrayInputStream(out.toByteArray()))) {
            Readable downloadedHentai = stream.objects(reader -> new TestNovel(reader.ints(), reader.strings()));
            Assertions.assertEquals(seededHentai, downloadedHentai);
        }
    }

    static class TestNovel implements Novelable, AutoWriteable {
        private final int elligence;
        TestNovel(int elligence) {
            this.elligence = elligence;
        }
        TestNovel() {
            this(69);
        }
        TestNovel(int elligence, String printMe) {
            this.elligence = elligence;
            System.out.println(printMe);
        }
        @Override
        public void write(DataPen<?> writer) {
            writer.ints(elligence).strings("I hope you're having a lovely day, whoever took the time to read this test :)");
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TestNovel
                    && ((TestNovel)obj).elligence == this.elligence;
        }
    }
}
