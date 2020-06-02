package novel.internal.reflective.handles;

import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;
import novel.internal.reflective.ReflectiveUtil;

import java.lang.reflect.Field;

public final class TokenableHandle implements ReflectiveHandle, Tokenable {

    private final ReflectiveHandle handle;
    private final TypeToken<?> token;
    private final boolean isAutoWriteable;
    private final boolean isNullsafe;

    public TokenableHandle(Field field) {
        this(field, TypeToken.get(field));
    }

    public TokenableHandle(Field field, TypeToken<?> token) {
        this.token = token;
        this.isAutoWriteable = ReflectiveUtil.isAutoWriteable(token.getRawType());
        this.isNullsafe = ReflectiveUtil.isNullsafe(field);
        this.handle = Handles.acquire(field);
    }

    @Override
    public void set(Object instance, Object memberValue) throws Throwable {
        handle.set(instance, memberValue);
    }

    @Override
    public Object get(Object instance) throws Throwable {
        return handle.get(instance);
    }


    @Override
    public TypeToken<?> token() {
        return token;
    }

    public boolean isNullsafe() {
        return isNullsafe;
    }

    public boolean isAutoWriteable() {
        return isAutoWriteable;
    }

}
