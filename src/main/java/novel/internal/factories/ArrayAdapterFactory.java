package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.ObjectDataWriter;

import java.lang.reflect.Array;

public class ArrayAdapterFactory extends AdapterFactory {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        if (!typeToken.isArray()) {
            return null;
        }
        Class<?> componentType = (Class<?>) $Gson$Types.getArrayComponentType(typeToken.getType());
        if(componentType.isPrimitive()) {
            return new PrimitiveArrayDataAdapter(novel, $Gson$Types.getRawType(componentType));
        } else {
            return new ObjectArrayDataAdapter(novel, $Gson$Types.getRawType(componentType));
        }
    }

    /**
     * A data adapter that references the component type's reader and serializes data as an array.
     * @param <ComponentType> the Object in Object[]
     */
    private final static class ObjectArrayDataAdapter<ComponentType> implements ObjectDataAdapter<ComponentType[]> {

        private final Class<ComponentType> componentType;
        private final ObjectDataWriter<ComponentType> componentWriter;
        private final ObjectDataReader<ComponentType> componentReader;
        ObjectArrayDataAdapter(Novel novel, Class<ComponentType> componentType) {
            this.componentType = componentType;
            var registry = novel.contents();
            this.componentReader = registry.reader(componentType);
            this.componentWriter = registry.writer(componentType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public ComponentType[] read(DataPaper paper) {
            int length = paper.ints();
            Object newArray = Array.newInstance(componentType, length);
            for(int i = 0; i < length; i++) {
                Array.set(newArray, i, componentReader.read(paper));
            }
            return (ComponentType[]) newArray;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(DataPen<?> pen, ComponentType[] array) {
            int length = Array.getLength(array);
            pen.ints(length);
            for(int i = 0; i < length; i++) {
                ComponentType element = (ComponentType) Array.get(array, i);
                componentWriter.write(pen, element);
            }
        }
    }

    /**
     * Allows us to reference the primitive array types while still conforming to the {@link ObjectDataAdapter} contract.
     * @param <ArrayType> ex. int[]
     * @param <ComponentType> ex. int.class
     */
    private final static class PrimitiveArrayDataAdapter<ArrayType, ComponentType> implements ObjectDataAdapter<ArrayType> {

        private final Class<ComponentType> componentType;
        private final ObjectDataWriter<ComponentType> componentWriter;
        private final ObjectDataReader<ComponentType> componentReader;
        PrimitiveArrayDataAdapter(Novel novel, Class<ComponentType> componentType) {
            this.componentType = componentType;
            var registry = novel.contents();
            this.componentReader = registry.reader(componentType);
            this.componentWriter = registry.writer(componentType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public ArrayType read(DataPaper paper) {
            int length = paper.ints();
            Object newArray = Array.newInstance(componentType, length);
            for(int i = 0; i < length; i++) {
                Array.set(newArray, i, componentReader.read(paper));
            }
            return (ArrayType) newArray;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(DataPen<?> pen, ArrayType array) {
            int length = Array.getLength(array);
            pen.ints(length);
            for(int i = 0; i < length; i++) {
                ComponentType element = (ComponentType) Array.get(array, i);
                componentWriter.write(pen, element);
            }
        }
    }
}
