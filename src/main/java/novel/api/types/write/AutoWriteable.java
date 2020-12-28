package novel.api.types.write;

import novel.api.types.write.pens.DataPen;

/**
 * Like an autobiography... this object writes itself!
 * Custom adapters CAN take priority over {@link AutoWriteable}s,
 * unless you explicitly call {@link #write(DataPen)} in the parent adapter.
 */
public interface AutoWriteable extends Writeable {
    /**
     * Defines a way to write the implementing class.
     * @param output a {@link DataPen}
     */
    void write(DataPen output);
}
