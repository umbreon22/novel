package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.token.TypeToken;
import novel.api.types.write.AutoWriteable;
import novel.api.types.write.DataPen;
import novel.api.types.write.ObjectDataWriter;

public abstract class ReflectiveObjectWriter<T, F> implements ObjectDataWriter<T> {

    protected final Novel novel;
    private final Iterable<F> sourceFields;
    public ReflectiveObjectWriter(Novel novel, TypeToken<T> source) {
        this.novel = novel;
        this.sourceFields = supplyFields(novel, source);
    }

    protected abstract Iterable<F> supplyFields(Novel novel, TypeToken<T> source);

    @Override
    public void write(DataPen<?> pen, T object) {
        try {
            for (var field : sourceFields) {
                writeField(pen, field, memberFrom(field, object));
            }
        } catch (IllegalAccessException e) {//todo: custom exception?
            throw new IllegalStateException(e);
        }
    }

    protected abstract Object memberFrom(F field, T object) throws IllegalAccessException;

    /**
     * Writes a bool, true if field object exists, false if it's null.
     * If the object is not null, serializes the object.
     */
    @SuppressWarnings("unchecked")
    protected <WriteType> void writeField(DataPen<?> pen, F field, WriteType fieldObject) {
        boolean shouldWrite = fieldObject != null;
        if(!isNullsafe(field)) {
            //Nullsafe fields are assumed to NEVER be null, skipping this boolean
            pen.bools(shouldWrite);
        }
        if (shouldWrite) {
            if (isAutoWriteable(field)) {
                pen.objects((AutoWriteable) fieldObject);
            } else {
                novel.write(pen, fieldObject, (TypeToken<WriteType>) tokenFor(field));
            }
        }
    }

    protected abstract TypeToken<?> tokenFor(F field);

    protected abstract boolean isAutoWriteable(F field);

    protected abstract boolean isNullsafe(F field);
}
