package novel.api.types.write.writers;

import novel.api.types.write.pens.BoolPen;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.StringPen;

/**
 * Created on 8/9/2020.
 */
public interface BoolDataWriter extends ObjectDataWriter<Boolean> {

    @Override
    default void write(DataPen pen, Boolean object) {
        write((BoolPen) pen, object);
    }

    void write(BoolPen pen, boolean b);

}
