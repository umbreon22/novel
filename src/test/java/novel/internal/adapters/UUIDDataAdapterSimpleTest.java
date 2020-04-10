package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;
import java.util.UUID;

class UUIDDataAdapterSimpleTest extends SimpleGoodBadTest<UUID> {
    UUIDDataAdapterSimpleTest() {
        super(new UUIDAdapter(), UUID.randomUUID(), stream->stream.strings("owo whats this?"));
    }
}
