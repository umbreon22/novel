package novel.internal.registry;


import novel.api.Novel;
import novel.api.types.adapt.CannotAdapt;
import novel.api.types.factory.AdapterFactory;
import novel.api.types.adapt.ObjectDataAdapter;
import novel.api.types.token.TypeToken;

import java.util.Optional;

class ObjectAdapterRegistry extends TokenRegistry<ObjectDataAdapter<?>, AdapterFactory> {

    ObjectAdapterRegistry(Novel parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    public <T> ObjectDataAdapter<T> get(TypeToken<T> typeToken) {
        var adapter = tokenMap.get(typeToken);
        if(adapter != null) {
            return (ObjectDataAdapter<T>) adapter;
        }
        var newAdapter = findFactoryAndCreate(typeToken).orElse(new CannotAdapt<>(typeToken));
        register(typeToken, newAdapter);
        return newAdapter;
    }

    private <T> Optional<ObjectDataAdapter<T>> findFactoryAndCreate(TypeToken<T> typeToken) {
        for(var factory : factories) {
            ObjectDataAdapter<T> creation = factory.create(parent, typeToken);
            if(creation != null) {
                return Optional.of(creation);
            }
        }
        return Optional.empty();
    }

}
