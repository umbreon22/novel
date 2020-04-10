package novel.api.types.write;

import novel.api.Novel;
import novel.api.types.token.Tokenable;
import java.io.Serializable;

/**
 * Objects which implement {@link Writeable} can be written
 * via {@link Novel#write(DataPen, Writeable)} and similar methods.
 */
public interface Writeable extends Tokenable, Serializable {}
