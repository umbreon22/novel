package novel.internal.reflective;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class Handles {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private Handles() { throw new UnsupportedOperationException("no"); }

    /**
     * Attempts to acquire a {@link java.lang.invoke.VarHandle} for the provided field and containing class.
     * If we are unable to access the VarHandle, we fallback on {@link ReflectiveFieldHandle} which uses Fields directly.
     */
    static ReflectiveHandle acquire(Field field, Class<?> parentType) {
        ReflectiveHandle acquiredHandle;
        try {
            MethodHandles.Lookup lookup = LOOKUP;
            if(Modifier.isPublic(field.getModifiers())) {
                lookup = MethodHandles.privateLookupIn(parentType, LOOKUP);
            }
            acquiredHandle = new ReflectiveVarHandle(lookup.unreflectVarHandle(field));
        } catch (IllegalAccessException e) {
            acquiredHandle = new ReflectiveFieldHandle(field);
        }
        return acquiredHandle;
    }
}
