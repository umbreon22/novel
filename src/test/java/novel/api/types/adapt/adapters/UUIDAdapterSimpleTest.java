package novel.api.types.adapt.adapters;

import novel.internal.testutil.SimpleGoodBadTest;
import java.util.UUID;

class UUIDAdapterSimpleTest extends SimpleGoodBadTest<UUID> {
    UUIDAdapterSimpleTest() {
        super(new UUIDAdapter(), UUID.randomUUID(), stream->stream.strings("owo whats this?"));
    }
}
