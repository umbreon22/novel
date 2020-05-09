package novel.internal.reflective;

import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;

import java.lang.reflect.Field;

class TokenableField implements ReflectiveHandle, Tokenable {

    private final Field field;
    private final TypeToken<?> token;
    private final boolean isAutoWriteable;
    private final boolean isNullsafe;

    public TokenableField(Field field, TypeToken<?> token) {
        this.field = field;
        this.token = token;
        this.isAutoWriteable = ReflectiveUtil.isAutoWriteable(token.getRawType());;
        this.isNullsafe = ReflectiveUtil.isNullsafe(field);
    }

    public TokenableField(Field field) {
        this(field, TypeToken.get(field));
    }

    @Override
    public TypeToken<?> token() {
        return token;
    }

    Field field() {
        return field;
    }

    public boolean isNullsafe() {
        return isNullsafe;
    }

    public boolean isAutoWriteable() {
        return isAutoWriteable;
    }

    @Override
    public void set(Object instance, Object memberValue) throws IllegalAccessException {
        field.set(instance, memberValue);
    }

    @Override
    public Object get(Object instance) throws IllegalAccessException {
        return field.get(instance);
    }
}
