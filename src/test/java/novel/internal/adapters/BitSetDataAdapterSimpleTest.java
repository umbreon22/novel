package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.util.BitSet;

class BitSetDataAdapterSimpleTest extends SimpleGoodBadTest<BitSet> {
    BitSetDataAdapterSimpleTest() {
        super(new BitSetAdapter(), BitSet.valueOf(new long[]{1L, 2L, 3L}), stream->stream.ints(-1));
    }
}
