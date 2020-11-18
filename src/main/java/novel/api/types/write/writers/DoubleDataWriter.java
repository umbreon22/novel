package novel.api.types.write.writers;

import novel.api.types.write.pens.BytePen;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.DoublePen;

/**
 * Created on 8/9/2020.
 */
public interface DoubleDataWriter extends ObjectDataWriter<Number> {

    @Override
    default void write(DataPen pen, Number object) {
        write(pen, object.doubleValue());
    }

    void write(DoublePen pen, double object);

}
