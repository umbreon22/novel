package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Supplier;

public final class CollectionAdapterFactory extends AdapterFactory {

    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if(!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        ObjectDataAdapter<?> elementAdapter = novel.contents().adapter(TypeToken.get(elementType));
        Supplier<Collection<T>> constructor = CollectionSupplier.fromToken(typeToken);
        @SuppressWarnings({"unchecked", "rawtypes"})
        ObjectDataAdapter<T> result = new CollectionAdapter(elementAdapter, constructor);
        return result;
    }

    private static final class CollectionAdapter<E> implements ObjectDataAdapter<Collection<E>> {

        private final ObjectDataAdapter<E> elementAdapter;
        private final Supplier<Collection<E>> constructor;
        private CollectionAdapter(ObjectDataAdapter<E> elementAdapter, Supplier<Collection<E>> constructor) {
            this.elementAdapter = elementAdapter;
            this.constructor = constructor;
        }

        @Override
        public Collection<E> read(DataPaper paper) {
            int size = paper.ints();
            Collection<E> instance = constructor.get();
            for(int i = 0; i < size; i++) {
                instance.add(paper.objects(elementAdapter));
            }
            return instance;
        }

        @Override
        public void write(DataPen pen, Collection<E> collection) {
            pen.ints(collection.size());
            if(!collection.isEmpty()) {
                for (var element : collection) {
                    elementAdapter.write(pen, element);
                }
            }
        }
    }

}
