package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

/**
 * Created on 8/27/2020.
 */
public class CharValidatorTest {

    private final CharValidator whitespace = Character::isWhitespace;
    private final CharValidator lowercase = Character::isLowerCase;
    private final CharValidator uppercase = lowercase.negate().and(Character::isUpperCase);
    private final CharValidator lowercaseOrWhitespace = lowercase.or(whitespace);
    private final CharValidator uppercaseAndEven = uppercase.and(c->c%2==0);

    @Test
    void testValidatorWithValidData() {
        whitespace.validate(' ');
        lowercase.validate('a');
        uppercase.validate('A');
        lowercaseOrWhitespace.validate(' ');
        lowercaseOrWhitespace.validate('b');
        int evenUpper = (char)66;//'B'
        uppercaseAndEven.validate(evenUpper);
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            () -> whitespace.validate('z'),
            () -> lowercase.validate('B'),
            () -> uppercase.validate('b'),
            () -> lowercaseOrWhitespace.validate('B'),
            () -> {
                int oddUpper = (char)65;//'A'
                uppercaseAndEven.validate(oddUpper);
            },
            () -> {
                int evenLower = (char) 98;//'b'
                uppercaseAndEven.validate(evenLower);
            }
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }


}
