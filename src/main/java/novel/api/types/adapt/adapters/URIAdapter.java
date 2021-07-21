package novel.api.types.adapt.adapters;

import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.adapt.ObjectDataAdapter;

import java.net.URI;
import java.net.URISyntaxException;

public class URIAdapter implements ObjectDataAdapter<URI> {

    public URIAdapter(){}

    @Override
    public URI read(DataPaper paper) {
        try {
            return new URI(paper.strings());
        } catch (URISyntaxException e) {
            throw new DataPaperReadException(e);
        }
    }

    @Override
    public void write(DataPen pen, URI uri) {
        pen.strings(uri.toString());
    }
}
