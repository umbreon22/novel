package novel.api.types.read;

import novel.api.Novel;
import novel.api.types.token.Tokenable;
import novel.api.types.token.TypeToken;

import java.io.Serializable;

/**
 * Objects which implement {@link Readable} can be written via {@link Novel#read(DataPaper, TypeToken)} and similar methods.
 */
public interface Readable extends Tokenable, Serializable {}
