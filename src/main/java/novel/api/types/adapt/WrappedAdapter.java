package novel.api.types.adapt;

import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.writers.ObjectDataWriter;

import java.util.Objects;

/**
 * This class wraps a {@link ObjectDataReader<T>} and a {@link ObjectDataWriter<T>}
 * together as a {@link ObjectDataAdapter<T>}.
 * @param <T> an object's type
 */
final class WrappedAdapter<T> implements ObjectDataAdapter<T> {

    protected final ObjectDataReader<T> reader;
    protected final ObjectDataWriter<T> writer;

    WrappedAdapter(ObjectDataReader<T> reader, ObjectDataWriter<T> writer) {
        this.reader = Objects.requireNonNull(reader, "Null reader in WrappedAdapter. Consider a WriteOnlyAdapter.");
        this.writer = Objects.requireNonNull(writer, "Null writer in WrappedAdapter. Consider a ReadOnlyAdapter.");
    }

    @Override
    public ObjectDataWriter<T> asWriter() {
        return writer;
    }

    @Override
    public ObjectDataReader<T> asReader() {
        return reader;
    }

    @Override
    public T read(DataPaper paper) {
        return reader.read(paper);
    }

    @Override
    public void write(DataPen pen, T object) {
        writer.write(pen, object);
    }

}
