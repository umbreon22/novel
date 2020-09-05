package novel.api.types.read.validators;

import java.util.Objects;
import java.util.function.Predicate;

public interface TypeValidator<T> extends Predicate<T> {

    default T validate(T data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(data), data);
        }
        return data;
    }

    default String category(T data) {
        return data == null ? "data" : data.getClass().getSimpleName();
    }

    default TypeValidator<T> negate() {
        return transform(Predicate.super.negate());
    }

    default TypeValidator<T> and(Predicate<? super T> other) {
        return transform(Predicate.super.and(other));
    }

    default TypeValidator<T> or(Predicate<? super T> other) {
        return transform(Predicate.super.or(other));
    }

    /**
     * Calling transform will not preserve any overridden methods.
     */
    private TypeValidator<T> transform(Predicate<? super T> predicate) {
        return predicate::test;
    }

}
