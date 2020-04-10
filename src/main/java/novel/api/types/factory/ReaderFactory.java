package novel.api.types.factory;

import novel.api.Novel;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.TypeToken;

public interface ReaderFactory {
    /**
     * Returns an {@link ObjectDataReader} for {@code typeToken}, or null if this factory doesn't
     * support {@code typeToken}.
     */
    <T> ObjectDataReader<T> createReader(Novel novel, TypeToken<T> typeToken);
}
