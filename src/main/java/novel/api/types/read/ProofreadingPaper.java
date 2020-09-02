package novel.api.types.read;

import novel.api.types.read.validators.*;

import java.util.Objects;

/**
 * A {@link DataPaper} with validation support!
 */
public interface ProofreadingPaper extends DataPaper {

    private String nullValidatorMessage(String caller) {
        return String.format("%s validator cannot be null.", caller);
    }

    private <T> T validateValidator(String caller, T validator) {
        return Objects.requireNonNull(validator, nullValidatorMessage(caller));
    }

    default int ints(IntValidator intValidator) {
        return validateValidator("ints", intValidator).validate(this.ints());
    }

    default int[] ints(int arrayLength, IntValidator intValidator) {
        validateValidator("ints[]", intValidator);
        int[] ret = new int[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = intValidator.validate(ints());
        }
        return ret;
    }

    default short shorts(ShortValidator shortValidator) {
        return validateValidator("shorts", shortValidator).validate(this.shorts());
    }

    default short[] shorts(int arrayLength, ShortValidator shortValidator) {
        validateValidator("shorts[]", shortValidator);
        short[] ret = new short[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = shortValidator.validate(shorts());
        }
        return ret;
    }

    default long longs(LongValidator longValidator) {
        return validateValidator("longs", longValidator).validate(this.longs());
    }

    default long[] longs(int arrayLength, LongValidator longValidator) {
        validateValidator("longs[]", longValidator);
        long[] ret = new long[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = longValidator.validate(longs());
        }
        return ret;
    }

    default String strings(TypeValidator<String> stringValidator) {
        return validateValidator("strings", stringValidator).validate(this.strings());
    }

    default String[] strings(int arrayLength, TypeValidator<String> stringValidator) {
        validateValidator("strings[]", stringValidator);
        String[] ret = new String[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = stringValidator.validate(strings());
        }
        return ret;
    }


    default boolean bools(BooleanValidator booleanValidator) {
        return validateValidator("booleanValidator", booleanValidator).validate(this.bools());
    }

    default boolean[] bools(int arrayLength, BooleanValidator booleanValidator) {
        validateValidator("bools[]", booleanValidator);
        boolean[] ret = new boolean[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = booleanValidator.validate(bools());
        }
        return ret;
    }

    default double doubles(DoubleValidator doubleValidator) {
        return validateValidator("doubles", doubleValidator).validate(this.doubles());
    }

    default double[] doubles(int arrayLength, DoubleValidator doubleValidator) {
        validateValidator("doubles[]", doubleValidator);
        double[] ret = new double[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = doubleValidator.validate(doubles());
        }
        return ret;
    }

    default float floats(FloatValidator floatValidator) {
        return validateValidator("floats", floatValidator).validate(this.floats());
    }

    default float[] floats(int arrayLength, FloatValidator floatValidator) {
        validateValidator("floats[]", floatValidator);
        float[] ret = new float[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = floatValidator.validate(floats());
        }
        return ret;
    }

    default byte bytes(ByteValidator byteValidator) {
        return validateValidator("bytes", byteValidator).validate(this.bytes());
    }

    default byte[] bytes(int arrayLength, ByteValidator byteValidator) {
        validateValidator("bytes[]", byteValidator);
        byte[] ret = new byte[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = byteValidator.validate(bytes());
        }
        return ret;
    }

    default char chars(CharValidator charValidator) {
        return validateValidator("chars", charValidator).validate(this.chars());
    }

    default char[] chars(int arrayLength, CharValidator charValidator) {
        validateValidator("chars[]", charValidator);
        char[] ret = new char[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = charValidator.validate(this.chars());
        }
        return ret;
    }

    default <T> T objects(ObjectDataReader<T> reader, TypeValidator<T> typeValidator) throws DataPaperReadException {
        return validateValidator("objects", typeValidator).validate(reader.read(this));
    }

    @SuppressWarnings("unchecked")
    default <T> T[] objects(ObjectDataReader<? extends T> reader, int arrayLength, TypeValidator<T> typeValidator) {
        validateValidator("objects[]", typeValidator);
        T[] t = (T[]) new Object[arrayLength];
        for(int i = 0; i < arrayLength; i++) {
            t[i] = typeValidator.validate(objects(reader));
        }
        return t;
    }

    void skip(int byteSize);
}
