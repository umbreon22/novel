package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

import java.net.URI;
import java.net.URISyntaxException;

class URIAdapterSimpleTest extends SimpleGoodBadTest<URI> {

    URIAdapterSimpleTest() throws URISyntaxException {
        super(new URIAdapter(), new URI("https://www.google.com"), stream->stream.strings("owo whats this?"));
    }

}
