package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.DoublePen;
import novel.api.types.write.pens.FloatPen;

/**
 * Created on 8/9/2020.
 */
public interface FloatDataWriter extends ObjectDataWriter<Number> {

    @Override
    default void write(DataPen<?> pen, Number object) {
        write(pen, object.floatValue());
    }

    void write(FloatPen<?> pen, float object);

}
