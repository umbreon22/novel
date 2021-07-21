package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import java.net.MalformedURLException;
import java.net.URL;

class URLAdapterSimpleTest extends SimpleGoodBadTest<URL> {

    URLAdapterSimpleTest() throws MalformedURLException {
        super(new URLAdapter(), new URL("https://www.google.com"), stream->stream.strings("owo whats this?"));
    }

}
