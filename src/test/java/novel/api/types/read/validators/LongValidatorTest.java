package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

public class LongValidatorTest {

    private final LongValidator positive = b -> b >= 0;
    private final LongValidator negative = positive.negate();
    private final LongValidator even = b -> b % 2 == 0;
    private final LongValidator odd = even.negate();
    private final LongValidator positiveAndEven = positive.and(even);
    private final LongValidator negativeOrOdd = negative.or(odd);

    @Test
    void testValidatorWithValidData() {
        positive.validate(0L);
        positive.validate(100L);

        negative.validate(-1L);
        negative.validate(-69L);

        even.validate(1234L);
        even.validate(0L);

        odd.validate(1L);
        odd.validate(69696969696969L);

        positiveAndEven.validate(2L);
        positiveAndEven.validate(1234L);

        negativeOrOdd.validate(-1L);
        negativeOrOdd.validate(-2L);
        negativeOrOdd.validate(1L);
        negativeOrOdd.validate(3L);
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            ()->positive.validate(-1L),
            ()->positive.validate(-69L),
            ()->negative.validate(0L),
            ()->negative.validate(100L),
            ()->even.validate(1L),
            ()->even.validate(69696969696969L),
            ()->odd.validate(1234L),
            ()->odd.validate(0L),
            ()->negativeOrOdd.validate(2L),
            ()->negativeOrOdd.validate(1234L),
            ()->positiveAndEven.validate(-1L),
            ()->positiveAndEven.validate(-2L),
            ()->positiveAndEven.validate(1L),
            ()->positiveAndEven.validate(3L)
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }

}
