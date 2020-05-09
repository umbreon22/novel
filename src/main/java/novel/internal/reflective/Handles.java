package novel.internal.reflective;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class Handles {


    private static final Logger log = LoggerFactory.getLogger(Handles.class);

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private Handles() { throw new UnsupportedOperationException("no"); }

    /**
     * Attempts to acquire a {@link java.lang.invoke.VarHandle} for the provided field and containing class.
     * If we are unable to access the VarHandle, we fallback on {@link ReflectiveFieldHandle} which uses Fields directly.
     */
    static ReflectiveHandle acquire(Field field) {
        ReflectiveHandle acquiredHandle;
        try {
            MethodHandles.Lookup lookup = LOOKUP;
            if(canLookupPrivately(field)) {
                lookup = MethodHandles.privateLookupIn(field.getType(), LOOKUP);
            }
            acquiredHandle = new ReflectiveVarHandle(lookup.unreflectVarHandle(field));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.warn("Could not acquire VarHandle for {}, using the field directly because {}",
                String.format("%s#%s", field.getType().getCanonicalName(), field.getName()), e.getMessage()
            );
            acquiredHandle = new ReflectiveFieldHandle(field);
        }
        return acquiredHandle;
    }

    /**
     * Arrays and primitives cannot be looked up privately.
     * @see MethodHandles#privateLookupIn(Class, MethodHandles.Lookup) 
     */
    private static boolean canLookupPrivately(Field field) {
        return !Modifier.isPublic(field.getModifiers())
                && !field.getType().isPrimitive()
                && !field.getType().isArray();
    }
}
