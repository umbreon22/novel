package novel.api.types.read;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Function;

/**
 * Novel's data input interface.
 * You read what's on the paper!
 */
public interface DataPaper {

    int ints();
    default int[] ints(int arrayLength) {
        int[] ret = new int[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = ints();
        }
        return ret;
    }

    short shorts();
    default short[] shorts(int arrayLength) {
        short[] ret = new short[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = shorts();
        }
        return ret;
    }

    long longs();
    default long[] longs(int arrayLength) {
        long[] ret = new long[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = longs();
        }
        return ret;
    }

    String strings();
    default String[] strings(int arrayLength) {
        String[] ret = new String[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = strings();
        }
        return ret;
    }


    boolean bools();
    default boolean[] bools(int arrayLength) {
        boolean[] ret = new boolean[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = bools();
        }
        return ret;
    }

    double doubles();
    default double[] doubles(int arrayLength) {
        double[] ret = new double[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = doubles();
        }
        return ret;
    }

    float floats();
    default float[] floats(int arrayLength) {
        float[] ret = new float[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = floats();
        }
        return ret;
    }

    byte bytes();
    default byte[] bytes(int arrayLength) {
        byte[] ret = new byte[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = bytes();
        }
        return ret;
    }

    char chars();
    default char[] chars(int arrayLength) {
        char[] ret = new char[arrayLength];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = chars();
        }
        return ret;
    }

    default <T> T objects(ObjectDataReader<T> reader) throws DataPaperReadException {
        return reader.read(this);
    }

    default <T> T[] objects(ObjectDataReader<? extends T> reader, T[] output) {
        int arrayLength = Objects.requireNonNull(output, "output array cannot be null").length;
        for(int i = 0; i < arrayLength; i++) {
            output[i] = objects(reader);
        }
        return output;
    }

    @SuppressWarnings("unchecked")
    default <T> T[] objects(ObjectDataReader<? extends T> reader, int length, Class<T> componentType) {
        return objects(reader, (T[]) Array.newInstance(componentType, length));
    }

    default <T> T[] objects(ObjectDataReader<? extends T> reader, int length, Function<Integer, T[]> constructor) {
        return objects(reader, constructor.apply(length));
    }

    void skip(int byteSize);
}
