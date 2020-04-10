package novel.api.types.token;

import novel.api.types.adapt.Novelable;
import novel.api.types.read.Readable;
import novel.api.types.write.Writeable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTokenTest {

    @Test
    public void testTypeTokensOnAnonymousClass() {
        TypeToken<?> token = new Novelable(){}.token();//Novelable extends Writeable, Readable ...
        boolean isReadable = Readable.class.isAssignableFrom(token.getRawType());
        Assertions.assertTrue(isReadable);
        boolean isWriteable = Writeable.class.isAssignableFrom(token.getRawType());
        Assertions.assertTrue(isWriteable);

        token = new Readable(){}.token();//Novelable extends Writeable, Readable ...
        isReadable = Readable.class.isAssignableFrom(token.getRawType());
        Assertions.assertTrue(isReadable);
        isWriteable = Writeable.class.isAssignableFrom(token.getRawType());
        Assertions.assertFalse(isWriteable);

        token = new Writeable(){}.token();//Novelable extends Writeable, Readable ...
        isReadable = Readable.class.isAssignableFrom(token.getRawType());
        Assertions.assertFalse(isReadable);
        isWriteable = Writeable.class.isAssignableFrom(token.getRawType());
        Assertions.assertTrue(isWriteable);
    }
}
