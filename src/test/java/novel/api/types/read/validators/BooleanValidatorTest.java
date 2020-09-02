package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created on 8/27/2020.
 */
public class BooleanValidatorTest {

    private final BooleanValidator isTrue = b->b;
    private final BooleanValidator isFalse = isTrue.negate();
    private final BooleanValidator isTrueOrFalse = isTrue.or(isFalse);
    private final BooleanValidator isNull = Objects::isNull;
    private final BooleanValidator isTrueOrNull = isNull.or(isTrue);//the other way around could NPE!

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    private final BooleanValidator isTrueAndFacts = isTrue.and(Predicate.not("facts"::equals));

    @Test
    void testValidatorWithValidData() {
        isTrue.validate(true);
        isFalse.validate(false);
        isTrueOrFalse.validate(true);
        isTrueOrFalse.validate(false);
        isTrueOrNull.validate(true);
        isTrueOrNull.validate(null);
        isNull.validate(null);
        isTrueAndFacts.validate(true);
    }

    @Test
    void testValidatorWithInvalidData() {
        Assertions.assertThrows(DataValidationException.class, () -> isTrue.validate(false));
        Assertions.assertThrows(DataValidationException.class, () -> isFalse.validate(true));
        Assertions.assertThrows(DataValidationException.class, () -> isTrueOrNull.validate(false));
        Assertions.assertThrows(DataValidationException.class, () -> isNull.validate(false));
        Assertions.assertThrows(DataValidationException.class, () -> isTrueAndFacts.validate(false));
    }

}
