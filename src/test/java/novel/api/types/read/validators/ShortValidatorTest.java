package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created on 8/27/2020.
 */
public class ShortValidatorTest {

    private final ShortValidator positive = b -> b >= 0;
    private final ShortValidator negative = b -> b < 0;
    private final ShortValidator even = b -> b % 2 == 0;
    private final ShortValidator odd = even.negate();
    private final ShortValidator evenOrPositive = even.or(positive);
    private final ShortValidator oddAndNegative = odd.and(negative);

    @Test
    void testValidatorWithValidData() {
        positive.validate(0);
        positive.validate(100);
        int negativeAsShort = Short.MAX_VALUE + 1;
        IntValidator positiveInt = i->i>=0;
        positiveInt.validate(negativeAsShort);
        negative.validate(negativeAsShort);
        negative.validate(-69);
        evenOrPositive.validate((short) 2);
        evenOrPositive.validate((short) 3);
        evenOrPositive.validate((short)-2);
        oddAndNegative.validate((short)-1);
    }

    @Test
    void testValidatorWithInvalidData() {
        Assertions.assertThrows(DataValidationException.class, ()->negative.validate((short)0));
        Assertions.assertThrows(DataValidationException.class, ()->negative.validate((short)100));
        Assertions.assertThrows(DataValidationException.class, ()->positive.validate((short)-69));
        Assertions.assertThrows(DataValidationException.class, ()->evenOrPositive.validate((short)-69));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((short) 1));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((short) 2));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((short)-2));
    }


}
