package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.Calendar;

public class CalendarAdapter implements ObjectDataAdapter<Calendar> {

    private static final int[] CALENDAR_FIELDS = {
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH,
        Calendar.HOUR_OF_DAY,
        Calendar.MINUTE,
        Calendar.SECOND
    };

    @Override
    public Calendar read(DataPaper paper) {
        var builder = new Calendar.Builder();
        for(int field : CALENDAR_FIELDS) {
            builder.set(field, paper.ints());
        }
        return builder.build();
    }

    @Override
    public void write(DataPen<?> pen, Calendar calendar) {
        for(int field : CALENDAR_FIELDS) {
            pen.ints(calendar.get(field));
        }
    }
}
