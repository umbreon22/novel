package novel.api;

import novel.api.types.annotations.SinceRevision;
import novel.api.types.annotations.UntilRevision;
import novel.internal.reflective.ReflectiveObjectReader;
import novel.internal.reflective.ReflectiveObjectWriter;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Determines whether or not a {@link Field} should be serialized by reflective readers and writers.
 * @see ReflectiveObjectReader
 * @see ReflectiveObjectWriter
 * Inspired by https://github.com/google/gson/blob/master/gson/src/main/java/com/google/gson/internal/Excluder.java
 */
public class FieldCensor {

    private final int revision;
    private final int illegalModifiers;

    public FieldCensor(int revision, Iterable<Integer> illegalModifiers) {
        this.revision = revision;
        int modifiersThatWillBeIllegalStartingNow = 0;
        for(int illegalModifier : illegalModifiers) {
            modifiersThatWillBeIllegalStartingNow |= illegalModifier;
        }
        this.illegalModifiers = modifiersThatWillBeIllegalStartingNow;
    }

    private boolean hasIllegalModifier(int modifiers) {
        return (modifiers & illegalModifiers) != 0;
    }

    private boolean isValidRevision(SinceRevision since, UntilRevision until) {
        return isValidRevision(since) && isValidRevision(until);
    }

    private boolean isValidRevision(SinceRevision since) {
        return since == null || revision >= since.value();
    }

    private boolean isValidRevision(UntilRevision until) {
        return until == null || revision <= until.value();
    }

    public boolean shouldCensor(Field field) {
        if(hasIllegalModifier(field.getModifiers())) {
            return true;
        } else {
            SinceRevision since = field.getAnnotation(SinceRevision.class);
            UntilRevision until = field.getAnnotation(UntilRevision.class);
            return !isValidRevision(since, until);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldCensor that = (FieldCensor) o;
        return revision == that.revision &&
                illegalModifiers == that.illegalModifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(revision, illegalModifiers);
    }
}
