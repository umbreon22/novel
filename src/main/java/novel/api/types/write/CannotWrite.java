package novel.api.types.write;

import novel.api.types.token.TypeToken;

public interface CannotWrite<T> extends ObjectDataWriter<T> {

    @Override
    default void write(DataPen<?> pen, T object) {
        throw new UnsupportedOperationException("Cannot write " + token());
    }

    TypeToken<?> token();

}
