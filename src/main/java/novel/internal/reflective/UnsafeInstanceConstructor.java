package novel.internal.reflective;

import sun.reflect.ReflectionFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public class UnsafeInstanceConstructor<T> implements Supplier<T> {

    private final Constructor<T> constructor;
    //todo support methodhandle

    public UnsafeInstanceConstructor(Class<T> rawType) {
        constructor = createUnsafeConstructor(rawType);
    }

    protected <T> Constructor<T> createUnsafeConstructor(Class<T> clazz) {
        return createUnsafeConstructor(clazz, Object.class);
    }

    protected <T> Constructor<T> createUnsafeConstructor(Class<T> clazz, Class<? super T> withConstructor) {//todo
        if(clazz.isInterface()) {//sighs ~~ should *probably* never happen? maybe unless a Readable is directly passed in?
            //todo: lets unit test this later
            //todo: revisit how to handle interfaces internally?
            return null;
        }
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor<? super T> usingConstructor = withConstructor.getDeclaredConstructor();
            @SuppressWarnings("unchecked")
            Constructor<T> intConstr = (Constructor<T>) rf.newConstructorForSerialization(clazz, usingConstructor);
            return intConstr;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create object", e);
        }
    }

    @Override
    public T get() {
        try {
            return constructor.newInstance();
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot create object", ex);
        }
    }
}