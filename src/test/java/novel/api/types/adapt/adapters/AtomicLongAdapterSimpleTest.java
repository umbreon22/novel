package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

class AtomicLongAdapterSimpleTest extends SimpleGoodBadTest<AtomicLong> {
    AtomicLongAdapterSimpleTest() {
        super(new AtomicLongAdapter(), new AtomicLong(ThreadLocalRandom.current().nextLong()), stream->stream.strings("big boi"));
    }

    @Override
    protected void assertGood(AtomicLong writeMe, AtomicLong readMe) {
        Assertions.assertEquals(writeMe.get(), readMe.get());
    }
}
