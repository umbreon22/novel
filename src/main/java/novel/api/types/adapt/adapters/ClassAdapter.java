package novel.api.types.adapt.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

@SuppressWarnings("rawtypes")
public class ClassAdapter implements ObjectDataAdapter<Class> {

    @Override
    public Class read(DataPaper paper) {
        String className = paper.strings();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new DataPaperReadException("Could not load class for name (not found): " + className);
        }
    }

    @Override
    public void write(DataPen pen, Class object) {
        pen.strings(object.getName());
    }
}
