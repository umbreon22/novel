package novel.internal.reflective;

import novel.api.types.annotations.Inked;
import novel.api.types.token.Tokenable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectiveUtilTest {

    @Test
    void testIsNullsafe() {
        @Inked
        class InkMe implements Tokenable {}
        Assertions.assertTrue(ReflectiveUtil.isNullsafe(InkMe.class));
        Assertions.assertTrue(ReflectiveUtil.isNullsafe(new InkMe().token().getRawType()));
    }

}