package novel.api.types.read.validators;

import java.util.function.Predicate;

@FunctionalInterface
public interface BooleanValidator extends TypeValidator<Boolean> {

    @Override
    default BooleanValidator negate() {
        return transform(TypeValidator.super.negate());
    }

    @Override
    default BooleanValidator and(Predicate<? super Boolean> other) {
        return transform(TypeValidator.super.and(other));
    }

    @Override
    default BooleanValidator or(Predicate<? super Boolean> other) {
        return transform(TypeValidator.super.or(other));
    }

    /**
     * Calling transform will not preserve any overridden methods.
     */
    private BooleanValidator transform(Predicate<? super Boolean> predicate) {
        return predicate::test;
    }
}
