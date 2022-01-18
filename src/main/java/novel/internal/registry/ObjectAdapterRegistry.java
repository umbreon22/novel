package novel.internal.registry;


import novel.api.Novel;
import novel.api.types.adapt.CannotAdapt;
import novel.api.types.adapt.SuperAdapter;
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
        ObjectDataAdapter<T> adapter = (ObjectDataAdapter<T>) tokenMap.get(typeToken);
        if(adapter != null) {
            return adapter;
        }
        adapter = findSuperAdapter(typeToken.getRawType());
        if (adapter == null) {
            adapter = findFactoryAndCreate(typeToken).orElse(new CannotAdapt<>(typeToken));
        }
        register(typeToken, adapter);
        return adapter;
    }

    @SuppressWarnings("unchecked")
    private <T> ObjectDataAdapter<T> findSuperAdapter(Class<? super T> rawType) {
        var superClass = rawType.getSuperclass();
        if(superClass == null
                || superClass == Object.class
                || rawType.getTypeParameters().length > 0
        ) {
            //todo: perhaps transfer type parameters to the parent class if applicable?
            return null;
        } else {
            var adapter = tokenMap.get(TypeToken.get(rawType.getSuperclass()));
            if(adapter instanceof SuperAdapter) {
                return (ObjectDataAdapter<T>) adapter;
            } else {
                return findSuperAdapter(superClass);
            }
        }
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
