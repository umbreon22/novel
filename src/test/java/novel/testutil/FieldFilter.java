package novel.testutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

public class FieldFilter<T> {

    private static final IntPredicate PREDICATE_TRUE = i -> true;
    private final Class<T> clazz;
    private final Set<IntPredicate> andPredicates;

    public FieldFilter(Class<T> clazz) {
        this.clazz = clazz;
        this.andPredicates = new HashSet<>();
    }

    public FieldFilter<T> andNot(IntPredicate predicate) {
        andPredicates.add(predicate.negate());
        return this;
    }

    public FieldFilter<T> and(IntPredicate and) {
        andPredicates.add(and);
        return this;
    }

    public FieldFilter<T> andTransient() {
        return and(Modifier::isTransient);
    }

    public FieldFilter<T> notTransient() {
        return andNot(Modifier::isTransient);
    }

    public FieldFilter<T> andFinal() {
        return and(Modifier::isFinal);
    }

    public FieldFilter<T> notFinal() {
        return andNot(Modifier::isFinal);
    }

    public FieldFilter<T> andStatic() {
        return and(Modifier::isStatic);
    }

    public FieldFilter<T> notStatic() {
        return andNot(Modifier::isStatic);
    }

    public FieldFilter<T> andPrivate() {
        return and(Modifier::isPrivate);
    }

    public FieldFilter<T> notPrivate() {
        return andNot(Modifier::isPrivate);
    }

    public FieldFilter<T> andProtected() {
        return and(Modifier::isProtected);
    }

    public FieldFilter<T> notProtected() {
        return andNot(Modifier::isProtected);
    }

    public int count() {
        IntPredicate predicate = andPredicates.stream()
                .reduce(IntPredicate::and)
                .orElse(PREDICATE_TRUE);
        return (int) streamMyFields()
                .mapToInt(Field::getModifiers)
                .filter(predicate)
                .count();
    }

    private Stream<Field> streamMyFields() {
        return Arrays.stream(clazz.getDeclaredFields());
    }

}
