package novel.api.types.write.writers;

import novel.api.types.write.pens.DataPen;

/**
 * Defines how {@link T} can be written by implementing {@link #write(DataPen, Object)}
 * @param <T> an object's type parameter
 */
public interface ObjectDataWriter<T> {
    void write(DataPen pen, T object);
}
