package novel.api.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * todo: move to its own module?
 */
public final class NovelFileStream {

    private NovelFileStream() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Please don't instantiate me :(");
    }

    public static NovelPenStream newPen(File file) throws IOException {
        return new NovelPenStream(new FileOutputStream(file));
    }

    public static NovelPenStream newPen(Path path) throws IOException {
        return newPen(path.toFile());
    }

    public static NovelPaperStream newPaper(File file) throws IOException {
        return new NovelPaperStream(new FileInputStream(file));
    }

    public static NovelPaperStream newPaper(Path path) throws IOException {
        return newPaper(path.toFile());
    }

}
