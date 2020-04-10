package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

import java.util.BitSet;

public class BitSetAdapter implements ObjectDataAdapter<BitSet> {

    @Override
    public BitSet read(DataPaper paper) {
        int size = paper.ints();
        validateSize(size);
        return BitSet.valueOf(paper.bytes(size));
    }

    @Override
    public void write(DataPen<?> pen, BitSet bitset) {
        byte[] byteArray = bitset.toByteArray();
        pen.ints(byteArray.length).bytes(byteArray);
    }

    private static void validateSize(int size) {
        if(size < 0 || size >= Integer.MAX_VALUE) {
            throw new DataPaperReadException("BitSet size is invalid: " + size);
        }
    }

}
