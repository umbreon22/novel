package novel.api.types.read;
/**
 * Defines how {@link T} can be read by implementing {@link #read(DataPaper)}
 * @param <T> an object's type parameter
 */
public interface ObjectDataReader<T> {
    T read(DataPaper paper) throws DataPaperReadException;
}
