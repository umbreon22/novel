package novel.internal.reflective.constructor;

import novel.api.types.read.DataPaper;

public class PaperConstructor<T> extends UnsafeInstanceConstructor {

    public PaperConstructor(Class<T> rawType) {
        super(rawType, rawType, DataPaper.class);
    }

}