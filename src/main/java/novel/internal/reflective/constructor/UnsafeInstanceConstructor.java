package novel.internal.reflective.constructor;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.Objects;

public class UnsafeInstanceConstructor<T> implements ReflectiveConstructor<T> {

    private final Constructor<T> constructor;
    //todo support methodhandle

    public UnsafeInstanceConstructor(Class<T> rawType, Class<? super T> withConstructor, Class<?>... parameterTypes) {
        this.constructor = Objects.requireNonNull(createUnsafeConstructor(rawType, withConstructor, parameterTypes));
    }

    protected UnsafeInstanceConstructor(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    protected <T> Constructor<T> createUnsafeConstructor(Class<T> clazz, Class<? super T> withConstructor, Class<?>... parameterTypes) {//todo
        if(clazz.isInterface()) {//sighs ~~ should *probably* never happen? maybe unless a Readable is directly passed in?
            //todo: lets unit test this later
            //todo: revisit how to handle interfaces internally?
            return null;
        }
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor<? super T> usingConstructor = withConstructor.getDeclaredConstructor(parameterTypes);
            @SuppressWarnings("unchecked")
            Constructor<T> intConstr = (Constructor<T>) rf.newConstructorForSerialization(clazz, usingConstructor);
            return intConstr;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create constructor", e);
        }
    }

    @Override
    public T construct(Object... params) {
        try {
            return constructor.newInstance(params);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot create object", ex);
        }
    }
}