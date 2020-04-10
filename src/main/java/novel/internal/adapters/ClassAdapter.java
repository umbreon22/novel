package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

public class ClassAdapter implements ObjectDataAdapter<Class> {
    @SuppressWarnings("unchecked")
    @Override
    public Class read(DataPaper paper) {
        String classCanonicalName = paper.strings();
        try {
            return Class.forName(classCanonicalName);
        } catch (ClassNotFoundException e) {
            throw new DataPaperReadException("Could not load class for name (not found): " + classCanonicalName);
        }
    }

    @Override
    public void write(DataPen<?> pen, Class object) {
        pen.strings(object.getCanonicalName());
    }
}