package novel.api.types.token;

/**
 * An object that can generate its own {@link TypeToken}.
 */
public interface Tokenable {

    /**
     * Instantiates a new {@link TypeToken} for this object's {@link Class}.
     * @return a new {@link TypeToken} for this object's {@link Class}
     */
    default TypeToken<?> token() {
        Class<?> clazz = getClass();
        if(clazz.getTypeParameters().length > 0) {
            return TypeToken.getParameterized(clazz, clazz.getTypeParameters());
        } else {
            return TypeToken.get(clazz);
        }
    }

    /**
     * todo: Not sure if I like this...
     * Casts the token for you! Dangerous to use in practice.
     * @param <T> Any type! Hopefully a correct one!
     * @return It's dangerous to go alone, cast {@link this#token()}...
     * or null, if it cannot be cast.
     */
    @SuppressWarnings("unchecked")
    default <T extends Tokenable> TypeToken<T> castToken() {
        try {
            return (TypeToken<T>) token();
        } catch (ClassCastException ex) {
            return null;
        }
    }

}
