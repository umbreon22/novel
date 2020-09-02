package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

public class FloatValidatorTest {

    private final FloatValidator positive = b -> b >= 0;
    private final FloatValidator negative = positive.negate();
    private final FloatValidator even = b -> b % 2 == 0;
    private final FloatValidator odd = even.negate();
    private final FloatValidator positiveAndEven = positive.and(even);
    private final FloatValidator negativeOrOdd = negative.or(odd);
    private final FloatValidator infinity = Double::isInfinite;

    @Test
    void testValidatorWithValidData() {
        positive.validate(0f);
        positive.validate(100f);

        negative.validate(-1f);
        negative.validate(-69f);

        even.validate(1234f);
        even.validate(0f);

        odd.validate(1f);
        odd.validate(696969f);

        positiveAndEven.validate(2f);
        positiveAndEven.validate(1234f);

        negativeOrOdd.validate(-1f);
        negativeOrOdd.validate(-2f);
        negativeOrOdd.validate(1f);
        negativeOrOdd.validate(3f);

        double infinityAsFloat = Double.MAX_VALUE;
        DoubleValidator positiveDouble = i->i>=0;
        positiveDouble.validate(infinityAsFloat);
        infinity.validate(infinityAsFloat);
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            ()->positive.validate(-1f),
            ()->positive.validate(-69f),
            ()->negative.validate(0f),
            ()->negative.validate(100f),
            ()->even.validate(1f),
            ()->even.validate(696969f),
            ()->odd.validate(1234f),
            ()->odd.validate(0f),
            ()->negativeOrOdd.validate(2f),
            ()->negativeOrOdd.validate(1234f),
            ()->positiveAndEven.validate(-1f),
            ()->positiveAndEven.validate(-2f),
            ()->positiveAndEven.validate(1f),
            ()->positiveAndEven.validate(3f)
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }

}
