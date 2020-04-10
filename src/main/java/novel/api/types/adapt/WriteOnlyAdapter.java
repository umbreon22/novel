package novel.api.types.adapt;

import novel.api.types.read.CannotRead;
import novel.api.types.token.TypeToken;
import novel.api.types.write.DataPen;
import novel.api.types.write.ObjectDataWriter;
/**
 * This Adapter will <b>FAIL</b> on read operations.
 * @see ObjectDataWriter
 */
final class WriteOnlyAdapter<T> implements ObjectDataAdapter<T>, CannotRead<T> {

    private final TypeToken<T> token;
    private final ObjectDataWriter<T> writer;

    WriteOnlyAdapter(TypeToken<T> token, ObjectDataWriter<T> writer) {
        this.token = token;
        this.writer = writer;
    }

    @Override
    public void write(DataPen<?> pen, T object) {
        writer.write(pen, object);
    }

    @Override
    public ObjectDataWriter<T> asWriter() {
        return writer;
    }

    @Override
    public TypeToken<?> token() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == writer || super.equals(obj);
    }

}
