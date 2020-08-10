package novel.api.types.adapt;

import novel.api.types.read.CannotRead;
import novel.api.types.token.TypeToken;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.CannotWrite;
import novel.api.types.write.writers.ObjectDataWriter;

/**
 * An adapter can read and write.
 * @see ObjectDataWriter
 * @see ObjectDataReader
 */
public interface ObjectDataAdapter<T> extends ObjectDataWriter<T>, ObjectDataReader<T> {

    static <T> ObjectDataAdapter<T> wrappedAdapter(ObjectDataReader<T> reader, ObjectDataWriter<T> writer) {
        return new WrappedAdapter<>(reader, writer);
    }

    static <T> ObjectDataAdapter<T> tryWrappingAdapter(TypeToken<T> token, ObjectDataReader<T> reader, ObjectDataWriter<T> writer) {
        boolean canRead = reader != null && !(reader instanceof CannotRead);
        boolean canWrite = writer != null && !(writer instanceof CannotWrite);
        if(canRead && canWrite) {
            return wrappedAdapter(reader, writer);
        } else if(canRead) {
            return createReadOnly(token, reader);
        } else if(canWrite) {
            return createWriteOnly(token, writer);
        } else return new CannotAdapt<>(token);
    }

    static <T> ObjectDataAdapter<T> createReadOnly(TypeToken<T> token, ObjectDataReader<T> reader) {
        return new ReadOnlyAdapter<>(token, reader);
    }

    static <T> ObjectDataAdapter<T> createWriteOnly(TypeToken<T> token, ObjectDataWriter<T> writer) {
        return new WriteOnlyAdapter<>(token, writer);
    }

    default ObjectDataWriter<T> asWriter() {
        return this;
    }

    default ObjectDataReader<T> asReader() {
        return this;
    }

    /**
     * Wraps the current adapter around a {@link NullsafeAdapter} if possible.
     * @return a {@link NullsafeAdapter} or {@code this}
     */
    default ObjectDataAdapter<T> nullSafe() {
        if(this instanceof NullsafeAdapter) {
            return this;
        } else return new NullsafeAdapter<>(this);
    }
}
