package novel.api.types.read.validators;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface ShortValidator extends IntValidator {

    default short validate(short data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException("short", data);
        }
        return data;
    }

    @Override
    default int validate(int data) throws DataValidationException {
        return validate((short) data);
    }
    @Override
    default ShortValidator negate() {
        return transform(IntValidator.super.negate());
    }

    @Override
    default ShortValidator and(IntPredicate other) {
        return transform(IntValidator.super.and(other));
    }

    @Override
    default ShortValidator or(IntPredicate other) {
        return transform(IntValidator.super.or(other));
    }

    /**
     * Calling transform will not preserve any changes to {@link #validate(short)}
     */
    private ShortValidator transform(IntPredicate predicate) {
        return predicate::test;
    }
}
