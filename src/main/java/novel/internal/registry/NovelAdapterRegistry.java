package novel.internal.registry;

import novel.api.AdapterRepository;
import novel.api.Novel;
import novel.api.Policies;
import novel.internal.factories.DefaultAdapterFactories;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.writers.ObjectDataWriter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class NovelAdapterRegistry implements AdapterRepository {

    private final ObjectAdapterRegistry adapterRegistry;
    private final Policies policies;

    public NovelAdapterRegistry(Novel parent, Policies policies, Set<AdapterFactory> additionalFactories, Map<Class<?>, ObjectDataAdapter<?>> additionalAdapters, Map<Class<?>, ObjectDataWriter<?>> additionalWriters, Map<Class<?>, ObjectDataReader<?>> additionalReaders) {
        this.adapterRegistry = new ObjectAdapterRegistry(parent);
        this.policies = policies;
        registerPrimitives();
        registerSpecialTypes();
        if(additionalFactories != null && !additionalFactories.isEmpty()) {
            additionalFactories.forEach(this.adapterRegistry::registerLast);
        }
        DefaultAdapterFactories.REQUIRED_FACTORIES.forEach(this.adapterRegistry::registerLast);
        if(additionalAdapters != null) {
            //noinspection rawtypes
            additionalAdapters.forEach((BiConsumer<Class, ObjectDataAdapter<?>>) this::register);
        }
        if(additionalReaders != null) {
            //noinspection rawtypes
            additionalReaders.forEach((BiConsumer<Class, ObjectDataReader<?>>) this::register);
        }
        if(additionalWriters != null) {
            //noinspection rawtypes
            additionalWriters.forEach((BiConsumer<Class, ObjectDataWriter<?>>) this::register);
        }
    }

    NovelAdapterRegistry(Novel parent) {
        this(parent, Policies.defaultPolicies(), null, null, null, null);
    }

    /**
     * Here we register the non-negotiable adapter contracts for primitives.
     */
    private void registerPrimitives() {
        registerPrimitive(byte.class,     Byte.class,       DataPaper::bytes,   DataPen::bytes);
        registerPrimitive(boolean.class,  Boolean.class,    DataPaper::bools,   DataPen::bools);
        registerPrimitive(short.class,    Short.class,      DataPaper::shorts,  DataPen::shorts);
        registerPrimitive(int.class,      Integer.class,    DataPaper::ints,    DataPen::ints);
        registerPrimitive(long.class,     Long.class,       DataPaper::longs,   DataPen::longs);
        registerPrimitive(float.class,    Float.class,      DataPaper::floats,  DataPen::floats);
        registerPrimitive(double.class,   Double.class,     DataPaper::doubles, DataPen::doubles);
        registerPrimitive(char.class,     Character.class,  DataPaper::chars,   DataPen::chars);
    }

    /**
     * Here we register the non-negotiable adapter contracts for non-primitives.
     */
    private void registerSpecialTypes() {
        register(DataPaper::strings, DataPen::strings, CharSequence.class);
        register(DataPaper::strings, DataPen::strings, String.class);
    }

    private <Boxed> void registerPrimitive(Class<Boxed> primitiveClass, Class<Boxed> boxedClass, ObjectDataReader<Boxed> reader, ObjectDataWriter<Boxed> writer) {
        var adapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        register(primitiveClass, adapter);
        register(boxedClass, adapter);
    }

    @Override
    public <T> ObjectDataReader<T> reader(TypeToken<T> token) {
        return adapterRegistry.get(token).asReader();
    }

    @Override
    public <T> ObjectDataWriter<T> writer(TypeToken<T> token) {
        return adapterRegistry.get(token).asWriter();
    }

    public <T> ObjectDataAdapter<T> adapter(TypeToken<T> token) {
        return adapterRegistry.get(token);
    }

    <T> void register(Class<T> clazz, ObjectDataReader<T> reader) {
        register(TypeToken.get(clazz), reader);
    }

    <T> void register(Class<T> clazz, ObjectDataWriter<T> writer) {
        register(TypeToken.get(clazz), writer);
    }

    <T> void register(Class<T> clazz, ObjectDataAdapter<T> adapter) {
        register(TypeToken.get(clazz), adapter);
    }

    <T> void register(TypeToken<T> token, ObjectDataReader<T> reader) {
        ObjectDataWriter<T> writer = writer(token);
        ObjectDataAdapter<T> wrappedAdapter;
        if(writer != null && policies.shouldMergeReadersAndWriters()) {
            wrappedAdapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        } else {
            wrappedAdapter = ObjectDataAdapter.createReadOnly(token, reader);
        }
        adapterRegistry.register(token, wrappedAdapter);
    }

    <T> void register(TypeToken<T> token, ObjectDataWriter<T> writer) {
        ObjectDataReader<T> reader = reader(token);
        ObjectDataAdapter<T> wrappedAdapter;
        if(reader != null && policies.shouldMergeReadersAndWriters()) {
            wrappedAdapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        } else {
            wrappedAdapter = ObjectDataAdapter.createWriteOnly(token, writer);
        }
        adapterRegistry.register(token, wrappedAdapter);
    }

    <T> void register(TypeToken<T> token, ObjectDataAdapter<T> adapter) {
        adapterRegistry.register(token, adapter);
    }

    <T> void register(ObjectDataReader<T> reader, ObjectDataWriter<T> writer, Class<T> clazz) {
        register(TypeToken.get(clazz), ObjectDataAdapter.wrappedAdapter(reader, writer));
    }

    <T> void register(TypeToken<T> token, ObjectDataReader<T> reader, ObjectDataWriter<T> writer) {
        register(token, ObjectDataAdapter.wrappedAdapter(reader, writer));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NovelAdapterRegistry that = (NovelAdapterRegistry) o;
        return Objects.equals(adapterRegistry, that.adapterRegistry) &&
                Objects.equals(policies, that.policies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adapterRegistry, policies);
    }

}
