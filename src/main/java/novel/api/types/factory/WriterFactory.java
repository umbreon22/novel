package novel.api.types.factory;

import novel.api.Novel;
import novel.api.types.write.writers.ObjectDataWriter;
import novel.api.types.token.TypeToken;

public interface WriterFactory {
    /**
     * Returns an {@link ObjectDataWriter} for {@code typeToken}, or null if this factory doesn't
     * support {@code typeToken}.
     */
    <T> ObjectDataWriter<T> createWriter(Novel novel, TypeToken<T> typeToken);
}
