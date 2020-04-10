package novel.internal.reflective;

import novel.api.types.annotations.Folio;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * A {@link Comparator<Field>} using {@link Folio}.
 * Falls back on comparing {@link Field#getName()} using
 * {@link CharSequence#compare(CharSequence, CharSequence)}
 * if {@link Folio#value()}s are equivalent.
 */
class PrioritizedFieldComparator implements Comparator<Field> {

    @Override
    public int compare(Field a, Field b) {
        int priority = Integer.compare(getPriority(a), getPriority(b));
        if(priority == 0) {
            return CharSequence.compare(a.getName(), b.getName());
        } else return priority;
    }

    private int getPriority(Field field) {
        Folio annotation = field.getAnnotation(Folio.class);
        if(annotation != null) {
            return annotation.value();
        } else return Integer.MAX_VALUE;
    }

}
