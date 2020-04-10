package novel.internal.registry;


import novel.api.Novel;
import novel.api.types.token.TypeToken;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

abstract class TokenRegistry<V, Factory> {

    protected final Map<TypeToken<?>, V> tokenMap;
    protected final LinkedList<Factory> factories;
    protected final Novel parent;

    TokenRegistry(Novel parent) {
        this.parent = parent;
        this.tokenMap = new ConcurrentHashMap<>();
        this.factories = new LinkedList<>();
    }

    void register(TypeToken<?> token, V value) {
        tokenMap.put(token, value);
    }

    void registerFirst(Factory factory) {
        this.factories.addFirst(factory);
    }

    void registerLast(Factory factory) {
        this.factories.addLast(factory);
    }

}
