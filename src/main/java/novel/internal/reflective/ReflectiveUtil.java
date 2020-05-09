package novel.internal.reflective;

import novel.api.types.annotations.Inked;
import novel.api.types.read.Readable;
import novel.api.types.write.AutoWriteable;
import novel.api.types.write.Writeable;

import java.lang.reflect.Field;
import java.util.Objects;

public final class ReflectiveUtil {

    ReflectiveUtil(){ throw new UnsupportedOperationException("Do not instantiate me! :("); }

    /**
     * Determines if the current class has {@code clazz} as a superclass, excluding {@link Object}.
     * @param currentClass your current class
     * @param clazz your queried superclass
     * @return true if {@code clazz} matches {@code currentClass}'s {@link Class#getSuperclass()}
     */
    static boolean hasSuperclassOf(Class<?> currentClass, Class<?> clazz) {
        return currentClass.getSuperclass() != null
            && currentClass.getSuperclass() != clazz
            && currentClass.getSuperclass() != Object.class
            ;
    }

    /**
     * Determines if a class has a {@link Writeable} superclass.
     * @see #hasSuperclassOf(Class, Class)
     */
    static boolean hasWriteableSuperclass(Class<?> currentClass) {
        return hasSuperclassOf(currentClass, Writeable.class);
    }

    /**
     * Determines if a class has a {@link Readable} superclass.
     * @see #hasSuperclassOf(Class, Class)
     */
    static boolean hasReadableSuperclass(Class<?> currentClass) {
        return hasSuperclassOf(currentClass, Readable.class);
    }

    /**
     * If a field is marked {@link Inked}, we consider it nullsafe.
     * We also treat primitives as {@link Inked}.
     */
    static boolean isNullsafe(Field field) {
        return field.getType().isPrimitive()
            || field.isAnnotationPresent(Inked.class);
    }

    public static boolean isNullsafe(Class<?> clazz) {
        return clazz.isAnnotationPresent(Inked.class);
    }

    public static boolean isAutoWriteable(Class<?> fieldType) {
        return AutoWriteable.class.isAssignableFrom(fieldType);
    }

    /**
     * A {@link Object#equals(Object)} utility method for anonymous classes.
     */
    public static boolean anonymousEquals(Object a, Object b) {
        if(a == null && b == null) {
            return true;
        } else if(a != null && b != null && a.getClass() == b.getClass()) {
            try {
                for (Field field : a.getClass().getFields()) {
                    if(!field.trySetAccessible()) {
                        throw new IllegalStateException("Could not access field " + field.getName());
                    }
                    boolean equals = Objects.equals(field.get(a), field.get(b));
                    if(!equals) return false;
                }
                return true;
            } catch (IllegalAccessException ignored) {}
        }
        return false;
    }
}
