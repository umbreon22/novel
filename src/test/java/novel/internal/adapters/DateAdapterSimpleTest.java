package novel.internal.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.time.Instant;
import java.util.Date;

class DateAdapterSimpleTest extends SimpleGoodBadTest<Date> {
    DateAdapterSimpleTest() {
        super(new DateAdapter(), Date.from(Instant.now()), stream->stream.strings("owo whats this?"));
    }
}
