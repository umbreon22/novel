package novel.api.types.token;

/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * Represents a generic type {@code T}. Java doesn't yet provide a way to
 * represent generic types, so this class does. Forces clients to create a
 * subclass of this class which enables retrieval the type information even at
 * runtime.
 *
 * <p>For example, to create a type literal for {@code List<String>}, you can
 * create an empty anonymous inner class:
 *
 * <p>
 * {@code TypeToken<List<String>> list = new TypeToken<List<String>>() {};}
 *
 * <p>This syntax cannot be used to create type literals that have wildcard
 * parameters, such as {@code Class<?>} or {@code List<? extends CharSequence>}.
 *
 * @author Bob Lee
 * @author Sven Mawson
 * @author Jesse Wilson
 */
public class TypeToken<T> {

    private final Class<? super T> rawType;
    private final Type type;
    private final int hashCode;

    /**
     * Constructs a new type literal. Derives represented class from type
     * parameter.
     *
     * <p>Clients create an empty anonymous subclass. Doing so embeds the type
     * parameter in the anonymous class's type hierarchy so we can reconstitute it
     * at runtime despite erasure.
     */
    @SuppressWarnings("unchecked")
    private TypeToken() {
        this.type = getSuperclassTypeParameter(getClass());
        this.rawType = (Class<? super T>) $Gson$Types.getRawType(type);
        this.hashCode = type.hashCode();
    }

    /**
     * Unsafe. Constructs a type literal manually.
     */
    @SuppressWarnings("unchecked")
    TypeToken(Type type) {
        if(type == null) throw new NullPointerException("Type cannot be null");
        this.type = $Gson$Types.canonicalize(type);
        this.rawType = (Class<? super T>) $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    /**
     * Returns the type from super class's type parameter in {@link $Gson$Types#canonicalize
     * canonical form}.
     */
    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * Returns the raw (non-generic) type for this type.
     */
    public final Class<? super T> getRawType() {
        return rawType;
    }

    /**
     * Gets underlying {@code Type} instance.
     */
    public final Type getType() {
        return type;
    }

    /**
     * Checks if two types are the same or are equivalent under a variable mapping
     * given in the type map that was provided.
     */
    private static boolean matches(Type from, Type to, Map<String, Type> typeMap) {
        return to.equals(from)
                || (from instanceof TypeVariable
                && to.equals(typeMap.get(((TypeVariable<?>) from).getName())));

    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    @Override
    public final boolean equals(Object o) {
        return o instanceof TypeToken<?> && $Gson$Types.equals(type, ((TypeToken<?>) o).type);
    }

    @Override
    public final String toString() {
        return rawType.toString();//$Gson$Types.typeToString(type);
    }

    /**
     * Gets type literal for the given {@code Type} instance.
     */
    public static TypeToken<?> get(Type type) {
        return new TypeToken<>(type);
    }

    /**
     * Gets type literal for the given {@code Class} instance.
     */
    public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<>(type);
    }

    /**
     * Gets type token for the parameterized type(s) represented by applying {@code typeArguments} to
     * {@code rawType}.
     */
    public static TypeToken<?> getParameterized(Type rawType, Type... typeArguments) {
        return new TypeToken<>($Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments));
    }

    /**
     * Gets the type token for the given field.
     * This allows us to process runtime parameterized types.
     * Should return List<String> vs List<E>, etc.
     */
    public static TypeToken<?> get(Field field) {
        var fieldType = field.getType();
        if(fieldType.getTypeParameters().length > 0) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            return TypeToken.getParameterized(fieldType, parameterizedType.getActualTypeArguments());
        } else return get(fieldType);
    }

    /**
     * Gets type literal for the parameterized type represented by applying {@code typeArguments} to
     * {@code rawType}.
     */
    public static Type getParameterizedType(Type rawType, Type... typeArguments) {
        return new TypeToken<>($Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments)).getType();
    }

    public static Type[] getActualTypeArguments(Class<?> clazz) {
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments();
    }

    /**
     * Gets type literal for the array type whose elements are all instances of {@code componentType}.
     */
    public static TypeToken<?> getArray(Type componentType) {
        return new TypeToken<>($Gson$Types.arrayOf(componentType));
    }

    /**
     * @return true if this is an array token
     */
    public boolean isArray() {
        return type instanceof GenericArrayType
            || type instanceof Class && ((Class<?>) type).isArray();
    }

}
