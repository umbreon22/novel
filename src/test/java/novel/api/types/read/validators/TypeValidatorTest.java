package novel.api.types.read.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TypeValidatorTest {

    interface StringValidator extends TypeValidator<String>{}
    private final StringValidator empty = String::isEmpty;
    private final TypeValidator<String> notEmpty = empty.negate();
    private final StringValidator evenLength = string -> string.length() % 2 == 0;
    private final TypeValidator<String> oddLength = evenLength.negate();
    private final StringValidator uppercase = string -> string.chars()
                                                              .filter(c->!Character.isWhitespace(c))
                                                              .allMatch(Character::isUpperCase);
    private final StringValidator lowercase = string -> string.chars()
                                                             .filter(c->!Character.isWhitespace(c))
                                                             .allMatch(Character::isLowerCase);
    private final TypeValidator<String> evenAndUpper = evenLength.and(uppercase);
    private final TypeValidator<String> lowercaseOrOdd = oddLength.or(lowercase);

    static class UserDummy {
        String name;
        public UserDummy(String name) {
            this.name = name;
        }
    }

    interface UserDummyValidator extends TypeValidator<UserDummy> {
        @Override
        default UserDummyValidator or(Predicate<? super UserDummy> other) {
            return this;
        }
    }

    @Test
    void testDummyUser() {
        UserDummyValidator isValidlyNamed = user -> notEmpty.test(user.name);
        isValidlyNamed.validate(new UserDummy("dummy"));//should be good
        Assertions.assertThrows(DataValidationException.class, ()->isValidlyNamed.validate(new UserDummy("")));
        UserDummyValidator walao = isValidlyNamed.or(Objects::isNull);
    }

    @Test
    void testValidatorWithValidData() {
        empty.validate("");
        notEmpty.validate("Yes");
        evenLength.validate("even");
        oddLength.validate("odD");
        uppercase.validate("YES");
        lowercase.validate("yes");
        evenAndUpper.validate("EVEN");
        lowercaseOrOdd.validate("ODD");
        lowercaseOrOdd.validate("lower case");
    }

    @Test
    void testValidatorWithInvalidData() {
        Stream<Executable> throwsCases = Stream.of(
            ()->empty.validate("NOT EMPTY"),
            ()->notEmpty.validate(""),
            ()->evenLength.validate("odd"),
            ()->oddLength.validate("even"),
            ()->uppercase.validate("nO"),
            ()->uppercase.validate("no"),
            ()->lowercase.validate("No"),
            ()->lowercase.validate("NO"),
            ()->evenAndUpper.validate("even"),
            ()->evenAndUpper.validate("ODD"),
            ()->lowercaseOrOdd.validate("EVEN")
        );
        throwsCases.forEach(
            throwCase->Assertions.assertThrows(DataValidationException.class, throwCase)
        );
    }

}
