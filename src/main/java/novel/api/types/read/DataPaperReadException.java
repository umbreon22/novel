package novel.api.types.read;

/**
 * An exception that may be thrown during {@link ObjectDataReader#read(DataPaper)}
 * todo: revisit the need for custom exceptions
 */
public class DataPaperReadException extends IllegalStateException {

    public DataPaperReadException(Exception e) {
        super(e);
    }

    public DataPaperReadException(String s) {
        super(s);
    }

}
