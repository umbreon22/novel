package novel.api.types.read.validators;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface IntValidator extends IntPredicate {

    default int validate(int data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    default String category() {
        return "int";
    }

    default IntValidator negate() {
        return transform(IntPredicate.super.negate());
    }

    default IntValidator and(IntPredicate other) {
        return transform(IntPredicate.super.and(other));
    }

    default IntValidator or(IntPredicate other) {
        return transform(IntPredicate.super.or(other));
    }

    /**
     * Calling transform will not preserve any changes to {@link #validate(int)}
     */
    private IntValidator transform(IntPredicate predicate) {
        return predicate::test;
    }

}
