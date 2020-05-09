package novel.internal.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.math.BigDecimal;

class BigDecimalAdapterSimpleTest extends SimpleGoodBadTest<BigDecimal> {
    BigDecimalAdapterSimpleTest() {
        super(new BigDecimalAdapter(), BigDecimal.valueOf(Long.MAX_VALUE), stream->stream.strings("big boi"));
    }
}
