package novel.api.types.read.validators;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface CharValidator extends IntValidator {

    default char validate(char data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    @Override
    default int validate(int data) throws DataValidationException {
        return validate((char) data);
    }

    @Override
    default String category() {
        return "char";
    }

    @Override
    default CharValidator negate() {
        return transform(IntValidator.super.negate());
    }

    @Override
    default CharValidator and(IntPredicate other) {
        return transform(IntValidator.super.and(other));
    }

    @Override
    default CharValidator or(IntPredicate other) {
        return transform(IntValidator.super.or(other));
    }

    /**
     * Calling transform will not preserve any changes to {@link #validate(char)}
     */
    private CharValidator transform(IntPredicate predicate) {
        return predicate::test;
    }
}
