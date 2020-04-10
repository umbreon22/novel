package novel.api.types.write;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Novel's data output interface. You write on paper with a pen!
 * @param <Pen> a class extending or implementing {@link DataPen <Pen>}
 */
public interface DataPen<Pen extends DataPen<Pen>> {

    /**
     * Writes a boolean.
     * @param b a boolean
     * @return {@link #parameterizedThis()}
     */
    Pen bools(boolean b);

    /*
     * Writes booleans.
     * @param booleans a {@link Iterable<Boolean>}
     * @return {@link #parameterizedThis()}
     */
    /*
    default Pen bools(Iterable<Boolean> booleans) {//todo do we want this.. prob not?
        for(boolean b : booleans) bools(b);
        return parameterizedThis();
    }*/

    /**
     * Writes booleans.
     * @param booleans a varargs array of booleans
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(boolean... booleans) {
        for(boolean b : booleans) bools(b);
        return parameterizedThis();
    }

    /**
     * @see #bools(boolean)
     * @return {@link #parameterizedThis()}
     */
    default Pen bools(Supplier<Boolean> booleans) {
        return bools(booleans.get());
    }

    /**
     * Writes an int.
     * @param i an int
     * @return {@link #parameterizedThis()}
     */
    Pen ints(int i);

    /**
     * Writes ints.
     * @param ints a varargs array of ints.
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(int... ints) {
        for(int i : ints) ints(i);
        return parameterizedThis();
    }

    /**
     * @see #ints(int)
     * @return {@link #parameterizedThis()}
     */
    default Pen ints(Supplier<Integer> ints) {
        return ints(ints.get());
    }

    /**
     * Writes a long.
     * @param l a long
     * @return {@link #parameterizedThis()}
     */
    Pen longs(long l);

    /**
     * Writes longs.
     * @param longs a varargs array of longs
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(long... longs) {
        for(long l : longs) longs(l);
        return parameterizedThis();
    }

    /**
     * @see #longs(long)
     * @return {@link #parameterizedThis()}
     */
    default Pen longs(Supplier<Long> longs) {
        return longs(longs.get());
    }

    /**
     * Writes a short.
     * @param s a short
     * @return {@link #parameterizedThis()}
     */
    Pen shorts(short s);

    /**
     * Writes shorts.
     * @param shorts a varargs array of shorts
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(short... shorts) {
        for(short s : shorts) shorts(s);
        return parameterizedThis();
    }

    /**
     * @see #shorts(short)
     * @return {@link #parameterizedThis()}
     */
    default Pen shorts(Supplier<Short> shorts) {
        return shorts(shorts.get());
    }

    /**
     * Writes a float.
     * @param f a float
     * @return {@link #parameterizedThis()}
     */
    Pen floats(float f);

    /**
     * Writes floats.
     * @param floats a varargs array of floats
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(float... floats) {
        for(float f : floats) floats(f);
        return parameterizedThis();
    }

    /**
     * @see #floats(float)
     * @return {@link #parameterizedThis()}
     */
    default Pen floats(Supplier<Float> floats) {
        return floats(floats.get());
    }

    /**
     * Writes a double.
     * @param d a double
     * @return {@link #parameterizedThis()}
     */
    Pen doubles(double d);

    /**
     * Writes doubles.
     * @param doubles a varargs array of doubles
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(double... doubles) {
        for(double d : doubles) doubles(d);
        return parameterizedThis();
    }

    /**
     * @see #doubles(double)
     * @return {@link #parameterizedThis()}
     */
    default Pen doubles(Supplier<Double> doubles) {
        return doubles(doubles.get());
    }

    /**
     * Writes a {@link CharSequence}.
     * @param str a {@link CharSequence}
     * @return {@link #parameterizedThis()}
     */
    Pen strings(CharSequence str);

    /**
     * Writes {@link CharSequence}s.
     * @param strings a varargs array of {@link CharSequence}s
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(CharSequence... strings) {
        for(CharSequence str : strings) strings(str);
        return parameterizedThis();
    }

    /**
     * @see #strings
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Supplier<CharSequence> string) {
        return strings(string.get());
    }

    /**
     * Writes an object as a String.
     * Default implementation uses {@link Objects#toString(Object)} and should be null safe.
     * @see #strings(CharSequence)
     * @param toStringMe an object
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Object toStringMe) {
        strings(Objects.toString(toStringMe));
        return parameterizedThis();
    }

    /**
     * Writes an varags object array as {@link String}s.
     * @see #strings(Object)
     * @param toStringMe an varargs array of objects to write as string
     * @return {@link #parameterizedThis()}
     */
    default Pen strings(Object... toStringMe) {
        for(Object object : toStringMe) {
            strings(object);
        }
        return parameterizedThis();
    }

    /**
     * Writes a char.
     * @param c a char
     * @return {@link #parameterizedThis()}
     */
    Pen chars(char c);

    /**
     * Writes chars.
     * @param chars a varargs array of chars
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(char... chars) {
        for(char c : chars) chars(c);
        return parameterizedThis();
    }

    /**
     * @see #chars
     * @return {@link #parameterizedThis()}
     */
    default Pen chars(Supplier<Character> chars) {
        return chars(chars.get());
    }

    /**
     * Writes a byte.
     * @param b a byte
     * @return {@link #parameterizedThis()}
     */
    Pen bytes(byte b);

    /**
     * Writes bytes.
     * @param bytes a varargs array of bytes
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(byte... bytes) {
        for(byte b : bytes) bytes(b);
        return parameterizedThis();
    }

    /**
     * @see #bytes(byte)
     * @return {@link #parameterizedThis()}
     */
    default Pen bytes(Supplier<Byte> bytes) {
        return bytes(bytes.get());
    }

    /**
     * Writes an object via it's self defined {@link AutoWriteable#write(DataPen)} method.
     * @param object a {@link AutoWriteable} object
     * @return {@link #parameterizedThis()}
     */
    default Pen objects(AutoWriteable object) {
        object.write(this);
        return parameterizedThis();
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@link #parameterizedThis()}
     */
    default Pen objects(AutoWriteable... objects) {
        for(AutoWriteable w : objects) objects(w);
        return parameterizedThis();
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@link #parameterizedThis()}
     */
    default Pen objects(Supplier<AutoWriteable> objects) {
        return objects(objects.get());
    }

    /**
     * Returns {@code this} cast as your defined type parameter, {@link Pen}
     * @return {@code this) cast as {@link Pen}
     */
    @SuppressWarnings("unchecked")
    private Pen parameterizedThis() {
        return (Pen) this;
    }
}
