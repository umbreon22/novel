package novel.internal.registry;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.read.Readable;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.DataPen;
import novel.api.types.write.ObjectDataWriter;
import novel.api.types.write.AutoWriteable;
import novel.internal.registry.NovelAdapterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NovelRegistryTest {

    private NovelAdapterRegistry registry;

    @BeforeEach
    void newRegistry() {
        this.registry = new NovelAdapterRegistry(Novel.newBuilder().build());
    }

    static class SomeReadable implements Readable {
        protected final String whoIsCute;
        private SomeReadable(String youAre) {
            this.whoIsCute = youAre;
        }
    }

    static class SomeWriteable implements AutoWriteable {
        protected final String whoIsCute;
        private SomeWriteable(String youAre) {
            this.whoIsCute = youAre;
        }

        @Override
        public void write(DataPen<?> writer) {
            writer.strings(whoIsCute);
        }
    }

    static class SomeSuperReadable extends SomeReadable {
        private SomeSuperReadable(String youAre) {
            super(youAre);
        }
    }

    static class SomeSuperWriteable extends SomeWriteable {
        private SomeSuperWriteable(String youAre) {
            super(youAre);
        }
    }

    @Test
    void testRegisterReadable() {
        ObjectDataReader<SomeReadable> odr = reader -> new SomeReadable(reader.strings());
        registry.register(SomeReadable.class, odr);
        Assertions.assertEquals(odr, registry.reader(SomeReadable.class));
    }

    @Test
    void testRegisterReadableSuper() {
        ObjectDataReader<SomeReadable> odr = reader -> new SomeReadable(reader.strings());
        registry.register(SomeReadable.class, odr);
        Assertions.assertNotSame(odr, registry.reader(SomeSuperReadable.class));
    }

    @Test
    void testRegisterReadableChild() {
        ObjectDataReader<SomeSuperReadable> odr = reader -> new SomeSuperReadable(reader.strings());
        registry.register(SomeSuperReadable.class, odr);
        Assertions.assertNotEquals(odr, registry.reader(SomeReadable.class));
    }

    @Test
    void testRegisterWriteable() {
        ObjectDataWriter<SomeWriteable> odw = (out, writeable) -> out.strings(writeable.whoIsCute);
        registry.register(SomeWriteable.class, odw);
        Assertions.assertEquals(odw, registry.writer(SomeWriteable.class));
    }

    @Test
    void testRegisterWriteableSuper() {
        ObjectDataWriter<SomeWriteable> odw = (out, writeable) -> out.strings(writeable.whoIsCute);
        registry.register(SomeWriteable.class, odw);
        Assertions.assertNotSame(odw, registry.writer(SomeSuperWriteable.class));
    }

    @Test
    void testRegisterWriteableChild() {
        ObjectDataWriter<SomeSuperWriteable> odw = (out, writeable) -> out.strings(writeable.whoIsCute);
        registry.register(SomeSuperWriteable.class, odw);
        Assertions.assertNotEquals(odw, registry.writer(SomeWriteable.class));
    }

    @Test
    void testRegisterBoth() {
        ObjectDataReader<Integer> reader = DataPaper::ints;
        ObjectDataWriter<Integer> writer = DataPen::ints;
        var adapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        registry.register(Integer.class, adapter);
        registry.register(int.class, adapter);
        Assertions.assertEquals(registry.reader(int.class), registry.reader(Integer.class));
        Assertions.assertEquals(registry.writer(int.class), registry.writer(Integer.class));
        Assertions.assertEquals(registry.adapter(int.class), registry.adapter(Integer.class));

        registry.register(r -> new SomeWriteable(r.strings()), DataPen::objects, SomeWriteable.class);
        Assertions.assertNotEquals(registry.reader(SomeWriteable.class), registry.reader(SomeSuperWriteable.class));
        Assertions.assertNotEquals(registry.writer(SomeWriteable.class), registry.writer(SomeSuperWriteable.class));
        Assertions.assertNotEquals(registry.adapter(SomeWriteable.class), registry.adapter(SomeSuperWriteable.class));
    }

    @Test
    void testPrimitives() {
        Assertions.assertEquals(registry.reader(int.class), registry.reader(Integer.class));
        Assertions.assertEquals(registry.reader(long.class), registry.reader(Long.class));
    }

}