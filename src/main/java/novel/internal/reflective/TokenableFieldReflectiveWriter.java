package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;
import novel.api.types.write.Writeable;

public final class TokenableFieldReflectiveWriter<T> extends ReflectiveObjectWriter<T, TokenableField> {

    public TokenableFieldReflectiveWriter(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<TokenableField> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collectAndMap(Writeable.class, source.getRawType(), TokenableField::new);
    }

    @Override
    protected Object memberFrom(TokenableField field, T object) throws IllegalAccessException {
        return field.field().get(object);
    }

    @Override
    protected TypeToken<?> tokenFor(TokenableField field) {
        return field.token();
    }

    @Override
    protected boolean isAutoWriteable(TokenableField field) {
        return field.isAutoWriteable();
    }

    @Override
    protected boolean isNullsafe(TokenableField field) {
        return field.isNullsafe();
    }
}
