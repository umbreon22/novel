package novel.internal.reflective.handles;

import java.lang.reflect.Field;

/**
 * A {@Link ReflectiveHandle} implementation using {@link Field}s
 */
class ReflectiveFieldHandle implements ReflectiveHandle {

    private final Field field;
    ReflectiveFieldHandle(Field field) {
        this.field = field;
    }

    @Override
    public void set(Object instance, Object memberValue) throws IllegalAccessException {
        field.set(instance, memberValue);
    }

    @Override
    public Object get(Object instance) throws IllegalAccessException {
        return field.get(instance);
    }
    
}