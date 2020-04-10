package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

class StringBufferDataAdapterSimpleTest extends SimpleGoodBadTest<StringBuffer> {

    StringBufferDataAdapterSimpleTest() {
        super(new StringBufferAdapter(), new StringBuffer("owo whats this"), stream->stream.longs(-1L));
    }

    @Override
    protected void assertGood(StringBuffer writeMe, StringBuffer readMe) {
        Assertions.assertEquals(String.valueOf(writeMe), String.valueOf(readMe));
    }

}