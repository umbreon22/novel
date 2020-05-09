package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;

public final class TokenableHandleReflectiveReader<T> extends ReflectiveObjectReader<T, TokenableHandle> {

    public TokenableHandleReflectiveReader(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<TokenableHandle> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collectAndMap(Readable.class, source.getRawType(), TokenableHandle::new);
    }

    @Override
    protected TypeToken<?> tokenFor(TokenableHandle tokenableField) {
        return tokenableField.token();
    }

    @Override
    protected void setInstance(TokenableHandle handle, T instance, Object read) throws IllegalAccessException {
        handle.set(instance, read);
    }

    @Override
    protected boolean isFieldNullsafe(TokenableHandle handle) {
        return handle.isNullsafe();
    }

}