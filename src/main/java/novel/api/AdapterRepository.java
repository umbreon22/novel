package novel.api;

import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.TypeToken;
import novel.api.types.write.ObjectDataWriter;

public interface AdapterRepository {

    <T> ObjectDataReader<T> reader(TypeToken<T> token);
    default <T> ObjectDataReader<T> reader(Class<T> clazz) {
        return reader(TypeToken.get(clazz));
    }

    <T> ObjectDataWriter<T> writer(TypeToken<T> token);
    default <T> ObjectDataWriter<T> writer(Class<T> clazz) {
        return writer(TypeToken.get(clazz));
    }

    <T> ObjectDataAdapter<T> adapter(TypeToken<T> token);
    default <T> ObjectDataAdapter<T> adapter(Class<T> clazz) {
        return adapter(TypeToken.get(clazz));
    }


}
