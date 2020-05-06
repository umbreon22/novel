package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.math.BigInteger;

class BigIntegerAdapterSimpleTest extends SimpleGoodBadTest<BigInteger> {
    BigIntegerAdapterSimpleTest() {
        super(new BigIntegerAdapter(), BigInteger.valueOf(Long.MAX_VALUE), stream->stream.strings("big boi"));
    }
}
