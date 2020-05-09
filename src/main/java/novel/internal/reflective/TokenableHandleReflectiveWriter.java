package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;
import novel.api.types.write.Writeable;

public final class TokenableHandleReflectiveWriter<T> extends ReflectiveObjectWriter<T, TokenableHandle> {

    public TokenableHandleReflectiveWriter(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<TokenableHandle> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collectAndMap(Writeable.class, source.getRawType(), TokenableHandle::new);
    }

    @Override
    protected Object memberFrom(TokenableHandle field, T object) throws IllegalAccessException {
        return field.get(object);
    }

    @Override
    protected TypeToken<?> tokenFor(TokenableHandle field) {
        return field.token();
    }

    @Override
    protected boolean isAutoWriteable(TokenableHandle field) {
        return field.isAutoWriteable();
    }

    @Override
    protected boolean isFieldNullsafe(TokenableHandle field) {
        return field.isNullsafe();
    }

}
