package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.IntPen;

/**
 * Created on 8/9/2020.
 */
public interface IntDataWriter extends ObjectDataWriter<Number> {
    @Override
    default void write(DataPen pen, Number object) {
        write(pen, object.intValue());
    }
    void write(IntPen pen, int object);
}
