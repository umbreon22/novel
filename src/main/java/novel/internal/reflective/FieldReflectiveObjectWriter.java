package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;
import novel.api.types.write.Writeable;

import java.lang.reflect.Field;

@Deprecated(forRemoval = true)//Needs performance testing against TokenableField classes before removal.
public final class FieldReflectiveObjectWriter<T> extends ReflectiveObjectWriter<T, Field> {

    public FieldReflectiveObjectWriter(Novel novel, TypeToken<T> source) {
        super(novel, source);
    }

    @Override
    protected Iterable<Field> supplyFields(Novel novel, TypeToken<T> source) {
        return novel.fieldCollector().collect(Writeable.class, source.getRawType());
    }

    @Override
    protected Object memberFrom(Field field, Object object) throws IllegalAccessException {
        return field.get(object);
    }

    @Override
    protected TypeToken<?> tokenFor(Field field) {
        return TypeToken.get(field);
    }

    @Override
    protected boolean isAutoWriteable(Field field) {
        return ReflectiveUtil.isAutoWriteable(field.getType());
    }

    @Override
    protected boolean isNullsafe(Field field) {
        return ReflectiveUtil.isNullsafe(field);
    }
}
