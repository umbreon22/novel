package novel.internal.reflective.handles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class Handles {


    private static final Logger log = LoggerFactory.getLogger(Handles.class);

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final Module thisModule = Handles.class.getModule();
    private Handles() { throw new UnsupportedOperationException("no"); }

    /**
     * Attempts to acquire a {@link java.lang.invoke.VarHandle} for the provided field and containing class.
     * If we are unable to access the VarHandle, we fallback on {@link ReflectiveFieldHandle} which uses Fields directly.
     */
    static ReflectiveHandle acquire(Field field) {
        ReflectiveHandle acquiredHandle;
        MethodHandles.Lookup lookup = LOOKUP;
        try {
            if(!thisModule.canUse(field.getType())) {
                log.warn("Unable to access module. Please use \"{}\" for optimal performance.",
                    addOpensString(field.getType())
                );
            }
            if(canLookupPrivately(field)) {
                lookup = MethodHandles.privateLookupIn(field.getType(), LOOKUP);
            }
            acquiredHandle = new ReflectiveVarHandle(lookup.unreflectVarHandle(field));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            try {
                acquiredHandle = acquireMethodHandle(lookup, field);
                log.warn("Could not acquire VarHandle for {}, using method handles because {}",
                    fullFieldString(field), e.getMessage()
                );
            } catch (IllegalAccessException x) {
                log.warn("Could not acquire VarHandle or MethodHandle for {}, using the field directly because {}",
                    fullFieldString(field), x.getMessage()
                );
                acquiredHandle = new ReflectiveFieldHandle(field);
            }
        }
        return acquiredHandle;
    }

    private static String fullFieldString(Field field) {
        return String.format("%s#%s",
            field.getDeclaringClass().getSimpleName(),
            field.getName()
        );
    }

    private static String addOpensString(Class<?> type) {
        return String.format("--add-opens %s/%s=%s",
            type.getModule().getName(), type.getPackageName(), thisModule.getName()
        );
    }

    private static ReflectiveHandle acquireMethodHandle(MethodHandles.Lookup lookup, Field field) throws IllegalAccessException {
        return new ReflectiveMethodHandle(
            lookup.unreflectGetter(field),
            lookup.unreflectSetter(field)
        );
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
