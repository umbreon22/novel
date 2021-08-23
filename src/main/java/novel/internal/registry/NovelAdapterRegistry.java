package novel.internal.registry;

import novel.api.AdapterRepository;
import novel.api.Novel;
import novel.api.Policies;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.write.pens.DataPen;
import novel.api.types.write.writers.ObjectDataWriter;
import novel.internal.adapters.TypeTokenAdapter;

import java.util.Objects;

public class NovelAdapterRegistry implements AdapterRepository {

    private final ObjectAdapterRegistry adapterRegistry;
    private final Policies policies;

    public NovelAdapterRegistry(Novel parent, Policies policies) {
        this.adapterRegistry = new ObjectAdapterRegistry(parent);
        this.policies = policies;
        registerPrimitives();
        registerSpecialTypes();
    }

    NovelAdapterRegistry(Novel parent) {
        this(parent, Policies.withDefaults());
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
        register(TypeToken.class, new TypeTokenAdapter());
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

    public <T> void register(Class<T> clazz, ObjectDataReader<T> reader) {
        register(TypeToken.get(clazz), reader);
    }

    public <T> void register(Class<T> clazz, ObjectDataWriter<T> writer) {
        register(TypeToken.get(clazz), writer);
    }

    public <T> void register(Class<T> clazz, ObjectDataAdapter<T> adapter) {
        register(TypeToken.get(clazz), adapter);
    }

    public <T> void register(TypeToken<T> token, ObjectDataReader<T> reader) {
        ObjectDataWriter<T> writer = writer(token);
        ObjectDataAdapter<T> wrappedAdapter;
        if(writer != null && policies.shouldMergeReadersAndWriters()) {
            wrappedAdapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        } else {
            wrappedAdapter = ObjectDataAdapter.createReadOnly(token, reader);
        }
        adapterRegistry.register(token, wrappedAdapter);
    }

    public <T> void register(TypeToken<T> token, ObjectDataWriter<T> writer) {
        ObjectDataReader<T> reader = reader(token);
        ObjectDataAdapter<T> wrappedAdapter;
        if(reader != null && policies.shouldMergeReadersAndWriters()) {
            wrappedAdapter = ObjectDataAdapter.wrappedAdapter(reader, writer);
        } else {
            wrappedAdapter = ObjectDataAdapter.createWriteOnly(token, writer);
        }
        adapterRegistry.register(token, wrappedAdapter);
    }

    public <T> void register(TypeToken<T> token, ObjectDataAdapter<T> adapter) {
        adapterRegistry.register(token, adapter);
    }

    public <T> void register(ObjectDataReader<T> reader, ObjectDataWriter<T> writer, Class<T> clazz) {
        register(TypeToken.get(clazz), ObjectDataAdapter.wrappedAdapter(reader, writer));
    }

    public <T> void register(TypeToken<T> token, ObjectDataReader<T> reader, ObjectDataWriter<T> writer) {
        register(token, ObjectDataAdapter.wrappedAdapter(reader, writer));
    }

    public void register(AdapterFactory factory) {
        this.adapterRegistry.registerLast(factory);
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
