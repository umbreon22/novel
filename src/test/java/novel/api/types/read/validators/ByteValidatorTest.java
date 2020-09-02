package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created on 8/27/2020.
 */
public class ByteValidatorTest {

    private final ByteValidator positive = b -> b >= 0;
    private final ByteValidator negative = b -> b < 0;
    private final ByteValidator even = b -> b % 2 == 0;
    private final ByteValidator odd = even.negate();
    private final ByteValidator evenOrPositive = even.or(positive);
    private final ByteValidator oddAndNegative = odd.and(negative);

    @Test
    void testValidatorWithValidData() {
        positive.validate(0);
        positive.validate(100);
        int negativeAsByte = Byte.MAX_VALUE + 1;
        IntValidator positiveInt = i->i>=0;
        positiveInt.validate(negativeAsByte);
        negative.validate(negativeAsByte);
        negative.validate(-69);
        evenOrPositive.validate((byte)2);
        evenOrPositive.validate((byte)3);
        evenOrPositive.validate((byte)-2);
        oddAndNegative.validate((byte)-1);
    }

    @Test
    void testValidatorWithInvalidData() {
        Assertions.assertThrows(DataValidationException.class, ()->negative.validate((byte)0));
        Assertions.assertThrows(DataValidationException.class, ()->negative.validate((byte)100));
        Assertions.assertThrows(DataValidationException.class, ()->positive.validate((byte)-69));
        Assertions.assertThrows(DataValidationException.class, ()->evenOrPositive.validate((byte)-69));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((byte)1));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((byte)2));
        Assertions.assertThrows(DataValidationException.class, ()->oddAndNegative.validate((byte)-2));
    }


}
