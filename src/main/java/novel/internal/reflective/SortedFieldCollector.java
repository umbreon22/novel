package novel.internal.reflective;

import novel.api.types.annotations.Folio;
import novel.api.Novel;
import novel.api.types.token.TypeToken;
import novel.api.types.read.Readable;
import novel.api.types.write.Writeable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class gathers fields meeting {@link Novel}'s 'writeable' and 'readable' conditions.
 */
public class SortedFieldCollector extends AbstractFieldCollector {

    private static final Comparator<Field> BY_FOLIO_PRIORITY = new PrioritizedFieldComparator();

    private final Comparator<Field> fieldComparator;
    private final Novel novel;
    public SortedFieldCollector(Novel novel, Comparator<Field> fieldComparator) {
        this.novel = novel;
        this.fieldComparator = fieldComparator;
    }

    /**
     * With {@link PrioritizedFieldComparator}, you can control the sort order
     * in {@link #gatherFieldsSorted(Class, Class)}
     * via {@link Folio} annotations.
     * {@see PrioritizedFieldComparator} for more information.
     * Those with the same or no priority will be sorted
     * with a String comparison on {@link Field#getName()}
     */
    public static SortedFieldCollector usingFolio(Novel novel) {
        return new SortedFieldCollector(novel, BY_FOLIO_PRIORITY);
    }

    /**
     * Gathers all {@link Writeable} fields.
     * @see #gatherFieldsSorted(Class, Class)
     */
    SortedSet<Field> gatherWriteableFields(Class<?> collectingFrom) {
        return gatherFieldsSorted(Writeable.class, collectingFrom);
    }

    /**
     * Gathers all {@link Readable} fields.
     * @see #gatherFieldsSorted(Class, Class)
     */
    SortedSet<Field> gatherReadableFields(Class<?> collectingFrom) {
        return gatherFieldsSorted(Readable.class, collectingFrom);
    }

    /**
     * We sort these fields to guarantee their order, as their order is JVM-specific.
     * @see #fieldComparator
     * @param superClass
     * @param currentClass
     * @return a SORTED list of fields.
     */
    SortedSet<Field> gatherFieldsSorted(Class<?> superClass, Class<?> currentClass) {
        SortedSet<Field> sortMe = new TreeSet<>(fieldComparator);
        gatherFields(sortMe, superClass, currentClass);
        return sortMe;
    }

    @Override
    public Collection<Field> collect(Class<?> toCollect, Class<?> collectingFrom) {
        return gatherFieldsSorted(toCollect, collectingFrom);
    }

    @Override
    public boolean shouldCollect(Field field) {
        return !novel.fieldCensor().shouldCensor(field);
    }

    @Override
    public boolean shouldThrow(Field field) {
        TypeToken<?> token = TypeToken.get(field);
        return !(novel.canRead(token) || novel.canWrite(token));
    }
}
