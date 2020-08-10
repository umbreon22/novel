package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.DataPenWriteException;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.writers.ObjectDataWriter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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
             return new ArrayDataAdapter(novel, typeToken.getRawType(), $Gson$Types.getRawType(componentType));
         } else {
            return new ObjectArrayDataAdapter(novel, $Gson$Types.getRawType(componentType));
        }
    }

    public <T> ObjectDataAdapter<T> createHandled(Novel novel, TypeToken<T> typeToken) {
        if (!typeToken.isArray()) {
            return null;
        }
        Class<?> componentType = (Class<?>) $Gson$Types.getArrayComponentType(typeToken.getType());
        return new ArrayDataAdapter(novel, typeToken.getRawType(), $Gson$Types.getRawType(componentType));
    }

    private final static class ArrayDataAdapter<ArrayType, ComponentType> implements ObjectDataAdapter<ArrayType> {

        private final Class<ComponentType> componentType;
        private final ObjectDataWriter<ComponentType> componentWriter;
        private final ObjectDataReader<ComponentType> componentReader;
        private final MethodHandle arrayConstructor;
        private final MethodHandle arrayLengthHandle;
        private final MethodHandle arrayElementGetter;
        private final MethodHandle arrayElementSetter;

        ArrayDataAdapter(Novel novel, Class<ArrayType> arrayType, Class<ComponentType> componentType) {
            this.componentType = componentType;
            this.arrayConstructor = MethodHandles.arrayConstructor(arrayType);
            this.arrayLengthHandle = MethodHandles.arrayLength(arrayType);
            this.arrayElementGetter = MethodHandles.arrayElementGetter(arrayType);
            this.arrayElementSetter = MethodHandles.arrayElementSetter(arrayType);
            var registry = novel.contents();
            this.componentReader = registry.reader(componentType);
            this.componentWriter = registry.writer(componentType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public ArrayType read(DataPaper paper) {
            int length = paper.ints();
            Object newArray;
            try {
                newArray = arrayConstructor.invoke(length);
                for(int i = 0; i < length; i++) {
                    arrayElementSetter.invoke(newArray, i, componentReader.read(paper));
                }
            } catch (Throwable throwable) {
                //This seems dangerous, as a read could've already began...
                //Let's throw an exception here instead.
                //newArray = readArrayReflective(paper, length);
                throw new DataPaperReadException("Could not read array because " + throwable.getMessage());
            }
            return (ArrayType) newArray;
        }

        private Object readArrayReflective(DataPaper paper, int length) {
            Object newArray = Array.newInstance(componentType, length);
            for(int i = 0; i < length; i++) {
                Array.set(newArray, i, componentReader.read(paper));
            }
            return newArray;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(DataPen<?> pen, ArrayType array) {
            try {
                int length = (int) arrayLengthHandle.invoke(array);
                pen.ints(length);
                for(int i = 0; i < length; i++) {
                    ComponentType element = (ComponentType) arrayElementGetter.invoke(array, i);
                    componentWriter.write(pen, element);
                }
            } catch (Throwable throwable) {
                //This seems dangerous, as a write could've already began...
                //Let's throw an exception here instead.
                //writeArrayReflective(pen, array);
                throw new DataPenWriteException("Could not write array because " + throwable.getMessage());
            }
        }

        @SuppressWarnings("unchecked")
        private void writeArrayReflective(DataPen<?> pen, ArrayType array) {
            int length = Array.getLength(array);
            pen.ints(length);
            for(int i = 0; i < length; i++) {
                ComponentType element = (ComponentType) Array.get(array, i);
                componentWriter.write(pen, element);
            }
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

        @Override
        public void write(DataPen<?> pen, ComponentType[] array) {
            pen.ints(array.length);
            for (ComponentType type : array) {
                componentWriter.write(pen, type);
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
