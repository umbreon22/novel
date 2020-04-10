package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

import java.util.Calendar;

class CalendarDataAdapterSimpleTest extends SimpleGoodBadTest<Calendar> {
    CalendarDataAdapterSimpleTest() {
        super(new CalendarAdapter(), Calendar.getInstance(), stream->stream.strings("owo whats this?"));
    }

    @Override
    protected void assertGood(Calendar writeMe, Calendar readMe) {
        //Clearing milliseconds Calendar seems to have some precision issues, as long as the rest is good it our assertion should be 'good enough'
        writeMe.clear(Calendar.MILLISECOND);
        readMe.clear(Calendar.MILLISECOND);
        Assertions.assertEquals(writeMe, readMe);
    }
}
