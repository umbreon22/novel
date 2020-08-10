package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;
import novel.api.types.write.pens.ShortPen;
import novel.api.types.write.pens.StringPen;

/**
 * Created on 8/9/2020.
 */
public interface StringDataWriter extends ObjectDataWriter<CharSequence> {

    @Override
    default void write(DataPen<?> pen, CharSequence object) {
        write((StringPen<?>) pen, object.toString());
    }

    void write(StringPen<?> pen, String object);

}
