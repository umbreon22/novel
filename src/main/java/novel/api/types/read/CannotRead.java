package novel.api.types.read;

import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;

public interface CannotRead<T> extends ObjectDataReader<T>, Tokenable {
    @Override
    default T read(DataPaper paper) throws DataPaperReadException {
        throw new UnsupportedOperationException("Cannot read " + token());
    }
    TypeToken<?> token();
}
