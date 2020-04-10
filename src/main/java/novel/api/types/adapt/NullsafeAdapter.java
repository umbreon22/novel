package novel.api.types.adapt;

import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.write.DataPen;

/**
 * A wrapped class for {@link ObjectDataAdapter} that performs a null safe read/write.
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