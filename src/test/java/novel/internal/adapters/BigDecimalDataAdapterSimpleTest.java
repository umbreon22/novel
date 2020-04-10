package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.math.BigDecimal;

class BigDecimalDataAdapterSimpleTest extends SimpleGoodBadTest<BigDecimal> {
    BigDecimalDataAdapterSimpleTest() {
        super(new BigDecimalAdapter(), BigDecimal.valueOf(Long.MAX_VALUE), stream->stream.strings("big boi"));
    }
}
