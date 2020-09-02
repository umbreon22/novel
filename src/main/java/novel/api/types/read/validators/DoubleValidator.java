package novel.api.types.read.validators;

import java.util.function.DoublePredicate;

@FunctionalInterface
public interface DoubleValidator extends DoublePredicate {

    default double validate(double data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    default String category() {
        return "double";
    }

    default DoubleValidator negate() {
        return transform(DoublePredicate.super.negate());
    }

    default DoubleValidator and(DoublePredicate other) {
        return transform(DoublePredicate.super.and(other));
    }

    default DoubleValidator or(DoublePredicate other) {
        return transform(DoublePredicate.super.or(other));
    }

    private DoubleValidator transform(DoublePredicate predicate) {
        return predicate::test;
    }

}
