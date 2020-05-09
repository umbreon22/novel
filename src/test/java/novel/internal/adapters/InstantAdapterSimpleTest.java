package novel.internal.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class InstantAdapterSimpleTest extends SimpleGoodBadTest<Instant> {
    InstantAdapterSimpleTest() {
        super(new InstantAdapter(),
            Instant.now().truncatedTo(ChronoUnit.MILLIS),//We truncate because of precision issues
            stream->stream.strings("owo whats this?")
        );
    }
}
