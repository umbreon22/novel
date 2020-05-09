package novel.internal.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;

class StringBuilderAdapterSimpleTest extends SimpleGoodBadTest<StringBuilder> {

    StringBuilderAdapterSimpleTest() {
        super(new StringBuilderAdapter(), new StringBuilder("owo whats this"), stream->stream.ints(-1));
    }

    @Override
    protected void assertGood(StringBuilder writeMe, StringBuilder readMe) {
        Assertions.assertEquals(String.valueOf(writeMe), String.valueOf(readMe));
    }

}
