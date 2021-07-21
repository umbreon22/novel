package novel.api.types.adapt.adapters;

import novel.api.Novel;
import novel.api.types.adapt.Novelable;
import novel.api.types.token.TypeToken;
import novel.internal.testutil.DataPenQueue;
import novel.internal.testutil.SimpleGoodBadTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("rawtypes")
class ClassDataAdapterSimpleTest extends SimpleGoodBadTest<Class> {

    ClassDataAdapterSimpleTest() {
        super(new ClassAdapter(), ClassDataAdapterSimpleTest.class, stream->stream.strings("owo whats this?"));
    }

    class Dummy implements Novelable {}

    @Test
    void testInnerClass() {
        Novel novel = Novel.newDefaultInstance();
        DataPenQueue pen = new DataPenQueue();
        novel.write(pen, Dummy.class, TypeToken.get(Class.class));
        Class<?> dummyClass = novel.read(pen.paper(), Class.class);
        Assertions.assertEquals(Dummy.class, dummyClass);
    }

}
