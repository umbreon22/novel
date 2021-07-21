package novel.api.types.adapt.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerAdapter implements ObjectDataAdapter<AtomicInteger> {

    public AtomicIntegerAdapter(){}

    @Override
    public AtomicInteger read(DataPaper paper) {
        return new AtomicInteger(paper.ints());
    }

    @Override
    public void write(DataPen pen, AtomicInteger atomic) {
        pen.ints(atomic.get());
    }
}
