package novel.api.types.write;

import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;
import novel.api.types.write.pens.DataPen;

public interface Author {

    <T> void write(DataPen<?> writer, T data, TypeToken<T> token);

    <T extends Writeable> void write(DataPen<?> writer, T[] data, TypeToken<T[]> token);

    @SuppressWarnings("unchecked")
    default <T extends Writeable> void write(DataPen<?> writer, T data) {
        write(writer, data, (TypeToken<T>) data.token());
    }

    @SuppressWarnings("unchecked")
    default <T extends Writeable> void write(DataPen<?> writer, T[] data) {
        write(writer, data, (TypeToken<T[]>) TypeToken.get(data.getClass()));
    }

    boolean canWrite(TypeToken<?> token);

    default <T extends Tokenable> boolean canWrite(T data) {
        return canWrite(data.token());
    }

    default boolean canWrite(Class<?> clazz) {
        return canWrite(TypeToken.get(clazz));
    }
}
