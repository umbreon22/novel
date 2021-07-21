package novel.api.types.adapt.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.time.Instant;

public class InstantAdapter implements ObjectDataAdapter<Instant> {

    @Override
    public Instant read(DataPaper paper) {
        return Instant.ofEpochMilli(paper.longs());
    }

    @Override
    public void write(DataPen pen, Instant instant) {
        pen.longs(instant.toEpochMilli());
    }
}
