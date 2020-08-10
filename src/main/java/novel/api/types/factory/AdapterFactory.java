package novel.api.types.factory;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.writers.ObjectDataWriter;
import novel.api.types.token.TypeToken;

public abstract class AdapterFactory implements ReaderFactory, WriterFactory {

    /**
     * Returns an {@link ObjectDataAdapter} for {@code typeToken}, or null if this factory doesn't
     * support {@code typeToken}.
     */
    @SuppressWarnings("unchecked")
    public abstract <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken);


    /**
     * Returns an {@link ObjectDataReader} for {@code typeToken}, or null if this factory doesn't
     * support {@code typeToken}.
     */
    @Override
    public <T> ObjectDataReader<T> createReader(Novel novel, TypeToken<T> typeToken) {
        return create(novel, typeToken).asReader();
    }

    /**
     * Returns an {@link ObjectDataWriter} for {@code typeToken}, or null if this factory doesn't
     * support {@code typeToken}.
     */
    @Override
    public <T> ObjectDataWriter<T> createWriter(Novel novel, TypeToken<T> typeToken) {
        return create(novel, typeToken).asWriter();
    }

}
