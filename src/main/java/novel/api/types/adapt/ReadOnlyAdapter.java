package novel.api.types.adapt;

import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.TypeToken;
import novel.api.types.write.CannotWrite;

/**
 * This Adapter will FAIL on write operations.
 * @see ObjectDataReader
 */
final class ReadOnlyAdapter<T> implements ObjectDataAdapter<T>, CannotWrite<T> {

    private final ObjectDataReader<T> reader;
    private final TypeToken<T> token;

    ReadOnlyAdapter(TypeToken<T> token, ObjectDataReader<T> reader) {
        this.reader = reader;
        this.token = token;
    }

    @Override
    public T read(DataPaper paper) throws DataPaperReadException {
        return reader.read(paper);
    }

    @Override
    public ObjectDataReader<T> asReader() {
        return reader;
    }

    @Override
    public TypeToken<?> token() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == reader || super.equals(obj);
    }

}
