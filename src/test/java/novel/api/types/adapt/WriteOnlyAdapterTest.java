package novel.api.types.adapt;

import novel.testutil.DataPenQueue;
import novel.api.types.read.CannotRead;
import novel.api.types.token.Tokenable;
import novel.api.types.write.ObjectDataWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriteOnlyAdapterTest {

    @Test()
    void testRead() {
        Assertions.assertThrows(
            UnsupportedOperationException.class,
            ()-> dummyWriteOnlyAdapter().read(null)
        );
    }

    @Test()
    void testAsReader() {
        Assertions.assertTrue(dummyWriteOnlyAdapter().asReader() instanceof CannotRead);
    }

    @Test()
    void testWrite() {
        dummyWriteOnlyAdapter().write(new DataPenQueue(), dummyTokenable());
    }

    @Test()
    void testAsWriter() {
        var dummy = dummyWriteOnlyAdapter();
        Assertions.assertEquals(dummy, dummy.asWriter());
    }

    private Tokenable dummyTokenable() {
        return new Tokenable(){};
    }

    private WriteOnlyAdapter<Tokenable> dummyWriteOnlyAdapter() {
        ObjectDataWriter<Tokenable> writer = (pen, object) -> {};
        return new WriteOnlyAdapter<>(dummyTokenable().castToken(), writer);
    }

}
