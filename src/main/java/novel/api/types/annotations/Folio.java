package novel.api.types.annotations;

import novel.internal.reflective.ReflectiveObjectReader;
import novel.internal.reflective.ReflectiveObjectWriter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;

@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, RECORD_COMPONENT})
public @interface Folio {
    /**
     * This value is used in comparison with other fields
     * to determine their write/read order in
     * {@link ReflectiveObjectReader} and {@link ReflectiveObjectWriter}
     * via {@link Integer#compare(int, int)}
     */
    int value() default Integer.MAX_VALUE;
}