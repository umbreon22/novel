package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

class EnumAdapterSimpleTest extends SimpleGoodBadTest<EnumAdapterSimpleTest.TestEnum> {
    enum TestEnum {TEST,ME,PLEASE}
    EnumAdapterSimpleTest() {
        super(new EnumAdapter<>(TestEnum.class), TestEnum.PLEASE, stream->stream.strings("owo whats this?"));
    }
}
