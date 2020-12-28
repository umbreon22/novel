package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.IntPen;
import novel.api.types.write.pens.ShortPen;

/**
 * Created on 8/9/2020.
 */
public interface ShortDataWriter extends ObjectDataWriter<Number> {

    @Override
    default void write(DataPen pen, Number object) {
        write(pen, object.shortValue());
    }

    void write(ShortPen pen, short object);

}
