package novel.internal.factories;

import novel.api.Novel;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.token.$Gson$Types;
import novel.api.types.token.TypeToken;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.writers.ObjectDataWriter;

import java.util.stream.Stream;

public class StreamAdapterFactory extends AdapterFactory {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> ObjectDataAdapter<T> create(Novel novel, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if(!Stream.class.isAssignableFrom(rawType)) {
            return null;
        }
        Class<?> componentType = (Class<?>) $Gson$Types.getStreamElementType(typeToken.getType(), rawType);
        return new ObjectStreamDataAdapter(novel, componentType);
    }

    /**
     * A data adapter that references the component type's reader and serializes data as a {@link Stream}.
     * Writing WILL consume the stream.
     * @param <ComponentType> the stream's component type
     */
    private final static class ObjectStreamDataAdapter<ComponentType> implements ObjectDataAdapter<Stream<ComponentType>> {

        private final ObjectDataWriter<ComponentType> componentWriter;
        private final ObjectDataReader<ComponentType> componentReader;

        ObjectStreamDataAdapter(Novel novel, Class<ComponentType> componentType) {
            var registry = novel.contents();
            this.componentReader = registry.reader(componentType);
            this.componentWriter = registry.writer(componentType);
        }

        @Override
        public Stream<ComponentType> read(DataPaper paper) {
            Stream.Builder<ComponentType> streamBuilder = Stream.builder();
            while(paper.bools()) {
                streamBuilder.add(componentReader.read(paper));
            }
            return streamBuilder.build();
        }

        @Override
        public void write(DataPen pen, Stream<ComponentType> stream) {
            stream.forEach(obj->{
                pen.bools(true);
                componentWriter.write(pen, obj);
            });
            pen.bools(false);
        }
    }

}