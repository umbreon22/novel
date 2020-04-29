package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongAdapter implements ObjectDataAdapter<AtomicLong> {

    AtomicLongAdapter(){}

    @Override
    public AtomicLong read(DataPaper paper) {
        return new AtomicLong(paper.longs());
    }

    @Override
    public void write(DataPen<?> pen, AtomicLong atomic) {
        pen.longs(atomic.get());
    }
}
