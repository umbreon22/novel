package novel.api.types.read.validators;

import java.util.function.Predicate;

public interface ThrowableValidator<T> extends Predicate<T> {

    default <Throws extends Throwable> T validate(T data, Class<Throws> throwable) throws DataValidationException {
        try {
            test(data);
            if(throwable == null) {
                return data;
            }
        } catch (Throwable actual) {
            if(throwable.isInstance(actual)) {
                return data;
            } else {
                throw throwFor(data, throwable, actual);
            }
        }
        throw throwFor(data, throwable);
    }

    private <Throws extends Throwable> DataValidationException throwFor(T data, Class<Throws> throwable) throws DataValidationException {
        return new DataValidationException(
            throwable.getSimpleName()
            + " was not thrown for " + category(data), data
        );
    }

    private <Throws extends Throwable> DataValidationException throwFor(T data, Class<Throws> throwable, Throwable actual) throws DataValidationException {
        return new DataValidationException(
                throwable.getSimpleName()
                        + " was not thrown for " + category(data)
                        +", instead was " + actual.getClass().getSimpleName(), data
        );
    }

    default String category(T data) {
        return data == null ? "data" : data.getClass().getSimpleName();
    }

    default ThrowableValidator<T> negate() {
        return transform(Predicate.super.negate());
    }

    default ThrowableValidator<T> and(Predicate<? super T> other) {
        return transform(Predicate.super.and(other));
    }

    default ThrowableValidator<T> or(Predicate<? super T> other) {
        return transform(Predicate.super.or(other));
    }

    /**
     * Calling transform will not preserve any overridden methods.
     */
    private ThrowableValidator<T> transform(Predicate<? super T> predicate) {
        return predicate::test;
    }

}
