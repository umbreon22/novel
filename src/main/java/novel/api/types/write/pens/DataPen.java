package novel.api.types.write.pens;

import novel.api.types.write.AutoWriteable;
import novel.api.types.write.writers.ObjectDataWriter;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Novel's data output interface. You write on paper with a pen!
 * @param <Pen> a class extending or implementing {@link DataPen<Pen>}
 */
public interface DataPen<Pen extends DataPen<Pen>> extends
    BytePen<Pen>,
    BoolPen<Pen>,
    CharPen<Pen>,
    DoublePen<Pen>,
    FloatPen<Pen>,
    IntPen<Pen>,
    LongPen<Pen>,
    ShortPen<Pen>,
    StringPen<Pen> {

    /**
     * Writes an object via it's self defined {@link AutoWriteable#write(DataPen)} method.
     * @param object a {@link AutoWriteable} object
     * @return {@code parameterizedThis()}
     */
    default Pen objects(AutoWriteable object) {
        object.write(this);
        return parameterizedThis();
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@code parameterizedThis()}
     */
    default Pen objects(AutoWriteable... objects) {
        for(AutoWriteable w : objects) objects(w);
        return parameterizedThis();
    }

    /**
     * Writes object arrays.
     * @param objects a varargs array of object arrays.
     * @return {@code parameterizedThis()}
     */
    default Pen objects(AutoWriteable[]... objects) {
        for(AutoWriteable[] o : objects) objects(o);
        return parameterizedThis();
    }

    /**
     * @see #objects(AutoWriteable)
     * @return {@code parameterizedThis()}
     */
    default Pen objects(Supplier<AutoWriteable> objects) {
        return objects(objects.get());
    }

    /**
     * Writes an {@link Iterable} of autowriteables.
     * @param objects a {@link Iterable<AutoWriteable>}
     * @return {@code parameterizedThis()}
     */
    default Pen objects(Iterable<AutoWriteable> objects) {
        for(AutoWriteable o : objects) objects(o);
        return parameterizedThis();
    }

    /**
     * Writes an object of type {@code T} using the provided {@code writer}
     * @param object an object of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code parameterizedThis()}
     */
    default <T> Pen objects(T object, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer).write(this, object);
        return parameterizedThis();
    }

    /**
     * Writes an object array of type {@code T} using the provided {@code writer}
     * @param objects an array of objects of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code parameterizedThis()}
     */
    default <T> Pen objects(T[] objects, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer);
        for(T object : objects) {
            writer.write(this, object);
        }
        return parameterizedThis();
    }

    /**
     * Writes an {@link Iterable<T>} using the provided {@code writer}
     * @param objects an iterable of objects of type {@code T}
     * @param writer a {@link ObjectDataWriter<T>} instance
     * @param <T> {@code object}'s type
     * @return {@code parameterizedThis()}
     */
    default <T> Pen objects(Iterable<T> objects, ObjectDataWriter<T> writer) {
        Objects.requireNonNull(writer);
        for(T object : objects) {
            writer.write(this, object);
        }
        return parameterizedThis();
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
