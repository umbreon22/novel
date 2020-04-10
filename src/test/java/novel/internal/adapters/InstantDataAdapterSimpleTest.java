package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class InstantDataAdapterSimpleTest extends SimpleGoodBadTest<Instant> {
    InstantDataAdapterSimpleTest() {
        super(new InstantAdapter(),
            Instant.now().truncatedTo(ChronoUnit.MILLIS),//We truncate because of precision issues
            stream->stream.strings("owo whats this?")
        );
    }
}
