package novel.internal.adapters;

import novel.testutil.SimpleGoodBadTest;

class EnumDataAdapterSimpleTest extends SimpleGoodBadTest<EnumDataAdapterSimpleTest.TestEnum> {
    enum TestEnum {TEST,ME,PLEASE}
    EnumDataAdapterSimpleTest() {
        super(new EnumAdapter<>(TestEnum.class), TestEnum.PLEASE, stream->stream.strings("owo whats this?"));
    }
}
