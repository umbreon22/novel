package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class AtomicIntegerAdapterSimpleTest extends SimpleGoodBadTest<AtomicInteger> {
    AtomicIntegerAdapterSimpleTest() {
        super(new AtomicIntegerAdapter(), new AtomicInteger(ThreadLocalRandom.current().nextInt()), stream->stream.strings("big boi"));
    }

    @Override
    protected void assertGood(AtomicInteger writeMe, AtomicInteger readMe) {
        Assertions.assertEquals(writeMe.get(), readMe.get());
    }
}
