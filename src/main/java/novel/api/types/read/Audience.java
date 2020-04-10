package novel.api.types.read;

import novel.api.types.read.DataPaper;
import novel.api.types.read.Readable;
import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;

import java.lang.reflect.Type;

public interface Audience {

    <T> T read(DataPaper reader, TypeToken<T> token);

    @SuppressWarnings("unchecked")
    default <T> T read(DataPaper reader, Type typeOfData) {
        return read(reader, (TypeToken<T>) TypeToken.get(typeOfData));
    }

    default <T extends Readable> T read(DataPaper reader, Class<T> clazz) {
        return read(reader, TypeToken.get(clazz));
    }

    boolean canRead(TypeToken<?> token);

    //this one feels kinda useless? eh
    default <T extends Tokenable> boolean canRead(T data) {
        return canRead(data.token());
    }

    default boolean canRead(Class<?> clazz) {
        return canRead(TypeToken.get(clazz));
    }

}
