package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;
import java.lang.reflect.Field;

@Deprecated(forRemoval = true)//Needs performance testing against TokenableField classes before removal.
public final class FieldReflectiveObjectReader<T> extends ReflectiveObjectReader<T, Field> {

    public FieldReflectiveObjectReader(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<Field> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collect(Readable.class, source.getRawType());
    }

    @Override
    protected TypeToken<?> tokenFor(Field field) {
        return TypeToken.get(field);
    }

    @Override
    protected void setInstance(Field field, T instance, Object read) throws IllegalAccessException {
        field.set(instance, read);
    }

    @Override
    protected boolean isNullsafe(Field field) {
        return ReflectiveUtil.isNullsafe(field);
    }

}