package novel.api.types.read.validators;

import java.util.Objects;
import java.util.function.LongPredicate;

@FunctionalInterface
public interface LongValidator extends LongPredicate {

    default long validate(long data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    default String category() {
        return "long";
    }

    default LongValidator negate() {
        return transform(LongPredicate.super.negate());
    }

    default LongValidator and(LongPredicate other) {
        return transform(LongPredicate.super.and(other));
    }

    default LongValidator or(LongPredicate other) {
        return transform(LongPredicate.super.or(other));
    }

    private LongValidator transform(LongPredicate predicate) {
        return Objects.requireNonNull(predicate)::test;
    }
}
