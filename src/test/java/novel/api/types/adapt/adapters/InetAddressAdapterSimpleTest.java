package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

class InetAddressAdapterSimpleTest extends SimpleGoodBadTest<InetAddress> {

    InetAddressAdapterSimpleTest() throws UnknownHostException {
        super(new InetAddressAdapter(), InetAddress.getLocalHost(), stream->stream.strings("uwu"));
    }

    void testNegativeArrayLength() {
        //todo
    }

}
