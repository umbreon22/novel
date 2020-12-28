package novel.api.types.write.writers;

import novel.api.types.write.pens.BytePen;
import novel.api.types.write.pens.CharPen;
import novel.api.types.write.pens.DataPen;

/**
 * Created on 8/9/2020.
 */
public interface CharDataWriter extends ObjectDataWriter<Character> {

    @Override
    default void write(DataPen pen, Character object) {
        write(pen, object.charValue());
    }

    void write(CharPen pen, char object);

}
