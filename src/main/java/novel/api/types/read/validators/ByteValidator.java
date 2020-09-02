package novel.api.types.read.validators;

import java.util.function.IntPredicate;

public interface ByteValidator extends IntValidator {

    default byte validate(byte data) throws DataValidationException {
        if(!test(data)) {
            throw new DataValidationException(category(), data);
        }
        return data;
    }

    @Override
    default int validate(int data) throws DataValidationException {
        return validate((byte) data);
    }

    @Override
    default String category() {
        return "byte";
    }

    @Override
    default ByteValidator negate() {
        return transform(IntValidator.super.negate());
    }

    @Override
    default ByteValidator and(IntPredicate other) {
        return transform(IntValidator.super.and(other));
    }

    @Override
    default ByteValidator or(IntPredicate other) {
        return transform(IntValidator.super.or(other));
    }

    /**
     * Calling transform will not preserve any changes to {@link #validate(byte)}
     */
    private ByteValidator transform(IntPredicate predicate) {
        return predicate::test;
    }

}
