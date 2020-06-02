package novel.api.types.adapt;

import novel.api.types.annotations.Inked;
import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.write.pens.DataPen;

/**
 * A wrapped class for {@link ObjectDataAdapter} that performs a null safe read/write.
 * This ignores {@link Inked} annotations for the adapted class only. todo: should this flow downstream?
 */
final class NullsafeAdapter<T> implements ObjectDataAdapter<T> {

    private final ObjectDataAdapter<T> adapter;

    NullsafeAdapter(ObjectDataAdapter<T> adapter) {
        if(adapter instanceof NullsafeAdapter) {
            throw new IllegalArgumentException("Can(should?)not wrap Nullsafe adapter with Nullsafe adapter");
        }
        this.adapter = adapter;
    }

    @Override
    public T read(DataPaper paper) throws DataPaperReadException {
        return paper.bools() ? adapter.read(paper) : null;
    }

    @Override
    public void write(DataPen<?> pen, T object) {
        boolean isInked = object != null;
        pen.bools(isInked);
        if(isInked) {
            adapter.write(pen, object);
        }
    }

}
