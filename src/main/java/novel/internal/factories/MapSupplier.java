package novel.internal.factories;

import novel.api.types.token.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

final class MapSupplier {

    private MapSupplier() {}
    /**
     * Uses the type token to determine an appropriate Collection constructor to use.
     * @return
     */
    @SuppressWarnings("unchecked")
    static Supplier<Map<?, ?>> fromToken(TypeToken<?> typeToken) {
        var rawType = typeToken.getRawType();
        if(!rawType.isInterface() && !Modifier.isAbstract(rawType.getModifiers())) {
            return tryNoArgsConstructor(rawType);
        } else return getMapConstructorFromInterface(rawType);
    }

    /**
     * If explicitly stated, we assume implementation matters.
     * If a map implementation lacks a no-args constructor, this will FAIL.
     */
    @SuppressWarnings("unchecked")
    static Supplier<Map<?,?>> tryNoArgsConstructor(Class<?> rawType) {
        Constructor<?> constructor;
        try {
            constructor = rawType.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Unsupported Map: " + rawType);
        }
        return () -> {
            try {
                return (Map<?,?>) constructor.newInstance();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new IllegalStateException("Unsupported Map: " + rawType);
            }
        };
    }

    /**
     * If the field is an interface, we assume implementation does not matter.
     */
    private static Supplier<Map<?,?>> getMapConstructorFromInterface(Class<?> rawType) {
        if (NavigableMap.class.isAssignableFrom(rawType)) {
            return TreeMap::new;
        } else {//probably Map?
            return LinkedHashMap::new;
        }
    }


}
