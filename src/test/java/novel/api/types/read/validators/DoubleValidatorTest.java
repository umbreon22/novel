package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

public class DoubleValidatorTest {

    private final DoubleValidator positive = b -> b >= 0;
    private final DoubleValidator negative = positive.negate();
    private final DoubleValidator even = b -> b % 2 == 0;
    private final DoubleValidator odd = even.negate();
    private final DoubleValidator positiveAndEven = positive.and(even);
    private final DoubleValidator negativeOrOdd = negative.or(odd);
    private final DoubleValidator infinity = Double::isInfinite;

    @Test
    void testValidatorWithValidData() {
        positive.validate(0d);
        positive.validate(100d);

        negative.validate(-1d);
        negative.validate(-69d);

        even.validate(1234d);
        even.validate(0d);

        odd.validate(1d);
        odd.validate(696969d);

        positiveAndEven.validate(2d);
        positiveAndEven.validate(1234d);

        negativeOrOdd.validate(-1d);
        negativeOrOdd.validate(-2d);
        negativeOrOdd.validate(1d);
        negativeOrOdd.validate(3d);

        infinity.validate(Double.POSITIVE_INFINITY);
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            ()->positive.validate(-1d),
            ()->positive.validate(-69d),
            ()->negative.validate(0d),
            ()->negative.validate(100d),
            ()->even.validate(1d),
            ()->even.validate(696969d),
            ()->odd.validate(1234d),
            ()->odd.validate(0d),
            ()->negativeOrOdd.validate(2d),
            ()->negativeOrOdd.validate(1234d),
            ()->positiveAndEven.validate(-1d),
            ()->positiveAndEven.validate(-2d),
            ()->positiveAndEven.validate(1d),
            ()->positiveAndEven.validate(3d)
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }

}
