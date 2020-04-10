package novel.api.types.adapt;

import novel.api.types.token.Tokenable;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.CannotWrite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReadOnlyAdapterTest {

    @Test()
    void testRead() {
        dummyReadOnlyAdapter().read(null);
    }

    @Test()
    void testAsReader() {
        var adapter = dummyReadOnlyAdapter();
        Assertions.assertEquals(adapter, adapter.asReader());
    }

    @Test()
    void testWrite() {
        Assertions.assertThrows(
            UnsupportedOperationException.class,
            ()->dummyReadOnlyAdapter().write(null, null)
        );
    }

    @Test()
    void testAsWriter() {
        Assertions.assertTrue(dummyReadOnlyAdapter().asWriter() instanceof CannotWrite);
    }

    private ReadOnlyAdapter<Tokenable> dummyReadOnlyAdapter() {
        Tokenable tokenable = new Tokenable(){};
        ObjectDataReader<Tokenable> writer = (pen) -> null;
        return new ReadOnlyAdapter<>(tokenable.castToken(), writer);
    }


}
