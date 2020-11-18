package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.NovelableWrapper;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;

import java.lang.reflect.Type;

public class NovelWrapperAdapterFactory extends AdapterFactory {

    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if(!NovelableWrapper.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type elementType = $Gson$Types.getElementType(type, rawType, NovelableWrapper.class);
        ObjectDataAdapter<?> elementAdapter = novel.contents().adapter(TypeToken.get(elementType));
        @SuppressWarnings({"unchecked", "rawtypes"})
        ObjectDataAdapter<T> result = new Adapter(elementAdapter);
        return result;
    }

    private static final class Adapter<E> implements ObjectDataAdapter<NovelableWrapper<E>> {

        private final ObjectDataAdapter<E> elementAdapter;
        private Adapter(ObjectDataAdapter<E> elementAdapter) {
            this.elementAdapter = elementAdapter;
        }

        @SuppressWarnings("unchecked")
        @Override
        public NovelableWrapper<E> read(DataPaper paper) {
            return (NovelableWrapper<E>) NovelableWrapper.of(elementAdapter.read(paper));
        }

        @Override
        public void write(DataPen pen, NovelableWrapper<E> wrapper) {
            elementAdapter.write(pen, wrapper.unwrap());
        }
    }
}
