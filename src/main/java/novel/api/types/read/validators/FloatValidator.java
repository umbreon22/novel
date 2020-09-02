package novel.api.types.read.validators;

import java.util.function.DoublePredicate;

public interface FloatValidator extends DoubleValidator {

    default float validate(float data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    @Override
    default double validate(double data) throws DataValidationException {
        return validate((float) data);
    }

    @Override
    default String category() {
        return "float";
    }

    @Override
    default FloatValidator negate() {
        return transform(DoubleValidator.super.negate());
    }

    @Override
    default FloatValidator and(DoublePredicate other) {
        return transform(DoubleValidator.super.and(other));
    }

    @Override
    default FloatValidator or(DoublePredicate other) {
        return transform(DoubleValidator.super.or(other));
    }

    /**
     * Calling transform will not preserve any overridden methods.
     */
    private FloatValidator transform(DoublePredicate predicate) {
        return predicate::test;
    }
}
