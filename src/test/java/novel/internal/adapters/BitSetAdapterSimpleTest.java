package novel.internal.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.util.BitSet;

class BitSetAdapterSimpleTest extends SimpleGoodBadTest<BitSet> {
    BitSetAdapterSimpleTest() {
        super(new BitSetAdapter(), BitSet.valueOf(new long[]{1L, 2L, 3L}), stream->stream.ints(-1));
    }
}
