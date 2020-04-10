package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

class AtomicBooleanAdapterSimpleTest extends SimpleGoodBadTest<AtomicBoolean> {
    AtomicBooleanAdapterSimpleTest() {
        super(new AtomicBooleanAdapter(), new AtomicBoolean(ThreadLocalRandom.current().nextBoolean()), stream->stream.strings("big boi"));
    }

    @Override
    protected void assertGood(AtomicBoolean writeMe, AtomicBoolean readMe) {
        Assertions.assertEquals(writeMe.get(), readMe.get());
    }
}
