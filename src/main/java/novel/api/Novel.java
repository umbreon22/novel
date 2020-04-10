package novel.api;

import novel.api.types.read.Audience;
import novel.api.types.write.Author;
import novel.internal.reflective.SortedFieldCollector;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.read.CannotRead;
import novel.api.types.read.DataPaper;
import novel.api.types.read.ObjectDataReader;
import novel.api.types.read.Readable;
import novel.api.types.token.TypeToken;
import novel.api.types.write.CannotWrite;
import novel.api.types.write.DataPen;
import novel.api.types.write.ObjectDataWriter;
import novel.api.types.write.Writeable;
import novel.internal.registry.NovelAdapterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Takes {@link Writeable} and {@link Readable} types and performs read/write operations
 * on a given {@link DataPen} or {@link DataPaper}.
 */
public final class Novel implements Author, Audience {

    private final FieldCollector collector;
    private final FieldCensor censor;
    private final AdapterRepository contents;

    private Novel(
        int revision,
        Iterable<Integer> illegalModifiers,
        Policies policies,
        List<AdapterFactory> additionalFactories,
        Map<Class<?>, ObjectDataAdapter<?>> additionalAdapters,
        Map<Class<?>, ObjectDataWriter<?>> additionalWriters,
        Map<Class<?>, ObjectDataReader<?>> additionalReaders
    ) {
        this.collector = SortedFieldCollector.usingFolio(this);
        this.censor = new FieldCensor(revision, illegalModifiers);//todo: poss in policies with more censor control?
        this.contents = new NovelAdapterRegistry(
        this, policies, additionalFactories, additionalAdapters, additionalWriters, additionalReaders
        );
    }

    public static Novel.Builder newBuilder() {
        return new Novel.Builder();
    }

    public static Novel newDefaultInstance() {
        return new Novel.Builder().build();
    }

    public FieldCensor fieldCensor() {
        return censor;
    }

    //todo: rethink the need for this to be public?
    public FieldCollector fieldCollector() {
        return collector;
    }

    /**
     * Provides direct access to the {@link AdapterRepository}
     * @return {@link #contents}
     */
    public AdapterRepository contents() {
        return contents;
    }

    @Override
    public <T> T read(DataPaper reader, TypeToken<T> token) {
        return contents.reader(token).read(reader);
    }

    @Override
    public <T> void write(DataPen<?> writer, T data, TypeToken<T> token) {
        contents.writer(token).write(writer, data);
    }

    @Override
    public <T extends Writeable> void write(DataPen<?> writer, T[] data, TypeToken<T[]> token) {
        contents.writer(token).write(writer, data);
    }

    @Override
    public boolean canWrite(TypeToken<?> token) {
        return !(contents.writer(token) instanceof CannotWrite);
    }

    @Override
    public boolean canRead(TypeToken<?> token) {
        return !(contents.reader(token) instanceof CannotRead);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Novel novel = (Novel) o;
        return Objects.equals(contents, novel.contents) &&
                Objects.equals(censor, novel.censor) && Objects.equals(collector, novel.collector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents, censor, collector);
    }

    public static class Builder {

        private static final Logger logger = LoggerFactory.getLogger(Builder.class);

        private final List<AdapterFactory> additionalFactories;
        private final Map<Class<?>, ObjectDataAdapter<?>> additionalAdapters;
        private final Map<Class<?>, ObjectDataWriter<?>> additionalWriters;
        private final Map<Class<?>, ObjectDataReader<?>> additionalReaders;
        private Policies policies;
        private int revision;
        private final Set<Integer> illegalModifiers;

        private Builder() {
            this.additionalFactories    = new LinkedList<>();
            this.additionalAdapters     = new HashMap<>();
            this.additionalWriters      = new HashMap<>();
            this.additionalReaders      = new HashMap<>();
            this.policies               = null;
            this.revision               = 0;
            this.illegalModifiers       = defaultIllegalModifiers();
        }

