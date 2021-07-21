package novel.api.types.adapt.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.Date;

public class DateAdapter implements ObjectDataAdapter<Date> {
    //todo: https://github.com/google/gson/blob/master/gson/src/main/java/com/google/gson/internal/bind/DateTypeAdapter.java
    @Override
    public Date read(DataPaper paper) {
        return new Date(paper.longs());
    }

    @Override
    public void write(DataPen pen, Date date) {
        pen.longs(date.getTime());
    }
}
