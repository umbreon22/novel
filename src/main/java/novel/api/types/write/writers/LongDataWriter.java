package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.FloatPen;
import novel.api.types.write.pens.LongPen;

/**
 * Created on 8/9/2020.
 */
public interface LongDataWriter extends ObjectDataWriter<Number> {

    @Override
    default void write(DataPen<?> pen, Number object) {
        write(pen, object.longValue());
    }

    void write(LongPen<?> pen, long object);

}
