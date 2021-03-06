package novel.api.types.annotations;

import novel.internal.reflective.ReflectiveObjectReader;
import novel.internal.reflective.ReflectiveObjectWriter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * This annotation is used to bypass the null check in
 * {@link ReflectiveObjectReader} and {@link ReflectiveObjectWriter}
 * This will save 1 boolean of data per field!
 * However, if an instance of the annotated {@link java.lang.reflect.Field} is null,
 * serialization will <b>fail</b>.
 * Primitives do not need this annotation and are inherently treated as {@link Inked}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, LOCAL_VARIABLE})
public @interface Inked {
    String description() default "";
}
