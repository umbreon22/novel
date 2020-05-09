package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;

@Deprecated(forRemoval = true)//Needs performance testing against HandledField classes before removal.
public final class TokenableFieldReflectiveReader<T> extends ReflectiveObjectReader<T, TokenableField> {

    public TokenableFieldReflectiveReader(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<TokenableField> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collectAndMap(Readable.class, source.getRawType(), TokenableField::new);
    }

    @Override
    protected TypeToken<?> tokenFor(TokenableField tokenableField) {
        return tokenableField.token();
    }

    @Override
    protected void setInstance(TokenableField tokenableField, T instance, Object read) throws IllegalAccessException {
        tokenableField.field().set(instance, read);
    }

    @Override
    protected boolean isFieldNullsafe(TokenableField tokenableField) {
        return tokenableField.isNullsafe();
    }


}