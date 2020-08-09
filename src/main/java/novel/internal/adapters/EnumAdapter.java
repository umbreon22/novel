package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

public class EnumAdapter<T extends Enum<T>> implements ObjectDataAdapter<T> {

    private final T[] enumConstants;
    EnumAdapter(Class<T> enumClass) {
        //hopefully the enum doesn't change (:
        this.enumConstants = enumClass.getEnumConstants();
        if(enumConstants == null) {
            throw new IllegalArgumentException(
                String.format("EnumConstants for %s is null.", enumClass.getCanonicalName())
            );
        }
    }

    @Override
    public T read(DataPaper paper) {
        int ordinal = paper.ints();
        validateOrdinal(ordinal);
        return enumConstants[ordinal];
    }

    @Override
    public void write(DataPen<?> pen, T object) {
        pen.ints(object.ordinal());
    }

    /**
     * A little safety around reading enums.
     * @param ordinal An int {@link Enum#ordinal()}
     * @throws DataPaperReadException if the ordinal is invalid
     */
    private void validateOrdinal(int ordinal) throws DataPaperReadException {
        if(ordinal < 0 || ordinal > enumConstants.length) {
            throw new DataPaperReadException("Ordinal was out of bounds: " + ordinal + "/" + enumConstants.length);
        }
    }

}
