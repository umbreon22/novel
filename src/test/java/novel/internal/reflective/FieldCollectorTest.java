package novel.internal.reflective;

import novel.api.FieldCensor;
import novel.api.FieldCollector;
import novel.internal.testutil.FieldFilter;
import novel.internal.testutil.FieldsWithModifiers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
public class FieldCollectorTest {
    //todo: add additional coverage with additional functionality in Novel.Builder

    @Test
    void testNothing() {
        int collected = collectCensored();
        long counted = countFiltered();
        Assertions.assertEquals(counted, collected);
    }

    @Test
    void testTransient() {
        int collected = collectCensored(Modifier.TRANSIENT);
        long counted = countFiltered(FieldFilter::notTransient);
        Assertions.assertEquals(counted, collected);
    }

    @Test
    void testStatic() {
        int collected = collectCensored(Modifier.STATIC);
        long counted = countFiltered(FieldFilter::notStatic);
        Assertions.assertEquals(counted, collected);
    }

    @Test
    void testTransientAndStatic() {
        int collected = collectCensored(Modifier.STATIC, Modifier.TRANSIENT);
        long counted = countFiltered(FieldFilter::notStatic, FieldFilter::notTransient);
        Assertions.assertEquals(counted, collected);
    }

    private static long countFiltered(Consumer<FieldFilter<?>>... consumers) {
        var filter = new FieldFilter<>(FieldsWithModifiers.class);
        for(var consumer : consumers) {
            consumer.accept(filter);
        }
        return filter.count();
    }

    private static int collectCensored(int... modifiers) {
        return collectorFor(modifiers)
                .collect(FieldsWithModifiers.class)
                .size();
    }

    static FieldCollector collectorFor(int... modifiersToCensor) {
        FieldCensor censor = new FieldCensor(0,
                IntStream.of(modifiersToCensor)
                        .boxed()
                        .collect(Collectors.toList())
        );
        return new AbstractFieldCollector() {
            @Override public boolean shouldCollect(Field field) { return !censor.shouldCensor(field); }
            @Override public boolean shouldThrow(Field field) { return false;}
        };
    }

}
