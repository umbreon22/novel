package novel.api.types.adapt;

import novel.api.types.read.Readable;
import novel.api.types.write.Writeable;

/**
 * A Novelable object can be read and written by {@link novel.api.Novel}.
 * @see Readable
 * @see Writeable
 */
public interface Novelable extends Readable, Writeable {}
