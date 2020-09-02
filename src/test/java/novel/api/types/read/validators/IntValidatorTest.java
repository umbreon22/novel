package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

public class IntValidatorTest {

    private final IntValidator positive = b -> b >= 0;
    private final IntValidator negative = positive.negate();
    private final IntValidator even = b -> b % 2 == 0;
    private final IntValidator odd = even.negate();
    private final IntValidator positiveAndEven = positive.and(even);
    private final IntValidator negativeOrOdd = negative.or(odd);

    @Test
    void testValidatorWithValidData() {
        positive.validate(0);
        positive.validate(100);

        negative.validate(-1);
        negative.validate(-69);

        even.validate(1234);
        even.validate(0);

        odd.validate(1);
        odd.validate(696969);

        positiveAndEven.validate(2);
        positiveAndEven.validate(1234);

        negativeOrOdd.validate(-1);
        negativeOrOdd.validate(-2);
        negativeOrOdd.validate(1);
        negativeOrOdd.validate(3);
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            ()->positive.validate(-1),
            ()->positive.validate(-69),
            ()->negative.validate(0),
            ()->negative.validate(100),
            ()->even.validate(1),
            ()->even.validate(696969),
            ()->odd.validate(1234),
            ()->odd.validate(0),
            ()->negativeOrOdd.validate(2),
            ()->negativeOrOdd.validate(1234),
            ()->positiveAndEven.validate(-1),
            ()->positiveAndEven.validate(-2),
            ()->positiveAndEven.validate(1),
            ()->positiveAndEven.validate(3)
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }

}
