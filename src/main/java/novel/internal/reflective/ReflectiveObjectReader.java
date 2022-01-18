package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.TypeToken;
import novel.internal.reflective.constructor.ReflectiveConstructor;
import novel.internal.reflective.constructor.UnsafeInstanceConstructor;

public abstract class ReflectiveObjectReader<T, F> implements ObjectDataReader<T> {

    protected final Novel novel;
    private final Iterable<F> sourceFields;
    private final ReflectiveConstructor<T> constructor;
    private final boolean isClassNullsafe;

    public ReflectiveObjectReader(Novel novel, TypeToken<T> source) {
        this.novel = novel;
        this.sourceFields = supplyFields(novel, source);
        this.constructor = new UnsafeInstanceConstructor(source.getRawType(), Object.class);
        this.isClassNullsafe = ReflectiveUtil.isNullsafe(source.getRawType());
    }

    protected abstract Iterable<F> supplyFields(Novel novel, TypeToken<T> source);

    @Override
    public T read(DataPaper paper) {
        try {
            T instance = constructor.construct();
            for (var field : sourceFields) {
                readField(paper, field, instance);
            }
            return instance;
        } catch (Throwable e) {//todo: generate custom exception
            throw new IllegalArgumentException(e);
        }
    }

    private void readField(DataPaper paper, F field, T instance) throws Throwable {
        if (shouldRead(paper, field)) {
            var read = novel.read(paper, tokenFor(field));
            setInstance(field, instance, read);
        }
    }

    private boolean shouldRead(DataPaper paper, F field) {
        return isNullsafe(field) || paper.bools();
    }

    protected abstract TypeToken<?> tokenFor(F field);

    protected abstract void setInstance(F field, T instance, Object read) throws Throwable;

    protected abstract boolean isFieldNullsafe(F field);

    protected final boolean isNullsafe(F field) {
        return isClassNullsafe || isFieldNullsafe(field);
    }

}