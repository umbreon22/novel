package novel.api;

import novel.internal.reflective.SortedFieldCollector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see SortedFieldCollector for my general idea, will need to think this through...
 * */
public interface FieldCollector {

    Collection<Field> collect(Class<?> toCollect, Class<?> collectingFrom);

    default Collection<Field> collect(Class<?> collectingFrom) {
        return collect(Object.class, collectingFrom);
    }

    default Stream<Field> stream(Class<?> toCollect, Class<?> collectingFrom) {
        return collect(toCollect, collectingFrom).stream();
    }

    default <T> Collection<T> collectAndMap(Class<?> toCollect, Class<?> collectingFrom, Function<Field, T> mapper) {
        return stream(toCollect, collectingFrom)
                .map(mapper)
                .collect(Collectors.toList());
    }

    boolean shouldCollect(Field field);
    boolean shouldThrow(Field field);
}
