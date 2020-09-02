package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created on 9/2/2020.
 */
public class ThrowableValidatorTest {

    @SuppressWarnings("ConstantConditions")
    ThrowableValidator<String> throwsNpe = str -> {
        String nullStr = null;
        return nullStr.equals(str);
    };

    ThrowableValidator<String> noThrow = str -> true;
    TypeValidator<String> positiveIntString = str -> Integer.parseInt(str, 10) > 0;
    ThrowableValidator<String> sometimesThrowsNumberFormat = noThrow.and(positiveIntString);
    ThrowableValidator<String> throwsNumberFormatOrNpe = sometimesThrowsNumberFormat.or(throwsNpe);//weird case.

    @Test
    void test() {
        throwsNpe.validate("anyString", NullPointerException.class);
        sometimesThrowsNumberFormat.validate("notAnInteger", NumberFormatException.class);
        sometimesThrowsNumberFormat.validate("anything", Throwable.class);
        Assertions.assertThrows(DataValidationException.class, ()->noThrow.validate("anything", Throwable.class));
        Assertions.assertThrows(DataValidationException.class, ()->sometimesThrowsNumberFormat.validate("anything", NullPointerException.class));
        Assertions.assertThrows(DataValidationException.class, ()->sometimesThrowsNumberFormat.validate("1", NumberFormatException.class));
        throwsNumberFormatOrNpe.validate("-1", NullPointerException.class);
        throwsNumberFormatOrNpe.validate("no null ptr", NumberFormatException.class);
        ThrowableValidator<String> nowThrowableValidator = positiveIntString::test;
        nowThrowableValidator.negate().validate("1", null);
    }

}
