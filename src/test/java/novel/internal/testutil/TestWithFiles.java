package novel.internal.testutil;

import novel.api.streams.NovelFileStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class TestWithFiles {
    @Test
    void testFile() throws IOException {
        File testFile = File.createTempFile("test", "test");
        testFile.deleteOnExit();
        int testing = 123;
        try (var penStream = NovelFileStream.newPen(testFile)) {
            penStream.ints(testing);
        }
        try(var paperStream = NovelFileStream.newPaper(testFile)) {
            var testResult = paperStream.ints();
            Assertions.assertEquals(testing, testResult);
        }
    }
}
