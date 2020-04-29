package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.DataPaper;
import novel.api.types.write.pens.DataPen;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Supplier;

public class MapAdapterFactory extends AdapterFactory {

    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if(!Map.class.isAssignableFrom(rawType)) {
            return null;
        }
        Class<?> rawTypeOfSrc = $Gson$Types.getRawType(type);
        Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);

        ObjectDataAdapter<?> keyAdapter = novel.contents().adapter(TypeToken.get(keyAndValueTypes[0]));
        ObjectDataAdapter<?> valueAdapter = novel.contents().adapter(TypeToken.get(keyAndValueTypes[1]));
        Supplier<Map<?,?>> constructor = MapSupplier.fromToken(typeToken);
        @SuppressWarnings({"unchecked", "rawtypes"})
        ObjectDataAdapter<T> result = (ObjectDataAdapter<T>) new MapDataAdapter(keyAdapter, valueAdapter, constructor);
        return result;
    }

    private static class MapDataAdapter<K,V> implements ObjectDataAdapter<Map<K,V>> {

        private final ObjectDataAdapter<K> keyAdapter;
        private final ObjectDataAdapter<V> valueAdapter;
        private final Supplier<Map<K,V>> constructor;
        MapDataAdapter(ObjectDataAdapter<K> keyAdapter, ObjectDataAdapter<V> valueAdapter, Supplier<Map<K,V>> constructor) {
            this.keyAdapter = keyAdapter;
            this.valueAdapter = valueAdapter;
            this.constructor = constructor;
        }

        @Override
        public Map<K,V> read(DataPaper paper) {
            int size = paper.ints();
            Map<K,V> instance = constructor.get();
            if(size > 0) {
                for (int i = 0; i < size; i++) {
                    instance.put(keyAdapter.read(paper), valueAdapter.read(paper));
                }
            }
            return instance;
        }

        @Override
        public void write(DataPen<?> pen, Map<K,V> map) {
            pen.ints(map.size());
            if(!map.isEmpty()) {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    keyAdapter.write(pen, entry.getKey());
                    valueAdapter.write(pen, entry.getValue());
                }
            }
        }

    }
}
