package novel.internal.reflective;

import novel.api.FieldCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

abstract class AbstractFieldCollector implements FieldCollector {

    private static final Logger log = LoggerFactory.getLogger(AbstractFieldCollector.class);

    protected final void gatherFields(Collection<Field> collectedFields, Class<?> superClass, Class<?> currentClass) {
        if(currentClass.isInterface()) {
            return;
        }
        if(ReflectiveUtil.hasSuperclassOf(superClass, currentClass)){
            gatherFields(collectedFields, superClass, currentClass.getSuperclass());
        }
        Field[] fields = currentClass.getDeclaredFields();
        Set<Type> unsupportedTypes = new HashSet<>();
        for(Field field : fields) {
            if(!field.trySetAccessible()) {
                throw new IllegalStateException("Cannot access " + field);
            }
            if(shouldCollect(field)) {
                if(shouldThrow(field)) {
                    unsupportedTypes.add(field.getType());
                } else {
                    if (!collectedFields.add(field)) {
                        log.debug("Could not add {} to field collection.", field.getName());
                    }
                }
            }
        }
        if(!unsupportedTypes.isEmpty()) {
            throw new UnsupportedOperationException("Unsupported: " + unsupportedTypes);
        }
    }

    @Override
    public Collection<Field> collect(Class<?> toCollect, Class<?> collectingFrom) {
        Collection<Field> anyCollectionReally = new HashSet<>();
        gatherFields(anyCollectionReally, toCollect, collectingFrom);
        return anyCollectionReally;
    }

}
