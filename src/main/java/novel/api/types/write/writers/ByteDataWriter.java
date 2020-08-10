package novel.api.types.write.writers;

import novel.api.types.write.pens.BytePen;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.ShortPen;

/**
 * Created on 8/9/2020.
 */
public interface ByteDataWriter extends ObjectDataWriter<Number> {

    @Override
    default void write(DataPen<?> pen, Number object) {
        write(pen, object.byteValue());
    }

    void write(BytePen<?> pen, byte object);

}
