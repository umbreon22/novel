package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.TypeToken;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public abstract class ReflectiveObjectReader<T, F> implements ObjectDataReader<T> {

    protected final Novel novel;
    private final Iterable<F> sourceFields;
    private final Supplier<T> constructor;

    public ReflectiveObjectReader(Novel novel, TypeToken<T> source) {
        this.novel = novel;
        this.sourceFields = supplyFields(novel, source);
        this.constructor = () -> {
            @SuppressWarnings("unchecked")
            T instance = createUnsafeInstance((Class<T>) source.getRawType());
            return instance;
        };
    }

    protected abstract Iterable<F> supplyFields(Novel novel, TypeToken<T> source);

    @SuppressWarnings("unchecked")
    @Override
    public T read(DataPaper paper) {
        try {
            T instance = constructor.get();
            for (var field : sourceFields) {
                readField(paper, field, instance);
            }
            return instance;
        } catch (IllegalAccessException e) {//todo: generate custom exception
            throw new IllegalArgumentException(e);
        }
    }

    private void readField(DataPaper paper, F field, T instance) throws IllegalAccessException {
        if (isNullsafe(field) || paper.bools()) {
            var read = novel.read(paper, tokenFor(field));
            setInstance(field, instance, read);
        }
    }

    protected abstract TypeToken<?> tokenFor(F field);

    protected abstract void setInstance(F field, T instance, Object read) throws IllegalAccessException;

    protected abstract boolean isNullsafe(F field);

    protected static <T> T createUnsafeInstance(Class<T> clazz) {
        return createUnsafeInstance(clazz, Object.class);
    }

    //not sure about this chief
    protected static <T> T createUnsafeInstance(Class<T> clazz, Class<? super T> withConstructor) {
        if(clazz.isInterface()) {//sighs ~~ should *probably* never happen? maybe unless a Readable is directly passed in?
            //todo: lets unit test this later
            //todo: revisit how to handle interfaces internally?
            return null;
        }
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor<? super T> usingConstructor = withConstructor.getDeclaredConstructor();
            Constructor<?> intConstr = rf.newConstructorForSerialization(clazz, usingConstructor);
            return clazz.cast(intConstr.newInstance());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create object", e);
        }
    }

}