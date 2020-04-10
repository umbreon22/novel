package novel.api.types.adapt;

import novel.api.types.read.Readable;
import novel.api.types.write.Writeable;
import novel.api.types.token.TypeToken;

import java.util.List;
import java.util.Objects;

/**
 * todo: maybe move this to Novel utility methods? i.e. novel.wrap(class), novel.wrap(object).. etc.
 * A collection of static methods that wraps primitives and certain basic Java types as {@link Novelable}s.
 * @see #defaultSupportedTypes
 * This will allow them to be read and written without implementing the basic {@link Writeable}
 * and {@link Readable} interfaces.
 * @param <T> an object's type
 */
public abstract class NovelableWrapper<T> implements Novelable {

    private static final List<Class<?>> defaultSupportedTypes = List.of(
        boolean.class, Boolean.class,
        int.class, Integer.class,
        byte.class, Byte.class,
        short.class, Short.class,
        char.class, Character.class,
        long.class, Long.class,
        double.class, Double.class,
        float.class, Float.class,
        CharSequence.class, String.class
    );

    private NovelableWrapper() {}

    /**
     * Resolves an object into supported wrapper types
     * @param obj an object
     * @return A wrapped type ripe for novelization
     * @throws IllegalArgumentException if the provided object cannot be wrapped
     */
    public static NovelableWrapper<?> of(Object obj) throws IllegalArgumentException {
        //maybe just use <T> instead and check types beforehand?
        if(obj instanceof String) {
            return strings((String) obj);
        } if(obj instanceof Long) {
            return longs((Long) obj);
        } else if(obj instanceof Integer) {
            return ints((Integer) obj);
        } else if(obj instanceof Short) {
            return shorts((Short) obj);
        } else if(obj instanceof Double) {
            return doubles((Double) obj);
        } else if(obj instanceof Float) {
            return floats((Float) obj);
        } else if(obj instanceof Byte) {
            return bytes((Byte) obj);
        } else if(obj instanceof Character) {
            return chars((Character) obj);
        } else if(obj instanceof Boolean) {
            return bools((Boolean) obj);
        } else {
            throw new IllegalArgumentException("Cannot wrap type: " + obj.getClass().getCanonicalName());
        }
    }

    /**
     * Wraps a {@link Class} as a {@link NovelableWrapper},
     * then generates a {@link TypeToken} for that wrapped class.
     */
    @SuppressWarnings("unchecked")
    public static <T> TypeToken<NovelableWrapper<T>> wrappedToken(Class<T> clazz) {
        for(var type : defaultSupportedTypes) {
            if(type.isAssignableFrom(clazz)) {
                return (TypeToken<NovelableWrapper<T>>) TypeToken.getParameterized(NovelableWrapper.class, type);
            }
        }
        throw new IllegalArgumentException("Cannot wrap type: " + clazz.getCanonicalName());
    }

    /**
     * As this is a wrapper, we need a way to.. unwrap.
     * @return the wrapped object
     */
    public abstract T unwrap();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NovelableWrapper
            && Objects.equals(this.unwrap(), ((NovelableWrapper<?>) obj).unwrap());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(unwrap());
    }

    public static NovelableWrapper<Byte> bytes(byte me) {
        return new NovelableWrapper<>() {
            public Byte unwrap() {
                return me;
            }
        };
    }

    public static NovelableWrapper<Integer> ints(int integer) {
        return new NovelableWrapper<>() {
            public Integer unwrap() {
                return integer;
            }
        };
    }

    public static NovelableWrapper<Short> shorts(short shorty) {
        return new NovelableWrapper<>() {
            public Short unwrap() {
                return shorty;
            }
        };
    }


    public static NovelableWrapper<Double> doubles(double d) {
        return new NovelableWrapper<>() {
            public Double unwrap() {
                return d;
            }
        };
    }


    public static NovelableWrapper<Float> floats(float on) {
        return new NovelableWrapper<>() {
            public Float unwrap() {
                return on;
            }
        };
    }

    public static NovelableWrapper<Long> longs(long shlong) {
        return new NovelableWrapper<>() {
            public Long unwrap() {
                return shlong;
            }
        };
    }


    private static NovelableWrapper<Character> chars(Character character) {
        return new NovelableWrapper<>() {
            @Override
            public Character unwrap() {
                return character;
            }
        };
    }

    public static NovelableWrapper<String> strings(String string) {
        return new NovelableWrapper<>() {
            public String unwrap() {
                return string;
            }
        };
    }

    private static NovelableWrapper<Boolean> bools(boolean bool) {
        return new NovelableWrapper<>() {
            @Override
            public Boolean unwrap() {
                return bool;
            }
        };
    }

}
