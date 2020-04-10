package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import java.net.MalformedURLException;
import java.net.URL;

class URLDataAdapterSimpleTest extends SimpleGoodBadTest<URL> {

    URLDataAdapterSimpleTest() throws MalformedURLException {
        super(new URLAdapter(), new URL("https://www.google.com"), stream->stream.strings("owo whats this?"));
    }

}
