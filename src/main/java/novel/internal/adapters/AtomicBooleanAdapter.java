package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanAdapter implements ObjectDataAdapter<AtomicBoolean> {

    AtomicBooleanAdapter(){}

    @Override
    public AtomicBoolean read(DataPaper paper) {
        return new AtomicBoolean(paper.bools());
    }

    @Override
    public void write(DataPen<?> pen, AtomicBoolean atomic) {
        pen.bools(atomic.get());
    }
}
