package novel.api;

import novel.api.types.write.writers.ObjectDataWriter;
import novel.internal.registry.NovelAdapterRegistry;

/**
 * Customize your {@link Novel} instance with these configurable properties.
 */
//todo: may remove
//todo: may move to Novel inner class
public class Policies {

    private final boolean wrapAdapters;
    private final boolean useDefaultFactories;//may be stupid but ok for now

    private Policies(boolean shouldWrapAdapters, boolean useDefaultFactories) {
        this.wrapAdapters = shouldWrapAdapters;
        this.useDefaultFactories = useDefaultFactories;
    }

    private static final Policies DEFAULTS = newBuilder()
            .shouldWrapAdapters(false)
            .shouldUseDefaultFactories(true)
            .build();

    public static Policies withDefaults() {
        return DEFAULTS;
    }

    /**
     * When registering individual {@link novel.api.types.read.ObjectDataReader}s
     * and {@link ObjectDataWriter}s,
     * you may want to merge an individually registered reader
     * with an individually registered writer as a WrappedAdapter
     * if possible.
     * @see NovelAdapterRegistry
     * @return true if enabled
     */
    public boolean shouldMergeReadersAndWriters() {
        return wrapAdapters;
    }

    public static Policies.Builder newBuilder() {
        return new Builder();
    }

	public boolean shouldUseDefaultJavaFactories() {
        return useDefaultFactories;
	}

	public static class Builder {

        private boolean shouldWrapAdapters;
        private boolean useDefaultFactories;

        private Builder() {//defaults
            this.shouldWrapAdapters = true;
        }

        Builder shouldWrapAdapters(boolean enabled) {
            this.shouldWrapAdapters = enabled;
            return this;
        }

        Builder shouldUseDefaultFactories(boolean enabled) {
            this.useDefaultFactories = enabled;
            return this;
        }

        public Policies build() {
            return new Policies(shouldWrapAdapters, useDefaultFactories);
        }
    }
}
