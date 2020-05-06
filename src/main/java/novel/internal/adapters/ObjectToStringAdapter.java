package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.function.Function;

public abstract class ObjectToStringAdapter<T> implements ObjectDataAdapter<T> {
    ObjectToStringAdapter(Function<String, T> constructor) {
        this.constructor = constructor;
    }

    private final Function<String, T> constructor;
    @Override
    public T read(DataPaper paper) throws DataPaperReadException {
        try {
            return constructor.apply(paper.strings());
        } catch(IllegalArgumentException ex) {
            throw new DataPaperReadException(ex);
        }
    }

    @Override
    public void write(DataPen<?> pen, T object) {
        pen.strings(object::toString);
    }
}
