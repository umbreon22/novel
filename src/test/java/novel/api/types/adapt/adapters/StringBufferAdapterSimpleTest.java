package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

class StringBufferAdapterSimpleTest extends SimpleGoodBadTest<StringBuffer> {

    StringBufferAdapterSimpleTest() {
        super(new StringBufferAdapter(), new StringBuffer("owo whats this"), stream->stream.longs(-1L));
    }

    @Override
    protected void assertGood(StringBuffer writeMe, StringBuffer readMe) {
        Assertions.assertEquals(String.valueOf(writeMe), String.valueOf(readMe));
    }

}
