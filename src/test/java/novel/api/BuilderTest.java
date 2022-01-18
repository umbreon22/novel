package novel.api;

import novel.api.types.adapt.Novelable;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.writers.ObjectDataWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuilderTest {

    static class StrawDummy extends Dummy {}

    static class Dummy implements Novelable {}

    private static ObjectDataReader<StrawDummy> strawDummyEmptyReader() { return paper -> new StrawDummy();}

    private static ObjectDataWriter<StrawDummy> strawDummyEmptyWriter() { return (pen, strawDummy) -> {};}

    @Test
    public void testBuildWithSuper() {
        ObjectDataReader<StrawDummy> strawDummyDummyReader = strawDummyEmptyReader();
        ObjectDataWriter<StrawDummy> strawDummyDummyWriter = strawDummyEmptyWriter();
        var novel = Novel.newBuilder()
                .withPolicies(Policies.newBuilder().shouldWrapAdapters(true).build())
                .withSuperReader(strawDummyDummyReader, StrawDummy.class)
                .withSuperWriter(strawDummyDummyWriter, StrawDummy.class)
                .build();
        var registry = novel.contents();

        //pre adapter
        Assertions.assertSame(registry.writer(StrawDummy.class), strawDummyDummyWriter);
        Assertions.assertSame(registry.reader(StrawDummy.class), strawDummyDummyReader);

        Assertions.assertSame(registry.writer(Dummy.class), strawDummyDummyWriter);
        Assertions.assertSame(registry.reader(Dummy.class), strawDummyDummyReader);

        //a wrapped adapter should be registered upon first retrieval
        ObjectDataAdapter<StrawDummy> strawDummyAdapter = registry.adapter(StrawDummy.class);
        Assertions.assertSame(strawDummyAdapter, registry.adapter(StrawDummy.class));
        //post adapter...
        Assertions.assertSame(registry.writer(StrawDummy.class), strawDummyDummyWriter);
        Assertions.assertSame(registry.reader(StrawDummy.class), strawDummyDummyReader);
        Assertions.assertNotSame(registry.reader(StrawDummy.class), strawDummyAdapter);


        //no adapter generated for Dummy
        Assertions.assertSame(registry.writer(Dummy.class), strawDummyDummyWriter);
        Assertions.assertSame(registry.reader(Dummy.class), strawDummyDummyReader);

        Assertions.assertNotSame(registry.writer(Dummy.class), strawDummyAdapter);
        Assertions.assertNotSame(registry.reader(Dummy.class), strawDummyAdapter);
        Assertions.assertNotSame(registry.adapter(Dummy.class), strawDummyAdapter);

        //now we generate an adapter for Dummy
        var dummyAdapter = registry.adapter(Dummy.class);
        Assertions.assertSame(dummyAdapter, registry.adapter(Dummy.class));
        Assertions.assertNotSame(dummyAdapter, strawDummyAdapter);
        Assertions.assertNotSame(registry.writer(Dummy.class), dummyAdapter);
        Assertions.assertNotSame(registry.reader(Dummy.class), dummyAdapter);
        Assertions.assertSame(registry.adapter(Dummy.class), dummyAdapter);
    }

    @Test
    public void testBuildWithoutSuper() {
        ObjectDataReader<StrawDummy> strawDummyDummyReader = strawDummyEmptyReader();
        ObjectDataWriter<StrawDummy> strawDummyDummyWriter = strawDummyEmptyWriter();
        var novel = Novel.newBuilder()
                .withReader(strawDummyDummyReader, StrawDummy.class)
                .withWriter(strawDummyDummyWriter, StrawDummy.class)
                .withPolicies(Policies.newBuilder().shouldWrapAdapters(true).shouldUseDefaultFactories(true).build())
                .build();
        var repo = novel.contents();

        //pre adapter
        Assertions.assertEquals(repo.writer(StrawDummy.class), strawDummyDummyWriter);
        Assertions.assertEquals(repo.reader(StrawDummy.class), strawDummyDummyReader);

        Assertions.assertNotEquals(repo.writer(Dummy.class), strawDummyDummyWriter);
        Assertions.assertNotEquals(repo.reader(Dummy.class), strawDummyDummyReader);

        //post adapter... a wrapped adapter should be registered on the following adapter class.
        ObjectDataAdapter<StrawDummy> strawDummyDummyAdapter = repo.adapter(StrawDummy.class);

        Assertions.assertSame(repo.reader(StrawDummy.class), strawDummyDummyReader);
        Assertions.assertNotSame(repo.reader(StrawDummy.class), strawDummyDummyAdapter);

        Assertions.assertNotSame(repo.writer(Dummy.class), strawDummyDummyWriter);
        Assertions.assertNotSame(repo.writer(StrawDummy.class), strawDummyDummyAdapter);

        Assertions.assertNotEquals(repo.writer(Dummy.class), strawDummyDummyWriter);
        Assertions.assertNotEquals(repo.reader(Dummy.class), strawDummyDummyReader);
        Assertions.assertNotEquals(repo.adapter(Dummy.class), strawDummyDummyAdapter);
        Assertions.assertNotEquals(repo.writer(Dummy.class), strawDummyDummyAdapter);
        Assertions.assertNotEquals(repo.reader(Dummy.class), strawDummyDummyAdapter);

    }
}
