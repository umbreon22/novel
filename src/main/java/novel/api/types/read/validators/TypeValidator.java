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

    private TypeValidator<T> transform(Predicate<? super T> predicate) {
        return Objects.requireNonNull(predicate, "transformed predicate cannot be null.")::test;
    }

}
