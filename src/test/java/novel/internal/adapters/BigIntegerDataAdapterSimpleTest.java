package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.math.BigInteger;

class BigIntegerDataAdapterSimpleTest extends SimpleGoodBadTest<BigInteger> {
    BigIntegerDataAdapterSimpleTest() {
        super(new BigIntegerAdapter(), BigInteger.valueOf(Long.MAX_VALUE), stream->stream.strings("big boi"));
    }
}
