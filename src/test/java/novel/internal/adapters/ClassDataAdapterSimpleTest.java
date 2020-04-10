package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

class ClassDataAdapterSimpleTest extends SimpleGoodBadTest<Class> {
    ClassDataAdapterSimpleTest() {
        super(new ClassAdapter(), ClassDataAdapterSimpleTest.class, stream->stream.strings("owo whats this?"));
    }
}
