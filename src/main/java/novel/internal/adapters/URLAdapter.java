package novel.internal.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLAdapter implements ObjectDataAdapter<URL> {

    URLAdapter(){}

    @Override
    public URL read(DataPaper paper) throws DataPaperReadException {
        try {
            return new URL(paper.strings());
        } catch (MalformedURLException e) {
            throw new DataPaperReadException(e);
        }
    }

    @Override
    public void write(DataPen<?> pen, URL object) {
        pen.strings(object.toString());
    }
}
