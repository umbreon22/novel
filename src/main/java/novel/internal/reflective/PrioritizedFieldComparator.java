package novel.internal.reflective;

import novel.api.types.annotations.Folio;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * A {@link Comparator<Field>} using {@link Folio}.
 * Falls back on {@link #fallback}
 * if {@link Folio#value()}s are equivalent.
 */
class PrioritizedFieldComparator implements Comparator<Field> {
    private final Comparator<Field> fallback;
    PrioritizedFieldComparator(Comparator<Field> fallback) {
        this.fallback = fallback;
    }

    @Override
    public int compare(Field a, Field b) {
        int priority = Integer.compare(getPriority(a), getPriority(b));
        if(priority == 0) {
            return fallback.compare(a, b);
        } else return priority;
    }

    private int getPriority(Field field) {
        Folio annotation = field.getAnnotation(Folio.class);
        if(annotation != null) {
            return annotation.value();
        } else return Integer.MAX_VALUE;
    }

}
