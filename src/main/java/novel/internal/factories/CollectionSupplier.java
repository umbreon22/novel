package novel.internal.factories;

import novel.api.types.token.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

final class CollectionSupplier {

    private CollectionSupplier() {}
    /**
     * Uses the type token to determine an appropriate Collection constructor to use.
     */
    @SuppressWarnings("unchecked")
    static <T> Supplier<Collection<T>> fromToken(TypeToken<T> typeToken) {
        var rawType = typeToken.getRawType();
        if (EnumSet.class.isAssignableFrom(rawType)) {//Special case!
            var type = typeToken.getType();
            if (type instanceof ParameterizedType) {
                var enumType = (Class) ((ParameterizedType) typeToken.getType()).getActualTypeArguments()[0];
                if(enumType.isEnum()) {
                    return () -> EnumSet.noneOf(enumType);
                } else throw new IllegalArgumentException(enumType + " is not an enum");
            } else throw new IllegalArgumentException(type + " is not a valid parameterized type");
        } else if (!rawType.isInterface() && !Modifier.isAbstract(rawType.getModifiers())) {//ehhhh
            return tryNoArgsConstructor(rawType);
        } else {
            return getCollectionConstructorFromInterface(rawType);
        }
    }


    /**
     * If the field is explicitly stated, we assume implementation matters.
     * If a collection implementation lacks a no-args constructor, we'll need to support it alternatively.
     */
    @SuppressWarnings("unchecked")
    private static <T> Supplier<Collection<T>> tryNoArgsConstructor(Class<? super T> rawType) {
        Constructor<? super T> constructor;
        try {
            constructor = rawType.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Unsupported Collection: " + rawType);
        }
        return () -> {
            try {
                return (Collection<T>) constructor.newInstance();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new IllegalStateException("Unsupported Collection: " + rawType);
            }
        };
    }

    /**
     * If the field is an interface, we assume implementation does not matter.
     */
    private static <T> Supplier<Collection<T>> getCollectionConstructorFromInterface(Class<? super T> rawType) {
        if (SortedSet.class.isAssignableFrom(rawType)) {
            return TreeSet::new;
        } else if (Set.class.isAssignableFrom(rawType)) {
            return LinkedHashSet::new;
        } else if(Deque.class.isAssignableFrom(rawType)) {
            return ArrayDeque::new;
        } else if (Queue.class.isAssignableFrom(rawType)) {
            return LinkedList::new;
        } else {//Probably LIST or COLLECTION
            return ArrayList::new;
        }
    }


}