        public Builder withTransientFields(boolean allowed) {
            setFieldModifier(allowed, Modifier.TRANSIENT);
            return this;
        }

        private void setFieldModifier(boolean allowed, int modifier) {
            if(allowed) {
                illegalModifiers.remove(modifier);
            } else {
                illegalModifiers.add(modifier);
            }
        }

        public Builder withStaticFields(boolean allowed) {
            setFieldModifier(allowed, Modifier.STATIC);
            return this;
        }
        /**
         * By default, we will skip transient and static field serialization in reflective adapters.
         * @return this
         */
        private static Set<Integer> defaultIllegalModifiers() {
            int[] defaultIllegalModifiers = new int[]{Modifier.TRANSIENT, Modifier.STATIC};
            Set<Integer> modifiers = new HashSet<>(defaultIllegalModifiers.length);
            for (int modifier : defaultIllegalModifiers) {
                modifiers.add(modifier);
            }
            return modifiers;
        }

        public Builder withPolicies(Policies policies) {
            this.policies = policies;
            return this;
        }


        public <T> Builder withAdapter(ObjectDataAdapter<T> adapter, Class<? super T> clazz) {
            if (additionalAdapters.putIfAbsent(clazz, adapter) != null) {
                logger.debug("Replacing {} adapter with {}", clazz.getSimpleName(), adapter);
            }
            return this;
        }

        public <T> Builder withWriter(ObjectDataWriter<T> writer, Class<? super T> clazz) {
            if (additionalWriters.putIfAbsent(clazz, writer) != null) {
                logger.debug("Replacing {} writer with {}", clazz.getSimpleName(), writer);
            }
            return this;
        }

        public <T> Builder withReader(ObjectDataReader<T> reader, Class<? super T> clazz) {
            if (additionalReaders.putIfAbsent(clazz, reader) != null) {
                logger.debug("Replacing {} reader with {}", clazz.getSimpleName(), reader);
            }
            return this;
        }

        public <T> Builder withSuperAdapter(ObjectDataReader<T> reader, ObjectDataWriter<T> writer, Class<? super T> clazz) {
            return withSuperAdapter(ObjectDataAdapter.wrappedAdapter(reader, writer), clazz);
        }

        private <T> Builder withSuperAdapter(ObjectDataAdapter<T> adapter, Class<? super T> clazz) {
            if (clazz != null && clazz != Object.class) {
                withSuperAdapter(adapter, clazz.getSuperclass());
            }
            return withAdapter(adapter, clazz);
        }

        public <T> Builder withSuperWriter(ObjectDataWriter<T> writer, Class<? super T> clazz) {
            if (clazz != null && clazz != Object.class) {
                withSuperWriter(writer, clazz.getSuperclass());
            }
            return withWriter(writer, clazz);
        }

        public <T> Builder withSuperReader(ObjectDataReader<T> reader, Class<? super T> clazz) {
            if (clazz != null && clazz != Object.class) {
                withSuperReader(reader, clazz.getSuperclass());
            }
            return withReader(reader, clazz);
        }

        public Builder withFactory(AdapterFactory factory) {
            additionalFactories.add(factory);
            return this;
        }

        public Builder withFactories(Collection<AdapterFactory> factories) {
            additionalFactories.addAll(factories);
            return this;
        }

        public Builder withFactories(AdapterFactory... factories) {
            Collections.addAll(additionalFactories, factories);
            return this;
        }

        /**
         * @see FieldCensor
         */
        public Builder withRevision(int revision) {
            this.revision = revision;
            return this;
        }

        public Novel build() {
            return new Novel(
                revision,
                illegalModifiers,
                policies != null ? policies : Policies.withDefaults(),
                !additionalFactories.isEmpty() ? additionalFactories : null,
                !additionalAdapters.isEmpty() ? additionalAdapters : null,
                !additionalWriters.isEmpty() ? additionalWriters : null,
                !additionalReaders.isEmpty() ? additionalReaders : null
            );
        }

    }
}
