package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.time.Instant;
import java.util.Date;

class DateDataAdapterSimpleTest extends SimpleGoodBadTest<Date> {
    DateDataAdapterSimpleTest() {
        super(new DateAdapter(), Date.from(Instant.now()), stream->stream.strings("owo whats this?"));
    }
}
